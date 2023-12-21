package com.cvs.crm.cukes.beautyClubTransactionHistory.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.RewardTransactionRequest;
import com.cvs.crm.model.response.*;
import com.cvs.crm.service.BeautyClubTransactionHistoryService;
import com.cvs.crm.util.DateUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Slf4j
public class BeautyClubTransactionHistoryStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    BeautyClubTransactionHistoryService beautyClubTransactionHistoryService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    public BeautyClubTransactionHistoryStepDefinitions() {
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

            When("I view Beauty Club Transaction History Details in DashBoard for my card", () -> {
                beautyClubTransactionHistoryService.viewBeautyClubTransactionHistory(request);
            });

            Then("I see the Beauty Club Transaction History", () -> {
                beautyClubTransactionHistoryService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I do not see the Beauty Club Transaction History", () -> {
                beautyClubTransactionHistoryService.getServiceResponse().then().log().all().statusCode(400);
            });


            Then("I see my Beauty Club Transaction History transaction Date as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String expectDt = String.valueOf(list.get(i).get("transaction_Date"));
                    Integer expectTransDt = Integer.valueOf(expectDt.replace("currentdate-", ""));
                    String expectedTransactionDate = dateUtil.dealEndDateMinus(expectTransDt);
                    BeautyClubTransHistResponse beautyClubTransHistResponse = beautyClubTransactionHistoryService.getServiceResponse().as(BeautyClubTransHistResponse.class);
                    String actualTransDate = beautyClubTransHistResponse.getBeautyClubTransResponseList().get(i).getTransactionDate();
                    Assert.assertEquals(expectedTransactionDate, actualTransDate);
                }
            });

            Then("I see my Beauty Club Transaction History extraBucks Earned as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    Double expectedExtraBucksAmount = Double.valueOf(list.get(i).get("extraBucks_Earned"));
                    BeautyClubTransHistResponse beautyClubTransHistResponse = beautyClubTransactionHistoryService.getServiceResponse().as(BeautyClubTransHistResponse.class);
                    Double actualEarnedXtraBucksAmount = beautyClubTransHistResponse.getBeautyClubTransResponseList().get(i).getExtraBucksEarned();
                    Assert.assertEquals(expectedExtraBucksAmount, actualEarnedXtraBucksAmount);
                    break;
                }
            });

            Then("I see my Beauty Club Transaction History transaction Amount as", (DataTable dt) -> {
                List<Double> expectedlist = dt.asList(Double.class);
                List<Double> tansAmountList = null;
                BeautyClubTransHistResponse beautyClubTransHistResponse = beautyClubTransactionHistoryService.getServiceResponse().as(BeautyClubTransHistResponse.class);
                List<BeautyClubTransResponse> beautyClubTrans = beautyClubTransHistResponse.getBeautyClubTransResponseList();
                for (int i = 0; i < beautyClubTrans.size(); i++) {
                    tansAmountList = beautyClubTrans.get(0).getTransactionAmount();
                    Assert.assertEquals(expectedlist, tansAmountList);

                }
            });


            Then("I see my Beauty Club Transaction History Details as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                List<Double> actualTransAmount = null;
                String actualTransDate = null;
                Double actualEarnedAmount = null;
                Double expectedTransAmount = null;
                String expectedTransDate = null;
                Double expectedEarnedAmount = null;
                Double expectXtraBucks = null;
                Double actualTransAmt = 0.0;
                for (i = 0; i < list.size(); i++) {
                    String expectDt = String.valueOf(list.get(i).get("transaction_Date"));
                    Integer expectedDt = Integer.valueOf(expectDt.replace("currentdate-", ""));
                    expectedTransDate = dateUtil.dealEndDateMinus(expectedDt);
                    expectXtraBucks = Double.valueOf(list.get(i).get("extraBucks_Earned"));
                    if (expectXtraBucks == 0) {
                        expectedEarnedAmount = null;
                    } else {
                        expectedEarnedAmount = expectXtraBucks;
                    }
                    expectedTransAmount = Double.valueOf(list.get(i).get("transaction_Amount"));

                    BeautyClubTransHistResponse beautyClubTransHistResponse = beautyClubTransactionHistoryService.getServiceResponse().as(BeautyClubTransHistResponse.class);
                    List<BeautyClubTransResponse> beautyClubTrans = beautyClubTransHistResponse.getBeautyClubTransResponseList();

                    actualTransDate = beautyClubTrans.get(i).getTransactionDate();
                    actualTransAmount = beautyClubTrans.get(i).getTransactionAmount();
                    actualEarnedAmount = beautyClubTrans.get(i).getExtraBucksEarned();
                    for (int j = 0; j < actualTransAmount.size(); j++) {
                        actualTransAmt = actualTransAmount.get(j);
                    }
                    Assert.assertEquals(expectedEarnedAmount, actualEarnedAmount);
                    Assert.assertEquals(expectedTransDate, actualTransDate);
                    Assert.assertEquals(expectedTransAmount, actualTransAmt);
                }
            });

            Then("I see my Error Code as {int}", (Integer errorCd) -> {

                ApiErrorResponse apiErrorResponse = beautyClubTransactionHistoryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });

            Then("I see Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = beautyClubTransactionHistoryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });


            Then("I see my Beauty Club Transaction History Info as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                List<Double> actualTransAmount = null;
                List<Double> amtlist = null;
                String actualTransDate = null;
                Double actualEarnedAmount = null;
                double[] expectedTransAmount = null;
                String expectedTransDate = null;
                Double expectedEarnedAmount = null;
                Double expectXtraBucks = null;
                Double actualTransAmt = 0.0;
                Double expectTransAmt = 0.0;
                for (i = 0; i < list.size(); i++) {
                    String expectDt = String.valueOf(list.get(i).get("transaction_Date"));
                    Integer expectedDt = Integer.valueOf(expectDt.replace("currentdate-", ""));
                    expectedTransDate = dateUtil.dealEndDateMinus(expectedDt);
                    expectXtraBucks = Double.valueOf(list.get(i).get("extraBucks_Earned"));
                    if (expectXtraBucks == 0) {
                        expectedEarnedAmount = null;
                    } else {
                        expectedEarnedAmount = expectXtraBucks;
                    }
                    expectedTransAmount = (Arrays.stream(list.get(i).get("transaction_Amount").split(",")).mapToDouble(Double::parseDouble).toArray());
                    amtlist = DoubleStream.of(expectedTransAmount).boxed().collect(Collectors.toList());
                    BeautyClubTransHistResponse beautyClubTransHistResponse = beautyClubTransactionHistoryService.getServiceResponse().as(BeautyClubTransHistResponse.class);
                    List<BeautyClubTransResponse> beautyClubTrans = beautyClubTransHistResponse.getBeautyClubTransResponseList();
                    actualTransDate = beautyClubTrans.get(i).getTransactionDate();
                    actualEarnedAmount = beautyClubTrans.get(i).getExtraBucksEarned();
                    actualTransAmount = beautyClubTrans.get(i).getTransactionAmount();
                    for (int j = 0; j < actualTransAmount.size(); j++) {
                        actualTransAmt = actualTransAmount.get(j);
                        expectTransAmt = amtlist.get(j);
                        Assert.assertEquals(expectTransAmt, actualTransAmt);
                    }
                    Assert.assertEquals(expectedEarnedAmount, actualEarnedAmount);
                    Assert.assertEquals(expectedTransDate, actualTransDate);
                }
            });
        }
    }
}