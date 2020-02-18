package com.huadong.hdgate.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * socket
 * @author lit
 *
 */
public class SendMsg2SocketUtils {

	private static final Logger logger = LoggerFactory.getLogger(SendMsg2SocketUtils.class);


	/**
	 * 与socket交互操作
	 * @param msg 相应参数等
	 */
	public static void sendMsg2SocketServer(String socketHost,int socketPort,String msg){
		Socket socket = null;
		try {
			//更改socket的ip和端口都从数据库获取
			//创建一个流套接字并将其连接到指定主机上的指定端口号
			logger.info("socket："+ socketHost+" 端口号："+ socketPort + " 数据：" + msg);
			socket = new Socket(socketHost, socketPort);

			//向服务器端发送车道信息
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.write(msg.getBytes("utf-8"));//字节
			//out.writeChars(URLEncoder.encode(msg, "utf-8"));//字符
			//out.writeUTF(msg);//直接发送msg，msg是个json串，其中包含了车道信息
			logger.info("成功发送数据到socket服务端"+socketHost+":"+socketPort+"。数据长度"+msg.length());
			out.close();
		} catch (IOException e) {
			logger.error("客户端发送数据异常:" + e.getMessage()+e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					socket = null;
					logger.error("客户端 finally 异常:" + e.getMessage());
				}
			}
		}
	}

}
