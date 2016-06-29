package net.relay2.redis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;

public class MyRedisDaoImpl {
	
	@Resource(name = "redisTemplate")
	private StringRedisTemplate redisTemplate;
	
    //private final HashOperations<String, String, String> strHashOps; 
    //private final ValueOperations<String, String> strValueOps;
    
	public MyRedisDaoImpl() {
    	
    	//strHashOps = redisTemplate.opsForHash();
    	//strValueOps = redisTemplate.opsForValue();
    }
	
//	public void setStringValue(String key, String val){
//		strValueOps.set(key, val);
//	}
//	public Integer appendStringValue(String key, String appVal){
//		return strValueOps.append(key, appVal);
//	}
	
	
	public void pipeline(){
		 List<Object> rst = redisTemplate.executePipelined(new RedisCallback<Object>() {
			 
	         public Object doInRedis(RedisConnection connection) throws DataAccessException {
	             StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
	             stringRedisConn.set("redis:key", "jian.zhou");
	             stringRedisConn.setNX("redis:key", "value1");
	             stringRedisConn.setNX("redis:key3", "value1");
	             return null;
	         }
	     });
		 
		 System.out.println(rst.size()+ "records pipelined:"+ rst);
	}
}
