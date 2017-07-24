package com.reapal.inchannel.cmbcpayxm.util;

import java.io.Serializable;

public class WhiteListResp implements Serializable{

	private String version; // 版本号
	private String transDate; // 交易日期
	private String transTime; // 交易时间
	private String reqSerialNo;// 请求流水号
	private String execType; // 响应类型
	private String execCode; // 响应代码
	private String execMsg; // 响应描述

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

}
