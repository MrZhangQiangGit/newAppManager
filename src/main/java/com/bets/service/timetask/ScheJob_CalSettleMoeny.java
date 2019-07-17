package com.bets.service.timetask;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 定时清算
 * @author
 */
public class ScheJob_CalSettleMoeny  extends QuartzJobBean{
	    @Resource(name="calSettelMoney")
	    @Autowired
	    private CalSettleMoney deductMoney;   
		protected void executeInternal(JobExecutionContext arg0)  
	            throws JobExecutionException {  
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
			try {
				deductMoney.settlement();
				deductMoney.rankstatistics();
			} catch (Exception e) {
				e.printStackTrace();
			}  
	    }

}
