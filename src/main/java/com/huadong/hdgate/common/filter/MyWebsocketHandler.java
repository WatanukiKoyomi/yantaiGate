package com.huadong.hdgate.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebsocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(MyWebsocketHandler.class);

	public static ConcurrentHashMap<String, WebSocketSession> socketSessionMap = new ConcurrentHashMap<>();

	/**
	 * 建立连接后
	 */
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		//不做操作
		String uri = session.getUri().toString();
		int index = uri.lastIndexOf("/");
		String name = uri.substring(index+1);//车道名称或者是字符串laneState,gate中包含了userid
		socketSessionMap.put(name,session);//放入map中

		//System.out.println(name+"======建立连接完成======"+session+"  -----------  "+socketSessionMap);

	}
	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理-
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String messageStr = message.getPayload();//获取传过来的数据   暂时不用获取前端传递数据
		System.out.println("handleTextMessage");
		if(messageStr.equals("HeartBeat")){
			session.sendMessage(new TextMessage("alive"));
		}
	}

	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.info("Websocket已经关闭"+session+"  "+closeStatus+"  "+socketSessionMap.toString());
		//System.out.println("Websocket已经关闭"+session+"  "+closeStatus+"   "+socketSessionMap);
		//System.out.println(socketSessionMap.keySet());
		for (String key : socketSessionMap.keySet()) {
			//value = socketSessionMap.get(key);
			//System.out.println("trueOrFalse:"+(socketSessionMap.get(key)==session));
			if(socketSessionMap.get(key)==session){
				socketSessionMap.remove(key);//不移除，赋值null，为了避免删除导致长度变化而同时其他也在循环map
			}
		}

	}

	/**
	 * 推送消息
	 *
	 * @param gate
	 * @param jsonStr
	 * @throws IOException
	 */
	public static void sendMessageToHtml(String gate, String jsonStr)
			throws IOException {
		WebSocketSession session = socketSessionMap.get(gate);
		//System.out.println("======给"+gate+"发送消息======");
		if (session != null && session.isOpen()) {
			session.sendMessage(new TextMessage(jsonStr));
		}
	}
}
