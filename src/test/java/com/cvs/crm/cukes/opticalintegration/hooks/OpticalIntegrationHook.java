package com.cvs.crm.cukes.opticalintegration.hooks;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.config.ServiceConfig;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpticalIntegrationHook {

	/*
	 * @Autowired OpticalIntegrationService opticalIntegrationService;
	 * 
	 * @Autowired OpticalIntegrationStub opticalIntegrationStub;
	 */

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for SetCust Preferences
     */
    @Before(value = "@Optical")
    public void setup() throws ParseException, InterruptedException {
		/*
		 * if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) { // setup
		 * Stubs opticalIntegrationStub.createOpticalStubData();
		 * 
		 * } else { // setup Test Data opticalIntegrationStub.deleteOpticalStubData(); }
		 */
    }


    /**
     * Tear Down SetCust-Preferences after Test
     */
    @After(value = "@SetCustPreferences")
    public void teardown() {
		/*
		 * if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
		 * 
		 * // Tear down Stubs opticalIntegrationStub.deleteOpticalStubData(); } else {
		 * 
		 * // Tear down Test Data opticalIntegrationStub.deleteOpticalStubData(); }
		 */
    }
}
