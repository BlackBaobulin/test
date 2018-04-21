package cn.blackbao.spark.hive;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;

import scala.Function1;

public class UDFTest {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("UDF").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc.sc());
		sc.close();
		
	}
}
