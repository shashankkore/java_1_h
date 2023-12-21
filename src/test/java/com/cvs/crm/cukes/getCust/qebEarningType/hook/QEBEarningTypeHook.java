package com.cvs.crm.cukes.getCust.qebEarningType.hook;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.MfrStoreEBService;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Slf4j
public class QEBEarningTypeHook {

    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    GetCustService getCustService;

    @Before(value = "@GetCust_qebEarningType")
    public void setup() throws ParseException, InterruptedException, SQLException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // Setup Stubs
        } else {
        	getCustService.createGetCustExtraBuckRewardsSummaryAPITestData();
        }
    }

    @After(value = "@GetCust_qebEarningType")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Teardown Stubs
        } else {
        	getCustService.deleteGetCustExtraBuckRewardsSummaryAPITestData();

        }
    }
}
