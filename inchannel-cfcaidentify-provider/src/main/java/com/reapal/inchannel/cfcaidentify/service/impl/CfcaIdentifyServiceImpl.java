package com.reapal.inchannel.cfcaidentify.service.impl;


import cfca.etl.common.client.exception.ClientException;
import cfca.etl.uaclient.UAClient;
import cfca.etl.uaserver.vo.request.BankCardValidateVO;
import cfca.etl.uaserver.vo.request.IdentificationValidateVO;
import cfca.etl.uaserver.vo.response.BankCardStatusVO;
import cfca.etl.uaserver.vo.response.IdentificationStatusVO;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.cfcaidentify.service.CfcaIdentifyService;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

/**
* Created by dell on 2014/12/29.
*/
@Service
public class CfcaIdentifyServiceImpl implements CfcaIdentifyService {

    private static Log log = LogFactory.getLog(CfcaIdentifyServiceImpl.class);
    private static int connectTimeout = 3000;
    private static int readTimeout = 120000;

//    private ResourceBundle resb = ResourceBundle.getBundle("application");
    private ResourceBundle resb = ResourceBundle.getBundle("application");

    private String url = this.resb.getString("url");
    private String keyStorePath = this.resb.getString("keyStorePath");
    private String keyStorePassword = this.resb.getString("keyStorePassword");
    private String trustStorePath = this.resb.getString("trustStorePath");
    private String trustStorePassword = this.resb.getString("trustStorePassword");
    private String acquirerCode = this.resb.getString("acquirerCode");

    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req){

        long currDate = System.currentTimeMillis();
        if(null == req.getCardNo() || "" == req.getCertNo()){
            log.info("======【cfcaidentify鉴权接口】======" + currDate + "====sha1卡=====【cfcaidentify参数】======" + req.toBase64String());
        }else {
            log.info("======【cfcaidentify鉴权接口】======" + currDate + "===sha1卡====" + Sha1Utils.HexString(req.getCardNo() == null ? "" : req.getCardNo()) + "===【cfcaidentify参数】======" + req.toBase64String());
        }
        log.info("======【cfcaidentify鉴权接口】======" + req.getDcType());

        InchannelIdentifyResponse  identifyResponse = new InchannelIdentifyResponse();

        String subscriberName =  req.getOwner();
        String identificationNo = req.getCertNo();
        String identificationTypeCode = "0"; //证件类型，我们是01代表身份证，该渠道默认0-代表身份证
       // req.getCertType();
        String phoneNo =  req.getPhone();
        String accountNo =  req.getCardNo();
        String dcType = req.getDcType();
        identifyResponse.setOwner(subscriberName);
        identifyResponse.setPhone(phoneNo);
        identifyResponse.setCertNo(identificationNo);
        identifyResponse.setCardNo(accountNo);
        try {
            // 创建UAClient后执行initSSL方法初始化SSL环境，之后多次调用可使用同一UAClient对象
            UAClient client = new UAClient(url, connectTimeout, readTimeout);
            client.initSSL(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword);
            // 如需指定ssl协议、算法、证书库类型，使用如下方式
            // client.initSSL(keyStorePath, keyStorePassword, trustStorePath,
            // trustStorePassword, "SSL", "IbmX509", "IbmX509", "JKS", "JKS");

            BankCardValidateVO bankCardValidateVO = new BankCardValidateVO();
            if(StringUtils.isNotEmpty(phoneNo)){
                log.info("==========四要素鉴权开始========");
                //四要素
                bankCardValidateVO.setTxCode("05001");
                bankCardValidateVO.setPhoneNo(phoneNo);
            }else{
                log.info("==========手机号为空，三要素鉴权开始========");
                //三要素
                bankCardValidateVO.setTxCode("05004");
            }
            bankCardValidateVO.setAccountNo(accountNo);
            bankCardValidateVO.setSubscriberName(subscriberName);
            bankCardValidateVO.setIdentificationNo(identificationNo);
            bankCardValidateVO.setIdentificationTypeCode(identificationTypeCode);

            bankCardValidateVO.setCardType(UAClient.CardType.DEBIT);
//            if("0".equals(dcType)){
//                bankCardValidateVO.setCardType(UAClient.CardType.DEBIT);
//            }else if("1".equals(dcType)){
//                bankCardValidateVO.setCardType(UAClient.CardType.CREDIT);
//            }else{
//                identifyResponse.setResultCode("1049");
//                identifyResponse.setResultMsg("银行超时，请稍后再试");
//                return identifyResponse;
//            }

            BankCardStatusVO bankCardStatusVO = (BankCardStatusVO) client.process(bankCardValidateVO);

            log.info("===========cfca鉴权返回信息=============="+bankCardStatusVO.getResultCode()+"="+bankCardStatusVO.getResultMessage());
            String channelCode = bankCardStatusVO.getResultCode();
            String channelMsg = bankCardStatusVO.getResultMessage();
            identifyResponse.setChannelCode(channelCode);
            identifyResponse.setChannelMsg(channelMsg);
            if ("00000000".equals(channelCode)) {
                identifyResponse.setResultCode("0000");
                identifyResponse.setResultMsg("鉴权成功");
            }else{
                log.info("===========查数据库==============");
                InchannelIdentifyResponse response = this.packResult(channelCode, channelMsg, "103040");
                if (null != response) {
                    identifyResponse.setResultCode(response.getResultCode());
                    identifyResponse.setResultMsg(response.getResultMsg());
                    identifyResponse.setResult(response.getResult());
                } else {
                    identifyResponse.setResult("2");
                    identifyResponse.setResultCode("3067");
                    identifyResponse.setResultMsg("其它");
                }
            }
        } catch (ClientException e) {
            e.printStackTrace();
            identifyResponse.setResultCode("1049");
            identifyResponse.setResultMsg("银行超时，请稍后再试");
        }

        log.info("=========【cfcaidentify鉴权返回值】===========" + identifyResponse.toString() + "交易流水号:" + req.getTradeNo() + "消耗时间:" + (System.currentTimeMillis() - currDate) );
        return identifyResponse;
    }

    @Override
    public InchannelIdentifyResponse  realName(InchannelIdentifyRequest req) {

        long currDate = System.currentTimeMillis();
        if(null == req.getCertNo() || "" == req.getCertNo()) {
            log.info("======【cfcaidentify鉴权接口】======" + currDate + "=====sha1证=======【cfcaidentify参数】======" + req.toBase64String());
        }else{
            log.info("======【cfcaidentify鉴权接口】======" + currDate + "=====sha1证====="+ Sha1Utils.HexString(req.getCertNo())+"=====【cfcaidentify参数】======" + req.toBase64String());
        }

//        log.info("======【cfcaidentify鉴权接口】======" + req.getDcType());

        InchannelIdentifyResponse  identifyResponse = new InchannelIdentifyResponse();

        String subscriberName =  req.getOwner();
        String identificationNo = req.getCertNo();
        identifyResponse.setOwner(subscriberName);
        identifyResponse.setCertNo(identificationNo);

        try {
            // 创建UAClient后执行initSSL方法初始化SSL环境，之后多次调用可使用同一UAClient对象
            UAClient client = new UAClient(url, connectTimeout, readTimeout);
            client.initSSL(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword);
            // 如需指定ssl协议、算法、证书库类型，使用如下方式
            // client.initSSL(keyStorePath, keyStorePassword, trustStorePath,
            // trustStorePassword, "SSL", "IbmX509", "IbmX509", "JKS", "JKS");

            IdentificationValidateVO identificationValidateVO = new IdentificationValidateVO();
            identificationValidateVO.setTxCode("04001");
            // personValidateVO.setTimestamp(timestamp);
            // personValidateVO.setLocale(locale);
            identificationValidateVO.setSubscriberName(subscriberName);
            identificationValidateVO.setIdentificationNo(identificationNo);

            IdentificationStatusVO identificationStatusVO = (IdentificationStatusVO) client.process(identificationValidateVO);

            String channelCode = identificationStatusVO.getResultCode();
            String channelMsg = identificationStatusVO.getResultMessage();
            if ("00000000".equals(channelCode)) {
                identifyResponse.setResultCode("0000");
                identifyResponse.setResultMsg("认证成功");
                log.info("======成功返回信息=========" + identificationStatusVO.getResultCode()+"=="+identificationStatusVO.getResultMessage());
            }else{
                InchannelIdentifyResponse response = this.packResult(channelCode, channelMsg, "103010");
                if (null != response) {
                    identifyResponse.setResultCode(response.getResultCode());
                    identifyResponse.setResultMsg(response.getResultMsg());
                    identifyResponse.setResult(response.getResult());
                } else {
                    identifyResponse.setResult("2");
                    identifyResponse.setResultCode("3067");
                    identifyResponse.setResultMsg("其它");
                }
            }

            identifyResponse.setChannelCode(channelCode);
            identifyResponse.setChannelMsg(channelMsg);
        } catch (ClientException e) {
            e.printStackTrace();
            identifyResponse.setResultCode("1049");
            identifyResponse.setResultMsg("银行超时，请稍后再试");
        }
        log.info("=========【cfcaidentify鉴权返回值】===========" + identifyResponse.toString() + "交易流水号:" + req.getTradeNo() + "消耗时间:" + (System.currentTimeMillis() - currDate) );
        return identifyResponse;
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
