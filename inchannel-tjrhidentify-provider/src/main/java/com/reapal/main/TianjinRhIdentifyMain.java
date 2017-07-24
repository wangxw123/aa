package com.reapal.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guoguangxiao on 17/1/23.
 */
public class TianjinRhIdentifyMain {
    public static void main(String[] strings) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "classpath*:spring/spring-provider.xml", "classpath*:spring/spring-consumer.xml","classpath*:spring/ApplicationContext.xml"
        });
        context.start();

        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
