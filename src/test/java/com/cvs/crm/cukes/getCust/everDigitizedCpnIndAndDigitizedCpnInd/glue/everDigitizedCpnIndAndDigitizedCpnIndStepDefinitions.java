package com.cvs.crm.cukes.getCust.everDigitizedCpnIndAndDigitizedCpnInd.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.request.CpnsRequest;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.CpnsResponse;
import com.cvs.crm.model.response.GetCustCusInfRespCpnsResponse;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.CpnsService;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class everDigitizedCpnIndAndDigitizedCpnIndStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    GetCustService getCustService;
    @Autowired
    CpnsService cpnsService;
    @Autowired
    SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;
    @Autowired
    XtraCardDao xtraCardDao;
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


    public everDigitizedCpnIndAndDigitizedCpnIndStepDefinitions() {
        {

            Given("{string}", (String scenario) -> {
            });

            Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
                jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
                jsonObject = testDataUtil.extractTestData(jsonArray);
                xtraCardNbr = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
                getCustRequest.setSearchCardNbr(xtraCardNbr);
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

            Then("API returns a response with status code as {int}", (Integer statusCd) -> {
                getCustService.getServiceResponse().then().statusCode(statusCd);
            });

            And("The response for xtraCard should have {string} with value {string}", (String node, String resp) -> {
                Assert.assertTrue("\nCurrent " + node + " value: " + getCustResponse.getGetCustCusInfRespResponse()
                                .getGetCustCusInfRespXtraCardResponse().getEverDigitizedCpnInd() +
                                "\nExpected " + node + " value: " + resp,
                        getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCardResponse()
                                .getEverDigitizedCpnInd().toString().equalsIgnoreCase(resp));
            });

            Then("The response for Cpns should have {string} with value {string}", (String node, String resp) -> {
                List<GetCustCusInfRespCpnsResponse> list_Cpns = getCustService.getCustResponse_Cpns(getCustResponse,"all");
                for (GetCustCusInfRespCpnsResponse cpns : list_Cpns) {
                    if (cpns.getCpnSeqNbr().equals(cpnsRequest.getCpnSeqNbr())) {
                        Assert.assertTrue("Current digitizedCpnInd for " + cpnsRequest.getCpnSeqNbr() + " : "
                                        + cpns.getDigitizedCpnInd() + "Expected : " + resp,
                                cpns.getDigitizedCpnInd().toString().equalsIgnoreCase(resp));
                    }
                }
            });

            Then("Atleast one coupon should be digitized", () -> {
                Assert.assertTrue("No.of Cpns not digitized: " + getCustService.getCustResponse_Cpns(getCustResponse, "N").size(),
                        !getCustService.getCustResponse_Cpns(getCustResponse, "Y").isEmpty());
            });

            Then("None of the coupons should be digitized", () -> {
                Assert.assertTrue("No.of Cpns digitized: " + getCustService.getCustResponse_Cpns(getCustResponse, "Y").size(),
                        getCustService.getCustResponse_Cpns(getCustResponse, "Y").isEmpty());
            });

            And("My xtraCard {string} in {string} table", (String is_exist, String tableName) -> {
                xtraCardNbr = getCustRequest.getSearchCardNbr();
                List xtra_card_data = xtraCardDao.getXtraCardDetails(xtraCardNbr, tableName);
                if (is_exist.equalsIgnoreCase("Y")) {
                    Assert.assertTrue("\nXTRA_CARD_NBR, " + xtraCardNbr + " does not exist in " + tableName + " table", !xtra_card_data.isEmpty());
                } else if (is_exist.equalsIgnoreCase("N")) {
                    Assert.assertTrue("\nXTRA_CARD_NBR, " + xtraCardNbr + " exist in " + tableName + " table", xtra_card_data.isEmpty());
                }
            });

            Given("I {string} via setCust", (String setCust_action) -> {
                setCustRequest.setSearchCardNbr(getCustRequest.getSearchCardNbr());
                setCustRequest.setSearchCardType(getCustRequest.getSearchCardType());
                setCustRequest.setVersion(getCustRequest.getVersion());
                setCustRequest.setChannel(getCustRequest.getChannel());
                try {
                    setCustCarepassEnrollmentService.setCust(setCustRequest, setCust_action);
                } catch (Exception ex) {
                    log.info("Exception - " + ex);
                    throw ex;
                }
            });

            And("I have a coupon with digitizedCpnInd as {string}", (String isDigitized) -> {
                GetCustCusInfRespCpnsResponse cpnResponse = null;
                List<GetCustCusInfRespCpnsResponse> list_cpnSeqNbr = getCustService.getCustResponse_Cpns(getCustResponse, isDigitized);
                System.out.println("CHECK >> list_cpnSeqNbr >> "+ list_cpnSeqNbr.size());
                if (list_cpnSeqNbr.size() > 0) {
                    cpnResponse = list_cpnSeqNbr.get(new Random().nextInt(list_cpnSeqNbr.size()));
                    System.out.println("CHECK >> cpnResponse_list_cpnSeqNbr >> " + cpnResponse.getCpnSeqNbr());
                }
                cpnsRequest.setCpnSeqNbr(cpnResponse.getCpnSeqNbr());
                cpnsRequest.setCmpgnId(cpnResponse.getCmpgnId());
                cpnsRequest.setCpnSkuNbr(cpnResponse.getCpnNbr());
                cpnsRequest.setSearchCardNbr(getCustRequest.getSearchCardNbr());
                cpnsRequest.setSearchCardType(getCustRequest.getSearchCardType());
                cpnsRequest.setVersion("1.1");
                cpnsRequest.setChannel(getCustRequest.getChannel());

                cpnsService.getCoupon_Eligible_for_Digitize(cpnsRequest);
            });

            Given("I {string} a coupon for my xtraCard", (String cpn_action) -> {
                cpnsResponse = cpnsService.cpnResponse(cpnsRequest, cpn_action).as(CpnsResponse.class);
            });

            When("I get a response with statusCd {int} from Coupons API", (Integer statusCd) -> {

                Assert.assertTrue("Current CpnSeqNbr: " + cpnsResponse.getCpnSeqNbr() +
                                "\n Expected CpnSeqNbr: " + cpnsRequest.getCpnSeqNbr(),
                        cpnsResponse.getCpnSeqNbr().equals(cpnsRequest.getCpnSeqNbr()));
                Assert.assertTrue("Current StatusCd: " + cpnsResponse.getStatusCd() +
                                "\n Expected StatusCd: " + statusCd,
                        cpnsResponse.getStatusCd().equals(statusCd));
            });

        }
    }
}

