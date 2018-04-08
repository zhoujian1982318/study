package com.example.redis.script;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examples.redis.config.RedisConfig;
import com.examples.redis.script.ScriptService;


import redis.embedded.RedisServer;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class ScriptServiceTest {
	
	
	private static RedisServer server;
	
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private ScriptService scriptService;
	
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
	public void testCallScript() {
		stringRedisTemplate.opsForValue().set("str:1", "temp1");
		stringRedisTemplate.opsForValue().set("str:2", "temp2");
		List<?> result = scriptService.callScript();
		List<String> list= (List<String>)result.get(0);
		assertThat(list.get(1), is("temp1"));
		assertThat(list.get(2), is("temp2"));
		
	}

}
