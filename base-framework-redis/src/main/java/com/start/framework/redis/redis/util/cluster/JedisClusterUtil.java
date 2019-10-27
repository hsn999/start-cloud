package com.start.framework.redis.redis.util.cluster;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.start.framework.redis.redis.util.ICacheUtil;
import com.start.framework.redis.redis.util.standalone.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;


public class JedisClusterUtil<T> implements ICacheUtil{

    private static final Logger LOGGER    = LoggerFactory.getLogger(JedisClusterUtil.class);    
  
    private  JedisCluster jedisCluster;
    
    JedisClusterConfig jedisClusterConfig = new JedisClusterConfig();
    
    static RedisOperator redisOperator= new RedisOperator();

     
	private static JedisClusterUtil jedisUtil = new JedisClusterUtil();
	
	public JedisClusterUtil(){
		this.jedisCluster = JedisClusterConfig.getJedisCluster();
	}
	

	/** set Object *//*
	public static void set(String key, Object object) {
		jedisCluster.set(key.getBytes(), SerializeUtil.serialize(object));

	}*/
	
	/**
	 * @date 2017年1月14日上午11:11:58
	 * @param key
	 * @param object
	 * @param seconds void
	 * @Des:带过期时间的保存
	 */
	/*public static void setExpire(String key, Object object,int seconds) {
		jedisCluster.set(key.getBytes(), SerializeUtil.serialize(object), "XX".getBytes(), "EX".getBytes(), seconds);
		
	}*/
	
	//队列应用
	/**
	 * 队列应用  2017-10-25 ，入列
	 * @param key 队列的名称，暂时定为point:order:queue
	 * @param map 订单
	 */
	@Override
	public void addToQueue(String key, Map<String,String> map) {
		jedisCluster.lpush(key.getBytes(), SerializeUtil.serialize(map)); 
		
		//jedisClusterConfig.getJedisCluster();

	} 
	
	/**
	 * 队列应用  2017-10-25 ，取出
	 * @param key 队列的名称，暂时定为point:order:queue
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Map<String,String> getFromQueue(String key) {
		byte[]  queueItem = jedisCluster.rpop(key.getBytes());
		return (Map<String, String>) SerializeUtil.unserialize(queueItem);

	}
	
	//jedis.hmset("kid", pairs);  
	public  void hmset(String key, Map<String,String> map) {
		jedisCluster.hmset(key, map);

	}
	
	//hset(String key, String field, String value)
	@Override
	public void hset(String key, String field, String value) {
		jedisCluster.hset(key,field,value);

	}

	/** get Object */
	public  Object get(String key) {
		byte[] value = jedisCluster.get(key.getBytes());
		return SerializeUtil.unserialize(value);

	}

	/** delete a key 
	 * @return **/
	public  Boolean del(String key) {
		Long r = jedisCluster.del(key.getBytes());
		if(r.equals(Long.valueOf("1")) ){
			return true;
		}
		return false;
	}
	
	public static JedisClusterUtil getInstance() {
		if (jedisUtil == null) {
			jedisUtil = new JedisClusterUtil();
		}
		return jedisUtil;
	}
	

	@Override
	public String getStringByKey(String key){
		String value = jedisCluster.get(key);
		return value;		
	}
	
	@Override
	public void setStringByKey(String key, String value) {
		jedisCluster.set(key, value);

	}

	

	@Override
	public Set getSetByKey(String key) throws Exception {
		Set<String> value = jedisCluster.smembers(key);
		return value;
	}


	@Override
	public void setSetByKey(String key, String value) {
		jedisCluster.sadd(key,value);
	}
	
	@Override
	public Map getMapByKey(String key) throws Exception {
		Map<String,String> map = jedisCluster.hgetAll(key);
		return map;
	}

	@Override
	public void setMapByKey(String key, Map<String, String> hash) throws Exception {
		jedisCluster.hmset(key,hash);
	}

	@Override
	public Boolean checkExists(String key) throws Exception {
		Boolean value = jedisCluster.exists(key);
		return value;
	}

	@Override
	public Set<String> getKeys(String key) {
		
		Set<String> setValues = redisOperator.keys(key);
		return setValues;
		
	}

	@Override
	public Boolean checkListMember(String key, String value) {
		Boolean value1 = jedisCluster.sismember(key, value);
		return value1;
	}

	@Override
	public void removeFromSet(String key, String value) {
		jedisCluster.srem(key,value);
	}

	@Override
	public void setBytesByKey(String key, byte[] value) {
		jedisCluster.set(key.getBytes(),value);
	}

	@Override
	public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reGetDistributedLock(String lockKey, String requestId, int expireTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseDistributedLock(String lockKey, String requestId) {
		// TODO Auto-generated method stub
		return false;
	}


}
