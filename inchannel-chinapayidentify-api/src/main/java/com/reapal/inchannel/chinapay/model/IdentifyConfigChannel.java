/*
 * Web Site: http://www.reapal.com
 * Since 2014 - 2015
 */

package com.reapal.inchannel.chinapay.model;

import java.io.Serializable;


public class IdentifyConfigChannel implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 通道编码#OS#VNN */
	private String channelNo;
	/** 通道类型(ChinaPay)#OS#VNN */
	private String channelType;
	/** 应用系统编号 */
	private String appSysId;
	/** URL地址#OS */
	private String url;
	/** Key值 */
	private String keyValue;
	/** 证书地址 */
	private String certPath;
	/** 证书有效期 */
	private java.util.Date certExpire;
	/** 借贷类型#ENR（0-借、1-贷、2-全） */
	private String dcType;
	/** 银行类型（0全） */
	private String bankType;
	/** 成本（0不考虑） */
	private String cost;
	/** 综合评分#OS#ENR（0-差、1-一般、2-好、3-很好、4-特好） */
	private String score;
	/** 通道状态#OS#ENR（1-开通，0-关闭） */
	private String channelStatus;
	/** 服务节点 */
	private String serviceNode;
	/** 备注 */
	private String note;
	/** 创建人 */
	private String createUserId;
	/** 创建时间 */
	private java.util.Date createDateTime;
	/** 最后修改人 */
	private String modifyUserId;
	/** 最后修改时间 */
	private java.util.Date modifyDateTime;
	/** 删除标记（1有效、2无效） */
	private String deleteFlag;

    private String certPath2;
    private String certPath3;

    private String port;


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCertPath2() {
        return certPath2;
    }

    public void setCertPath2(String certPath2) {
        this.certPath2 = certPath2;
    }

    public String getCertPath3() {
        return certPath3;
    }

    public void setCertPath3(String certPath3) {
        this.certPath3 = certPath3;
    }

    public String getChannelNo() {
		return this.channelNo;
	}

	public void setChannelNo(String value) {
		this.channelNo = value;
	}





	public String getChannelType() {
		return this.channelType;
	}

	public void setChannelType(String value) {
		this.channelType = value;
	}





	public String getAppSysId() {
		return this.appSysId;
	}

	public void setAppSysId(String value) {
		this.appSysId = value;
	}





	public String getUrl() {
		return this.url;
	}

	public void setUrl(String value) {
		this.url = value;
	}





	public String getKeyValue() {
		return this.keyValue;
	}

	public void setKeyValue(String value) {
		this.keyValue = value;
	}





	public String getCertPath() {
		return this.certPath;
	}

	public void setCertPath(String value) {
		this.certPath = value;
	}





	public java.util.Date getCertExpire() {
		return this.certExpire;
	}

	public void setCertExpire(java.util.Date value) {
		this.certExpire = value;
	}





	public String getDcType() {
		return this.dcType;
	}

	public void setDcType(String value) {
		this.dcType = value;
	}





	public String getBankType() {
		return this.bankType;
	}

	public void setBankType(String value) {
		this.bankType = value;
	}





	public String getCost() {
		return this.cost;
	}

	public void setCost(String value) {
		this.cost = value;
	}





	public String getScore() {
		return this.score;
	}

	public void setScore(String value) {
		this.score = value;
	}





	public String getChannelStatus() {
		return this.channelStatus;
	}

	public void setChannelStatus(String value) {
		this.channelStatus = value;
	}





	public String getServiceNode() {
		return this.serviceNode;
	}

	public void setServiceNode(String value) {
		this.serviceNode = value;
	}





	public String getNote() {
		return this.note;
	}

	public void setNote(String value) {
		this.note = value;
	}





	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String value) {
		this.createUserId = value;
	}





	public java.util.Date getCreateDateTime() {
		return this.createDateTime;
	}

	public void setCreateDateTime(java.util.Date value) {
		this.createDateTime = value;
	}





	public String getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(String value) {
		this.modifyUserId = value;
	}





	public java.util.Date getModifyDateTime() {
		return this.modifyDateTime;
	}

	public void setModifyDateTime(java.util.Date value) {
		this.modifyDateTime = value;
	}





	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String value) {
		this.deleteFlag = value;
	}
	
	
	
	
	

}

