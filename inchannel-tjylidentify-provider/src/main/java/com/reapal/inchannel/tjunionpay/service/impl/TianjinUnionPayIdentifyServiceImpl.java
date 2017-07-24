package com.reapal.inchannel.tjunionpay.service.impl;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.reapal.common.util.ReapalBase64Utils;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtil;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.inchannel.tjunionpay.service.TianjinUnionPayIdentifyService;
import com.reapal.inchannel.tjunionpay.service.model.RequestData;
import com.reapal.inchannel.tjunionpay.service.model.ResponseData;
import com.reapal.inchannel.tjunionpay.service.util.*;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by liukai on 2016/11/23.
 */
@Service
public class TianjinUnionPayIdentifyServiceImpl implements TianjinUnionPayIdentifyService {

    @Autowired
    private TianjinUnionPayDyncKey tianjinUnionPayDyncKey;
    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

    private static final Log LOGGER = LogFactory.getLog(TianjinUnionPayIdentifyServiceImpl.class);

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("tjunionpay");
    private String  COMPANY_CODE = resourceBundle.getString("companyCode");
    private String TWAIN_TYPE = resourceBundle.getString("twainType");
    private String MORE_TYPE = resourceBundle.getString("moreType");

    /**
     * Description: 鉴权
     *      1.配置连接参数
     *      2.封装参数
     *      3.调用接口
     *      4.处理返回数据
     * @param req
     * @return com.reapal.inchannel.tjunionpay.model.TianjinUnionPayIdentifyResponse
     * @Author liukai
     * @Data 2016/11/24 9:38
     */
    @Override
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req)  {
        tianjinUnionPayDyncKey.checkDyncKey();      //校验 key 是否超时
        InchannelIdentifyResponse identifyResponse = null;
        int identifyType = 0;
        try {
            //发送请求报文
            HttpsURLConnection httpURLConnection = this.requestMessage(req, identifyType);
            //处理返回报文
            identifyResponse = this.disposeMessage(httpURLConnection, identifyType);
        } catch (Exception e) {
            e.printStackTrace();
            identifyResponse.setResult("2");
            identifyResponse.setResultCode("2010");
            identifyResponse.setResultMsg("银行超时，请重试");
        }
        return identifyResponse;
    }

    /**
     * Description: 发送报文
     * @Author liukai
     * @Data 2016/11/24 16:01
     * @param req
     * @param identifyType 0：三四六;1 二要素
     * @return javax.net.ssl.HttpsURLConnection
     */
    private HttpsURLConnection requestMessage(InchannelIdentifyRequest req, Integer identifyType) throws Exception {
        HttpsURLConnection httpURLConnection = null;
        PrintStream out = null;
        try {
            // 发送请求
            URL url = new URL(Constants.URL_TRADE);
            httpURLConnection = (HttpsURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setReadTimeout(50000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            //调用接口
            out = new PrintStream(httpURLConnection.getOutputStream(), false, "UTF-8");
            out.print(getTransReq(req, identifyType));
            out.flush();
            return httpURLConnection;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Description: 处理返回报文
     *      resultCode: 00 通过, 01 不通过
     * @Author liukai
     * @Data 2016/11/24 15:24
     * @param httpURLConnection
     * @param identifyType 0：三四六;1 二要素
     * @return void
     */
    private InchannelIdentifyResponse disposeMessage (HttpsURLConnection httpURLConnection, Integer identifyType) throws IOException {
        //接收响应
        String resultString = null;
        InputStream in = null;
        StringBuilder sb = new StringBuilder(1024);
        BufferedReader br = null;
        String temp = null;
        InchannelIdentifyResponse identifyResponse = null;
        ResponseData responseData = null;
        try {
            if (200 == httpURLConnection.getResponseCode()) {
                in = httpURLConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
            } else {
                in = httpURLConnection.getErrorStream();
                br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
            }
            resultString = sb.toString();
            LOGGER.info("应答报文:" + resultString);
//            LOGGER.info("=====应答报文:"+resultString);
            responseData = this.coverMessage(resultString);
            identifyResponse = this.packResult(responseData, identifyType);       //封装返回信息
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (in != null) {
                in.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return identifyResponse;
    }

    /**
     * Description:  封装请求报文
     * @Author liukai
     * @Data 2016/11/24 12:38
     * @param map
     * @return java.lang.String
     */
    private static String getTransReq(Map<String, String> map)
            throws Exception {
        String result = "";
        RequestData request = new RequestData();
        request.setCharCode(Constants.ENCODING_UTF8);
        request.setVersion("1.0.0");
        request.setTradeType(Constants.TRADE_TYPE);     //交易类型 Constants.TRADE_TYPE 有这个常量
        request.setChnlId(Constants.CHANNEL_ID);
        request.setUserId(Constants.USER_ID);
        request.setTradeSource("1");
        request.setOrderId(OrderUtil.getDateTimeTemp() + OrderUtil.getRandomStr(2));
        request.setTimeStamp(OrderUtil.getDateTimeTemp());
        request.setAccNo(map.get("accNo"));     //卡号
        request.setName(map.get("name"));       //姓名
        request.setCertificateCode(map.get("certificateCode")); //身份证号
        request.setNbr(map.get("nbr"));         //手机号
        request.setParams(map.get("params"));   //黑名单指数
        request.setMd5ConSec(TransMsgUtil.genMd5ConSec(request));

        String xmlStr = XmlUtil.convertToXml(request);
        String certNo  = map.get("certificateCode");
        String sha1CertNo = "";
        if(StringUtils.isNotEmpty(certNo)){
            sha1CertNo = Sha1Utils.HexString(certNo);
        }

        LOGGER.info("==sha证=="+ sha1CertNo +"----" + ReapalBase64Utils.encode(xmlStr));
        byte[] xmlByte = DESedeUtil.encryptMode(xmlStr.getBytes(Constants.ENCODING_UTF8), Constants.DYN_3DES_KEY,
                Constants.ENCODING_UTF8);

        result = TransMsgUtil.appendLeft(Constants.USER_ID, " ", 12) + Constants.CHANNEL_ID + "1"
                + org.apache.commons.codec.binary.Base64.encodeBase64String(xmlByte);
        result = TransMsgUtil.addZeroForNum(String.valueOf(xmlByte.length + 25), 4) + result;
        LOGGER.info("请求报文：" + result);
        return result;
    }

    /**
     * Description: 封装三四六要素请求报文
     * @Author liukai
     * @Data 2016/11/24 15:02
     * @param req
     * @return java.lang.String
     */
    private static String getTransReq(InchannelIdentifyRequest req, Integer identifyType) throws Exception {
        HashedMap map = new HashedMap();
        if (identifyType == 0) {
            map.put("accNo", req.getCardNo());
            map.put("name", req.getOwner());
            map.put("certificateCode", req.getCertNo());
            map.put("nbr", req.getPhone());
            map.put("params", "");
        } else {
            map.put("name", req.getOwner());
            map.put("certificateCode", req.getCertNo());
        }

        Constants.TRADE_TYPE = getTradeType(req, map);
        return getTransReq(map);
    }

    /**
     * Description: 转换响应报文为 XML 格式
     * @Author liukai
     * @Data 2016/11/24 15:18
     * @param resultString
     * @return com.reapal.inchannel.tjunionpay.service.model.ResponseData
     */
    private ResponseData coverMessage(String resultString) throws UnsupportedEncodingException {
        ResponseData responseData = null;
        try {
            JSONObject jsonObject = new JSONObject(resultString);
            responseData = new ResponseData();
            responseData.setResultCode(jsonObject.getString("status"));
            responseData.setResultDesc(jsonObject.getString("msg"));
            return responseData;
        } catch (Exception e) {
            LOGGER.info("应答报文不是JSON格式，进行正常处理！");
        }

        if (null == resultString || "".equals(resultString.trim()) || resultString.length() < 29) {
            return null;
        } else {
            resultString = new String(DESedeUtil.decryptMode(org.apache.commons.codec.binary.Base64.decodeBase64(resultString.substring(29)),
                    Constants.DYN_3DES_KEY, Constants.ENCODING_UTF8), Constants.ENCODING_UTF8);
        }
        responseData = XmlUtil.converyToJavaBean(resultString, ResponseData.class);
        LOGGER.info("======响应报文===sha1卡==="+ Sha1Utils.HexString(responseData.getAccNo())+"======"+ReapalBase64Utils.encode(resultString));
        LOGGER.info("验证结果：" + responseData.getResultCode() + responseData.getResultDesc());
        return responseData;
    }


    /**
     * Description: 封装返回信息
     * @Author liukai
     * @Data 2016/11/28 10:24
     * @param responseData
     * @return void
     */
  // private InchannelIdentifyResponse packResult(ResponseData responseData) {
//    public InchannelIdentifyResponse packResult(ResponseData responseData) {
//        InchannelIdentifyResponse identifyResponse = new InchannelIdentifyResponse();
//        if (null != responseData) {
//            String resultCode = responseData.getResultCode();
//            switch (resultCode) {
//                case "00" :{
//                    identifyResponse.setResultCode(CodeEnum.Reapal.Success.v());
//                    identifyResponse.setResultMsg(CodeEnum.Reapal.Success.c());
//                    break;
//                }
//                case "01" :{
//                    String resultDesc = responseData.getResultDesc();
//                    if (resultDesc.contains(CodeEnum.Channel.CarOrMobileError.c())) {   //持卡人身份信息、手机号或CVN2输入不正确，验证失败
////                        identifyResponse.setChannelCode(responseData.getResultCode());
////                        identifyResponse.setChannelMsg(responseData.getResultDesc());
//                        identifyResponse.setResultCode(CodeEnum.Reapal.CarOrMobileError.v());
//                        identifyResponse.setResultMsg(CodeEnum.Reapal.CarOrMobileError.c());
//                    } else if (resultDesc.contains(CodeEnum.Channel.Exceed.c())) {  //认证不一致(不通过):超出取款金额限制
////                        identifyResponse.setChannelCode(responseData.getResultCode());
////                        identifyResponse.setChannelMsg(responseData.getResultDesc());
//                        identifyResponse.setResultCode(CodeEnum.Reapal.CarOrMobileError.v());
//                        identifyResponse.setResultMsg(CodeEnum.Reapal.CarOrMobileError.c());
//                    } else if (resultDesc.contains(CodeEnum.Channel.NotAllowTrading.c())) { //认证不一致(不通过):不允许持卡人进行的交易
////                        identifyResponse.setChannelCode(responseData.getResultCode());
////                        identifyResponse.setChannelMsg(responseData.getResultDesc());
//                        identifyResponse.setResultCode(CodeEnum.Reapal.SignFail.v());
//                        identifyResponse.setResultMsg(CodeEnum.Reapal.SignFail.c());
//                    } else if (resultDesc.contains(CodeEnum.Channel.CarInvalid.c())) {  //输入的卡号无效，请确认后输入
////                        identifyResponse.setChannelCode(responseData.getResultCode());
////                        identifyResponse.setChannelMsg(responseData.getResultDesc());
//                        identifyResponse.setResultCode(CodeEnum.Reapal.InvalidCar.v());
//                        identifyResponse.setResultMsg(CodeEnum.Reapal.InvalidCar.c());
//                    } else if (resultDesc.contains(CodeEnum.Channel.InfoDiff.c())) {    //信息不匹配
////                        identifyResponse.setChannelCode(responseData.getResultCode());
////                        identifyResponse.setChannelMsg(responseData.getResultDesc());
//                        identifyResponse.setResultCode(CodeEnum.Reapal.CarOrMobileError.v());
//                        identifyResponse.setResultMsg(CodeEnum.Reapal.CarOrMobileError.c());
//                    }
//                    break;
//                }
//                case "06" :{     //签约失败，请重新签约或更换其它银行卡签约
//                    identifyResponse.setResultCode(CodeEnum.Reapal.ReSign.v());
//                    identifyResponse.setResultMsg(CodeEnum.Reapal.ReSign.c());
//                    break;
//                }
//                case "09" :{    //签约失败，请重新签约或更换其它银行卡签约
//                    identifyResponse.setResultCode(CodeEnum.Reapal.ReSign.v());
//                    identifyResponse.setResultMsg(CodeEnum.Reapal.ReSign.c());
//                    break;
//                }
//                case "18" :{    //卡片查询次数超限
//                    identifyResponse.setResultCode(CodeEnum.Reapal.OutNumber.v());
//                    identifyResponse.setResultMsg(CodeEnum.Reapal.OutNumber.c());
//                    break;
//                }
//                case "32" :{    //报文解析失败,请检查渠道ID和秘钥
////                    identifyResponse.setChannelCode(CodeEnum.Channel.MessageFail.v());
////                    identifyResponse.setChannelMsg(CodeEnum.Channel.MessageFail.c());
//                    identifyResponse.setResultCode(CodeEnum.Reapal.ReSign.v());
//                    identifyResponse.setResultMsg(CodeEnum.Reapal.ReSign.c());
//                    break;
//                }
//                default: {
//                    identifyResponse.setChannelCode(responseData.getResultCode());
//                    identifyResponse.setChannelMsg(responseData.getResultDesc());
//                    identifyResponse.setResultCode("3067");
//                    identifyResponse.setResultMsg("其它");
//                }
//            }
//            if (StringUtil.isEmpty(identifyResponse.getChannelCode())) {
//                identifyResponse.setChannelCode(responseData.getResultCode());
//                identifyResponse.setChannelMsg(responseData.getResultDesc());
//            }
//            LOGGER.info("========【渠道返回信息】========" + responseData.getResultDesc());
//        } else {    //鉴权失败
//            identifyResponse.setResultCode(CodeEnum.Reapal.Fail.v());
//            identifyResponse.setResultMsg(CodeEnum.Reapal.Fail.c());
//        }
//        return identifyResponse;
//    }


        /**
         * 封装返回信息
         *
         * @param responseData
         * @return
         */
        private InchannelIdentifyResponse packResult(ResponseData responseData, Integer identifyType) {
            InchannelIdentifyResponse identifyResponse = new InchannelIdentifyResponse();
            if (null != responseData) {
                String resultCode = responseData.getResultCode();
                String resultMsg = responseData.getResultDesc();
                identifyResponse.setChannelCode(resultCode);
                identifyResponse.setChannelMsg(resultMsg);

                if("00".equals(resultCode)){
                    if (0 == identifyType) {
                        identifyResponse.setResultCode(CodeEnum.Reapal.Success.v());
                        identifyResponse.setResultMsg(CodeEnum.Reapal.Success.c());
                    } else {
                        identifyResponse.setResultCode(CodeEnum.Reapal.Success_twain.v());
                        identifyResponse.setResultMsg(CodeEnum.Reapal.Success_twain.c());
                    }
                }else{
                    InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
                    inchannelErrorCode.setChannelCode(resultCode);
                    inchannelErrorCode.setChannelMsg(resultMsg);
                    inchannelErrorCode.setAcquirerCode(COMPANY_CODE);
                    inchannelErrorCode.setBusinesstype("141030");
                    if (0 == identifyType) {
                        inchannelErrorCode.setProductId(MORE_TYPE);
                    } else {
                        inchannelErrorCode.setProductId(TWAIN_TYPE);
                    }
                    ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);
                    if (null != resultErrorCode) {
                        identifyResponse.setResultCode(resultErrorCode.getResultCode());
                        identifyResponse.setResultMsg(resultErrorCode.getResultMsg());
                        identifyResponse.setResult(resultErrorCode.getStatus());
                    } else {
                        LOGGER.info("========认证接口匹配错误码出现异常========");
                        identifyResponse.setResultCode(CodeEnum.Reapal.Fail.v());
                        identifyResponse.setResultMsg(CodeEnum.Reapal.Fail.c());
                    }
                }
            } else {    //鉴权失败
                LOGGER.info("========认证接口返回参数异常========");
                identifyResponse.setResultCode(CodeEnum.Reapal.Fail.v());
                identifyResponse.setResultMsg(CodeEnum.Reapal.Fail.c());
            }
            return identifyResponse;
        }

    /**
     * Description: 获取交易类型
     *      1.二要素：卡号 + 姓名 0405
     *      2.二要素：卡号 + 身份证 0407
     *      3.三要素认证，卡号 + 姓名 + 身份证号 0409
     *      4.四要素认证，卡号 + 姓名 + 身份证号 + 手机号 0411
     *      5.两要素认证，姓名 + 身份证号(实名认证) 0417
     *      6.两要素认证返回照片，姓名 + 身份证号(实名认证) 0433
     *      7.运营商两要素认证，姓名 + 手机号(后台字段区分电信，联通，移动号段，价格有差异) 0419
     *      8.运营商三要素认证，姓名 + 手机号 + 身份证号(后台字段区分电信，联通，移动号段，价格有差异) 0421
     *      9.火眼查询，即持卡人的黑名单查询 0435
     * @Author liukai
     * @Data 2016/11/28 17:29
     * @param req
     * @return java.lang.String
     */
    private static String getTradeType(InchannelIdentifyRequest req, Map<String, String> map) {
        String owner = req.getOwner();
        String cardNo = req.getCardNo();
        String certNo = req.getCertNo();
        String phone = req.getPhone();
        String params = (String)map.get("params");
        String tradeType = null;
        if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(cardNo) && StringUtil.isNotEmpty(certNo) && StringUtil.isNotEmpty(phone)) {
            tradeType = "0411";
        } else if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(certNo) && StringUtil.isNotEmpty(phone) && StringUtil.isNotEmpty(params)) {
            tradeType = "0435";
        } else if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(cardNo) && StringUtil.isNotEmpty(certNo)) {
            tradeType = "0409";
        } else if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(phone) && StringUtil.isNotEmpty(certNo)) {
            tradeType = "0421";
        } else if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(cardNo)) {
            tradeType = "0405";
        } else if(StringUtil.isNotEmpty(cardNo) && StringUtil.isNotEmpty(certNo)) {
            tradeType = "0407";
        } else if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(certNo)) {
            tradeType = "0417";
        } else if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(certNo)) {
            tradeType = "0433";
        } else if(StringUtil.isNotEmpty(owner) && StringUtil.isNotEmpty(phone)) {
            tradeType = "0419";
        }
        return tradeType;
    }


    /**
     * Description: 二要素鉴权
     * @Author liukai
     * @Data 2017/1/5 10:07
     * @param identifyRequest
     * @return com.reapal.inchannel.model.InchannelIdentifyResponse
     */
    @Override
    public InchannelIdentifyResponse realName(InchannelIdentifyRequest identifyRequest) {
        tianjinUnionPayDyncKey.checkDyncKey();              //校验 key 是否超时
        InchannelIdentifyResponse identifyResponse = null;
        int identifyType = 1;
        try {
            HttpsURLConnection httpURLConnection = this.requestMessage(identifyRequest, identifyType);  //发送请求报文
            identifyResponse = this.disposeMessage(httpURLConnection, identifyType);            //处理返回报文
        } catch (Exception e) {
            e.printStackTrace();
            identifyResponse.setResult("2");
            identifyResponse.setResultCode("2010");
            identifyResponse.setResultMsg("银行超时，请重试");
        }
        return identifyResponse;
    }
}