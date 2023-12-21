package com.cvs.crm.cukes.carePass.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.CarePassService;

import com.cvs.crm.util.CarePassDateUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class CarePassStepDefinitions extends SpringIntegrationTests implements En {

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

    public CarePassStepDefinitions() {
        {
            DashBoardRequest request = new DashBoardRequest();

            Given("{string}", (String scenario) -> {
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
