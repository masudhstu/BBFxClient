package bb.org.bd.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateTimeManager {
	
	private static final Logger logger = Logger.getLogger(DateTimeManager.class);

	public static String formateDate(String strDate) {

		/*
		 * System.out.println(strDate); DateFormat df = new
		 * SimpleDateFormat("yyyy-mm-dd"); Date date = new Date(); try { date =
		 * df.parse(strDate); } catch (ParseException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * System.out.println(df.format(date));
		 * 
		 * return df.format(date);
		 */

		return strDate;
	}

	public static String formateDate(Date strDate) {

		System.out.println(strDate);

		// return df.format(strDate);
		return strDate.toString();
	}

	public static String getToday() {
		// return new SimpleDateFormat("yyyy-mm-dd").format(new Date());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}
	public static String getToday(String Format) {
		// return new SimpleDateFormat("yyyy-mm-dd").format(new Date());
		DateFormat dateFormat = new SimpleDateFormat(Format);
		Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}

	public static String getYesterdayDateString() {
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}

	public static String getCurrentDateTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}
	
	public static String getYesterdayDateString(String Format) {
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat(Format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}
	
	public static boolean parseDate(String dateTime, String Format)
	{		
		DateFormat dateFormat = new SimpleDateFormat(Format);
		try {
			dateFormat.parse(dateTime);
			return true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}		
	}
	
	public static boolean isHoliday(String dateString, String dateFormate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
			Date date = sdf.parse(dateString);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			//check weekend
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				return true;
			}
			
			// check Labor Day (1st Monday of September)
			// check 1st Monday of November
			if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER
				&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
				&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				return true;
			}
			
			// check if 16 December
			else if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
				&& cal.get(Calendar.DAY_OF_MONTH) == 16) {
				return true;
			}

		} catch (Exception e) {
			logger.warn("Exception: ", e);
		}

		return false;
	}

}
