package bb.org.bd.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import bb.org.bd.utils.DateFormater;


public class HeaderParams {

	@NotNull
	private String dataType; // lc/exp/1/2
	@NotNull
	private String queryType;
	private String date;
	private String timeFrom;
	private String timeTo;
	private String lc;
	private String exp;
	
	public HeaderParams(String dataType, String queryType, String date, String timeFrom, String timeTo, String lc, String exp) {
		super();
		this.dataType = dataType;
		this.queryType = queryType;
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.lc = lc;
		this.exp = exp;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	
	@Override
	public String toString() {
		return "HeaderParams [dataType=" + dataType + ", queryType=" + queryType + ", date=" + date + ", timeFrom="
				+ timeFrom + ", timeTo=" + timeTo + ", lc=" + lc + ", exp=" + exp + "]";
	}
	
	public String validate()
	{
		String message = "";
		if(dataType.equals("1") && queryType.equals("1"))
		{
			/*try {
				BigDecimal bd=new BigDecimal(lc);
				//Integer.parseInt(lc);
				return "OK";
			} catch (Exception e) {
				return "LC ID is not Valid";
			}*/
			
			if (lc.matches("[0-9]+"))
				return "OK";
			else
				return "LC ID is not Valid"; 
			
		}
		else if(dataType.equals("2") && queryType.equals("1"))
		{
			if (exp.matches("[0-9]+"))
				return "OK";
			else
				return "EXP No is not Valid"; 
		}
		
		else if(queryType.equals("2"))
		{
			if(DateFormater.parseDate(date, "yyyy-MM-dd"))
				return "OK";
			else
				return "Date is not Valid as yyyy-MM-dd";			
		}
		else if(queryType.equals("3"))
		{
			//oLC = lcToCustomsService.findLcsByDateAndTimeRange(date, timeFrom, timeTo);
			if(DateFormater.parseDate(date, "yyyy-MM-dd") &&  DateFormater.parseDate(timeFrom, "HH:mm:ss") && DateFormater.parseDate(timeTo, "HH:mm:ss"))
				return "OK";
			else
			{
				if(DateFormater.parseDate(date, "yyyy-MM-dd"))
					message = "1. Date is not Valid as yyyy-MM-dd";
				if(DateFormater.parseDate(date, "yyyy-MM-dd"))
					message = message + " \n 2. timeFrom is not Valid as HH:mm:ss";
				if(DateFormater.parseDate(date, "yyyy-MM-dd"))
					message = message + "\n 3. timeTo is not Valid as HH:mm:ss";				
			}		
			
			return message;
		}
		else
		{
			//oLC = null;
			return "Invalid Parameters";
		}		
	}


}
