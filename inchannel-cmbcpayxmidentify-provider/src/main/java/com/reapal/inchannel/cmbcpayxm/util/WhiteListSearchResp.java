package com.reapal.inchannel.cmbcpayxm.util;

import java.io.Serializable;

public class WhiteListSearchResp implements Serializable {

	private String version;
	private String transDate;
	private String transTime;
	private String reqSerialNo;
	private String execType;
	private String execCode;
	private String execMsg;
	private String status;
	private String bankAccNo;
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
	public String getReqSerialNo() {
		return reqSerialNo;
	}
	public void setReqSerialNo(String reqSerialNo) {
		this.reqSerialNo = reqSerialNo;
	}
	public String getExecType() {
		return execType;
	}
	public void setExecType(String execType) {
		this.execType = execType;
	}
	public String getExecCode() {
		return execCode;
	}
	public void setExecCode(String execCode) {
		this.execCode = execCode;
	}
	public String getExecMsg() {
		return execMsg;
	}
	public void setExecMsg(String execMsg) {
		this.execMsg = execMsg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	
	
}
