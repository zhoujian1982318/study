package com.relay2.config;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *  The pool is initialized the first time one of the
 *  following methods is invoked: <code>getConnection, setLogwriter,
 *  setLoginTimeout, getLoginTimeout, getLogWriter.
 *  如果你想连接池启动的时候， 创建连接的化，必须手工调用createDataSource 方法
 *
 */
public class InitDataSource extends BasicDataSource{
	
	public void init() throws SQLException{
		createDataSource();
	}
}
