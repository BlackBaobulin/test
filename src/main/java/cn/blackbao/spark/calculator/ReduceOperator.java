package cn.blackbao.spark.calculator;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class ReduceOperator {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday","tuesday","wednesday","thursday","friday","saturday","sunday","monday","tuesday","wednesday","thursday","friday","saturday","sunday");
		JavaRDD<String> javaRDD = sc.parallelize(numbers);
		/***
		 * reduce 对应集合是直接累计操作，如果是对于元组，则是每次的第二个参数是上次的计算结果，第三个是这次新数据
		 */
		JavaPairRDD<String, Integer> pairRDD = javaRDD.mapToPair(new PairFunction<String, String, Integer>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				return new Tuple2<String, Integer>(t, 1);
			}
		});
		Tuple2<String, Integer> result = pairRDD.reduce(new Function2<Tuple2<String,Integer>, Tuple2<String,Integer>, Tuple2<String,Integer>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<String, Integer> call(Tuple2<String, Integer> v1, Tuple2<String, Integer> v2) throws Exception {
				return new Tuple2<String, Integer>(v1._1+v2._1,v1._2+v2._2);
			}
		});
		System.out.println(result);
		sc.close();
	}
}
