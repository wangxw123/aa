package com.reapal.inchannel.kaola.service.impl;

import com.reapal.common.util.ReapalBase64Utils;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtil;
import com.reapal.inchannel.kaola.service.KaolaIdentifyService;
import com.reapal.inchannel.kaola.service.utils.*;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Created by liukai on 2016/12/2.
 */
@Service
public class KaolaIdentifyServiceImpl implements KaolaIdentifyService {
    private static final Logger LOGGER = Logger.getLogger(KaolaIdentifyServiceImpl.class);
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("klConfig");
    private String lakalGateway = resourceBundle.getString("lakalGateway");
    private String identifyGateway = resourceBundle.getString("identifyGateway");
    private String lakalaCustomerId = resourceBundle.getString("lakalaCustomerId");
    private String COMPANY_CODE = resourceBundle.getString("companyCode");


    private String TWAIN_TYPE = resourceBundle.getString("twainType");
    private String MORE_TYPE = resourceBundle.getString("moreType");
    private String PHOTO_TYPE = resourceBundle.getString("photoType");


    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

    private static Properties pps = new Properties();
    static {
        try {
            LOGGER.info("【读取配置文件】");
            //读取配置信息
            InputStream in = new ClassPathResource("kl.properties").getInputStream();
            pps.load(in);
        } catch (Exception e) {
            LOGGER.info("【系统初始化失败】");
            e.printStackTrace();
        }
    }

    /**
     * Description: 二要素鉴权，不返照片
     * @Author liukai
     * @Data 2016/12/30 11:00
     * @param identifyRequest
     * @return com.reapal.inchannel.model.InchannelIdentifyResponse
     */
    public InchannelIdentifyResponse realName(InchannelIdentifyRequest identifyRequest) {
        String sha1CertNo = Sha1Utils.HexString(identifyRequest.getCertNo());
        LOGGER.info("======【kaola 二要素不返照片请求接口参数】=====sha1证=" + sha1CertNo + "----" + identifyRequest.toBase64String());
        Map<String, Object> responseMessage = this.identifyCert(identifyRequest.getOwner(), identifyRequest.getCertNo(), 1);
        responseMessage.put("identifyType", 1);
        return getIdentifyResult(responseMessage);
    }

