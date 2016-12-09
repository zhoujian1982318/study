package com.relay2.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MysqlDaoTest {
	private AnnotationConfigApplicationContext ac ;
	private MysqlDaoImpl mysqlDao;
	
	
	
	@Before
	public void setUp(){
		ac = new AnnotationConfigApplicationContext("com.relay2");
		mysqlDao = ac.getBean("mysqlDao", MysqlDaoImpl.class);
	}
	
	@Test
	public void testInsertList(){
		try {
			//sleep 5 second
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mysqlDao.insertLists();
	}
	
	public void tearDown(){
		ac.close();
	}
	
}
