package com.reapal.inchannel.zcxzxidentify.service.impl;

import com.alibaba.fastjson.JSON;
import com.reapal.common.util.StringUtils;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import com.reapal.inchannel.zcxzxidentify.service.ZhongchengxinzhengxinIdentifyService;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HttpContext;
import org.omg.CORBA.NameValuePair;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.*;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.apache.log4j.Logger;

/**
 * @The author  zhongyuting
 * <p/>
 * 中诚信征信二.三.四要素鉴权
 */
@Service
public class ZhongchengxinzhengxinIdentifyServiceImpl implements ZhongchengxinzhengxinIdentifyService {
    private static Logger log = Logger.getLogger(ZhongchengxinzhengxinIdentifyServiceImpl.class);
    private ResourceBundle para = ResourceBundle.getBundle("zcxzx");
    private String account = this.para.getString("zcxzx.rongbao.account");
    private String privateKey = this.para.getString("zcxzx.rongbao.privateKey");
    private String url_3 = this.para.getString("zcxzx.rongbao.url_3");
    private String url_4 = this.para.getString("zcxzx.rongbao.url_4");
    private String url_2 = this.para.getString("zcxzx.rongbao.url_2");
    private String url_pho = this.para.getString("zcxzx.rongbao.url_pho");


    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req) {

        long currDate = System.currentTimeMillis();
        log.info("======【中诚信征信三四要素鉴权接口】======" + currDate + "======" + req.toString());
        String cid = req.getCertNo();
        String name = req.getOwner();//姓名
        String card = req.getCardNo();//银行卡号
        String mobile = req.getPhone();//手机号
        String reqTid = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(100000);
        InchannelIdentifyResponse response = new InchannelIdentifyResponse();
        response.setOwner(name);
        response.setCertNo(cid);
        response.setCardNo(card);
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();

            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            String sign = "";
            String url = "";
            if (StringUtils.isNotEmpty(mobile)) {
                try {
                    //四要素鉴权
                    SSLContext sslcontext = SSLContext.getInstance("TLS");
                    sslcontext.init(null, new TrustManager[]{tm}, null);
                    SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext);
                    socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    Scheme sch = new Scheme("https", 443, socketFactory);
                    httpclient.getConnectionManager().getSchemeRegistry().register(sch);
                    String base = "account" + account + "card" + card + "cid" + cid + "mobile" + mobile + "name" + name + "reqTid" + reqTid
                            + privateKey;
                    log.info("=====【中诚信征信三四要素鉴权base】=====" + base);
                    sign = md5(base).toUpperCase();

                    url = url_4 + "?account=" + account + "&cid=" + cid
                            + "&card=" + card + "&name=" + URLEncoder.encode(name) + "&reqTid=" + reqTid + "&mobile=" + mobile + "&sign=" + sign;
                    log.info("=====【中诚信征信三四要素鉴权url】=====" + url);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("获取sign出现异常，中诚信征信三四要素鉴权sign的值为空");
                    sign = "";
                }

            } else {
                //三要素鉴权
                try {
                    SSLContext sslcontext = SSLContext.getInstance("TLS");
                    sslcontext.init(null, new TrustManager[]{tm}, null);

                    SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext);
                    socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    Scheme sch = new Scheme("https", 443, socketFactory);
                    httpclient.getConnectionManager().getSchemeRegistry().register(sch);
                    String base = "account" + account + "card" + card + "cid" + cid + "name" + name + "reqTid" + reqTid
                            + privateKey;
                    log.info("=====【中诚信征信三四要素鉴权base】=====" + base);
                    sign = md5(base).toUpperCase();

                    url = url_3 + "?account=" + account + "&cid=" + cid
                            + "&card=" + card + "&name=" + URLEncoder.encode(name) + "&reqTid=" + reqTid + "&sign=" + sign;
                    log.info("=====【中诚信征信三四要素鉴权url】=====" + url);
                } catch (Exception e) {
                    log.info("获取sign出现异常，中诚信征信三四要素鉴权sign的值为空");
                    sign = "";
                }

            }
            if (StringUtils.isNotEmpty(sign)) {
                HttpGet httpget = new HttpGet(url);

                log.info("=====【中诚信征信三四要素鉴权executing request】=====" + httpget.getRequestLine());

                HttpEntity backV = null;
                try {
                    long endDate1 = System.currentTimeMillis();
                    HttpResponse responseV = httpclient.execute(httpget);
                    long endDate2 = System.currentTimeMillis();


                    log.info("======【中诚信征信三四要素鉴权请求耗时】======" + (endDate2 - endDate1));
                    backV = responseV.getEntity();
                } catch (Exception e) {
                    log.info("中诚信征信三四要素鉴权通道返回报文体为空");
                    backV = null;
                }
                if (backV != null) {
                    String backValue = "";
                    try {
                        backValue = EntityUtils.toString(backV, "utf-8");
                        log.info("=====【中诚信征信三四要素鉴权返回报文】=====" + backValue);
                    } catch (Exception e) {
                        log.info("中诚信征信三四要素鉴权通道返回报文体为空");
                    }
                    Map<String, Object> dataV = (Map<String, Object>) JSON.parse(backValue);
                    String resCode = dataV.get("resCode") + "";
                    String resMsg = dataV.get("resMsg") + "";
                    if ("2030".equals(resCode)) {
                        response.setResultCode("0000");
                        response.setResultMsg("鉴权成功");
                        response.setChannelCode(resCode);
                        response.setChannelMsg(resMsg);
                    } else {
                        response.setResultCode("3067");
                        response.setResultMsg("其他");
                        response.setChannelCode(resCode);
                        response.setChannelMsg(resMsg);
                    }
                } else {
                    log.info("中诚信征信三四要素鉴权通道返回报文体为空");
                    response.setResultCode("1049");
                    response.setResultMsg("银行超时，请稍后再试");
                }
            } else {
                log.info("中诚信征信三四要素鉴权通道sign为空");
                response.setResultCode("1049");
                response.setResultMsg("银行超时，请稍后再试");
            }
        } catch (Exception e) {
            log.info("请求中诚信征信三四要素鉴权渠道出现异常");
            response.setResultCode("1049");
            response.setResultMsg("银行超时，请稍后再试");
        }
        long endDate = System.currentTimeMillis();
        log.info("======【中诚信征信三四要素鉴权耗时】======" + (endDate - currDate));
        log.info("======【中诚信征信三四要素鉴权响应接口参数】======" + response.toString());
        return response;
    }


    public InchannelIdentifyResponse realName(InchannelIdentifyRequest req) {

        //二要素鉴权
        long currDate = System.currentTimeMillis();
        log.info("======【中诚信征信二要素鉴权接口】======" + currDate + req.toString());
        String cid = req.getCertNo();
        String name = req.getOwner();//姓名
        String reqTid = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(100000);
        String sign = "";
        String url = "";
        InchannelIdentifyResponse response = new InchannelIdentifyResponse();
        response.setOwner(name);
        response.setCertNo(cid);
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();

            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            try {
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[]{tm}, null);

                SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext);
                socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                Scheme sch = new Scheme("https", 443, socketFactory);
                httpclient.getConnectionManager().getSchemeRegistry().register(sch);
                String base = "account" + account + "cid" + cid + "name" + name
                        + privateKey;
                sign = md5(base).toUpperCase();

                url = url_2 + "?account=" + account + "&cid=" + cid
                        + "&name=" + URLEncoder.encode(name) + "&sign=" + sign;
                log.info("=====【中诚信征信二要素鉴权url】=====" + url);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("获取sign出现异常，中诚信征信二要素鉴权sign的值为空");
                sign = "";
            }
            if (StringUtils.isNotEmpty(sign)) {
                HttpGet httpget = new HttpGet(url);

                log.info("=====【中诚信征信二要素鉴权executing request】=====" + httpget.getRequestLine());
                HttpEntity backV = null;
                try {
                    HttpResponse responseV = httpclient.execute(httpget);
                    backV = responseV.getEntity();
                } catch (Exception e) {
                    log.info("中诚信征信二要素鉴权通道返回报文体为空");
                    backV = null;
                }
                if (backV != null) {
                    String backValue = "";
                    try {
                        backValue = EntityUtils.toString(backV, "utf-8");
                        log.info("=====【中诚信征信二要素鉴权返回报文】=====" + backValue);
                    } catch (Exception e) {
                        log.info("中诚信征信二要素鉴权通道返回报文体为空");
                    }
                    Map<String, Object> dataV = (Map<String, Object>) JSON.parse(backValue);
                    String resCode = dataV.get("resCode") + "";
                    String resMsg = dataV.get("resMsg") + "";
                    if ("2010".equals(resCode)) {
                        response.setResultCode("0000");
                        response.setResultMsg("鉴权成功");
                        response.setChannelCode(resCode);
                        response.setChannelMsg(resMsg);
                    } else {
                        response.setResultCode("3067");
                        response.setResultMsg("其他");
                        response.setChannelCode(resCode);
                        response.setChannelMsg(resMsg);
                    }
                } else {
                    log.info("中诚信征信二要素鉴权通道返回报文体为空");
                    response.setResultCode("1049");
                    response.setResultMsg("银行超时，请稍后再试");
                }
            } else {
                log.info("中诚信征信二要素鉴权通道sign为空");
                response.setResultCode("1049");
                response.setResultMsg("银行超时，请稍后再试");
            }
        } catch (Exception e) {
            log.info("请求中诚信征信二要素鉴权渠道出现异常");
            response.setResultCode("1049");
            response.setResultMsg("银行超时，请稍后再试");
        }
        long endDate = System.currentTimeMillis();
        log.info("======【中诚信征信二要素鉴权耗时】======" + (endDate - currDate));
        log.info("======【中诚信征信二要素鉴权响应接口参数】======" + response.toString());
        return response;
    }


    public String savePictoServer(InchannelIdentifyRequest req) {

        long currDate = System.currentTimeMillis();
        log.info("======【中诚信征信返回照片接口】======" + currDate + req.toString());
        String cid = req.getCertNo();
        String name = req.getOwner();//姓名
        String reqTid = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(100000);
        String sign = "";
        String url = "";
        String fileName = "";
        String path = "D:\\";
        String imgName = "";
        InchannelIdentifyResponse response = new InchannelIdentifyResponse();
        response.setOwner(name);
        response.setCertNo(cid);
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();


            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            try {
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[]{tm}, null);

                SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext);
                socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                Scheme sch = new Scheme("https", 443, socketFactory);
                httpclient.getConnectionManager().getSchemeRegistry().register(sch);
                String base = "account" + account + "cid" + cid + "name" + name + "reqTid" + reqTid
                        + privateKey;
                sign = md5(base).toUpperCase();

                url = url_pho + "?account=" + account + "&cid=" + cid
                        + "&name=" + URLEncoder.encode(name) + "&reqTid=" + reqTid + "&sign=" + sign;
                log.info("=====【中诚信征信返回照片url】=====" + url);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("获取sign出现异常，中诚信征信返回照片sign的值为空");
                sign = "";
            }
            if (StringUtils.isNotEmpty(sign)) {
                HttpGet httpget = new HttpGet(url);

                log.info("=====【中诚信征信返回照片executing request】=====" + httpget.getRequestLine());
                HttpEntity backV = null;
                try {
                    HttpResponse responseV = httpclient.execute(httpget);
                    backV = responseV.getEntity();
                } catch (Exception e) {
                    log.info("中诚信征信返回照片返回报文体为空");
                    backV = null;
                }
                if (backV != null) {
                    String backValue = "";
                    try {
                        backValue = EntityUtils.toString(backV, "utf-8");
                        log.info("=====【中诚信征信返回照片返回报文】=====" + backValue);
                    } catch (Exception e) {
                        log.info("返中诚信征信返回照片回报文体为空");
                    }
                    Map<String, Object> dataV = (Map<String, Object>) JSON.parse(backValue);
                    String resCode = dataV.get("resCode") + "";
                    String resMsg = dataV.get("resMsg") + "";

                    String identityPhoto = dataV.get("image") + "";
                    if ("2010".equals(resCode)) {
                        response.setChannelCode(resCode);
                        response.setChannelMsg(resMsg);
                        try {
                            BASE64Decoder decoder = new sun.misc.BASE64Decoder();
                            byte[] bytes1 = decoder.decodeBuffer(identityPhoto);
                            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
                            log.info("=======【中诚信征信返回照片bais】=======" + bais);
                            BufferedImage bi1 = ImageIO.read(bais);
                            log.info("==========【中诚信征信返回照片bi1】=========" + bi1);
                            String realPath = path;

                            File dir = new File(realPath);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            fileName = path + "zhongchengxinzhengxin" + imgName + ".png";
                            File w2 = new File(fileName);// 可以是jpg,png,gif格式
                            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动

                        } catch (Exception e) {
                            log.info("中诚信征信返回照片失败");
                        }
                    } else {
                        response.setResultCode("3067");
                        response.setResultMsg("其他");
                        response.setChannelCode(resCode);
                        response.setChannelMsg(resMsg);
                    }
                } else {
                    log.info("中诚信征信返回照片返回报文体为空");
                    response.setResultCode("1049");
                    response.setResultMsg("银行超时，请稍后再试");
                }
            } else {
                log.info("中诚信征信返回照片sign为空");
                response.setResultCode("1049");
                response.setResultMsg("银行超时，请稍后再试");
            }
        } catch (Exception e) {
            log.info("请求中中诚信征信返回照片出现异常");
            response.setResultCode("1049");
            response.setResultMsg("银行超时，请稍后再试");
        }
        log.info("======【中诚信征信返回照片响应接口参数】======" + response.toString());
        return fileName;
    }

    public static String md5(String s) throws NoSuchAlgorithmException {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();

            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            mdInst.update(btInput);

            byte[] md = mdInst.digest();

            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
