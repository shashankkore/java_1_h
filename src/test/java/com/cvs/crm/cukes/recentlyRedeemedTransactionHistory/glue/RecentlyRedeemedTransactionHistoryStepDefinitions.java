package com.cvs.crm.cukes.recentlyRedeemedTransactionHistory.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.RewardTransactionRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.RecentlyRedeemedDealsResponse;
import com.cvs.crm.model.response.RedeemedDealsResponse;
import com.cvs.crm.model.response.CouponInfoResponse;
import com.cvs.crm.service.RecentlyRedeemedTransactionHistoryService;
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
public class RecentlyRedeemedTransactionHistoryStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    RecentlyRedeemedTransactionHistoryService recentlyRedeemedTransactionHistoryService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    public RecentlyRedeemedTransactionHistoryStepDefinitions() {
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

            When("I view Recently Redeemed Transaction History Details in DashBoard for my card", () -> {
                recentlyRedeemedTransactionHistoryService.viewRecentlyRedeemedTransactionHistory(request);
            });

            Then("I see the Recently Redeemed Transaction History", () -> {
                recentlyRedeemedTransactionHistoryService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I do not see the Recently Redeemed Transaction History", () -> {
                recentlyRedeemedTransactionHistoryService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });

            Then("I see my Recently Redeemed Transaction History redeem Date as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);

                for (int i = 0; i < list.size(); i++) {
                    Integer expectRedeemDt = Integer.valueOf(list.get(i).get("redeem_date"));
                    String expectedRedeemDate = dateUtil.dealEndDateMinus(expectRedeemDt);
                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
                    String actualRedeemDate = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList().get(i).getRedeemDate();
                    Assert.assertEquals(expectedRedeemDate, actualRedeemDate);
                }
            });

//            Then("I see my Recently Redeemed Transaction History  coupon Info as", (DataTable dt) -> {
//                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
//                int i = 0;
//                int j = 0;
//                String actualCouponHeader = null;
//                String actualCouponDesc = null;
//                String expectedCouponHeader = null;
//                String expectedCouponDesc = null;
//                for (i = 0; i < list.size(); i++) {
//                    expectedCouponHeader = list.get(i).get("coupon_header");
//                    expectedCouponDesc = list.get(i).get("coupon_description");
//                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
//                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
//                    for (j = 0; j < redeemedDealsList.size(); j++) {
//                        actualCouponHeader = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(i).getCouponHeader();
//                        actualCouponDesc = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(i).getCouponDescription();
//                    }
//                    Assert.assertEquals(expectedCouponDesc, actualCouponDesc);
//                    Assert.assertEquals(expectedCouponHeader, actualCouponHeader);
//                }
//            });

            
            Then("I see my Recently Redeemed Transaction History  coupon Info as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                int j = 0;
                String actualCouponHeader = null;
                String actualCouponDesc = null;
                String expectedCouponHeader = null;
                String expectedCouponDesc = null;
                for (i = 0; i < list.size(); i++) {
                    expectedCouponHeader = list.get(i).get("coupon_header");
                    expectedCouponDesc = list.get(i).get("coupon_description");
                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
                    for (j = 0; j < redeemedDealsList.size(); j++) {
                        actualCouponHeader = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(j).getCouponReceiptText();
//                        System.out.println("for instance i = "+i +" j = "+j+"value = " +actualCouponHeader);
                        actualCouponDesc = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(j).getCouponDescription();
//                        System.out.println("for instance i = "+i +" j = "+j+"value = " +actualCouponDesc);
                    }
                    Assert.assertEquals(expectedCouponDesc, actualCouponDesc);
                    Assert.assertEquals(expectedCouponHeader, actualCouponHeader);
                }
            });


