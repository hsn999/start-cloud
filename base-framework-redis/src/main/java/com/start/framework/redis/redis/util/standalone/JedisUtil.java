package com.start.framework.redis.redis.util.standalone;

import java.util.Map;
import java.util.Set;

import com.start.framework.redis.redis.util.ICacheUtil;

import redis.clients.jedis.Jedis;

//@Service
public class JedisUtil<T> implements ICacheUtil{
	// jedis
	private static JedisFactory jedisFactory = JedisFactory.getInstance();
	@SuppressWarnings("rawtypes")
	private static JedisUtil<?> jedisUtil = new JedisUtil();

	/** set Object */
	public static void set(String key, Object object) {
		jedisFactory.getJedis().set(key.getBytes(), SerializeUtil.serialize(object));

	}
	
	/**
	 * @date 2017年1月14日上午11:11:58
	 * @param key
	 * @param object
	 * @param seconds void
	 * @Des:带过期时间的保存
	 */
	public static void setExpire(String key, Object object,int seconds) {
		jedisFactory.getJedis().set(key.getBytes(), SerializeUtil.serialize(object), "XX".getBytes(), "EX".getBytes(), seconds);
		
	}
	
	//队列应用
	/**
	 * 队列应用  2017-10-25 ，入列
	 * @param key 队列的名称，暂时定为point:order:queue
	 * @param map 订单
	 */
	@Override
	public void addToQueue(String key, Map<String,String> map) {
		Jedis jedis = jedisFactory.getJedis();
		jedis.lpush(key.getBytes(), SerializeUtil.serialize(map));
		JedisFactory.returnResource(jedis);

	}
	
	/**
	 * 队列应用  2017-10-25 ，取出
	 * @param key 队列的名称，暂时定为point:order:queue
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Map<String,String> getFromQueue(String key) {
		Jedis jedis = jedisFactory.getJedis();
		byte[]  queueItem = jedis.rpop(key.getBytes());
		JedisFactory.returnResource(jedis);
		return (Map<String, String>) SerializeUtil.unserialize(queueItem);

	}
	
	//jedis.hmset("kid", pairs);  
	public static void hmset(String key, Map<String,String> map) {
		jedisFactory.getJedis().hmset(key, map);

	}
	
	//hset(String key, String field, String value)
	@Override
	public void hset(String key, String field, String value) {
		//jedisFactory.getJedis().hset(key,field,value);
		Jedis jedis = jedisFactory.getJedis();
		jedis.hset(key,field,value);
		returnResource(jedis);

	}

	/** get Object */
	public static Object get(String key) {
		byte[] value = jedisFactory.getJedis().get(key.getBytes());
//		System.err.println("value.length:"+value.length);
//		for (int i = 0; i < value.length; i++) {
//			System.err.println("value"+i+":"+value[i]);
//			
//		}
		return SerializeUtil.unserialize(value);

	}

	/** delete a key **/
	public Boolean del(String key) {
		Long r = jedisFactory.getJedis().del(key.getBytes());
		if(r.equals(Long.valueOf("1")) ){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static JedisUtil getInstance() {
		if (jedisUtil == null) {
			jedisUtil = new JedisUtil();
		}
		return jedisUtil;
	}
	
	//Jedis
	public static Jedis getJedis() {
		return jedisFactory.getJedis();
	}
	//pool
	public static void returnResource(Jedis jedis) {
		JedisFactory.returnResource(jedis);;
	}
	
	@Override
	public String getStringByKey(String key){
		Jedis jedis = jedisFactory.getJedis();
		String value = jedis.get(key);
		returnResource(jedis);
		return value;
		
	}
	
	@Override
	public void setStringByKey(String key, String value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		jedis.set(key, value);
		returnResource(jedis);

	}

	

	@Override
	public Set<String> getSetByKey(String key) throws Exception {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		Set<String> value = jedis.smembers(key);
		returnResource(jedis);
		return value;
	}


	@Override
	public void setSetByKey(String key, String value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		jedis.sadd(key,value);
		returnResource(jedis);
	}
	

	@Override
	public Map<String,String> getMapByKey(String key) throws Exception {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		Map<String,String> map = jedis.hgetAll(key);
		returnResource(jedis);
		return map;
	}

	@Override
	public void setMapByKey(String key, Map<String, String> hash) throws Exception {
		Jedis jedis = jedisFactory.getJedis();
		jedis.hmset(key,hash);
		returnResource(jedis);
	}

	@Override
	public Boolean checkExists(String key) throws Exception {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		Boolean value = jedis.exists(key);
		returnResource(jedis);
		return value;
	}

	@Override
	public Set<String> getKeys(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		Set<String> setValues = jedis.keys(key);
		returnResource(jedis);
		return setValues;
		
	}

	@Override
	public Boolean checkListMember(String key, String value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		Boolean value1 = jedis.sismember(key, value);
		returnResource(jedis);
		return value1;
	}

	@Override
	public void removeFromSet(String key, String value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		jedis.srem(key,value);
		returnResource(jedis);
	}

	@Override
	public void setBytesByKey(String key, byte[] value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisFactory.getJedis();
		jedis.set(key.getBytes(),value);
		returnResource(jedis);
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
