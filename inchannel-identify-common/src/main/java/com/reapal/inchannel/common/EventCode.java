/*
 * @Id: EventCode.java 16:39:03 2006-2-11
 * 
 * @author 
 * @version 1.0
 * PAYGW_CORE PROJECT
 */
package com.reapal.inchannel.common;

/**
 * @author Description: 异常事件和错误代码
 *
 */
public class EventCode {
    // 编码规则
    // 子模块 + 错误级别 2位 序号(2位数字)
    // 类别 1-系统异常 2-安全 3-数据异常 4-未知异常 5-业务 6-通信 7-web
    // 错误级别： 0-严重 1-中等 2-轻微
    // 07-逻辑
    // 08-数据
    // 09-系统内
    // 10-b2c支付
    // 11-外卡
    // 12-bank
    // 13-MPI
    // 15-EPOS
    // 16-数据库

    public static final String UNEXCPECTED_EXCEPTION = "100900"; // 程序未预期的异常

    public static final String ICBC_SIGNVERIFY = "201001"; // 工行验证返回签名未通过，可疑银行交易
    public static final String BOC_MD5VERIFY = "201002"; // 中行MD5验证出现错误
    public static final String SIGN_VERIFY = "201003"; // 签名验证失败

    public static final String SLA_SERVICENOTFOUND = "511601"; // 数据库返回
    // 错误服务编号或者该服务未开通
    public static final String SLA_SERVICEDISABLED = "511602"; // 请求的服务已经中止
    public static final String SLA_SERVICENOTACTIVE = "511603"; // 服务未激活
    public static final String SLA_SERVICEEXCEPIOTN = "501604"; // 服务未开通(数据异常)
    public static final String SLA_SERVICETYPEEXCEPIOTN = "501605"; // 服务参数错误(数据异常)
    public static final String SLA_SERVICECHANNELCOLSED = "501610"; // 服务通道关闭
    public static final String SLA_NOSERVICECHANNEL = "501611"; // 暂时没有可用收单服务通道
    public static final String SLA_NOUSERCHANNEL = "501612"; // 商户没有配置可用的支付渠道
    public static final String SLA_NOPAYMENTTYPE = "501613"; // 商户错误的支付类型（直联时只能传1：借记卡或2：贷记卡）

    public static final String SP_TIMEOUT = "501606"; // 服务商系统超时,请稍后查询结果或重试
    public static final String SP_AMOUNT_VALUEOVER = "501607"; // 面额小于订单金额,不能进行交易

    // new code
    public static final String SELLER_NOTFOUND = "520001"; // 信息错误,卖家未找到
    public static final String SELLER_INF_LOST = "520002"; // 卖家信息不能空
    public static final String SELLER_SAME_BUYER = "520003"; // 买家和卖家信息为同一用户
    public static final String INVALIDATE_USER = "520004"; // 无效用户
    public static final String BLACK_NAME_USER = "520005"; // 商户被列为黑名单

    public static final String BUYER_NOTFOUND = "530001"; // 信息错误,买家未找到
    public static final String TRX_PROCESSFAILURE = "530002"; // 交易处理失败
    public static final String ORDER_STS_NOTPAY = "530003"; // 订单状态已改变,不能支付
    public static final String ORDER_CHECK_EXCEPTION = "530004"; // 交易检查异常
    public static final String ROYATY_PARAME_MISSING = "530005"; // 缺少分润参数
    public static final String TRX_EXPIRECLOSED = "530006"; // 交易超时已关闭
    public static final String MERCHANT_SERVICESTS = "530007"; // 客户服务状态异常
    public static final String BUYER_PAYPWD_WRONG = "530008"; // 支付密码错误
    public static final String PAGE_VERFIYCODE = "530009"; // 验证码错误
    public static final String BUYER_ACCOUNT_NOTFOUND = "530010"; // 账户查询失败
    public static final String BUYER_WAIT_OVERTIME = "530011"; // 会话时间过长
    public static final String BUYER_STATEILL = "530012"; // 买家状态异常
    public static final String PAGE_MOBILE_VERFIYCODE = "530024";// 手机验证码错误
    public static final String PAGE_MOBILE_RVERFIYCODE = "530025";// 请输入手机验证码错误
    public static final String PAYACCOUNTNO_UNBINDCODE = "530026";// 付款账号未设定定向支付绑定

