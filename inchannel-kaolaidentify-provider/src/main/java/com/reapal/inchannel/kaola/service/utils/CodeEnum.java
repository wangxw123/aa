package com.reapal.inchannel.kaola.service.utils;

/**
 * Created by liukai on 2016/12/8.
 */
public class CodeEnum {
    /** 渠道返回码 */
    public enum Channel {
        Success("00", "认证一致"),
        CarOrMobileError("01", "持卡人身份信息、手机号或CVN2输入不正确，验证失败"),
        Exceed("01", "认证不一致(不通过):超出取款金额限制"),
        NotAllowTrading("01", "认证不一致(不通过):不允许持卡人进行的交易"),
        CarInvalid("01", "输入的卡号无效，请确认后输入"),
        InfoDiff("01", "信息不匹配"),
        OutLimit("01", "认证不一致(不通过):该卡单日累计次数超限"),
        NotMoney("06", "【未扣费】用户总额度不足"),
        Delta("09", "【未扣费】渠道总额度不足，请渠道充值后再做交易"),
        OutNumber("18", "【未扣费】卡片查询次数超限"),
        MessageFail("32", "报文解析失败,请检查渠道ID和秘钥");
        private final String value;
        private final String content;
        Channel(String value, String content) {
            this.value = value;
            this.content = content;
        }
        public String v() {
            return value;
        }
        public String c() {
            return content;
        }
    }

    /** 融宝返回码 */
    public enum Reapal {
        Success("0000", "鉴权成功"),
        ReSign("2001", "签约失败，请重新签约或更换其它银行卡签约"),
        InvalidCar("2007", "银行卡无效，请重新输入"),
        SignFail("2009", "签约失败，请更换其它银行卡进行签约"),
        CarOrMobileError("2011", " 持卡人身份信息或手机号输入不正确"),
        OutNumber("2049", "认证次数超限，请次日再试"),
        Fail("3067", "其它");
        private final String value;
        private final String content;
        Reapal(String value, String content) {
            this.value = value;
            this.content = content;
        }
        public String v() {
            return value;
        }
        public String c() {
            return content;
        }
    }
}
