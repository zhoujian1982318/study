package com.relay2.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.fabric.jdbc.FabricMySQLDataSource;
import com.relay2.dao.MysqlDaoImpl;
import com.relay2.dao.MysqlFabricDaoImpl;
import com.relay2.dao.MysqlReplicationDaoImpl;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.relay2.dao",useDefaultFilters=false,
	includeFilters={@Filter(type=FilterType.ASSIGNABLE_TYPE,value= {MysqlReplicationDaoImpl.class, MysqlDaoImpl.class, MysqlFabricDaoImpl.class})})
public class DaoConfig {
	
	//private static Logger logger = LoggerFactory.getLogger(DaoConfig.class);
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		
//		ComboPooledDataSource c3pool = new ComboPooledDataSource();
//		c3pool.setDriverClass("com.mysql.jdbc.Driver");
//		c3pool.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
//		c3pool.setUser("root");
//		c3pool.setPassword("mysql");
//		c3pool.setAcquireIncrement(3);
//		c3pool.setInitialPoolSize(3);
//		c3pool.setMinPoolSize(3);
//		c3pool.setMaxPoolSize(10);
//		c3pool.setMaxIdleTime(60);
		 InitDataSource dbcp = new InitDataSource();
		 dbcp.setDriverClassName("com.mysql.jdbc.Driver");
		 dbcp.setUrl("jdbc:mysql://10.10.20.170:3306/r2db?useDynamicCharsetInfo=false&useUnicode=true&characterEncoding=UTF-8");
		 dbcp.setUsername("root");
		 dbcp.setPassword("mysql");
		 dbcp.setInitialSize(5);
		 
		 dbcp.setMaxIdle(10);
		 dbcp.setMinIdle(5);
		 dbcp.setMaxTotal(5);
//       if dbcp don't  init.  dchp don't connect to mysql host
//		 try {
//			dbcp.init();
//		 } catch (SQLException e) {
//			logger.error("init connection faield...");
//		 }
		 return dbcp;
	}
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Bean
	public DataSource fabricDataSource() {
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
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		DataSource ds = dataSource();
		return new JdbcTemplate(ds);
	}
	
	
	@Bean  
    public PlatformTransactionManager txManager() {  
        return new DataSourceTransactionManager(replicationDataSource());  
    }
	
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Bean
	public DataSource replicationDataSource() {
		BasicDataSource dbcp = new BasicDataSource();
		dbcp.setDriverClassName("com.mysql.jdbc.Driver");
		//dbcp.setUrl("jdbc:mysql:replication://10.22.1.26:3306,192.168.20.143:3306/r2db");
		 dbcp.setUrl("jdbc:mysql:replication://10.22.1.26:3306,192.168.20.143:3306/r2db");
		//dbcp.setConnectionProperties("roundRobinLoadBalance=true");
		dbcp.addConnectionProperty("retriesAllDown", "5");
		//表示当所有的slaves都失效（异常）时，是否允许将read请求转发给master, 默认是false， readFromMasterWhenNoSlaves 这个属性似乎不起作用
		dbcp.addConnectionProperty("readFromMasterWhenNoSlaves","true");
		dbcp.addConnectionProperty("allowSlavesDownConnections", "true");
		dbcp.setUsername("root");
		dbcp.setPassword("mysql");
		dbcp.setInitialSize(3);
		 
		dbcp.setMaxIdle(10);
		dbcp.setMinIdle(5);
		dbcp.setMaxTotal(5);
		return dbcp;
		
		//readFromMasterWhenNoSlaves 好像没有效果
		//注意 jdbc:mysql:loadbalance写成 replication 的时候，  driver class name 不要写成 ReplicationDriver, 否则无效.
		//jdbc:mysql:loadbalance 表示用于多主的 结构。 根据策略挑选 host
//		DriverManagerDataSource driverMgmtDs = new DriverManagerDataSource();
//		driverMgmtDs.setDriverClassName("com.mysql.jdbc.Driver");
//		//jdbc:mysql:replication, 如果这样写 queriesBeforeRetryMaster=10
//		driverMgmtDs.setUrl("jdbc:mysql:loadbalance://10.22.1.26:3306,192.168.20.143:3306/r2db?retriesAllDown=5");
//		driverMgmtDs.setUsername("root");
//		driverMgmtDs.setPassword("mysql");
//		return driverMgmtDs;
//		
	}
	
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Bean
	public DataSource readOnlyDataSource() {
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
	
	
	@Bean
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
