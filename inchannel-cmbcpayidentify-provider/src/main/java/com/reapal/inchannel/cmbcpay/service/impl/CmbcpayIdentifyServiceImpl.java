package com.reapal.inchannel.cmbcpay.service.impl;

import com.cmbc.proxyfees.client.wart.trans.TopPayLink;
import com.cmbc.proxyfees.client.wart.trans.msg.RemitTransReqMsg;
import com.cmbc.proxyfees.client.wart.trans.msg.RemitTransRspMsg;
import com.cmbc.proxyfees.common.static_data.MerchantConfiguration_data;
import com.reapal.common.util.DateUtil;
import com.reapal.common.util.ReapalBase64Utils;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.cmbcpay.model.RBRemitTransReqMsg;
import com.reapal.inchannel.cmbcpay.service.CmbcpayIdentifyService;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

/**
 * Created by huangg on 2015/2/4.
 */
@Service
public class CmbcpayIdentifyServiceImpl implements CmbcpayIdentifyService {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(CmbcpayIdentifyServiceImpl.class);
    private ResourceBundle para = ResourceBundle.getBundle("cmbc");
    private String acquirerCode = para.getString("acquirerCode");
    private String storeURL = para.getString("storeURL");
    private String ssLJksURL = para.getString("ssLJksURL");
    private String dir = para.getString("dir");
    String[] serUrl=new String[]{para.getString("serUrl")};
    private String password = para.getString("password");
    private String merchentId = para.getString("merchentId");
    private String productId = para.getString("productId");
    private String businesstype = para.getString("businesstype");


    private static int sNum = 0;


    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;
    @Override
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req) {
        long beginTime = System.currentTimeMillis();
        log.info("[RBRequestParameter] - [{}] - [{}] - [{}] - [{}] - body={} ", "identify", "identify", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), req.toBase64String());

//        String storeURL = "/data0/reapal/inchannel/cmbcpay/tjrbzf@100110000010047.p12";
//        String ssLJksURL = "/data0/reapal/inchannel/cmbcpay/tjrbzf@100110000010047.jks";
//        String dir = "/data0/reapal/inchannel/cmbcpay/jssecacerts";
//
//        String storeURL = "D:\\cert\\cmbc\\tjrbzf@100110000010047.p12";
//        String ssLJksURL = "D:\\cert\\cmbc\\tjrbzf@100110000010047.jks";
//        String dir = "D:\\cert\\cmbc\\shengchancert/cmbc/jssecacerts";

//        String password = "tjrbzf123456";
//        String merchentId = "100110000010047";        //机构号（3位）+地区代码（4位）+商户类型（4位）+商户序列号（4位）
//        String[] serUrl = {"https://118.122.92.62:8443/cmbcproxyfees"};
        int port = Integer.parseInt("8443");
        long currentTime = System.currentTimeMillis();
        log.info("渠道{}，银行卡号{}，【民生鉴权接口开始执行】", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
        InchannelIdentifyResponse identifyResponse = new InchannelIdentifyResponse();
        //2、组装请求参数
        String startNum = "";
        String cardNo = req.getCardNo();            //卡号
        String usrName = req.getOwner();            //姓名
        String cardPhone = req.getPhone();        //手机号
        String certType = req.getCertType();        //证件类型   01：身份证  02：军官证； 03：护照；04: 回乡证；05: 台胞证；06: 警官证；07: 士兵证；99: 其他证件
        if (StringUtils.isEmpty(certType)) certType = "01";
        String certNo = req.getCertNo();            //证件号
        //订单号  1-4位 CUPSecure商户代码中地区代码	 5-8位    CUPSecure商户代码中商户序列号 9-24位     16位订单顺序号
        if (sNum > 99) sNum = 0;
        if (sNum < 9) startNum = "0" + sNum;
        else startNum = String.valueOf(sNum);
        String merOrderNum = "11000047" + DateUtil.getCurrTimeYMDHMS() + startNum;
        sNum++;
        new MerchantConfiguration_data(storeURL, ssLJksURL, password, merchentId, serUrl, "", false, port);
        System.setProperty("javax.net.ssl.trustStore", dir);
        RemitTransReqMsg msg = new RemitTransReqMsg();
        msg.setMerchantID(merchentId);
        msg.setBusinessType("1001");                //业务类型:1001
        msg.setMerOrderNum(merOrderNum);
        msg.setPan(cardNo);
        msg.setCardName(usrName);
        msg.setPayerTel(cardPhone);
        msg.setCardKind(certType);
        msg.setCardId(certNo);
        msg.setTranDateTime(DateUtil.getCurrTimeYMDHMS());
        msg.setTermId("");
        msg.setMsgExt("");
        msg.setMisc("");
        msg.setRespCode("");
        msg.setRespDesc("");
        msg.setSettleDate("");
        msg.setCupsTraceNum("");
        msg.setCupsSysTime("");
        RemitTransRspMsg remitTransRspMsg = new RemitTransRspMsg();
        TopPayLink link = new TopPayLink();
        RBRemitTransReqMsg rbRemitTransReqMsg=new RBRemitTransReqMsg();
        try {
            BeanUtils.copyProperties(rbRemitTransReqMsg,msg);
            log.info("渠道{}，银行卡号{}，发送银行明文SecureLink={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), ReapalBase64Utils.encode(rbRemitTransReqMsg.toString()));

            remitTransRspMsg = link.remitTrans(msg);
            identifyResponse.setChannelNo("2");
            identifyResponse.setCardNo(cardNo);
            identifyResponse.setOwner(usrName);
            identifyResponse.setCertType(certType);
            identifyResponse.setCertNo(certNo);
            identifyResponse.setPhone(cardPhone);

            String  respCode=remitTransRspMsg.getRespCode();
            String respDesc=remitTransRspMsg.getRespDesc();
            log.info("渠道{}，银行卡号{}，银行返回明文collPayTransRspMsg={}", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), "respCode="+remitTransRspMsg.getRespCode()+"  respDesc="+remitTransRspMsg.getRespDesc());

            if ("PF0000".equals(remitTransRspMsg.getRespCode())) {
                RemitTransReqMsg Msg = remitTransRspMsg.getRemitTransReqMsg();
                String retCode = Msg.getRespCode();
                String retMsg = Msg.getRespDesc();
                log.info("渠道{}，银行卡号{}，交易应答成功！", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));

                if("000000".equals(retCode)){
                    log.info("渠道{}，银行卡号{}，鉴权成功！", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    identifyResponse.setResultCode("0000");
                    identifyResponse.setResultMsg("鉴权成功");
                    identifyResponse.setResult("1");
                }else{
                    log.info("渠道{}，银行卡号{}，鉴权失败！", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                    InchannelIdentifyResponse response = this.packResult(retCode, retMsg,productId,req.getCardNo());

                    identifyResponse.setResultCode(response.getResultCode());
                    identifyResponse.setResultMsg(response.getResultMsg());
                    identifyResponse.setResult(response.getResult());

                }

                identifyResponse.setChannelCode(Msg.getRespCode());
                identifyResponse.setChannelMsg(Msg.getRespDesc());
            }else{

                log.info("渠道{}，银行卡号{}，交易应答失败！", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()));
                identifyResponse.setChannelCode(respCode);
                identifyResponse.setChannelMsg(respDesc);

                log.info("渠道{}(成都民生银行鉴权)，银行卡号{}(reapal)，银行{}，错误码匹配", acquirerCode,ReapalBase64Utils.encode(req.getCardNo()),req.getBankCode());
                InchannelIdentifyResponse response = this.packResult(respCode, respDesc,productId,req.getCardNo());

                identifyResponse.setResultCode(response.getResultCode());
                identifyResponse.setResultMsg(response.getResultMsg());
                identifyResponse.setResult(response.getResult());

            }
        } catch (Exception e) {
            log.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "identify", "identify", e.getMessage(), ReapalBase64Utils.encode(req.getCardNo()),e);
            identifyResponse.setResultCode("1049");
            identifyResponse.setResultMsg("银行超时，请稍后再试");
        }

