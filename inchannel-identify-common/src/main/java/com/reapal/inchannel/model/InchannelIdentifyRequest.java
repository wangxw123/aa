package com.reapal.inchannel.model;

import com.reapal.common.util.ReapalBase64Utils;

import java.io.Serializable;

/**
 * Created by dell on 2015/1/6.
 */
public class InchannelIdentifyRequest implements Serializable {

    /**渠道*/
    private String channelNo;
    /**银行编号  0101*/
    private String bankNo;
    /**银行编码  CCB*/
    private String bankCode;
    /**银行名称  工商银行*/
    private String bankName;
    /**持卡人姓名*/
    private String owner;
    /**卡号*/
    private String cardNo;
    /**证件类型*/
    private String certType;
    /**证件号*/
    private String certNo;
    /**手机号*/
    private String phone;
    /**有效期*/
    private String validthru;
    /**cvv2*/
    private String cvv2;
    /**商户ID*/
    private String merchantId;
    /**交易流水号*/
    private String tradeNo;
    /** 用户Ip */
    private String userIP;
    /** 用户Id#OS */
    private String userId;
    /**回调地址*/
    private String notifyUrl;
    /**金额*/
    private int amount;
    /**产品名称*/
    private String productname;
    /** 借贷类型 */
    private String dcType;//0储蓄、1信用

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getValidthru() {
        return validthru;
    }

    public void setValidthru(String validthru) {
        this.validthru = validthru;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDcType() {
        return dcType;
    }

    public void setDcType(String dcType) {
        this.dcType = dcType;
    }


    @Override
    public String toString() {
        return "InchannelIdentifyRequest{" +
                "channelNo='" + channelNo + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", owner='" + owner + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", certType='" + certType + '\'' +
                ", certNo='" + certNo + '\'' +
                ", phone='" + phone + '\'' +
                ", validthru='" + validthru + '\'' +
                ", cvv2='" + cvv2 + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", userIP='" + userIP + '\'' +
                ", userId='" + userId + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", amount=" + amount +
                ", productname='" + productname + '\'' +
                ", dcType='" + dcType + '\'' +
                '}';
    }


    public String toBase64String() {
        return "InchannelIdentifyRequest{" +
                "channelNo='" + channelNo + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", owner='" + ReapalBase64Utils.encode(owner) + '\'' +
                ", cardNo='" + ReapalBase64Utils.encode(cardNo) + '\'' +
                ", certType='" + certType + '\'' +
                ", certNo='" + ReapalBase64Utils.encode(certNo) + '\'' +
                ", phone='" + ReapalBase64Utils.encode(phone) + '\'' +
                ", validthru='" + validthru + '\'' +
                ", cvv2='" + cvv2 + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", userIP='" + userIP + '\'' +
                ", userId='" + userId + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", amount=" + amount +
                ", productname='" + productname + '\'' +
                ", dcType='" + dcType + '\'' +
                '}';
    }
}
