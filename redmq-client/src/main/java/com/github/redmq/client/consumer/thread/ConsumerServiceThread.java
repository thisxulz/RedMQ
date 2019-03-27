package com.github.redmq.client.consumer.thread;

import com.github.redmq.client.consumer.ConsumeMessageResult;
import com.github.redmq.client.consumer.service.ConsumerService;
import com.github.redmq.client.log.ClientLog;
import com.github.redmq.client.log.ClientLogFactory;
import com.github.redmq.utils.constants.RedisConstants;
import com.github.redmq.utils.redis.RedisService;

/**
 * @title 
 * @author xulz
 * @date 2019年1月21日上午11:26:09
 */
public class ConsumerServiceThread extends Thread{
	private static final ClientLog log = ClientLogFactory.getLogger(ConsumerServiceThread.class);
	
	private ConsumerService consumerService;
	
	private RedisService redisService;
	
	public ConsumerServiceThread() {}
	
	@Override
	public void run() {
		log.info("消费者线程{}启动...",this.getName());
		int count = 0;
		String lockKey = RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_THREAD_LOCK_PREFIX, consumerService.getDefaultConsumer().getTopicName(), consumerService.getDefaultConsumer().getGroupName());
		String heartKey = RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_HEART_PREFIX, consumerService.getDefaultConsumer().getTopicName(), consumerService.getDefaultConsumer().getGroupName());
		redisService.del(lockKey);//重启时删除key
		while(true) {
			try {
				if(redisService.exists(lockKey)) {//通过redis缓存锁安全关闭线程，保证队列的安全性和完整性
					log.info("消费者线程{}安全关闭...",this.getName());
					break;
				}else {
					// 加入心跳，监听线程	
					redisService.zadd(heartKey, System.currentTimeMillis(), this.getName());
					ConsumeMessageResult result = consumerService.dealMessage();
					// 消费消息结果处理
					if(!result.isSuccess()) {//消费成功、失败、没有更多消息
						sleep(3000);
					}
					count++;
				}
			}catch(Exception e) {
				log.error(this.getName() + "消费者线程异常", e);
			}
		}
		log.info("消费者线程{}安全结束...消费消息{}条",this.getName(),count);
	}
	
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	
	public void setConsumerService(ConsumerService consumerService) {
		this.consumerService = consumerService;
	}
}
