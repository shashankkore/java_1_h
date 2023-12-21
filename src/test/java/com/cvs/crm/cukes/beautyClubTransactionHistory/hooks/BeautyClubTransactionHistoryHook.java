package com.cvs.crm.cukes.beautyClubTransactionHistory.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.BeautyClubTransactionHistoryService;
import com.cvs.crm.stubs.BeautyClubTransactionHistoryStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class BeautyClubTransactionHistoryHook {

    @Autowired
    BeautyClubTransactionHistoryService beautyClubTransactionHistoryService;

    @Autowired
    BeautyClubTransactionHistoryStub beautyClubTransactionHistoryStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for BeautyClubTransactionHistory
     */
    @Before(value = "@BeautyClubTranHistory")
    public void setup() throws ParseException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            beautyClubTransactionHistoryStub.createBeautyClubTransactionHistoryStubData();
        } else {

            // setup Test Data
            beautyClubTransactionHistoryService.createBeautyClubTransHistoryTestData();
        }
    }


    /**
     * Tear Down BeautyClubTransactionHistory data after Test
     */
    @After(value = "@BeautyClubTranHistory")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            beautyClubTransactionHistoryStub.deleteBeautyClubTransactionHistoryStubData();
        } else {

            // Tear down Test Data
            beautyClubTransactionHistoryService.deleteBeautyClubTransHistoryTestData();
        }
    }
}