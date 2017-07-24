//package com.reapal.inchannel.cmbcpay.logaspect;
//
//import com.reapal.inchannel.model.InchannelIdentifyRequest;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//
///**
// * Created by huangg on 2015/6/30.
// */
//@Aspect
//public class CmbcAspect {
//
//	private static Log log = LogFactory.getLog(CmbcAspect.class);
//	/**
//	 * cmbc鉴权切面
//	 * @param pjp
//	 * @return
//	 * @throws Throwable
//	 */
//	@Around(value="execution(* CmbcpayIdentifyServiceImpl.identify(..))")
//	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
//		long beginTime = System.currentTimeMillis();
//		InchannelIdentifyRequest inchannelIdentifyRequest = (InchannelIdentifyRequest)pjp.getArgs()[0];
//		log.info("======请求参数:"+ inchannelIdentifyRequest.toString() +"【inchannel的cp签约查询开始时间】======" + beginTime);
//		Object retVal = pjp.proceed();
//		long endTime = System.currentTimeMillis();
//		log.info("======请求参数:"+ inchannelIdentifyRequest.toString() +"【inchannel的cp签约接口查询结束】======" + endTime + "消耗时间:" + (endTime - beginTime));
//		return retVal;
//	}
//
//
//}
