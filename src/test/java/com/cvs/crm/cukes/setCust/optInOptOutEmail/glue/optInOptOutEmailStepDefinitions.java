package com.cvs.crm.cukes.setCust.optInOptOutEmail.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.SetCustResponse;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class optInOptOutEmailStepDefinitions extends SpringIntegrationTests implements En {

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

    ObjectMapper mapper = new ObjectMapper();

    public optInOptOutEmailStepDefinitions() {

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

        Then("The customerEmail table updated with emailStatusCd as {string}", (String emailStatusCd) -> {
            List<CustomerEmail> customer_email_data = testDataUtil.getData_customerEmail(xtraCardNbr.toString(),"EMAIL_STATUS_CD");
            Assert.assertTrue("Current EmailStatusCd: " + customer_email_data.get(0).getEmailStatusCd() +
                            "\n Expected EmailStatusCd: " + emailStatusCd,
                    customer_email_data.get(0).getEmailStatusCd().equals(emailStatusCd));

        });

    }

}
