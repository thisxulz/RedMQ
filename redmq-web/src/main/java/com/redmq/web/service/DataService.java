package com.redmq.web.service;

import java.util.List;
import java.util.Set;

import com.redmq.web.model.MsgListStatus;

/**
 * @title 
 * @author xulz
 * @date 2019年1月24日下午6:04:09
 */
public interface DataService {

	/**
	 * 获取所有的注册topic
	 * @return
	 */
	public Set<String> getAllTopics();
	
	/**
	 * 获取所有注册的groups
	 * @return
	 */
	public Set<String> getAllGroups();
	
	/**
	 * 获取主题下注册的消费者分组
	 * @param topic
	 * @return
	 */
	public Set<String> getTopicGroups(String topic);
	
	/**
	 * 获取消费者分组监听的主题
	 * @param group
	 * @return
	 */
	public Set<String> getGroupTopics(String group);
	
	/**
	 * 获取消息队列长度，剩余未处理消息
	 * @param topic
	 * @param group
	 * @return
	 */
	public MsgListStatus getMsgList(String topic, String group);

	/**
	 * 获取所有消息队列
	 * @return
	 */
	public List<MsgListStatus> getAllMsgList();
	
	/**
	 * 开启/关闭消费者
	 * @param topic
	 * @param group
	 * @param status
	 * @return
	 */
	public int changeConsumerStatus(String topic, String group, int status);
}
