package com.github.redmq.web.service;

/**
 * @title 
 * @author xulz
 * @date 2019年1月25日上午11:32:54
 */
public interface DataRegService {

	/**
	 * 注册消费者主题
	 * @param topic
	 * @return
	 */
	public int regTopic(String topic);
	
	/**
	 * 注册消费者分组
	 * @param topic
	 * @param group
	 * @return
	 */
	public int regGroup(String topic, String group);
}
