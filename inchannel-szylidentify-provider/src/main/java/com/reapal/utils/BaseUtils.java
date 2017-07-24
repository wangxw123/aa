package com.reapal.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/29.
 */
public class BaseUtils {


    /**
     * 拼接请求参数
     * @param host
     * @param mercode
     * @return
     */
    public static String createRealnameAuthUrl(String host, String mercode) {
        return "" + host + "/easserver/gateway/1/realNameVerify/"+ mercode;
    }

    public static  String createTranId(){
        String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int serno = (int) (100000000+Math.random()*900000000) ;//测试使用随机数 商户建议使用序列号 防止重复
        return day+serno;
     }

}
