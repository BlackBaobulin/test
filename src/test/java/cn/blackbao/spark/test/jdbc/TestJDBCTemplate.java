package cn.blackbao.spark.test.jdbc;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.blackbao.spark.dao.TestJDBC;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestJDBCTemplate {
	
	@Autowired
	private TestJDBC testJDBC;
	
	@Test
	public void show() {
		long start = System.currentTimeMillis();
		List<Map<String,Object>> user = this.testJDBC.getUser();
		long end = System.currentTimeMillis();
		System.out.println("查询用时："+(end-start));
		System.out.println(user);
	}

}
