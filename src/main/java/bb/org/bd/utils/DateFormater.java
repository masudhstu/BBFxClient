package bb.org.bd.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormater {

	private final String dateFormat = "yyyy-MM-dd";

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

}
