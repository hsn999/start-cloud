package com.start.framework.redis.redis.util.cluster;

import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

//@Component
public class RedisOperator implements IRedisOperator {

	public final static Logger logger = LoggerFactory.getLogger(RedisOperator.class);

	@Autowired
	private JedisCluster jedisCluster;
 
	@Override
	public TreeSet<String> keys(String pattern){
		logger.debug("Start getting keys...");
		TreeSet<String> keys = new TreeSet<>();
		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
		for(String k : clusterNodes.keySet()){
			logger.debug("Getting keys from: {}", k);
			JedisPool jp = clusterNodes.get(k);
			Jedis connection = jp.getResource();
			try {
				keys.addAll(connection.keys(pattern)); 
			} catch(Exception e){
				logger.error("Getting keys error: {}", e);
			} finally{
				logger.debug("Connection closed.");
				connection.close();//用完一定要close这个链接！！！
			}
		}
		logger.debug("Keys gotten!");
		return keys;
	} 
}