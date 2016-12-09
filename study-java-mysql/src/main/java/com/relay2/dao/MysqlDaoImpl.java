package com.relay2.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

@Repository("mysqlDao")
public class MysqlDaoImpl {
	
	private static Logger logger = LoggerFactory.getLogger(MysqlDaoImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcT ;
	
	
	
	/**
	 *  INSERT  VALUES lists 速度测试..  1000 大约  650 ms, 10000条数据 大约 2712 ms (将近3秒钟)  
	 */
	public void insertLists(){
		StopWatch sw = new StopWatch();
		sw.start();
		String sql =  " INSERT INTO CsDwellTime(idNMSAccount,idClientStationMacAddr,isVisitor,firstSeen,lastSeen) "
			    + " VALUES";
		List<Object> batchArgs = new ArrayList<>();
		for(int i=0; i<10000; i++){
			
			long ts = System.currentTimeMillis();
			//Object[] args = new Object[]{18,"02:91:EA:ED:D2:C7", false, ts,ts};
			//batchArgs.add(args);
			//sql += "(18,'02:91:EA:ED:D2:C7',0,"+String.valueOf(ts)+","+String.valueOf(ts)+")";
			
			sql += "(?,?,?,?,?)";
			batchArgs.add(18);
			batchArgs.add("02:91:EA:ED:D2:C7");
			batchArgs.add(0);
			batchArgs.add(ts);
			batchArgs.add(ts);
			if(i!=9999){
				sql+=",";
			}
		}
		int count = jdbcT.update(sql,batchArgs.toArray());
		//jdbcT.batchUpdate(sql)
		logger.debug("the update count is {}",count);
		sw.stop();
		logger.debug("insert CsDwellTime table cost in millis: {} ms.", sw.getTotalTimeMillis());
	}
}
