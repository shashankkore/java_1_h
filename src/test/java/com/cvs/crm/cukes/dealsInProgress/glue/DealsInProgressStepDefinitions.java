package com.cvs.crm.cukes.dealsInProgress.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.model.response.DealsInProgressResponse;
import com.cvs.crm.service.DealsInProgressService;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CampaignEarnServiceUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Slf4j
public class DealsInProgressStepDefinitions extends SpringIntegrationTests implements En {

//	@JsonIgnoreProperties(ignoreUnknown = true)
	
    @Autowired
    DealsInProgressService dealsInProgressService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;
    
    @Autowired
    CampaignEarnServiceUtil campaignEarnServiceUtil;
    
    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    public DealsInProgressStepDefinitions() {
    	
    	

        DashBoardRequest request = new DashBoardRequest();

        Given("{string}", (String scenario) -> {
        });

        Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
            request.setSearchCardType("0002");
            request.setSearchCardNbr(xtraCardNbr);
        });
        
        Given("I hit campaign earn service for zero state", () -> {
        	System.out.println("Campaign Service from steps");
        	campaignEarnServiceUtil.hitCampaignEarn(98158319, 0, 0, 200926);
        	cacheRefreshUtil.refresCacheusingXtraParms();
            cacheRefreshUtil.refresCacheforCmpgnDefns();
        });
        
//        Given("I hit campaign earn service for zero state for card {int}", (Integer xtraCardNbr, Integer qty, Integer amt, Integer sku) -> {
//        	System.out.println("Campaign Service from steps");
//        	campaignEarnServiceUtil.hitCampaignEarn(xtraCardNbr, qty, amt, sku);
//        	cacheRefreshUtil.refresCacheusingXtraParms();
//            cacheRefreshUtil.refresCacheforCmpgnDefns();
//        });
        
        Given("I hit campaign earn service for card {int} for quantity {int} for amount {int} and sku {int}", (Integer xtraCardNbr, Integer qty, Integer amt, Integer sku) -> {
        	System.out.println("Campaign Service from steps");
        	campaignEarnServiceUtil.hitCampaignEarn(xtraCardNbr, qty, amt, sku);
        	cacheRefreshUtil.refresCacheusingXtraParms();
            cacheRefreshUtil.refresCacheforCmpgnDefns();
        });

        When("I view Deals In Progress Details in DashBoard for my card", () -> {
            dealsInProgressService.viewDealsInProgress(request);
        });

        Then("I do not see the DIP Rewards", () -> {
            dealsInProgressService.getServiceResponse().then().log().all().statusCode(200);
        });

        Then("I see the DIP Rewards", () -> {
            dealsInProgressService.getServiceResponse().then().log().all().statusCode(200);
        });

        Then("I see my Deals In Progress as", (DataTable dt) -> {
//        	final ObjectMapper objectMapper = 
//        		    new ObjectMapper()
//        		        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Map<String, String>> list = dt.asMaps(String.class, String.class);
            for (int i = 0; i < list.size(); i++) {
                Integer expectedCampaignId = Integer.valueOf(list.get(i).get("campaign_id"));
                String expectedWebDescription = list.get(i).get("web_description");
                Integer expectEndDt = Integer.valueOf(list.get(i).get("campaign_end_date"));
                String expectedCampaignEndDate = dateUtil.dealEndDate(expectEndDt);
                String expectedThresholdTypeCode = list.get(i).get("threshold_type_code");
                Double expectedFirstThreshold = Double.valueOf(list.get(i).get("first_threshold"));
                Double expectedRewardAmount = Double.valueOf(list.get(i).get("reward_amount"));
                Double expectedPointsToNextThreshold = Double.valueOf(list.get(i).get("points_to_next_threshold"));
                Boolean expectedOfferLimitReached = Boolean.valueOf(list.get(i).get("offer_limit_reached"));
                String expectedCampaignTypeCode = list.get(i).get("campaign_type_code");
                String expectedCampaignSubtypeCode = list.get(i).get("campaign_subtype_code");
                String expectedPointsQuantity = list.get(i).get("points_quantity");
                
//                DashboardResponse dashboardResponse = dealsInProgressService.getServiceResponse().as(DashboardResponse.class);
//                Integer actualCampaignId = dashboardResponse.getDealsInProgressResponseList().get(i).getCampaignId();
//                String actualWebDescription = dashboardResponse.getDealsInProgressResponseList().get(i).getWebDescription();
//                String actualCampaignEndDate = dashboardResponse.getDealsInProgressResponseList().get(i).getCampaignEndDate();
//                String actualThresholdTypeCode = dashboardResponse.getDealsInProgressResponseList().get(i).getThresholdTypeCode();
//                Double actualFirstThreshold = dashboardResponse.getDealsInProgressResponseList().get(i).getFirstThreshold();
//                Double actualRewardAmount = dashboardResponse.getDealsInProgressResponseList().get(i).getRewardAmount();
//                Double actualPointsToNextThreshold = dashboardResponse.getDealsInProgressResponseList().get(i).getPointsToNextThreshold();
//                Boolean actualOfferLimitReached = dashboardResponse.getDealsInProgressResponseList().get(i).getOfferLimitReached();
//                String actualCampaignTypeCode = dashboardResponse.getDealsInProgressResponseList().get(i).getCampaignTypeCode();
//                String actualCampaignSubtypeCode = dashboardResponse.getDealsInProgressResponseList().get(i).getCampaignSubtypeCode();
//                String actualPointsQuantity = dashboardResponse.getDealsInProgressResponseList().get(i).getPointsQuantity();
                
//                #And I see my Deals In Progress as
//                #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
//                #| 41624       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 30.0                     | "false"             | E                  | T                     | 0.0             | "WEB"   |

            	Integer actualCampaignId = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].campaignId");
            	String actualThresholdTypeCode = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].thresholdTypeCode");
            	Double actualFirstThreshold = Double.valueOf(dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].firstThreshold").toString());
//            	Double actualRewardAmount = Double.valueOf(dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].rewardAmount").toString());
            	Double actualPointsToNextThreshold = Double.valueOf(dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].pointsToNextThreshold").toString());
            	Boolean actualOfferLimitReached = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].offerLimitReached");
            	String actualCampaignTypeCode = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].campaignTypeCode");
            	String actualCampaignSubtypeCode = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].campaignSubtypeCode");
            	String actualPointsQuantity = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].pointsQuantity").toString();
            	
            	System.out.println("End date is: "+expectedCampaignEndDate);
                Assert.assertEquals(expectedCampaignId, actualCampaignId);
