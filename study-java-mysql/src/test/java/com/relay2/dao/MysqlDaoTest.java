package com.relay2.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
public class MysqlDaoTest {
	private static Logger logger = LoggerFactory.getLogger(MysqlDaoTest.class);
	private AnnotationConfigApplicationContext ac ;
	private MysqlDaoImpl mysqlDao;
	
	
	
	@Before
	public void setUp(){
		ac = new AnnotationConfigApplicationContext("com.relay2");
		mysqlDao = ac.getBean("mysqlDao", MysqlDaoImpl.class);
	}
	
	//@Test
	public void testInsertList(){
		try {
			//sleep 5 second
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mysqlDao.insertLists();
	}
	
	//@Test
	public void testQuery(){
		try {
			//sleep 5 second
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mysqlDao.queryTest();
	}
	
	@Test(expected=UncategorizedSQLException.class)
	public void testSqlModeAnsi(){
		
		try{
			mysqlDao.testSqlModeAnsi();
		}catch(UncategorizedSQLException ex ){
			logger.debug("testSqlModeAnsi throw ex is ", ex);
			assertEquals(1111, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	
	@Test(expected=BadSqlGrammarException.class)
	public void testSqlModeOnlyFullGroupBy(){
		try{
			mysqlDao.testSqlModeOnlyFullGroupBy();
		}catch(BadSqlGrammarException ex ){
			logger.debug("testSqlModeOnlyFullGroupBy throw ex is ", ex);
			assertEquals(1055, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	
	@Test
	public void testSqlModeOnlyFullGroupByPK(){
		mysqlDao.testSqlModeOnlyFullGroupByPK();
	}
	
	@Test
	public void testSqlModeOnlyFullGroupByUniqueCol(){
		mysqlDao.testSqlModeOnlyFullGroupByUniqueCol();
	}
	
	@Test(expected=BadSqlGrammarException.class)
	public void testSqlModeOnlyFullGroupByUniqueColNull(){
		try{
			mysqlDao.testSqlModeOnlyFullGroupByUniqueColNull();
		}catch(BadSqlGrammarException ex ){
			logger.debug("testSqlModeOnlyFullGroupByUniqueColNull throw ex is ", ex);
			assertEquals(1055, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	
	@Test(expected=BadSqlGrammarException.class)
	public void testSqlModeOnlyFullGroupBySelectNoAggCol(){
		try{
			mysqlDao.testSqlModeOnlyFullGroupBySelectNoAggCol();
		}catch(BadSqlGrammarException ex ){
			logger.debug("testSqlModeOnlyFullGroupBySelectNoAggCol throw ex is ", ex);
			assertEquals(1055, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	
	@Test(expected=BadSqlGrammarException.class)
	public void testSqlModeOnlyFullGroupByOrderbyNoAggCol(){
		try{
			mysqlDao.testSqlModeOnlyFullGroupByOrderbyNoAggCol();
		}catch(BadSqlGrammarException ex ){
			logger.debug("testSqlModeOnlyFullGroupByOrderbyNoAggCol throw ex is ", ex);
			assertEquals(1055, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	
	@Test(expected=BadSqlGrammarException.class)
	public void testSqlModeOnlyFullGroupByWithoutGroupby(){
		try{
			mysqlDao.testSqlModeOnlyFullGroupByWithoutGroupby();
		}catch(BadSqlGrammarException ex ){
			logger.debug("testSqlModeOnlyFullGroupByWithoutGroupby throw ex is ", ex);
			assertEquals(1140, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	
	@Test(expected=UncategorizedSQLException.class)
	public void testSqlModeOnlyFullGroupByDistinctOrderby(){
		try{
			mysqlDao.testSqlModeOnlyFullGroupByDistinctOrderby();
		}catch(UncategorizedSQLException ex ){
			logger.debug("testSqlModeOnlyFullGroupByDistinctOrderby throw ex is ", ex);
			assertEquals(3065, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void testSqlModeStrictNoZeroDate(){
		try{
			mysqlDao.testSqlModeStrictNoZeroDate();
		}catch(DataIntegrityViolationException ex ){
			logger.debug("testSqlModeStrictNoZeroDate throw ex is ", ex);
			logger.debug("root cause is ", ex.getRootCause());
			//assertEquals(3065, );
			throw ex;
		}
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void testSqlModeStrictUnCorrectDate(){
		try{
			mysqlDao.testSqlModeStrictUnCorrectDate();
		}catch(DataIntegrityViolationException ex ){
			logger.debug("testSqlModeStrictUnCorrectDate throw ex is ", ex);
			logger.debug("root cause is ", ex.getRootCause());
			//assertEquals(3065, ex.getSQLException().getErrorCode());
			throw ex;
		}
	}
	@Test(expected=DataIntegrityViolationException.class)
	public void testSqlModeStrictDivisiobyZero(){
		try{
			mysqlDao.testSqlModeStrictDivisiobyZero();
		}catch(DataIntegrityViolationException ex ){
			logger.debug("testSqlModeStrictDivisiobyZero throw ex is ", ex);
			logger.debug("root cause is ", ex.getRootCause());
			throw ex;
		}
	}
	
	@Test
	public void testSqlModeNoAutoValueOnZero(){
		int rows = mysqlDao.testSqlModeNoAutoValueOnZero();
		assertEquals(1, rows);
	}
	
	
	
	
	
	public void tearDown(){
		ac.close();
	}
	
}
