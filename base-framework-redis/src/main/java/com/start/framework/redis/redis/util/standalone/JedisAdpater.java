package com.start.framework.redis.redis.util.standalone;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisAdpater {

	private Jedis jedis;

	public JedisAdpater(Jedis jedis) {
		this.jedis = jedis;
	}

	public void set(final byte[] key, final byte[] value) {
		if(value==null){
			return ;
		}
		try {
			jedis.set(key, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisFactory.returnResource(jedis);
		}
	}
	public void setExpire(final byte[] key, final byte[] value,int seconds) {
		if(value==null){
			return ;
		}
		try {
			String set = jedis.set(key, value);
			Long expire = jedis.expire(key, seconds);
			System.err.println("set:"+set+";expire:"+expire);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisFactory.returnResource(jedis);
		}
	}

	public byte[] get(final byte[] key) {
		try {
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return null;
	}


	public void del(final byte[] key) {
		try {
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisFactory.returnResource(jedis);
		}
	}
	
	public void del(String key) {
		try {
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisFactory.returnResource(jedis);
		}
	}

	public Set<String> hkeys(final String key) {
		try {
			return jedis.hkeys(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisFactory.returnResource(jedis);
		}
		return null;
	}

	public void expire(final String key, final int seconds) {
		try {
			jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisFactory.returnResource(jedis);
		}
	}


}
