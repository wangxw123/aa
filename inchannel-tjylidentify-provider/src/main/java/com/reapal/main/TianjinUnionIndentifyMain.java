package com.reapal.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/11/24.
 */
public class TianjinUnionIndentifyMain {

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
