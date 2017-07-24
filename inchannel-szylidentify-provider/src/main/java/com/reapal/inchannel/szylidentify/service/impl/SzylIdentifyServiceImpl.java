package com.reapal.inchannel.szylidentify.service.impl;

import com.alibaba.fastjson.JSON;
import com.reapal.common.util.ReapalBase64Utils;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.inchannel.szylidentify.service.SzylIdentifyService;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import com.reapal.utils.BaseUtils;
import com.reapal.utils.CertificateUtils;
import com.reapal.utils.EncryptUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/3/29.
 */
@Service
public class SzylIdentifyServiceImpl implements SzylIdentifyService {

    private static Logger logger = LoggerFactory.getLogger(SzylIdentifyServiceImpl.class);
    private ResourceBundle para = ResourceBundle.getBundle("application");
    public   String KEYPATH = para.getString("KEYPATH");
    public   String PASSWD =para.getString("PASSWD");

    public String PUBLICKEY=para.getString("PUBLICKEY");
    public  String mercode=para.getString("mercode");
    public  String host=para.getString("host");

    public  String acquirerCode=para.getString("acquirerCode");
    public  String businessType=para.getString("businessType");
    public  String identifyProductId=para.getString("identifyProductId");
    public  String realNameProductId=para.getString("realNameProductId");

    @Autowired
    CashierInchannelCodeService cashierInchannelCodeService;

