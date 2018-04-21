package cn.blackbao.spark.calculator;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

public class MapOperator{

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("lineCount").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);
		JavaRDD<Integer> javaRDD = sc.parallelize(numbers);
		JavaRDD<Integer> reuslt = javaRDD.map(new Function<Integer, Integer>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Integer call(Integer number) throws Exception {
				return number*10;
			}
		});
		reuslt.foreach(new VoidFunction<Integer>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(Integer result) throws Exception {
				System.out.println(result);
			}
		});
		sc.close();
	}	
}
