package com.huadong.hdgate.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class RedisUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	@Autowired
	private JedisPool jedisPool;

	public void brpopQueue(String queueName){
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		while(true){
			List<String> msg = jedis.brpop(0, queueName);
			logger.info("队列："+queueName+",数据："+msg);
			String jsonStr = msg.get(1);
			System.out.println("======"+jsonStr);// 解析数据，操作数据库，避免死锁
		}
	}

	public String rpopQueue(String queueName,int index){
		StringBuffer result = new StringBuffer();
		try{
			Jedis jedis = jedisPool.getResource();
			jedis.select(index);
			result.append(jedis.rpop(queueName));
			jedis.close();
		} catch (Exception e){
			logger.error(e.getMessage());
		} finally {
			return result.toString();
		}

	}

	public void lpushQueue(String queueName,String dbData,int index){
		try{
			Jedis jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.lpush(queueName, dbData);
			jedis.close();
		} catch (Exception e){
			logger.error(e.getMessage());
		}
	}

	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.set(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "0";
		} finally {
			jedis.close();
		}
	}

	public String setex(String key, int seconds,String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			//jedis.select(indexdb);
			return jedis.setex(key, seconds,value);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "0";
		} finally {
			jedis.close();
		}
	}

	public String get(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = jedisPool.getResource();
			//jedis.select(indexdb);
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedis.close();
		}
		return value;
	}


}
