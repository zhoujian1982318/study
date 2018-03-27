package com.relay2.dao;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.relay2.config.DaoConfig;

public class MysqlReplicationDaoTest {
	private static Logger logger = LoggerFactory.getLogger(MysqlFabricDaoTest.class);
	private AnnotationConfigApplicationContext ac ;
	private MysqlReplicationDaoImpl mysqlReplicationDao;
	
	
	
	@Before
	public void setUp(){
		ac = new AnnotationConfigApplicationContext(DaoConfig.class, MysqlReplicationDaoImpl.class );
		mysqlReplicationDao = ac.getBean("mysqlReplicationDao", MysqlReplicationDaoImpl.class);
	}
	
	@Test
	public void testQuery(){
		
		try {
			//for(int i=0; i<5; i++){
				mysqlReplicationDao.testQuery();
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
