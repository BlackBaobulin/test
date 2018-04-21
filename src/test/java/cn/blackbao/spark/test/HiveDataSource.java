package cn.blackbao.spark.test;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class HiveDataSource {
	public static void main(String[] args) {
		SparkSession sparkSession = SparkSession.builder().appName("HiveDataSourceTest").enableHiveSupport().getOrCreate();
		/**
		 * 1.使用sql插入数据，分割符，换行符指定是创建表格时创建的，需要注意转义字符的使用
		 * 2.使用overwrite，如果数据存在，则重新写入
		 * 3,本地文件的路径最好是绝对路径load data local inpath，或者hdfs路径(hdfs路径只要路径，不要ip，端口，如/user/root/score.txt')LOAD DATA  INPATH
		 * 4,数据导入表之后，或者再临时表中，直接通过sql即可筛选处理
		 * 5,spark standalone环境下
		 * 		client:./bin/spark-submit  --master spark://node1:7077 --deploy-mode client  --class cn.blackbao.spark.test.HiveDataSource  hdfs://node2:8020/user/root/wc.jar 
		 * 			或者./bin/spark-submit  --master spark://node1:7077 --deploy-mode client  --class cn.blackbao.spark.test.HiveDataSource  /opt/wc.jar 
		 * 
		 * 		cluster:./bin/spark-submit  --master spark://node1:7077 --deploy-mode cluster  --class cn.blackbao.spark.test.HiveDataSource  hdfs://node2:8020/user/root/wc.jar 
		 * 	
		 * 6,spark on yarn 环境下
		 * 
		 * 		client:./bin/spark-submit  --master yarn  --deploy-mode client  --class cn.blackbao.spark.test.HiveDataSource  ./wc.jar 
		 * 				yarn环境下，jar包，以及数据文件都需要程序自动提交到hdfs,人工上传无效
		 * 		cluster:一直未成功
		 * 				./bin/spark-submit  --master yarn  --deploy-mode cluster  --class cn.blackbao.spark.test.HiveDataSource  ./wc.jar 
		 * 
		 */
//		sparkSession.sql("drop table  if exists users");
		sparkSession.sql("create table if not exists users (name string,age int,id int) row format delimited fields terminated by '\\,' lines terminated by '\\n'");
		sparkSession.sql("LOAD DATA local INPATH  '/opt/spark-2.2.0-bin-hadoop2.7/user.txt' OVERWRITE INTO TABLE users ");
//		sparkSession.sql("select * from users").show();
		
//		sparkSession.sql("drop table  if exists score");
		sparkSession.sql("create table if not exists score (id int,score string,clazz string,user_id int) row format delimited fields terminated by '\\,' lines terminated by '\\n'");
		sparkSession.sql("LOAD DATA local  INPATH  '/opt/spark-2.2.0-bin-hadoop2.7/score.txt' OVERWRITE INTO TABLE score ");
//		sparkSession.sql("LOAD DATA  INPATH '/user/root/score.txt' OVERWRITE INTO TABLE score ");
//		sparkSession.sql("select * from score").show();
		Dataset<Row> sql = sparkSession.sql("select users.name,users.age,score.clazz,score.score from users join score on users.id=score.user_id where score.score>85");
//		sparkSession.sql("drop table  if exists result_info");
		
		sql.write().saveAsTable("result_info");
		Dataset<Row> table = sparkSession.table("result_info");
		Object collect = table.collect();
		System.err.println("____________________"+collect);
		
//		Dataset<Row> sql = sparkSession.sql("");
//		Dataset<Row> sql = sparkSession.sql("");
//		Dataset<Row> sql = sparkSession.sql("");
//		Dataset<Row> sql = sparkSession.sql("");
//		Dataset<Row> sql = sparkSession.sql("");
		
	}

}
