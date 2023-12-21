package com.cvs.crm.cukes.beautyClub.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.BeautyClubService;
import com.cvs.crm.stubs.BeautyClubStub;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class BeautyClubHook {

    @Autowired
    BeautyClubService beautyClubService;

    @Autowired
    BeautyClubStub beautyClubStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for BeautyClub
     */
    @Before(value = "@BeautyClub")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            beautyClubStub.createBeautyClubStubData();

        } else {

            // setup Test Data
            beautyClubService.createBeautyClubTestData();
        }
    }


    /**
     * Tear Down BeautyClub data after Test
     */
    @After(value = "@BeautyClub")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            beautyClubStub.deleteBeautyClubStubData();
        } else {

            // Tear down Test Data
            beautyClubService.deleteBeautyClubTestData();
        }
    }
}