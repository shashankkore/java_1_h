package com.cvs.crm.cukes.GetCustCarepass.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.GetCustCarepassEnrollmentService;
import com.cvs.crm.stubs.GetCustCarepassEnrollmentStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class GetcustCarepassHook {

//	 @Autowired
//	 PharmacyHealthRewardsData pharmacyHealthRewardsData;

    @Autowired
    GetCustCarepassEnrollmentService getCustCarepassEnrollmentService;

    @Autowired
    GetCustCarepassEnrollmentStub getCustCarepassEnrollmentStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for SetCust Carepass Enrollment
     */
    @Before(value = "@GetCustCarepass")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
            getCustCarepassEnrollmentStub.createGetCustCarepassEnrollmentStubData();
        } else {
            // setup Test Data
            getCustCarepassEnrollmentService.createGetCustCarepassEnrollmentTestData();
        }
    }

    /**
     * Tear Down GetCust-Carepass Enrollment after Test
     */
    @After(value = "@GetCustCarepass")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // Tear down Stubs
            getCustCarepassEnrollmentStub.deleteGetCustCarepassEnrollmentStubData();
        } else {
            // Tear down Test Data
            getCustCarepassEnrollmentService.deleteGetCustCarepassEnrollmentTestData();
        }
    }
}
