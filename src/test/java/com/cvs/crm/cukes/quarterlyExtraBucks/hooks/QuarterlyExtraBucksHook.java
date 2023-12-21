package com.cvs.crm.cukes.quarterlyExtraBucks.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.QuarterlyExtraBucksService;
import com.cvs.crm.stubs.QuarterlyExtraBucksStub;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class QuarterlyExtraBucksHook {

    @Autowired
    QuarterlyExtraBucksService quarterlyExtraBucksService;

    @Autowired
    QuarterlyExtraBucksStub quarterlyExtraBucksStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for QuarterlyExtraBucks
     */
    @Before(value = "@QEB")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            quarterlyExtraBucksStub.createQuarterlyExtraBucksStubData();
        } else {

            // setup Test Data
            quarterlyExtraBucksService.createQuarterlyExtraBucksTestData();
        }
    }


    /**
     * Tear Down QuarterlyExtraBucks data after Test
     */
    @After(value = "@QEB")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            quarterlyExtraBucksStub.deleteQuarterlyExtraBucksStubData();
        } else {

            // Tear down Test Data
            quarterlyExtraBucksService.deleteQuarterlyExtraBucksTestData();
        }
    }
}