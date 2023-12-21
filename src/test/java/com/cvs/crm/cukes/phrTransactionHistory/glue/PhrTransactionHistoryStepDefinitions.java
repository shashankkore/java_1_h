package com.cvs.crm.cukes.phrTransactionHistory.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.RewardTransactionRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.PharmacyHealthRewardsTransactionsResponse;
import com.cvs.crm.model.response.PharmacyHealthRewardsDealsResponse;
import com.cvs.crm.model.response.CreditsSummaryResponse;
import com.cvs.crm.service.PhrTransactionHistoryService;
import com.cvs.crm.util.DateUtil;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class PhrTransactionHistoryStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    PhrTransactionHistoryService phrTransactionHistoryService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    public PhrTransactionHistoryStepDefinitions() {
        {
            RewardTransactionRequest request = new RewardTransactionRequest();
            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });

            Given("I use {string}", (String channel) -> {
                request.setChannel(channel);
            });

            Given("I have offset and limit as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                Integer expectOffset = Integer.valueOf(list.get(0).get("offset"));
                Integer expectLimit = Integer.valueOf(list.get(0).get("limit"));
                request.setOffset(expectOffset);
                request.setLimit(expectLimit);
            });

            When("I view PHR Transaction History Details in DashBoard for my card", () -> {
                phrTransactionHistoryService.viewPhrTransactionHistory(request);
            });

            Then("I see the Pharmacy Health Rewards Transaction History", () -> {
                phrTransactionHistoryService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I do not see the PHR Transaction History", () -> {
                phrTransactionHistoryService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = phrTransactionHistoryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });

            Then("I see my Pharmacy Health Rewards Transaction History Transaction Date as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String expectDt = String.valueOf(list.get(i).get("transaction_Date"));
                    Integer expectTransDt = Integer.valueOf(expectDt.replace("currentdate-", ""));
                    String expectedTransDate = dateUtil.dealEndDateMinus(expectTransDt);
                    PharmacyHealthRewardsTransactionsResponse pharmacyHealthRewardsTransactionsResponse = phrTransactionHistoryService.getServiceResponse().as(PharmacyHealthRewardsTransactionsResponse.class);
                    String actualTransDate = pharmacyHealthRewardsTransactionsResponse.getPharmacyHealthRewardsDealsResponseList().get(i).getTransactionDate();
                    Assert.assertEquals(expectedTransDate, actualTransDate);
                }
            });

            Then("I see my Pharmacy Health Rewards Transaction History creditsSummary as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                int j = 0;
                Double actualCreditsEarned = null;
                String actualEventType = null;
                String actualFirstName = null;
                String expectedFirstName = null;
                Double expectedCreditsEarned = null;
                String expectedEventType = null;
                for (i = 0; i < list.size(); i++) {
                    expectedCreditsEarned = Double.valueOf(list.get(i).get("credits_Earned"));
                    expectedEventType = list.get(i).get("event_Type");
                    expectedFirstName = list.get(i).get("first_Name");
                    PharmacyHealthRewardsTransactionsResponse pharmacyHealthRewardsTransactionsResponse = phrTransactionHistoryService.getServiceResponse().as(PharmacyHealthRewardsTransactionsResponse.class);
                    List<PharmacyHealthRewardsDealsResponse> pharmacyHealthRewardsDealsResponseList = pharmacyHealthRewardsTransactionsResponse.getPharmacyHealthRewardsDealsResponseList();
                    for (j = 0; j < pharmacyHealthRewardsDealsResponseList.size(); j++) {
                        actualCreditsEarned = Double.valueOf(pharmacyHealthRewardsDealsResponseList.get(j).getCreditsSummary().get(i).getCreditsEarned());
                        actualEventType = pharmacyHealthRewardsDealsResponseList.get(j).getCreditsSummary().get(i).getEventType();
                        actualFirstName = pharmacyHealthRewardsDealsResponseList.get(j).getCreditsSummary().get(i).getFirstName();
                    }

                    Assert.assertEquals(expectedEventType, actualEventType);
                    Assert.assertEquals(expectedFirstName, actualFirstName);
                    Assert.assertEquals(actualCreditsEarned, expectedCreditsEarned);
                }
            });


            Then("I see my Pharmacy Health Rewards Transaction History extraBucksEarned as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    Double expectExtraBucksEarned = Double.valueOf(list.get(i).get("extraBucks_Earned"));
                    Double expectedExtraBucksEarned = null;
                    if (expectExtraBucksEarned == 0) {
                        expectedExtraBucksEarned = null;
                    } else {
                        expectedExtraBucksEarned = expectExtraBucksEarned;
                    }
                    PharmacyHealthRewardsTransactionsResponse pharmacyHealthRewardsTransactionsResponse = phrTransactionHistoryService.getServiceResponse().as(PharmacyHealthRewardsTransactionsResponse.class);
                    Double actualSavedAmount = pharmacyHealthRewardsTransactionsResponse.getPharmacyHealthRewardsDealsResponseList().get(i).getExtraBucksEarned();
                    Assert.assertEquals(expectedExtraBucksEarned, actualSavedAmount);
                }
            });

            Then("I see my Pharmacy Health Rewards Transaction History Information as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                int j = 0;
                Double actualCreditsEarned = null;
                String actualEventType = null;
                String actualFirstName = null;
                String expectedFirstName = null;
                Double expectedCreditsEarned = null;
                String expectedEventType = null;
                String expectedTransDate = null;
                String actualTransDate = null;
                Double expectedExtraBucksEarned = null;
                Double expectExtraBucksEarned = null;
                Double actualExtraBucksEarned = null;

                for (i = 0; i < list.size(); i++) {
                    String expectDt = String.valueOf(list.get(i).get("transaction_Date"));
                    Integer expectTransDt = Integer.valueOf(expectDt.replace("currentdate-", ""));
                    expectedTransDate = dateUtil.dealEndDateMinus(expectTransDt);
                    expectedCreditsEarned = Double.valueOf(list.get(i).get("creditsEarned"));
                    expectExtraBucksEarned = Double.valueOf(list.get(i).get("extraBucks_Earned"));
                    if (expectExtraBucksEarned == 0) {
                        expectedExtraBucksEarned = null;
                    } else {
                        expectedExtraBucksEarned = expectExtraBucksEarned;
                    }
                    expectedEventType = list.get(i).get("eventType");
                    expectedFirstName = list.get(i).get("firstName");
                    PharmacyHealthRewardsTransactionsResponse pharmacyHealthRewardsTransactionsResponse = phrTransactionHistoryService.getServiceResponse().as(PharmacyHealthRewardsTransactionsResponse.class);
                    List<PharmacyHealthRewardsDealsResponse> pharmacyHealthRewardsDealsResponseList = pharmacyHealthRewardsTransactionsResponse.getPharmacyHealthRewardsDealsResponseList();
                    actualTransDate = pharmacyHealthRewardsDealsResponseList.get(i).getTransactionDate();
                    actualExtraBucksEarned = pharmacyHealthRewardsDealsResponseList.get(i).getExtraBucksEarned();
                    actualCreditsEarned = Double.valueOf(pharmacyHealthRewardsDealsResponseList.get(i).getCreditsSummary().get(0).getCreditsEarned());
                    actualEventType = pharmacyHealthRewardsDealsResponseList.get(i).getCreditsSummary().get(0).getEventType();
                    actualFirstName = pharmacyHealthRewardsDealsResponseList.get(i).getCreditsSummary().get(0).getFirstName();
                    Assert.assertEquals(expectedCreditsEarned, actualCreditsEarned);
                    Assert.assertEquals(expectedFirstName, actualFirstName);
                    Assert.assertEquals(expectedEventType, actualEventType);
                    Assert.assertEquals(expectedExtraBucksEarned, actualExtraBucksEarned);
                    Assert.assertEquals(expectedTransDate, actualTransDate);
                }
            });

            Then("I see my Error Code as {int}", (Integer errorCd) -> {

                ApiErrorResponse apiErrorResponse = phrTransactionHistoryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });
        }
    }
}