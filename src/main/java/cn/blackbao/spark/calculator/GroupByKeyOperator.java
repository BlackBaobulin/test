package cn.blackbao.spark.calculator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class GroupByKeyOperator {

	public static void main(String[] args) {
		/***
		 * 将相同的key分组，value合并，涉及到shuffle操作
		 */
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday","tuesday","wednesday","thursday","friday","saturday","sunday","monday","tuesday","wednesday","thursday","friday","saturday","sunday");
		JavaRDD<String> javaRDD = sc.parallelize(numbers);
		JavaPairRDD<String, String> pairRDD = javaRDD.mapToPair(new PairFunction<String, String, String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<String, String> call(String t) throws Exception {
				return new Tuple2<String, String>(t, t+"_"+new Random().nextInt(10));
			}
		});
		pairRDD.groupByKey().foreach(new VoidFunction<Tuple2<String,Iterable<String>>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(Tuple2<String, Iterable<String>> t) throws Exception {
				System.out.println(t._1+"--------"+t._2);
			}
		});
		sc.close();
	}
}
