package com.redmq.utils.constants;

import com.redmq.utils.tools.ConfigUtils;

/**
 * @title
 * @author xulz
 * @date 2019年3月11日
 */
public class ConfigConstants {

	public static String REDIS_SERVER = "172.16.4.20:6379";
	public static String REDIS_REQUIREPASS = "";
	public static int REDIS_MAXTOTAL = 1000;
	public static int REDIS_MAXIDLE = 100;
	public static int REDIS_MAXWAIT = 200;
	public static int REDIS_TIMEOUT = 2000;
	
	/* cluster */
	public static int REDIS_CLUSTER_OPEN = 0;
	public static String REDIS_CLUSTER_SERVER = "172.16.4.8:7000;172.16.4.8:7001;172.16.4.8:7002";
	public static int REDIS_MAXREDIRECTIONS = 1000;
			
	static {
		REDIS_CLUSTER_OPEN = ConfigUtils.getConf().getInt("redis.cluster.open");
	}
	
}
