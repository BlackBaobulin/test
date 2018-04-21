package cn.blackbao.spark.test;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class RDD2DataFrameReflection {

	/**
	 * 反射方式创建
	 * @param args
	 */
	public static void main(String[] args) {
		SparkSession sparkSession = SparkSession.builder().appName("r3d").master("local").getOrCreate();//创建sparkSession
		JavaSparkContext sc = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());//根据sparkSession生成javaSparkContext
		
		
		//读取文本，生成rdd----->转成rdd对象---->转化成DataFrame---->转化成JavaRDD<Row>---->转化成javaRDD<Person>---->数据最终再落地
		JavaRDD<String> file = sc.textFile("person.txt");
		JavaRDD<Person> rdd = file.map(new Function<String, Person>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Person call(String line) throws Exception {
				String[] split = line.split(",");
				Person person = new Person();
				person.setName(split[0]);
				person.setAge(Integer.parseInt(split[1]));
				return person;
			}
		});
		
		Dataset<Row> ds = sparkSession.createDataFrame(rdd, Person.class);//1.通过反射的方式创建dataFrame
		ds.createOrReplaceTempView("person");
		Dataset<Row> sql = sparkSession.sql("select * from person where age >85");
		
		JavaRDD<Row> javaRDD = sql.toJavaRDD();
		JavaRDD<Person> map = javaRDD.map(new Function<Row, Person>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Person call(Row row) throws Exception {
				String name = row.getAs("name");
				int age = row.getAs("age");
				Person person = new Person();
				person.setAge(age);
				person.setName(name);
				return person;
			}
		});
		map.foreach(new VoidFunction<Person>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(Person t) throws Exception {
				System.out.println(t);
			}
		});
		map.saveAsTextFile("person1.txt");
		sc.close();
	}
}
