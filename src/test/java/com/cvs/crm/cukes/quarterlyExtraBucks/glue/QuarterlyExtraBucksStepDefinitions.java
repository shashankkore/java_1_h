package com.cvs.crm.cukes.quarterlyExtraBucks.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.model.response.QuarterlyExtraBucksResponse;
import com.cvs.crm.service.QuarterlyExtraBucksService;
import com.cvs.crm.util.DateUtil;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class QuarterlyExtraBucksStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    QuarterlyExtraBucksService quarterlyExtraBucksService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    public QuarterlyExtraBucksStepDefinitions() {
        {
            DashBoardRequest request = new DashBoardRequest();
            Given("{string}", (String scenario) -> {

            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });

            When("I view Quarterly Extra Bucks Details in DashBoard for my card", () -> {
                quarterlyExtraBucksService.viewQuarterlyExtraBucks(request);
            });

            Then("I see the QEB Rewards", () -> {
                quarterlyExtraBucksService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I see my QEB Campaign Id as {int}", (Integer campaignId) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(campaignId, dashboardResponse.getQuarterlyExtraBucksResponse().getCampaignId());
            });

            Then("I see my QEB {string}", (String webDescription) -> {
                String webDescriptionYearExpected = dateUtil.webDescriptionYear();
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(webDescriptionYearExpected, dashboardResponse.getQuarterlyExtraBucksResponse().getWebDescription());
            });


            Then("I see my QEB Campaign End Date as {int}", (Integer campaignEndDate) -> {
                String campaignEndDateExpected = dateUtil.campaignEndDate(campaignEndDate);
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(campaignEndDateExpected, dashboardResponse.getQuarterlyExtraBucksResponse().getCampaignEndDate());
            });

            Then("I see my QEB Threshold Type Code as {string}", (String thresholdTypeCode) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(thresholdTypeCode, dashboardResponse.getQuarterlyExtraBucksResponse().getThresholdTypeCode());
            });

            Then("I see my QEB First Threshold as {double}", (Double firstThreshold) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(firstThreshold, dashboardResponse.getQuarterlyExtraBucksResponse().getFirstThreshold());
            });

            Then("I see my QEB Reward Amount as {double}", (Double rewardAmount) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(rewardAmount, dashboardResponse.getQuarterlyExtraBucksResponse().getRewardAmount());
            });

            Then("I see my QEB Points required to get next coupon as {double}", (Double pointsToNextThreshold) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(pointsToNextThreshold, dashboardResponse.getQuarterlyExtraBucksResponse().getPointsToNextThreshold());
            });

            Then("I see my QEB Offer Limit Reached as {string}", (String offerLimitReached) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertFalse(dashboardResponse.getQuarterlyExtraBucksResponse().getOfferLimitReached());
            });

            Then("I see my QEB Extrabuck Rewards Earned as {double}", (Double extrabuckRewardsEarned) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(extrabuckRewardsEarned, dashboardResponse.getQuarterlyExtraBucksResponse().getExtrabuckRewardsEarned());
            });


            Then("I see my QEB Coupon Issue Date as {int}", (Integer couponIssueDate) -> {
                String couponIssueDateExpected = dateUtil.couponIssueDate(couponIssueDate);
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(couponIssueDateExpected, dashboardResponse.getQuarterlyExtraBucksResponse().getCouponIssueDate());
            });

            Then("I see my QEB Points Progress as {double}", (Double pointsProgress) -> {
                DashboardResponse dashboardResponse = quarterlyExtraBucksService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(pointsProgress, dashboardResponse.getQuarterlyExtraBucksResponse().getPointsProgress());
            });

            When("I view quarterlyExtraBucks Details in DashBoard for my card", () -> {
                quarterlyExtraBucksService.viewQuarterlyExtraBucks(request);
            });

            Then("I do not see the QEB Rewards", () -> {
                quarterlyExtraBucksService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see Error Code as {int}", (Integer errorCd) -> {
                ApiErrorResponse apiErrorResponse = quarterlyExtraBucksService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });

            Then("I see Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = quarterlyExtraBucksService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });
        }
    }
}