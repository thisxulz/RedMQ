package com.github.thisxulz.redmq.client.consumer.service;

import com.github.thisxulz.redmq.client.consumer.ConsumeMessageResult;
import com.github.thisxulz.redmq.client.consumer.DefaultConsumer;

/**
 * @title 
 * @author xulz
 * @date 2019年1月21日上午11:27:25
 */
public interface ConsumerService {
	
	/**
	 * 处理消息
	 * @return
	 */
	ConsumeMessageResult dealMessage();
	
	/**
	 * 
	 * @return
	 */
	DefaultConsumer getDefaultConsumer();
}
