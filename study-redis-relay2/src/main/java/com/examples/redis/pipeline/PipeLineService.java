package com.examples.redis.pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PipeLineService {
	
	private Logger LOG = LoggerFactory.getLogger(PipeLineService.class);
	
	@Resource(name="stringRedisTemplate")
	private StringRedisTemplate strRedisTempl;
	
	
	public List<Object> pipeline() {
		
		//set 操作不会返回Object,   get 操作的返回的object 会返回.
		List<Object> rst = strRedisTempl.executePipelined(new RedisCallback<Object>() {
			 
	         public Object doInRedis(RedisConnection connection) throws DataAccessException {
	             StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
	             stringRedisConn.set("redis:key", "jian.zhou");
	             stringRedisConn.get("redis:key");
	             Map<String,String> keyValues = new HashMap<>();
	             keyValues.put("redis:key:1", "value1");
	             keyValues.put("redis:key:2", "value2");
	             stringRedisConn.mSetString(keyValues);
	             stringRedisConn.mGet("redis:key:1", "redis:key:2");
	             stringRedisConn.set("redis:key:3", "temp");
	             stringRedisConn.del("redis:key:3");
	             //pipline 操作 必须返回 null ,  要不然会报错 InvalidDataAccessApiUsageException Callback cannot return a non-null value as it gets overwritten by the pipeline
	             return null;
	         }
	     });
		 LOG.info("the result of pipeline is {}", rst);
		 return rst;
		
	}
	
}
