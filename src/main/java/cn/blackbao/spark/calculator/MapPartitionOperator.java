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
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class MapPartitionOperator {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday","tuesday","wednesday","thursday","friday","saturday","sunday");
		JavaRDD<String> javaRDD = sc.parallelize(numbers);
		final Map<String, Integer> map = new HashMap<>();
		map.put("monday", 1000);
		map.put("tuesday", 1500);
		map.put("wednesday", 1200);
		map.put("thursday", 1000);
		map.put("friday", 2000);
		map.put("saturday", 1300);
		map.put("sunday", 2300);
		
		JavaRDD<Tuple2> result = javaRDD.mapPartitions(new FlatMapFunction<Iterator<String>, Tuple2>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Iterator<Tuple2> call(Iterator<String> iterator) throws Exception {
				List<Tuple2> list = new ArrayList<>();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Integer integer = map.get(key);
					list.add(new Tuple2<String, Integer>(key, integer));
				}
				return list.iterator();
			}
		});
		result.foreach(new VoidFunction<Tuple2>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(Tuple2 num) throws Exception {
				System.out.println(num._1+"--------"+num._2);
			}
		});
		
		
		sc.close();
	}
}
