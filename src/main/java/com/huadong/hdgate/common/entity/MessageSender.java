package com.huadong.hdgate.common.entity;

import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@EnableScheduling //开启定时器功能
@Order(value = 10)
@Component
public class MessageSender {
	@Resource(name="template")
	private StringRedisTemplate stringRedisTemplate;

	//@Scheduled(fixedRate = 2000) //间隔2s 通过StringRedisTemplate对象向redis消息队列chat频道发布消息
	public void sendMessage(){
		stringRedisTemplate.convertAndSend("hd_gate_business_data_db",new Timestamp(new Date().getTime()).toString());
	}
}
