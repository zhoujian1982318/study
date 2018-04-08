package com.examples.redis.transaction;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examples.redis.config.RedisConfig;

import redis.embedded.RedisServer;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class TransactionServiceTest {
	private static Logger LOG = LoggerFactory.getLogger(TransactionServiceTest.class);
	
	private static RedisServer server;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private TransactionService transactionService;
	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		server = new RedisServer();
//		server.start();
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		server.stop();
//	}
	
	
	@Before
	@After
	public void setUp() throws Exception {
		stringRedisTemplate.execute(new RedisCallback<String>() {
	        public String doInRedis(RedisConnection connection) throws DataAccessException {
	        	connection.flushDb();
	        	return "OK";
	        }
	    });
	}
	

	@Test
	public void testNotSupportRollback() {
		transactionService.notSupportRollback();
		ValueOperations<String, String> opsValue =  stringRedisTemplate.opsForValue();
		assertThat(opsValue.get("key:string"), is("test"));
		assertThat(opsValue.get("key:string:after:error"), is("still-exec"));
	}
	
	
	@Test
	public void testDiscardOpes() {
		transactionService.discardOper();
		ValueOperations<String, String> opsValue =  stringRedisTemplate.opsForValue();
		assertThat(opsValue.get("key:string"),is(nullValue()));
	}
	
	@Test
	public void testWatch() {
		
		String keyZset ="sort:lan";
		final BoundZSetOperations<String, String> zsetOpers =  stringRedisTemplate.boundZSetOps(keyZset);
		
		zsetOpers.add(".net",  1);
		zsetOpers.add("php",   2);
		zsetOpers.add("js",    3);
		zsetOpers.add("scala", 4);
		zsetOpers.add("java", 5);
		
		Thread t  = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<50; i++) {
					zsetOpers.add("android", 6);
				}
			}
		});
	
		t.start();
		String lowest = transactionService.watchOper(keyZset);
		
		//ValueOperations<String, String> opsValue =  stringRedisTemplate.opsForValue();
		assertThat(lowest,is(".net"));
		//wait t exit
		try {
			while(t.getState()!=Thread.State.TERMINATED) {
				LOG.info("the thread t not terminated, wait 1 second");
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
