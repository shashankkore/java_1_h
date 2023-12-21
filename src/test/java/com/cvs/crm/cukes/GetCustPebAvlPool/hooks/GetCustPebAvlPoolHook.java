package com.cvs.crm.cukes.GetCustPebAvlPool.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.GetCustTablesnPreferencesService_old;
import com.cvs.crm.stubs.GetCustTablesandPreferencesStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class GetCustPebAvlPoolHook {

    @Autowired
    GetCustTablesnPreferencesService_old getCustTablesnPreferencesServiceOld;

    @Autowired
    GetCustTablesandPreferencesStub getCustTablesandPreferencesStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for GetCust Tables and Preferences
     */
    @Before(value = "@GetCustTablesnPreferences")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
            getCustTablesandPreferencesStub.createGetCustTablesnPreferencesStubData();
        } else {
            // setup Test Data
            getCustTablesnPreferencesServiceOld.createGetCustTablesnPreferencesTestData();
        }
    }


    /**
     * Tear Down GetCust Tables and Preferences test data
     */
    @After(value = "@GetCustTablesnPreferences")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            getCustTablesandPreferencesStub.deleteGetCustTablesnPreferencesStubData();
        } else {

            // Tear down Test Data
            getCustTablesnPreferencesServiceOld.deleteGetCustTablesnPreferencesTestData();
        }
    }
}
