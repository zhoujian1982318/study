package net.relay2.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author Ralf
 */
@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger("r2.common.redisconfig");
 
    private static final int RedisTimeout = 2000;
    
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {

        return initJedis();
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(jedisConnectionFactory());
    }

//    @Bean
//    public R2CacheFactory cacheFactory() {
//        return new R2CacheFactory(jedisConnectionFactory());
//    }
//
//    @Bean
//    public AccountTreeCache accountTreeCache() {
//        return cacheFactory().getAccountCache();
//    }
//    
//    @Bean
//    public HeartBeatStatusCache heartBeatStatusCache() {
//        return cacheFactory().getHeartBeatStatusCache();
//    }
//    
//    @Bean 
//    public WsQuotaCache wsQuotaCache() {
//    	return cacheFactory().getWsQuotaCache();
//    }

    private JedisConnectionFactory initJedis() {
        

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(Integer.parseInt("16"));
        poolConfig.setMinIdle(Integer.parseInt("8"));
        poolConfig.setMaxTotal(Integer.parseInt("32"));
        poolConfig.setMaxWaitMillis(Integer.parseInt("30000"));
        poolConfig.setNumTestsPerEvictionRun(Integer.parseInt("32"));
        poolConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt("30000"));
        poolConfig.setTestWhileIdle(true);

        JedisConnectionFactory jedisConnectionFactory = null;
//        
//        try {
//        	
//        		// Connect to Redis Sentinels.
//				List<RedisNode> redisNodeList = new ArrayList<RedisNode>(1);
//				redisNodeList.add(new RedisNode("10.10.20.36", 26379));
//    			if (redisNodeList != null) {
//    				RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master("r2master");
//    				sentinelConfig.setSentinels(redisNodeList);
//    				jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig, poolConfig); 
//        			jedisConnectionFactory.setTimeout(RedisTimeout);
//    				logger.info("Use Redis Sentinel configuration: mastername - {}, sentinels - {}.", sentinelConfig.getMaster(), 
//    						redisNodeList);
//    			}
//        	
//        } catch (Exception ex) {
//        	logger.error("Failed to init Jedis connection.", ex);
//        	jedisConnectionFactory = null;
//        }
        jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
		jedisConnectionFactory.setHostName("10.10.20.170");
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setTimeout(RedisTimeout);
		logger.info("Use Redis Server - [{}:{}]:{}.", new Object [] {"10.10.20.170", "6379", jedisConnectionFactory.getUsePool()});
        return jedisConnectionFactory;
        
    }

}