    public static final String CCB_MD5VERIFY = "201005"; // CCB
    public static final String CBP_SIGNVERIFY = "201006"; // CBP
    public static final String CMB_MD5VERIFY = "201007"; // Cmb
    public static final String CMBC_VERIFY = "201008"; // CmbC
    public static final String COMM_VERIFY = "201009"; // COMM
    public static final String GDB_VERIFY = "201010"; // gdb
    public static final String GYL_VERIFY = "201011"; // gYL
    public static final String SDB_VERIFY = "201012"; // SDB
    public static final String MATERCARD_VE_FAIL = "201101"; // VE验证失败
    public static final String VISA_TRANS_ORDER = "201102"; // visa 订单数据验证错误
    public static final String JCB_VERIFY = "201103"; // JCB外卡验证错误
    public static final String JCB_VE_FAILURE = "201104"; // VE 验证失败
    public static final String JCB_VE_OTHERFAILURE = "201105"; // 验证失败
    public static final String CRYPT_MODE_NOTSUPPORT = "200901"; // 不支持的安全方式,参数有误
    public static final String CRYPT_EXCEPTION = "200902"; // 加密出现异常
    public static final String CRYPT_VALIADATESIGN = "200903"; // 数据签名验证未通过
    public static final String KEY_MISS = "200904"; // 证书不存在
    public static final String MPI_VEFAILURE = "201300"; // VE验证失败
    public static final String TFT_VERIFY_RETURNSIGN = "201301"; // 腾付通返回信息验签失败

    public static final String RISK_BLACK = "201106"; // 该号列入风险控制
    public static final String RISK_3D_NEED = "201107"; // 商户只能通过3D验证
    public static final String RISK_UNKNOWN = "201108"; // 未知风险
    public static final String RISK_EXCEPTION = "201109"; // 验证异常，存在风险,请稍后重试
    public static final String RISK_BLACK_MERCHANT = "201114"; // 验证异常，存在风险,请稍后重试

    public static final String WEB_PARAMNULL = "300800"; // WEB 参数缺少
    public static final String WEB_PARAMFORMAT = "300801"; // 参数类型或格式错误
    public static final String WEB_PARAMEMPTY = "300802"; // 参数空值错误
    public static final String WEB_PARAM_LENGTH_OVERFLOW = "300803"; // 参数长度溢出
    public static final String WEB_PARAM_LENGTH = "300804"; // 长度不符设定
    public static final String WEB_PARAM_LOST = "300805"; // 缺少必要参数
    public static final String WEB_PARAM_CONFLICT = "300806"; // 参数冲突
    public static final String WEB_PARAM_FILTER = "300807"; // 参数过滤错误,含非法字符
    public static final String WEB_EDUCATION_FEE = "300808"; // 教育类商户手续费计算错误
    public static final String WEB_EDUCATION_MIN_FEE = "300809"; // 教育类商户手续费不能低于最低手续费
    public static final String WEB_EDUCATION_MAX_FEE = "300810"; // 教育类商户手续费不能高于最高手续费
    public static final String WEB_LITE_MAX_SINGLE_LIMIT = "300811"; // 订单金额超过单笔限额

    public static final String JCB_SERVICE_REQUESTSAME = "501101"; // 重复的服务请求
    // "Please do not use your BACK or RELOAD/REFRESH browser functions or CLOSE your browser while using this service. ");
    public static final String MASTERCARD_SERVICE_REQUESTSAME = "501102";
    public static final String MASTERCARD_ORDERNULL = "501103"; // 订单号不能空
    public static final String JCB_ORDERNULL = "501104"; // 订单号不能空

