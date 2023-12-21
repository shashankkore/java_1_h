package com.cvs.crm.cukes.phr.hooks;

import com.cvs.crm.config.ServiceConfig;
//import com.cvs.crm.model.data.PharmacyHealthRewardsData;
import com.cvs.crm.service.PharmacyHealthRewardsService;
import com.cvs.crm.stubs.PharmacyHealthRewardsStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class PhrHook {

//	 @Autowired
//	 PharmacyHealthRewardsData pharmacyHealthRewardsData;

    @Autowired
    PharmacyHealthRewardsService pharmacyHealthRewardsService;

    @Autowired
    PharmacyHealthRewardsStub pharmacyHealthRewardsStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for PHR Summary
     */
    @Before(value = "@Phr")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            pharmacyHealthRewardsStub.createPharmacyHealthRewardsStubData();

        } else {

            // setup Test Data
            pharmacyHealthRewardsService.createPharmacyHealthRewardsTestData();
        }
    }


    /**
     * Tear Down PHR after Test
     */
    @After(value = "@Phr")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            pharmacyHealthRewardsStub.deletePharmacyHealthRewardsStubData();
        } else {

            // Tear down Test Data
            pharmacyHealthRewardsService.deletePharmacyHealthRewardsTestData();
        }
    }
}
