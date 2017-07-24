package com.reapal.inchannel.cmbcpayxm.util;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.Serializable;
import java.util.Date;

/**
 * 跨行代扣
 */
public class CrossBankDkReq extends BaseModel implements Serializable {

	private String version;
	private String transDate;
	private String transTime;
	private String serialNo;
	private String merId;
	private String merName;
	private String bizType;
	private String bizNo;
	private String bizObjType;
	private String payerAcc;
	private String payerName;
	private String cardType;
	private String payerBankName;
	private String payerBankInsCode;
	private String payerBankSettleNo;
	private String payerBankSwitchNo;
	private String payerPhone;
	private String tranAmt;
	private String currency;
	private String certType;
	private String certNo;
	private String provNo;
	private String cityNo;
	private String agtNo;
	private String purpose;
	private String postscript;

	// 组装实时代扣xml
	public Element getElement() {
		Element req = new Element(BasicConst.Req);
		req.addContent(new Element(BasicConst.Version).setText(BasicConst.Common.VersionValue));
		Date date = new Date();
		req.addContent(new Element(BasicConst.TransDate).setText(BasicConst.CURRENT_DATE.format(date)));
		req.addContent(new Element(BasicConst.TransTime).setText(BasicConst.CURRENT_TIME.format(date)));
		req.addContent(new Element(BasicConst.SerialNo).setText(serialNo));
		
		req.addContent(new Element(BasicConst.CrossBankDk.MerId).setText(merId));
		req.addContent(new Element(BasicConst.CrossBankDk.MerName).setText(merName));
		req.addContent(new Element(BasicConst.CrossBankDk.BizType).setText(BasicConst.Common.BizTypeValue));
		req.addContent(new Element(BasicConst.CrossBankDk.BizNo).setText(bizNo));
		req.addContent(new Element(BasicConst.CrossBankDk.BizObjType).setText(BasicConst.Common.BizObjTypeValue));
		req.addContent(new Element(BasicConst.CrossBankDk.PayerAcc).setText(payerAcc));
		req.addContent(new Element(BasicConst.CrossBankDk.PayerName).setText(payerName));
		req.addContent(new Element(BasicConst.CrossBankDk.CardType).setText(cardType));
		req.addContent(new Element(BasicConst.CrossBankDk.PayerBankName).setText(payerBankName));
		req.addContent(new Element(BasicConst.CrossBankDk.PayerBankInsCode).setText(payerBankInsCode));
		req.addContent(new Element(BasicConst.CrossBankDk.PayerBankSettleNo).setText(payerBankSettleNo));
		
		req.addContent(new Element(BasicConst.CrossBankDk.PayerBankSwitchNo).setText(payerBankSwitchNo));
		req.addContent(new Element(BasicConst.CrossBankDk.PayerPhone).setText(payerPhone));
		req.addContent(new Element(BasicConst.CrossBankDk.TranAmt).setText(tranAmt));
		
		req.addContent(new Element(BasicConst.CrossBankDk.Currency).setText(BasicConst.Common.CurrencyValue));
		req.addContent(new Element(BasicConst.CrossBankDk.CertType).setText(certType));
		req.addContent(new Element(BasicConst.CrossBankDk.CertNo).setText(certNo));
		req.addContent(new Element(BasicConst.CrossBankDk.ProvNo).setText(provNo));
		req.addContent(new Element(BasicConst.CrossBankDk.Purpose).setText(purpose));
		
		return req;
	}

	// 获取完整的XML报文
	public void createXml() {
		String s = BasicConst.XML_HEADER
				+ "\n"
				+ new XMLOutputter(Format.getPrettyFormat()).outputString(this.getElement());
						
		this.setRequestXml(s);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getBizObjType() {
		return bizObjType;
	}

	public void setBizObjType(String bizObjType) {
		this.bizObjType = bizObjType;
	}

	public String getPayerAcc() {
		return payerAcc;
	}

	public void setPayerAcc(String payerAcc) {
		this.payerAcc = payerAcc;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPayerBankName() {
		return payerBankName;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}

	public String getPayerBankInsCode() {
		return payerBankInsCode;
	}

	public void setPayerBankInsCode(String payerBankInsCode) {
		this.payerBankInsCode = payerBankInsCode;
	}

	public String getPayerBankSettleNo() {
		return payerBankSettleNo;
	}

	public void setPayerBankSettleNo(String payerBankSettleNo) {
		this.payerBankSettleNo = payerBankSettleNo;
	}

	public String getPayerBankSwitchNo() {
		return payerBankSwitchNo;
	}

	public void setPayerBankSwitchNo(String payerBankSwitchNo) {
		this.payerBankSwitchNo = payerBankSwitchNo;
	}

	public String getPayerPhone() {
		return payerPhone;
	}

	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}

	public String getTranAmt() {
		return tranAmt;
	}

	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getProvNo() {
		return provNo;
	}

	public void setProvNo(String provNo) {
		this.provNo = provNo;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getAgtNo() {
		return agtNo;
	}

	public void setAgtNo(String agtNo) {
		this.agtNo = agtNo;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

}
