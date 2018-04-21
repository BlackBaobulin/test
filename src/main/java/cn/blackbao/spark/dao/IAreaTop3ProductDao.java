package cn.blackbao.spark.dao;

import java.util.List;

import cn.blackbao.spark.entity.AreaTop3Product;

/**
 * 各区域top3热门商品DAO接口
 * @author Administrator
 *
 */
public interface IAreaTop3ProductDao extends IDao {

	void insertBatch(List<AreaTop3Product> areaTopsProducts);
	
}
