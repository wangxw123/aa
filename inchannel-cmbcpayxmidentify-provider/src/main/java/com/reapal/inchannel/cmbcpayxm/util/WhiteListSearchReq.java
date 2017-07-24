package com.reapal.inchannel.cmbcpayxm.util;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.Serializable;
import java.util.Date;

public class WhiteListSearchReq extends BaseModel implements Serializable {

	private String version;
	private String transDate;
	private String transTime;
	private String serialNo;
	private String merId;
	private String bankAccNo;
	
	// 组装实时代扣xml
		public Element getElement() {
			Element req = new Element(BasicConst.Req);
			req.addContent(new Element(BasicConst.Version).setText(BasicConst.Common.VersionValue));
			Date date = new Date();
			req.addContent(new Element(BasicConst.TransDate).setText(BasicConst.CURRENT_DATE.format(date)));
			req.addContent(new Element(BasicConst.TransTime).setText(BasicConst.CURRENT_TIME.format(date)));
			req.addContent(new Element(BasicConst.SerialNo).setText(BasicConst.getSerialNo(date)));
			req.addContent(new Element(BasicConst.WhiteListSearch.MerId).setText(merId));
			req.addContent(new Element(BasicConst.WhiteListSearch.BankAccNo).setText(bankAccNo));
			
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

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	
}
