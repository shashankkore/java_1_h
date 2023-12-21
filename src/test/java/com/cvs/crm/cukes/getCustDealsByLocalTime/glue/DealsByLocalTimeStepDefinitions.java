package com.cvs.crm.cukes.getCustDealsByLocalTime.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.service.GetCustDealsByLocalTimeService;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java8.En;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class DealsByLocalTimeStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    GetCustDealsByLocalTimeService getCustDealsByLocalTimeService;
    @Autowired
    TestDataUtil testDataUtil;
    String localTimeStamp = "";
    String requestParam = "";
    Integer xtraCardNbr = null;
    JSONArray jsonArray;
    JSONObject jsonObject;

    public DealsByLocalTimeStepDefinitions() {
        {
            GetCustRequest request = new GetCustRequest();


            Given("{string}", (String scenario) -> {
            });

            Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
                jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
                jsonObject = testDataUtil.extractTestData(jsonArray);
                xtraCardNbr = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
                request.setSearchCardNbr(xtraCardNbr);
                request.setSearchCardType("0002");
            });

            When("I use XCardType {string}", (String string) -> {
                request.setSearchCardType(string);
            });

            When("I use transactionTS {string}", (String string) -> {
                localTimeStamp=string;
            });
            Given("I filter for {string} in the request", (String filter) -> {
                requestParam = filter;
            });

            And("I filter for {string} in the request with {string}", (String filter, String criteria) -> {
                requestParam = filter + "," + criteria;
            });

            When("I use the get customer IVR API for channel {string}", (String channel) -> {
                request.setVersion("1.2");
                request.setChannel(channel);
                getCustDealsByLocalTimeService.getServiceResponse(request, requestParam,localTimeStamp);
            });

            Then("I receive a response with success status", () -> {
                getCustDealsByLocalTimeService.getServiceResponse().then().log().all().statusCode(200);
            });
            Then("I receive a response with fail status", () -> {
                getCustDealsByLocalTimeService.getServiceResponse().then().log().all().statusCode(400);
            });
            And("The response should return {string} nodes", (String respNode) -> {
                String response = getCustDealsByLocalTimeService.getServiceResponse().jsonPath().getString("cusInfResp");
                String[] nodes = respNode.split(",");
                for (String node : nodes) {
                    Assert.assertTrue("The response contain " + node + " node.", response.contains(node.trim()));
                }
            });


        }
    }




}

