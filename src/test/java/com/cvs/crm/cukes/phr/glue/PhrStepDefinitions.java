package com.cvs.crm.cukes.phr.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.service.PharmacyHealthRewardsService;
import com.cvs.crm.util.DateUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class PhrStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    PharmacyHealthRewardsService pharmacyHealthRewardsService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    public PhrStepDefinitions() {
        {
            DashBoardRequest request = new DashBoardRequest();

            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });

            When("I view PHR Summary in DashBoard for my card", () -> {
                pharmacyHealthRewardsService.viewPharmacyHealthRewards(request);
            });

            Then("I see the Pharmacy Health Rewards", () -> {
                pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I see my PHR enrollment status as {string}", (String enrolled) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertTrue(dashboardResponse.getPharmacyHealthRewardsResponse().getEnrolled());

            });

            Then("I see my PHR enrollment as {string}", (String enrolled) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getEnrolled());
            });

            Then("I see my PHR Campaign Id as {int}", (Integer campaignId) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(campaignId, dashboardResponse.getPharmacyHealthRewardsResponse().getCampaignId());
            });

            Then("I see my PHR {string}", (String webDescription) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(webDescription, dashboardResponse.getPharmacyHealthRewardsResponse().getWebDescription());
            });

            Then("I see my PHR Campaign End Date as {string}", (String campaignEndDate) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(campaignEndDate, dashboardResponse.getPharmacyHealthRewardsResponse().getCampaignEndDate());
            });

            Then("I see my PHR Threshold Type Code as {string}", (String thresholdTypeCode) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(thresholdTypeCode, dashboardResponse.getPharmacyHealthRewardsResponse().getThresholdTypeCode());
            });

            Then("I see my PHR First Threshold as {double}", (Double firstThreshold) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(firstThreshold, dashboardResponse.getPharmacyHealthRewardsResponse().getFirstThreshold());
            });

            Then("I see my PHR Reward Amount as {double}", (Double rewardAmount) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(rewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getRewardAmount());
            });

            Then("I see my PHR Points required to get next coupon as {double}", (Double pointsToNextThreshold) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(pointsToNextThreshold, dashboardResponse.getPharmacyHealthRewardsResponse().getPointsToNextThreshold());
            });


            Then("I see my PHR Offer Limit Reached as {string}", (String offerLimitReached) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getOfferLimitReached());

            });

            Then("I see my PHR Points Progress as {double}", (Double pointsProgress) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(pointsProgress, dashboardResponse.getPharmacyHealthRewardsResponse().getPointsProgress());
            });

            Then("I do not see the Pharmacy Health Rewards", () -> {
                pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(404);
            });


            Then("I see under my PHR who all enrolled as John Doe, Mary Krisher, Donna Furter and Tim Richards", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String fName = list.get(i).get("firstName");
                    //	String lName = list.get(i).get("lastName");
                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    Assert.assertEquals(fName, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getFirstName());
                    //        Assert.assertEquals(lName,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getLastName());
                }
            });

            Then("I see under my PHR who all enrolled as John Doe and Mary Krisher", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String fName = list.get(i).get("firstName");
                    //	String lName = list.get(i).get("lastName");
                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    Assert.assertEquals(fName, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getFirstName());
                    //        Assert.assertEquals(lName,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getLastName());
                }
            });


            Then("I see under my PHR who all enrolled true as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String capped = list.get(i).get("capped");
                    //String cappedDate = list.get(i).get("cappedDate");
                    Integer cappedDate = Integer.valueOf(list.get(i).get("cappedDate"));
                    String expectedCappedDate = dateUtil.dealEndDate(cappedDate);
                    Double maxCredits = Double.valueOf(list.get(i).get("maxCredits"));
                    Double maxRewardAmount = Double.valueOf(list.get(i).get("maxRewardAmount"));
                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    Assert.assertEquals(expectedCappedDate, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCappedDate());
                    Assert.assertTrue(dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCapped());
                    Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxCredits());
                    Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxRewardAmount());
                }
            });


            Then("I see under my PHR who all enrolled as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String capped = list.get(i).get("capped");
                    Double maxCredits = Double.valueOf(list.get(i).get("maxCredits"));
                    Double maxRewardAmount = Double.valueOf(list.get(i).get("maxRewardAmount"));

                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    // Assert.assertEquals(capped,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCapped());
                    Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCapped());
                    Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxCredits());
                    Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxRewardAmount());
                }
            });

            Then("I see under my PHR who all enrolled as: {string}", (String flName) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(flName, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getFirstName());
                //Assert.assertEquals(flName,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getMaxCredits());

            });

            Then("I see under my PHR who all enrolled as:", () -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                int members = dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().size();
                Assert.assertEquals(0, members);

            });

            Then("I see PHR Error Code as {int}", (Integer errorCd) -> {
                ApiErrorResponse apiErrorResponse = pharmacyHealthRewardsService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });

            Then("I see PHR Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = pharmacyHealthRewardsService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });

            Then("I see my PHR maxCredits as {double}", (Double maxCredits) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMaxCredits());
            });


            Then("I see my PHR maxRewardAmount as {double}", (Double maxRewardAmount) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMaxRewardAmount());
            });


            Then("I see my PHR capped as {string}", (String capped) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getCapped());

            });

            Then("I see my PHR capped status as {string}", (String capped) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertTrue(dashboardResponse.getPharmacyHealthRewardsResponse().getCapped());

            });


            Then("I see my PHR year To Date Earned as {double}", (Double yearToDateEarned) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(yearToDateEarned, dashboardResponse.getPharmacyHealthRewardsResponse().getYearToDateEarned());
            });


            Then("I see my PHR year To Date Credits as {double}", (Double yearToDateCredits) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(yearToDateCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getYearToDateCredits());
            });


            Then("I see my Member maxCredits as {double}", (Double maxCredits) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getMaxCredits());

            });

            Then("I see my Member maxRewardAmount as {double}", (Double maxRewardAmount) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getMaxRewardAmount());

            });

            Then("I see my Member capped as {string}", (String capped) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getCapped());

            });


        }
    }
}
