package com.examples.redis.script;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

@Service
public class ScriptService {
	
	
	@SuppressWarnings("rawtypes")
	private RedisScript<List> script;
	
	private Logger LOG = LoggerFactory.getLogger(ScriptService.class);
	
	@PostConstruct
	private void init() {
		@SuppressWarnings("rawtypes")
		DefaultRedisScript<List> redisScript = new DefaultRedisScript<List>();
    	redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/study.lua")));
    	redisScript.setResultType(List.class);
    	LOG.info("init redis script");
    	script = redisScript;
	}
	
	@Resource(name="stringRedisTemplate")
	private StringRedisTemplate strRedisTempl;
	
	
	public List<?> callScript() {
		List<String> keys = new ArrayList<>();
		keys.add("str:1");
		keys.add("str:2");
		
		//List<String> args = new ArrayList<>();
		//args.add(String.valueOf(System.currentTimeMillis()));
		
		List<?> result =  strRedisTempl.execute(script, keys, String.valueOf(System.currentTimeMillis()));
		
		LOG.info("call script return is : {}", result );
				
		return result;
	}
}
