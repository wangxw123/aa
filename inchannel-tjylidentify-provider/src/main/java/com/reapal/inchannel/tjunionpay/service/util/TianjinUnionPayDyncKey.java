package com.reapal.inchannel.tjunionpay.service.util;

import com.reapal.cache.redis.service.RedisCacheService;
import com.reapal.common.util.StringUtil;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.tjunionpay.service.model.RequestData;
import com.reapal.inchannel.tjunionpay.service.model.ResponseData;
import com.reapal.inchannel.tjunionpay.service.util.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

@Service
public class TianjinUnionPayDyncKey {

	@Autowired
	private RedisCacheService redisCacheService;

	private static Logger LOGGER = Logger.getLogger(TianjinUnionPayDyncKey.class);

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("certs");

	static {
		System.setProperty("javax.net.ssl.keyStore", resourceBundle.getString("javax.net.ssl.keyStore"));
		System.setProperty("javax.net.ssl.keyStorePassword", resourceBundle.getString("javax.net.ssl.keyStorePassword"));
		System.setProperty("javax.net.ssl.keyStoreType", resourceBundle.getString("javax.net.ssl.keyStoreType"));
		System.setProperty("javax.net.ssl.trustStore", resourceBundle.getString("javax.net.ssl.trustStore"));
		System.setProperty("javax.net.ssl.trustStorePassword", resourceBundle.getString("javax.net.ssl.trustStorePassword"));
	}


    /**
     * Description: 获取请求动态秘钥
     *
     * @return java.lang.String
     * @Author liukai
     * @Data 2016/11/25 15:46
     */
    public String getDynKey() throws Exception {

        HttpsURLConnection.setDefaultHostnameVerifier(new TrustAnyVerifier());

        String resultString = "";
        HttpsURLConnection httpURLConnection = null;
        URL url = null;
        PrintStream out = null;
        try {
            //发送请求
            url = new URL(Constants.URL_DYN);
            httpURLConnection = (HttpsURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setReadTimeout(50000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            httpURLConnection.setRequestMethod("POST");
            try {
                httpURLConnection.connect();
                out = new PrintStream(httpURLConnection.getOutputStream(), false,
                        "UTF-8");
                out.print(getRandomPwdReq());
                out.flush();
            } catch (Exception e) {
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }


			//接收响应
			InputStream in = null;
			StringBuilder sb = new StringBuilder(1024);
			BufferedReader br = null;
			String temp = null;
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
				try{
					JSONObject jsonObject = new JSONObject(resultString);
					LOGGER.info(jsonObject.getString("status"));
					LOGGER.info(jsonObject.getString("msg"));
					/*
					 * 返回值前加数字，用于判断返回内容为动态密钥还是错误信息
					 * 00:正确信息
					 * 01:错误信息
					 * */
					return "01" + "状态：" + jsonObject.getString("status") + "信息：" + jsonObject.getString("msg");
				} catch(Exception e){
					LOGGER.info("应答报文不是JSON格式，进行正常处理！");
				}
				
				resultString = new String(DESedeUtil.decryptMode(Base64.decodeBase64(resultString.substring(29)), Constants.DES3_KEY, Constants.ENCODING_GBK), Constants.ENCODING_GBK);
				
				ResponseData response = XmlUtil.converyToJavaBean(resultString, ResponseData.class);
				LOGGER.info("秘钥随机数：" + response.getRandom());
				String dyn3DesKey = MD5Util.md5(Constants.DES3_KEY + response.getRandom(), Constants.ENCODING_GBK).substring(4, 28);
				LOGGER.info("秘钥：" + dyn3DesKey);

				Constants.DYN_3DES_KEY = dyn3DesKey;
				Constants.RANDOM = response.getRandom();

				String dynKeyCache = dyn3DesKey + "#" + response.getRandom();
				redisCacheService.set("dynKeyCache", dynKeyCache, 1800,"tjylidentify");

				/*
				 * 返回值前加数字，用于判断返回内容为动态密钥还是错误信息
				 * 00:正确信息
				 * 01:错误信息
				 * */
				return "00" + dyn3DesKey;
			}catch (Exception e) {
				throw e;
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
		}catch (Exception e1) {
			throw e1;
		}
	}

	/**
	 * Description: 请求动态报文
	 *      注意报文的userId只能填写渠道注册时银联分配的主用户ID，不能使用二级用户ID请求动态秘钥
	 * @Author liukai
	 * @Data 2016/11/24 19:56
	 * @return java.lang.String
	 */
	public static String getRandomPwdReq() throws Exception {
		String result = "";
		RequestData request = new RequestData();
		request.setCharCode(Constants.ENCODING_UTF8);
		request.setVersion("1.0.0");
		request.setTradeType("0413");
		request.setChnlId(Constants.CHANNEL_ID);
		request.setUserId(Constants.CHANNEL_ID);
		request.setTradeSource("1");
		request.setOrderId(OrderUtil.getDateTimeTemp() + OrderUtil.getRandomStr(2));
		request.setTimeStamp(OrderUtil.getDateTimeTemp());
		request.setMd5ConSec(TransMsgUtil.genMd5ConSec(request));
		
		String xmlStr = XmlUtil.convertToXml(request);
		LOGGER.info("组装XML报文");
		LOGGER.info(xmlStr);
		
		byte[] xmlByte = DESedeUtil.encryptMode(xmlStr.getBytes(Constants.ENCODING_UTF8), Constants.DES3_KEY, Constants.ENCODING_UTF8);
		result = Constants.CHANNEL_ID + Constants.CHANNEL_ID + "1" + Base64.encodeBase64String(xmlByte);
		result = TransMsgUtil.addZeroForNum(String.valueOf(xmlByte.length + 25), 4) + result;
		LOGGER.info("请求报文：" + result);
		return result;
	}

	/**
	 * Description:  检验 DyncKey 是否过期
	 * @Author Administrator
	 * @Data 2016/11/25 15:47
	 * @return boolean
	 */
	public boolean checkDyncKey() {
		boolean isExpire = false;
		String expireKey = redisCacheService.get("dynKeyCache", "tjylidentify");
        LOGGER.info("====当前缓存密钥===" + expireKey);
		try {
			if (StringUtils.isNotEmpty(expireKey)) {
                String[] keys = expireKey.split("#");
				String dyn3DesKey = keys[0];
				String random = keys[1];
				Constants.DYN_3DES_KEY = dyn3DesKey;
				Constants.RANDOM = random;
				return true;
			} else {
                LOGGER.info("====密钥过期或不存在，重新获取密钥===");
				this.getDynKey();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExpire;
	}
}
