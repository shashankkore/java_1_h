package com.cvs.crm.cukes.quarterlyExtraBucksTransactionHistory.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.QEBTransactionHistoryService;
import com.cvs.crm.stubs.QEBTransactionHistoryStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;

@Slf4j
public class QEBTransactionHistoryHook {

    @Autowired
    QEBTransactionHistoryService qebTransactionHistoryService;

    @Autowired
    QEBTransactionHistoryStub qebTransactionHistoryStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for QEB Transaction History
     */
    @Before(value = "@QEBTranHistory")
    public void setup() throws ParseException, InterruptedException, IOException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            qebTransactionHistoryStub.createQEBTransactionHistoryStubData();

        } else {

            // setup Test Data
            qebTransactionHistoryService.createQEBTransHistoryTestData();
        }
    }


    /**
     * Tear Down QEBTranHistory after Test
     */
    @After(value = "@QEBTranHistory")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            qebTransactionHistoryStub.deleteQEBTransactionHistoryStubData();
        } else {

            // Tear down Test Data
            qebTransactionHistoryService.deleteQEBTransHistoryTestData();
        }
    }
}
