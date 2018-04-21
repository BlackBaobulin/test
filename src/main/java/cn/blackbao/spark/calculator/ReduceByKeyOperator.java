package cn.blackbao.spark.calculator;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class ReduceByKeyOperator {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday","tuesday","wednesday","thursday","friday","saturday","sunday","monday","tuesday","wednesday","thursday","friday","saturday","sunday");
		JavaRDD<String> javaRDD = sc.parallelize(numbers);
		JavaPairRDD<String, Integer> pairRDD = javaRDD.mapToPair(new PairFunction<String, String, Integer>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				return new Tuple2<String, Integer>(t, 1);
			}
		});
		JavaPairRDD<String,Integer> reduceByKey = pairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
		});
		reduceByKey.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(Tuple2<String, Integer> t) throws Exception {
				System.out.println(t);
			}
		});
		
		
		sc.close();
	}
}
