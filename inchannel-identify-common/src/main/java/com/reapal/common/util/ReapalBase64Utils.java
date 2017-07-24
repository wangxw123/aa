package com.reapal.common.util;


import org.apache.commons.codec.binary.Base64;

/**
 * Created by wanghao on 2017/1/12.
 */
public class ReapalBase64Utils {

    /**
     * 加密
     *
     * @param plain
     * @return
     */
    public static String encode(String plain) {
        String encodeResult = "";
        if(StringUtils.isNotEmpty(plain)){
            byte[] plainBytes = plain.getBytes();
            Base64 base64 = new Base64();
            plainBytes = base64.encode(plainBytes);
            encodeResult = new String(plainBytes);
        }
        return encodeResult;
    }


    /**
     * 解密
     *
     * @param base64Str
     * @return
     */
    public static String decode(String base64Str) {
        String decodeResult = "";
        if(StringUtils.isNotEmpty(base64Str)){
            byte[] plainBytes = base64Str.getBytes();
            Base64 base64 = new Base64();
            plainBytes = base64.decode(plainBytes);
            decodeResult = new String(plainBytes);
        }
        return decodeResult;
    }

}
