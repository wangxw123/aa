package com.reapal.inchannel.chinapay.service.impl;


import com.reapal.common.util.ReapalBase64Utils;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtils;
import com.reapal.common.util.WebUtils;
import com.reapal.inchannel.chinapay.model.IdentifyConfigChannel;
import com.reapal.inchannel.chinapay.service.ChinapayIdentifyService;
import com.reapal.inchannel.chinapay.utils.HttpRequest;
import com.reapal.inchannel.chinapay.utils.SignUtil;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by wanghao on 2016/12/11.
 */
@Service
public class ChinapayIdentifyServiceImpl implements ChinapayIdentifyService {

    private static Log log = LogFactory.getLog(ChinapayIdentifyServiceImpl.class);

    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

    private static String businessType = resourceBundle.getString("businessType");
    private static String acquirerCode = resourceBundle.getString("acquirerCode");


    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req) {
        long currDate = System.currentTimeMillis();
        String sha1CardNo = Sha1Utils.HexString(req.getCardNo());
        log.info("======【chinapay鉴权接口】======" + currDate + "-sha1CardNo-" + sha1CardNo + "----" + req.toBase64String());
        InchannelIdentifyResponse identifyResponse = new InchannelIdentifyResponse();
        //1、从缓存获取数据
        IdentifyConfigChannel identifyConfigChannel = null;

        identifyConfigChannel = new IdentifyConfigChannel();
        identifyConfigChannel.setUrl("https://bianmin.chinapay.com/UMPWeb/service/param");
        identifyConfigChannel.setAppSysId("90062");
        identifyConfigChannel.setKeyValue("FMXyZ6qfbsCmgK7MCK6fBqSeNJQtvHkm");

        //2、组装请求参数
        String cardNo = req.getCardNo();            //卡号
        String usrName = req.getOwner();            //姓名
        String cardPhone = req.getPhone();        //手机号
        String certType = req.getCertType();        //证件类型   01：身份证  02：军官证； 03：护照；04: 回乡证；05: 台胞证；06: 警官证；07: 士兵证；99: 其他证件
        if (StringUtils.isEmpty(certType)) certType = "01";
        String certNo = req.getCertNo();            //证件号
        String cardCvn2 = req.getCvv2();        //Cvn2
        if (StringUtils.isEmpty(cardCvn2)) cardCvn2 = "";
        String cardExpire = req.getValidthru();    //有效期
        if (StringUtils.isEmpty(cardPhone)) {
            cardPhone = "";
        }
        if (StringUtils.isEmpty(cardExpire)) {
            cardExpire = "";
        } else {
            cardExpire = convertNum(cardExpire);
        }

        String dcType = req.getDcType();            //借贷类型	0：借记卡    1：贷记卡
        if (StringUtils.isEmpty(dcType)) {
            dcType = "0";
        }


        identifyResponse.setChannelNo("1");
        identifyResponse.setCardNo(cardNo);
        identifyResponse.setOwner(usrName);
        identifyResponse.setCertType(certType);
        identifyResponse.setCertNo(certNo);
        identifyResponse.setPhone(cardPhone);
        identifyResponse.setCvv2(cardCvn2);
        identifyResponse.setValidthru(cardExpire);

        Map<String, String> map = new HashMap<String, String>();
        map.put("dcType", dcType);
        map.put("cardNo", cardNo);
        map.put("usrName", usrName);
        map.put("certType", certType);
        map.put("certNo", certNo);
        map.put("cardPhone", cardPhone);
        map.put("cardCvn2", cardCvn2);
        map.put("cardExpire", cardExpire);

        map.put("appSysId", identifyConfigChannel.getAppSysId());//应用系统编号
        map.put("validWayId", "2");// 认证方法
        map.put("bizType", "02");//产品类型
        map.put("save", "false");//是否保存
        map.put("mobile", cardPhone);//卡关联手机号
        map.put("usrSysId", "");//客户号
        map.put("email", "");//邮箱
        map.put("merId", "");//渠道商户号
        map.put("merName", "");//渠道商户名称
        map.put("pin", "");//卡密
        map.put("channelId", "");

        Set removeKey = new HashSet();
        removeKey.add("signMethod");
        removeKey.add("signature");

        String signedMsgs = SignUtil.getURLParam(map, true, removeKey);
        map.put("signature", SignUtil.sign(signedMsgs, identifyConfigChannel.getKeyValue()));
        map.put("signMethod", "MD5");

        //对usrName进行URLEncoding编码，放在签名后执行
        if (StringUtils.isNotEmpty(usrName)) {
            try {
                map.put("usrName", URLEncoder.encode(usrName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error("系统异常" + e.getClass().getName() + ":" + e.getMessage(), e);
            }
        }

        //3、签名-调用接口
        String getData = SignUtil.getURLParam(map, true, null);
        log.info("======【chinapay鉴权接口请求地址】======" + identifyConfigChannel.getUrl() + "-sha1CardNo-" + sha1CardNo + "-请求报文-密文-" + ReapalBase64Utils.encode(getData));
        String cpResMes = HttpRequest.sendPost(identifyConfigChannel.getUrl(), getData);

        //4、返回数据处理
        Map paramMap = SignUtil.parseResponse(cpResMes);
        log.info("======【chinapay鉴权接口返回】======" + "-sha1CardNo-" + sha1CardNo + "-返回报文-密文-" + ReapalBase64Utils.encode(paramMap.toString()));
        Set removeKeys = new HashSet();
        removeKeys.add("signMethod");
        removeKeys.add("signature");

        String signedMsg = SignUtil.getURLParam(paramMap, true, removeKeys);
        String verify = SignUtil.sign(signedMsg, identifyConfigChannel.getKeyValue());

        if (verify.equalsIgnoreCase((String) paramMap.get("signature"))) {
            String retCode = (String) paramMap.get("respcode");
            String retMsg = (String) paramMap.get("respmsg");
            identifyResponse.setChannelCode(retCode);
            identifyResponse.setChannelMsg(retMsg);


            if ("0000".equals(retCode)) {
                identifyResponse.setResult("1");
                identifyResponse.setResultCode("0000");
                identifyResponse.setResultMsg("鉴权成功");
                log.info("========银联鉴权CP鉴权成功========");
            } else {
                InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
                inchannelErrorCode.setChannelCode(retCode);
                inchannelErrorCode.setChannelMsg(retMsg);
                inchannelErrorCode.setBusinesstype(businessType);
                inchannelErrorCode.setAcquirerCode(acquirerCode);
                ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);
                if (resultErrorCode != null) {
                    identifyResponse.setResultCode(resultErrorCode.getResultCode());
                    identifyResponse.setResultMsg(resultErrorCode.getResultMsg());
                    identifyResponse.setResult(resultErrorCode.getStatus());
                } else {
                    log.info("========银联鉴权CP接口匹配不能匹配到错误码========");
                    identifyResponse.setResult("0");
                    identifyResponse.setResultCode("3067");
                    identifyResponse.setResultMsg("其它");
                }
            }
        } else {
            log.info("========CP返回结果验签失败========");
            identifyResponse.setResult("2");
            identifyResponse.setResultCode("2010");
            identifyResponse.setResultMsg("银行超时，请重试");
        }

        log.info("=========【chinapay鉴权返回值】==sha1CardNo=" + sha1CardNo + "----" + identifyResponse.toString() + "交易流水号:" + req.getTradeNo() + "消耗时间:" + (System.currentTimeMillis() - currDate));
        return identifyResponse;
    }

    /**
     * 有效期转换
     *
     * @param cardExpire
     * @return
     */
    private String convertNum(String cardExpire) {
        String top = cardExpire.substring(2, cardExpire.length());
        String last = cardExpire.substring(0, 2);
        return top + last;
    }


    public static void main(String[] args) throws Exception {
//        卡号：5358870003171652 姓名：张力 身份证号：450102198406071019 手机号：18577770607
        ChinapayIdentifyServiceImpl cp = new ChinapayIdentifyServiceImpl();
        InchannelIdentifyRequest req = new InchannelIdentifyRequest();
        req.setCardNo("36088414298649");
//        req.setPhone("15376997888");
        req.setCertNo("110101198407044536");
        req.setOwner("陈海波");
        req.setCertType("01");

//        工商银行    ICBC    36088310901353    衣文华   370686198201096129
//        工商银行    ICBC    36088414298649    陈海波   110101198407044536

//        刘欣峰 、身份证号：371082198712031311 、手机号：15376997888  、卡号：6223200623032434

        InchannelIdentifyResponse identify = cp.identify(req);
        System.out.print("identify:" + identify.toString());


        //cp.identify(req);
    }

}