//            Then("I see my Recently Redeemed Transaction History  saved Amount as", (DataTable dt) -> {
//                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
//                for (int i = 0; i < list.size(); i++) {
//                    Double expectedSavedAmount = Double.valueOf(list.get(i).get("saved_amount"));
//                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
//                    Double actualSavedAmount = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList().get(i).getRedeemEvents().get(i).getSavedAmount();
//                    Assert.assertEquals(expectedSavedAmount, actualSavedAmount);
//                }
//            });
            
            Then("I see my Recently Redeemed Transaction History  saved Amount as", (DataTable dt) -> {
            	List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                int j = 0;
                Double expectedSavedAmount = null;
                Double actualSavedAmount = null;
                for (i = 0; i < list.size(); i++) {
                	expectedSavedAmount = Double.valueOf(list.get(i).get("saved_amount"));
                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
                    for (j = 0; j < redeemedDealsList.size(); j++) {
                    	actualSavedAmount = redeemedDealsList.get(j).getRedeemEvents().get(i).getSavedAmount();
//                        System.out.println("for instance i = "+i +" j = "+j+"value = " +actualSavedAmount);
                    }
                    Assert.assertEquals(expectedSavedAmount, actualSavedAmount);
                }
            });

//            Then("I see my Recently Redeemed Transaction History details as", (DataTable dt) -> {
//                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
//                int i = 0;
//                int j = 0;
//                String actualCouponHeader = null;
//                String actualCouponDesc = null;
//                String actualRedeemDate = null;
//                Double actualSavedAmount = null;
//                String expectedRedeemDate = null;
//                String expectedCouponHeader = null;
//                String expectedCouponDesc = null;
//                Double expectedSavedAmount = null;
//                for (i = 0; i < list.size(); i++) {
//                    Integer expectRedeemDt = Integer.valueOf(list.get(i).get("redeem_date"));
//                    expectedRedeemDate = dateUtil.dealEndDateMinus(expectRedeemDt);
//                    expectedCouponHeader = list.get(i).get("coupon_header");
//                    expectedCouponDesc = list.get(i).get("coupon_description");
//                    expectedSavedAmount = Double.valueOf(list.get(i).get("saved_amount"));
//                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
//                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
//                    actualRedeemDate = redeemedDealsList.get(i).getRedeemDate();
////                    actualSavedAmount = redeemedDealsList.get(i).getSavedAmount();
//                    actualSavedAmount = redeemedDealsList.get(i).getRedeemEvents().get(i).getSavedAmount();
//                    actualCouponHeader = redeemedDealsList.get(i).getRedeemEvents().get(i).getCouponInfo().get(0).getCouponReceiptText();
//                    actualCouponDesc = redeemedDealsList.get(i).getRedeemEvents().get(i).getCouponInfo().get(0).getCouponDescription();
//
//                    Assert.assertEquals(expectedRedeemDate, actualRedeemDate);
//                    Assert.assertEquals(expectedCouponHeader, actualCouponHeader);
//                    Assert.assertEquals(expectedCouponDesc, actualCouponDesc);
//                    Assert.assertEquals(expectedSavedAmount, actualSavedAmount);
//                }
//            });
            
            Then("I see my Recently Redeemed Transaction History details as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                int j = 0;
                String actualCouponHeader = null;
                String actualCouponDesc = null;
                String actualRedeemDate = null;
                Double actualSavedAmount = null;
                String expectedRedeemDate = null;
                String expectedCouponHeader = null;
                String expectedCouponDesc = null;
                Double expectedSavedAmount = null;
                for (i = 0; i < list.size(); i++) {
                    Integer expectRedeemDt = Integer.valueOf(list.get(i).get("redeem_date"));
                    expectedRedeemDate = dateUtil.dealEndDateMinus(expectRedeemDt);
                    expectedCouponHeader = list.get(i).get("coupon_header");
                    expectedCouponDesc = list.get(i).get("coupon_description");
                    expectedSavedAmount = Double.valueOf(list.get(i).get("saved_amount"));
                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
                    actualRedeemDate = redeemedDealsList.get(i).getRedeemDate();
//                    actualSavedAmount = redeemedDealsList.get(i).getSavedAmount();
//                    for (j = 0; j < redeemedDealsList.size(); j++) {
                        actualCouponHeader = redeemedDealsList.get(i).getRedeemEvents().get(0).getCouponInfo().get(0).getCouponReceiptText();
//                        System.out.println("for instance CH " +actualCouponHeader);
                        actualCouponDesc = redeemedDealsList.get(i).getRedeemEvents().get(0).getCouponInfo().get(0).getCouponDescription();
//                        System.out.println("for instance CD " +actualCouponDesc);
                        actualSavedAmount = redeemedDealsList.get(i).getRedeemEvents().get(0).getSavedAmount();
//                        System.out.println("for instance AS " +actualSavedAmount);
//                    }
//                    System.out.println("redeem list size is " +redeemedDealsList.get(i).getRedeemEvents().size());

                    Assert.assertEquals(expectedRedeemDate, actualRedeemDate);
                    Assert.assertEquals(expectedCouponHeader, actualCouponHeader);
                    Assert.assertEquals(expectedCouponDesc, actualCouponDesc);
                    Assert.assertEquals(expectedSavedAmount, actualSavedAmount);
                }
            });

            Then("I see my Error Code as {int}", (Integer errorCd) -> {

                ApiErrorResponse apiErrorResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });


