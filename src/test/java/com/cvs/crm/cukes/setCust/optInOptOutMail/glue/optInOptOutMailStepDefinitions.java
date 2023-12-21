package com.cvs.crm.cukes.setCust.optInOptOutMail.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.CustomerOpt;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.SetCustResponse;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class optInOptOutMailStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;

    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    GetCustService getCustService;
    @Autowired
    TestDataUtil testDataUtil;

    String setCust_action = "";
    Integer xtraCardNbr = null;
    Long encodedXtraCardNbr = null;
    JSONArray jsonArray = null;
    JSONObject jsonObject;

    SetCustRequest setCustRequest = new SetCustRequest();

    SetCustResponse setcustResponse;

    public optInOptOutMailStepDefinitions() {

        Given("{string}", (String scenario) -> {
        });

        Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
            jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
            jsonObject = testDataUtil.extractTestData(jsonArray);
            xtraCardNbr = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
            encodedXtraCardNbr = Long.parseLong(jsonObject.get("ENCODED_XTRA_CARD_NBR").toString());
            setCustRequest.setSearchCardNbr(xtraCardNbr);
            setCustRequest.setEncodedXtraCardNbr(encodedXtraCardNbr);
        });

        Given("I use my xtraCard with card type {string} at {string}", (String card_type, String channel) -> {
            setCustRequest.setVersion("1.2");
            setCustRequest.setChannel(channel);
            setCustRequest.setSearchCardType(getCustService.set_typeCd(card_type));
        });

        Then("I try {string} via request", (String action) -> {
            setCust_action = action;
        });

        When("I {string} via setCust", (String setCust_action) -> {
            try {
                setCustCarepassEnrollmentService.setCust(setCustRequest, setCust_action);
            } catch (Exception ex) {
                log.info("Exception - " + ex);
                throw ex;
            }
        });

        And("I get a response from setCust API", () -> {
            setcustResponse = setCustCarepassEnrollmentService.setCust(setCustRequest, setCust_action).as(SetCustResponse.class);
        });

        Then("API returns a response with status code as {int}", (Integer statusCd) -> {
            setCustCarepassEnrollmentService.getServiceResponse().then().statusCode(statusCd);
        });

        Then("The response should have xtraCard details", () -> {
        Assert.assertTrue("Current XtraCardNbr: " + setcustResponse.getXtraCardNbr() +
                        "\n Expected XtraCardNbr: " + setCustRequest.getSearchCardNbr(),
                setcustResponse.getXtraCardNbr().equals(setCustRequest.getSearchCardNbr()));
        Assert.assertTrue("Current EncodedXtraCardNbr: " + setcustResponse.getEncodedXtraCardNbr() +
                        "\n Expected EncodedXtraCardNbr: " + setCustRequest.getEncodedXtraCardNbr(),
                setcustResponse.getEncodedXtraCardNbr().equals(setCustRequest.getEncodedXtraCardNbr()));
        });

        Then("The customerOpt table updated with optCd as {string} and optTypeCd as {string}", (String optCd, String optTypeCd) -> {
            List<CustomerOpt> customer_opt_data = testDataUtil.getData_customerOpt(xtraCardNbr.toString(), "OPT_CD, OPT_TYPE_CD");
            Assert.assertTrue("Current OptCd: " + customer_opt_data.get(0).getOptCd() +
                            "\n Expected OptCd: " + optCd,
                    customer_opt_data.get(0).getOptCd().equals(optCd));
            Assert.assertTrue("Current OptTypeCd: " + customer_opt_data.get(0).getOptTypeCd() +
                            "\n Expected OptTypeCd: " + optTypeCd,
                    customer_opt_data.get(0).getOptTypeCd().equals(optTypeCd));
        });

    }
}
