package com.cvs.crm.cukes.beautyClub.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.model.response.BeautyClubResponse;
import com.cvs.crm.service.BeautyClubService;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class BeautyClubStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    BeautyClubService beautyClubService;

    @Autowired
    ServiceConfig serviceConfig;

    public BeautyClubStepDefinitions() {
        {
            DashBoardRequest request = new DashBoardRequest();

            Given("{string}", (String scenario) -> {

            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });

            When("I view Beauty Club Details in DashBoard for my card", () -> {
                beautyClubService.viewBeautyClub(request);
            });

            Then("I see the Beauty Club Rewards", () -> {
                beautyClubService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I see my Beauty Club enrollment status as {string}", (String enrolled) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertTrue(dashboardResponse.getBeautyClubRelaunchResponse().getEnrolled());
            });

            Then("I see my Beauty Club enrollment as {string}", (String enrolled) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertFalse(dashboardResponse.getBeautyClubRelaunchResponse().getEnrolled());
            });

            Then("I see my Beauty Club  Campaign Id as {int}", (Integer campaignId) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
         //       Assert.assertEquals(campaignId, dashboardResponse.getBeautyClubResponse().getCampaignId());
            });

            Then("I see my Beauty Club  {string}", (String webDescription) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
        //        Assert.assertEquals(webDescription, dashboardResponse.getBeautyClubResponse().getWebDescription());
            });

            Then("I see my Beauty Club  Campaign End Date as {string}", (String campaignEndDate) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
        //        Assert.assertEquals(campaignEndDate, dashboardResponse.getBeautyClubResponse().getCampaignEndDate());
            });

            Then("I see my Beauty Club  Threshold Type Code as {string}", (String thresholdTypeCode) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
         //       Assert.assertEquals(thresholdTypeCode, dashboardResponse.getBeautyClubResponse().getThresholdTypeCode());
            });

            Then("I see my Beauty Club  First Threshold as {double}", (Double firstThreshold) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
      //          Assert.assertEquals(firstThreshold, dashboardResponse.getBeautyClubResponse().getFirstThreshold());
            });

            Then("I see my Beauty Club  Reward Amount as {double}", (Double rewardAmount) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
     //           Assert.assertEquals(rewardAmount, dashboardResponse.getBeautyClubResponse().getRewardAmount());
            });

            Then("I see my Beauty Club  Points required to get next coupon as {double}", (Double pointsToNextThreshold) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
       //         Assert.assertEquals(pointsToNextThreshold, dashboardResponse.getBeautyClubResponse().getPointsToNextThreshold());
            });

            Then("I see my Beauty Club  Offer Limit Reached as {string}", (String offerLimitReached) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
      //          Assert.assertFalse(dashboardResponse.getBeautyClubResponse().getOfferLimitReached());

            });

            Then("I see my Beauty Club  Points Progress as {double}", (Double pointsProgress) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
    //            Assert.assertEquals(pointsProgress, dashboardResponse.getBeautyClubResponse().getPointsProgress());
            });
        }
    }
}