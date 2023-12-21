package com.cvs.crm.cukes.BulkCoupons.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.BulkCouponsService;
import com.cvs.crm.stubs.BulkCouponsStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class BulkCouponsHook {
    @Autowired
    BulkCouponsService bulkCouponsService;

    @Autowired
    BulkCouponsStub bulkCouponsStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for MFR and Store EB coupons
     */
    @Before(value = "@Bulk-CpnAPI")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
            bulkCouponsStub.createBulkCpnStubData();

        } else {
            // setup Test Data
            bulkCouponsService.createBulkCpnServiceTestData();
        }  
    }


    /**
     * Tear Down Test Data for MFR and Store EB coupons
     */
    @After(value = "@Bulk-CpnAPI")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            bulkCouponsStub.deleteBulkCpnStubData();
        } else {

            // Tear down Test Data
            bulkCouponsService.deleteBulkCpnServiceTestData();
        }
    }
}