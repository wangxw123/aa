package com.reapal.common.util;

import java.security.MessageDigest;

/**
 * Created by wanghao on 2017/1/12.
 */
public class Sha1Utils {
    public static String HexString(String content) {
        String result = "";
        try {
            MessageDigest digest2 = MessageDigest.getInstance("SHA-1", "SUN");
            byte[] o2 = digest2.digest(content.getBytes());
            result = printHexString(o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String printHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
}
