package com.reapal.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AhzxidentifyMain {

    public static void main(String[] strings) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "classpath*:spring/ApplicationContext.xml","classpath*:spring/spring-provider.xml", "classpath*:spring/spring-consumer.xml"
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
