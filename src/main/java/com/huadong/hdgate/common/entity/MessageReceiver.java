package com.huadong.hdgate.common.entity;

import com.huadong.hdgate.jobManagement.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis接收处理数据
 */
@Component
@Slf4j
public class MessageReceiver {

	@Resource
	private BusinessService businessService;

	/**接收消息的方法*/
	public void receiveMessage(String message){
		log.info("redis收到数据："+message);
		businessService.insertOrUpdateBusinessData(message);
	}

	/**
	 * 2.19 新增加对接redis，数据推送从api改为redis推送
	 * @param message
	 */
	public void receiveRedisMessage(String message){
		System.out.println("receiveRedisMessage:"+message);
		log.info("receiveRedisMessage接收到数据：{}，转发到redis的hd_gate_business_data_db频道中",message);
	}
}
