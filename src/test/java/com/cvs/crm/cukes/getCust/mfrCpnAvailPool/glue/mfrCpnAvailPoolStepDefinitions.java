package com.cvs.crm.cukes.getCust.mfrCpnAvailPool.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.request.CpnsRequest;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.CpnsResponse;
import com.cvs.crm.model.response.GetCustCusInfRespCpnsResponse;
import com.cvs.crm.model.response.GetCustCusInfRespMfrCpnAvailPoolResponse;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.CpnsService;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
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
public class mfrCpnAvailPoolStepDefinitions extends SpringIntegrationTests implements En {

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

    public mfrCpnAvailPoolStepDefinitions() {
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

            Given("I filter with {string} in the request", (String search_term) -> {
                requestParam = search_term;
            });
            
            Given("I choose a coupon for {string}", (String indicator) -> {
            	switch (indicator.toLowerCase()) {
                case "everwebredeemableind":
                	target_cpn = campaignDao.selectRecentCampaignOMCoupon();
                	getCustService.getCustMFRAvailPoolAPITestDataSetup(target_cpn);
                	System.out.println("target cpn is: " + target_cpn);
                    break;
                case "expsoonind":
                	target_cpn = campaignDao.selectRecentCampaignOMCoupon();
                    getCustService.getCustMFRAvailPoolAPITestDataSetup(target_cpn);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + indicator.toLowerCase());
            }
            });
//
            And("I get a response from getCust API", () -> {
//                getCustService.getCustResponse(getCustRequest, requestParam);
                getCustResponse = getCustService.getCustResponse(getCustRequest, requestParam).as(GetCustResponse.class);
            });
//
            Then("API returns a response with status code as {int}", (Integer statusCd) -> {
                getCustService.getServiceResponse().then().statusCode(statusCd);
            });

            Then("All active MFR coupons should be available in response", () -> {
            	ArrayList<String> couponsJson = new ArrayList<String>();
            	ArrayList<String> couponsDB = new ArrayList<String>();
            	
            	List<GetCustCusInfRespMfrCpnAvailPoolResponse> getcust = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMfrCpnAvailPoolResponseList();
            	Collections.sort(getcust,(o1, o2) -> (o1.getCpnNbr().compareTo(o2.getCpnNbr())));
            	
            	for(GetCustCusInfRespMfrCpnAvailPoolResponse a: getcust) {
            		couponsJson.add(a.getCpnNbr().toString());
//                	System.out.println("json path is:" + a.getCpnNbr().toString());
                	}        	
//            	System.out.println("list json path is:" + couponsJson.get(0));
//            	System.out.println("target coupon is " + target_cpn);
//            	System.out.println("list is " + couponsJson);
            	if (target_cpn!=0) {
            		index = couponsJson.indexOf(target_cpn.toString());
            		}
//            	System.out.println("index is " + index);
            	
            	List<Map<String, Object>> cmpgn_cpn_data = campaignDao.getActiveCmpgnOMCoupons(43325);
            	cmpgn_cpn_data.sort(Comparator.comparing(map-> map.get("CPN_NBR").toString()));
            	
            	for(Map<String, Object> a: cmpgn_cpn_data) {
            		couponsDB.add(a.get("CPN_NBR").toString());
//            		System.out.println("DB value is:" + a.get("CPN_NBR").toString());
                	}
//            	System.out.println("list DB path is:" + couponsDB.get(0));         
            	
            	Assert.assertEquals(cmpgn_cpn_data.size(), getcust.size());
            	for(int i=0; i<getcust.size();i++) {
            		Assert.assertEquals(couponsJson.get(i), couponsDB.get(i));
//            		System.out.println("Assert passed for index: "+i);
            	}
            });
            
