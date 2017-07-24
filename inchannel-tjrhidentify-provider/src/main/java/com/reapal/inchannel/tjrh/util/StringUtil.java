package com.reapal.inchannel.tjrh.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil {

	/**
	 * base64字符串解码
	 * @param base64String
	 * @return
	 * @throws Exception
	 * @author zhanjie
	 */
	public static String base64Decoder(String base64String) 
		throws Exception
	{
		BASE64Decoder base64Decoder = new BASE64Decoder();
		base64String = base64String.trim().replaceAll(" ","");
		byte[] xmlByte = base64Decoder.decodeBuffer(base64String);
		String str = new String(xmlByte,"UTF-8");
		return str;
	}
	
	public static byte[] base64DecoderByte(String base64String) 
			throws Exception
		{
			BASE64Decoder base64Decoder = new BASE64Decoder();
			base64String = base64String.trim().replaceAll(" ","");
			byte[] xmlByte = base64Decoder.decodeBuffer(base64String);
			
			return xmlByte;
		}
	
	/**
	 * 字符串生成base64字符串
	 * @param str
	 * @return
	 * @throws Exception
	 * @author zhanjie
	 */
	public static String base64Encoder(String str)
		throws Exception
	{
		 byte[] data=str.getBytes("UTF-8");    
		 BASE64Encoder base64Encoder = new BASE64Encoder();
		 String base64 = base64Encoder.encode(data);
		 return base64;
	}
	
	/**
	 * byte[] 生成base64字符串
	 * @param data byte数组
	 * @return
	 * @throws Exception
	 * @author zhanjie
	 */
	public static String base64Encoder(byte[] data)
		throws Exception {
		 BASE64Encoder base64Encoder = new BASE64Encoder();
		 String base64 = base64Encoder.encode(data);
		 return base64;
	}
	

}
