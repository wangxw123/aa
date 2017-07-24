package com.reapal.inchannel.cmbcpayxm.util;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.Serializable;
import java.util.Date;

public class IdentifyReq extends BaseModel implements Serializable {
	
	private String version;
	private String transDate;
	private String transTime;
	private String serialNo;
	private String merId;
	private String merName;
	private String cardType;
	private String accNo;
	private String accName;
	private String certType;
	private String certNo;
	private String phone;
	private String payerBankInsCode;
	private String provNo;
	private String resv;
    public  String tradeCode;
	// 组装实时代扣xml
	public Element getElement() {
		Element req = new Element(BasicConst.Req);
		req.addContent(new Element(BasicConst.Version).setText(BasicConst.Common.VersionValue));
		Date date = new Date();
		req.addContent(new Element(BasicConst.TransDate).setText(BasicConst.CURRENT_DATE.format(date)));
		req.addContent(new Element(BasicConst.TransTime).setText(BasicConst.CURRENT_TIME.format(date)));
		req.addContent(new Element(BasicConst.SerialNo).setText(serialNo));
		
		req.addContent(new Element(BasicConst.Identify.MerId).setText(merId));
		req.addContent(new Element(BasicConst.Identify.MerName).setText(merName));
		req.addContent(new Element(BasicConst.Identify.CardType).setText(cardType));
		req.addContent(new Element(BasicConst.Identify.AccNo).setText(accNo));
		req.addContent(new Element(BasicConst.Identify.AccName).setText(accName));
		req.addContent(new Element(BasicConst.Identify.CertType).setText(certType));
		req.addContent(new Element(BasicConst.Identify.CertNo).setText(certNo));
		req.addContent(new Element(BasicConst.Identify.Phone).setText(phone));
		req.addContent(new Element(BasicConst.Identify.PayerBankInsCode).setText(payerBankInsCode));
		req.addContent(new Element(BasicConst.Identify.ProvNo).setText(provNo));
		req.addContent(new Element(BasicConst.Identify.Resv).setText(resv));
		
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPayerBankInsCode() {
		return payerBankInsCode;
	}

	public void setPayerBankInsCode(String payerBankInsCode) {
		this.payerBankInsCode = payerBankInsCode;
	}

	public String getProvNo() {
		return provNo;
	}

	public void setProvNo(String provNo) {
		this.provNo = provNo;
	}

	public String getResv() {
		return resv;
	}

	public void setResv(String resv) {
		this.resv = resv;
	}

}
