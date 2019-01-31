package com.redmq.client.consumer.service.impl;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.redmq.client.consumer.service.ProducerService;
import com.redmq.client.log.ClientLog;
import com.redmq.client.log.ClientLogFactory;
import com.redmq.client.producer.DefaultProducer;
import com.redmq.utils.constants.RedisConstants;
import com.redmq.utils.redis.JedisClusterClient;

import redis.clients.jedis.JedisCluster;

/**
 * @title 
 * @author xulz
 * @date 2019年1月22日下午6:00:20
 */
public class ProducerServiceImpl implements ProducerService{
	private static final ClientLog log = ClientLogFactory.getLogger(ProducerServiceImpl.class);
	
	private DefaultProducer defaultProducer;

	public ProducerServiceImpl(DefaultProducer defaultProducer) {
		this.defaultProducer = defaultProducer;
	}
	
	@Override
	public void sendMessage(String message) {
		try {
			JedisCluster jedis = JedisClusterClient.getJedis();
			// 已注册的topic，有分组的发送给分组，没有分组的发送给topic下所有的分组
			if(StringUtils.isNotBlank(defaultProducer.getGroupName())) {//有分组的发送给分组
				jedis.rpush(RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, defaultProducer.getTopicName(), defaultProducer.getGroupName()), message);
			}else {//没有分组的发送给topic下所有的分组
				Set<String> groups = jedis.smembers(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, defaultProducer.getTopicName()));
				if(groups != null) {
					for(String group : groups) {
						jedis.rpush(RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, defaultProducer.getTopicName(), group), message);
					}
				}
			}
		}catch(Exception e) {
			log.error("sendMessage error", e);
			JedisClusterClient.returnResource();
		}
	}

}
