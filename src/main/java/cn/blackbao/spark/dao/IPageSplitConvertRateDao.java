package cn.blackbao.spark.dao;

import cn.blackbao.spark.entity.PageSplitConvertRate;

/**
 * 页面切片转换率DAO接口
 * @author Administrator
 *
 */
public interface IPageSplitConvertRateDao extends IDao {

	void insert(PageSplitConvertRate pageSplitConvertRate);
	
}
