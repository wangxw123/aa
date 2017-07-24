/*
 * Web Site: http://www.reapal.com
 * Since 2014 - 2015
 */

package com.reapal.inchannel.cmbcpay.model;

import java.io.Serializable;

public class CmbcpayIdentifyResponse  extends CmbcpayIdentifyRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	private String resultCode;      /** 响应码 */
	private String resultMsg;       /** 响应结果 */

    private String channelCode;      /** 响应码 */
    private String channelMsg;       /** 响应结果 */
    private String channelNo;      /** 响应码 */

    private int smsconfirm;

    public int getSmsconfirm() {
        return smsconfirm;
    }

    public void setSmsconfirm(int smsconfirm) {
        this.smsconfirm = smsconfirm;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelMsg() {
        return channelMsg;
    }

    public void setChannelMsg(String channelMsg) {
        this.channelMsg = channelMsg;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    @Override
    public String toString() {
        return "IdentifyResponse{" +
                "resultCode='" + this.resultCode + '\'' +
                ", resultMsg='" + this.resultMsg + '\'' +
                ", channelCode='" + this.channelCode + '\'' +
                ", channelMsg='" + this.channelMsg + '\'' +
                ", channelNo='" + this.channelNo + '\'' +
                "custId='" +  this.getCustId()  + '\'' +
                ", userId='" + this.getUserId()  + '\'' +
                ", keyId=" + this.getKeyId()  +
                ", cardNo='" + this.getCardNo()  + '\'' +
                ", dcType='" + this.getDcType()  + '\'' +
                ", usrName='" + this.getUsrName() + '\'' +
                ", certType='" + this.getCertType()  + '\'' +
                ", certNo='" + this.getCertNo()  + '\'' +
                ", cardPhone='" + this.getCardPhone()  + '\'' +
                ", cardCvn2='" + this.getCardCvn2()  + '\'' +
                ", cardExpire='" + this.getCardExpire()  + '\'' +
                ", flag='" + this.getFlag()  + '\'' +
                ", orderId='" + this.getOrderId()  + '\'' +
                '}';
    }
}

