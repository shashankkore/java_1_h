package com.cvs.crm.cukes.productAPI.hooks;


import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.ProductAPIService;
import com.cvs.crm.stubs.ProductAPIStub;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class ProductAPIHook {

    @Autowired
    ProductAPIService productAPIService;

    @Autowired
    ProductAPIStub productAPIStub;

    @Autowired
    ServiceConfig serviceConfig;

    /* *
     *  Setup Test Data for Product API
     */
    @Before(value = "@HealthlyExtraCareProducts")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // setup Stubs
            productAPIStub.createProductApiStubData();

        } else {
            // setup Test Data
            productAPIService.createProductAPITestData();
//            productAPIService.callTestDataAPIPost();
        }
    }


    /**
     * Tear Down Product API
     */
    @After(value = "@HealthlyExtraCareProducts")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Tear down Stubs
            productAPIStub.deleteProductApiStubData();
        } else {

            // Tear down Test Data
            productAPIService.deleteProductAPITestData();
        }
    }
}
