package com.cvs.crm.cukes.getCust.singleCpn.glue;

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
public class singleCpnStepDefinitions extends SpringIntegrationTests implements En {

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

    public singleCpnStepDefinitions() {
        {

            Given("{string}", (String scenario) -> {
            });

            Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
                jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
                jsonObject = testDataUtil.extractTestData(jsonArray);
                xtraCardNbr = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
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
            
            Given("I hit campaign earn1 for card {string}", (String CardNbr) -> {
            	System.out.println("Campaign hit earn 1");
            	Integer txnNbr1 = generateRandom.randomNumberString();
                Integer txnInvoiceNbr1 = generateRandom.randomNumberString();
                campaignEarnServiceUtil.hitCampaignEarn(xtraCardNbr, 4, 10, 999975, txnNbr1, txnInvoiceNbr1);
                campaignEarnServiceUtil.getServiceResponse().then().log().all();
            });
            
            Given("I hit campaign earn2 for card {string}", (String CardNbr) -> {
            	System.out.println("Campaign hit earn 2");
            	Integer txnNbr2 = generateRandom.randomNumberString();
                Integer txnInvoiceNbr2 = generateRandom.randomNumberString();
                campaignEarnServiceUtil.hitCampaignEarn(xtraCardNbr, 10, 100, 999975, txnNbr2, txnInvoiceNbr2);
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
            
            Then("Single coupon details should be available in the response", () -> {
            	int CmpgnID;
            	int CpnNbr;
            	GetCustCusInfRespCpnResponse getcust = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnResponse();
            	CpnNbr = Integer.parseInt(getcust.getCpnNbr());
            	CmpgnID = getcust.getCmpgnId();
            	Assert.assertEquals(getcust.getCmpgnId().toString(), getCustService.getXtraCardActiveCouponData(getCustRequest.getSearchCardNbr(), "CMPGN_ID"));
            	Assert.assertEquals(getcust.getCpnNbr().toString(), getCustService.getXtraCardActiveCouponData(getCustRequest.getSearchCardNbr(), "CPN_NBR"));
            	Assert.assertEquals(getcust.getCpnSeqNbr().toString(), getCustService.getXtraCardActiveCouponData(getCustRequest.getSearchCardNbr(), "CPN_SEQ_NBR"));
            	Assert.assertEquals(getcust.getCpnDsc().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CPN_DSC"));
            	Assert.assertEquals(getcust.getFndgCd().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "FNDG_CD"));
            	Assert.assertEquals(getcust.getAmtTypeCd().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "AMT_TYPE_CD"));
            	Assert.assertEquals(getcust.getCpnRecptTxt().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CPN_RECPT_TXT"));
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
//            	int CmpgnID;
//            	int CpnNbr;
            	GetCustCusInfRespResponse getcust = getCustResponse.getGetCustCusInfRespResponse();
//            	CpnNbr = Integer.parseInt(getcust.getCpnNbr());
//            	CmpgnID = getcust.getCmpgnId();
            	Assert.assertNull(getcust.getGetCustCusInfRespCpnResponse());
             });
                                          
            Then("The response should have status code {int} with message {string}", (Integer int1, String string) -> {
                String errorActual = getCustService.getServiceResponse().jsonPath().getString("errorMsg");
                int errorCode = getCustService.getServiceResponse().jsonPath().getInt("errorCd");
                Assert.assertEquals("Invalid Error code", int1.intValue(), errorCode);
                Assert.assertEquals("Invalid Error description", string, errorActual);
            });
//
//            Then("Atleast one coupon should be digitized", () -> {
//                Assert.assertTrue("No.of Cpns not digitized: " + getCustService.getCustResponse_Cpns(getCustResponse, "N").size(),
//                        !getCustService.getCustResponse_Cpns(getCustResponse, "Y").isEmpty());
//            });
//
//            Then("None of the coupons should be digitized", () -> {
//                Assert.assertTrue("No.of Cpns digitized: " + getCustService.getCustResponse_Cpns(getCustResponse, "Y").size(),
//                        getCustService.getCustResponse_Cpns(getCustResponse, "Y").isEmpty());
//            });
//
//            And("My xtraCard {string} in {string} table", (String is_exist, String tableName) -> {
//                xtraCardNbr = getCustRequest.getSearchCardNbr();
//                List xtra_card_data = xtraCardDao.getXtraCardDetails(xtraCardNbr, tableName);
//                if (is_exist.equalsIgnoreCase("Y")) {
//                    Assert.assertTrue("\nXTRA_CARD_NBR, " + xtraCardNbr + " does not exist in " + tableName + " table", !xtra_card_data.isEmpty());
//                } else if (is_exist.equalsIgnoreCase("N")) {
//                    Assert.assertTrue("\nXTRA_CARD_NBR, " + xtraCardNbr + " exist in " + tableName + " table", xtra_card_data.isEmpty());
//                }
//            });
//
//            Given("I {string} via setCust", (String setCust_action) -> {
//                setCustRequest.setSearchCardNbr(getCustRequest.getSearchCardNbr());
//                setCustRequest.setSearchCardType(getCustRequest.getSearchCardType());
//                setCustRequest.setVersion(getCustRequest.getVersion());
//                setCustRequest.setChannel(getCustRequest.getChannel());
//                try {
//                    setCustCarepassEnrollmentService.setCust(setCustRequest, setCust_action);
//                } catch (Exception ex) {
//                    log.info("Exception - " + ex);
//                    throw ex;
//                }
//            });
//
//            And("I have a coupon with digitizedCpnInd as {string}", (String isDigitized) -> {
//                GetCustCusInfRespCpnsResponse cpnResponse = null;
//                List<GetCustCusInfRespCpnsResponse> list_cpnSeqNbr = getCustService.getCustResponse_Cpns(getCustResponse, isDigitized);
//                System.out.println("CHECK >> list_cpnSeqNbr >> "+ list_cpnSeqNbr.size());
//                if (list_cpnSeqNbr.size() > 0) {
//                    cpnResponse = list_cpnSeqNbr.get(new Random().nextInt(list_cpnSeqNbr.size()));
//                    System.out.println("CHECK >> cpnResponse_list_cpnSeqNbr >> " + cpnResponse.getCpnSeqNbr());
//                }
//                cpnsRequest.setCpnSeqNbr(cpnResponse.getCpnSeqNbr());
//                cpnsRequest.setCmpgnId(cpnResponse.getCmpgnId());
//                cpnsRequest.setCpnSkuNbr(cpnResponse.getCpnNbr());
//                cpnsRequest.setSearchCardNbr(getCustRequest.getSearchCardNbr());
//                cpnsRequest.setSearchCardType(getCustRequest.getSearchCardType());
//                cpnsRequest.setVersion("1.1");
//                cpnsRequest.setChannel(getCustRequest.getChannel());
//
//                cpnsService.getCoupon_Eligible_for_Digitize(cpnsRequest);
//            });
//
//            Given("I {string} a coupon for my xtraCard", (String cpn_action) -> {
//                cpnsResponse = cpnsService.cpnResponse(cpnsRequest, cpn_action).as(CpnsResponse.class);
//            });
//
//            When("I get a response with statusCd {int} from Coupons API", (Integer statusCd) -> {
//
//                Assert.assertTrue("Current CpnSeqNbr: " + cpnsResponse.getCpnSeqNbr() +
//                                "\n Expected CpnSeqNbr: " + cpnsRequest.getCpnSeqNbr(),
//                        cpnsResponse.getCpnSeqNbr().equals(cpnsRequest.getCpnSeqNbr()));
//                Assert.assertTrue("Current StatusCd: " + cpnsResponse.getStatusCd() +
//                                "\n Expected StatusCd: " + statusCd,
//                        cpnsResponse.getStatusCd().equals(statusCd));
//            });

        }
    }
}

