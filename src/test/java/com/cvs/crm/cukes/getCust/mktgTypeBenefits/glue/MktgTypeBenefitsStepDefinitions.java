package com.cvs.crm.cukes.getCust.mktgTypeBenefits.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.request.CpnsRequest;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.CpnsResponse;
import com.cvs.crm.model.response.GetCustCusInfRespCpnResponse;
import com.cvs.crm.model.response.GetCustCusInfRespCpnsResponse;
import com.cvs.crm.model.response.GetCustCusInfRespMfrCpnAvailPoolResponse;
import com.cvs.crm.model.response.GetCustCusInfRespQebEarningTypeResponse;
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
public class MktgTypeBenefitsStepDefinitions extends SpringIntegrationTests implements En {

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

    public MktgTypeBenefitsStepDefinitions() {
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
                getCustRequest.setVersion("1.2");
                getCustRequest.setChannel(channel);
                getCustRequest.setSearchCardType(getCustService.set_typeCd(card_type));
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
            
            Then("Validate MktgTypeBenefits in the response for type code {string}", (String typeCode) -> {
            	getCustResponse = getCustService.getCustResponse(getCustRequest, requestParam).as(GetCustResponse.class);
            	if(typeCode.equalsIgnoreCase("E")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("E",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("20",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            		Assert.assertEquals("P",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitItemGrpCd());
            		Assert.assertEquals("30",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("A")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("E",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("30",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            		Assert.assertEquals("P",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitItemGrpCd());
            		Assert.assertEquals("30",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("D")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("FP",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("50",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("G1")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("P",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("30",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("G2")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("E",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("10",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            		Assert.assertEquals("P",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitItemGrpCd());
            		Assert.assertEquals("20",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("H")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("P",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("15",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("M")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("FP",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("20",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("O")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("O",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("10",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("P")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("P",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("20",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("S")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("E",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("20",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            		Assert.assertEquals("P",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitItemGrpCd());
            		Assert.assertEquals("50",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(1).getBenefitPct().toString());
            	} else if(typeCode.equalsIgnoreCase("W")) {
            		Assert.assertEquals(typeCode,getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
            		Assert.assertEquals("FP",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitItemGrpCd());
            		Assert.assertEquals("20",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList().get(0).getBenefitPct().toString());
            	}
            });
            
            Then("MktgTypeBenefits should be available in the response", () -> {
            	Assert.assertNotNull(getCustService.getServiceResponse().jsonPath().get("cusInfResp.mktgTypeBenefits"));
//            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.xtraCarePrefs.phr.enrolled").equals(getCustStatus));
            	});
            

            
            Then("Validate MktgTypeBenefits in the response for employee card", () -> {
            	getCustResponse = getCustService.getCustResponse(getCustRequest, requestParam).as(GetCustResponse.class);
            	Assert.assertNotNull(getCustService.getServiceResponse().jsonPath().get("cusInfResp.mktgTypeBenefits"));
        		Assert.assertEquals("E",getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getMktgTypeCd());
        		Assert.assertNull(getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespMktgTypeBenefitsResponse().getGetCustCusInfRespMktgTypeBenefitsbenefitsResponseList());
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

