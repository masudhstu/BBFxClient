package bb.org.bd.schedules;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import bb.org.bd.utils.EncryptDecryptMgr;

public class JobC extends QuartzJobBean {
	
	//private static final Logger logger = Logger.getLogger(JobC.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
		throws JobExecutionException {
		//System.out.println("Job C is runing at: " + new Date());
		//System.out.println("Yesterday is: " + DateFormater.getYesterdayDateString());
		//System.out.println("testing: " + DateFormater.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		//logger.debug("testing: " + DateTimeManager.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		
		//logger.debug("Yesterday is holiday: " + DateTimeManager.isHoliday(DateTimeManager.getYesterdayDateString(), Constants.DATE_FORMAT));
		
		//logger.info("2016-10-21 is holiday: " + HolidayChecker.isHoliday("2016-10-21", "yyyy-MM-dd"));
		
		//ExpXmlGenerator oExpXmlGenerator = new ExpXmlGenerator();
		//oExpXmlGenerator.generateExpXmlForHolidays();
		
		/*try {
			System.out.println(EncryptDecryptMgr.encrypt("exp"));
			System.out.println(EncryptDecryptMgr.encrypt("Exp123"));
			System.out.println(EncryptDecryptMgr.encrypt("jdbc:oracle:thin:@10.11.100.50:1521:forex"));
			
			System.out.println(EncryptDecryptMgr.decrypt("KCZj2Snoa8M="));
			System.out.println(EncryptDecryptMgr.decrypt("X2gBSspq814="));
			System.out.println(EncryptDecryptMgr.decrypt("ZyDu5uzlO+BX5pHv7aZPcYvAmaS2iDQLJkUWW0s91yU8vnE8zzn8/X06YYY3Ffo1"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ 
	}
}
