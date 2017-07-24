package com.reapal.inchannel.tjunionpay.service.util;

import java.lang.reflect.Field;

/**
 * 交易的报文工具类
 * 
 * @author CuiXin
 *
 */
public class TransMsgUtil {

	/**
	 * 生成md5摘要
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String genMd5ConSec(Object obj) throws Exception {
		if(null == obj) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for(Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if("md5ConSec".equals(field.getName())) {
				continue;
			}
			if(null == field.get(obj) || "null".equals(field.get(obj))) {
				sb.append("");
			} else {
				String value = (String) field.get(obj);
				value = value.trim();
				String temp;
				try {
					temp = String.valueOf(value);
				} catch (NumberFormatException e) {
					temp = "";
				}
				sb.append(temp);
			}
		}
		String forEncodeStr = sb.toString() + Constants.MD5_KEY;
//		System.out.println("待MD5加密的字符串" + forEncodeStr);
		String result = MD5Util.md5(forEncodeStr, Constants.ENCODING_GBK);
		
		return result;
	}
	
	/**
	 * 为数字字符串左填充0
	 * 
	 * @param str 原字符串
	 * @param strLength 需要填充的总长度
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
              sb = new StringBuffer();
              sb.append("0").append(str);
              str = sb.toString();
              strLen = str.length();
        }
        return str;
    }
	
	public static String appendLeft(String str, String addStr, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
              sb = new StringBuffer();
              sb.append(addStr).append(str);
              str = sb.toString();
              strLen = str.length();
        }
        return str;
	}
	
	
}
