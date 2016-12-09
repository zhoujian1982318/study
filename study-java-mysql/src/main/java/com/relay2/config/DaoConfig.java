package com.relay2.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DaoConfig {
	private static Logger logger = LoggerFactory.getLogger(DaoConfig.class);
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		// DriverManagerDataSource driverMgmtDs = new DriverManagerDataSource();
		// driverMgmtDs.setDriverClassName("com.mysql.jdbc.Driver");
		// driverMgmtDs.setUrl("jdbc:mysql://10.10.20.170:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
		// driverMgmtDs.setUsername("root");
		// driverMgmtDs.setPassword("mysql");
		// return driverMgmtDs;
		InitDataSource dbcp = new InitDataSource();
//		ComboPooledDataSource c3pool = new ComboPooledDataSource();
		// try {
//		try {
//			c3pool.setDriverClass("com.mysql.jdbc.Driver");
//
//			c3pool.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
//			c3pool.setUser("root");
//			c3pool.setPassword("mysql");
//			c3pool.setAcquireIncrement(3);
//			c3pool.setInitialPoolSize(3);
//			c3pool.setMinPoolSize(3);
//			c3pool.setMaxPoolSize(10);
//			c3pool.setMaxIdleTime(60);
	
			 dbcp.setDriverClassName("com.mysql.jdbc.Driver");
			 dbcp.setUrl("jdbc:mysql://10.10.20.170:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
			 dbcp.setUsername("root");
			 dbcp.setPassword("mysql");
			 dbcp.setInitialSize(5);
			 
			 dbcp.setMaxIdle(10);
			 dbcp.setMinIdle(5);
			 dbcp.setMaxTotal(5);
			try {
				dbcp.init();
			} catch (SQLException e) {
				logger.debug("init connection faield...");
			}
//			try {
//				c3pool.getConnection();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//		} catch (PropertyVetoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return dbcp;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		DataSource ds = dataSource();
		return new JdbcTemplate(ds);
	}

}
