package com.github.thisxulz.redmq.client.consumer.service;

import com.github.thisxulz.redmq.client.consumer.MessageStatus;

/**
 * @title 
 * @author xulz
 * @date 2019年1月22日上午11:30:28
 */
public interface MessageLinser {

	/**
	 * 处理消息
	 * @param message
	 * @return
	 */
	MessageStatus consumeMessage(String message);
}
