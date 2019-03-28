package com.github.thisxulz.redmq.utils.redis;

import java.util.Set;

/**
 * @title
 * @author xulz
 * @date 2019年3月8日
 */
public interface RedisService {

	/* kv */
	void set(String key, String value);
	
	String get(String key);
	
	void del(String key);
	
	boolean exists(String key);
	
	/* list */
	long llen(String key);
	
	void rpush(String key, String value);
	
	void lpush(String key, String value);
	
	String rpop(String key);
	
	String lpop(String key);
	
	/* set */
	
	Set<String> smembers(String key);
	
	void sadd(String key, String value);
	
	boolean sismember(String key, String member);
	
	/* zset */
	
	void zadd(String key, double score, String member);
	
	long zcount(String key, double min, double max);
	
}
