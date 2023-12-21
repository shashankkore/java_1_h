package com.cvs.crm.cukes.IBotta.hooks;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.CustSearchService;
import com.cvs.crm.util.TestDataSetupUtil;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IBottaHook {


    @Autowired
    CustSearchService custSearchService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    TestDataSetupUtil testDataSetupUtil;

    @Before(value = "@IBotta")
    public void setup() throws ParseException, InterruptedException, SQLException {
    	testDataSetupUtil.createUserWithXtraCardNbr(48720001);
    }


    @After(value = "@IBotta")
    public void teardown() {
    	testDataSetupUtil.deleteGetCustCarepassEnrollmentTestData();
    }
}
