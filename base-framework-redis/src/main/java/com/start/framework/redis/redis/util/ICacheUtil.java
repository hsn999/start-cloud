package com.start.framework.redis.redis.util;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @author <a href="h_sn999@126.com">hsn</a>
 * @Discription
 * @Data 2017-9-12
 * @Version 1.0.0
 */

public interface ICacheUtil {

	String getStringByKey(String key);

	void setStringByKey(String key, String value);

	void setBytesByKey(String key, byte[] value);

	Set<String> getSetByKey(String key) throws Exception;

	void setSetByKey(String key, String value);

	void removeFromSet(String key, String value);

	Map<String, String> getMapByKey(String key) throws Exception;

	void setMapByKey(String key, Map<String, String> hash) throws Exception;

	Boolean checkExists(String key) throws Exception;

	void addToQueue(String key, Map<String, String> map);

	Map<String, String> getFromQueue(String key);

	/**
	 * 获取满足条件的全部key
	 */
	Set<String> getKeys(String key);

	Boolean checkListMember(String key, String value);

	void hset(String key, String field, String value);

	void del(String key);

	/**
	 * 尝试获取分布式锁	 *
	 * @param lockKey锁
	 * @param requestId请求标识
	 * @param expireTime超期时间
	 * @return 是否获取成功
	 */
	boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime);

	/**
	 * 重新获取分布式锁
	 *
	 * @param lockKey
	 * @param requestId
	 * @return
	 */
	boolean reGetDistributedLock(String lockKey, String requestId, int expireTime);

	/**
	 * 释放分布式锁
	 *
	 * @param lockKey锁
	 * @param requestId请求标识
	 * @return 是否释放成功
	 */
	boolean releaseDistributedLock(String lockKey, String requestId);
}
