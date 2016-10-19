package bb.org.bd.schedules;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import bb.org.bd.utils.DateTimeManager;
import bb.org.bd.xmlmanager.ExpXmlGenerator;
import bb.org.bd.xmlmanager.LcXmlGenerator;

public class JobB extends QuartzJobBean {
	
	private static final Logger logger = Logger.getLogger(JobB.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
		throws JobExecutionException {
		System.out.println("Job B is runing at: " + new Date());
		System.out.println("today is: " + DateTimeManager.getToday("yyyyMMdd"));
		
		LcXmlGenerator oLcXmlGenerator = new LcXmlGenerator();
		logger.info("LC XML ForEveryday generated: " + oLcXmlGenerator.generateLCxmlForEveryday());
		
		ExpXmlGenerator oExpXmlGenerator = new ExpXmlGenerator();
		logger.info("EXP XML ForEveryday generated: " + oExpXmlGenerator.generateLCxmlForEveryday());
	}

}

/*
00:00:00 to 13:30:00 Current Day 
13:30:01 to 16:30:00 Current Day
16:30:01 to 24:00:00 Previous Da
*/
