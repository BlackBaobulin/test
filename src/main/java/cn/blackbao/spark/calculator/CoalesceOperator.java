package cn.blackbao.spark.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

public class CoalesceOperator {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local[2]");
		conf.set("spark.default.parallelism", "3");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday","tuesday","wednesday","thursday","friday","saturday","sunday");
		JavaRDD<String> javaRDD = sc.parallelize(numbers,5);
		/***
		 * coalesce算子通过shuffle方式，将不同partition上的数据进行重新切分，可以解决数据倾斜问题（减少partition）。但是此方法的第二个参数如果不传，或者传入false,则不会进行网络shuffle,
		 * 只会在单机上不同partition之间执行此操作，如果为true，则会在整个集群执行shuffle操作
		 */

		JavaRDD<String> result = javaRDD.mapPartitionsWithIndex(new Function2<Integer, Iterator<String>, Iterator<String>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Iterator<String> call(Integer id, Iterator<String> split) throws Exception {
				List<String> list = new ArrayList<>();
				while (split.hasNext()) {
					String day = (String) split.next();
					list.add(id+"------"+day);
				}
				return list.iterator();
			}
		},true);
		
		List<String> collect = result.collect();
		for (String string : collect) {
			System.out.println(string);
		}
		JavaRDD<String> coalesce = result.coalesce(7);
		JavaRDD<String> result1 = coalesce.mapPartitionsWithIndex(new Function2<Integer, Iterator<String>, Iterator<String>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Iterator<String> call(Integer id, Iterator<String> split) throws Exception {
				List<String> list = new ArrayList<>();
				while (split.hasNext()) {
					String day = (String) split.next();
					list.add(id+"》》》》》》》》》》》"+day);
				}
				return list.iterator();
			}
		},true);
		List<String> r = result1.collect();
		for (String string : r) {
			System.err.println(string);
		}
	}
}
