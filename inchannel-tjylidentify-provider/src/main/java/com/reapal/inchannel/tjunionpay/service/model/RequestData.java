package com.reapal.inchannel.tjunionpay.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 请求天津银联报文
 * 
 * @author CuiXin
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "requestData")
public class RequestData {
	/** 参数编码方式，UTF-8 */
    private String charCode;
	/** 接口版本号（1.0.0） */
	private String version;
	/** 交易类型*/
	private String tradeType;
	/** 交易方式 */
	private String tradeSource;
	/** 渠道号 */
	private String chnlId;
	/** 发起交易的二级用户ID */
	private String userId;
	/** 订单号 */
	private String orderId;
	/** 时间戳，当前接口调用时间，具体格式: yyyyMMddHHmmss */
	private String timeStamp;
	/** 银行卡号 */
	private String accNo;
	/** 手机号 */
	private String nbr;
	/** 姓名 */
	private String name;
	/** 身份证号 */
	private String certificateCode;
	/** 加密后的摘要 */
	private String md5ConSec;
	/** 黑名单指数 */
	private String params;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getNbr() {
		return nbr;
	}

	public void setNbr(String nbr) {
		this.nbr = nbr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	public String getMd5ConSec() {
		return md5ConSec;
	}

	public void setMd5ConSec(String md5ConSec) {
		this.md5ConSec = md5ConSec;
	}

	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}
}