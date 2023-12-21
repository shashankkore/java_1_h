package com.cvs.crm.cukes.MfrStoreEB.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.MfrStoreEBService;
import com.cvs.crm.stubs.MfrStoreEBStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class MfrStoreEBHook {
    @Autowired
    MfrStoreEBService mfrStoreEBService;

    @Autowired
    MfrStoreEBStub mfrStoreEBStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for MFR and Store EB coupons
     */
    @Before(value = "@MFR-StoreEB")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
//            mfrStoreEBStub.createMfrStoreEBStubData();

        } else {
            // setup Test Data
            mfrStoreEBService.createMfrStoreEBServiceTestData();
        }
    }


    /**
     * Tear Down Test Data for MFR and Store EB coupons
     */
    @After(value = "@MFR-StoreEB")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
//            mfrStoreEBStub.deleteMfrStoreEBStubData();
        } else {

            // Tear down Test Data
            mfrStoreEBService.deleteMfrStoreEBServiceTestData();
        }
    }
}