package com.examples.redis.pipeline;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examples.redis.config.RedisConfig;

import junit.framework.Assert;
import redis.embedded.RedisServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class PipeLineServiceTest {
	
	private static RedisServer server;
	
	@Autowired
	private PipeLineService piplineService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		server = new RedisServer();
		server.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		server.stop();
	}

	@Before
	@After
	public void clear() throws Exception {
		
		stringRedisTemplate.execute(new RedisCallback<String>() {
	        public String doInRedis(RedisConnection connection) throws DataAccessException {
	        	connection.flushDb();
	        	return "OK";
	        }
	    });
	}


	@Test
	public void testPipeline() {
		List<Object> rst = piplineService.pipeline();
		Assert.assertEquals(3, rst.size());
		BoundValueOperations<String, String> boundStringOper = stringRedisTemplate.boundValueOps("redis:key");
		Assert.assertEquals("jian.zhou", boundStringOper.get()); 
	}

}
