package com.reapal.inchannel.cmbcpayxm.util;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.Serializable;
import java.util.Date;

public class WhiteListReq extends BaseModel implements Serializable {

	private String version;
	private String transDate;
	private String transTime;
	private String serialNo;
	private String merId;
	private String merName;
	private String bankInsCode;
	private String bankName;
	private String bankAccNo;
	private String bankAccName;
	private String bankAccType;
	private String certType;
	private String certNo;
	private String mobile;
	private String address;
	private String email;
	
	
	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}
	
	// 组装实时代扣xml
	public Element getElement() {
		Element req = new Element(BasicConst.Req);
			req.addContent(new Element(BasicConst.Version).setText(BasicConst.Common.VersionValue));
				Date date = new Date();
			req.addContent(new Element(BasicConst.TransDate).setText(BasicConst.CURRENT_DATE.format(date)));
			req.addContent(new Element(BasicConst.TransTime).setText(BasicConst.CURRENT_TIME.format(date)));
			req.addContent(new Element(BasicConst.SerialNo).setText(BasicConst.getSerialNo(date)));
			req.addContent(new Element(BasicConst.WhiteList.MerId).setText(merId));
			req.addContent(new Element(BasicConst.WhiteList.MerName).setText(merName));
			req.addContent(new Element(BasicConst.WhiteList.BankInsCode).setText(bankInsCode));
			req.addContent(new Element(BasicConst.WhiteList.BankName).setText(bankName));
			req.addContent(new Element(BasicConst.WhiteList.BankAccNo).setText(bankAccNo));
			req.addContent(new Element(BasicConst.WhiteList.BankAccName).setText(bankAccName));
			req.addContent(new Element(BasicConst.WhiteList.BankAccType).setText(bankAccType));
			req.addContent(new Element(BasicConst.WhiteList.CertType).setText(certType));
			req.addContent(new Element(BasicConst.WhiteList.CertNo).setText(certNo));
			req.addContent(new Element(BasicConst.WhiteList.Mobile).setText(mobile));
			req.addContent(new Element(BasicConst.WhiteList.Address).setText(address));
			req.addContent(new Element(BasicConst.WhiteList.Email).setText(email));
	 return req;
	}
	
	//获取完整的XML报文
	public void createXml() {
		
		String s = BasicConst.XML_HEADER + "\n" + new XMLOutputter(Format.getPrettyFormat())
				.outputString(this.getElement());
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

	public String getBankInsCode() {
		return bankInsCode;
	}

	public void setBankInsCode(String bankInsCode) {
		this.bankInsCode = bankInsCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankAccName() {
		return bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getBankAccType() {
		return bankAccType;
	}

	public void setBankAccType(String bankAccType) {
		this.bankAccType = bankAccType;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