//                Assert.assertEquals(expectedWebDescription, actualWebDescription);
//                Assert.assertEquals(expectedCampaignEndDate, actualCampaignEndDate);
                Assert.assertEquals(expectedThresholdTypeCode, actualThresholdTypeCode);
                Assert.assertEquals(expectedFirstThreshold, actualFirstThreshold);
//                Assert.assertEquals(expectedRewardAmount, actualRewardAmount);
                Assert.assertEquals(expectedPointsToNextThreshold, actualPointsToNextThreshold);
                Assert.assertEquals(expectedOfferLimitReached, actualOfferLimitReached);
                Assert.assertEquals(expectedCampaignTypeCode, actualCampaignTypeCode);
                Assert.assertEquals(expectedCampaignSubtypeCode, actualCampaignSubtypeCode);
                Assert.assertEquals(expectedPointsQuantity, actualPointsQuantity);
            }
        });

        Then("I see my Deals In Progress NewDeal Indicator as true", (DataTable dt) -> {
            List<Map<String, String>> list = dt.asMaps(String.class, String.class);
            for (int i = 0; i < list.size(); i++) {
                Boolean expectedNewDeal = Boolean.valueOf(list.get(i).get("new_deal"));
//                DashboardResponse dashboardResponse = dealsInProgressService.getServiceResponse().as(DashboardResponse.class);
//                Boolean actualNewDeal = dashboardResponse.getDealsInProgressResponseList().get(i).getNewDeal();
                //  Assert.assertTrue(actualNewDeal);
                Boolean actualNewDeal = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].newDeal");
                Assert.assertEquals(expectedNewDeal, actualNewDeal);
            }
        });

        Then("I see my Deals In Progress Deal Ending Indicator as true", (DataTable dt) -> {
            List<Map<String, String>> list = dt.asMaps(String.class, String.class);
            for (int i = 0; i < list.size(); i++) {
                String expectedDealEndingSoon = list.get(i).get("deal_ending_soon");
//                DashboardResponse dashboardResponse = dealsInProgressService.getServiceResponse().as(DashboardResponse.class);
//                Boolean actualDealEndingSoon = dashboardResponse.getDealsInProgressResponseList().get(i).getDealEndingSoon();
                Boolean actualDealEndingSoon = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].dealEndingSoon");
                Assert.assertTrue(actualDealEndingSoon);

            }
        });

        Then("I see my Deals In Progress NewDeal Indicator as false", (DataTable dt) -> {
            List<Map<String, String>> list = dt.asMaps(String.class, String.class);
            for (int i = 0; i < list.size(); i++) {
                Boolean expectedNewDeal = Boolean.valueOf(list.get(i).get("new_deal"));
//                DashboardResponse dashboardResponse = dealsInProgressService.getServiceResponse().as(DashboardResponse.class);
//                Boolean actualNewDeal = dashboardResponse.getDealsInProgressResponseList().get(i).getNewDeal();
                Boolean actualNewDeal = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].newDeal");
                Assert.assertFalse(actualNewDeal);
            }
        });

        Then("I see my Deals In Progress Deal Ending Indicator as false", (DataTable dt) -> {
            List<Map<String, String>> list = dt.asMaps(String.class, String.class);
            for (int i = 0; i < list.size(); i++) {
                String expectedDealEndingSoon = list.get(i).get("deal_ending_soon");
//                DashboardResponse dashboardResponse = dealsInProgressService.getServiceResponse().as(DashboardResponse.class);
//                Boolean actualDealEndingSoon = dashboardResponse.getDealsInProgressResponseList().get(i).getDealEndingSoon();
                Boolean actualDealEndingSoon = dealsInProgressService.getServiceResponse().jsonPath().get("dealsInProgress["+i+"].dealEndingSoon");
                Assert.assertFalse(actualDealEndingSoon);
            }
        });

    }
}