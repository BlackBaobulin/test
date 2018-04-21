package cn.blackbao.spark.dao;

import cn.blackbao.spark.entity.Top10Category;

/**
 * top10品类DAO接口
 * @author Administrator
 *
 */
public interface ITop10CategoryDao extends IDao {

	void insert(Top10Category category);
	
}
