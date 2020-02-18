package com.huadong.hdgate.common.config;

import com.huadong.hdgate.common.filter.MyWebsocketHandler;
import com.huadong.hdgate.common.filter.MyWebsocketInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MyWebsocketHandler(), "/ws/*").setAllowedOrigins("*").addInterceptors(new MyWebsocketInterceptor());
		registry.addHandler(new MyWebsocketHandler(), "/ws/sockjs").setAllowedOrigins("*").addInterceptors(new MyWebsocketInterceptor()).withSockJS();
	}
}
