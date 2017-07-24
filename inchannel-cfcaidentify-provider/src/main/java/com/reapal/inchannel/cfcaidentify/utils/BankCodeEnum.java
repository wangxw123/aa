package com.reapal.inchannel.cfcaidentify.utils;

public enum BankCodeEnum {


    PSBC("PSBC", "0100"), //邮储银行
    ICBC("ICBC", "0102"), //中国工商银行
    ABC("ABC", "0103"), //中国农业银行
    CCB("CCB", "0105"), //中国建设银行
    CEB("CEB", "0303"), //中国光大银行
    GDB("GDB", "0306"), //广东发展银行
    CMB("CMB", "0308"), //招商银行
    CIB("CIB", "0309"), //兴业银行
    SDB("SDB", "0307"), //深发展银行
    BOC("BOC", "0104"), //中国银行
    PAB("PAB", "0410"), //中国平安银行
    CMBC("CMBC", "0305"), //中国民生银行
    BOCM("BOCM", "0301"), //交通银行
    CITIC("CITIC", "0302"), //中信银行
    SPDB("CITIC", "0310"); //浦发银行


    private final String code;
    private final String msg;

    BankCodeEnum(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 判断支付结果代码合法性
     * @param code
     * @return
     */
    public static boolean isPayResult(String code)
    {
        for (BankCodeEnum v : BankCodeEnum.values())
        {
            if (v.getCode().equals(code))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * 根据编码获取信息
     * @param code
     * @return
     */
    public static String getMsgByCode(String code)
    {
        for (BankCodeEnum v : BankCodeEnum.values())
        {
            if (v.getCode().equals(code))
            {
                return v.getMsg();
            }
        }
        return "未知结果";
    }
    public String getCode()
    {
        return code;
    }

    public String getMsg()
    {
        return msg;
    }
}
