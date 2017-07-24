/*
 * Web Site: http://www.reapal.com
 * Since 2014 - 2015
 */

package com.reapal.inchannel.cmbcpayxm.model;

import java.io.Serializable;

public class CmbcpayxmIdentifyRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

    /** 商户Id#OS */
    private String custId;
    /** 用户Id#OS */
    private String userId;
	/** 主键ID#OS */
	private Long keyId;
	/** 卡号#OS#VNN */
	private String cardNo;
    /** 借贷类型 */
    private String dcType;//0储蓄、1信用
	/** 银行编码 */
	private String bankCode;
	/** 持卡人姓名#OS#VNN */
	private String usrName;
	/** 证件类型#ENS（01-身份证、02-军官证、03-护照、04-回乡证、05-台胞证、06-警官证、07-士兵证、99-其他证件） */
	private String certType;
	/** 证件号#VNN */
	private String certNo;
	/** 手机号#VNN#VM */
	private String cardPhone;
	/** CVN2 */
	private String cardCvn2;
	/** 有效期 */
	private String cardExpire;

    private String flag = "0";//0表示chinapay的鉴权，1表示民生银行
    /** 订单号*/
    private String orderId;
    /**金额*/
    private int amount;

    private String productname;

    private String userIP;

    /**回调url*/
    private String callbackurl;

    /**交易流水号*/
    private String tradeNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCardExpire() {
        return cardExpire;
    }

    public void setCardExpire(String cardExpire) {
        this.cardExpire = cardExpire;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getDcType() {
        return dcType;
    }

    public void setDcType(String dcType) {
        this.dcType = dcType;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
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

    public String getCardPhone() {
        return cardPhone;
    }

    public void setCardPhone(String cardPhone) {
        this.cardPhone = cardPhone;
    }

    public String getCardCvn2() {
        return cardCvn2;
    }

    public void setCardCvn2(String cardCvn2) {
        this.cardCvn2 = cardCvn2;
    }


    @Override
    public String toString() {
        return "IdentifyRequest{" +
                "custId='" + custId + '\'' +
                ", userId='" + userId + '\'' +
                ", keyId=" + keyId +
                ", cardNo='" + cardNo + '\'' +
                ", dcType='" + dcType + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", usrName='" + usrName + '\'' +
                ", certType='" + certType + '\'' +
                ", certNo='" + certNo + '\'' +
                ", cardPhone='" + cardPhone + '\'' +
                ", cardCvn2='" + cardCvn2 + '\'' +
                ", cardExpire='" + cardExpire + '\'' +
                ", flag='" + flag + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}

