package com.reapal.inchannel.txskidentify.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.reapal.common.util.ReapalBase64Utils;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.inchannel.txskidentify.service.TianxingshukeIdentifyService;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import com.squareup.okhttp.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @The author  zhongyuting
 * <p/>
 * 天行数科二.三.四要素鉴权
 */
@Service
public class TianxingshukeIdentifyServiceImpl implements TianxingshukeIdentifyService {
    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

    private static Log log = LogFactory.getLog(TianxingshukeIdentifyServiceImpl.class);
    private ResourceBundle para = ResourceBundle.getBundle("tianxingshuke");
    private String account = this.para.getString("tianxingshuke.rongbao.account");
    private String signature = this.para.getString("tianxingshuke.rongbao.signature");
    private String url_3 = this.para.getString("tianxingshuke.rongbao.url_3");
    private String url_4 = this.para.getString("tianxingshuke.rongbao.url_4");
    private String url_2 = this.para.getString("tianxingshuke.rongbao.url_2");
    private String url_pho = this.para.getString("tianxingshuke.rongbao.url_pho");
    private String url_acc = para.getString("tianxingshuke.rongbao.url_acc");
    private String acquirerCode = para.getString("acquirerCode");

    /**
     * 三四要素鉴权接口
     *
     * @param req
     * @return
     */
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req) {
        InchannelIdentifyResponse response = new InchannelIdentifyResponse();
        //三四要素鉴权
        String sha1CardNo = Sha1Utils.HexString(req.getCardNo());
        log.info("======【天行数科三四要素鉴权接口】======sha1卡==" + sha1CardNo + "==提交参数密文==" + req.toBase64String());
        Response responseV = null;
        Request request = null;
        String backValue = "";
        String idCard = req.getCertNo();
        String name = req.getOwner();//姓名
        String accountNO = req.getCardNo();//银行卡号
        String mobile = req.getPhone();//手机号

        response.setOwner(name);
        response.setCertNo(idCard);
        response.setCardNo(accountNO);
        //获取授权码
        String accessToken = this.getAccessToken();
        if (StringUtils.isNotEmpty(accessToken)) {
            if (StringUtils.isNotEmpty(mobile)) {
                //四要素鉴权
                String url = url_4 + "?account=" + account + "&accessToken=" + accessToken + "&name=" + name + "&idCard=" + idCard + "&accountNO=" + accountNO + "&bankPreMobile=" + mobile + "";
                request = new Request.Builder().url(url).build();
                log.info("=====【天行数科四要素鉴权请求报文-密文】====sha1卡==" + sha1CardNo + "=====" + ReapalBase64Utils.encode(request.toString()));
            } else {
                //三要素鉴权
                String url = url_3 + "?account=" + account + "&accessToken=" + accessToken + "&name=" + name + "&idCard=" + idCard + "&accountNO=" + accountNO + "";
                request = new Request.Builder().url(url).build();
                log.info("=====【天行数科三要素鉴权请求报文-密文】====sha1卡==" + sha1CardNo + "=====" + ReapalBase64Utils.encode(request.toString()));
            }


            try {
                OkHttpClient client = new OkHttpClient();
                responseV = client.newCall(request).execute();
                backValue = responseV.body().string();
                log.info("=====【天行数科三四要素鉴权返回报文】====sha1卡==" + sha1CardNo + "=====" + ReapalBase64Utils.encode(backValue));

                if (StringUtils.isNotEmpty(backValue)) {
                    Map<String, Object> respmapV = (Map<String, Object>) JSON.parse(backValue);
                    log.info("=====【天行数科三四要素鉴权返回的报文体respmapV】==sha1卡===" + sha1CardNo + "=====" + ReapalBase64Utils.encode(respmapV.toString()));
                    String success = respmapV.get("success") + "";
                    String errorDesc = respmapV.get("errorDesc") + "";
                    log.info("=====【天行数科三四要素鉴权返回编码】===sha1卡==" + sha1CardNo + "=====" + success + "-----" + errorDesc);
                    if (("true").equals(success)) {
                        String data = respmapV.get("data") + "";
                        Map<String, Object> dataV = (Map<String, Object>) JSON.parse(data);
                        String checkStatus = dataV.get("checkStatus") + "";
                        String result = dataV.get("result") + "";
                        response.setChannelCode(checkStatus);
                        response.setChannelMsg(result);
                        log.info("=====【天行数科三四要素鉴权返回】===sha1卡==" + sha1CardNo + "=====" + result + "-----" + checkStatus);
                        if (("SAME").equals(checkStatus)) {
                            response.setResultCode("0000");
                            response.setResultMsg("鉴权成功");
                        } else {
                            InchannelIdentifyResponse identifyResponse = this.packResult(checkStatus, result, "103040");
                            if (null != identifyResponse) {
                                response.setResultCode(identifyResponse.getResultCode());
                                response.setResultMsg(identifyResponse.getResultMsg());
                            } else {
                                response.setResultCode("3067");
                                response.setResultMsg("其它");
                            }
                        }

                    } else {
                        response.setResultCode("2011");
                        response.setResultMsg("持卡人身份信息或手机号输入不正确");
                        response.setChannelCode(success);
                        response.setChannelMsg(errorDesc);
                    }

                } else {
                    log.info("=====【天行数科三四要素鉴权返回报文为空】===sha1卡==" + sha1CardNo);
                    response.setResultCode("1049");
                    response.setResultMsg("银行超时，请稍后再试");
                }

            } catch (Exception e) {
                log.error("=====【天行数科三四要素鉴权请求出现异常】===sha1卡==" + sha1CardNo, e);
//                e.printStackTrace();
                response.setResultCode("1049");
                response.setResultMsg("银行超时，请稍后再试");
            }

        } else {
            log.info("=====【天行数科三四要素鉴权获取不到渠道授权码】===sha1卡==" + sha1CardNo);
            response.setResultCode("1049");
            response.setResultMsg("银行超时，请稍后再试");
        }
        log.info("======【天行数科三四要素鉴权响应接口参数】====sha1卡==" + sha1CardNo + "=====" + response.toString());
        return response;
    }


    /**
     * 二要素鉴权接口
     *
     * @param req
     * @return
     */
    public InchannelIdentifyResponse realName(InchannelIdentifyRequest req) {

        InchannelIdentifyResponse response = new InchannelIdentifyResponse();
        //二要素鉴权
        String sha1CertNo = Sha1Utils.HexString(req.getCertNo());
        log.info("======【天行数科二要素鉴权接口】======sha1证==" + sha1CertNo + "==提交参数密文==" + req.toBase64String());


        Response responseV = null;
        Request request = null;
        String backValue = "";

        String idCard = req.getCertNo();
        String name = req.getOwner();//姓名

        response.setOwner(name);
        response.setCertNo(idCard);

        //获取授权码
        String accessToken = this.getAccessToken();
        if (StringUtils.isNotEmpty(accessToken)) {

            String url1 = url_2 + "?account=" + account + "&accessToken=" + accessToken + "&name=" + name + "&idCard=" + idCard + "";
            request = new Request.Builder().url(url1).build();
            log.info("=======【天行数科二要素鉴权请求报文-密文】===sha1证=====" + sha1CertNo + "===" + ReapalBase64Utils.encode(request.toString()));
            try {
                OkHttpClient client = new OkHttpClient();
                responseV = client.newCall(request).execute();
                backValue = responseV.body().string();
                log.info("=======【天行数科二要素鉴权返回的报文体backValue】==sha1证====" + sha1CertNo + "===" + ReapalBase64Utils.encode(backValue));
                if (StringUtils.isNotEmpty(backValue)) {

                    Map<String, Object> respmapV = (Map<String, Object>) JSON.parse(backValue);
                    log.info("======【天行数科二要素鉴权返回的报文体respmapV】==sha1证====" + sha1CertNo + "===" + ReapalBase64Utils.encode(respmapV.toString()));
                    String success = respmapV.get("success") + "";
                    String errorDesc = respmapV.get("errorDesc") + "";
                    log.info("======【天行数科二要素鉴权返回】===sha1证===" + sha1CertNo + "===" + success + "-----" + errorDesc);
                    if (("true").equals(success)) {
                        String data = respmapV.get("data") + "";
                        Map<String, Object> dataV = (Map<String, Object>) JSON.parse(data);
                        String checkStatus = dataV.get("compareStatus") + "";
                        String result = dataV.get("compareStatusDesc") + "";
                        response.setChannelCode(checkStatus);
                        response.setChannelMsg(result);
                        log.info("======【天行数科二要素鉴权返回】===sha1证===" + sha1CertNo + "===" + result + "-----" + checkStatus);
                        if (("SAME").equals(checkStatus)) {
                            response.setResultCode("0000");
                            response.setResultMsg("鉴权成功");
                        } else {
                            InchannelIdentifyResponse identifyResponse = this.packResult(checkStatus, result, "103010");
                            if (null != identifyResponse) {
                                response.setResultCode(identifyResponse.getResultCode());
                                response.setResultMsg(identifyResponse.getResultMsg());
                            } else {
                                response.setResultCode("3067");
                                response.setResultMsg("其它");
                            }
                        }
                    } else {
                        response.setResultCode("2012");
                        response.setResultMsg("姓名与身份证不一致");
                        response.setChannelCode(success);
                        response.setChannelMsg(errorDesc);
                    }
                } else {
                    log.debug("======【天行数科二要素鉴权返回的报文体为null】===sha1证===" + sha1CertNo);
                    response.setResultCode("1049");
                    response.setResultMsg("银行超时，请稍后再试");
                }

            } catch (Exception e) {
                log.error("======【天行数科二要素鉴权渠道请求出现异常】===sha1证===" + sha1CertNo, e);
//                e.printStackTrace();
                response.setResultCode("1049");
                response.setResultMsg("银行超时，请稍后再试");
            }
        } else {
            log.debug("======【天行数科二要素鉴权获取授权码失败】===sha1证===" + sha1CertNo);
            response.setResultCode("1049");
            response.setResultMsg("银行超时，请稍后再试");
        }
        log.info("======【天行数科二要素鉴权响应接口参数】==sha1证====" + sha1CertNo + "===" + response.toString());
        return response;
    }

    /**
     * 获取渠道授权码
     *
     * @return
     * @throws IOException
     */
    private String getAccessToken() {

        RequestBody formBody = new FormEncodingBuilder().add("account", account).add("signature", signature).build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url_acc).post(formBody).build();

        try {
            Response response = client.newCall(request).execute();
            JsonObject tokenJson = new JsonParser().parse(response.body().string()).getAsJsonObject();
            Map<String, Object> respmapV = (Map<String, Object>) JSON.parse(tokenJson + "");
            log.info("=====【授权码接口返回的报文体】======" + respmapV);
            String data = respmapV.get("data") + "";
            String success = respmapV.get("success") + "";
            if (success.equals("true")) {
                Map<String, Object> dataV = (Map<String, Object>) JSON.parse(data);
                String accessToken = dataV.get("accessToken") + "";
                log.info("========【天行数科授权码】========" + accessToken);
                return accessToken;
            } else {
                log.debug("获取授权码失败null");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取授权码出现异常，返回值为null");
            return null;
        }

    }

    public String savePictoServer(InchannelIdentifyRequest req) {
        Response responseV = null;
        Request request = null;
        String backValue = null;
        String fileName = "";
        String path = "D:\\";
        String imgName = "";
        String idCard = req.getCertNo();
        String name = req.getOwner();//姓名
        InchannelIdentifyResponse response = new InchannelIdentifyResponse();
        response.setOwner(name);
        response.setCertNo(idCard);

        //获取授权码
        String accessToken = this.getAccessToken();
        if (StringUtils.isNotEmpty(accessToken)) {
            String url1 = url_pho + "?account=" + account + "&accessToken=" + accessToken + "&name=" + name + "&idCard=" + idCard + "";
            request = new Request.Builder().url(url1).build();
            log.info("========【天行数科返回照片请求报文】=========" + request);
            try {
                OkHttpClient client = new OkHttpClient();
                responseV = client.newCall(request).execute();
                backValue = responseV.body().string();
                log.info("========【天行数科返回照片返回报文】=========" + backValue);

                if (StringUtils.isNotEmpty(backValue)) {
                    log.info("========【天行数科返回照片返回的报文体backValue】=======" + backValue);
                    Map<String, Object> respmapV = (Map<String, Object>) JSON.parse(backValue);
                    log.info("======【天行数科返回照片返回的报文体respmapV】======" + respmapV);
                    String success = respmapV.get("success") + "";
                    log.info("========【天行数科返回照片返回参数success】======" + success);
                    if (success.equals("true")) {
                        String data = respmapV.get("data") + "";
                        Map<String, Object> dataV = (Map<String, Object>) JSON.parse(data);
                        String checkStatus = dataV.get("compareStatus") + "";
                        String result = dataV.get("compareStatusDesc") + "";
                        String identityPhoto = dataV.get("identityPhoto") + "";
                        if (checkStatus.equals("SAME")) {
                            response.setChannelCode(success);
                            response.setChannelMsg(result);

                            try {
                                BASE64Decoder decoder = new sun.misc.BASE64Decoder();
                                byte[] bytes1 = decoder.decodeBuffer(identityPhoto);
                                ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
                                log.info("=======【天行数科返回照片bais】=======" + bais);
                                BufferedImage bi1 = ImageIO.read(bais);
                                log.info("==========【天行数科返回照片bi1】=========" + bi1);
                                String realPath = path;

                                File dir = new File(realPath);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                fileName = path + "tianxingshuke" + imgName + ".png";
                                File w2 = new File(fileName);// 可以是jpg,png,gif格式
                                ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (checkStatus.equals("DIFFERENT")) {
                            response.setResultCode("2011");
                            response.setResultMsg("持卡人身份信息或手机号输入不正确");
                            response.setChannelCode(checkStatus);
                            response.setChannelMsg(result);
                        } else if (checkStatus.equals("ACCOUNTNO_UNABLE_VERIFY")) {
                            response.setResultCode("2011");
                            response.setResultMsg("持卡人身份信息或手机号输入不正确");
                            response.setChannelCode(checkStatus);
                            response.setChannelMsg(result);
                        } else {
                            response.setResultCode("2011");
                            response.setResultMsg("持卡人身份信息或手机号输入不正确");
                        }
                    } else {
                        String errorDesc = respmapV.get("errorDesc") + "";
                        log.info("=========【天行数科返回照片参数errorDesc】==========" + errorDesc);
                        response.setResultCode("2001");
                        response.setResultMsg("返回照片失败，请重新签约或更换其它银行卡签约");
                        response.setChannelCode(success);
                        response.setChannelMsg(errorDesc);
                    }

                } else {
                    log.info("=========【天行数科返回报文体为空】=========");
                    response.setResultCode("1049");
                    response.setResultMsg("银行请求超时");
                }


            } catch (Exception e) {
                log.info("=========【天行数科请求返回照片渠道出现异常】=========");
                e.printStackTrace();
                response.setResultCode("1049");
                response.setResultMsg("银行请求超时");
            }
        } else {
            log.info("=========【天行数科请求返回照片获取授权码失败】=========");
            response.setResultCode("1049");
            response.setResultMsg("银行请求超时");

        }

        log.info("======【返回照片的地址】fileName======" + fileName);
        log.info("======【天行数科返回照片响应接口参数】======" + response.toString());
        return fileName;
    }


    /**
     * 封装返回信息
     *
     * @param channelCode
     * @param channelmsg
     * @param productId
     * @return
     */
    public InchannelIdentifyResponse packResult(String channelCode, String channelmsg, String productId) {

        InchannelIdentifyResponse identifyResponse = new InchannelIdentifyResponse();
        if (StringUtils.isEmpty(channelCode)) {
            log.info("========认证接口返回参数异常========");
            identifyResponse.setResultCode("3067");
            identifyResponse.setResultMsg("其它");
        } else {
            InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
            inchannelErrorCode.setChannelCode(channelCode);
            inchannelErrorCode.setChannelMsg(channelmsg);
            inchannelErrorCode.setProductId(productId);
            inchannelErrorCode.setBusinesstype("141030");
            inchannelErrorCode.setAcquirerCode(acquirerCode);

            ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);

            if (null != resultErrorCode) {
                identifyResponse.setResultCode(resultErrorCode.getResultCode());
                identifyResponse.setResultMsg(resultErrorCode.getResultMsg());
                identifyResponse.setResult(resultErrorCode.getStatus());
            } else {
                log.info("========认证接口匹配不能匹配到错误码========");
                identifyResponse.setResultCode("3067");
                identifyResponse.setResultMsg("其它");
            }
        }
        return identifyResponse;
    }
}