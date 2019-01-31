package com.redmq.client.consumer;

import com.redmq.client.consumer.service.ConsumerService;
import com.redmq.client.consumer.service.MessageLinser;
import com.redmq.client.consumer.service.impl.ConsumerServiceImpl;
import com.redmq.client.consumer.thread.ConsumerServiceThread;
import com.redmq.client.log.ClientLog;
import com.redmq.client.log.ClientLogFactory;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午3:30:17
 */
public class DefaultConsumer {
	private static final ClientLog log = ClientLogFactory.getLogger(DefaultConsumer.class);
	
	//默认启动的处理message线程数
	private int threadNum = 1;
	
	//消息的topic
	private String topicName;
	
	//消费者的group
	private String groupName;
	
	private ConsumerService consumerService;
	
	private MessageLinser messageLinser;
	
	public DefaultConsumer(String topicName, String groupName, int threadNum) {
		this.topicName = topicName;
		this.groupName = groupName;
		if(threadNum > 0) this.threadNum = threadNum;
		consumerService = new ConsumerServiceImpl(this);
	}
	
	public DefaultConsumer(String topicName, String groupName) {
		this.topicName = topicName;
		this.groupName = groupName;
		consumerService = new ConsumerServiceImpl(this);
	}
	
	//启动。保证消息不重复，不丢失，但是不保存历史消息
	public void start() {
		for(int i=0;i<this.threadNum;i++) {
			ConsumerServiceThread thread = new ConsumerServiceThread(consumerService);
			thread.setName("ConsumerServiceThread_" + i);
			thread.start();
		}
		log.info("消息者线程启动已启动");
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
	
	public void setMessageLinser(MessageLinser messageLinser) {
		this.messageLinser = messageLinser;
	}
	
	public MessageLinser getMessageLinser() {
		return this.messageLinser;
	}
}
