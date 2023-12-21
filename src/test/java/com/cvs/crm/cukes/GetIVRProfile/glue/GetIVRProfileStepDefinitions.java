package com.cvs.crm.cukes.GetIVRProfile.glue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.service.GetIVRProfileService;
import com.cvs.crm.util.TestDataUtil;

import cucumber.api.java8.En;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetIVRProfileStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    GetIVRProfileService getCustomerProfileService;
    @Autowired
    TestDataUtil testDataUtil;

    String requestParam = "";
    Integer xtraCardNbr = null;
    JSONArray jsonArray;
    JSONObject jsonObject;

    public GetIVRProfileStepDefinitions() {
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

            Given("I filter for {string} in the request", (String filter) -> {
                requestParam = filter;
            });

            And("I filter for {string} in the request with {string}", (String filter, String criteria) -> {
                requestParam = filter + "," + criteria;
            });

            When("I use the get customer IVR API for channel {string}", (String channel) -> {
                request.setVersion("1.2");
                request.setChannel(channel);
                getCustomerProfileService.getServiceResponse(request, requestParam);
            });

            Then("I receive a response with success status", () -> {
                getCustomerProfileService.getServiceResponse().then().log().all().statusCode(200);
            });

            And("The response should return {string} nodes", (String respNode) -> {
                String response = getCustomerProfileService.getServiceResponse().jsonPath().getString("cusInfResp");
                String[] nodes = respNode.split(",");
                for (String node : nodes) {
                    Assert.assertTrue("The response contain " + node + " node.", response.contains(node.trim()));
                }
            });

            And("The response should return tables data with information of the customer in {string} node",
                    (String respNode) -> {
                        Response response = getCustomerProfileService.getServiceResponse();
                        String[] nodes = respNode.split(",");
                        int seqNum = 0;

                        for (String node : nodes) {
                            node = node.trim();
                            Assert.assertTrue("The response contain " + node + " node.",
                                    response.jsonPath().getString("cusInfResp").contains(node));
                            switch (node) {
                                case "customer":
                                    validateCustomerInformation(response, node, 0, 0, seqNum);
                                    break;
                                case "customer_email":
                                    validateCustomerEmail(response, node, 1, 0, seqNum);
                                    break;
                                case "customer_phone":
                                    validateCustomerPhoneNumber(response, node, 2, 0, seqNum);
                                    break;

                                case "customer_address":
                                    validateCustomerAddress(response, node, 3, 0, seqNum);
                                    break;
                            }
                        }
                    });

            When("I change my date of birth to {string}", (String dob) -> {
                getCustomerProfileService.updateCustomerDOB(dob, xtraCardNbr);
            });

            Then("The response should return updated date of birth of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerInformation(response, respNode, 0, 0, 0);
            });

            When("I change my primary email address to {string}", (String email) -> {
                getCustomerProfileService.updateCustomerEmail(email, xtraCardNbr);
            });

            Then("The response should return updated email address of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerEmail(response, respNode, 1, 0, 0);
            });

            When("I change my primary phone number to {string}", (String phone) -> {
                getCustomerProfileService.updateCustomerPhone(phone, xtraCardNbr);
            });

            Then("The response should return updated phone number of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerPhoneNumber(response, respNode, 2, 0, 0);
            });

            When("I change my primary residence address to {string}", (String addr2Txt) -> {
                getCustomerProfileService.updateCustomerAddress(addr2Txt, xtraCardNbr);
            });

            Then("The response should return updated address of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerAddress(response, respNode, 3, 0, 0);
            });

            And("I added my secondary residence address", () -> {
                getCustomerProfileService.addCustomerAddress(xtraCardNbr);
            });

            Then("The response should return secondary address of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerAddress(response, respNode, 3, 0, 1);
            });

            When("I removed my secondary residence address", () -> {
                getCustomerProfileService.deleteCustomerAddress(xtraCardNbr, 2);
            });

            Then("The response should return only primary address of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerAddress(response, respNode, 3, 0, 0);
            });

            And("I added my secondary phone number", () -> {
                getCustomerProfileService.addCustomerPhoneNumber(xtraCardNbr);
            });

            Then("The response should return secondary phone numbers of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerPhoneNumber(response, respNode, 2, 0, 1);
            });

            When("I removed my secondary phone number", () -> {
                getCustomerProfileService.deleteCustomerPhoneNumber(xtraCardNbr, 2);
            });

            Then("The response should return only primary phone numbers of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerPhoneNumber(response, respNode, 2, 0, 0);
            });

            And("I added my secondary email address", () -> {
                getCustomerProfileService.addCustomerEmailAddress(xtraCardNbr);
            });

            Then("The response should return secondary email address of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerEmail(response, respNode, 1, 0, 1);
            });

            And("I removed my secondary email address", () -> {
                getCustomerProfileService.deleteCustomerEmailAddress(xtraCardNbr, 2);
            });

            And("The response should return only primary email address of the customer in {string} node", (String respNode) -> {
                Response response = getCustomerProfileService.getServiceResponse();
                validateCustomerEmail(response, respNode, 1, 0, 0);
            });
        }
    }

    private void validateCustomerInformation(Response response, String node, int tableIndex, int rowIndex, int seqNum) {
        String tableName = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].tableName");
        Assert.assertEquals("Table Name is correct.", node, tableName);

        List<Customer> customerList = getCustomerProfileService.getCustomerInformation(xtraCardNbr);
        Customer customer = customerList.get(0);

        String lastName = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].last_name");
        Assert.assertEquals("Last Name is correct.", customer.getLastName(), lastName);

        String firstName = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].first_name");
        Assert.assertEquals("First Name is correct.", customer.getFirstName(), firstName);

        String dob = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].birth_dt");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String strDate = dateFormat.format(customer.getBirthDt());

        Assert.assertEquals("Birth Date is correct.", strDate, dob);
    }

    private void validateCustomerEmail(Response response, String node, int tableIndex, int rowIndex, int seqNum) {
        String tableName = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].tableName");
        Assert.assertEquals("Table Name is correct.", node, tableName);

        List<CustomerEmail> customerEmailList = getCustomerProfileService.getCustomerEmail(xtraCardNbr);
        CustomerEmail customerEmail = customerEmailList.get(seqNum);

        String emailAddress = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].email_addr_txt");
        Assert.assertEquals("Email Address is correct.",
                customerEmail.getEmailAddrTxt(), emailAddress);
    }

    private void validateCustomerPhoneNumber(Response response, String node, int tableIndex, int rowIndex, int seqNum) {
        String tableName = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].tableName");
        Assert.assertEquals("Table Name is correct.", node, tableName);

        List<CustomerPhone> customerPhoneList = getCustomerProfileService.getCustomerPhoneNumber(xtraCardNbr);
        CustomerPhone customerPhone = customerPhoneList.get(seqNum);

        String phoneAreaCode = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].phone_area_cd_nbr");
        Assert.assertTrue("Current phoneAreaCode: " + customerPhone.getPhoneAreaCdNbr() +
                        "\n Expected phoneAreaCode: " + phoneAreaCode,
                Integer.valueOf(customerPhone.getPhoneAreaCdNbr()).equals(Integer.valueOf(phoneAreaCode)));

        String phonePfxNbr = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].phone_pfx_nbr");
        Assert.assertTrue("Current PhonePfxNbr: " + customerPhone.getPhonePfxNbr() +
                "\n Expected PhonePxNbr: " + phonePfxNbr,
                Integer.valueOf(customerPhone.getPhonePfxNbr()).equals(Integer.valueOf(phonePfxNbr)));

        String phoneSfxNbr = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].phone_sfx_nbr");
        Assert.assertTrue("Current PhoneSfxNbr: " + customerPhone.getPhoneSfxNbr() +
                        "\n Expected PhoneSfxNbr: " + phoneSfxNbr,
                Integer.valueOf(customerPhone.getPhoneSfxNbr()).equals(Integer.valueOf(phoneSfxNbr)));

    }

    private void validateCustomerAddress(Response response, String node, int tableIndex, int rowIndex, int seqNum) {
        String tableName = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].tableName");
        Assert.assertEquals("Table Name is correct.", node, tableName);

        List<CustomerAddress> customerAddressList = getCustomerProfileService.getCustomerAddress(xtraCardNbr);
        CustomerAddress customerAddress = customerAddressList.get(seqNum);

        String address1 = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].addr1_txt");
        Assert.assertEquals("Address1 is correct.", customerAddress.getAddr1Txt(), address1);

        String address2 = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].addr2_txt");
        Assert.assertEquals("Address2 is correct.", customerAddress.getAddr2Txt(), address2);

        String city = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].city_name");
        Assert.assertEquals("City is correct.", customerAddress.getCityName(), city);

        String zipCode = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].zip_cd");
        Assert.assertEquals("ZipCode is correct.", customerAddress.getZipCd(), zipCode);

        String state = response.jsonPath()
                .getString("cusInfResp.tables[" + tableIndex + "].row[" + rowIndex + "].st_cd");
        Assert.assertEquals("State is correct.", customerAddress.getStCd(), state);
    }
}

