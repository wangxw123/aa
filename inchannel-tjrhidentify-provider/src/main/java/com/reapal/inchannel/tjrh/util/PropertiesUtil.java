package com.reapal.inchannel.tjrh.util;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 读取配置文件
 * Created by guoguangxiao on 16/12/20.
 */
public class PropertiesUtil {

    public static PropertiesUtil instance;
    public static ResourceBundle rbConfig = null;

    private PropertiesUtil(){
        readResourcesconfig(null);
    }

    private PropertiesUtil(String fileName) {
        readResourcesconfig(fileName);
    }

    public static ResourceBundle getResourcesconfig() {
        if(instance == null){
            instance = new PropertiesUtil();
        }
        return rbConfig;
    }


    public static ResourceBundle getResourcesconfig(String fileName) {
        if(instance == null){
            instance = new PropertiesUtil(fileName);
        }
        return rbConfig;
    }


    /**
     * 获取配置文件
     * @param fileName
     */
    private void readResourcesconfig(String fileName) {
        if(fileName == null || "".equals(fileName.trim())){
            fileName = "application";
        }
        rbConfig = PropertyResourceBundle.getBundle(fileName);
    }

}
