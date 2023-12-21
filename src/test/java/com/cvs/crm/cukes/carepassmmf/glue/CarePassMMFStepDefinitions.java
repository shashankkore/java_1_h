package com.cvs.crm.cukes.carepassmmf.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.CarePassService;
import com.cvs.crm.service.SetCustCarepassMMFService;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import com.cvs.crm.util.TestDataSetupUtil;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class CarePassMMFStepDefinitions extends SpringIntegrationTests implements En {

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
    TestDataSetupUtil testDataSetupUtil;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;
    
    @Autowired
    SetCustCarepassMMFService setCustCarepassMMFService;
   
    public CarePassMMFStepDefinitions() {
        {
            DashBoardRequest request = new DashBoardRequest();
            SetCustRequest setCustRequest = new SetCustRequest();
            
            Given("{string}", (String scenario) -> {
            });
            
            Given("I am a CVS user with {int}", (Integer xtraCardNbr) -> {
            	// Create a user with extra card number
            	testDataSetupUtil.createUserWithXtraCardNbr(xtraCardNbr);
  		  	});
            
            When("I use Extra Card {int} to enroll in to carepass monthly", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassEnrollMonthly(setCustRequest);
            });
            
            When("I use Extra Card {int} to enroll in to mmf carepass", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMMFMonthly(setCustRequest);
            });
            
            When("I use Extra Card {int} to enroll in to mmf carepass monthly", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassEnrollMonthly(setCustRequest);
            });
           
            When("I use Extra Card {int} to reissue {int} mmf carepass coupon", (Integer xtraCardNbr, Integer month) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMMFMonthly(setCustRequest);
            });
 
            Then("The user enroled in to carepass annual successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
             });

            When("I use Extra Card {int} to enroll in to mmf carepass annual", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMMFMonthly(setCustRequest);
            });

            When("use Extra Card {int} to issue mmf coupons for non carepass users", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassEnrollMonthly(setCustRequest);
            });

            Then("Validate the promo mmf coupon issued to non carepass user", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            	
            });

            When("I redeem mmf carepass coupon", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("Validate the promo mmf coupon redeemed successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });

            When("I use Extra Card {int} to reissue <number> mmf carepass coupon", (Integer int1) -> {
               
            });

            When("The user campaign should be kept on hold", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("validate the user can not be issued new coupons", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("use Extra Card {int} to issue mmf coupons with {string}", (Integer xtraCardNbr, String ReissueMmfCoupon) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMMFMonthly(setCustRequest);
            });

            When("The user carepass membership is kept on hold", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("validate the user carepass membership is on hold", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });

            When("I use Extra Card {int} to issue non mmf coupons", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMMFMonthly(setCustRequest);
            });

            Then("Validate the promo non mmf coupon issued to carepass user", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200); 
            });
            Then("Validate the promo mmf coupon issued to carepass user", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            Then("The user enroled in to carepass monthly successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("The user enroll {int} in to mmf for reissue coupons", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMMFMonthly(setCustRequest);
            });
            
            Then("Validate the coupon issued no of times", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });

            When("I use Extra Card {int} to enroll in to carepass annually", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassEnrollAnnually(setCustRequest);
            });
            
            Then("The user enroled in to carepass annual subscription successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("I use Extra Card {int} to renew carepass monthly enrollment", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassRenewMonthly(setCustRequest);
            });
            
            Then("The user renewed in to carepass monthly successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("I use Extra Card {int} to renew carepass annual enrollment", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassRenewAnnual(setCustRequest);
            });
            
            Then("The user renewed in to carepass annual successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("use Extra Card {int} to unenroll in to monthly carepass", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMonthlyCancellation(setCustRequest);
            });
            
            Then("The user unenrolled in to carepass monthly successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("use Extra Card {int} to unenroll in to annual carepass", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassAnnualCancellation(setCustRequest);
            });
            
            Then("The user unenrolled in to carepass annual successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("use Extra Card {int} to reenroll in to monthly carepass", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassEnrollMonthly(setCustRequest);
            });
            
            Then("The user reenrolled in to carepass monthly successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("use Extra Card {int} to reenroll in to annual carepass", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassEnrollAnnually(setCustRequest);
            });
            
            Then("The user reenrolled in to carepass annual successfully", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            
            When("use Extra Card {int} to put carepass monthly membership on hold", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMonthlyOnHold(setCustRequest);
            });
            
            Then("I validate the user carepass monthly membership is on hold", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("use Extra Card {int} to put carepass annual membership on hold", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassAnnualOnHold(setCustRequest);
            });
            
            Then("I validate the user carepass annual membership is on hold", () -> {
            	setCustCarepassMMFService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            When("The user with {int} switch monthly membership to annual", (Integer xtraCardNbr) -> {
            	setCustRequest.setSearchCardNbr(xtraCardNbr);
            	setCustRequest.setSearchCardType("0002");
            	setCustCarepassMMFService.setCustCarePassMonthlyToAnnualSwitch(setCustRequest);
            });
            
            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });


            When("I view CarePass Details in DashBoard for my card", () -> {
                carePassService.viewCarePass(request);
            });

            Then("I see the Carepass Rewards", () -> {
                carePassService.getServiceResponse().then().log().all().statusCode(200);
            });


            Then("I do not see the Carepass Rewards", () -> {
                carePassService.getServiceResponse().then().log().all().statusCode(404);
            });

            Then("I validate the {string}", (String errorMessage) -> {
  			  ApiErrorResponse apiErrorResponse = setCustCarepassMMFService.getServiceResponse().as(ApiErrorResponse.class);
  			  Assert.assertEquals(errorMessage, apiErrorResponse.getErrorMsg());
  		  	});
            
            Then("I see my Carepass reward Next Issue Days Count as {int}", (Integer rewardNextIssueDaysCount) -> {
                DashboardResponse dashboardResponse = carePassService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(rewardNextIssueDaysCount, dashboardResponse.getCarepassResponse().getRewardNextIssueDaysCount());
            });

            Then("I see my Carepass status Code as {string}", (String statusCode) -> {
                DashboardResponse dashboardResponse = carePassService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(statusCode, dashboardResponse.getCarepassResponse().getStatusCode());
            });

            Then("I see my Carepass coupon expiry Date as {string}", (String couponExpiryDate) -> {
                Integer expiryDt = Integer.valueOf(couponExpiryDate.replaceAll("[^0-9]", ""));
                String expectedExpiryDate = carePassDateUtil.carePassExpireDate(expiryDt);
                DashboardResponse dashboardResponse = carePassService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(expectedExpiryDate, dashboardResponse.getCarepassResponse().getCouponExpiryDate());
            });

            Then("I see my Carepass enrollment expiry Date as {string}", (String enrollmentExpiryDate) -> {
                Integer enrollExpiryDt = Integer.valueOf(enrollmentExpiryDate.replaceAll("[^0-9]", ""));
                String expectedEnrollExpiryDate = carePassDateUtil.carePassExpireDate(enrollExpiryDt);
                DashboardResponse dashboardResponse = carePassService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(expectedEnrollExpiryDate, dashboardResponse.getCarepassResponse().getEnrollmentExpiryDate());
            });

            Then("I see my Carepass coupon Sequence Number as {string}", (String couponSequenceNumber) -> {
                Long couponSequenceNumberExpected;
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                    couponSequenceNumberExpected = Long.valueOf(couponSequenceNumber.replace("_", "0"));
                } else {
                    String[] cardNbrCampNbr = couponSequenceNumber.split("_");
                    int cardNbr = Integer.parseInt(cardNbrCampNbr[0]);
                    int campNbr = Integer.parseInt(cardNbrCampNbr[1]);
                    xtraCardActiveCoupon.setXtraCardNbr(cardNbr);
                    xtraCardActiveCoupon.setCmpgnId(campNbr);
                    couponSequenceNumberExpected = xtraCardDao.selectXtraCardActiveCoupon(xtraCardActiveCoupon);
                }
                DashboardResponse dashboardResponse = carePassService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(couponSequenceNumberExpected, dashboardResponse.getCarepassResponse().getCouponSequenceNumber());
            });

            Then("I see my Carepass campaign Id as {int}", (Integer campaignId) -> {
                DashboardResponse dashboardResponse = carePassService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(campaignId, dashboardResponse.getCarepassResponse().getCampaignId());
            });

            Then("I see my Carepass coupon Number as {int}", (Integer couponNumber) -> {
                DashboardResponse dashboardResponse = carePassService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(couponNumber, dashboardResponse.getCarepassResponse().getCouponNumber());
            });

        }
    }
}
