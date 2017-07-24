package com.reapal.inchannel.tjunionpay.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 天津银联返回报文
 * 
 * @author CuiXin
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "responseData")
public class ResponseData {

	private String charCode;
	
	private String version;
	
	private String tradeType;
	
	private String tradeSource;
	
	private String chnlId;
	
	private String userId;
		
	private String timeStamp;
	
	private String resultCode;
	
	private String accNo;
	
	private String resultDesc;
	
	private String orderId;
	
	private String random;
	
	private String md5ConSec;

	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeSource() {
		return tradeSource;
	}

	public void setTradeSource(String tradeSource) {
		this.tradeSource = tradeSource;
	}

	public String getChnlId() {
		return chnlId;
	}

	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getMd5ConSec() {
		return md5ConSec;
	}

	public void setMd5ConSec(String md5ConSec) {
		this.md5ConSec = md5ConSec;
	}
}