//        if (StringUtils.isEmpty(identifyResponse.getResultCode())) {
//            log.info("渠道{}，银行卡号{}，返回编码为空，设置返回编码！", acquirerCode, req.getCardNo());
//            identifyResponse.setResultCode("1049");
//        }
//        if (StringUtils.isEmpty(identifyResponse.getResultMsg())) {
//            log.info("渠道{}，银行卡号{}，返回鉴权信息为空，设置返回信息！", acquirerCode, req.getCardNo());
//            identifyResponse.setResultMsg("银行超时，请稍后再试");
//        }
        long endTime = System.currentTimeMillis();
        log.info("[RBResponseParameter] - [{}] - [{}] - [{}] - [{}] - cardhash:[{}] - cardType:[{}] - bank:[{}] - channel:[{}] - returnCode:[{}] - returnMsg:[{}] - duration:[{}] - body={} ",
                "identify", "identify", acquirerCode, ReapalBase64Utils.encode(req.getCardNo()), Sha1Utils.HexString(req.getCardNo()), req.getCertType(), req.getBankCode(), acquirerCode, identifyResponse.getResultCode(), identifyResponse.getResultMsg(), (endTime - beginTime), identifyResponse.toBase64String());

        return identifyResponse;
    }


    /**
     * 封装返回信息
     *
     * @param channelCode
     * @param channelmsg
     * @return
     */
    public InchannelIdentifyResponse packResult(String channelCode, String channelmsg,String productId, String cardNo) {

        InchannelIdentifyResponse identifyResponse = new InchannelIdentifyResponse();
        if (StringUtils.isEmpty(channelCode)) {
            log.info("渠道{}，银行卡号{}，认证接口返回参数异常！", acquirerCode, ReapalBase64Utils.encode(cardNo));
            identifyResponse.setResultCode("3067");
            identifyResponse.setResultMsg("其它");
            identifyResponse.setResult("2");
        } else {
            InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
            inchannelErrorCode.setChannelCode(channelCode);
            inchannelErrorCode.setChannelMsg(channelmsg);
            inchannelErrorCode.setProductId(productId);
            inchannelErrorCode.setBusinesstype(businesstype);
            inchannelErrorCode.setAcquirerCode(acquirerCode);

            ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);

            if (null != resultErrorCode) {
                log.info("渠道{}，银行卡号{}，认证接口匹配到错误码！", acquirerCode, ReapalBase64Utils.encode(cardNo));
                identifyResponse.setResultCode(resultErrorCode.getResultCode());
                identifyResponse.setResultMsg(resultErrorCode.getResultMsg());
                identifyResponse.setResult(resultErrorCode.getStatus());
            } else {
                identifyResponse.setResultCode("3067");
                identifyResponse.setResultMsg("其它");
                identifyResponse.setResult("2");
            }
        }
        return identifyResponse;
    }
}
