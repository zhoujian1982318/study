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

import com.relay2.util.RequiresMysqlServer;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:tx-context.xml"})
public class TransactionDaoTest {
	
	private static Logger logger = LoggerFactory.getLogger(TransactionDaoTest.class);
	
	
	@ClassRule
	public static RequiresMysqlServer beforeRule = new RequiresMysqlServer("10.10.20.170:3306");
	
	
	@Resource(name="myTransactionImpl")
	private TransactionDaoImpl transactionImpl;
	
	@Resource(name="myTransactionProxy")
	private TransactionDaoImpl transactionImplProxy;
	@Before
	public void setUp(){
		logger.info("enter the before");
	}
	
	
	@Test
	public void testQuery(){
		transactionImpl.testQuery();
	}
	
	@Test
	public void testQueryTran(){
		transactionImplProxy.testQuery();
	}
}	
