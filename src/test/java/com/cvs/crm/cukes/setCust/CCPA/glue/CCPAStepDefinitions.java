package com.cvs.crm.cukes.setCust.CCPA.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.SetCustResponse;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Slf4j
public class CCPAStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;
    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    GetCustService getCustService;
    @Autowired
    DateUtil dateUtil;
    @Autowired
    TestDataUtil testDataUtil;
    @Autowired
    CustomerPhone customerPhone;
    @Autowired
    XtraCardDao xtraCardDao;
    @Autowired
    CustomerDao customerDao;

    String setCust_action = "";
    Integer xtraCardNbr = null;
    Integer custId = null;
    Long encodedXtraCardNbr = null;
    String emailAddrTxt = null;
    JSONArray jsonArray = null;
    JSONObject jsonObject;

    SetCustRequest setCustRequest = new SetCustRequest();
    SetCustResponse setcustResponse;

    public CCPAStepDefinitions() {

        Given("{string}", (String scenario) -> {
        });

        Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
            jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
            jsonObject = testDataUtil.extractTestData(jsonArray);
            JSONObject jsonObject_Email = testDataUtil.extractTestData(jsonArray, "CUSTOMER_EMAIL");
            xtraCardNbr = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
            encodedXtraCardNbr = Long.parseLong(jsonObject.get("ENCODED_XTRA_CARD_NBR").toString());
            custId = Integer.parseInt(jsonObject.get("CUST_ID").toString());
            emailAddrTxt = jsonObject_Email.get("EMAIL_ADDR_TXT").toString();
            setCustRequest.setSearchCardNbr(xtraCardNbr);
            setCustRequest.setEncodedXtraCardNbr(encodedXtraCardNbr);
            setCustRequest.setEmailAddrTxt(emailAddrTxt);
        });

        Given("I use my xtraCard with card type {string} at {string}", (String card_type, String channel) -> {
            setCustRequest.setVersion("1.2");
            setCustRequest.setChannel(channel);
            setCustRequest.setSearchCardType(getCustService.set_typeCd(card_type));
        });

        When("I want to request {string} from {string}",(String data_request, String state) -> {
            setCustRequest.setRqstDt(dateUtil.timeStamp(0, "yyyy-MMM-dd HH:mm:ss"));
            setCustRequest.setRqstStateCd(state.toUpperCase());

            if (data_request.equalsIgnoreCase("data report")){
                setCustRequest.setRptRqstInd("Y");
                setCustRequest.setPurgeRqstInd("N");
            } else if (data_request.equalsIgnoreCase("data purge")) {
                setCustRequest.setRptRqstInd("N");
                setCustRequest.setPurgeRqstInd("Y");
            } else
                throw new IllegalArgumentException("Unexpected value: " + data_request.toLowerCase());
        });

        Then("I try {string} via request", (String action) -> {
            setCust_action = action;
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

        And("My xtraCard exists in {string} table", (String tableName) -> {
            List xtra_card_data = xtraCardDao.getXtraCardDetails(xtraCardNbr, tableName);
            Assert.assertTrue("\nXTRA_CARD_NBR, " + xtraCardNbr + " does not exist in " + tableName + " table", !xtra_card_data.isEmpty());
        });

        Then("The XTRA_PRIVACY_REQUEST table should have correct {string} values", (String data_request) -> {
            List<XtraPrivacyRequest> xtraPrivacyRequest_data = xtraCardDao.getData_XtraPrivacyRequest(xtraCardNbr.toString());

            Assert.assertTrue("Current RPT_RQST_IND:: " +  xtraPrivacyRequest_data.get(0).getRptRqstInd() +
                            "\n Expected RPT_RQST_IND:: " + setCustRequest.getRptRqstInd(),
                    setCustRequest.getRptRqstInd().equals(xtraPrivacyRequest_data.get(0).getRptRqstInd()));

            Assert.assertTrue("Current PURGE_RQST_IND:: " +  xtraPrivacyRequest_data.get(0).getPurgeRqstInd() +
                            "\n Expected PURGE_RQST_IND:: " + setCustRequest.getPurgeRqstInd(),
                    setCustRequest.getPurgeRqstInd().equals(xtraPrivacyRequest_data.get(0).getPurgeRqstInd()));

            if (setCustRequest.getRqstStateCd().equals("")) {
                Assert.assertNull("Current RQST_STATE_CD:: " +  xtraPrivacyRequest_data.get(0).getRqstStateCd() +
                        "\n Expected RQST_STATE_CD:: " + setCustRequest.getRqstStateCd(),
                        xtraPrivacyRequest_data.get(0).getRqstStateCd());
            } else {
                Assert.assertTrue("Current RQST_STATE_CD:: " +  xtraPrivacyRequest_data.get(0).getRqstStateCd() +
                                "\n Expected RQST_STATE_CD:: " + setCustRequest.getRqstStateCd(),
                        setCustRequest.getRqstStateCd().equals(xtraPrivacyRequest_data.get(0).getRqstStateCd()));
            }
        });

    }
}
