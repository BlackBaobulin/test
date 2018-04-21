package cn.blackbao.spark.dao.impl;

import org.springframework.stereotype.Component;

import cn.blackbao.spark.dao.IPageSplitConvertRateDao;
import cn.blackbao.spark.entity.PageSplitConvertRate;
import cn.blackbao.spark.jdbc.JDBCHelper;

/**
 * 页面切片转化率DAO实现类
 * @author Administrator
 *
 */
@Component("pageSplitConvertRateDao")
public class PageSplitConvertRateDaoImpl implements IPageSplitConvertRateDao {

	@Override
	public void insert(PageSplitConvertRate pageSplitConvertRate) {
		String sql = "insert into page_split_convert_rate values(?,?)";  
		Object[] params = new Object[]{pageSplitConvertRate.getTaskid(), 
				pageSplitConvertRate.getConvertRate()};
		
		JDBCHelper jdbcHelper = JDBCHelper.getInstance();
		jdbcHelper.executeUpdate(sql, params);
	}

}
