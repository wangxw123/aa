package com.reapal.inchannel.cmbcpayxm.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class BasicConst implements Serializable{

    public static final SimpleDateFormat CURRENT_DATE = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat CURRENT_TIME = new SimpleDateFormat("HHmmss");

    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public static final String Req = "Req";
    public static final String Version = "Version";
    public static final String TransDate = "TransDate";
    public static final String TransTime = "TransTime";
    public static final String SerialNo = "SerialNo";
//    1003--实时代扣请求     3003--实时跨行代扣结果查询
//    * 1004--实名身份认证     3004--实名身份认证结果查询
//    * 1007--白名单采集         3007--白名单查询
    public static final String TradeCode_DK = "    1003" ;
    public static final String TradeCode_DK_SEARCH = "    3003" ;
    public static final String TradeCode_IDENTIFY = "    1004" ;
    public static final String TradeCode_IDENTIFY_SEARCH = "    3004" ;
    public static final String TradeCode_WHITE = "    1007" ;
    public static final String TradeCode_WHITE_SEARCH = "    3007" ;
    //通用
    public static final class Common {

        public static final String VersionValue = "1.0";// 默认1.0
        public static final String BizObjTypeValue = "00"; // 00-对私（默认） 01-对公
        public static final String CardTypeValue = "0"; // 0-借记卡（默认） 1-存折 // 2-贷记卡（信用卡） 3-公司账号
        public static final String CurrencyValue = "RMB"; // 默认值 RMB
        public static final String BizTypeValue = "14900";//默认14900
    }
    //跨行代扣
    public static final class CrossBankDk {

        public static final String MerId = "MerId";
        public static final String MerName = "MerName";
        public static final String BizType = "BizType";
        public static final String BizNo = "BizNo";
        public static final String BizObjType = "BizObjType";
        public static final String PayerAcc = "PayerAcc";
        public static final String PayerName = "PayerName";
        public static final String CardType = "CardType";
        public static final String PayerBankName = "PayerBankName";
        public static final String PayerBankInsCode = "PayerBankInsCode";
        public static final String PayerBankSettleNo = "PayerBankSettleNo";
        public static final String PayerBankSwitchNo = "PayerBankSwitchNo";
        public static final String PayerPhone = "PayerPhone";
        public static final String TranAmt = "TranAmt";
        public static final String Currency = "Currency";
        public static final String CertType = "CertType";
        public static final String CertNo = "CertNo";
        public static final String ProvNo = "ProvNo";
        public static final String CityNo = "CityNo";
        public static final String AgtNo = "AgtNo";
        public static final String Purpose = "Purpose";
        public static final String Postscript = "Postscript";

    }

    //跨行代扣查询
    public static final class CrossBankDkSearch {

        public static final String MerId = "MerId";
        public static final String OriTransDate = "OriTransDate";
        public static final String OriReqSerialNo = "OriReqSerialNo";
        public static final String Resv = "Resv";

    }
    //本行代扣
    public static final class LocalBankDk {


    }

    //账务交易结果查询
    public static final class TradeResultSearch {


    }

    //白名单采集
    public static final class WhiteList {

        public static final String MerId = "MerId";
        public static final String MerName = "MerName";
        public static final String BankInsCode = "BankInsCode";
        public static final String BankName = "BankName";
        public static final String BankAccNo = "BankAccNo";
        public static final String BankAccName = "BankAccName";
        public static final String BankAccType = "BankAccType";
        public static final String CertType = "CertType";
        public static final String CertNo = "CertNo";
        public static final String Mobile = "Mobile";
        public static final String Address = "Address";
        public static final String Email = "Email";

    }


    //实名认证
    public static final class Identify {

        public static final String MerId = "MerId";
        public static final String MerName = "MerName";
        public static final String CardType = "CardType";
        public static final String AccNo = "AccNo";
        public static final String AccName = "AccName";
        public static final String CertType = "CertType";
        public static final String CertNo = "CertNo";
        public static final String Phone = "Phone";
        public static final String PayerBankInsCode = "PayerBankInsCode";
        public static final String ProvNo = "ProvNo";
        public static final String Resv = "Resv";

    }

    //白名单查询

    public static final class WhiteListSearch {

        public static final String MerId = "MerId";          //商户号
        public static final String BankAccNo = "BankAccNo";  //银行账号

    }

    //生成交易流水号
    public static String getSerialNo(Date date) {
        return CURRENT_DATE.format(date) + new SimpleDateFormat("hhmmssSSS").format(new Date()).substring(1,9);
    }

}
