package com.examples.redis.serializer;


import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examples.redis.config.RedisConfig;
import com.examples.redis.domain.User;

import junit.framework.Assert;
import redis.embedded.RedisServer;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class JsonSerializerTest{
	
	private static RedisServer server;
	
	@Autowired
	private RedisTemplate<String, User>  userTemplate;
	
	@Autowired
	private RedisTemplate<?, ?> redisTemplate;
	
//	@Autowired
//	private RedisOperations<K, V> operations;
	
	
	@BeforeClass
	public  static void startRedis() throws IOException {
		server = new RedisServer();
		server.start();
	}
	
	
	@Before
	@After
	public void setUp() {
		
		redisTemplate.execute(new RedisCallback<String>() {
	        public String doInRedis(RedisConnection connection) throws DataAccessException {
	        	connection.flushDb();
	        	return "OK";
	        }
	    });
	}
	
	
	@Test
	public void jsonTest() {
		User user = new User("jason", 20);
		userTemplate.opsForValue().set(user.getName(), user);
		Assert.assertEquals(20, userTemplate.opsForValue().get("jason").getAge());
	}
	
	
	@AfterClass
	public static void stopRedis() {
		server.stop();
	}

}
