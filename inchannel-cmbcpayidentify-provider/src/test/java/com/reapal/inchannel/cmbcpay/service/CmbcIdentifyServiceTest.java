/*
package com.reapal.inchannel.cmbcpay.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/ApplicationContext.xml", "classpath*:spring/spring-consumer.xml","classpath*:spring/ApplicationContext.xml"
})
public class CmbcIdentifyServiceTest {

    @Autowired
    CmbcpayIdentifyService cmbcpayIdentifyService;

    @Test
    public void crud() {
        InchannelIdentifyRequest req = new InchannelIdentifyRequest();


        String strJson = "{\"users\":["+
                //信用卡
                //"{\"sh\":\"融宝测试-兴业信用卡\",\"card\":\"5240707982330119\",\"name\":\"仲金丹\",\"phone\":\"13401123943\",\"cvn2\":\"019\",\"expire\":\"1602\",\"sfz\":\"230802198710081323\"},"+
                //"{\"sh\":\"融宝测试-招商信用卡\",\"card\":\"6225768382049946\",\"name\":\"仲金丹\",\"phone\":\"13401123943\",\"cvn2\":\"206\",\"expire\":\"1703\",\"sfz\":\"230802198710081323\"},"+
                //"{\"sh\":\"融宝测试-招商信用卡\",\"card\":\"6225758381375319\",\"name\":\"黄刚\",\"phone\":\"13401123943\",\"cvn2\":\"379\",\"expire\":\"1509\",\"sfz\":\"230802198710081323\"},"+
                "{\"sh\":\"融宝测试-平安信用卡\",\"card\":\"6225260026501607\",\"name\":\"仲金丹\",\"phone\":\"13401123943\",\"cvn2\":\"060\",\"expire\":\"0220\",\"sfz\":\"230802198710081323\"},"+
                //"{\"sh\":\"融宝测试-广发信用卡\",\"card\":\"5201521373730762\",\"name\":\"仲金丹\",\"phone\":\"13401123943\",\"cvn2\":\"287\",\"expire\":\"1702\",\"sfz\":\"230802198710081323\"},"+
                //"{\"sh\":\"融宝测试-建设信用卡\",\"card\":\"5324505103310161\",\"name\":\"于景鑫\",\"phone\":\"13436319934\",\"cvn2\":\"891\",\"expire\":\"1609\",\"sfz\":\"21102219870912003X\"},"+
                //"{\"sh\":\"融宝测试-民生信用卡\",\"card\":\"6226011022599072\",\"name\":\"罗辉煌\",\"phone\":\"13811450632\",\"cvn2\":\"499\",\"expire\":\"1607\",\"sfz\":\"431123198610201529\"},"+
                //"{\"sh\":\"融宝测试-光大信用卡\",\"card\":\"4062522702064361\",\"name\":\"乔赛男\",\"phone\":\"13651298745\",\"cvn2\":\"982\",\"expire\":\"1503\",\"sfz\":\"110223198601101446\"},"+
                //"{\"sh\":\"融宝测试-工行信用卡\",\"card\":\"5288560033768602\",\"name\":\"刘笑\",\"phone\":\"13718041777\",\"cvn2\":\"499\",\"expire\":\"1902\",\"sfz\":\"230803198610080813\"},"+
                //"{\"sh\":\"融宝测试-中信信用卡\",\"card\":\"4033930006767603\",\"name\":\"刘笑\",\"phone\":\"13718041777\",\"cvn2\":\"176\",\"expire\":\"1603\",\"sfz\":\"230803198610080813\"},"+
                //借记卡
                //"{\"sh\":\"融宝测试-招商借记卡\",\"card\":\"6225880136947026\",\"name\":\"仲金丹\",\"phone\":\"13401123943\",\"sfz\":\"230802198710081323\"},"+
                //"{\"sh\":\"融宝测试-交行储蓄卡\",\"card\":\"6222620910007084126\",\"name\":\"贾利娟\",\"phone\":\"13552550781\",\"sfz\":\"410725199203122028\"},"+

                "]}";

        JSONObject json= JSON.parseObject(strJson);
        JSONArray jsonArray=json.getJSONArray("users");
        String resultCode="";
        String resultMsg="";
        InchannelIdentifyResponse result = null;
        for(int i=0;i<jsonArray.size();i++){
            JSONObject user=(JSONObject) jsonArray.get(i);
            req.setCardNo((String) user.get("card"));
            req.setOwner((String) user.get("name"));
            req.setPhone((String) user.get("phone"));
            req.setCertNo((String) user.get("sfz"));
            req.setDcType("1");
            if(req.getPhone()!=null || req.getPhone()!=""){
                result = cmbcpayIdentifyService.identify(req);
                resultCode = result.getResultCode();
                resultMsg = result.getResultMsg();
            }
            else{
                resultCode = "-1";
                resultMsg = "手机号为空";
            }

            System.out.println((String) user.get("sh") + "\t" + req.getOwner() + "\t" + req.getCardNo() + "\t" + req.getPhone() + "\t" + req.getCertNo() + "\t" + resultCode + "\t" + resultMsg);

        }

        req.setCardNo("6222800014781009896");
    //    req.setCardPhone("13681466208");
        req.setCertNo("132124197702270179");
     //   req.setUsrName("滕飞");

        req.setCardNo("622908583257249713");
     //   req.setCardPhone("13180830511");
        req.setCertNo("220124196712171428");
     //   req.setUsrName("王雪艳");
        //cp.identify(req);
    }


//    InchannelIdentifyRequest{channelNo='null', bankNo='null', bankCode='ICBC', bankName='null', owner='5buW54+j', cardNo='NjI1ODU5MDAzMTE4NjMwMw==', certType='01', certNo='MjEwMzA0MTk4NzExMjYxNjM1', phone='MTg1MDAwMjE3ODQ=', validthru='', cvv2='', merchantId='null', tradeNo='null', userIP='null', userId='null', notifyUrl='null', amount=0, productname='null', dcType='1'}

}
*/
