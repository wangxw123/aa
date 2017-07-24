package com.reapal.inchannel.zcxzxidentify.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by dell on 2015/1/13.
 */
public class HttpClientUtil {

 
    /**
     * 封装HTTP POST方法
     * @param
     * @param
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "GBK");
        httpPost.setEntity(param);
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP POST方法
     * @param
     * @param （如JSON串）
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, String data) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","text/json; charset=utf-8");
        httpPost.setEntity(new StringEntity(URLEncoder.encode(data, "UTF-8")));
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP GET方法
     * @param
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String get(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP GET方法
     * @param
     * @param
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String get(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpGet.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }
    /**
     * 封装HTTP GET方法
     * @param
     * @param
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String get(String url, Map<String, String> paramMap,String encoding) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        //方法一， 此方法设置超市无效  要等1分20多秒
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpGet httpGet = new HttpGet();
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, encoding);
        httpGet.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }
    /**
     * 封装HTTP PUT方法
     * @param
     * @param
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String put(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPut.setEntity(param);
        HttpResponse response = httpClient.execute(httpPut);
        String httpEntityContent = getHttpEntityContent(response);
        httpPut.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP DELETE方法
     * @param
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String delete(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete= new HttpDelete();
        httpDelete.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpDelete);
        String httpEntityContent = getHttpEntityContent(response);
        httpDelete.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP DELETE方法
     * @param
     * @param
     * @return
     * @throws org.apache.http.client.ClientProtocolException
     * @throws IOException
     */
    public static String delete(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete();
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpDelete.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpDelete);
        String httpEntityContent = getHttpEntityContent(response);
        httpDelete.abort();
        return httpEntityContent;
    }


    /**
     * 设置请求参数
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    /**
     * 获得响应HTTP实体内容
     * @param response
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            return sb.toString();
        }
        return "";
    }

    public static void main(String[] strings) {
       Map<String,String> map = new HashMap<String ,String>();
       map.put("OperID", "tjrbzf");
       map.put("OperPass", "tjrbzf01");
       map.put("ContentType", "15");
       map.put("DesMobile", "18710075023");
       map.put("Content","【融宝支付】测试通道哦！");
           try {
               HttpClientUtil c=new HttpClientUtil();
               String smsCode =  c.post("http://221.179.180.158:9007/QxtSms/QxtFirewall", map);
           } catch (IOException e) {
               e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
           }
   }

}
