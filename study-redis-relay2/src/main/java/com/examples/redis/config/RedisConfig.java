package com.examples.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.examples.redis.domain.User;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan(basePackages= {"com.examples.redis.pipeline","com.examples.redis.transaction", "com.examples.redis.script"})
public class RedisConfig {
	
 	@Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        return standaloneJedis();
    }
 	
 	
 	@Bean
	RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		return template;
	}
	
 	@Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
 	
 	@Bean
    public RedisTemplate<String, User> jacksonRedisTemplate(RedisConnectionFactory factory) {
 		RedisTemplate<String, User> template = new RedisTemplate<String, User>();
 		Jackson2JsonRedisSerializer<User> userSerializer = new Jackson2JsonRedisSerializer<>(User.class);
 		template.setConnectionFactory(factory);
 		template.setKeySerializer(new StringRedisSerializer());
 		template.setValueSerializer(userSerializer);
 		return template;
    }
 	
 	
	private JedisConnectionFactory standaloneJedis() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(8);
		config.setMaxIdle(8);
		config.setMinIdle(0);
		//config.setMaxWaitMillis(-1);
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(config);
		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(6379);
		jedisConFactory.setTimeout(2000);
		return jedisConFactory;
	}
	
}
