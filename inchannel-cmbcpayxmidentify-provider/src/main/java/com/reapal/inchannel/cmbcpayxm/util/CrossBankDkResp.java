package com.reapal.inchannel.cmbcpayxm.util;

import java.io.Serializable;

public class CrossBankDkResp implements Serializable {

	private String version;
	private String settDate;
	private String transTime;
	private String reqSerialNo;
	private String execType;
	private String execCode;
	private String execMsg;
	private String merId;
	private String paySerialNo;
	private String resv;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSettDate() {
		return settDate;
	}

	public void setSettDate(String settDate) {
		this.settDate = settDate;
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

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getPaySerialNo() {
		return paySerialNo;
	}

	public void setPaySerialNo(String paySerialNo) {
		this.paySerialNo = paySerialNo;
	}

	public String getResv() {
		return resv;
	}

	public void setResv(String resv) {
		this.resv = resv;
	}

}
