package com.redmq.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.redmq.utils.constants.RedisConstants;
import com.redmq.utils.factory.RedisFactory;
import com.redmq.utils.redis.RedisService;
import com.redmq.web.model.MsgListStatus;
import com.redmq.web.service.DataService;

/**
 * @title 
 * @author xulz
 * @date 2019年1月24日下午6:05:41
 */
@Service
public class DataServiceImpl implements DataService{
	private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);
	
	@Override
	public Set<String> getAllTopics() {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.REDIS_TOPIC);
		}catch(Exception e) {
			log.error("getAllTopics error", e);
		}
		return data;
	}
	
	@Override
	public Set<String> getAllGroups() {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.REDIS_GROUP);
		}catch(Exception e) {
			log.error("getAllTopics error", e);
		}
		return data;
	}

	@Override
	public Set<String> getTopicGroups(String topic) {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, topic));
		}catch(Exception e) {
			log.error("getTopicGroups error", e);
		}
		return data;
	}

	@Override
	public Set<String> getGroupTopics(String group) {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.getKey(RedisConstants.REDIS_GROUP_TOPICS_PREFIX, group));
		}catch(Exception e) {
			log.error("getGroupTopics error", e);
		}
		return data;
	}

	@Override
	public MsgListStatus getMsgList(String topic, String group) {
		MsgListStatus msgListStatus = new MsgListStatus();
		try {
			RedisService redisService = RedisFactory.getRedisService();
			long leftMsg = redisService.llen(RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, topic, group));
			long time = System.currentTimeMillis();
			long leftThread = redisService.zcount(RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_HEART_PREFIX, topic, group), time - 10000, time + 10000);
			msgListStatus.setLeftMsg(leftMsg);
			msgListStatus.setLeftThread(leftThread);
			msgListStatus.setGroup(group);
			msgListStatus.setTopic(topic);
		}catch(Exception e) {
			log.error("getMsgList error", e);
		}
		return msgListStatus;
	}
	
	@Override
	public List<MsgListStatus> getAllMsgList() {
		List<MsgListStatus> list = new ArrayList<MsgListStatus>();
		try {
			Set<String> topics = this.getAllTopics();
			if(topics != null && topics.size() > 0) {
				for(String topic : topics) {
					Set<String> groups = this.getTopicGroups(topic);
					if(groups != null && groups.size() > 0) {
						for(String group : groups) {
							MsgListStatus object = this.getMsgList(topic, group);
							list.add(object);
						}
					}
				}
			}
		}catch(Exception e) {
			log.error("getMsgList error", e);
		}
		return list;
	}

	@Override
	public int changeConsumerStatus(String topic, String group, int status) {
		int result = 0;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			String key = RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_THREAD_LOCK_PREFIX, topic, group);
			if(status == 0) {//关闭
				redisService.set(key, "1");
				result = 1;
			}else if(status == 1) {//开启
				redisService.del(key);
				result = 1;
			}
		}catch(Exception e) {
			log.error("getMsgList error", e);
			result = -1;
		}
		return result;
	}

}