            Then("Check the data in the response", () -> {
            	int CmpgnID;
            	int CpnNbr;
            	List<GetCustCusInfRespMfrCpnAvailPoolResponse> getcust = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMfrCpnAvailPoolResponseList();
            	CpnNbr = getcust.get(0).getCpnNbr();
            	CmpgnID = getcust.get(0).getCmpgnId();
            	System.out.println("CMPGN ID IS:" + CmpgnID);
            	System.out.println("CPN NBR IS:" + CpnNbr);
            	Assert.assertEquals(getcust.get(0).getCmpgnId().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CMPGN_ID"));
            	Assert.assertEquals(getcust.get(0).getCpnNbr().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CPN_NBR"));
//            	Assert.assertEquals(getcust.get(0).getMaxRedeemAmt().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CMPGN_ID")+".00");
            	Assert.assertEquals(getcust.get(0).getCpnDsc().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CPN_DSC"));
            	Assert.assertEquals(getcust.get(0).getFndgCd().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "FNDG_CD"));
            	Assert.assertEquals(getcust.get(0).getMfrOfferValueDsc().toString(), getCustService.getCmpgnOMCpnData(CmpgnID, CpnNbr, "MFR_OFFER_VALUE_DSC"));
            	Assert.assertEquals(getcust.get(0).getMfrOfferBrandName().toString(), getCustService.getCmpgnOMCpnData(CmpgnID, CpnNbr, "MFR_OFFER_BRAND_NAME"));
            	Assert.assertEquals(getcust.get(0).getMfrOfferValueDsc().toString(), getCustService.getCmpgnOMCpnData(CmpgnID, CpnNbr, "MFR_OFFER_VALUE_DSC"));
            	Assert.assertEquals(getcust.get(0).getMfrInd().toString(), "Y");
            	Assert.assertEquals(getcust.get(0).getPersonalizedInd().toString(), "N");

//            	List<Map<String, Object>> cmpgn_cpn_data = campaignDao.getCmpgnCpnsData(CmpgnID,CpnNbr);
//            	List<Map<String, Object>> cmpgn_o_m_cpn_data = campaignDao.getCmpgnOMCpnsData(CmpgnID,CpnNbr);
            	
//            	for(Map<String,Object> map: cmpgn_cpn_data) {
//            		if(map.containsKey("CMPGN_ID")) {
//            			Assert.assertEquals(getcust.get(0).getCmpgnId().toString(), map.get("CMPGN_ID").toString());
//            		}
//            	}
            	
            	
//            	Assert.assertEquals(getcust.get(0).getCmpgnId(), campaignDao.getCmpgnCpnsData("CPN_NBR",CmpgnID,CpnNbr));
//            	System.out.println("CMPGN ID IS:" + CmpgnID1);
//            	System.out.println("CPN NBR IS:" + CpnNbr1);
            });
            
            Then("Validate the flag of {string} is {string}", (String node, String value) -> {
            	int CmpgnID;
            	int CpnNbr;
            	List<GetCustCusInfRespMfrCpnAvailPoolResponse> getcust = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMfrCpnAvailPoolResponseList();
            	
            	switch (node.toLowerCase()) {
                case "everwebredeemableind":
                	Assert.assertEquals(value,getcust.get(index).getEverWebRedeemableInd().toString());
                	System.out.println("index is " + index);
                	getCustService.getCustMFRAvailPoolAPITestDataRevert(target_cpn);
                    break;
                case "expsoonind":
                	Assert.assertEquals(value,getcust.get(index).getExpSoonInd().toString());

//                    getServiceBody = getServiceBody_DigitizedCpnInd(getCustRequest);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + node.toLowerCase());
            	
//            	Assert.assertEquals(value,getcust.get(index).getCpnDsc().toString());
            	}

//            	CpnNbr = getcust.get(0).getCpnNbr();
//            	CmpgnID = getcust.get(0).getCmpgnId();
//            	System.out.println("CMPGN ID IS:" + CmpgnID);
//            	System.out.println("CPN NBR IS:" + CpnNbr);
//            	Assert.assertEquals(getcust.get(0).getCmpgnId().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CMPGN_ID"));
//            	Assert.assertEquals(getcust.get(0).getCpnNbr().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CPN_NBR"));
////            	Assert.assertEquals(getcust.get(0).getMaxRedeemAmt().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CMPGN_ID")+".00");
//            	Assert.assertEquals(getcust.get(0).getCpnDsc().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "CPN_DSC"));
//            	Assert.assertEquals(getcust.get(0).getFndgCd().toString(), getCustService.getCmpgnCpnData(CmpgnID, CpnNbr, "FNDG_CD"));
//            	Assert.assertEquals(getcust.get(0).getMfrOfferValueDsc().toString(), getCustService.getCmpgnOMCpnData(CmpgnID, CpnNbr, "MFR_OFFER_VALUE_DSC"));
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

