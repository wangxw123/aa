/*
package com.reapal.inchannel.szylidentify.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


*/
/**
 * Created by Administrator on 2017/3/29.
 *//*





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/ApplicationContext.xml", "classpath*:spring/spring-consumer.xml","classpath*:spring/ApplicationContext.xml"
})
public class SzylTest {

    @Autowired
    SzylIdentifyService szylIdentifyService;

    @Test
    public void testIdentify(){
        InchannelIdentifyRequest request=new InchannelIdentifyRequest();
        request.setCardNo("6214830165802108");  //6225260026501607
        request.setCertNo("412722199310223521");
        request.setOwner("孙晨杨");

        request.setPhone("18310517109");
   //     szylIdentifyService.realName(request);
        szylIdentifyService.identify(request);
    }
}
*/
