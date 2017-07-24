package com.reapal.inchannel.model;

import java.io.Serializable;

/**
 * Created by dell on 2015/1/6.
 */
public class InchannelIdentifyResponse extends InchannelIdentifyRequest implements Serializable {

    /** 响应码 */
    private String resultCode;
    /** 响应结果 */
    private String resultMsg;
    /** 渠道响应码 */
    private String channelCode;
    /** 渠道响应结果 */
    private String channelMsg;
    /** 响应状态 0:处理中; 1:成功; 2:失败 */
    private String result;

    private int smsconfirm;

    private String certPhoto;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public int getSmsconfirm() {
        return smsconfirm;
    }

    public void setSmsconfirm(int smsconfirm) {
        this.smsconfirm = smsconfirm;
    }

    public String getCertPhoto() {
        return certPhoto;
    }

    public void setCertPhoto(String certPhoto) {
        this.certPhoto = certPhoto;
    }

    @Override
    public String toString() {
        return "InchannelIdentifyResponse{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", channelMsg='" + channelMsg + '\'' +
                ", result='" + result + '\'' +
                ", smsconfirm=" + smsconfirm +
                ", certPhoto='" + certPhoto + '\'' +
                '}';
    }
}
