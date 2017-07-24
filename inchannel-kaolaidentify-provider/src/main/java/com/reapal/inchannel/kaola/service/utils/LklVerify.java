package com.reapal.inchannel.kaola.service.utils;

import java.util.Map;

import net.sf.json.JSONObject;

public class LklVerify {
	 /**
     * 拉卡拉消息验证地址
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.lakala.com/gateway.do?service=notify_verify&";
    
    /**
     * 验证消息是否是拉卡拉发出的合法消息，验证callback
     * @param result 通知返回来的参数数组
     * @return 验证结果
     */
    @SuppressWarnings("unchecked")
    public static boolean verifyReturn(String result) {
    	
//    	System.out.println("=====verifyReturn====" + result);
    	
    	Map<String,Object> resMap = JSONObject.fromObject(result);
 		if(!"000000".equals(resMap.get(LklDict.RETURNCODE))){
 			System.err.println("--------ReturnCode:"+ resMap.get(LklDict.RETURNCODE) +"--------—ReturnMsg:"+ resMap.get(LklDict.RETURNMSG));
 			return false;
 		}
	    //验证签名
 		return getSignVeryfy(resMap);

    }

    /**
     * 验证消息是否是拉卡拉发出的合法消息，验证服务器异步通知
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verifyNotify(Map<String, Object> params) throws Exception {
    	if(!"PS0000".equals(params.get(LklDict.RETURNCODE))){
 			System.err.println("--------ReturnCode:"+ params.get(LklDict.RETURNCODE) +"--------—ReturnMsg:"+ params.get(LklDict.RETURNMSG));
 			return false;
 		}
    	return getSignVeryfy(params);
    }
    
    /**
     * 根据反馈回来的信息，生成签名结果
     * @param resMap 通知返回来的参数数组
//     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	public static boolean getSignVeryfy(Map<String, Object> resMap) {
        //获得签名验证结果
        boolean isSign = false;
        if(SignConfig.SIGNTYPE.equals(SignConfig.MD5) ) {
//        	isSign = MD5.verify(preSignStr, sign, SignConfig.KEY, LklConfig.INPUT_CHARSET);
        }
        if(SignConfig.SIGNTYPE.equals(SignConfig.RSA)){
    		//验签
        	isSign = RSA.verify(String.valueOf(resMap.get(LklDict.RESDATA)),String.valueOf(resMap.get(LklDict.SIGN)), SignConfig.LKL_PUBLIC_KEY);
        }
        return isSign;
    }

}
