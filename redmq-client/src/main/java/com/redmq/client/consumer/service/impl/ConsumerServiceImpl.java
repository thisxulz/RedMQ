package com.redmq.client.consumer.service.impl;

import org.apache.commons.lang.StringUtils;

import com.redmq.client.consumer.ConsumeMessageResult;
import com.redmq.client.consumer.DefaultConsumer;
import com.redmq.client.consumer.MessageStatus;
import com.redmq.client.consumer.service.ConsumerService;
import com.redmq.client.log.ClientLog;
import com.redmq.client.log.ClientLogFactory;
import com.redmq.utils.constants.RedisConstants;
import com.redmq.utils.redis.RedisService;

/**
 * @title 
 * @author xulz
 * @date 2019年1月21日上午11:34:07
 */
public class ConsumerServiceImpl implements ConsumerService{
	private static final ClientLog log = ClientLogFactory.getLogger(ConsumerServiceImpl.class);
	
	private DefaultConsumer defaultConsumer;
	
	private RedisService redisService;
	
	public ConsumerServiceImpl(DefaultConsumer defaultConsumer, RedisService redisService) {
		this.defaultConsumer = defaultConsumer;
		this.redisService = redisService;
	}

	@Override
	public ConsumeMessageResult dealMessage() {
		ConsumeMessageResult result = new ConsumeMessageResult();
		try {
			//redis队列中获取数据
			String key = RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, defaultConsumer.getTopicName(), defaultConsumer.getGroupName());
			String message = redisService.lpop(key);
			if(StringUtils.isNotBlank(message)) {
				//处理消息
				MessageStatus messageStatus = defaultConsumer.getMessageLinser().consumeMessage(message);
				if(messageStatus != null) {
					switch(messageStatus) {
					case SUCCESS :
						result.setSuccess(true);
						break;
					case ERROR :
						result.setSuccess(false);
						//处理失败放回队列中
						redisService.rpush(key, message);
						break;
					default:
		                break;
					}
				}else {
					result.setSuccess(false);
				}
			}else {
				result.setSuccess(false);
				result.setStatus(1);
			}
		}catch(Exception e) {
			log.error("dealMessage error", e);
		}
		return result;
	}

	@Override
	public DefaultConsumer getDefaultConsumer() {
		return this.defaultConsumer;
	}
	
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
}