    @Override
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req) {

        long beginTime = System.currentTimeMillis();
        logger.info("[RBRequestParameter] - [{}] - [{}] - [{}] - [{}] - body={} ", "identify", "identify", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), req.toBase64String());

        InchannelIdentifyResponse inchannelIdentifyResponse=new InchannelIdentifyResponse();

        String subscriberName =  req.getOwner();
        String identificationNo = req.getCertNo();


        String phoneNo =  req.getPhone();
        String accountNo =  req.getCardNo();
        String dcType = req.getDcType();
        inchannelIdentifyResponse.setOwner(subscriberName);
        inchannelIdentifyResponse.setPhone(phoneNo);
        inchannelIdentifyResponse.setCertNo(identificationNo);
        inchannelIdentifyResponse.setCardNo(accountNo);

        Map<String,String> param=new TreeMap<String, String>();
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try{
            param.put("tranId",BaseUtils.createTranId());
            param.put("umsAcctType","1");   //默认为1到电子账户
            param.put("acctNo",accountNo);
            param.put("acctName",subscriberName);
            param.put("certNo",req.getCertNo());
            param.put("tranTime",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

            if(StringUtils.isNotEmpty(phoneNo)){
                logger.info("渠道{}，银行卡号{}，【四要素鉴权开始！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                //四要素
                param.put("verifyType","0040");
                param.put("phone",phoneNo);
            }else{
                logger.info("渠道{}，银行卡号{}，【三要素鉴权开始！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                //三要素
                param.put("verifyType","0030");
            }
            logger.info("渠道{}，银行卡号{}，【签名开始！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
            //获取请求参数
            String requestParam= this.createSign(param,req.getCardNo());

            logger.info("渠道{}，银行卡号{}，发送银行明文requestParam={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), ReapalBase64Utils.encode(requestParam));

            // 创建httppost
            HttpPost httppost = new HttpPost(BaseUtils.createRealnameAuthUrl(host,mercode));

            StringEntity entity = new StringEntity(requestParam, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httppost.setEntity(entity);

//            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                if(response !=null){
                    logger.info("渠道{}，银行卡号{}，【深圳银联实名认证渠道成功响应！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    HttpEntity entity1 = response.getEntity();

                    if (entity1 != null) {
                        String result = EntityUtils.toString(entity1, "UTF-8");
                        logger.info("渠道{}，银行卡号{}，银行返回明文collPayTransRspMsg={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), ReapalBase64Utils.encode(result));
                        //将返回的参数封装成map集合
                        Map responseParam=JSON.parseObject(result);

                        String channelCode=(String) responseParam.get("respCode");
                        String channelMsg=(String) responseParam.get("respMsg");
                        logger.info("渠道{}，银行卡号{}，银行返回结果 Msg={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), "channelCode = "+channelCode+" channelMsg = "+channelMsg);

                        if(verify(result)){
                            logger.info("渠道{}，银行卡号{}，【签名验证成功！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                            if ("0000".equals(channelCode)) {
                                logger.info("渠道{}，银行卡号{}，鉴权成功！", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                                inchannelIdentifyResponse.setResultCode("0000");
                                inchannelIdentifyResponse.setResultMsg("鉴权成功");
                                inchannelIdentifyResponse.setResult("1");
                                inchannelIdentifyResponse.setChannelCode(channelCode);
                                inchannelIdentifyResponse.setChannelMsg(channelMsg);
                            }else{
                                logger.info("渠道{}，银行卡号{}，【鉴权失败！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                                inchannelIdentifyResponse.setChannelCode(channelCode);
                                inchannelIdentifyResponse.setChannelMsg(channelMsg);
                                InchannelIdentifyResponse identifyResponse = this.packResult(channelCode, channelMsg,identifyProductId,req.getCardNo());

                                logger.info("渠道{}，银行卡号{}，【匹配错误码！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                                inchannelIdentifyResponse.setResultCode(identifyResponse.getResultCode());
                                inchannelIdentifyResponse.setResultMsg(identifyResponse.getResultMsg());
                                inchannelIdentifyResponse.setResult(identifyResponse.getResult());

                            }
                        }else{
                            logger.info("渠道{}，银行卡号{}，【签名验证失败！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                            inchannelIdentifyResponse.setChannelCode(channelCode);
                            inchannelIdentifyResponse.setChannelMsg(channelMsg);
                            InchannelIdentifyResponse identifyResponse = this.packResult(channelCode, channelMsg,identifyProductId,req.getCardNo());

                            logger.info("渠道{}，银行卡号{}，【匹配错误码！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                            inchannelIdentifyResponse.setResultCode(identifyResponse.getResultCode());
                            inchannelIdentifyResponse.setResultMsg(identifyResponse.getResultMsg());
                            inchannelIdentifyResponse.setResult(identifyResponse.getResult());
                        }

                    }else{
                        logger.info("渠道{}，银行卡号{}，【银行返回明文为空！】 ", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    }
                }else{
                    logger.info("渠道{}，银行卡号{}，【深圳银联实名认证渠道响应失败！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                }

                if (StringUtils.isEmpty(inchannelIdentifyResponse.getResultCode())) {
                    logger.info("渠道{}，银行卡号{}，【返回编码为空，设置返回编码！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    inchannelIdentifyResponse.setResult("2");
                    inchannelIdentifyResponse.setResultCode("1049");
                }
                if (StringUtils.isEmpty(inchannelIdentifyResponse.getResultMsg())) {
                    logger.info("渠道{}，银行卡号{}，【返回鉴权信息为空，设置返回信息！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    inchannelIdentifyResponse.setResultMsg("银行超时，请稍后再试");
                }

            }catch(Exception e){
                logger.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "identify", "identify", e.getMessage(), ReapalBase64Utils.encode(req.getCardNo()),e);
                inchannelIdentifyResponse.setResult("2");
                inchannelIdentifyResponse.setResultCode("1049");
                inchannelIdentifyResponse.setResultMsg("银行超时，请稍后再试");
            }
            finally {
                response.close();
            }

        }catch (Exception e){
            logger.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "identify", "identify", e.getMessage(), ReapalBase64Utils.encode(req.getCardNo()),e);
            inchannelIdentifyResponse.setResultCode("1049");
            inchannelIdentifyResponse.setResultMsg("银行超时，请稍后再试");
        }finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "identify", "identify", e.getMessage(),ReapalBase64Utils.encode(req.getCardNo()),e);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("[RBResponseParameter] - [{}] - [{}] - [{}] - [{}] - cardhash:[{}] - cardType:[{}] - bank:[{}] - channel:[{}] - returnCode:[{}] - returnMsg:[{}] - duration:[{}] - body={} ",
                "identify", "identify", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), Sha1Utils.HexString(req.getCardNo()), req.getCertType(), req.getBankCode(), acquirerCode, inchannelIdentifyResponse.getResultCode(), inchannelIdentifyResponse.getResultMsg(), (endTime - beginTime), inchannelIdentifyResponse.toBase64String());
        return inchannelIdentifyResponse;
    }

    @Override
    public InchannelIdentifyResponse realName(InchannelIdentifyRequest req) {
        long beginTime = System.currentTimeMillis();
        logger.info("[RBRequestParameter] - [{}] - [{}] - [{}] - [{}] - body={} ", "realName", "realName", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), req.toBase64String());

        logger.info("渠道{}，银行卡号{}，【鉴权二要素开始！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
        InchannelIdentifyResponse inchannelIdentifyResponse=new InchannelIdentifyResponse();

        String subscriberName =  req.getOwner();
        String identificationNo = req.getCertNo();

        String phoneNo =  req.getPhone();
        String accountNo =  req.getCardNo();
        String dcType = req.getDcType();
        inchannelIdentifyResponse.setOwner(subscriberName);
        inchannelIdentifyResponse.setPhone(phoneNo);
        inchannelIdentifyResponse.setCertNo(identificationNo);
        inchannelIdentifyResponse.setCardNo(accountNo);

        Map<String,String> param=new TreeMap<String, String>();
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try{
            param.put("tranId",BaseUtils.createTranId());
            param.put("umsAcctType","1");   //默认为1到电子账户
            param.put("acctNo",accountNo);
            param.put("tranTime",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

            if(StringUtils.isNotEmpty(subscriberName)){
                logger.info("渠道{}，银行卡号{}， 【鉴权二要素 卡号+户名！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                // 二要素 卡号+户名
                param.put("verifyType","0020");
                param.put("acctName",subscriberName);
            }else if (StringUtils.isNotEmpty(identificationNo)){
                logger.info("渠道{}，银行卡号{}，【鉴权二要素 卡号+证件！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                //二要素 卡号+证件
                param.put("certNo",identificationNo);
                param.put("verifyType","0021");
            }else{
                logger.info("渠道{}，银行卡号{}，【鉴权二要素 卡号+手机号！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                //二要数 卡号+手机号
                param.put("phone",phoneNo);
                param.put("verifyType","0022");
            }
            //获取请求参数
            String requestParam= this.createSign(param,req.getCardNo());

            logger.info("渠道{}，银行卡号{}，发送银行明文requestParam={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), ReapalBase64Utils.encode(requestParam));

            // 创建httppost
            HttpPost httppost = new HttpPost(BaseUtils.createRealnameAuthUrl(host,mercode));

            StringEntity entity = new StringEntity(requestParam, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httppost.setEntity(entity);


            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                if(response !=null){
                    logger.info("渠道{}，银行卡号{}，【深圳银联实名认证渠道成功响应！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    HttpEntity entity1 = response.getEntity();
                    if (entity1 != null) {
                        String result = EntityUtils.toString(entity1, "UTF-8");
                        logger.info("渠道{}，银行卡号{}，银行返回密文 result={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), ReapalBase64Utils.encode(result));
                        //将返回的参数封装成map集合
                        Map responseParam=JSON.parseObject(result);

                        String channelCode=(String) responseParam.get("respCode");
                        String channelMsg=(String) responseParam.get("respMsg");

                        logger.info("渠道{}，银行卡号{}，银行返回结果 result={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()),"channelCode = "+channelCode+" channelMsg = "+channelMsg);
                        if (verify(result)){
                             logger.info("渠道{}，银行卡号{}，【签名验证成功！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));

                            if ("0000".equals(channelCode)) {
                                logger.info("渠道{}，银行卡号{}，鉴权成功！", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                                inchannelIdentifyResponse.setResultCode("0000");
                                inchannelIdentifyResponse.setResultMsg("鉴权成功");
                                inchannelIdentifyResponse.setResult("1");
                                inchannelIdentifyResponse.setChannelCode(channelCode);
                                inchannelIdentifyResponse.setChannelMsg(channelMsg);
                            }else{
                                logger.info("渠道{}，银行卡号{}，【鉴权失败！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                                inchannelIdentifyResponse.setChannelCode(channelCode);
                                inchannelIdentifyResponse.setChannelMsg(channelMsg);
                                InchannelIdentifyResponse identifyResponse = this.packResult(channelCode, channelMsg ,realNameProductId,req.getCardNo());

                                logger.info("渠道{}，银行卡号{}，【匹配错误码！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                                inchannelIdentifyResponse.setResultCode(identifyResponse.getResultCode());
                                inchannelIdentifyResponse.setResultMsg(identifyResponse.getResultMsg());
                                inchannelIdentifyResponse.setResult(identifyResponse.getResult());


                            }
                        }else{
                            logger.info("渠道{}，银行卡号{}，【签名验证失败！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                            inchannelIdentifyResponse.setChannelCode(channelCode);
                            inchannelIdentifyResponse.setChannelMsg(channelMsg);
                            InchannelIdentifyResponse identifyResponse = this.packResult(channelCode, channelMsg ,realNameProductId,req.getCardNo());

                            logger.info("渠道{}，银行卡号{}，【匹配错误码！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                            inchannelIdentifyResponse.setResultCode(identifyResponse.getResultCode());
                            inchannelIdentifyResponse.setResultMsg(identifyResponse.getResultMsg());
                            inchannelIdentifyResponse.setResult(identifyResponse.getResult());
                        }

                    }else{
                        logger.info("渠道{}，银行卡号{}，【银行返回明文为空！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    }
                }else{
                    logger.info("渠道{}，银行卡号{}，【深圳银联实名认证渠道响应失败！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                }

                if (StringUtils.isEmpty(inchannelIdentifyResponse.getResultCode())) {
                    logger.info("渠道{}，银行卡号{}，【返回编码为空，设置返回编码！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    inchannelIdentifyResponse.setResult("2");
                    inchannelIdentifyResponse.setResultCode("1049");
                }
                if (StringUtils.isEmpty(inchannelIdentifyResponse.getResultMsg())) {
                    logger.info("渠道{}，银行卡号{}，【返回鉴权信息为空，设置返回信息！】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    inchannelIdentifyResponse.setResultMsg("银行超时，请稍后再试");
                }

            }catch(Exception e){
                logger.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "realName", "realName", e.getMessage(), req.getCardNo(),e);
                inchannelIdentifyResponse.setResult("2");
                inchannelIdentifyResponse.setResultCode("1049");
                inchannelIdentifyResponse.setResultMsg("银行超时，请稍后再试");
            }
            finally {
                response.close();
            }

        }catch (Exception e){
            logger.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "realName", "realName", e.getMessage(), ReapalBase64Utils.encode(req.getCardNo()),e);
            inchannelIdentifyResponse.setResultCode("1049");
            inchannelIdentifyResponse.setResultMsg("银行超时，请稍后再试");
        }finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "realName", "realName", e.getMessage(), ReapalBase64Utils.encode(req.getCardNo()),e);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("[RBResponseParameter] - [{}] - [{}] - [{}] - [{}] - cardhash:[{}] - cardType:[{}] - bank:[{}] - channel:[{}] - returnCode:[{}] - returnMsg:[{}] - duration:[{}] - body={} ",
                "realName", "realName", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), Sha1Utils.HexString(req.getCardNo()), req.getCertType(), req.getBankCode(), acquirerCode, inchannelIdentifyResponse.getResultCode(), inchannelIdentifyResponse.getResultMsg(), (endTime - beginTime), inchannelIdentifyResponse.toBase64String());
        return inchannelIdentifyResponse;

    }


    /***
     *  获取签名信息并封装请求参数
     * @param param
     * @return
     * @throws Exception
     */
    public String createSign(Map<String, String> param,String cardNo) throws Exception {
        StringBuffer signData = new StringBuffer();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            signData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        //加签
        String signDataStr = signData.substring(0, signData.length() - 1);

        InputStream in = new FileInputStream(new File(KEYPATH));
        PrivateKey privateKey = CertificateUtils.getPrivateKey(in, null, PASSWD);//"1"为私钥密码
        String sign = EncryptUtils.sign(signDataStr.getBytes("UTF-8"), privateKey);
        logger.info("渠道{}，银行卡号{}，签名密文信息 sign={}，【签名成功生成！】", acquirerCode, ReapalBase64Utils.encode(cardNo),ReapalBase64Utils.encode(sign));
        param.put("sign", sign);

        JSONObject json = JSONObject.fromObject(param);
        String request = json.toString();
        return request;
    }

    /**
     * 签名验证
     * @param param
     * @return
     * @throws Exception
     */
    public boolean verify(String param) throws Exception {
        StringBuffer signData = new StringBuffer();
        TreeMap<String,String> map=(TreeMap<String,String>)JSONObject.toBean(JSONObject.fromObject(param),TreeMap.class);
        String signature=map.get("sign");
        map.remove("sign");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            signData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        //加签
        String signDataStr = signData.substring(0, signData.length() - 1);

//        System.out.println("签名串："+signDataStr);
        InputStream in = new FileInputStream(new File(PUBLICKEY));
        PublicKey bublicKey = CertificateUtils.getPublicKey(in);

        return  EncryptUtils.verify(signDataStr.getBytes("UTF-8"), Base64.decodeBase64(signature), bublicKey);
    }




    /**
     * 封装返回信息
     *
     * @param channelCode
     * @param channelmsg
     * @return
     */
    public InchannelIdentifyResponse packResult(String channelCode, String channelmsg,String productId,String cardNo) {

        InchannelIdentifyResponse identifyResponse = new InchannelIdentifyResponse();
        if (StringUtils.isEmpty(channelCode)) {
            logger.info("渠道{}，银行卡号{}，【认证接口返回参数异常！】", acquirerCode, ReapalBase64Utils.encode(cardNo));
            identifyResponse.setResultCode("3067");
            identifyResponse.setResultMsg("其它");
            identifyResponse.setResult("2");
        } else {
            InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
            inchannelErrorCode.setChannelCode(channelCode);
            inchannelErrorCode.setChannelMsg(channelmsg);
            inchannelErrorCode.setProductId(productId);
            inchannelErrorCode.setBusinesstype(businessType);
            inchannelErrorCode.setAcquirerCode(acquirerCode);

            ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);

            if (null != resultErrorCode) {
                logger.info("渠道{}，银行卡号{}，【认证接口匹配到错误码！】", acquirerCode, ReapalBase64Utils.encode(cardNo));
                identifyResponse.setResultCode(resultErrorCode.getResultCode());
                identifyResponse.setResultMsg(resultErrorCode.getResultMsg());
                identifyResponse.setResult(resultErrorCode.getStatus());
            } else {
                logger.info("渠道{}，银行卡号{}，【认证接口匹配不能匹配到错误码！】", acquirerCode, ReapalBase64Utils.encode(cardNo));
                identifyResponse.setResultCode("3067");
                identifyResponse.setResultMsg("其它");
                identifyResponse.setResult("2");
            }
        }
        return identifyResponse;
    }
}
