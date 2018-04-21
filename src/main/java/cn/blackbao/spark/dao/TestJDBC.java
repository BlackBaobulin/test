package cn.blackbao.spark.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("testJDBC")
public class TestJDBC {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getUser() {
		String sql ="select * from user limit 1";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
}
