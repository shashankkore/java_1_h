package com.cvs.crm.cukes.phrJ.enrollment.hooks;

import com.cvs.crm.config.ServiceConfig;
//import com.cvs.crm.model.data.PharmacyHealthRewardsData;
import com.cvs.crm.service.PharmacyHealthRewardsService;
import com.cvs.crm.stubs.PharmacyHealthRewardsStub;
import com.cvs.crm.util.PhrTestDataSetupUtil;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class PhrEnrollmentHook {

//	 @Autowired
//	 PharmacyHealthRewardsData pharmacyHealthRewardsData;

    @Autowired
    PharmacyHealthRewardsService pharmacyHealthRewardsService;

    @Autowired
    PharmacyHealthRewardsStub pharmacyHealthRewardsStub;

    @Autowired
    ServiceConfig serviceConfig;
    
    @Autowired
    PhrTestDataSetupUtil phrTestDataSetupUtil;

    /* *
     *  Setup Test Data for PHR Summary
     */
    @Before(value = "@Phrdashboard")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
//            pharmacyHealthRewardsStub.createPharmacyHealthRewardsStubData();

        } else {

            // setup Test Data
            pharmacyHealthRewardsService.createhRMemberProfileAndcampaignActivePointBaseTestData();
        }
    }


    /**
     * Tear Down PHR after Test
     */
    @After(value = "@Phrdashboard")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
//            pharmacyHealthRewardsStub.deletePharmacyHealthRewardsStubData();
        } else {

            // Tear down Test Data
        	pharmacyHealthRewardsService.deleteHRMemberProfileAndCampaignActivePointBaseTestData();
        }
    }
}
