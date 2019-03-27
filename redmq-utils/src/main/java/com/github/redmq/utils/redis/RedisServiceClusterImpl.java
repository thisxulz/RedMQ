package com.github.redmq.utils.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

/**
 * @title
 * @author xulz
 * @date 2019年3月8日
 */
public class RedisServiceClusterImpl implements RedisService{
	private static final Logger logger = LoggerFactory.getLogger(RedisServiceClusterImpl.class);
	
	public RedisServiceClusterImpl() {
		logger.info("redis集群开启");
	};
	
	@Override
	public void set(String key, String value) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			jedis.set(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
	}

	@Override
	public String get(String key) {
		try {
			JedisCluster jedis = JedisClusterClient.getJedis();
			return jedis.get(key);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return null;
	}

	@Override
	public void del(String key) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			jedis.del(key);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
	}

	@Override
	public long llen(String key) {
		try {
			JedisCluster jedis = JedisClusterClient.getJedis();
			return jedis.llen(key);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return 0L;
	}

	@Override
	public void rpush(String key, String value) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			jedis.rpush(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
	}

	@Override
	public void lpush(String key, String value) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			jedis.lpush(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}		
	}

	@Override
	public String rpop(String key) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			return jedis.rpop(key);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return null;
	}

	@Override
	public String lpop(String key) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			return jedis.lpop(key);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return null;
	}

	@Override
	public Set<String> smembers(String key) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			return jedis.smembers(key);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return null;
	}

	@Override
	public void sadd(String key, String value) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			jedis.sadd(key, value);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
	}

	@Override
	public void zadd(String key, double score, String member) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			jedis.zadd(key, score, member);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
	}

	@Override
	public long zcount(String key, double min, double max) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			return jedis.zcount(key, min, max);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return 0L;
	}

	@Override
	public boolean exists(String key) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			return jedis.exists(key);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return false;
	}

	@Override
	public boolean sismember(String key, String member) {
		JedisCluster jedis = JedisClusterClient.getJedis();
		try {
			return jedis.sismember(key, member);
		}catch(Exception e) {
			logger.error("RedisServiceClusterImpl error", e);
		}
		return false;
	}

}
