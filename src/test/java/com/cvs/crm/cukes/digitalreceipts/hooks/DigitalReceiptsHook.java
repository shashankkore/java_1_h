package com.cvs.crm.cukes.digitalreceipts.hooks;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.CustSearchService;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DigitalReceiptsHook {


    @Autowired
    CustSearchService custSearchService;

    @Autowired
    ServiceConfig serviceConfig;

    @Before(value = "@DigitalReceipts")
    public void setup() throws ParseException, InterruptedException, SQLException {

    }


    @After(value = "@DigitalReceipts")
    public void teardown() {
            
    }
}
