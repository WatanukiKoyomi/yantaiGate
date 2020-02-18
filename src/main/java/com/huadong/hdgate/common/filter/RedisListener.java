package com.huadong.hdgate.common.filter;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;

public class RedisListener implements MessageListener{
	@Override
	public void onMessage(Message message, @Nullable byte[] bytes) {

	}
}
