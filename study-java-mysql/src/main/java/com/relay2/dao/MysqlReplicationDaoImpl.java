/**
 * 
 */
package com.relay2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jzhou
 *
 */
@Repository("mysqlReplicationDao")
public class MysqlReplicationDaoImpl {
	private static Logger logger = LoggerFactory.getLogger(MysqlReplicationDaoImpl.class);
	
	@Resource(name="replicationJdbcT")
	private JdbcTemplate jdbcT ;
	
//	@Resource(name="readonlyJdbcT")
//	private JdbcTemplate roJdbcT ;
	
	
//	public String testQuery(boolean readonly) throws SQLException{
////	  try {
////			Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
////		} catch (ClassNotFoundException e) {
////			e.printStackTrace();
////		}
//	   Properties  props = new Properties();
//
//	    // We want this for failover on the slaves
//	    //props.put("autoReconnect", "true");
//
//	    // We want to load balance between the slaves
//	    props.put("roundRobinLoadBalance", "true");
//
//	    props.put("user", "root");
//	    props.put("password", "mysql");
//	  	String url = "jdbc:mysql:replication://10.22.1.7:24801,10.22.1.7:24802,10.22.1.7:24803/test";
////	  	Connection rawConnection =  DriverManager.getConnection(url , props) ;  
//////		Connection rawConnection = jdbcT.getDataSource().getConnection();
//		rawConnection.setReadOnly(readonly);
////		System.out.println("readOnly is :"+rawConnection.isReadOnly());
////		Statement  statement = rawConnection.createStatement();
////		//PreparedStatement ps = rawConnection.prepareStatement("SELECT @@server_uuid");
////		String uuid = "";
////		String sql =  "SELECT @@server_uuid";
////		ResultSet rs = statement.executeQuery(sql);
////		if(rs.next()){
////			uuid = rs.getString(1);
////			System.out.println("uuid="+uuid);
////		}
////		rs.close();
////		sql  = "SELECT c2 FROM t1 where c1=1";
////		rs = statement.executeQuery(sql);
////		if(rs.next()){
////			String c2 = rs.getString(1);
////			System.out.println("c2="+c2);
////		}
////		rs.close();
////		rawConnection.close();
////		return uuid;
////	}
//	@Transactional(rollbackFor = Exception.class, readOnly=true)
//	public String testQuery() throws SQLException{
//		String sql =  "SELECT @@server_uuid";
//		String uuid = jdbcT.queryForObject(sql, String.class);
//		System.out.println("uuid="+uuid);
//		sql  = "SELECT c2 FROM t1 where c1=1";
//		String c2 = jdbcT.queryForObject(sql, String.class);
//		System.out.println("c2="+c2);
//		return uuid;
//	}
	
//	
	/**
	 * retriesAllDown 需要设置该属性，默认重试 120 次
	 * jdbc:mysql:replication://[master-host]:[port],[slave-host]:[port],.../database?[property=<value>]  
	 * 在replication模式下，默认slaves的负载均衡的策略与LoadBalance协议保持一致, 而且由Connection.getReadOnly()值决定；
	 * 如果read_only为true，Replication Connection将使用“随机”模式选择一个slave链接, 如果所有的slaves都失效，此时将使用master链接（由readFromMasterNoSlaves参数控制）
	 * @return
	 * @throws SQLException
	 */
	@Transactional(rollbackFor = Exception.class, readOnly=true)
	public String testQuery() throws SQLException{
		String sql =  "SELECT @@server_uuid";
		String uuid = jdbcT.queryForObject(sql, String.class);
		logger.info("mysql uuid is {} ", uuid );
		sql  = "SELECT apMacAddr FROM AccessPoint where apMacAddr='B4:82:C5:00:05:38'";
		String apMac = jdbcT.queryForObject(sql, String.class);
		logger.info("the apMac  is {} ", apMac );
		return uuid;
	}
	
}
