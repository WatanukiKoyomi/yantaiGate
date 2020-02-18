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
}
