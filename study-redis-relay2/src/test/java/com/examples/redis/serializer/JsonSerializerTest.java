package com.examples.redis.serializer;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examples.redis.config.RedisConfig;
import com.examples.redis.domain.User;

import junit.framework.Assert;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class JsonSerializerTest {
	
	@Autowired
	private RedisTemplate<String, User>  userTemplate;
	
	@Test
	public void jsonTest() {
		User user = new User("jason", 20);
		userTemplate.opsForValue().set(user.getName(), user);
		Assert.assertEquals(20, userTemplate.opsForValue().get("jason").getAge());
	}

}
