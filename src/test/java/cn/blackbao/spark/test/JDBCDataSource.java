package cn.blackbao.spark.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;


public class JDBCDataSource {
	public static void main(String[] args) {
		SparkSession sparkSession = SparkSession.builder().appName("jdbc").master("local").getOrCreate();
		
		JavaSparkContext sc = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
		
		Map<String,String> info = new HashMap<>();
		info.put("url", "jdbc:mysql://node1:3306/spark");
		info.put("dbtable", "score");
		info.put("user", "root");
		info.put("password", "1234");
		Dataset<Row> score = sparkSession.read().format("jdbc").options(info).load();
		info.put("dbtable", "user");
		Dataset<Row> user = sparkSession.read().format("jdbc").options(info).load();
		
//		List<StructField> fields = new ArrayList<>();
//		fields.add(DataTypes.createStructField("id", DataTypes.IntegerType, true));
//		fields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
//		fields.add(DataTypes.createStructField("userId", DataTypes.IntegerType, true));
//		fields.add(DataTypes.createStructField("score", DataTypes.IntegerType, true));
//		
//		StructType structType = DataTypes.createStructType(fields);
		
		score.toJavaRDD().saveAsTextFile("score");
		user.toJavaRDD().saveAsTextFile("user");
		
		
		JavaPairRDD<Integer, Row> userRDD = user.toJavaRDD().mapToPair(new PairFunction<Row, Integer, Row>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<Integer, Row> call(Row row) throws Exception {
				return new Tuple2<Integer, Row>((int)row.getAs("id"),row);
			}
		}) ;
		JavaPairRDD<Integer, Row> scoreRDD = score.toJavaRDD().mapToPair(new PairFunction<Row, Integer, Row>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<Integer, Row> call(Row row) throws Exception {
				return new Tuple2<Integer, Row>((int)row.getAs("user_id"),row);
			}
		});
		JavaPairRDD<Integer, Tuple2<Row, Row>> pairRDD = userRDD.join(scoreRDD);
		
		JavaPairRDD<Integer,Tuple2<Row,Row>> filter = pairRDD.filter(new Function<Tuple2<Integer,Tuple2<Row,Row>>, Boolean>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean call(Tuple2<Integer, Tuple2<Row, Row>> v1) throws Exception {
				return (int)v1._2._2.getAs("score")>85;
			}
		});
		JavaRDD<Row> result = filter.map(new Function<Tuple2<Integer,Tuple2<Row,Row>>, Row>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Row call(Tuple2<Integer, Tuple2<Row, Row>> v1) throws Exception {
				String name = v1._2._1.getAs("name");
				Integer age = v1._2._1.getAs("age");
				Integer score = v1._2._2.getAs("score");
				String clazz = v1._2._2.getAs("name");
				return RowFactory.create(name,age,clazz,score);
			}
		});
		result.saveAsTextFile("score");
	}
}
