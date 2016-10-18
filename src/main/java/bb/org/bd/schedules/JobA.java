package bb.org.bd.schedules;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import bb.org.bd.xmlmanager.ExpXmlGenerator;
import bb.org.bd.xmlmanager.LcXmlGenerator;

public class JobA extends QuartzJobBean {
	
	private static final Logger logger = Logger.getLogger(JobA.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
		throws JobExecutionException {
		System.out.println("Job A is runing at: " + new Date());
		try {
			
			LcXmlGenerator oLcXmlGenerator = new LcXmlGenerator();
			logger.info("LC XML For Holidays generated: " + oLcXmlGenerator.generateLCxmlForHolidays());
			
			ExpXmlGenerator expXmlGenerator = new ExpXmlGenerator();
			logger.info("Exp XML For Holidays generated: " + expXmlGenerator.generateExpXmlForHolidays());
			
		} catch (Exception e) {
			logger.warn("Exception: ", e);
			
		}
	}

}