    public static final String MASTERCARD_SERVERCONNECT = "601301"; // MASTERCARD
    // MPI 连接错误
    public static final String MASTERCARD_ORDERVALIDATION = "511101"; // 订单验证

    public static final String ORDER_IDNULL = "500902"; // 缺少订单号
    public static final String ORDER_OVERFLOW = "500903"; // 订单号过长
    public static final String ORDER_XMLRESOVERL = "500800"; // 错误的数据格式,不能解析
    public static final String ORDER_SAVE = "500700"; // 订单不能保存
    public static final String ORDER_NOTFOUND = "500701"; // 订单不存在
    public static final String SERVICE_NOTPROVIED = "500702"; // 服务未提供
    public static final String MERCHANT_IDNOTFOUND = "500703"; // 商户找不到
    public static final String MERCHANT_STATEILL = "500704"; // 商户状态
    public static final String ORDER_PAYNOTFOUND = "500705"; // 订单支付信息未找到
    public static final String STATE_UPDATEFAILURE = "500706"; // 状态更新失败
    public static final String MERCHANT_IDNULL = "500708"; // 缺少商户ID号
    public static final String SERVICE_CREDITNOTSUPPORT = "500709"; // 该商户未开通外卡支付
    public static final String POS_MERCHANTNOTFOUND = "500711"; // 该业务对应的商户不存在
    public static final String ORDER_ISEXIST = "500712"; // 订单已存在
    public static final String ORDER_FRAUD_CHECK = "500713"; // 无效来源，本次请求被拒绝
    public static final String ORDER_URL_OUTTIME = "500714"; // url超时，本次请求拒绝
    public static final String ORDER_PAYERNOTFOUND = "500715"; // 付款客户号为空
    public static final String ORDER_PAYENONOTFOUND = "500716"; // 付款账号为空
    public static final String TFT_GETORDER_URL_FAILURE = "500717"; // 腾付通获取订单支付url失败

    public static final String ORDER_VALIDATE_AMOUNT = "501132"; // 无效支付金额
    public static final String ORDER_VERSION_DISABLE = "501133"; // 版本号不能识别

    public static final String CHECK_LOGIN = "508001"; // 支付密码校验错误

    public static final String BOC_COMMUNICATIONERROR = "601000"; // 中行网络故障,服务不能提供
    public static final String BOC_SIGNERROR = "601001"; // 请求中国银行数据签名出现故障，服务中止
    public static final String COMM_COMMUNICATIONERROR = "601002"; // 交通银行网络故障,服务不能提供
    public static final String COMM_SIGNERROR = "601003"; // 请求交通银行数据签名出现故障，服务中止
    public static final String EXE_ACCESSTIMEOUT = "601300"; // 底层通信超时或服务拒绝
    public static final String MPI_HANDLEVEFAILURE = "601301"; // MPI底层处理VE失败
    public static final String MPI_DISABLED3D = "601302"; // 该信用卡未在发卡行开通3D验证服务
    public static final String MPI_UNIDENTIFY = "601303"; // 发卡行无法对该卡认证
    public static final String MPI_PAFAILURE = "601304"; // 密码验证操作失败，支付取消
    public static final String MPI_PAEXCEPTION = "601305"; // 密码验证中出现故障，请稍后重试
    public static final String MPI_AUTHFAIL = "601306"; // 持卡人认证失败
    public static final String MPI_AUTHNOTPERFORMED = "601307"; // 持卡人未能成功认证
    public static final String EPOS_EXCEPTION = "601308"; // epos异常
    public static final String CARD_TRANS_DISABLED = "601309"; // 银行限制该类卡交易

    public static final String SEQ_GET_FAILURE = "601600"; // 连数据库的网络故障,稍后重试

}
