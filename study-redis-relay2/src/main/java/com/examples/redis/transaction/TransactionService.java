package com.examples.redis.transaction;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
	
	private static Logger LOG = LoggerFactory.getLogger(TransactionService.class);
	
	@Resource(name="stringRedisTemplate")
	private StringRedisTemplate strRedisTempl;
	
	public boolean notSupportRollback() {
		
		
		boolean resp = strRedisTempl.execute(new SessionCallback<Boolean>() {

			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public  Boolean execute(RedisOperations operations) throws DataAccessException {
				// Start a Redis transaction
				operations.multi();
				try {
				
				ValueOperations<String, String> stringOper = operations.opsForValue();
				ListOperations<String, String>  listOper = operations.opsForList();
				stringOper.set("key:string", "test");
				String res = listOper.leftPop("key:string");
				LOG.info("the return is {} for  lpop command", res);
				stringOper.set("key:string:after:error", "still-exec");
				// Commit the transaction
				List<Object> result = operations.exec();
				LOG.info("the results of redis exec : {}", result);
				}catch(InvalidDataAccessApiUsageException ex) {
					LOG.warn("exec throw exception,  we can't  use discard method to rollback, the client need to process this issue by itself : {}", ex.getMessage());
					//operations.discard();
				}
				return true;
			}
			
		});
		
		return resp;
	}
	
	
	public  void errorBeforExec() {
		// the client lib can avoid this type of error
//		strRedisTempl.execute(new SessionCallback<Object>() {
//
//			@Override
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			public  Object execute(RedisOperations operations) throws DataAccessException {
//				// Start a Redis transaction
//				operations.multi();
//				ValueOperations<String, String> stringOper = operations.opsForValue();
//				ListOperations<String, String>  listOper = operations.opsForList();
//				stringOper.set("key:string", "test");
//				// Commit the transaction
//				List<Object> result = operations.exec();
//				LOG.info("the results of redis exec : {}", result);
//				return null;
//			}
//			
//		});
	}
	
	
	public void discardOper() {
		
		strRedisTempl.execute(new SessionCallback<Object>() {
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public  Object execute(RedisOperations operations) throws DataAccessException {
				// Start a Redis transaction
				operations.multi();
				//RedisOperations<String, String> opers = (RedisOperations<String, String>)operations;
				ValueOperations<String, String> stringOper = operations.opsForValue();
				ListOperations<String, String>  listOper = operations.opsForList();
				Long listSize = listOper.leftPushAll("key:list","list-number-1", "list-number-2");
				LOG.info("the return is {} for  lpush command", listSize);
				stringOper.set("key:string", "one-string");
				String res = listOper.leftPop("key:string");
				LOG.info("the return is {} for  lpop command", res);
				// discard the transaction
				operations.discard();
				//operations.exec();
				return null;
			}
			
		});
	}
	
	
	public String watchOper(final String keySortSet) {
		
		String lowestScore = strRedisTempl.execute(new SessionCallback<String>() {
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public  String execute(RedisOperations operations) throws DataAccessException {
				ZSetOperations<String, String> zsetOpers = operations.opsForZSet();
				operations.watch(keySortSet);
				Set<String> sets =  zsetOpers.range(keySortSet, 0, 0);
				operations.multi();
				String lowestScore = sets.toArray(new String[0])[0];
				zsetOpers.remove(keySortSet, lowestScore);
				List<Object> result = operations.exec();
				LOG.info("the results of redis exec : {}", result);
				if(result == null) {
					throw new RecoverableDataAccessException("the content of the watch key changed ");
				}
				return lowestScore;
			}
			
		});
		
		return lowestScore;
	}
}
