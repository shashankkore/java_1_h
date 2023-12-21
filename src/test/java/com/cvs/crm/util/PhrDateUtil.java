package com.cvs.crm.util;


import com.cvs.crm.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j

public class PhrDateUtil {
	@Autowired
	  ServiceConfig serviceConfig;
	
	public String phrEnrollmentTS() {
	  	DateTime dateTimeXp = new DateTime();
	  	String currentTswtz  = dateTimeXp.toString("yyyy-MM-dd");
	  	return currentTswtz;
	  }

}
