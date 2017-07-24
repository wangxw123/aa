package com.reapal.kaola.test;


import com.reapal.inchannel.kaola.service.KaolaIdentifyService;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
* Unit test for simple App.
* kaola
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/ApplicationContext.xml", "classpath:spring/spring-provider.xml", "classpath:spring/spring-consumer.xml"
})
public class AppTest

{
    @Autowired
    private KaolaIdentifyService kaolaIdentifyService;


    @Test
    public void crud(){
        //identifyTwo();
        //identifyTwoPic();
        //identifyThree();
        //identifyFour();
        //identifySix();
    }

    @Test
    public void identifyTwo(){
        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();

        identifyRequest.setOwner("袁玫");
       // identifyRequest.setPhone("18311233172");
        identifyRequest.setCertNo("41038119930427656X");
        //identifyRequest.setCardNo("6212260200089765230");
        InchannelIdentifyResponse response = kaolaIdentifyService.realName(identifyRequest);

        System.out.print("************************");
        System.out.println(response.toString());
    }

    public void identifyTwoPic(){
        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();
        String userName = "袁玫";
        String phone = "18311233172";
        identifyRequest.setCertNo("41038119930427656X");
        String certId = "41038119930427656X";
        String cardId = "6212260200089765230";
        Map map = kaolaIdentifyService.lakalaChannel(certId, userName, cardId, phone);
        System.out.print("************************");
        System.out.println(map.toString());
    }

    @Test
    public void identifyThree(){
        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();
        identifyRequest.setOwner("袁玫");
        //identifyRequest.setPhone("18311233172");
        identifyRequest.setCertNo("41038119930427656X");
        identifyRequest.setCardNo("6212260200089765230");
        InchannelIdentifyResponse response = kaolaIdentifyService.identify(identifyRequest);

        System.out.print("************************");
        System.out.println(response.toString());
    }
    @Test
    public void identifyFour(){
        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();
        identifyRequest.setOwner("袁玫");
        identifyRequest.setPhone("18311233172");
        identifyRequest.setCertNo("41038119930427656X");
        //identifyRequest.setCardNo("6212260200089765230");
        identifyRequest.setCardNo("6217000010066764658");

        InchannelIdentifyResponse response = kaolaIdentifyService.identify(identifyRequest);
        System.out.print("************************");
        System.out.println(response.toString());
    }

    public void identifySix(){
        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();
        identifyRequest.setOwner("袁玫");
        identifyRequest.setPhone("18311233172");
        identifyRequest.setCertNo("41038119930427656X");
        identifyRequest.setCardNo("4033910025806442");

        identifyRequest.setValidthru("1020");
        identifyRequest.setCvv2("850");
        InchannelIdentifyResponse response = kaolaIdentifyService.identify(identifyRequest);
        System.out.print("************************");
        System.out.println(response.toString());
    }
}
