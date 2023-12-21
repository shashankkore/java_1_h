package com.cvs.crm.cukes.dealsInProgress.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.service.DealsInProgressService;
import com.cvs.crm.stubs.DealsInProgressStub;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class DealsInProgressHook {


    @Autowired
    DealsInProgressService dealsInProgressService;

    @Autowired
    DealsInProgressStub dealsInProgressStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for Deals In Progress
     */
    @Before(value = "@dealsInProgress")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            dealsInProgressStub.createDealsInProgressStubData();

        } else {

            // setup Test Data
//            dealsInProgressService.createDealsInProgressTestData();
        }
    }


    /**
     * Tear Down deals In Progress after Test
     */
    @After(value = "@dealsInProgress")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            dealsInProgressStub.deleteDealsInProgressStubData();
        } else {

            // Tear down Test Data
//            dealsInProgressService.deleteDealsInProgressTestData();
        }
    }
}
