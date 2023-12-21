package com.cvs.crm.cukes.getCust.extraBuckRewardsSummary.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.request.CpnsRequest;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.CpnsResponse;
import com.cvs.crm.model.response.GetCustCusInfRespCpnResponse;
import com.cvs.crm.model.response.GetCustCusInfRespCpnsResponse;
import com.cvs.crm.model.response.GetCustCusInfRespMfrCpnAvailPoolResponse;
import com.cvs.crm.model.response.GetCustCusInfRespResponse;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.CpnsService;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.CampaignEarnServiceUtil;
import com.cvs.crm.util.GenerateRandom;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java8.En;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class ExtraBuckRewardsSummaryStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    GetCustService getCustService;
    @Autowired
    CpnsService cpnsService;
    @Autowired
    SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;
    @Autowired
    XtraCardDao xtraCardDao;
    @Autowired
    CampaignDao campaignDao;
    @Autowired
    TestDataUtil testDataUtil;
    @Autowired
    GenerateRandom generateRandom;
    @Autowired
    CampaignEarnServiceUtil campaignEarnServiceUtil;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String requestParam = "";
    Integer xtraCardNbr = null;

    GetCustRequest getCustRequest = new GetCustRequest();
    SetCustRequest setCustRequest = new SetCustRequest();
    CpnsRequest cpnsRequest = new CpnsRequest();
    GetCustResponse getCustResponse;
    CpnsResponse cpnsResponse;
    Integer target_cpn=0;
    Integer index;

    public ExtraBuckRewardsSummaryStepDefinitions() {
        {

            Given("{string}", (String scenario) -> {
            });

            Given("I have my user with xtraCard {int}", (Integer xtraCardNbr) -> {
            	getCustRequest.setSearchCardNbr(xtraCardNbr);
            });
            
            Given("I use xtraCard {string}", (String xtraCard) -> {
                getCustRequest.setSearchCardNbr(Integer.parseInt(xtraCard));
            });

            Given("I use my xtraCard with card type {string} at {string}", (String card_type, String channel) -> {
                getCustRequest.setVersion("1.3");
                getCustRequest.setChannel(channel);
                getCustRequest.setSearchCardType(getCustService.set_typeCd(card_type));
            });
            
            Given("I hit campaign earn1 for card {int}", (Integer CardNbr) -> {
            	System.out.println("Campaign hit earn 1");
            	Integer txnNbr1 = generateRandom.randomNumberString();
                Integer txnInvoiceNbr1 = generateRandom.randomNumberString();
                campaignEarnServiceUtil.hitCampaignEarn(CardNbr, 4, 10, 999975, txnNbr1, txnInvoiceNbr1);
                campaignEarnServiceUtil.getServiceResponse().then().log().all();
            });
            
            Given("I hit campaign earn2 for card {int}", (Integer CardNbr) -> {
            	System.out.println("Campaign hit earn 2");
            	Integer txnNbr2 = generateRandom.randomNumberString();
                Integer txnInvoiceNbr2 = generateRandom.randomNumberString();
                campaignEarnServiceUtil.hitCampaignEarn(CardNbr, 10, 100, 999975, txnNbr2, txnInvoiceNbr2);
                campaignEarnServiceUtil.getServiceResponse().then().log().all();
            });

            Given("I filter with {string} in the request", (String search_term) -> {
                requestParam = search_term;
            });
            
            And("I get a response from getCust API", () -> {
//                getCustService.getCustResponse(getCustRequest, requestParam);
                getCustResponse = getCustService.getCustResponse(getCustRequest, requestParam).as(GetCustResponse.class);
            });
//
            Then("API returns a response with status code as {int}", (Integer statusCd) -> {
                getCustService.getServiceResponse().then().statusCode(statusCd);
            });
            
            Then("ExtraBuckRewardsSummary should be available in the response", () -> {
            	Assert.assertNotNull(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary"));
//            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.xtraCarePrefs.phr.enrolled").equals(getCustStatus));
            	});
            
            Then("Validate ExtraBuckRewardsSummary for no rewards in response", () -> {
            	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()), "0.0");
            	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()), "0.0");
            	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks").toString()), "0.0");
            	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.expiringExtraBuckCpnCount").toString()), "0");
//            	Assert.assertNull(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns"));
            	});
            
            And("Load a coupon", () -> {
            	xtraCardDao.updateXtraCardActiveCouponLoad(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[1].cpnSeqNbr"));
            });
            
            And("Redeem a coupon", () -> {
            	xtraCardDao.updateXtraCardActiveCouponRedeem(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[1].cpnSeqNbr"));
            });
            
            Then("Validate ExtraBuckRewardsSummary for {string} in response", (String noOfCpns) -> {
            	if (noOfCpns.equalsIgnoreCase("noCoupon")) {
            		Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()), "0.0");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()), "0.0");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks").toString()), "0.0");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.expiringExtraBuckCpnCount").toString()), "0");
                } else if (noOfCpns.equalsIgnoreCase("onecoupon")) {
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()), "0.2");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()), "0.2");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks").toString()), "0.0");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.expiringExtraBuckCpnCount").toString()), "0");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[0].cpnSeqNbr").toString()), getCustService.getXtraCardActiveCouponData(getCustRequest.getSearchCardNbr(), "CPN_SEQ_NBR"));
                } else if (noOfCpns.equalsIgnoreCase("twocoupons")) {
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()), "2.2");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()), "2.2");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks").toString()), "0.0");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.expiringExtraBuckCpnCount").toString()), "0");
                	Assert.assertNotNull((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[0].cpnSeqNbr")));
                	Assert.assertNotNull((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[1].cpnSeqNbr")));
                } else if (noOfCpns.equalsIgnoreCase("loadedcoupon")) {
//                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()), "2.0");
                	Assert.assertTrue((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()).equals("2.0") ||
                			(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()).equals("0.2"));
//                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()), "2.0");
                	Assert.assertTrue((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()).equals("2.0") ||
                			(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()).equals("0.2"));
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks").toString()), "0.0");
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.expiringExtraBuckCpnCount").toString()), "0");
                	Assert.assertNotNull((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[0].cpnSeqNbr")));
                	Assert.assertNull((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[1].cpnSeqNbr")));
                } else if (noOfCpns.equalsIgnoreCase("redeemablecoupon")) {
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.availableExtraBucks").toString()), "2.2");
//                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()), "2.0");
                	Assert.assertTrue((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()).equals("2.0") ||
                			(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBucks").toString()).equals("0.2"));
//                	Assert.assertTrue(((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks")).contains("0.2"));
                	Assert.assertTrue((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks").toString()).equals("2.0") ||
                			(getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks").toString()).equals("0.2"));
//                	Assert.assertTrue(((ArrayList) getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.onCardExtraBucks")).contains("0.2"));
                	Assert.assertEquals((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.expiringExtraBuckCpnCount").toString()), "1");
                	Assert.assertNotNull((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[0].cpnSeqNbr")));
                	Assert.assertNull((getCustService.getServiceResponse().jsonPath().get("cusInfResp.extraBuckRewardsSummary.loadableExtraBuckCpns[1].cpnSeqNbr")));
                }
            	
            	});
            
            Then("One coupon should be available in the response", () -> {
            	int CmpgnID;
            	int CpnNbr;
            	GetCustCusInfRespCpnResponse getcust = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnResponse();
            	CpnNbr = Integer.parseInt(getcust.getCpnNbr());
            	CmpgnID = getcust.getCmpgnId();
            	Assert.assertEquals(getcust.getCmpgnId().toString(), getCustService.getXtraCardActiveCouponData(getCustRequest.getSearchCardNbr(), "CMPGN_ID"));
             });
            
            Then("No coupon should be available in the response", () -> {
            	GetCustCusInfRespResponse getcust = getCustResponse.getGetCustCusInfRespResponse();
            	Assert.assertNull(getcust.getGetCustCusInfRespCpnResponse());
             });
                                          
            Then("The response should have status code {int} with message {string}", (Integer int1, String string) -> {
                String errorActual = getCustService.getServiceResponse().jsonPath().getString("errorMsg");
                int errorCode = getCustService.getServiceResponse().jsonPath().getInt("errorCd");
                Assert.assertEquals("Invalid Error code", int1.intValue(), errorCode);
                Assert.assertEquals("Invalid Error description", string, errorActual);
            });

        }
    }
}

