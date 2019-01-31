package com.redmq.web.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @title 
 * @author xulz
 * @date 2019年1月25日上午11:58:17
 */
public class MsgListStatus {

	@ApiModelProperty(value = "消息队列主题")
	private String topic;
	@ApiModelProperty(value = "消息者分组")
	private String group;
	@ApiModelProperty(value = "剩余线程数") 
	private long leftThread;
	@ApiModelProperty(value = "剩余未处理的消息数") 
	private long leftMsg;
	
	public long getLeftThread() {
		return leftThread;
	}

	public void setLeftThread(long leftThread) {
		this.leftThread = leftThread;
	}

	public long getLeftMsg() {
		return leftMsg;
	}

	public void setLeftMsg(long leftMsg) {
		this.leftMsg = leftMsg;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	
}
