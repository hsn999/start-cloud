package com.start.framework.redis.redis.util.standalone;

import com.start.framework.redis.util.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description : redis 缓存工厂类
 */
public class JedisFactory {

	// ridis 连接池
	private static JedisPool jedisPool;

	private static JedisFactory jedisFactory = new JedisFactory();

	private JedisFactory() {

	}

	public static JedisFactory getInstance() {
		if (jedisFactory == null) {
			jedisFactory = new JedisFactory();
		}
		return jedisFactory;
	}
 
	/**
	 * @Description : 初始化  非切片池(非集群环境)
	 * @return :void
	 */
	static {

		JedisPoolConfig config = new JedisPoolConfig();
		// 最大连接数
		config.setMaxTotal(3000);
		// 最大空闲连接数
		config.setMaxIdle(50);
		// 获取连接时的最大等待毫秒数（5分钟）
		config.setMaxWaitMillis(5 * 60 * 1000);
		// 在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(true);
		// 在空闲时检查有效性, 默认false
		config.setTestWhileIdle(true);
		
		//config.
		
		//RedisProperties redisProperties = new RedisProperties();

		//jedisPool = new JedisPool(config, redisProperties.getHost(), redisProperties.getPort());
		
		jedisPool = new JedisPool(config, RedisConfig.getHostName(), RedisConfig.getPort());
		
		//PooledObjectFactory<Jedis> factory = null;
		//GenericObjectPoolConfig poolConfig = null;
		//jedisPool.initPool(poolConfig, factory);
	}


	// 释放连接
	public static void returnResource(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}

	
	/**
	 * 删除指定 数据
	 */
	public void remove(String key) {
		getJedis().del(key);
	}

	/*public JedisAdpater getJedis() {
		return new JedisAdpater(jedisPool.getResource());
		//jedisPool.getResource();
	}*/
	
	public Jedis getJedis() {
		//return new JedisAdpater(jedisPool.getResource());
		return jedisPool.getResource();
	}

	public Object get(String key) {
		byte[] bytes = getJedis().get(key.getBytes());
		return SerializeUtil.unserialize(bytes);
	}
	
    /**
     * @param key
     * @param seconds void
     * @Des: 带期限存值
     */
	public void expire(String key, int seconds) {
		getJedis().expire(key, seconds);
	}

	public void set(String key, Object value) {
		getJedis().set(key.getBytes(), SerializeUtil.serialize(value));
	}

}
