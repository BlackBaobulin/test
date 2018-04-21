package cn.blackbao.spark.configration;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class JDBCConfig {

	@Autowired
	public Environment env;
	
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDbType(env.getProperty("spring.datasource.type"));
		dataSource.setName(env.getProperty("spring.datasource.poolName"));
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		dataSource.setInitialSize(env.getProperty("spring.datasource.initialSize",Integer.class));
		dataSource.setMinIdle(env.getProperty("spring.datasource.minIdle",Integer.class));
		dataSource.setMaxActive(env.getProperty("spring.datasource.maxActive",Integer.class));
		dataSource.setMaxWait(env.getProperty("spring.datasource.maxWait",Long.class));
		dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("spring.datasource.timeBetweenEvictionRunsMillis",Long.class));
		dataSource.setMinEvictableIdleTimeMillis(env.getProperty("spring.datasource.minEvictableIdleTimeMillis",Long.class));
		dataSource.setValidationQuery(env.getProperty("spring.datasource.validationQuery"));
		dataSource.setTestWhileIdle(env.getProperty("spring.datasource.testWhileIdle",Boolean.class));
		dataSource.setTestOnBorrow(env.getProperty("spring.datasource.testOnBorrow",Boolean.class));
		dataSource.setTestOnReturn(env.getProperty("spring.datasource.testOnReturn",Boolean.class));
		dataSource.setPoolPreparedStatements(env.getProperty("spring.datasource.poolPreparedStatements",Boolean.class));
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(env.getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize",Integer.class));
		try {
			dataSource.setFilters(env.getProperty("spring.datasource.filters"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataSource.setConnectionProperties(env.getProperty("spring.datasource.connectionProperties"));
		dataSource.setUseGlobalDataSourceStat(env.getProperty("spring.datasource.useGlobalDataSourceStat",Boolean.class));
		return dataSource;
	}
	@Bean
	public JdbcTemplate getJDBCTemplate(@Qualifier("dataSource") DataSource dataSource) {
	    return new JdbcTemplate(dataSource);
	}
    // 创建事物管理器  
    @Bean  
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {  
        return new DataSourceTransactionManager(dataSource);  
    }   
}
