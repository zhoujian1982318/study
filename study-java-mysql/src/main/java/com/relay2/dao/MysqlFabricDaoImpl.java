/**
 * 
 */
package com.relay2.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author jzhou
 *
 */
@Repository("mysqlFabricDao")
public class MysqlFabricDaoImpl {
	//private static Logger logger = LoggerFactory.getLogger(MysqlFabricDaoImpl.class);
	
	@Resource(name="fabricDataSource")
	private DataSource ds ;
	
	public String testFabricQuery(boolean readonly) throws SQLException{
		
		Connection rawConnection = ds.getConnection();
		rawConnection.setReadOnly(readonly);
		System.out.println("readOnly is :"+rawConnection.isReadOnly());
		Statement  statement = rawConnection.createStatement();
		//PreparedStatement ps = rawConnection.prepareStatement("SELECT @@server_uuid");
		String uuid = "";
		String sql =  "SELECT @@server_uuid";
		ResultSet rs = statement.executeQuery(sql);
		if(rs.next()){
			uuid = rs.getString(1);
			System.out.println("uuid="+uuid);
		}
		rs.close();
		sql  = "SELECT Name FROM city where ID=4091";
		rs = statement.executeQuery(sql);
		if(rs.next()){
			String name = rs.getString(1);
			System.out.println("name="+name);
		}
		rs.close();
		rawConnection.close();
		return uuid;
	}
}