//            Then("I see my Recently Redeemed Transaction History  coupon Info with offset 0 as", (DataTable dt) -> {
//                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
//                int i = 0;
//                int j = 0;
//                String actualCouponHeader = null;
//                String actualCouponDesc = null;
//                String expectedCouponHeader = null;
//                String expectedCouponDesc = null;
//                for (i = 0; i < list.size(); i++) {
//                    expectedCouponHeader = list.get(i).get("coupon_header");
//                    expectedCouponDesc = list.get(i).get("coupon_description");
//                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
//                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
//                    for (j = 0; j < redeemedDealsList.size(); j++) {
//                        actualCouponHeader = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(i).getCouponReceiptText();
//                        actualCouponDesc = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(i).getCouponDescription();
//                    }
//                    Assert.assertEquals(expectedCouponDesc, actualCouponDesc);
//                    Assert.assertEquals(expectedCouponHeader, actualCouponHeader);
//                }
//            });
            
            Then("I see my Recently Redeemed Transaction History  coupon Info with offset 0 as", (DataTable dt) -> {
            	List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                int j = 0;
                String actualCouponHeader = null;
                String actualCouponDesc = null;
                String expectedCouponHeader = null;
                String expectedCouponDesc = null;
                for (i = 0; i < list.size(); i++) {
                    expectedCouponHeader = list.get(i).get("coupon_header");
                    expectedCouponDesc = list.get(i).get("coupon_description");
                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
                    for (j = 0; j < redeemedDealsList.size(); j++) {
                        actualCouponHeader = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(j).getCouponReceiptText();
//                        System.out.println("for instance i = "+i +" j = "+j+"value = " +actualCouponHeader);
                        actualCouponDesc = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(j).getCouponDescription();
//                        System.out.println("for instance i = "+i +" j = "+j+"value = " +actualCouponDesc);
                    }
                    Assert.assertEquals(expectedCouponDesc, actualCouponDesc);
                    Assert.assertEquals(expectedCouponHeader, actualCouponHeader);
                }
            });

            Then("I see my Recently Redeemed Transaction History  coupon Info with offset 5 as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                int j = 0;
                String actualCouponHeader = null;
                String actualCouponDesc = null;
                String expectedCouponHeader = null;
                String expectedCouponDesc = null;
                for (i = 0; i < list.size(); i++) {
                    expectedCouponHeader = list.get(i).get("coupon_header");
                    expectedCouponDesc = list.get(i).get("coupon_description");
                    RecentlyRedeemedDealsResponse recentlyRedeemedDealsResponse = recentlyRedeemedTransactionHistoryService.getServiceResponse().as(RecentlyRedeemedDealsResponse.class);
                    List<RedeemedDealsResponse> redeemedDealsList = recentlyRedeemedDealsResponse.getRedeemedDealsResponseList();
                    for (j = 0; j < redeemedDealsList.size(); j++) {
                        actualCouponHeader = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(i).getCouponReceiptText();
                        actualCouponDesc = redeemedDealsList.get(j).getRedeemEvents().get(i).getCouponInfo().get(i).getCouponDescription();
                    }
                    Assert.assertEquals(expectedCouponDesc, actualCouponDesc);
                    Assert.assertEquals(expectedCouponHeader, actualCouponHeader);
                }
            });
        }
    }
}