package com.redmq.utils.redis;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @title redis集群
 * @author xulz
 * @date 2015年8月24日下午11:09:57
 *  1)cluster环境下redis的slave不接受任何读写操作，
	2)client端不支持keys批量操作,不支持select dbNum操作，只有一个db:select 0
	3)JedisCluster 的info()等单机函数无法调用,返回(No way to dispatch this command to Redis Cluster)错误，.
	4)JedisCluster 没有针对byte[]的API，需要自己扩展
 */
public class JedisClusterClient {
	private static Logger logger = LoggerFactory.getLogger(JedisClusterClient.class);
	
	private static JedisCluster jedisCluster;
	
	private static Configuration config = null;
	
	private static final String fileName = "redmq.properties";
	
//	private static final String fileName = "E:\\zcoolworkspace\\zcool\\zcool-web\\src\\main\\resources\\redis_cluster.properties";
	
	/**
	 * 初始化集群
	 */
	private static void init(){
		try {
			logger.info("init redis_cluster config file : " + fileName);
			File objFile=new File(fileName);

			// 传入绝对路径还是文件名，处理方式不同
			if(objFile.exists())
			{
				config = new PropertiesConfiguration(objFile);
			}
			else 
			{
				config = new PropertiesConfiguration(fileName);
			}
			
			int timeout = config.getInt("redis.timeout");
			int maxRedirections = config.getInt("redis.maxRedirections");
			int maxTotal = config.getInt("redis.maxTotal");
			int maxIdle = config.getInt("redis.maxIdle");
			int maxWait = config.getInt("redis.maxWait");
			
			String[] servers = config.getString("redis.cluster.server").split(";");

			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(maxTotal);
			poolConfig.setMaxIdle(maxIdle);
			poolConfig.setMaxWaitMillis(maxWait);
			
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
			
			if(servers != null && servers.length >= 3){
				for(String server : servers){
					jedisClusterNodes.add(new HostAndPort(server.split(":")[0], Integer.valueOf(server.split(":")[1])));
				}
		        jedisCluster = new JedisCluster(jedisClusterNodes, timeout, maxRedirections, poolConfig); 
			}else{
				logger.info("集群节点配置错误");
			}
	        
		} catch (Exception e) {
			logger.error("初始化redis集群异常", e);
		}
	}
	
	/**
	 * 获取集群对象
	 * @return
	 */
	public synchronized static JedisCluster getJedis(){
		try{
			if(jedisCluster == null){
				init();
			}
			return jedisCluster;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 异常时候重置集群
	 */
	public static void returnResource(){
		try{
			jedisCluster = null;
//			init();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//例子
		try{
			long t = System.currentTimeMillis();
			JedisCluster jedis = JedisClusterClient.getJedis();
//			Jedis jedis = JedisClient.getInstance().getJedis();
			for(int i=0;i<10;i++){
				System.out.println(jedis.get("test"));
			}
			System.out.println("耗时： " + (System.currentTimeMillis() -t));
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
}
