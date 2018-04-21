package cn.blackbao.spark.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

public class RepartitionOperator {
	public static void main(String[] args) {
		 /**
		  *  repartition算子通过shuffle方式，将不同partition上的数据进行重新切分，可以解决数据倾斜问题,或者提高任务并行度
		  * 
		  */
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local[2]");
		conf.set("spark.default.parallelism", "3");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> numbers = Arrays.asList("monday","tuesday","wednesday","thursday","friday","saturday","sunday");
		JavaRDD<String> javaRDD = sc.parallelize(numbers,5);
		JavaRDD<String> indexRDD = javaRDD.mapPartitionsWithIndex(new Function2<Integer, Iterator<String>, Iterator<String>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Iterator<String> call(Integer id, Iterator<String> iterator) throws Exception {
				List<String> list = new ArrayList<>();
				while (iterator.hasNext()) {
					String string = (String) iterator.next();
					list.add(id+"--------"+string);
				}
				return list.iterator();
			}
		}, true);
		JavaRDD<String> result = indexRDD.repartition(7);
		List<String> collect = result.mapPartitionsWithIndex(new Function2<Integer, Iterator<String>, Iterator<String>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Iterator<String> call(Integer id, Iterator<String> iterator) throws Exception {
				List<String> list = new ArrayList<>();
				while (iterator.hasNext()) {
					String string = (String) iterator.next();
					list.add(id+">>>>>>>>>>"+string);
				}
				return list.iterator();
			}
		}, true).collect();
		
		for (String string : collect) {
			System.out.println(string);
		}
		
		sc.close();
	}
}
