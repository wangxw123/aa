//package com.reapal.inchannel.ahzxidentify.service;
//
//import com.reapal.inchannel.model.InchannelIdentifyRequest;
//import com.reapal.inchannel.model.InchannelIdentifyResponse;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
//* Created by Administrator on 2017/3/20.
//*/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//        "classpath:spring/ApplicationContext.xml",
//        "classpath:spring/spring-consumer.xml",
//        "classpath:spring/spring-provider.xml"
//})
//public class AhzxIdentifyServiceTest {
//    @Autowired
//    private AhzxIdentifyService ahzxIdentifyService;
//
//    @Test
//    public void identify() {
//        InchannelIdentifyRequest request = new InchannelIdentifyRequest();
//        //            袁玫
////            18311233172
////            41038119930427656x
////
////            民生银行：    6226220125778331
//        request.setOwner("袁玫");
//        request.setCardNo("6226220125778331");
//        request.setPhone("18311233172");
//        request.setCertNo("41038119930427656x");
//        InchannelIdentifyResponse response = ahzxIdentifyService.identify(request);
//        System.out.println("++++++++++++====="+response.toString());
//
//    }
//}
//
