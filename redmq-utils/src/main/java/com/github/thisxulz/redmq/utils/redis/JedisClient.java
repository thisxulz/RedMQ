package com.github.thisxulz.redmq.utils.redis;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisClient {
	private static final Logger logger = LoggerFactory.getLogger(JedisClient.class);

	public final static String SUCCESS_FLAG = "OK";

	private JedisPool pool;

	private static JedisClient jedisClinet = new JedisClient();

	public static JedisClient getInstance() {
		return jedisClinet;
	}

	private JedisClient() {
		init("redmq.properties");
	}

	private static Configuration config = null;

	public void init(String file) {

		if (pool != null) {
			return;
		}
		try {
			logger.info("init jedis config : {}", file);
			File objFile = new File(file);
			// 传入绝对路径还是文件名，处理方式不同
			if (objFile.exists()) {
				config = new PropertiesConfiguration(objFile);
			} else {
				config = new PropertiesConfiguration(file);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}
		try {
			int maxTotal = config.getInt("redis.maxTotal");
			int maxIdle = config.getInt("redis.maxIdle");
			int maxWait = config.getInt("redis.maxWait");
			int timeout = config.getInt("redis.timeout");
			
			String server = config.getString("redis.server").split(":")[0];
			String port = config.getString("redis.server").split(":")[1];

			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxIdle(maxIdle);
			poolConfig.setMaxTotal(maxTotal);
			poolConfig.setMaxWaitMillis(maxWait);

			String requirepass = config.getString("redis.requirepass");

			if(StringUtils.isBlank(requirepass)){
				pool = new JedisPool(poolConfig, server, Integer.valueOf(port), timeout, null, Protocol.DEFAULT_DATABASE);
			}else{
				pool = new JedisPool(poolConfig, server, Integer.valueOf(port), timeout, requirepass, Protocol.DEFAULT_DATABASE);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Jedis getJedis() {
		Jedis jedis = null;
		if (pool != null) {
			try {
				jedis = pool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("从redis获取数据超时.", e);
				try {
					Thread.sleep(2000);
					logger.info("再次连接redis获取数据");
					jedis = pool.getResource();
				} catch (Exception e1) {
					logger.error("从redis获取数据超时", e1);
				}
			}

		}
		return jedis;
	}

	/**
	 * jedis归还到pool
	 * @param jedis
	 */
	public void close(Jedis jedis) {
		if (pool != null && jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 程序结束，销毁pool
	 */
	public void destroy() {
		if (pool != null) {
			pool.destroy();
		}
	}
}