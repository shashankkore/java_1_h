package com.cvs.crm.cukes.campaign.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.service.CarePassService;
import com.cvs.crm.stubs.CarePassStub;
import com.cvs.crm.util.CarePassEnrollmentUtil;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class CampaignHook {

    @Autowired
    CarePassService carePassService;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

    @Autowired
    CarePassStub carePassStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for CarePass Summary
     */
    @Before(value = "@Campaign")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            // carePassStub.createCarePassStubData();

        } else {

            // setup Test Data
            // carePassService.createCarePassTestData();
        }
    }


    /**
     * Tear Down CarePass after Test
     */
    @After(value = "@Campaign")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            // carePassStub.deleteCarePassStubData();
        } else {

            // Tear down Test Data
            // carePassService.deleteCarePassTestData();
        }
    }
}
