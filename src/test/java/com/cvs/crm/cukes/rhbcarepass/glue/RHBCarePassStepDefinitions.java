package com.cvs.crm.cukes.rhbcarepass.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.request.RHBCouponRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.CarePassService;
import com.cvs.crm.service.RHBCouponsService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.CarePassDateUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class RHBCarePassStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    CarePassService carePassService;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    ServiceConfig serviceConfig;
    
    @Autowired
    RHBCouponsService rhbCouponsService;
    
    

    public RHBCarePassStepDefinitions() {
        {
        	RHBCouponRequest rhbCouponRequest = new RHBCouponRequest();

            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {string} and {string}", (String xtraCardNbr, String cardType) -> {
            	rhbCouponRequest.setSearchCardNbr(xtraCardNbr);
            	rhbCouponRequest.setSearchCardType(cardType);
            });


            When("I use Extra Card for given {string} and {string} to create RHB onboard coupon", (String progType, String couponEvntType) -> {
            	rhbCouponRequest.setProgType_Code(progType);
            	rhbCouponRequest.setCouponEventType(couponEvntType);
            	rhbCouponsService.issueRHBCoupons(rhbCouponRequest);
            });

            Then("The RHB device onboarding coupon created successfully", () -> {
            	rhbCouponsService.getServiceResponse().then().log().all().statusCode(201);
            });
            
            Then("I validate the {string}", (String errorMsg) -> {
            	rhbCouponsService.getServiceResponse().then().log().all().statusCode(400);
            });

        }
    }
}
