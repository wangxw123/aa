package com.reapal.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangg on 2015/10/8.
 */
public class BankCodeUtils {


    public static String getBankNameByCode(String bankCode){
        Map<String,String> map = new HashMap<String, String>();
        map.put("BCCB","北京银行");
        map.put("ICBC","工商银行");
        map.put("CEB","光大银行");
        map.put("CGB","广发银行");
        map.put("HXB","华夏银行");
        map.put("CCB","建设银行");
        map.put("BOCM","交通银行");
        map.put("CMBC","民生银行");
        map.put("ABC","农业银行");
        map.put("PAYH","平安银行");
        map.put("SDB","平安银行");
        map.put("SHBANK","上海银行");
        map.put("CIB","兴业银行");
        map.put("PSBC","邮储银行");
        map.put("BOC","中国银行");
        map.put("CITIC","中信银行");
        map.put("CMB","招商银行");
        map.put("SPDB","浦发银行");
        return map.get(bankCode);
    }

    public static String getBankCodeByCode(String bankCode) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("BCCB","04031000");
        map.put("ICBC","01020000");
        map.put("CEB","03030000");
        map.put("CGB","03060000");
        map.put("HXB","03040000");
        map.put("CCB","01050000");
        map.put("BOCM","03010000");
        map.put("CMBC","03050000");
        map.put("ABC","01030000");
        map.put("PAYH","04100000");
        map.put("SDB","04100000");
        map.put("SHBANK","04010000");
        map.put("CIB","03090000");
        map.put("PSBC","01000000");
        map.put("BOC","01040000");
        map.put("CITIC","03020000");
        map.put("CMB","03080000");
        map.put("SPDB","03100000");
        return map.get(bankCode);
    }

}
