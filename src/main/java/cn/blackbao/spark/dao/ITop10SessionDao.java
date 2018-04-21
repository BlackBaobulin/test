package cn.blackbao.spark.dao;

import cn.blackbao.spark.entity.Top10Session;

/**
 * top10活跃session的DAO接口
 * @author Administrator
 *
 */
public interface ITop10SessionDao extends IDao {

	void insert(Top10Session top10Session);
	
}
