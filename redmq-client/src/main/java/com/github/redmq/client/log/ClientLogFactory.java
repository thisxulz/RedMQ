package com.github.redmq.client.log;

/**
 * @title
 * @author xulz
 * @date 2019年1月18日下午4:02:42
 */
public abstract class ClientLogFactory {

	public static ClientLog getLogger(Class classz) {
		return new LogbackLoggerFactory().getLoggerInstance(classz.getName());
	}

	public abstract ClientLog getLoggerInstance(String name);
}
