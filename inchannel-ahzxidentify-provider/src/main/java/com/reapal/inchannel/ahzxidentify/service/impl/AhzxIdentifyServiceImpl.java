package com.reapal.inchannel.ahzxidentify.service.impl;

import com.reapal.common.util.JsonUtil;
import com.reapal.common.util.ReapalBase64Utils;
import com.reapal.common.util.Sha1Utils;
import com.reapal.common.util.StringUtil;
import com.reapal.inchannel.ahzxidentify.service.AhzxIdentifyService;
import com.reapal.inchannel.ahzxidentify.utils.AhzxAES;
import com.reapal.inchannel.ahzxidentify.utils.HttpClientUtil;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.trade.model.InchannelErrorCode;
import com.reapal.trade.model.ResultErrorCode;
import com.reapal.trade.service.CashierInchannelCodeService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/3/20.
 */
@Service
public class AhzxIdentifyServiceImpl implements AhzxIdentifyService {
    private static Logger logger = LoggerFactory.getLogger(AhzxIdentifyServiceImpl.class);
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    private String MORE_TYPE = resourceBundle.getString("moreType");
    private String acquirerCode = resourceBundle.getString("acquirerCode");
    private String url = resourceBundle.getString("url");
    private String HEX_AES_128_PASSWORD = resourceBundle.getString("HEX_AES_128_PASSWORD");
    private String unitId = resourceBundle.getString("unitId");
    private String customerId = resourceBundle.getString("customerId");
    private String productId = resourceBundle.getString("productId");
    private String loginName = resourceBundle.getString("loginName");
    private String orderId = resourceBundle.getString("orderId");
    private String afemarkId = resourceBundle.getString("afemarkId");



    @Autowired
    private CashierInchannelCodeService cashierInchannelCodeService;

    @Override
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req) {
        long beginTime = System.currentTimeMillis();
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        String tradeDate = format.format(new Date());
        logger.info("[RBRequestParameter] - [{}] - [{}] - [{}] - [{}] - body={} ", "identify", "identify", acquirerCode, Sha1Utils.HexString(req.getCardNo()), req.toBase64String());

        InchannelIdentifyResponse inchannelIdentifyResponse = new InchannelIdentifyResponse();
        try {
//            String url = "http://36.7.144.13:53033/P_C_B0201";
//            String HEX_AES_128_PASSWORD = "620ec1c5fd13c1b34298a241054f9eff";



            AhzxAES AhzxAES = new AhzxAES(Hex.decodeHex(HEX_AES_128_PASSWORD.toCharArray()));
            Map<String, String> params = new HashMap<String, String>();

            params.put("idType", "01");
            params.put("idNo", req.getCertNo());
            params.put("custName", req.getOwner());
            params.put("phoneNo", req.getPhone());
            params.put("bankCardno", req.getCardNo());

            //接口号，安徽征信提供
            params.put("interfaceNo", "bankThrOrFourCheck");  //
            params.put("unitId", unitId);   //接入机构号，安徽征信提供
            params.put("customerId", customerId);   //客户id，安徽征信提供
            params.put("productId", productId);  //产品id，安徽征信提供
            params.put("loginName", loginName);  //客户名称，安徽征信提供
            params.put("orderId", orderId); //产品订单编号，安徽征信提供
            params.put("channel", "001");
            params.put("department", "10");
            params.put("tradeDate", tradeDate);

            String paramJson = JsonUtil.toJson(params);
//            String paramJson = JSON.toJSONString(params);
            logger.info("请求明文(json)：paramJson=" + ReapalBase64Utils.encode(paramJson));
            if(StringUtil.isNotEmpty(req.getPhone())){
                logger.info("======【安徽四要素鉴权】======");
            }else{
                logger.info("======【安徽三要素鉴权】======");
            }
            // 2.1 AES 128
            byte[] encryptBytes = AhzxAES.encrypt(paramJson);
            // 2.2 BASE64
            String base64String = new sun.misc.BASE64Encoder().encode(encryptBytes);

            logger.info("请求报文：base64String=" + base64String);
            String response=null;

            Map<String, String> heads = new HashMap<String, String>();
            heads.put("afemarkId", afemarkId);  //客户名称需要加入到请求头==生产
            response = HttpClientUtil.post(url, base64String, heads, "application/json");

//            logger.info("=========response==========" + response);

            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            byte[] encryptAESResponseByte = decoder.decodeBuffer(response);
            byte[] responseByte = AhzxAES.decrypt(encryptAESResponseByte);
            String result = new String(responseByte,"utf-8");

//            logger.info("==================result============" + result);
            logger.info("渠道{}，卡号{}，银行返回明文result={}", acquirerCode, Sha1Utils.HexString(req.getCardNo()), result);

            JSONObject jsonObject = JSONObject.fromObject(result);
            logger.info("==================jsonObject============" + jsonObject);

            String code =jsonObject.get("code").toString();
            String msg =jsonObject.get("msg").toString();
            inchannelIdentifyResponse.setChannelCode(code);
            inchannelIdentifyResponse.setChannelMsg(msg);
            if(code != null && "40100".equals(code)|| "30100".equals(code)){
                inchannelIdentifyResponse.setResult("1");
                inchannelIdentifyResponse.setResultCode("0000");
                inchannelIdentifyResponse.setResultMsg("鉴权成功");
            }else{
                InchannelErrorCode inchannelErrorCode = new InchannelErrorCode();
                inchannelIdentifyResponse.setResult("2");
                inchannelErrorCode.setChannelCode(code);
                inchannelErrorCode.setChannelMsg(msg);
                inchannelErrorCode.setBusinesstype("141030");
                inchannelErrorCode.setProductId(MORE_TYPE);
                inchannelErrorCode.setAcquirerCode(acquirerCode);
                ResultErrorCode resultErrorCode = cashierInchannelCodeService.matchingErrorCode(inchannelErrorCode);
                if (null != resultErrorCode) {
                    inchannelIdentifyResponse.setResultCode(resultErrorCode.getResultCode());
                    inchannelIdentifyResponse.setResultMsg(resultErrorCode.getResultMsg());
                    inchannelIdentifyResponse.setResult(resultErrorCode.getStatus());
                } else {
                    logger.info("========认证接口匹配错误码出现异常========");
                    inchannelIdentifyResponse.setResultCode("3067");
                    inchannelIdentifyResponse.setResultMsg("其他");
                }
            }

//            logger.info("==================inchannelIdentifyResponse============" + inchannelIdentifyResponse.toString());
        } catch (Exception e) {
            logger.error("[RBException] - [{}] - [{}] - errorMsg:[{}] - body={}", "identify", "identify", e.getMessage(), req.getTradeNo(),e);

            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        logger.info("[RBResponseParameter] - [{}] - [{}] - [{}] - [{}] - cardhash:[{}] - cardType:[{}] - bank:[{}] - channel:[{}] - returnCode:[{}] - returnMsg:[{}] - duration:[{}] - body={} ",
                "identify", "identify", acquirerCode, req.getTradeNo(), Sha1Utils.HexString(req.getCardNo()), "null", req.getBankCode(), acquirerCode, inchannelIdentifyResponse.getResultCode(), inchannelIdentifyResponse.getResultMsg(), (endTime - beginTime), inchannelIdentifyResponse.toBase64String());

        return inchannelIdentifyResponse;
    }
}
