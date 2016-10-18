package bb.org.bd.schedules;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import bb.org.bd.utils.DateFormater;

public class JobC extends QuartzJobBean {
	
	private static final Logger logger = Logger.getLogger(JobC.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
		throws JobExecutionException {
		//System.out.println("Job C is runing at: " + new Date());
		//System.out.println("Yesterday is: " + DateFormater.getYesterdayDateString());
		System.out.println("testing: " + DateFormater.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		logger.debug("testing: " + DateFormater.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		
		//ExpXmlGenerator oExpXmlGenerator = new ExpXmlGenerator();
		//oExpXmlGenerator.generateExpXmlForHolidays();
		
/*		try {
			System.out.println(EncryptDecryptMgr.encrypt("exp"));
			System.out.println(EncryptDecryptMgr.encrypt("Exp123"));
			System.out.println(EncryptDecryptMgr.encrypt("jdbc:oracle:thin:@10.11.100.50:1521:forex"));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
