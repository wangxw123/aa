package com.reapal.inchannel.cmbcpayxm.service.impl;

import com.reapal.common.util.BankCodeUtils;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.cmbcpayxm.service.CmbcpayxmIdentifyService;
import com.reapal.inchannel.cmbcpayxm.util.*;
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

import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by dell on 2015/8/9.
 */
@Service
public class CmbcpayxmIdentifyServiceImpl implements CmbcpayxmIdentifyService {

//    private static Properties ppsidentify = new Properties();
    private static Log logger = LogFactory.getLog(CmbcpayxmIdentifyServiceImpl.class);

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

    private static String businessType = resourceBundle.getString("businessType");
    private static String acquirerCode = resourceBundle.getString("acquirerCode");

    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

//    static {
//        try {
//            logger.info("【读取配置文件】");
//            //读取配置信息
//            InputStream in = new ClassPathResource("cmbcxmIdentify.properties").getInputStream();
//            ppsidentify.load(in);
//        } catch (Exception e) {
//            logger.info("【系统初始化失败】"+e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req){
        logger.info("======【cmbcpayxm鉴权请求参数】======" + req.toBase64String());
        InchannelIdentifyResponse response=new InchannelIdentifyResponse();
        String message = "";
        String bankCode = BankCodeUtils.getBankCodeByCode(req.getBankCode());
        try {
            IdentifyReq identifyReq = new IdentifyReq();
            Date date=new Date();
            identifyReq.setSerialNo(BasicConst.getSerialNo(date));
            identifyReq.setTradeCode(BasicConst.TradeCode_IDENTIFY);//实名身份认证交易码
            identifyReq.setCardType("1".equals(req.getDcType())?"2":"0");//0-	借记卡（默认）1-	存折	2-	贷记卡（信用卡）3-	公司账号
            identifyReq.setAccNo(req.getCardNo());//卡号
            identifyReq.setAccName(req.getOwner());//姓名
            identifyReq.setCertType("ZR01");//证件类型
            identifyReq.setCertNo(req.getCertNo().toUpperCase());//证件号码
            identifyReq.setPhone(req.getPhone());//手机号
            identifyReq.setPayerBankInsCode(bankCode);//银联机构号
            identifyReq.setProvNo("110000");//付款省份编码
            //identifyReq.setResv("test");//扩展字段
            String serialNo= identifyReq.getSerialNo();


            identifyReq.createXml();
            String strXml = identifyReq.getRequestXml();

            logger.info("发送的XML数据=======XML====：" + strXml);
            // 初始化 公私钥
            RSAHelper.initKey(2048);
            // 生成发送报文
            byte[] str = identifyReq.sendData();
            logger.info("发送的byte[]数据=======000000000====：" + str.length);
            // 发送并接受数据
            message = Cient.sendMessage(str);
//
            logger.info("=========收到的数据========：" + message);

            //解析并组装response
            if(StringUtils.isNotEmpty(message) && message.length()>4  && BasicConst.TradeCode_IDENTIFY.trim().equals(message.substring(0,4))){
                Map<String, String> paraMap = XmlUtil.parseXML(message.substring(4));
                logger.info("=========厦门民生返回的鉴权XML数据：" + "\n" + message);
                if ("00".equals(paraMap.get("ValidateStatus")) && "S".equals(paraMap.get("ExecType")) && serialNo.equals(paraMap.get("ReqSerialNo")) ) {
                    response.setResult("1");
                    response.setResultCode("0000");
                    response.setResultMsg("鉴权成功");
                    response.setChannelCode(paraMap.get("ExecType")+paraMap.get("ExecCode")+paraMap.get("ValidateStatus"));
					response.setChannelMsg(paraMap.get("ExecMsg"));
                } else {

                    String retCode = paraMap.get("ExecType")+paraMap.get("ExecCode")+paraMap.get("ValidateStatus");
                    String retMsg = paraMap.get("ExecMsg");
                    response.setChannelCode(retCode);
                    response.setChannelMsg(retMsg);

                    InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
                    inchannelErrorCode.setChannelCode(retCode);
                    inchannelErrorCode.setChannelMsg(retMsg);
                    inchannelErrorCode.setBusinesstype(businessType);
                    inchannelErrorCode.setAcquirerCode(acquirerCode);
                    ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);

                    if(resultErrorCode != null){
                        response.setResultCode(resultErrorCode.getResultCode());
                        response.setResultMsg(resultErrorCode.getResultMsg());
                        response.setResult(resultErrorCode.getStatus());
                    }else{
                        logger.info("========厦门民生鉴权接口匹配不能匹配到错误码========");
                        response.setResult("0");
                        response.setResultCode("3067");
                        response.setResultMsg("其它");
                    }

                }
            }else {
                response.setResult("2");
                response.setResultCode("2010");
                response.setResultMsg("银行超时，请重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            response.setResultCode("1049");
            response.setResultMsg("银行超时，请稍后再试");
        }
        logger.info("======【cmbcpayxm鉴权返回的结果】======" + response.toBase64String());
        return  response;
    }
}
