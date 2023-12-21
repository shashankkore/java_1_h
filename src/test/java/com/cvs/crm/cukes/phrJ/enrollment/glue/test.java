package com.cvs.crm.cukes.phrJ.enrollment.glue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public class test {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
//		int daysRemain = 0;
//	  	DateTime dateTimeXp = new DateTime();
//	    String expireTswtz  = dateTimeXp.toString("yyyy-MM-dd");
//	    System.out.println("time is " + expireTswtz.substring(8, 10));
//	    System.out.println(expireTswtz);
	    
	    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHH:mm:ss");
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//	    System.out.println("time is " + expireTswtz.substring(8, 10));
	    System.out.println(sdf3.format(timestamp));
	    
	    String jsonString = null;
	    String test = "test";
		
		jsonString  = "[\r\n" +
    			"{\n" +
    			"\"skuNumber\": \"12362\",\r\n" +
    			"\"dateViewed\": \"tesr\"\r\n" +
    			"},\n"
    			
    			+ "]";
		String jsonString2  = "{\r\n"
				+ "  \"recommendations\": [\r\n"
				+ "     {\r\n"
				+ "     \"recommendationType\": \"topSelling\",\r\n      "
				+ "      \"filters\": {\r\n        "
				+ "        \"storeNumber\": 5610\r\n       "
				+ "     }\r\n    "
				+ "    }\r\n    ]\r\n}";
		
		String jsonString23 = "{\r\n" + 
				"    \"recommendations\": [\r\n" + 
				"        {\r\n" + 
				"            \"recommendationType\": \"topSelling\",\r\n" + 
				"            \"filters\": {\r\n" + 
				"                \"storeNumber\": \"5610\"\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}";
	            
		System.out.println("input payload : " + jsonString);
		System.out.println("input payload : " + jsonString2);
		String pattern = "yyyy-MM-dd";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
//	    Date date = new Date();
//	    String dateCurrent = simpleDateFormat.format(date);
	
//	    String patternTs = "yyyy-MM-dd HH.MM.SS a";
//	    SimpleDateFormat simpleDateFormatTs = new SimpleDateFormat(patternTs);
	
	
//	    DateTime dateTime = new DateTime();
//	    String todayDate = dateTime.toString("yyyy-MM-dd");
//	    String yestardayDate = dateTime.minusDays(1).toString("yyyy-MM-dd");
//	    String tomorrowDate = dateTime.plusDays(1).toString("yyyy-MM-dd");
//	    String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
//	    String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");	    
//	    String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyyMMdd HH:MM:ss");

//	    for(int i=0;i<61;i++) {
//	    DateTime dateTime = new DateTime();
//	    String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyyMMdd hh:mm:ss");
//	    System.out.println("TS: " + yestardayTimeStamp);
//	    Thread.sleep(1000);
//	    }
	    
	    DateTime dateTime = new DateTime();
        String tomorrowTimeStamp = dateTime.plusDays(1).toString("dd-MMM-yy hh.mm.ss a");
        System.out.println("Date time stamp is: "+ tomorrowTimeStamp);
	}
	

}
