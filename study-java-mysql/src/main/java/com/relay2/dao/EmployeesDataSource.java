/**
 * 
 */
package com.relay2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.fabric.jdbc.FabricMySQLConnection;
import com.mysql.fabric.jdbc.FabricMySQLDataSource;

/**
 * @author jzhou
 *
 */
public class EmployeesDataSource {
	 public static void main(String args[]) throws Exception {
		 FabricMySQLDataSource ds = new FabricMySQLDataSource();
	        ds.setServerName("10.22.1.8");
	        ds.setPort(32274);
	        ds.setDatabaseName("employees");
	        ds.setFabricUsername("admin");
	        ds.setFabricPassword("admin");
	        ds.setUser("root");
	        ds.setPassword("mysql");
	        if (!com.mysql.jdbc.Util.isJdbc4()) {
	            Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
	        }
	        // 1. Create database and table for our demo
//	        ds.setDatabaseName("mysql"); // connect to the `mysql` database before creating our `employees` database
//	        ds.setFabricServerGroup("fabric_test1_global"); // connect to the global group
//	        Connection rawConnection = ds.getConnection();
//	        Statement statement = rawConnection.createStatement();
//	        statement.executeUpdate("create database if not exists employees");
//	        statement.close();
//	        rawConnection.close();
	        

	        // We should connect to the global group to run DDL statements, they will be replicated to the server groups for all shards.

	        // The 1-st way is to set its name explicitly via the "fabricServerGroup" datasource property
//	        ds.setFabricServerGroup("fabric_test1_global");
//	        Connection rawConnection = ds.getConnection();
//	        Statement statement = rawConnection.createStatement();
//	        statement.executeUpdate("create database if not exists employees");
//	        statement.close();
//	        rawConnection.close();
	        
//	        ds.setFabricServerGroup(null); // clear the setting in the datasource for previous connections
//	        ds.setFabricShardTable("employees.employees");
//	        Connection rawConnection = ds.getConnection();
//	        // At this point, we have a connection to the global group for  the 'employees.employees' shard mapping.
//	        Statement statement = rawConnection.createStatement();
//	        statement.executeUpdate("drop table if exists employees");
//	        statement.executeUpdate("create table employees (emp_no int not null, first_name varchar(50), last_name varchar(50), primary key (emp_no))");
//	        statement.close();
//	        rawConnection.close();
	        
	        
	        ds.setFabricServerGroup(null); // clear the setting in the datasource for previous connections
	        ds.setFabricShardTable("employees.employees");
	        Connection rawConnection = ds.getConnection();
	        // At this point, we have a connection to the global group for  the 'employees.employees' shard mapping.
	        FabricMySQLConnection connection = (FabricMySQLConnection) rawConnection;

	        // example data used to create employee records
	        Integer ids[] = new Integer[] { 1, 2, 10001, 10002 };
	        String firstNames[] = new String[] { "John", "Jane", "Andy", "Alice" };
	        String lastNames[] = new String[] { "Doe", "Doe", "Wiley", "Wein" };

	        // insert employee data
//	        PreparedStatement ps = connection.prepareStatement("INSERT INTO employees.employees VALUES (?,?,?)");
//	        for (int i = 0; i < 4; ++i) {
//	            // choose the shard that handles the data we interested in
//	            connection.setShardKey(ids[i].toString());
//
//	            // perform insert in standard fashion
//	            ps.setInt(1, ids[i]);
//	            ps.setString(2, firstNames[i]);
//	            ps.setString(3, lastNames[i]);
//	            ps.executeUpdate();
//	        }
	        
	     // 3. Query the data from employees
	        System.out.println("Querying employees");
	        System.out.format("%7s | %-30s | %-30s%n", "emp_no", "first_name", "last_name");
	        System.out.println("--------+--------------------------------+-------------------------------");
	        PreparedStatement ps = connection.prepareStatement("select emp_no, first_name, last_name from employees where emp_no = ?");
	        for (int i = 0; i < 4; ++i) {

	            // we need to specify the shard key before accessing the data
	            connection.setShardKey(ids[i].toString());

	            ps.setInt(1, ids[i]);
	            ResultSet rs = ps.executeQuery();
	            rs.next();
	            System.out.format("%7d | %-30s | %-30s%n", rs.getInt(1), rs.getString(2), rs.getString(3));
	            rs.close();
	        }
	        ps.close();
	 }
}
