package com.relay2.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MysqlFabricDaoTest {
	private static Logger logger = LoggerFactory.getLogger(MysqlFabricDaoTest.class);
	private AnnotationConfigApplicationContext ac ;
	private MysqlFabricDaoImpl mysqlFabricDao;
	
	
	
	@Before
	public void setUp(){
		ac = new AnnotationConfigApplicationContext("com.relay2");
		mysqlFabricDao = ac.getBean("mysqlFabricDao", MysqlFabricDaoImpl.class);
	}
	
//	@Test
//	public void testInsertList(){
//		mysqlDao.testFabricInsert();
//	}
	
	@Test
	public void testFabricQuery(){
		
		try {
			String uuid = mysqlFabricDao.testFabricQuery(false);
//			System.out.println(uuid);
//			List<String> list = new ArrayList<String>();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println(list);
//		System.out.println(list.size());
	}
}
