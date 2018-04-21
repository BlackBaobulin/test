package cn.blackbao.spark.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.blackbao.spark.dao.ITaskDao;
import cn.blackbao.spark.entity.Task;

/**
 * 任务管理DAO实现类
 * @author Administrator
 *
 */
@Component("taskDao")
public class TaskDaoImpl implements ITaskDao {

	/**
	 * 根据主键查询任务
	 * @param taskid 主键
	 * @return 任务
	 */
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public Task findById(long taskid) {
		String sql = "select * from task where taskId=?";
		List<Task> tasks = null;
		try {
			tasks = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Task.class), new Object[]{taskid});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		if(tasks==null||tasks.isEmpty()) {
			return null;
		}else {
			return tasks.get(0);
		}
	}
	
}
