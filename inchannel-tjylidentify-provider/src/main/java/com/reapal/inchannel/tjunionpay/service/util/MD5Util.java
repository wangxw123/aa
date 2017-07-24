package com.reapal.inchannel.tjunionpay.service.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * 
 * @author CuiXin
 *
 */
public class MD5Util {

	/**
	 * MD5加密
	 * 
	 * @param string 待加密字符串
	 * @param encoding 编码格式
	 * @return 加密结果
	 */
    public static String md5(String string, String encoding) {
        if (string == null) {
            return null;
        }

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(string.getBytes(encoding));
        } catch (NoSuchAlgorithmException e) {
        	System.out.println(e);
            return string;
        } catch (UnsupportedEncodingException e) {
        	System.out.println(e);
            return string;
        }

        byte[] byteArray = messageDigest.digest();
        
        return toHexString(byteArray);//
    }
    
    /**
     * 转换为十六进制字符串
     * 
     * @param byteArray 字节数组
     * @return 十六进制字符串
     */
    public static String toHexString(byte[] byteArray) {
        StringBuilder strBuilder = new StringBuilder();

//        for (int i = 0; i < byteArray.length; i++) {
//            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
//            	strBuilder.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
//            else
//            	strBuilder.append(Integer.toHexString(0xFF & byteArray[i]));
//        }
 	   int i;
 	   for (int offset = 0; offset < byteArray.length; offset++) {
 	    i = byteArray[offset];
 	    if (i < 0)
 	     i += 256;
 	    if (i < 16)
 	    	strBuilder.append("0");
 	   strBuilder.append(Integer.toHexString(i));
 	  }

        return strBuilder.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(md5("GBK0414530536000282200910000000000099172016031609460700交易成功20160316094607pj20160316094806v8gclae363b63a08108c80469d56510e80f4aa", "GBK"));
    }
}
