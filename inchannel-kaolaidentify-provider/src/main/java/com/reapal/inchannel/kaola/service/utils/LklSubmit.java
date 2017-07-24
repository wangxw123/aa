package com.reapal.inchannel.kaola.service.utils;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.reapal.inchannel.kaola.service.utils.httpclient.HttpProtocolHandler;
import com.reapal.inchannel.kaola.service.utils.httpclient.HttpRequest;
import com.reapal.inchannel.kaola.service.utils.httpclient.HttpResponse;
import com.reapal.inchannel.kaola.service.utils.httpclient.HttpResultType;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;

public class LklSubmit {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("klConfig");

    public static String desKey = resourceBundle.getString("desKey");
	/**
     * 建立请求，以表单HTML形式构造（默认）
     * @param Lakala_gateway_new 拉卡拉网关地址
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     * @throws  
     */
    public static String buildRequest(String Lakala_GATEWAY_NEW, Map<String, String> sParaTemp, String strMethod, String strButtonName) {
    	sParaTemp.put("_input_charset", LklConfig.INPUT_CHARSET);
        List<String> keys = new ArrayList<String>(sParaTemp.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"Lakalasubmit\" name=\"Lakalasubmit\" action=\"" + Lakala_GATEWAY_NEW
                      + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = sParaTemp.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['Lakalasubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
    /**
     * 建立请求，以表单HTML形式构造，带文件上传功能
     * @param Lakala_GATEWAY_NEW 拉卡拉网关地址
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @param strParaFileName 文件上传的参数名
     * @return 提交表单HTML文本
     */
    public static String buildRequest(String Lakala_GATEWAY_NEW, Map<String, String> sParaTemp, String strMethod, String strButtonName, String strParaFileName) {
        //待请求参数数组
//        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sParaTemp.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"Lakalasubmit\" name=\"Lakalasubmit\"  enctype=\"multipart/form-data\" action=\"" + Lakala_GATEWAY_NEW
                      + "_input_charset=" + LklConfig.INPUT_CHARSET + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = sParaTemp.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        
        sbHtml.append("<input type=\"ImgErToFileUtil\" name=\"" + strParaFileName + "\" />");

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");

        return sbHtml.toString();
    }
    
    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取拉卡拉的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值
     * 如：buildRequest("", "",sParaTemp)
     * @param Lakala_GATEWAY_NEW 拉卡拉网关地址
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @param sParaTemp 请求参数数组
     * @return 拉卡拉处理结果
     * @throws Exception
     */
	public static String buildRequest(String Lakala_GATEWAY_NEW, String strParaFileName, String strFilePath,Map<String, String> sParaTemp) throws Exception {

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(LklConfig.INPUT_CHARSET);

        request.setParameters(generatNameValuePair(sParaTemp));
        request.setUrl(Lakala_GATEWAY_NEW+"?_t=json");

        String strResult = null;
        HttpResponse response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
        if (response == null) {
        	strResult = null;
        }else{
            strResult = response.getStringResult();
        }
        return strResult;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String sign(String str) {
    	String mysign = "";
        if(SignConfig.SIGNTYPE.equals(SignConfig.MD5) ) {
//        	mysign = MD5.sign(sPara, SignConfig.KEY, LklConfig.INPUT_CHARSET);
        }
        if(SignConfig.SIGNTYPE.equals(SignConfig.RSA) ){
    		mysign = RSA.sign(str, SignConfig.PRIVATE_KEY);
        }
        return mysign;
    }
    
	/**
	 * 加密并签名
	 * @param req
	 * @return 加密和签名后的结果Map
	 */
    public static Map<String,String> getSign(Map<String ,String> req){
    	Map<String, String> data = new HashMap<String, String>();
    	JSONObject reqJson = JSONObject.fromObject(req);
		String reqData = reqJson.toString();
		try {
			 String key = "r5b6z7f9";
			 DES des=new DES(key);
			 reqData=des.encrypt(reqData);
			data.put(LklDict.REQDATA, reqData);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		data.put(LklDict.SIGN, sign(reqData));
 		return data;
 		
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getResData(String sHtmlTextToken){
    	Map<String,String> resMap = JSONObject.fromObject(sHtmlTextToken);
    	String reqTransData = resMap.get(LklDict.RESDATA);
    	DES des = new DES(desKey);
		try {
			reqTransData=des.decrypt(reqTransData);
		} catch (Exception e) {
			
		}
		Map<String, Object> data = JSONObject.fromObject(reqTransData);
		return data;
    }
    
	

}
