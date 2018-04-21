package cn.blackbao.spark.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;

public class MapPartitionWithIndexOperator {


	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local[2]");
		conf.set("spark.default.parallelism", "3");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday","tuesday","wednesday","thursday","friday","saturday","sunday");
		JavaRDD<String> javaRDD = sc.parallelize(numbers,5);
		final Map<String, Integer> map = new HashMap<>();
		map.put("monday", 1000);
		map.put("tuesday", 1500);
		map.put("wednesday", 1200);
		map.put("thursday", 1000);
		map.put("friday", 2000);
		map.put("saturday", 1300);
		map.put("sunday", 2300);
		
		JavaRDD<String> result = javaRDD.mapPartitionsWithIndex(new Function2<Integer, Iterator<String>, Iterator<String>>(	) {
			private static final long serialVersionUID = 1L;
			@Override
			public Iterator<String> call(Integer key, Iterator<String> iterator) throws Exception {
					List<String> list = new ArrayList<>();
				while (iterator.hasNext()) {
					String next = iterator.next();
					list.add(key+"-----------"+next);
				}	
				return list.iterator();
				
			}
		}, true);
		result.foreach(new VoidFunction<String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(String num) throws Exception {
				System.out.println(num);
			}
		});
		sc.close();
	}
}
