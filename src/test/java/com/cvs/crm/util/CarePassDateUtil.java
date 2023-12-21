package com.cvs.crm.util;

import com.cvs.crm.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class CarePassDateUtil {
	  @Autowired
	  ServiceConfig serviceConfig;

	SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat simpleDateFormat_ddMMMyy = new SimpleDateFormat("dd-MMM-yy");
	SimpleDateFormat simpleDateFormat_Ts = new SimpleDateFormat("yyyy-MM-dd HH.MM.SS a");
	SimpleDateFormat simpleDateFormat_yyyyMMddHHmmssZ = new SimpleDateFormat("yyyyMMddHHmmssZ");
	SimpleDateFormat simpleDateFormat_yyyy =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);


//	CarePass TSWTZ with pattern : yyyy-MMM-dd, yyyyMMddHH, yyyyMMddHH:mm:ss, yyyyMMddHH:mm:ssZ
	public String carePassTswtz(int days, String pattern) {
		DateTime dateTime = new DateTime();
		String carePassDays  = dateTime.plusDays(days).toString(pattern);
		return carePassDays;
	}
	public String carePassEnrollTswtz() throws ParseException {
		DateTime dateTime = new DateTime();
		String todayTimeStamp = dateTime.toString("yyyyMMddHH:mm:ss");
		return todayTimeStamp;
	}

	  //CVS care pass program Expire Days
	  public String carePassExpireDate(Integer days) {
	  	DateTime dateTimeCp = new DateTime();
	  	Integer daysAdd = days;
	  	String carePassExpireDt = dateTimeCp.plusDays(daysAdd).toString("yyyy-MM-dd");
	  	return carePassExpireDt;
	  }
	  
	  public String carePassRedeemActlTswtzSetDt(Integer days) {
		  	DateTime dateTimeCp = new DateTime();
		  	Integer daysSub = days;
		  	String carePassRedeemActlTswtzSetDt = dateTimeCp.minusDays(daysSub).toString("yyyy-MM-dd");
		  	return carePassRedeemActlTswtzSetDt;
		  }

	  public Timestamp carePassEnrollTswtz(int expDays) throws ParseException {
		  int daysRemain = expDays;
		  Timestamp enrollTswtz = new Timestamp(System.currentTimeMillis());
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(enrollTswtz);
		  cal.add(Calendar.DAY_OF_WEEK, daysRemain);
		  enrollTswtz.setTime(cal.getTime().getTime()); 
	  	  return enrollTswtz;
	  }

	  public Timestamp carePassUnEnrollTswtz(int expDays) throws ParseException {
		  int daysRemain = expDays;
		  Timestamp unEnrollTswtz = new Timestamp(System.currentTimeMillis());
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(unEnrollTswtz);
		  cal.add(Calendar.DAY_OF_WEEK, daysRemain);
		  cal.add(Calendar.HOUR, 1);
		  unEnrollTswtz.setTime(cal.getTime().getTime()); 
	  	  return unEnrollTswtz;
	  }

	  public String carePassExpireTswtz(int expDays) {
		int daysRemain = expDays;
	  	DateTime dateTimeXp = new DateTime();
	    String expireTswtz  = dateTimeXp.plusDays(daysRemain).toString("yyyy-MM-dd");
	  	return expireTswtz;
	  }

	  public String carePassActionTswtzSetDt() {
	  	DateTime dateTimeXp = new DateTime();
	  	Integer mon = dateTimeXp.getMonthOfYear();
	  	Integer year = dateTimeXp.getYear();
	  	Integer day = dateTimeXp.getDayOfMonth();
	  	String actionTswtzSetDt= year.toString()+"-"+mon.toString()+"-"+day.toString();
	  	return actionTswtzSetDt;
	  }

	  public Date carePassPrntStartEndTswtz() throws ParseException {
	  	Timestamp prntStartEndTswtz = new Timestamp(System.currentTimeMillis());
	  	return prntStartEndTswtz;
	  }

	  public Timestamp carePassRedeemEndTswtz(int expDays) {
		  int daysRemain = expDays;
		  Timestamp redeemEndTswtz = new Timestamp(System.currentTimeMillis());
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(redeemEndTswtz);
		  cal.add(Calendar.DAY_OF_WEEK, daysRemain);
		  redeemEndTswtz.setTime(cal.getTime().getTime()); 	    
	      return redeemEndTswtz;
	  }
	/*  
	  public Timestamp carePassRedeemEndTswtzYear() {
		
		  Timestamp redeemEndTswtz = new Timestamp(System.currentTimeMillis());
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(redeemEndTswtz);
		  cal.add(Calendar.YEAR, 1);
		  cal.add(Calendar.DAY_OF_WEEK, 1);
		  cal.add(Calendar.MONTH, -1);
		  redeemEndTswtz.setTime(cal.getTime().getTime()); 	    
	      return redeemEndTswtz;
	  }
	  */
}
