package cn.blackbao.spark.dao;

import cn.blackbao.spark.entity.SessionRandomExtract;

/**
 * session随机抽取模块DAO接口
 * @author Administrator
 *
 */
public interface ISessionRandomExtractDao extends IDao {

	/**
	 * 插入session随机抽取
	 * @param sessionAggrStat 
	 */
	void insert(SessionRandomExtract sessionRandomExtract);
	
}
