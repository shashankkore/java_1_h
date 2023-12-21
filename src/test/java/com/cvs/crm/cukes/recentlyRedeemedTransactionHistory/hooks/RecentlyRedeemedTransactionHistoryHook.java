package com.cvs.crm.cukes.recentlyRedeemedTransactionHistory.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.RecentlyRedeemedTransactionHistoryService;
import com.cvs.crm.stubs.RecentlyRedeemedTransactionHistoryStub;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class RecentlyRedeemedTransactionHistoryHook {

    @Autowired
    RecentlyRedeemedTransactionHistoryService recentlyRedeemedTransactionHistoryService;

    @Autowired
    RecentlyRedeemedTransactionHistoryStub recentlyRedeemedTransactionHistoryStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for RecentlyRedeemedTransactionHistory
     */
    @Before(value = "@RecentlyRedeemTranHistory")
    public void setup() throws ParseException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            recentlyRedeemedTransactionHistoryStub.createRecentlyRedeemedHistoryStubData();
        } else {

            // setup Test Data
            recentlyRedeemedTransactionHistoryService.createRecentlyRedeemedHistoryTestData();
        }
    }


    /**
     * Tear Down RecentlyRedeemTranHistory data after Test
     */
    @After(value = "@RecentlyRedeemTranHistory")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            recentlyRedeemedTransactionHistoryStub.deleteRecentlyRedeemedHistoryStubData();
        } else {

            // Tear down Test Data
            recentlyRedeemedTransactionHistoryService.deleteRecentlyRedeemedHistoryTestData();
        }
    }
}