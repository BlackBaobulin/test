package cn.blackbao.spark.dao.impl;

import org.springframework.stereotype.Component;

import cn.blackbao.spark.dao.ITop10SessionDao;
import cn.blackbao.spark.entity.Top10Session;
import cn.blackbao.spark.jdbc.JDBCHelper;

/**
 * top10活跃session的DAO实现
 * @author Administrator
 *
 */
@Component("top10SessionDao")
public class Top10SessionDaoImpl implements ITop10SessionDao {

	@Override
	public void insert(Top10Session top10Session) {
		String sql = "insert into top10_session values(?,?,?,?)"; 
		
		Object[] params = new Object[]{top10Session.getTaskid(),
				top10Session.getCategoryid(),
				top10Session.getSessionid(),
				top10Session.getClickCount()};
		
		JDBCHelper jdbcHelper = JDBCHelper.getInstance();
		jdbcHelper.executeUpdate(sql, params);
	}

}
