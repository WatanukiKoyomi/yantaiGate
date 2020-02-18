package com.huadong.hdgate.test.socket;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerTest {

	private int PORT = 9000;//监听的端口号

	@Test
	public void socketServerTest(){
		System.out.println("服务器启动...\n");
		SocketServerTest server = new SocketServerTest();
		server.init();
	}

	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			//System.out.println("111111111111");
			while (true) {
				// 一旦有堵塞, 则表示服务器与客户端获得了连接
				Socket client = serverSocket.accept();
				//System.out.println("2222222222222:"+client.getRemoteSocketAddress());
				// 处理这次连接
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("服务器异常: " + e.getMessage());
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;
		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		public void run() {
			try {
				// 读取客户端数据
				DataInputStream input = new DataInputStream(socket.getInputStream());
				//String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
				// 处理客户端数据
				//System.out.println("客户端发过来的内容:" + clientInputStr);

				byte[] b = new byte[3072];
				input.read(b);
				System.out.println("==="+new String(b, 0, 3072));

				// 读取字符
				//char[] c = new char[1024];
				//for (int i = 0; i < 200; i++) {
				//	c[i] = input.readChar();
				//}
				//System.out.println("---"+new String(c, 0, 1024));

				// 向客户端回复信息
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());

				// 回馈信息
				out.writeUTF("success");

				out.close();
				input.close();
			} catch (Exception e) {
				System.out.println("服务器 run 异常: " + e.getMessage()+" "+e);
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("服务端 finally 异常:" + e.getMessage());
					}
				}
			}
		}
	}
}
