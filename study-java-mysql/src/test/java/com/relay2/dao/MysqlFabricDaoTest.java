package com.relay2.dao;

import java.sql.SQLException;

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
public class MysqlFabricDaoTest {
	private static Logger logger = LoggerFactory.getLogger(MysqlFabricDaoTest.class);
	
	
	@ClassRule
	public static RequiresMysqlServer beforeRule = new RequiresMysqlServer("10.22.1.8:32274");
	
	@Resource(name="mysqlFabricDao")
	private MysqlFabricDaoImpl mysqlFabricDao;
	
	
	@Before
	public void setUp(){
		logger.info("enter the before");
	}
	
	
	@Test
	public void testFabricQuery(){
		
		try {
			String uuid = mysqlFabricDao.testFabricQuery(false);
			for(int i=0; i<5; i++){
				if(i/2==0){
					 mysqlFabricDao.testFabricQuery(true);
				}else{
					mysqlFabricDao.testFabricQuery(false);
				}
//				if(!uuid.equals(tmp)){
//					list.add(tmp);
//				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
