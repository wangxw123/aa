package com.reapal.inchannel.txskidentify.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext.xml", "classpath:spring/spring-provider.xml","classpath:spring/spring-consumer.xml"})
@Service
public class TianxingshukeidentifyServiceTest {

    @Autowired
    private TianxingshukeIdentifyService tianxingshukeIdentifyService;


    @Test
    public void crud() {


//        redisCacheService.set("12bb3", "a111111a",60, "api");
//        System.out.println("--------------------------------"+redisCacheService.get("12bb3","api"));
//        System.out.println("1111111111111111111111111111");

        InchannelIdentifyRequest req = new InchannelIdentifyRequest();
        req.setCardNo("6214830107222514");
        req.setCertNo("411323199104110511");
        //req.setCertType("01");
        req.setOwner("王浩");
        req.setPhone("15510333259");
//        req.setTradeNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        InchannelIdentifyResponse result = tianxingshukeIdentifyService.identify(req);
//        InchannelIdentifyResponse result = tianxingshukeIdentifyService.realName(req);
        //String  result = tianxingshukeIdentifyService.getPhoto(req);
        System.out.println(result);
    }


}
