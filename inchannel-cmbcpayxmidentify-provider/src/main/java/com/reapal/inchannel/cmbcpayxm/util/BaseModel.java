package com.reapal.inchannel.cmbcpayxm.util;

import org.apache.log4j.Logger;

import java.io.Serializable;


public class BaseModel implements Serializable{
	public static Logger logger = Logger.getLogger(BaseModel.class);
	
	// 8位报文头长度
	public String headLength;
	// 8位合作方编号 固定值 ：dk_rbzf ，不足补加空格
	public static String partnerNo = " dk_rbzf";
	// 8位交易码 暂定 ：1003 ，不足签名前面 加空格
	/**
	 * 1003--实时代扣请求     3003--实时跨行代扣结果查询
	 * 1004--实名身份认证     3004--实名身份认证结果查询
	 * 1007--白名单采集         3007--白名单查询
	 */
	public  String tradeCode;
	// 4位签名域长度 固定值 0256
	public static String signLength = "0256";
	// 签名域值
	public byte[] signValue;
	// 报文密文
	public byte[] encMessage;
	
	public byte[] tempSign;
	
	public byte[] tempMessage;
	// XML字符串
	public String requestXml;

	public String getHeadLength() throws Exception {
		//调用报文总长度
		return getSize();
	}

	public  String getTradeCode() {
		return tradeCode;
	}

	public  void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public static String getSignLength() {
		return signLength;
	}

	public byte[] getSignValue() throws Exception {
		//调用签名
		return signMessage(this.getRequestXml());
	}

	public byte[] getEncMessage() throws Exception {
		//调用加密
		return EncryptMessage(this.getRequestXml());
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	//整个发出报文串（不含本身8位）的所有字节数，以字符串表示，位数不足左补0
	public String getSize() throws Exception {
		tempSign = this.getSignValue();
		tempMessage = this.getEncMessage();
		int length = partnerNo.getBytes().length + tradeCode.getBytes().length + signLength.getBytes().length + tempSign.length + tempMessage.length;
		logger.info("报文长度=========="+String.format("%08d", length));
		return String.format("%08d", length);
	}

	public byte[] sendData() throws Exception{
		byte[] bt = (this.getHeadLength() + partnerNo + tradeCode + signLength).getBytes("UTF-8");
		byte[] req = byteMerge(tempSign,tempMessage);
		byte[] data =byteMerge(bt, req);
		
		return data;
	}
	
	// 加密xml
	public byte[] EncryptMessage(String strXml) throws Exception{
		byte[] cryptedBytes = RSAHelper.encryptRSA(strXml.getBytes("UTF-8"), false, "UTF-8");
		logger.info("加密后=========数据====" + cryptedBytes);
		return cryptedBytes;
	}
	//签名xml
	public byte[] signMessage(String strXml) throws Exception {
		byte[] signBytes = RSAHelper.signRSA(strXml.getBytes("UTF-8"),false, "UTF-8");
		logger.info("签名后=========数据====" + signBytes);
		return signBytes;
	}
	
	//拼接 byte[]
	public byte[] byteMerge(byte[] byte_1, byte[] byte_2){
		
		byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
        return byte_3;
	}
	
}
