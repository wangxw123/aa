package com.reapal.inchannel.tjrh.service.impl;

import com.reapal.common.util.Base64;
import com.reapal.common.util.Sha1Utils;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.inchannel.tjrh.service.TjrhIdentifyService;
import com.reapal.inchannel.tjrh.util.HttpClient;
import com.reapal.inchannel.tjrh.util.PropertiesUtil;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoguangxiao on 17/1/23.
 */
@Service
public class TjrhIdentifyServiceImpl implements TjrhIdentifyService{

    private static Log logger = LogFactory.getLog(TjrhIdentifyServiceImpl.class);
    private static String SERVICE_URL = PropertiesUtil.getResourcesconfig().getString("auth_service_url");
    private static String USERNAME = PropertiesUtil.getResourcesconfig().getString("username");
    private static String USERPWD = PropertiesUtil.getResourcesconfig().getString("userpwd");
    private static String X_API_KEY = PropertiesUtil.getResourcesconfig().getString("x_api_key");
    private static String businessType = PropertiesUtil.getResourcesconfig().getString("businessType");
    private static String acquirerCode = PropertiesUtil.getResourcesconfig().getString("acquirerCode");

    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

    @Override
    public InchannelIdentifyResponse realName(InchannelIdentifyRequest identifyRequest) {

        InchannelIdentifyResponse response = new InchannelIdentifyResponse();
        //二要素鉴权
        String idCard = identifyRequest.getCertNo();
        String name = identifyRequest.getOwner();//姓名
        String sha1CertNo = Sha1Utils.HexString(idCard);
        logger.info("======【天津人行二要素鉴权接口】======sha1证==" + sha1CertNo + "==提交参数密文==" + identifyRequest.toBase64String());

        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("id_card",idCard);
        paramMap.put("uname",name);
        paramMap.put("Authorization",  "Basic " + Base64.getBase64(USERNAME + ":" + USERPWD));
        paramMap.put("X-API-KEY", X_API_KEY);

        logger.info("=====【天津人行二要素鉴权请求报文】=====sha1证==" + sha1CertNo + "==请求参数=="+ com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(paramMap.toString().getBytes()));
        String result = HttpClient.post(SERVICE_URL,paramMap);
        logger.info("=====【天津人行二要素鉴权返回报文】=====" + result);

        JSONObject retJson = JSONObject.fromObject(result);
        String retCode = retJson.getString("code");
        String retMsg = retJson.getString("msg");
        try {
            retMsg = new String(retMsg.getBytes("Unicode"),"UTF-16");
        } catch (UnsupportedEncodingException e) {
            logger.info("======【天津人行二要素鉴权接口】异常========");
            e.printStackTrace();
        }
        response.setChannelCode(retCode);
        response.setChannelMsg(retMsg);
        if("100".equals(retCode)){
            response.setResultMsg("鉴权成功");
            response.setResult("1");
            response.setResultCode("0000");
        }else{
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
                logger.info("========【天津人行二要素鉴权接口】匹配不到错误码========");
                response.setResult("0");
                response.setResultCode("3067");
                response.setResultMsg("其它");
            }
        }
        return response;
    }

    public static void main(String[] args){
        TjrhIdentifyServiceImpl service = new TjrhIdentifyServiceImpl();
        InchannelIdentifyRequest identifyRequest = new InchannelIdentifyRequest();
        identifyRequest.setCertNo("370783199004121912");
        identifyRequest.setOwner("郭光晓");
        System.out.println(service.realName(identifyRequest));
    }
}
