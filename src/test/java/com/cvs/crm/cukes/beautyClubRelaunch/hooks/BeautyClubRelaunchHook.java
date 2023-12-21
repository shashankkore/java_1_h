package com.cvs.crm.cukes.beautyClubRelaunch.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.BeautyClubRelaunchService;
import com.cvs.crm.stubs.BeautyClubRelaunchStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.text.ParseException;

@Slf4j
public class BeautyClubRelaunchHook {

    @Autowired
    BeautyClubRelaunchService beautyClubService;

    @Autowired
    BeautyClubRelaunchStub beautyClubRelaunchStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for BeautyClubRelaunch
     */
    @Before(value = "@BeautyClubRelaunch")
    public void setup() throws ParseException, InterruptedException, SQLException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            beautyClubRelaunchStub.createBeautyClubStubData();

        } else {

            // setup Test Data
            beautyClubService.createBeautyClubTestData();
        }
    }


    /**
     * Tear Down BeautyClub data after Test
     */
    @After(value = "@BeautyClubRelaunch")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            beautyClubRelaunchStub.deleteBeautyClubStubData();
        } else {

            // Tear down Test Data
            beautyClubService.deleteBeautyClubTestData();
        }
    }
}