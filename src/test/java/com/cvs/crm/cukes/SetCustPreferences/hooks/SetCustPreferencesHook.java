package com.cvs.crm.cukes.SetCustPreferences.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.SetCustPreferencesService;
import com.cvs.crm.stubs.SetCustPreferencesStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class SetCustPreferencesHook {

    @Autowired
    SetCustPreferencesService setCustPreferencesService;

    @Autowired
    SetCustPreferencesStub setCustPreferencesStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for SetCust Preferences
     */
    @Before(value = "@SetCustPreferences")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
            setCustPreferencesStub.createSetCustPreferencesStubData();

        } else {
            // setup Test Data
            setCustPreferencesService.createSetCustPreferencesTestData();
        }
    }


    /**
     * Tear Down SetCust-Preferences after Test
     */
    @After(value = "@SetCustPreferences")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            setCustPreferencesStub.deleteSetCustPreferencesStubData();
        } else {

            // Tear down Test Data
            setCustPreferencesService.deleteSetCustPreferencesTestData();
        }
    }
}
