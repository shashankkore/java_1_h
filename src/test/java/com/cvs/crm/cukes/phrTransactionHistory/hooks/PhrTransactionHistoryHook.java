package com.cvs.crm.cukes.phrTransactionHistory.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.PhrTransactionHistoryService;
import com.cvs.crm.stubs.PHRTransactionHistoryStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class PhrTransactionHistoryHook {

//	 @Autowired
//	 PharmacyHealthRewardsData pharmacyHealthRewardsData;

    @Autowired
    PhrTransactionHistoryService phrTransactionHistoryService;

    @Autowired
    PHRTransactionHistoryStub phrTransactionHistoryStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for PHR Transaction History
     */
    @Before(value = "@PHRTranHistory")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            phrTransactionHistoryStub.createPHRHistoryStubData();

        } else {

            // setup Test Data
            phrTransactionHistoryService.createPhrTransactionHistoryTestData();
        }
    }


    /**
     * Tear Down PHR after Test
     */
    @After(value = "@PHRTranHistory")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            phrTransactionHistoryStub.deletePHRHistoryStubData();
        } else {

            // Tear down Test Data
            phrTransactionHistoryService.deletePHRTransactionHistoryTestData();
        }
    }
}
