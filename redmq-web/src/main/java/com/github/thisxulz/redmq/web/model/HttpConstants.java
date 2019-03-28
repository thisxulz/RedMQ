package com.github.thisxulz.redmq.web.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @title 
 * @author xulz
 * @date 2019年1月25日上午11:16:53
 */
public class HttpConstants {
	
	//http
	public static final String RESUTL_OK = "SUCCESS";
	public static final String RESUTL_FAIL = "FAIL";
	
	//error
	public static final String CODE_UNKNOWN = "unknown";
	public static final String CODE_EXCEPTION = "exception";
	public static final String CODE_PARAMETER_MISS = "parameter_miss";
	
	public static Map<String, String> MSG_MAP = new HashMap<String, String>();
	
	static{
		MSG_MAP.put(CODE_PARAMETER_MISS, "缺少参数");
		MSG_MAP.put(CODE_UNKNOWN, "未知错误，请重新发起请求");
		MSG_MAP.put(CODE_EXCEPTION, "程序异常");
	}
}
