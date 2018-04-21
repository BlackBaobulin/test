package cn.blackbao.spark.dao;

import java.util.List;

import cn.blackbao.spark.entity.AdClickTrend;

/**
 * 广告点击趋势DAO接口
 * @author Administrator
 *
 */
public interface IAdClickTrendDao  extends IDao {

	void updateBatch(List<AdClickTrend> adClickTrends);
	
}
