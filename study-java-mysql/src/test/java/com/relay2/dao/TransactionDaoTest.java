package com.relay2.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransactionDaoTest {
	
	private ClassPathXmlApplicationContext ac ;
	private TransactionDaoImpl transactionImpl;
	private TransactionDaoImpl transactionImplProxy;
	@Before
	public void setUp(){
		ac = new ClassPathXmlApplicationContext("tx-context.xml", TransactionDaoTest.class);
		transactionImpl      =  ac.getBean("myTransactionImpl", TransactionDaoImpl.class);
		transactionImplProxy = ac.getBean("myTransactionProxy", TransactionDaoImpl.class);
		
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
