package cn.blackbao.spark.dao;

import java.util.List;

import cn.blackbao.spark.entity.AdStat;

/**
 * 广告实时统计DAO接口
 * @author Administrator
 *
 */
public interface IAdStatDao  extends IDao {

	void updateBatch(List<AdStat> adStats);
	
}
