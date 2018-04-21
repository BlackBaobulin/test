package cn.blackbao.spark.dao;

import java.util.List;

import cn.blackbao.spark.entity.AdProvinceTop3;

/**
 * 各省份top3热门广告DAO接口
 * @author Administrator
 *
 */
public interface IAdProvinceTop3Dao  extends IDao {

	void updateBatch(List<AdProvinceTop3> adProvinceTop3s);
	
}
