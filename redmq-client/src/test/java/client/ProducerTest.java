package client;

import org.junit.Test;

import com.github.redmq.client.producer.DefaultProducer;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午5:38:08
 */
public class ProducerTest {
	
	@Test
	public void test() {
		String topicName = "testTopic";
		String groupName = "testGroup";
		DefaultProducer defaultProducer = new DefaultProducer(topicName, groupName);
		for(int i=0;i<1000;i++) {
			String message = "i am message " + i;
			defaultProducer.sendMessage(message);
		}
	}
}
