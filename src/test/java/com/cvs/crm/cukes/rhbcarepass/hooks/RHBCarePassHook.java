package com.cvs.crm.cukes.rhbcarepass.hooks;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.CarePassService;
import com.cvs.crm.stubs.CarePassStub;
import com.cvs.crm.util.CarePassEnrollmentUtil;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RHBCarePassHook {

    @Autowired
    CarePassService carePassService;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

    @Autowired
    CarePassStub carePassStub;

    @Autowired
    ServiceConfig serviceConfig;


    /* *
     *  Setup Test Data for RHB CarePass Summary
     */
    @Before(value = "@RHB")
    public void setup() throws ParseException, InterruptedException {
        
    }


    /**
     * Tear Down CarePass after Test
     */
    @After(value = "@RHB")
    public void teardown() {
       
    }
}
