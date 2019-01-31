package com.redmq.utils.constants;

/**
 * @title 
 * @author xulz
 * @date 2019年1月22日上午11:59:48
 */
public class RedisConstants {

	//所有注册的TOPIC
	public static final String REDIS_TOPIC = "rmq:topics";
	
	//所有注册的Groups
	public static final String REDIS_GROUP = "rmq:groups";
		
	//topic下注册的消费者group,prefix+topicName
	public static final String REDIS_TOPIC_GROUPS_PREFIX = "rmq:topic:groups";
	
	//消费者group监听的topic,prefix+groupName
	public static final String REDIS_GROUP_TOPICS_PREFIX = "rmq:group:topics";
	
	//消息队列前缀,prefix+topicName+groupName
	public static final String REDIS_MESSAGE_PREFIX = "rmq:msg";
	
	//消费者线程锁,prefix+topicName+groupName
	public static final String REDIS_CONSUMER_THREAD_LOCK_PREFIX = "rmq:comthread:lock";
	
	//消费者心跳,prefix+topicName+groupName
	public static final String REDIS_CONSUMER_HEART_PREFIX = "rmq:comheart:lock";
	
	public static String getKey(String keyPrefix, String...parameters) {
		if(parameters != null && parameters.length > 0) {
			StringBuffer sbf = new StringBuffer(keyPrefix);
			for(String str : parameters) {
				sbf.append(":").append(str);
			}
			return sbf.toString();
		}else {
			return keyPrefix;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getKey(REDIS_MESSAGE_PREFIX));
	}
}
