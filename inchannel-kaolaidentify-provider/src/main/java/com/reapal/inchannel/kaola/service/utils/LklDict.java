package com.reapal.inchannel.kaola.service.utils;

/**
 * 
 *类名：LakalaDict
 *功能：拉卡拉接口数据字典定义类
 *详细：该类是定义在demo中各类使用到的变量名称，不需要修改
 *版本：1.0
 *日期：2014年1月13日
 *说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *提示：该代码仅供学习和研究拉卡拉接口使用，只是提供一个参考。
 */
public class LklDict {

	public static String FORMAT="Format";//请求参数格式

	public static String DEVICEID = "DeviceId";//用户设备号

	public static String VERSION="Version";//接口版本号
	
	public static String MERCHANTID="MerchantId";//合作商户ID
	
	public static String SIGN="sign";//签名
	
	public static String SIGNTYPE="SignType";//签名方式
	
	public static String SIGNTYPE_M="SignType_M";//合作商户签名方式
	
	public static String NOTIFYDATA="NotifyData";//返回通知数据
	
	public static String SHA="SHA";//SHA加密
	
	public static String RESDATA="retData";//返回业务数据
	
	public static String REQDATA="ReqData";//请求业务数据
	
	public static String REQUESTTOKEN="RequestToken";//授权令牌
	
	public static String TOKEN="Token";//加密授权令牌
	
	public static String TRANSCODE="TransCode";//接口名称
	
	public static String TIMESTAMP="Timestamp";//时间戳
	
	public static String MERCHANTURL="MerchantUrl";//中断请求地址
	
	public static String MERCHANTORDERAMT="MerchantOrderAmt";//商品总金额
	
	public static String KAOLAACCOUNTNO = "KaoLaAccountNo";//考拉账号
	
	public static String RETURNCODE = "retCode";
	
	public static String RETURNMSG = "retMsg";
	
	/**
	 * 新定义接口字段
	 */
	public static String AUTHTOKEN = "AuthToken";//拉卡拉令牌
	
	public static String SERVICE = "Service";//收银台服务
	
	public static String F = "F";//收银台服务
	
	public static String V = "V";//收银台服务
	
	public static String RID = "RID";//收银台服务
	
	public static String BUSID = "BusId";//收银台服务

	public static String INPUTCHARSET = "InputCharset";//编码集
	
	public static String SUBJECT = "Subject";//订单名称
	
	public static String OUTORDERNO = "OutOrderNo";//商户订单号
	
	public static String ORDERAMOUNT = "OrderAmount";//商户订单金额
	
	public static String CALLBACKURL = "CallBackURL";//异步通知地址
	
	public static String NOTIFYURL = "NotifyURL";//同步通知地址
	
	public static String PAYEXPIRE = "PayExpire";//订单自动关闭时间，单位（分）
}
