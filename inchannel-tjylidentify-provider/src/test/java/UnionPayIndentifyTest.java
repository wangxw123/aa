//import com.reapal.inchannel.model.InchannelIdentifyRequest;
//import com.reapal.inchannel.tjunionpay.service.TianjinUnionPayIdentifyService;
//import com.reapal.inchannel.tjunionpay.service.util.TianjinUnionPayDyncKey;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
//* Created by Administrator on 2016/11/24.
//*/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/ApplicationContext.xml", "classpath:spring/spring-provider.xml", "classpath:spring/spring-consumer.xml"})
//public class UnionPayIndentifyTest {
//
//
//    @Autowired
//    private TianjinUnionPayIdentifyService tianjinUnionPayIdentifyService;
//
//
//    @Autowired
//    private TianjinUnionPayDyncKey tianjinUnionPayDyncKey;
//    @Test
//    public void test1()  {
//       //redisCacheService.del("isExpire", "tianjianunionpy");
//        System.out.println("====================================");
//        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();
//        identifyRequest.setOwner("袁玫");
//        identifyRequest.setCardNo("6226220125778331");
//        identifyRequest.setCertNo("41038119930427656X");
//        InchannelIdentifyRequest dentifyRequest  = tianjinUnionPayIdentifyService.identify(identifyRequest);
//        Assert.assertNotNull(dentifyRequest);
//    }
//
//
//    @Test
//    public void test2() {
//        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();
//        identifyRequest.setOwner("袁玫");
//        identifyRequest.setCertNo("41038119930427656X");
//        InchannelIdentifyRequest dentifyRequest  = tianjinUnionPayIdentifyService.realName(identifyRequest);
//        Assert.assertNotNull(dentifyRequest);
//    }
//
//    //@Test
///*    public void delKey () {
//        redisCacheService.del("isExpire", "tianjianunionpy");
//    }*/
//}
//
