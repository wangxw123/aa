package com.reapal.inchannel.tjrh.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/** 
* @ClassName: HttpXmlClient 
* @Description: http请求工具类 
* @author gyc
* @date 2017年1月9日
*/
public class HttpClient {
	private static Logger log = Logger.getLogger(HttpClient.class);
	
	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		String body = null;
		
//		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);
		
		post.setHeader("Authorization", params.get("Authorization"));
		post.setHeader("X-API-KEY", params.get("X-API-KEY"));
		
		body = invoke(httpclient, post);
		
		httpclient.getConnectionManager().shutdown();
		return body;
	}
	
	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		String body = null;
		
		log.info("create httppost:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);
		
		httpclient.getConnectionManager().shutdown();
		
		return body;
	}
		
	
	private static String invoke(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {
		
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);
		
		return body;
	}

	private static String paseResponse(HttpResponse response) {
//		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();
		
//		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
//		log.info(charset);
		
		String body = null;
		try {
            if (entity != null) {    
                InputStream instreams = entity.getContent();    
                body = convertStreamToString(instreams);  
//                System.out.println("result:" + body);
            } 
//			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return body;
	}
    public static String convertStreamToString(InputStream is) {      
        StringBuilder sb1 = new StringBuilder();      
        byte[] bytes = new byte[4096];    
        int size = 0;    
          
        try {      
            while ((size = is.read(bytes)) > 0) {    
                String str = new String(bytes, 0, size, "UTF-8");    
                sb1.append(str);    
            }    
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb1.toString();      
    } 
	private static HttpResponse sendRequest(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {
//		log.info("execute post...");
		HttpResponse response = null;
		
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private static HttpPost postForm(String url, Map<String, String> params){
		
		HttpPost httpost = new HttpPost(url);
		
		if (params != null){
			List<NameValuePair> nvps = new ArrayList <NameValuePair>();
			Set<String> keySet = params.keySet();
			for(String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}

			try {
//				log.info("set utf-8 form entity to httppost");
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return httpost;
	}
}
