package cn.blackbao.spark.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class RDD2DataFrameDynamic {

	
	/**
	 * 动态编程创建dataFrame
	 * @param args
	 */
	public static void main(String[] args) {
		SparkSession sparkSession = SparkSession.builder().appName("r3d").master("local").getOrCreate();//创建sparkSession
		JavaSparkContext sc = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());//根据sparkSession生成javaSparkContext
		
		//读取文本，生成rdd----->转成rdd对象---->转化成JavaRDD<Row>---->创建schema---->转化成DataFrame
		JavaRDD<String> file = sc.textFile("person.txt");
		JavaRDD<Row> rdd = file.map(new Function<String, Row>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Row call(String line) throws Exception {
				String[] split = line.split(",");
				return RowFactory.create(split[0],Integer.parseInt(split[1]),4);
			}
		});
		
		List<StructField> fields = new ArrayList<>();
		fields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
		fields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));
		StructType structType = DataTypes.createStructType(fields);

		Dataset<Row> dataFrame = sparkSession.createDataFrame(rdd, structType);
		dataFrame.createOrReplaceTempView("person");
		Dataset<Row> sql = sparkSession.sql("select * from person where age >85");
		List<Row> collect = sql.javaRDD().collect();
		for (Row row : collect) {
			String name = row.getAs("name");
			int age = row.getAs("age");
			System.out.println(name+":"+age);
		}
		
	}
}
