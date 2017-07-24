package com.reapal.job;

import com.reapal.common.util.SpringUtils;
import com.reapal.inchannel.szjszxdk.service.SzjszxdkFtpService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * Created by wanghao on 16/11/03.
 */
//获取深圳结算中心对账文件
public class SzjszxAccountFileJob implements Job {


    private static Logger logger = Logger.getLogger(SzjszxAccountFileJob.class);

    private SzjszxdkFtpService szjszxdkFtpService = (SzjszxdkFtpService) SpringUtils.getBean("szjszxdkFtpService");

    private void getFiles() {

        logger.info("========【深圳结算中心生成FTP开始】========");
        szjszxdkFtpService.acquireSzjsFileByFtp("");
        logger.info("========【深圳结算中心生成FTP结束】========");

    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getFiles();
    }


}
