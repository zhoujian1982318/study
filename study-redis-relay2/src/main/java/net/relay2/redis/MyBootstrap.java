/**
 * 
 */
package net.relay2.redis;

import java.util.HashSet;
import java.util.Set;

import net.relay2.redis.TestInClass.Main;
import net.relay2.redis.TestInClass.StaticMain;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @author jzhou
 *
 */
public class MyBootstrap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("redis-context.xml");
		//MyRedisDaoImpl redisDao =  ctx.getBean(MyRedisDaoImpl.class);
		//redisDao.setStringValue("redis:key", "redis");
		//int i = redisDao.appendStringValue("redis:key", "-append");
		//System.out.println("append integer:["+i+"]");
//		redisDao.pipeline();
//		JedisPoolConfig poolConfig= new JedisPoolConfig();
//		poolConfig.setMaxIdle(Integer.parseInt("16"));
//	    poolConfig.setMinIdle(Integer.parseInt("8"));
//	    poolConfig.setMaxTotal(Integer.parseInt("32"));
//	    poolConfig.setMaxWaitMillis(Integer.parseInt("30000"));
//	    poolConfig.setNumTestsPerEvictionRun(Integer.parseInt("32"));
//	    poolConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt("30000"));
//	    poolConfig.setTestWhileIdle(true);
//		JedisPool pool = new JedisPool(poolConfig, "10.10.20.170", 6379, 2000);
//		Jedis jedis = pool.getResource();
//		String test = jedis.hget("ws:quota:2097152:0", "maxQuota");
//		System.out.println(test);
//		jedis.close();
//		while(true){
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		//int keySlot = JedisClusterCRC16.getSlot("jian");
		//System.out.println("keySlot: " + keySlot);
		try{
		    Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		    jedisClusterNode.add(new HostAndPort("10.10.20.175", 7000));
		    JedisCluster jc = new JedisCluster(jedisClusterNode);
		    System.out.println(jc.getClusterNodes().size());
		    for(int i=0; i<99; i++){
		    	jc.set("a"+i, "test"+i);
		    }
		    //jc.set
		    System.out.println(jc.get("a10"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
//		while(true){
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		//TestInClass test = new TestInClass();
		//Main main = test.new Main();
		//StaticMain testIn = new StaticMain();
		
	}
	
	public void test(){
		
	}
}
