package cn.blackbao.spark.calculator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction;

public class FlatMapOperator {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local");
//		conf.set("spark.default.parallelism", "3");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday 星期一","tuesday 星期二","wednesday 星期三",
				"thursday 星期四","friday 星期五","saturday 星期六","sunday 星期日","sunday 星期日","sunday 星期日","sunday 星期日");
		JavaRDD<String> javaRDD = sc.parallelize(numbers);
		JavaRDD<String> flatMap = javaRDD.flatMap(new FlatMapFunction<String, String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Iterator<String> call(String t) throws Exception {
				return Arrays.asList(t.split(" ")).iterator();
			} 
		});
		flatMap.foreach(new VoidFunction<String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(String t) throws Exception {
				System.out.println(t);
			}
		});
		
		sc.close();
	}
}
