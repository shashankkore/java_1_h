package com.cvs.crm.cukes.getCustXtraCardCipherTxt.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.service.GetCustXtraCardCipherTxtService;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class GetCustXtraCardCipherTxtStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    GetCustXtraCardCipherTxtService getCustXtraCardCipherTxtService;

    String requestParam = "";
    Integer xtraCardNum = null;

    public GetCustXtraCardCipherTxtStepDefinitions() {
        {
            GetCustRequest request = new GetCustRequest();

            Given("I use my Extra Card {string}", (String cardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(Integer.parseInt(cardNbr));
                xtraCardNum = Integer.parseInt(cardNbr);
            });

            Given("I filter for  {string} in the request", (String filter) -> {
                requestParam = filter;
            });

            When("I use the get customer API for channel {string}", (String channel) -> {
                request.setVersion("1.2");
                request.setChannel(channel);
                getCustXtraCardCipherTxtService.getServiceResponse(request, requestParam);
            });

            Then("I receive a response with success status", () -> {
                getCustXtraCardCipherTxtService.getServiceResponse().then().log().all().statusCode(200);
            });

            And("The response should contain {string} node", (String respNode) -> {
                String response = getCustXtraCardCipherTxtService.getServiceResponse().jsonPath().getString("cusInfResp");
                Assert.assertTrue("The response contain "+respNode+ "node.", response.contains(respNode));
                String xtraCardCipherTxt = getCustXtraCardCipherTxtService.getServiceResponse()
                        .jsonPath().getString("cusInfResp.xtraCard.xtraCardCipherTxt");
                Assert.assertNotNull("Xtra Card Cipher Text is returned in the response", xtraCardCipherTxt);
            });
        }
    }
}

