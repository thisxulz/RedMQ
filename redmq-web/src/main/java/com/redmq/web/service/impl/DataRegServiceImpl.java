package com.redmq.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.redmq.utils.constants.RedisConstants;
import com.redmq.utils.redis.JedisClusterClient;
import com.redmq.web.service.DataRegService;

import redis.clients.jedis.JedisCluster;

/**
 * @title 
 * @author xulz
 * @date 2019年1月25日上午11:34:21
 */
@Service
public class DataRegServiceImpl implements DataRegService{
	private static final Logger log = LoggerFactory.getLogger(DataRegServiceImpl.class);
	
	@Override
	public int regTopic(String topic) {
		int result = 0;
		try {
			JedisCluster jedis = JedisClusterClient.getJedis();
			if(jedis.sismember(RedisConstants.REDIS_TOPIC, topic)) {//判断主题是否存在
				result = 2;
			}else {
				jedis.sadd(RedisConstants.REDIS_TOPIC, topic);
				result = 1;
			}
		}catch(Exception e) {
			log.error("regTopic error", e);
			result = -1;
		}
		return result;
	}

	@Override
	public int regGroup(String topic, String group) {
		int result = 0;
		try {
			JedisCluster jedis = JedisClusterClient.getJedis();
			if(jedis.sismember(RedisConstants.REDIS_TOPIC, topic)) {//判断主题是否存在
				jedis.sadd(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, topic), group);
				jedis.sadd(RedisConstants.getKey(RedisConstants.REDIS_GROUP_TOPICS_PREFIX, group), topic);
				jedis.sadd(RedisConstants.REDIS_GROUP, group);
				result = 1;
			}else {
				result = 2;
			}
		}catch(Exception e) {
			log.error("regGroup error", e);
		}
		return result;
	}

}
