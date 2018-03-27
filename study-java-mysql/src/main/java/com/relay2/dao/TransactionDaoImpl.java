package com.relay2.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionDaoImpl {
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void testQuery(){
		String sql =  "SELECT @@server_uuid";
		String uuid = jdbcTemplate.queryForObject(sql, String.class);
		System.out.println("uuid="+uuid);
	}
	
}
