package client;

import org.junit.Test;

import com.redmq.client.consumer.DefaultConsumer;
import com.redmq.client.consumer.MessageStatus;
import com.redmq.client.consumer.service.MessageLinser;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午5:38:08
 */
public class ConsumerTest {
	
	@Test
	public void test() {
	}
	
	public static void main(String[] args) {
		String topicName = "testTopic";
		String groupName = "testGroup";
		DefaultConsumer defaultConsumer = new DefaultConsumer(topicName, groupName, 1);
		defaultConsumer.setMessageLinser(new MessageLinser() {
			@Override
			public MessageStatus consumeMessage(String message) {
				System.out.println(message);
				return MessageStatus.SUCCESS;
			}
		});
		defaultConsumer.start();
	}
}
