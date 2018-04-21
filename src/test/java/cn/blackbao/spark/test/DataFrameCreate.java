package cn.blackbao.spark.test;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public class DataFrameCreate {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("DataFrameCreate").setMaster("local");
		SparkSession sparkSession = SparkSession.builder().config(conf).getOrCreate();
		//通过读取json,parquet,text.等格式的文本
//		Dataset<Row> df = sparkSession.read().json("word.json");
//		Dataset<Row> df1 = sparkSession.read().parquet("");
		//以什么方式写入的某个类型的文件中
//		df.write().mode(SaveMode.Append).format("parquet").save("word.parquet");
	}
}
