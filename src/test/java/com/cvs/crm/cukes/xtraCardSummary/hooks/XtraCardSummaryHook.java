package com.cvs.crm.cukes.xtraCardSummary.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.XtraCardSummaryService;
import com.cvs.crm.stubs.XtraCardSummaryStub;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class XtraCardSummaryHook {

    @Autowired
    XtraCardSummaryService xtraCardSummaryService;

    @Autowired
    XtraCardSummaryStub xtraCardSummaryStub;

    @Autowired
    ServiceConfig serviceConfig;


    /**
     * Setup Test Data for XtraCard Summary
     */
    @Before(value = "@XtraCardSummary")
    public void setup() throws ParseException {

        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            xtraCardSummaryStub.createXtraCardSummaryStubData();
        } else {

            // setup Test Data
            xtraCardSummaryService.createXtraCardSummaryTestData();
        }
    }


    /**
     * Tear Down Xtra Cards after Test
     */
    @After(value = "@XtraCardSummary")
    public void teardown() {

        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            xtraCardSummaryStub.deleteXtraCardSummaryStubData();
        } else {

            // Tear down Test Data
            xtraCardSummaryService.deleteXtraCardSummaryTestData();
        }
    }
}
