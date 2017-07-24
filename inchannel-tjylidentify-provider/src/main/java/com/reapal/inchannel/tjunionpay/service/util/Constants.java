package com.reapal.inchannel.tjunionpay.service.util;

public class Constants {

	/** GBK编码 */
	public static final String ENCODING_GBK = "GBK";
	/** UTF-8编码 */
	public static final String ENCODING_UTF8 = "UTF-8";
	/** 交易类型-动态秘钥 */
	public static final String TRANS_TP_DY_KEY = "dykey";
	/** 交易类型-实名验证֤ */
	public static final String TRANS_TP_ID_TRANS = "idtrans";
	/** 交易类型 */
	public static String TRADE_TYPE = "";
	/** 动态3DES秘钥 */
	public static String DYN_3DES_KEY = "";
	/** 动态秘钥随机数 */
	public static String RANDOM = "";

	public static final String URL_DYN = "https://62tj.unionpay.com:8443/auth-web/trans/getDynKey";

	public static final String URL_TRADE = "https://62tj.unionpay.com:8443/auth-web/trans/apiTrans";


	/** 版本信息 */
	public static final String VERSION_ID = "1.1";
	
	/** 用户 */
	public static final String USER_ID = "rongbao";
	/** 渠道 */
	public static final String CHANNEL_ID = "TJ0000000021";
	/** 3DES秘钥 */
	public static final String DES3_KEY = "1nlgkby2026t6p48gfk0ik3r";
	/** MD5秘钥 */
	public static final String MD5_KEY = "6a82afaef5a84675b1006675a275048e";

	public static final String URL = "https://62tj.unionpay.com:8443";
	
}
