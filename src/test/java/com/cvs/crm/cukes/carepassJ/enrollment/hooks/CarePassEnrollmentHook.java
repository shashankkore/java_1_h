package com.cvs.crm.cukes.carepassJ.enrollment.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.service.CarePassService;
import com.cvs.crm.stubs.CarePassStub;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import com.cvs.crm.util.TestDataSetupUtil;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class CarePassEnrollmentHook {

    @Autowired
    CarePassService carePassService;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

    @Autowired
    CarePassStub carePassStub;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    TestDataSetupUtil testDataSetupUtil;
    

    /* *
     *  Setup Test Data for CarePass Summary
     */
    @Before(value = "@CarePass")
    public void setup() throws ParseException, InterruptedException {
    	// testDataSetupUtil.deleteGetCustCarepassEnrollmentTestData();
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
    @After(value = "@CarePass")
    public void teardown() {
    	testDataSetupUtil.deleteGetCustCarepassEnrollmentTestData();
		
	  /*if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
		  // Tear down Stubs 
		  carePassStub.deleteCarePassStubData(); } else {
		  // Tear down Test Data 
			  carePassService.deleteCarePassTestData(); }*/
	 
    }
}
