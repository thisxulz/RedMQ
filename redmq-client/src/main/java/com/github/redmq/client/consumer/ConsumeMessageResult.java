package com.github.redmq.client.consumer;

/**
 * @title 
 * @author xulz
 * @date 2019年1月22日上午11:48:01
 */
public class ConsumeMessageResult {

	private boolean success;//消费成功/失败
	private int status = 0;//默认为0，1是队列为空,2处理消息失败
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
