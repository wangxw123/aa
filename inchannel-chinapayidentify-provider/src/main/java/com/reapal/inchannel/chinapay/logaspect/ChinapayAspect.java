package com.reapal.inchannel.chinapay.logaspect;

import com.reapal.common.util.Sha1Utils;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by wanghao on 2016/12/11.
 */
@Aspect
public class ChinapayAspect {

	private static Log log = LogFactory.getLog(ChinapayAspect.class);

	/**
	 * chinapay鉴权切面
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around(value="execution(* com.reapal.inchannel.chinapay.service.impl.ChinapayIdentifyServiceImpl.identify(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		long beginTime = System.currentTimeMillis();
		InchannelIdentifyRequest inchannelIdentifyRequest = (InchannelIdentifyRequest)pjp.getArgs()[0];
		String sha1CardNo = Sha1Utils.HexString(inchannelIdentifyRequest.getCardNo());
		log.info("======请求参数sha1CardNo:"+ sha1CardNo +"----" + inchannelIdentifyRequest.toBase64String() +"【inchannel的cp鉴权接口开始时间】======" + beginTime);
		Object retVal = pjp.proceed();
		long endTime = System.currentTimeMillis();
		log.info("======请求参数sha1CardNo:"+ sha1CardNo +"----" + inchannelIdentifyRequest.toBase64String() +"【inchannel的cp鉴权接口结束】======" + endTime + "消耗时间:" + (endTime - beginTime));
		return retVal;
	}



}
