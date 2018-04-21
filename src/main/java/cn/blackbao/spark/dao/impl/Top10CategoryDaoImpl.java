package cn.blackbao.spark.dao.impl;

import org.springframework.stereotype.Component;

import cn.blackbao.spark.dao.ITop10CategoryDao;
import cn.blackbao.spark.entity.Top10Category;
import cn.blackbao.spark.jdbc.JDBCHelper;

/**
 * top10品类DAO实现
 * @author Administrator
 *
 */

@Component("top10CategoryDao")
public class Top10CategoryDaoImpl implements ITop10CategoryDao {

	@Override
	public void insert(Top10Category category) {
		String sql = "insert into top10_category values(?,?,?,?,?)";  
		
		Object[] params = new Object[]{category.getTaskid(),
				category.getCategoryid(),
				category.getClickCount(),
				category.getOrderCount(),
				category.getPayCount()};  
		
		JDBCHelper jdbcHelper = JDBCHelper.getInstance();
		jdbcHelper.executeUpdate(sql, params);
	}

}
