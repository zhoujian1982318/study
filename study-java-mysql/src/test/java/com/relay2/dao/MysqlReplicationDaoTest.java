package com.relay2.dao;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.relay2.config.DaoConfig;
import com.relay2.util.RequiresMysqlServer;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfig.class)
public class MysqlReplicationDaoTest {
	
	private static Logger logger = LoggerFactory.getLogger(MysqlReplicationDaoTest.class);
	
	
	@ClassRule
	public static RequiresMysqlServer beforeRule = new RequiresMysqlServer("10.22.1.26:3306","192.168.20.143:3306");
	
	@Resource(name="mysqlReplicationDao")
	private MysqlReplicationDaoImpl mysqlReplicationDao;
	
	
	
	@Before
	public void setUp(){
		logger.info("enter the before");
	}
	
	@Test
	public void testQuery(){
		
		try {
			for(int i=0; i<10; i++){
				mysqlReplicationDao.testQuery();
			}
//			Thread.sleep(5*60*1000);
//			for(int i=0; i<10; i++){
//				mysqlReplicationDao.testQuery();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQuery2(){
		
		try {
			for(int i=0; i<10; i++){
				mysqlReplicationDao.testQuery();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
