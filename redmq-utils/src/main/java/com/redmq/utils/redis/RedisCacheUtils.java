package com.redmq.utils.redis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redmq.utils.tools.TimeUtils;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

/**
 * @title redis 缓存操作工具类
 * @author xulz
 * @date 2015年8月27日上午10:43:37
 */
public class RedisCacheUtils {
	private static Logger logger = LoggerFactory.getLogger(RedisCacheUtils.class);

	public static final String MAP_ARRAY = "array";
	public static final String MAP_CLASS = "class";
	
	public static final int CACHE_OUTTIME_MONTH = 30 * 24 * 60 * 60;// 一个月
	
	public static final int CACHE_OUTTIME_DAY = 24 * 60 * 60;// 一天
	
	public static final int CACHE_OUTTIME_HOUR = 60 * 60;// 1小时
	
	public static void sadd(String key, String member){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.sadd(key, member);
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	public static boolean sismember(String key, String member){
		boolean exists = false;
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			exists = jedisCluster.sismember(key, member);
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return exists;
	}
	
	public static String hgetObjectField(String key, String field){
		String value = null;
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			value = jedisCluster.hget(key, field);
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return value;
	}
	
	/**
	 * redis 字符串
	 * @param object 
	 * @param key
	 * @return
	 */
	public static void set(String key, String value){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.set(key, value);
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}

	public static void setWithExpire(String key, String value, int expire){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.set(key, value);
			jedisCluster.expire(key, expire);
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * redis 自增
	 * @param object 
	 * @param key
	 * @return
	 */
	public static void incrBy(String key, long incrBy){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			if(incrBy==1){
				jedisCluster.incr(key);
			}else{
				jedisCluster.incrBy(key, incrBy);
			}
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}

	public static Long incr(String key){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.incr(key);
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return -1L;
	}
	
	/**
	 * redis 字符串
	 * @param key
	 * @return 
	 * @return
	 */
	public static String get(String key){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.get(key);
		}catch(Exception e){
			logger.error("redis集群操作异常...key:"+key, e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return null;
	}

	public static Boolean exists(String key){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.exists(key);
		}catch(Exception e){
			logger.error("redis集群操作异常...key:"+key, e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return false;
	}
	
	/**
	 * redis Hash获取对象
	 * @param object 
	 * @param key
	 * @param ignores 不需要取的字段，逗号隔开
	 * @return
	 */
	public static Object hmgetObject(Object object, String key, String ignores){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			Map<String, Object[]> map = objToMap(object, ignores);
			object = listToObj(jedisCluster.hmget(key, (String[])map.get(MAP_ARRAY)), object, (String[])map.get(MAP_ARRAY), (Class[])map.get(MAP_CLASS));
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return object;
	}
	
	/**
	 * redis 批量获取对象
	 * @param objects 
	 * @param keys
	 * @param ignores 不需要取的字段，逗号隔开
	 * @return
	 */
	public static Object[] hmgetObjectList(Object[] objects, String[] keys, String ignores){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			Map<String, Object[]> map = objToMap(objects[0], ignores);
			for(int i=0;i<keys.length;i++){
				listToObj(jedisCluster.hmget(keys[i], (String[])map.get(MAP_ARRAY)), objects[i], (String[])map.get(MAP_ARRAY), (Class[])map.get(MAP_CLASS));
			}
		}catch(Exception e){
			logger.error("redis集群操作异常...", e);
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return objects;
	}
	
	/**
	 * redis Hash存储对象
	 * @param object
	 * @param key
	 */
	public static void hmsetObject(Object object, String key, int seconds){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.hmset(key, objToHash(object));
			if(seconds > 0) {
				jedisCluster.expire(key, seconds);
			}
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * redis 给对象其中一个字段设值
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hset(String key, String field, String value, int seconds){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.hset(key, field, value);
			if(seconds > 0) {
				jedisCluster.expire(key, seconds);
			}
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * redis 给对象其中一个字段设值,过期时间
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hsetExpire(String key, String field, String value, int seconds){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.hset(key, field, value);
			jedisCluster.expire(key, seconds);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * redis 对象某字段加n
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hincrBy(String key, String field, long value){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.hincrBy(key, field, value);
//			jedisCluster.expire(key, CACHE_OUTTIME);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * redis 删除缓存
	 * @param key
	 */
	public static void del(String key){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.del(key);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * 返回 key=key 的 sorted set的元素个数
	 * @param key
	 * @return
	 */
	public static long zcard(String key){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.zcard(key);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return 0;
	}
	
	/**
	 * sorted set元素按照score从大到小顺序排列，取下标[start,end]闭区间内元素(带score返回)
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<Tuple> zrevrangeWithScores(String key,long start,long end){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.zrevrangeWithScores(key, start, end);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return new HashSet<Tuple>();
	}
	
	/**
	 * sorted set元素按照score从大到小顺序排列，取下标[start,end]闭区间内元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrevrange(String key,long start,long end){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			logger.debug("zrevrange key:"+key+" start:"+start+" end:"+end);
			return jedisCluster.zrevrange(key, start, end);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return new HashSet<String>();
	}
	
	/**
	 * sorted set元素按照score从小到大顺序排列，取下标[start,end]闭区间内元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrange(String key,long start,long end){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.zrange(key, start, end);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return new HashSet<String>();
	}
	
	/**
	 * 往sorted set中添加元素
	 * @param key
	 * @param score
	 * @param member
	 */
	public static void zadd(String key, double score, String member){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.zadd(key, score, member);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
			e.printStackTrace();
		}
	}
	
	/**
	 * 从sorted set中删除元素
	 * @param key
	 * @param score
	 * @param member
	 */
	public static void zrem(String key, String member){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.zrem(key, member);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * sorted set元素 按照score 从小到大 的顺序 排列，移除下标[start,end]闭区间的元素
	 * @param key
	 * @param start
	 * @param end
	 */
	public static void zremrangeByRank(String key,long start,long end){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.zremrangeByRank(key, start, end);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * sorted set元素 按照score 从大到小 的顺序 排列，取得score在[min,max]闭区间的元素
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 */
	public static Set<String> zrevrangeByScore(String key,long min,long max){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.zrevrangeByScore(key, max, min);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * sorted set元素 按照score 从大到小 的顺序 排列，取得score在[min,max]闭区间，下标[offset,count]闭区间的元素
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 */
	public static Set<Tuple> zrevrangeByScoreWithScores(String key,long min,long max,int offset,int count){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * sorted set,返回member在zset中的score，score<=0说明不存在
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 */
	public static Double zscore(String key,String member){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.zscore(key, member);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从尾部压入list
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 */
	public static void rpush(String key,String value){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			jedisCluster.rpush(key, value);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
	}
	
	/**
	 * 从头部取数据
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @return 
	 */
	public static List<String> lrange(String key,long start,long end){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.lrange(key, start, end);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return null;
	}
	
	/**
	 * 从尾部压入
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @return 
	 */
	public static Long lpush(String key,String value){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.lpush(key, value);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return null;
	}
	
	/**
	 * 对list进行修剪
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 * @return 
	 */
	public static String ltrim(String key,long start,long end){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.ltrim(key, start, end);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return null;
	}
	
	/**
	 * 取list长度
	 * @param key
	 * @return 
	 */
	public static Long llen(String key){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.llen(key);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return null;
	}
	
	/**
	 * 对key设置过期时间
	 * @param key
	 * @return 
	 */
	public static Long expire(String key, int seconds){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.expire(key, seconds);
		}catch(Exception e){
			JedisClusterClient.returnResource();//异常时候必须return
		}
		return null;
	}

	/**
	 * 对key中的指定value删除
	 * @param key
	 * @param count 移除从表头到表尾，最先发现的count个
	 * @param value 需要删除的value
	 * @return 
	 * @return 
	 */
	public static Long lrem(String key,long count,String value){
		try{
			JedisCluster jedisCluster = JedisClusterClient.getJedis();
			return jedisCluster.lrem(key, count, value);
		}catch(Exception e){
			JedisClusterClient.returnResource();
		}
		return null;
	}
	
	/**
	 * 根据redis hash获取的数据，给对象赋值
	 * @param list
	 * @param obj
	 * @param arrs
	 * @param clas
	 * @return
	 */
	public static Object listToObj(List<String> list, Object obj, String[] arrs, Class[] clas){
		if(list != null) {
			for(int i=0;i<list.size();i++){
				if(StringUtils.isNotBlank(list.get(i))){
					obj = invoke(obj,arrs[i],getOriginalValue(list.get(i),clas[i]));
				}
			}
			return obj;
		}else {
			return null;
		}
	}
	
	public static Object invoke(Object obj, String fieldName, Object value) {
		try{
			if(value != null){
				String firstWord = fieldName.substring(0, 1).toUpperCase();
			    String methodName = String.format("set%s%s", firstWord, fieldName.substring(1));
			    Method method = obj.getClass().getMethod(methodName, value.getClass());
			    method.invoke(obj, value);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	    return obj;
	}
	
	/**
	 * 根据数据类型转换对象
	 * @param value
	 * @param c
	 * @return
	 */
	public static Object getOriginalValue(String value, Class c){
		Object obj = null;
		try{
			if(StringUtils.isNotBlank(value)){
				if(c.getName().equals(java.lang.Integer.class.getName()) || c.getName().equals("int")){
					obj = Integer.valueOf(value);
				}else if(c.getName().equals(java.lang.String.class.getName())){
					obj = value;
				}else if(c.getName().equals(java.lang.Long.class.getName()) || c.getName().equals("long")){
					obj = Long.valueOf(value);
				}else if(c.getName().equals(java.lang.Double.class.getName()) || c.getName().equals("double")){
					obj = Double.valueOf(value);
				}else if(c.getName().equals(java.util.Date.class.getName())){
					obj = TimeUtils.StringDateClever(value);
				}else if(c.getName().equals(java.lang.Boolean.class.getName()) || c.getName().equals("boolean")){
					obj = Boolean.valueOf(value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 将 object 转化为 属性数组和类数组
	 * @param obj 需要转换的对象
	 * @param ignores 不需要获取的属性，如 "serialVersionUID,description",属性之间用逗号隔开
	 * @return
	 */
	public static Map<String, Object[]> objToMap(Object obj, String ignores) {
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		Field[] fields2 = obj.getClass().getSuperclass().getDeclaredFields();
		if(fields.length > 0){
			Map<String, Object[]> map = new HashMap<String, Object[]>();
			List<String> nameList = new ArrayList();
			List<Class> typeList = new ArrayList();
			for (int i = 0; i < fields.length;i++) {
				if(StringUtils.isNotBlank(ignores) && ignores.lastIndexOf(fields[i].getName()) > -1){
					continue;
				}
				if(fields[i].getName().equals("serialVersionUID")){
					continue;
				}
				nameList.add(fields[i].getName());
				typeList.add(fields[i].getType());
			}
			if(fields2.length > 0){
				for (int i = 0; i < fields2.length;i++) {
					if(StringUtils.isNotBlank(ignores) && ignores.lastIndexOf(fields2[i].getName()) > -1){
						continue;
					}
					if(fields2[i].getName().equals("serialVersionUID")){
						continue;
					}
					nameList.add(fields2[i].getName());
					typeList.add(fields2[i].getType());
				}
			}
			Class[] classes = new Class[typeList.size()];
			String[] array = new String[nameList.size()];
			nameList.toArray(array);
			typeList.toArray(classes);

			map.put(MAP_ARRAY, array);
			map.put(MAP_CLASS, classes);
			return map;
		}else{
			return null;
		}
	}
	
	/**
	 * 将Object对象转换为map,用于redis的hash存储
	 * @param obj
	 * @return
	 */
	public static Map<String, String> objToHash(Object obj) {
		Map<String, String> hash = new HashMap<String, String>();
		if (obj == null) return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		String value = "";
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				value = String.valueOf(fields[i].get(obj));
				hash.put(fields[i].getName(), value.equals("null") ? "" : value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}
	
}
