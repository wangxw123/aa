package com.reapal.common.util;

/**
 * Created by dell on 2015/11/23.
 */
public enum ErrorCodeEnum {

    ERROR_3067 ("3067", "其它"),
    ERROR_3026 ("3026", "可用余额不足，请联系发卡银行"),
    ERROR_3023 ("3023", "消费金额超限"),
    ERROR_3095 ("3095", "支付失败，持卡人身份信息或手机号输入不正确"),
    ERROR_3013 ("3013", "支付失败，请重新或更换其它银行进行支付"),
    ERROR_3108 ("3108", "交易金额不能低于0.1元"),
    ERROR_3081 ("3067", "交易处理中，正在等待银行返回最终状态"),
    ERROR_3107 ("3107", "您输入密码次数已超限，请重新或更换其它银行进行支付"),
    ERROR_3104 ("3104", "银行卡未开通认证支付，请到银联开通此功能"),
    ERROR_3098("3098", "不支持此银行卡交易，请更换其它银行的银行卡进行交易"),
    ERROR_3032 ("3032", "订单已过期或已撤销"),
    ERROR_3031 ("3031", "该笔交易风险较高，拒绝此次交易"),
    ERROR_3100 ("3100", "您的银行卡交易受限，请联系您的发卡行"),
    ERROR_3008 ("3008", "单卡超过当月累积支付限额"),
    ERROR_3009 ("3009", "单卡超过单日累积支付次数上限"),
    ERROR_3010 ("3010", "单卡超过单月累积支付次数上限"),
    ERROR_3029 ("3029", "银行系统超时，请稍后再试"),
    ERROR_3084 ("3084", "支付失败"),
    ERROR_3005 ("3005", "单卡超过当日累积支付限额"),
    ERROR_3086 ("3086", "单卡超过当日累积支付限额"),
    ERROR_3040 ("3040", "银行交易受限，建议更换其它银行进行支付"),
    ERROR_3036 ("3036", "支付失败，请更换其它银行进行支付"),
    ERROR_3007 ("3007", "单卡超过单笔支付限额"),
    ERROR_3099 ("3099", "银行卡无效，请更换其它银行进行支付"),
    ERROR_3015 ("3015", "订单不存在"),
    ERROR_3059 ("3059", "此卡为挂失卡，请更换其它银行卡进行支付"),
    ERROR_3111 ("3111", "交易金额不能低于1.0元");

    private String code ;
    private String msg;
    private ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.code+":"+this.code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
