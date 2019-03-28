package com.github.thisxulz.redmq.utils.factory;

import com.github.thisxulz.redmq.utils.constants.ConfigConstants;
import com.github.thisxulz.redmq.utils.redis.RedisService;
import com.github.thisxulz.redmq.utils.redis.RedisServiceClusterImpl;
import com.github.thisxulz.redmq.utils.redis.RedisServiceImpl;

/**
 * @title
 * @author xulz
 * @date 2019年3月11日
 */
public class RedisFactory {

	private static RedisService redisService;
	
	public static RedisService getRedisService() {
		if(redisService == null) {
			if(ConfigConstants.REDIS_CLUSTER_OPEN == 1) {
				redisService = new RedisServiceClusterImpl();
			}else {
				redisService = new RedisServiceImpl();
			}
		}
		return redisService;
	}
	
}
