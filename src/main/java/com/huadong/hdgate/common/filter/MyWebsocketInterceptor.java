package com.huadong.hdgate.common.filter;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class MyWebsocketInterceptor extends HttpSessionHandshakeInterceptor {

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
								   Map<String, Object> attributes) throws Exception {
		//解决The extension [x-webkit-deflate-frame] is not supported问题
		if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
			request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
		}
		//System.out.println("MyWebSocketInterceptor : Before Handshake");

		return super.beforeHandshake(request, response, wsHandler, attributes);
	}


	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
							   Exception ex) {
		//System.out.println("MyWebSocketInterceptor : After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
