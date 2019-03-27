package com.github.redmq.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.redmq.utils.constants.RedisConstants;
import com.github.redmq.utils.factory.RedisFactory;
import com.github.redmq.utils.redis.RedisService;
import com.github.redmq.web.service.DataRegService;

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
			RedisService redisService = RedisFactory.getRedisService();
			if(redisService.sismember(RedisConstants.REDIS_TOPIC, topic)) {//判断主题是否存在
				result = 2;
			}else {
				redisService.sadd(RedisConstants.REDIS_TOPIC, topic);
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
			RedisService redisService = RedisFactory.getRedisService();
			if(redisService.sismember(RedisConstants.REDIS_TOPIC, topic)) {//判断主题是否存在
				redisService.sadd(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, topic), group);
				redisService.sadd(RedisConstants.getKey(RedisConstants.REDIS_GROUP_TOPICS_PREFIX, group), topic);
				redisService.sadd(RedisConstants.REDIS_GROUP, group);
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
