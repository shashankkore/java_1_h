package com.cvs.crm.cukes.SetCustCarepass.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.SetCustCarepassEnrollmentService;
import com.cvs.crm.stubs.SetCustCarepassEnrollmentStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class SetcustCarepassHook {

//	 @Autowired
//	 PharmacyHealthRewardsData pharmacyHealthRewardsData;

    @Autowired
    SetCustCarepassEnrollmentService setCustCarepassEnrollmentService;

    @Autowired
    SetCustCarepassEnrollmentStub setCustCarepassEnrollmentStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for SetCust Carepass Enrollment
     */
    @Before(value = "@SetCustCarepass")
    public void setup() throws ParseException, InterruptedException {

        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
            setCustCarepassEnrollmentStub.createSetCustCarepassEnrollmentStubData();

        } else {
            // setup Test Data
            setCustCarepassEnrollmentService.createSetCustCarepassEnrollmentTestData();
        }
    }


    /**
     * Tear Down SetCust-Carepass Enrollment after Test
     */
    @After(value = "@SetCustCarepass")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            setCustCarepassEnrollmentStub.deleteSetCustCarepassEnrollmentStubData();
        } else {

            // Tear down Test Data
            setCustCarepassEnrollmentService.deleteSetCustCarepassEnrollmentTestData();
        }
    }
}
