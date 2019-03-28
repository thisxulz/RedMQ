package com.github.thisxulz.redmq.utils.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * @title
 * @author xulz
 * @date 2019年3月8日
 */
public class RedisServiceImpl implements RedisService{
	private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
	
	public RedisServiceImpl() {
		logger.info("redis单机开启");
	}
	
	@Override
	public void set(String key, String value) {
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			jedis.set(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
	}

	@Override
	public String get(String key) {
		String value = null;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			value = jedis.get(key);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return value;
	}

	@Override
	public void del(String key) {
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			jedis.del(key);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
	}

	@Override
	public long llen(String key) {
		long value = 0L;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			value = jedis.llen(key);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return value;
	}

	@Override
	public void rpush(String key, String value) {
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			jedis.rpush(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
	}

	@Override
	public void lpush(String key, String value) {
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			jedis.lpush(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
	}

	@Override
	public String rpop(String key) {
		String value = null;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			value = jedis.rpop(key);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return value;
	}

	@Override
	public String lpop(String key) {
		String value = null;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			value = jedis.lpop(key);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return value;
	}

	@Override
	public Set<String> smembers(String key) {
		Set<String> value = null;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			value = jedis.smembers(key);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return value;
	}

	@Override
	public void sadd(String key, String value) {
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			jedis.sadd(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
	}

	@Override
	public void zadd(String key, double score, String member) {
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			jedis.zadd(key, score, member);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
	}

	@Override
	public long zcount(String key, double min, double max) {
		long value = 0L;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			value = jedis.zcount(key, min, max);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return value;
	}

	@Override
	public boolean exists(String key) {
		boolean result = false;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			result = jedis.exists(key);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return result;
	}

	@Override
	public boolean sismember(String key, String member) {
		boolean result = false;
		Jedis jedis = JedisClient.getInstance().getJedis();
		try {
			result = jedis.sismember(key, member);
		}catch(Exception e) {
			logger.error("RedisServiceImpl error", e);
		}finally {
			JedisClient.getInstance().close(jedis);
		}
		return result;
	}

}
