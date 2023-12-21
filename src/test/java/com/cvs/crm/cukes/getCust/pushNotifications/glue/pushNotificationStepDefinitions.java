package com.cvs.crm.cukes.getCust.pushNotifications.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.*;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class pushNotificationStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    GetCustTablesAndPreferencesService getCustTablesAndPreferencesService;
    @Autowired
    GetCustPushNotificationsService getCustPushNotificationsService;
    @Autowired
    SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;
    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    TestDataUtil testDataUtil;
    @Autowired
    XtraCardDao xtraCardDao;
    @Autowired
    GetCustService getCustService;

    String requestParam = "";
    Integer xtraCardNbr = null;
    JSONArray jsonArray = null;
    JSONObject jsonObject;
    GetCustRequest getCustRequest = new GetCustRequest();
    GetCustResponse getCustResponse;

    public pushNotificationStepDefinitions() {

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

        And("I filter with {string} in the request", (String search_term) -> {
            requestParam = search_term;
        });

        And("I get a response from getCust API", () -> {
            getCustResponse = getCustService.getCustResponse(getCustRequest, requestParam).as(GetCustResponse.class);
        });

        Then("API returns a response with status code as {int}", (Integer statusCd) -> {
            getCustService.getServiceResponse().then().statusCode(statusCd);
        });

        Then("I get error response with errorCd {int} from getCust API", (Integer errorCd) -> {
            String errorMsg = "";
            if (errorCd == 4) {
                errorMsg = "Card Not on File";
            } else
                throw new IllegalArgumentException("Unexpected statusCd : " + errorCd);

            Assert.assertTrue("Current ErrorCd: " + getCustResponse.getErrorCd() +
                            "\n Expected StatusCd: " + errorCd,
                    getCustResponse.getErrorCd().equals(errorCd));
            Assert.assertTrue("Current ErrorDesc: " + getCustResponse.getErrorMsg() +
                            "\n Expected ErrorDesc: " + errorMsg,
                    getCustResponse.getErrorMsg().equalsIgnoreCase(errorMsg));
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

        And("The response should have the node {string}", (String node) -> {
            Assert.assertTrue("The response doesn't contain the node - " + node + "\n ACTUAL :: " +
                            getCustService.getServiceResponse_jsonPath().getJsonObject("cusInfResp"),
                    getCustService.getServiceResponse_jsonPath().getJsonObject("cusInfResp").toString().contains(node));
        });

        Then("The response should have the node with value as", (DataTable dt) -> {
            List<Map<String, String>> data_set = dt.asMaps(String.class, String.class);
            String actual_value = null;

            for (Map<String, String> data : data_set) {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String key = entry.getKey();
                    System.out.println("KEY _ " + key);
                    String value = entry.getValue();
                    System.out.println("VALUE _ " + value);

                    if (key.equalsIgnoreCase("msgid")) {
                        actual_value = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPushNotifsResponse().getMsgId().toString();
                    } else if (key.equalsIgnoreCase("cpnCnt")) {
                        actual_value = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPushNotifsResponse().getCpnCnt().toString();
                    } else if (key.equalsIgnoreCase("pushNotifSeqNbr")) {
                        actual_value = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPushNotifsResponse().getPushNotifSeqNbr().toString();
                    } else {
                        throw new IllegalArgumentException("Incorrect argument passed - " + key);
                    }
                    System.out.println("ACTUAL VALUE _ " + actual_value);

                    if (key.equalsIgnoreCase("pushNotifSeqNbr")) {
                        if (value.equalsIgnoreCase("Y")) {
                            Assert.assertFalse(actual_value.isEmpty());
                        } else {
                            throw new IllegalArgumentException("Incorrect argument passed for " + key + " as = " + value);
                        }
                    } else {
                        Assert.assertTrue(key + " - doesn't match. \nACTUAL :: " + actual_value + "\nEXPECTED :: " + value,
                                actual_value.equalsIgnoreCase(value));
                    }
                }
            }

        });

        And("The response should have error code {int}", (String subNode, String node, String enrolled, String resp) -> {
            Assert.assertTrue("The response contain " + node + " > " + subNode + " node.",
                    getCustPushNotificationsService.getServiceResponse_Node("cusInfResp").contains(subNode));
            Assert.assertTrue("Response doesn't match.\n" + node + " > " + subNode + " > " + enrolled + " ::" + " \nACTUAL :: " + getCustPushNotificationsService
                            .getServiceResponse_Node("cusInfResp." + node + "." + subNode + "." + enrolled) + "\nEXPECTED :: " + resp,
                    getCustPushNotificationsService.getServiceResponse_Node("cusInfResp." + node + "." + subNode + "." + enrolled)
                            .equalsIgnoreCase(resp));
        });
    }
}
