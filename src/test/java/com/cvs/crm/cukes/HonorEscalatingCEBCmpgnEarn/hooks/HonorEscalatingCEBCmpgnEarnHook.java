package com.cvs.crm.cukes.HonorEscalatingCEBCmpgnEarn.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.HonorEscalatingCEBCmpgnEarnService;
import com.cvs.crm.stubs.HonorEscalatingCEBCmpgnEarnStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.text.ParseException;

@Slf4j
public class HonorEscalatingCEBCmpgnEarnHook {

    @Autowired
    HonorEscalatingCEBCmpgnEarnService honorEscalatingCEBCmpgnEarnService;

    @Autowired
    HonorEscalatingCEBCmpgnEarnStub honorEscalatingCEBCmpgnEarnStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for HonorEscalating CEB CmpgnEarn
     */
    @Before(value = "@HonorEscalating-CEBCmpgnEarn")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
            honorEscalatingCEBCmpgnEarnStub.createHonorEscalatingCEBCmpgnEarnStubData();

        } else {
            // setup Test Data
            honorEscalatingCEBCmpgnEarnService.createHonorEscalatingCEBCmpgnEarnServiceTestData();
        }
    }


    /**
     * Tear Down Test Data for HonorEscalating CEB CmpgnEarn
     */
    @After(value = "@HonorEscalating-CEBCmpgnEarn")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            honorEscalatingCEBCmpgnEarnStub.deleteHonorEscalatingCEBCmpgnEarnStubData();
        } else {

            // Tear down Test Data
            honorEscalatingCEBCmpgnEarnService.deleteHonorEscalatingCEBCmpgnEarnServiceTestData();
        }
    }
}
