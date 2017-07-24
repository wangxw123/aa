package com.reapal.inchannel.cfcaidentify.utils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.*;

/**
 * 
 * @author guoj
 *
 */
public class SignUtil {

	/**
	 * 解析参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map parseParam(HttpServletRequest request) {
		Map paramsMap = new HashMap();
		Enumeration paramsEnum = request.getParameterNames();
		while (paramsEnum.hasMoreElements()) {
			String paramName = (String) paramsEnum.nextElement();
			String paramValue = request.getParameter(paramName);
			paramsMap.put(paramName, paramValue);
		}
		return paramsMap;
	}

	/**
	 * 应答数据解析成map
	 * 
	 * @param msg
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map parseResponse(String msg) {
		Map paramsMap = new HashMap();
		int beginIndex = 0;
		int endIndex = 0;
		while (true) {
			// 解析参数名
			endIndex = msg.indexOf("=", beginIndex);
			String key = null;
			String value = null;
			if (endIndex > beginIndex) {
				key = msg.substring(beginIndex, endIndex);
			} else {
				break;
			}
			// 解析参数值
			beginIndex = endIndex + 1;
			endIndex = msg.indexOf("&", beginIndex);
			if (endIndex >= beginIndex) {
				value = msg.substring(beginIndex, endIndex);
				paramsMap.put(key, value);
				// 移动游标
				beginIndex = endIndex + 1;
			} else {
				value = msg.substring(beginIndex);
				paramsMap.put(key, value);
				break;
			}
		}
		return paramsMap;
	}

	/**
	 * 转换成url参数
	 * 
	 * @param map
	 * @param isSort
	 *            是否排序
	 * @param removeKey
	 *            不包含的key元素集
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getURLParam(Map map, boolean isSort, Set removeKey) {
		StringBuffer param = new StringBuffer();
		List msgList = new ArrayList();
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = (String) map.get(key);
			if (removeKey != null && removeKey.contains(key)) {
				continue;
			}
			msgList.add(key + "=" + value);
		}

		if (isSort) {
			// 排序
			Collections.sort(msgList);
		}

		for (int i = 0; i < msgList.size(); i++) {
			String msg = (String) msgList.get(i);
			if (i > 0) {
				param.append("&");
			}
			param.append(msg);
		}

		return param.toString();
	}

	/**
	 * 签名
	 * 
	 * @param signedMsg
	 * @param key
	 * @return
	 */
	public static String sign(String signedMsg, String key) {
		try {
			MessageDigest digit = MessageDigest.getInstance("MD5");
			digit.update((signedMsg + key).getBytes("UTF-8"));
			return byte2hex(digit.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * byte array to hex
	 * 
	 * @param b
	 *            byte array
	 * @return hex string
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp;
		for (int i = 0; i < b.length; i++) {
			stmp = Integer.toHexString(b[i] & 0xFF).toUpperCase();
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString();
	}

}
