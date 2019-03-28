package com.github.thisxulz.redmq.utils.tools;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @title
 * @author xulz
 * @date 2016年5月30日上午11:33:28
 */
public class ConfigUtils {
	private static Configuration conf;
	static {
		try {
			conf = new PropertiesConfiguration("redmq.properties");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static Configuration getConf() {
		return conf;
	}

	public static void setConf(Configuration conf) {
		ConfigUtils.conf = conf;
	}
}
