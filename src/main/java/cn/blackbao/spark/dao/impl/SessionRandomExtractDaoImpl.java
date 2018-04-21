package cn.blackbao.spark.dao.impl;

import org.springframework.stereotype.Component;

import cn.blackbao.spark.dao.ISessionRandomExtractDao;
import cn.blackbao.spark.entity.SessionRandomExtract;
import cn.blackbao.spark.jdbc.JDBCHelper;

/**
 * 随机抽取session的DAO实现
 * @author Administrator
 *
 */
@Component("sessionRandomExtractDao")
public class SessionRandomExtractDaoImpl implements ISessionRandomExtractDao {

	/**
	 * 插入session随机抽取
	 * @param sessionAggrStat 
	 */
	public void insert(SessionRandomExtract sessionRandomExtract) {
		String sql = "insert into session_random_extract values(?,?,?,?,?)";
		
		Object[] params = new Object[]{sessionRandomExtract.getTaskid(),
				sessionRandomExtract.getSessionid(),
				sessionRandomExtract.getStartTime(),
				sessionRandomExtract.getSearchKeywords(),
				sessionRandomExtract.getClickCategoryIds()};
		
		JDBCHelper jdbcHelper = JDBCHelper.getInstance();
		jdbcHelper.executeUpdate(sql, params);
	}
	
}
