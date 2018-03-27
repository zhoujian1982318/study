package com.relay2.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.fabric.jdbc.FabricMySQLDataSource;

@Configuration
@EnableTransactionManagement 
public class DaoConfig {
	private static Logger logger = LoggerFactory.getLogger(DaoConfig.class);
	
	//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	//@Bean(destroyMethod = "close")
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
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	//@Bean
	public DataSource fabricDataSource() {
		// DriverManagerDataSource driverMgmtDs = new DriverManagerDataSource();
		// driverMgmtDs.setDriverClassName("com.mysql.jdbc.Driver");
		// driverMgmtDs.setUrl("jdbc:mysql://10.10.20.170:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
		// driverMgmtDs.setUsername("root");
		// driverMgmtDs.setPassword("mysql");
		// return driverMgmtDs;
//		InitDataSource dbcp = new InitDataSource();
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
			
//			 dbcp.setDriverClassName("com.mysql.fabric.jdbc.FabricMySQLDriver");
//			 dbcp.setUrl("jdbc:mysql:fabric://10.22.1.8:32274/world_x?fabricServerGroup=my_group");
//			 dbcp.setUsername("admin");
//			 dbcp.setPassword("admin");
//			 dbcp.setInitialSize(5);
//			 
//			 dbcp.setMaxIdle(10);
//			 dbcp.setMinIdle(5);
//			 dbcp.setMaxTotal(5);
//			try {
//				dbcp.init();
//			} catch (SQLException e) {
//				logger.debug("init connection faield...");
//			}
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
		FabricMySQLDataSource ds = new FabricMySQLDataSource();
        ds.setServerName("10.22.1.8");
        ds.setPort(32274);
        ds.setDatabaseName("world_x");
        ds.setUser("root");
        ds.setPassword("mysql");
        ds.setFabricUsername("admin");
        ds.setFabricPassword("admin");
        //ds.setReadOnlyPropagatesToServer(true);
        //ds.setReadOnlyPropagatesToServer(true);
        // Load the driver if running under Java 5
        if (!com.mysql.jdbc.Util.isJdbc4()) {
            try {
				Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
        //ds.setDatabaseName("mysql"); // connect to the `mysql` database before creating our `employees` database
        ds.setFabricServerGroup("my_group"); // connect to the global group
		return ds;
	}
	
//	@Bean
	public JdbcTemplate jdbcTemplate() {
		DataSource ds = dataSource();
		return new JdbcTemplate(ds);
	}
	
	
	@Bean  
    public PlatformTransactionManager txManager() {  
        return new DataSourceTransactionManager(replicationDataSource());  
    }
	
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	//@Bean
	public DataSource replicationDataSource() {
		// DriverManagerDataSource driverMgmtDs = new DriverManagerDataSource();
		// driverMgmtDs.setDriverClassName("com.mysql.jdbc.Driver");
		// driverMgmtDs.setUrl("jdbc:mysql://10.10.20.170:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
		// driverMgmtDs.setUsername("root");
		// driverMgmtDs.setPassword("mysql");
		// return driverMgmtDs;
		BasicDataSource dbcp = new BasicDataSource();
		dbcp.setDriverClassName("com.mysql.jdbc.ReplicationDriver");
		//dbcp.setUrl("jdbc:mysql:replication://10.22.1.7:24801,10.22.1.7:24802,10.22.1.7:24803/test");
		dbcp.setUrl("jdbc:mysql:replication://10.22.1.7:24801,10.22.1.7:24802,10.22.1.7:24803/test");
		dbcp.setConnectionProperties("roundRobinLoadBalance=true");
		dbcp.setUsername("root");
		dbcp.setPassword("mysql");
		dbcp.setInitialSize(3);
		 
		dbcp.setMaxIdle(10);
		dbcp.setMinIdle(5);
		dbcp.setMaxTotal(5);
		return dbcp;
//		DriverManagerDataSource driverMgmtDs = new DriverManagerDataSource();
//		driverMgmtDs.setDriverClassName("com.mysql.jdbc.ReplicationDriver");
//		driverMgmtDs.setUrl("jdbc:mysql:replication://10.22.1.7:24801,10.22.1.7:24802,10.22.1.7:24803/test");
//		driverMgmtDs.setUsername("root");
//		driverMgmtDs.setPassword("mysql");
//		return driverMgmtDs;
		
	}
	
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Bean
	public DataSource readOnlyDataSource() {
		// DriverManagerDataSource driverMgmtDs = new DriverManagerDataSource();
		// driverMgmtDs.setDriverClassName("com.mysql.jdbc.Driver");
		// driverMgmtDs.setUrl("jdbc:mysql://10.10.20.170:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
		// driverMgmtDs.setUsername("root");
		// driverMgmtDs.setPassword("mysql");
		// return driverMgmtDs;
		 BasicDataSource dbcp = new BasicDataSource();
		 dbcp.setDriverClassName("com.mysql.jdbc.Driver");
		 dbcp.setUrl("jdbc:mysql://192.168.20.139:6447/test?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
		 dbcp.setUsername("root");
		 dbcp.setPassword("mysql");
		 dbcp.setInitialSize(5);
		 
		 dbcp.setMaxIdle(10);
		 dbcp.setMinIdle(5);
		 dbcp.setMaxTotal(5);
		
		 return dbcp;
	}
	
	@Bean
	public JdbcTemplate readonlyJdbcT(){
		try{
			DataSource ds = readOnlyDataSource();
			return new JdbcTemplate(ds);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	//@Bean
	public JdbcTemplate replicationJdbcT(){
		try{
			DataSource ds = replicationDataSource();
			return new JdbcTemplate(ds);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	

}
