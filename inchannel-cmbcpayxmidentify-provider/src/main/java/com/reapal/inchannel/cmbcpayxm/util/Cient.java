package com.reapal.inchannel.cmbcpayxm.util;


import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cient {

	private static Logger logger = Logger.getLogger(Cient.class);
//	public static final String IP_ADDR = "192.168.92.50";// 测试服务器地址
    public static final String IP_ADDR = "192.168.92.65";// 生产服务器地址
	public static final int PORT = 9006;// 服务器端口号
	
	public static String sendMessage(byte[] request) throws IOException {
		Socket socket = null;
		String str = "";
		DataOutputStream out = null;
		DataInputStream input = null;
		try {
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			socket = new Socket(IP_ADDR, PORT);
			// 向服务器端发送数据
			out= new DataOutputStream(socket.getOutputStream());
			// 像银行发送数据
			out.write(request);
            out.flush();
			// 读取服务器端数据
			input = new DataInputStream(socket.getInputStream());
			logger.info("银行返回的流 InputStream input=========" + input);
			str = StreamUtil.toStrXml(input);
			logger.info("Cient处理数据流 转成XML串===========：" + str);
			
		} catch (Exception e) {
			logger.info("======客户端异常========" + e.getStackTrace());
			logger.info("======客户端异常信息========" + e.getMessage());
		} finally {
			out.close();
			input.close();
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					socket = null;
					logger.info("socket客户端finally===============" + e.getStackTrace());
				}
			}
		}
		return str;
	}
}
