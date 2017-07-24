package com.reapal.inchannel.cmbcpayxm.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;

public class StreamUtil {
	
	private static Logger logger = Logger.getLogger(StreamUtil.class);

	/**
	 * 报文头长度
	 */
	private static int headLength = 8;
	/**
	 * 发起机构号长度
	 */
	private static int organLength = 8;

	/**
	 * 交易码长度
	 */
	private static int trancodeLength = 8;

	/**
	 * 签名域长度
	 */
	private static int signLength = 4;
	

	public static String toStrXml(InputStream input) throws Exception {
		
		byte msg[] = new byte[4096]; // 创建合适文件大小的数组
		input.read(msg); // 读取文件中的内容到b[]数组
		// 当前消息的总长度，不包括8位报文总长度
		long msgLength = NumberUtils.toLong(new String(ArrayUtils.subarray(msg,0, headLength)));
				
		logger.info("====== msgLength===============" + msgLength);
		// 截止到现在收到的消息内容的长度小于消息总长度，则返回空，待全部读取完成后再返回
		long realLength = msgLength + headLength;
		logger.info("====== 消息总长度realLength===============" + realLength);
		byte[] bytes = ArrayUtils.subarray(msg, headLength, (int) realLength);
		// 提取交易处理码
		String messageCode = new String(ArrayUtils.subarray(bytes, organLength,organLength + trancodeLength)).trim();
				
		logger.info("====== 交易处理码messageCode===============" + messageCode);
		// 合作方代码
		String hzfdm = new String(ArrayUtils.subarray(bytes, 0, organLength)).trim();
				
		logger.info("====== 合作方代码hzfdm===============" + hzfdm);
		// 前置长度：合作方编号长度+交易码长度+4位签名域长度
		int preLength = organLength + trancodeLength + signLength;
		logger.info("====== 前置长度preLength===============" + preLength);
		// 签名域值长度
		long signLength = NumberUtils.toLong(new String(ArrayUtils.subarray(bytes, organLength + trancodeLength, preLength)));
				
		logger.info("====== 签名域值长度signLength===============" + signLength);
		// 签名域值
		byte[] signBytes = ArrayUtils.subarray(bytes, preLength, preLength+ (int) signLength);
				
		// 提取xml报文内容并且解密
		byte[] xmlBytes = ArrayUtils.subarray(bytes,(int) (preLength + signLength), bytes.length);
				
		// 解密
		byte[] decryptedBytes = RSAHelper.decryptRSA(xmlBytes, false, "UTF-8");
		logger.info("====== 解密结果===============：" + decryptedBytes);

		// 验证签名
		boolean isValid = RSAHelper.verifyRSA(decryptedBytes, signBytes, false,"UTF-8");
				
		logger.info("====== 验证签名结果===============：" + isValid);

		String strXml = new String(decryptedBytes, "UTF-8");
		logger.info("====== 解密后转成XML字符串的结果=============：" + strXml);
		
		input.close();
		return messageCode+strXml;
	}
}