    /**
     * Description: 三四六要素鉴权
     * @Author liukai
     * @Data 2016/12/2 16:32
     * @param identifyRequest
     * @return com.reapal.inchannel.model.InchannelIdentifyResponse
     */
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest identifyRequest) {
        String sha1CardNo = Sha1Utils.HexString(identifyRequest.getCardNo());
        LOGGER.info("======【kaola 三四六鉴权请求接口参数】=====sha1卡=" + sha1CardNo + "----" +  identifyRequest.toBase64String());
        Map<String, Object> responseMessage = this.getResponseMessage(identifyRequest);
        responseMessage.put("identifyType", 2);
        return getIdentifyResult(responseMessage);
    }

    /**
     * Description: 二要素返照片
     * @Author liukai
     * @Data 2016/12/20 16:53
     * @param certId
     * @param usrName
     * @param cardNo
     * @param phone
     * @return java.util.Map
     */
    public Map lakalaChannel(String certId, String usrName, String cardNo, String phone) {
        LOGGER.info("======【kaola 二要素返照片】======");
        return this.identifyCert(usrName, certId, 0);
    }




    /**
     * 身份证查询照片
     * @param identifyRequest
     * @return
     */
    public InchannelIdentifyResponse realNamePhoto(InchannelIdentifyRequest identifyRequest){
        String sha1CertNo = Sha1Utils.HexString(identifyRequest.getCertNo());
        LOGGER.info("======【kaola 身份证查询照片请求接口参数】=====sha1证=" + sha1CertNo + "----" + identifyRequest.toBase64String());
        return this.identifyCertPhoto(identifyRequest.getOwner(), identifyRequest.getCertNo(), 0);
    }


    /**
     * Description: 转换报文
     * @Author liukai
     * @Data 2016/12/2 17:22
     * @param identifyRequest
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private Map<String, Object> getResponseMessage (InchannelIdentifyRequest identifyRequest) {
        Map<String, String> params = new HashMap<String, String>();         // 组装报文头
        Map<String, String> signParams = new HashMap<String, String>();
        signParams.put("outOrderNo", UuidUtil.uuid());
        String certId = identifyRequest.getCertNo();        //身份证号
        String userName = identifyRequest.getOwner();       //持卡人姓名
        String phone = identifyRequest.getPhone();          //手机号
        String accountNo = identifyRequest.getCardNo();     //卡号
        String cvn2 = identifyRequest.getCvv2();
        String validityTerm = identifyRequest.getValidthru();   //有效期

        //六要素
        if (StringUtil.isNotEmpty(certId) && StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(phone) && StringUtil.isNotEmpty(accountNo) && StringUtil.isNotEmpty(cvn2) && StringUtil.isNotEmpty(validityTerm)) {
            signParams.put("name", userName);                //开户人姓名
            signParams.put("idCardCore", certId);           //开户人身份证号
            signParams.put("bankPreMobile", phone);         //银行预留手机号
            signParams.put("accountNo", accountNo);         //银行卡号
            signParams.put("validityTerm", validityTerm);   //有效期
            signParams.put("cvn2", cvn2);                   //信用卡类型
            params.put("prdGrpId", "bankCardQuery");
            params.put("prdId", "qryBankCardBy6Element");
            LOGGER.info("======【考拉六要素鉴权】======");
        }
        //四要素
        else if (StringUtil.isNotEmpty(certId) && StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(phone) && StringUtil.isNotEmpty(accountNo)) {
            signParams.put("name", userName);         //开户人姓名
            signParams.put("idCardCore", certId);           //开户人身份证号
            signParams.put("bankPreMobile", phone);         //银行预留手机号
            signParams.put("accountNo", accountNo);         //银行卡号
            params.put("prdGrpId", "bankCardQuery");
            params.put("prdId", "qryBankCardBy4Element");
            LOGGER.info("======【考拉四要素鉴权】======");
        }
        //三要素
        else if (StringUtil.isNotEmpty(certId) && StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(accountNo)) {
            signParams.put("name", userName);                //开户人姓名
            signParams.put("idCardCore", certId);           //开户人身份证号
            signParams.put("accountNo", accountNo);         //银行卡号
            params.put("prdGrpId", "bankCardQuery");
            params.put("prdId", "qryBankCardBy3Element");
            LOGGER.info("======【考拉三要素鉴权】======");
        }
        else {
            LOGGER.info("======【考拉身份认证：专入参数不正确】======");
        }
        Map<String, String> messageHead = packMessageHead(signParams, params);   //组装报文头
        return this.sendRequestMessage(messageHead);            // 获取请求结果
    }

    /**
     * Description: 发送鉴权请求
     * @Author liukai
     * @Data 2016/12/5 9:59
     * @param params
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private Map<String, Object> sendRequestMessage(Map<String, String> params) {
        String requestResult = null;
        Map<String, Object> requestMap = null;
        try {
            requestResult = HttpUtils.doPost(this.identifyGateway + "?_t=json", params, 30000, 30000);
            //requestResult = HttpUtils.doPost(this.lakalGateway + "?_t=json", params, 30000, 30000);
            LOGGER.info("=========报文结果=======" + requestResult);
            if (StringUtil.isNotEmpty(requestResult)) {
                boolean isRight = LklVerify.verifyReturn(requestResult);
                if (isRight) {
                    requestMap =  LklSubmit.getResData(requestResult);
                    LOGGER.info("=========消息合法=======" +"=====sha1卡====="+ Sha1Utils.HexString(requestMap.get("accountNo").toString())+"======"+ ReapalBase64Utils.encode(requestMap.toString()));
//                    LOGGER.info("=========消息合法=======" + Base64.encode(requestMap.toString().getBytes()));
                    requestMap.put("retCode", requestMap.get("result"));
                    requestMap.put("retMsg", requestMap.get("message"));
                } else {
                    requestMap = JSONObject.fromObject(requestResult);
                    LOGGER.info("=========消息不合法=======" +"=====sha1卡====="+ Sha1Utils.HexString(requestMap.get("accountNo").toString())+"======"+ ReapalBase64Utils.encode(requestMap.toString()));
                }
            } else {
                throw new Exception("======请求异常=====：返回报文为空");
            }
        } catch (IOException e) {
            LOGGER.info("===========请求异常=========" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestMap;
    }

    /**
     * Description: 组装报文头
     * @Author liukai
     * @Data 2016/12/26 11:50
     * @param signParams
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private Map<String, String> packMessageHead(Map<String, String> signParams, Map<String, String> params) {
        // 加密签名
        Map<String, String> tokenSigns = LklSubmit.getSign(signParams);
        params.put("customerId", lakalaCustomerId);
        params.put("sign", tokenSigns.get(LklDict.SIGN));
        params.put("reqData", tokenSigns.get(LklDict.REQDATA));
        LOGGER.info("【============请求报文字段=============】：" + params);
        return params;
    }


    /**
     * Description: 返回码
     * @Author liukai
     * @Data 2017/1/4 15:57
     * @param responseMessage
     * @return com.reapal.inchannel.model.InchannelIdentifyResponse
     */
    private InchannelIdentifyResponse getIdentifyResult(Map<String, Object> responseMessage) {
        InchannelIdentifyResponse response = null;
        Integer identifyType = null;
        if (responseMessage != null) {
            identifyType = (Integer) responseMessage.get("identifyType");
            response = new InchannelIdentifyResponse();
            String retCode = (String) responseMessage.get("retCode");
            String message = (String) responseMessage.get("retMsg");
            LOGGER.info("result="+retCode+"-------"+"message="+message);
            if(retCode != null && "T".equals(retCode)){             //三四六返回 T
                response.setResultMsg("鉴权成功");
                response.setResult("1");
                response.setResultCode("0000");
                response.setChannelCode(retCode);
                response.setChannelMsg(message);
            } else if(retCode != null && "00".equals(retCode)){      //二要素返回 00
                response.setResultMsg("认证成功");
                response.setResult("1");
                response.setResultCode("0000");
                response.setChannelCode(retCode);
                response.setChannelMsg(message);
            }
            else{
                response.setChannelCode(retCode);
                response.setChannelMsg(message);
                InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
                inchannelErrorCode.setChannelCode(retCode);
                inchannelErrorCode.setChannelMsg(message);
                inchannelErrorCode.setBusinesstype("141030");
                if (identifyType == 1) {    //二要素
                    inchannelErrorCode.setProductId(TWAIN_TYPE);
                } else if (identifyType == 2) { //三四六
                    inchannelErrorCode.setProductId(MORE_TYPE);
                }
                inchannelErrorCode.setAcquirerCode(COMPANY_CODE);
                ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);
                if (null != resultErrorCode) {
                    response.setResultCode(resultErrorCode.getResultCode());
                    response.setResultMsg(resultErrorCode.getResultMsg());
                    response.setResult(resultErrorCode.getStatus());
                } else {
                    LOGGER.info("========认证接口匹配错误码出现异常========");
                    response.setResultCode(CodeEnum.Reapal.Fail.v());
                    response.setResultMsg(CodeEnum.Reapal.Fail.c());
                }
            }
        } else {
            response = new InchannelIdentifyResponse();
            response.setResult("2");
            response.setResultCode("1049");
            response.setResultMsg("银行超时，请稍后再试");
        }
        return response;
    }

    /**
     * Description: 身份认证接口
     * @Author liukai
     * @Data 2017/1/4 18:21
     * @param userName
     * @param certId
     * @param type 0 返照片，1 不返照片
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public InchannelIdentifyResponse identifyCertPhoto(String userName, String certId, Integer type) {
        InchannelIdentifyResponse inchannelIdentifyResponse = new InchannelIdentifyResponse();
        String sha1CertNo = Sha1Utils.HexString(certId);
        Map<String, Object> validatedResult = null;
        String messageResult = null;
        Map<String, String> encryptSign = new HashMap<String, String>();    //封装验签参数
        encryptSign.put("idCardName", userName);
        encryptSign.put("idCardCode", certId);
        encryptSign.put("outOrderNo", UuidUtil.uuid());
        Map<String, String> requestToken = LklSubmit.getSign(encryptSign);  //验签结果
        Map<String, String> requestParameters = new HashMap<String, String>();  //封装请求参数
        requestParameters.put("customerId", lakalaCustomerId);
        requestParameters.put("sign", requestToken.get(LklDict.SIGN));
        requestParameters.put("reqData", requestToken.get(LklDict.REQDATA));
        requestParameters.put("prdGrpId", "identityQuery");
        if (null == type || 0 == type) {
            requestParameters.put("prdId", "queryIDCard");      //返照片
        } else {
            requestParameters.put("prdId", "validateIDCard");   //不返照片
        }
        try {
            messageResult =  HttpUtils.doPost(this.lakalGateway + "?_t=json", requestParameters, 30000, 30000);
        } catch (IOException e) {
            LOGGER.info("=========IO异常=======");
            e.printStackTrace();
        }
        if(messageResult != null) {
            boolean verifyFlag = LklVerify.verifyReturn(messageResult);
            if (verifyFlag) {
                validatedResult = LklSubmit.getResData(messageResult);
//                validatedResult.put("retCode", (String) validatedResult.get("result"));
//                validatedResult.put("retMsg", (String) validatedResult.get("message"));
//                LOGGER.info("=========验证成功=======" + validatedResult);
                LOGGER.info("=========验证成功=======" +"=====sha1卡====="+ sha1CertNo+"----"+ ReapalBase64Utils.encode(validatedResult.toString()));
            } else {
                validatedResult = JSONObject.fromObject(messageResult);
                LOGGER.info("=========验证失败=======" +"=====sha1卡====="+ sha1CertNo+"----"+ ReapalBase64Utils.encode(validatedResult.toString()));
            }
        }

        inchannelIdentifyResponse.setChannelCode((String)validatedResult.get("result"));
        inchannelIdentifyResponse.setChannelMsg((String)validatedResult.get("message"));
        inchannelIdentifyResponse.setCertPhoto((String)validatedResult.get("idCardPhoto"));

        if("00".equals((String)validatedResult.get("result"))){             //三四六返回 T
            inchannelIdentifyResponse.setResultMsg("获取照片成功");
            inchannelIdentifyResponse.setResult("1");
            inchannelIdentifyResponse.setResultCode("0000");
        } else{
            InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
            inchannelErrorCode.setChannelCode((String)validatedResult.get("result"));
            inchannelErrorCode.setChannelMsg((String)validatedResult.get("message"));
            inchannelErrorCode.setBusinesstype("141030");
            inchannelErrorCode.setProductId(PHOTO_TYPE);
            inchannelErrorCode.setAcquirerCode(COMPANY_CODE);
            ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);
            if (null != resultErrorCode) {
                inchannelIdentifyResponse.setResultCode(resultErrorCode.getResultCode());
                inchannelIdentifyResponse.setResultMsg(resultErrorCode.getResultMsg());
                inchannelIdentifyResponse.setResult(resultErrorCode.getStatus());
            } else {
                LOGGER.info("========认证接口匹配错误码出现异常========");
                inchannelIdentifyResponse.setResultCode(CodeEnum.Reapal.Fail.v());
                inchannelIdentifyResponse.setResultMsg(CodeEnum.Reapal.Fail.c());
            }
        }

        LOGGER.info("=========inchannelIdentifyResponse=======" +"=====sha1卡====="+ sha1CertNo+"----"+ ReapalBase64Utils.encode(inchannelIdentifyResponse.toString()));

        return inchannelIdentifyResponse;
    }



    /**
     * Description: 身份认证接口
     * @Author liukai
     * @Data 2017/1/4 18:21
     * @param userName
     * @param certId
     * @param type 0 返照片，1 不返照片
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> identifyCert(String userName, String certId, Integer type) {
        String sha1CertNo = Sha1Utils.HexString(certId);
        Map<String, Object> validatedResult = null;
        String messageResult = null;
        Map<String, String> encryptSign = new HashMap<String, String>();    //封装验签参数
        encryptSign.put("idCardName", userName);
        encryptSign.put("idCardCode", certId);
        encryptSign.put("outOrderNo", UuidUtil.uuid());
        Map<String, String> requestToken = LklSubmit.getSign(encryptSign);  //验签结果
        Map<String, String> requestParameters = new HashMap<String, String>();  //封装请求参数
        requestParameters.put("customerId", lakalaCustomerId);
        requestParameters.put("sign", requestToken.get(LklDict.SIGN));
        requestParameters.put("reqData", requestToken.get(LklDict.REQDATA));
        requestParameters.put("prdGrpId", "identityQuery");
        if (null == type || 0 == type) {
            requestParameters.put("prdId", "queryIDCard");      //返照片
        } else {
            requestParameters.put("prdId", "validateIDCard");   //不返照片
        }
        try {
            messageResult =  HttpUtils.doPost(this.lakalGateway + "?_t=json", requestParameters, 30000, 30000);
        } catch (IOException e) {
            LOGGER.info("=========IO异常=======");
            e.printStackTrace();
        }
        if(messageResult != null) {
            boolean verifyFlag = LklVerify.verifyReturn(messageResult);
            if (verifyFlag) {
                validatedResult = LklSubmit.getResData(messageResult);
                validatedResult.put("retCode", (String) validatedResult.get("result"));
                validatedResult.put("retMsg", (String) validatedResult.get("message"));
//                LOGGER.info("=========验证成功=======" + validatedResult);
                LOGGER.info("=========验证成功=======" +"=====sha1卡====="+ sha1CertNo+"----"+ ReapalBase64Utils.encode(validatedResult.toString()));
            } else {
                validatedResult = JSONObject.fromObject(messageResult);
                LOGGER.info("=========验证失败=======" +"=====sha1卡====="+ sha1CertNo+"----"+ ReapalBase64Utils.encode(validatedResult.toString()));
            }
        }
        return validatedResult;
    }


}
