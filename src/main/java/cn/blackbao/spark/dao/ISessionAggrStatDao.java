package cn.blackbao.spark.dao;

import cn.blackbao.spark.entity.SessionAggrStat;

/**
 * session聚合统计模块DAO接口
 * @author Administrator
 *
 */
public interface ISessionAggrStatDao extends IDao {

	/**
	 * 插入session聚合统计结果
	 * @param sessionAggrStat 
	 */
	void insert(SessionAggrStat sessionAggrStat);
	
}
