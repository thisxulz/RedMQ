package com.redmq.client.producer;

import org.apache.commons.lang.StringUtils;

import com.redmq.client.consumer.service.ProducerService;
import com.redmq.client.consumer.service.impl.ProducerServiceImpl;
import com.redmq.client.log.ClientLog;
import com.redmq.client.log.ClientLogFactory;
import com.redmq.utils.constants.RedisConstants;
import com.redmq.utils.redis.JedisClusterClient;

import redis.clients.jedis.JedisCluster;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午3:23:30
 */
public class DefaultProducer {
	private static final ClientLog log = ClientLogFactory.getLogger(DefaultProducer.class);

	//消息的topic
	private String topicName;
	
	//消费者的group
	private String groupName;
	
	private ProducerService producerService;
	
	/**
	 * 生产者，
	 * @param topicName，消息主题
	 * @param groupName，消费者group，为空的时候，消息发送给topic下所有group
	 */
	public DefaultProducer(String topicName, String groupName) {
		this.topicName = topicName;
		this.groupName = groupName;
		if(checkTopicAndGroup()) {
			producerService = new ProducerServiceImpl(this);
		}
	}
	
	public void sendMessage(String message) {
		if(producerService != null) {
			producerService.sendMessage(message);
		}else {
			log.info("发送消息失败，请检查消息topic和group是否注册");
		}
	}
	
	public boolean checkTopicAndGroup() {
		try {
			JedisCluster jedis = JedisClusterClient.getJedis();
			//是否注册的topic
			boolean exists = jedis.sismember(RedisConstants.getKey(RedisConstants.REDIS_TOPIC), this.getTopicName());
			if(exists) {//已注册
				if(StringUtils.isNotBlank(this.getGroupName())) {//是否注册group
					exists = jedis.sismember(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, this.getTopicName()), this.getGroupName());
				}
			}
			if(exists) return true; 
		}catch(Exception e) {
			log.error("checkTopicAndGroup error", e);
		}
		return false;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}