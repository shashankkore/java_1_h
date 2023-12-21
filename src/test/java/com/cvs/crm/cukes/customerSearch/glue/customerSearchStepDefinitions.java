package com.cvs.crm.cukes.customerSearch.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.CustSearchRequest;
import com.cvs.crm.model.response.customerSearchResponse;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.service.CustSearchService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.JsonUtil;
import com.cvs.crm.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class customerSearchStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    CustSearchService custSearchService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    JsonUtil jsonUtil;

    ObjectMapper mapper = new ObjectMapper();
    JSONArray inputDataArray = null;

    @Autowired
    CustomerDao customerDao;
    @Autowired
    TestDataUtil testDataUtil;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String custSearch_lookUp = null;
    String lookup_type = null;

    customerSearchResponse[] customerSearchResponse_Actual;
    List<customerSearchResponse> customerSearchResponseList_Actual = new ArrayList<>();
    CustSearchRequest custSearchRequest = new CustSearchRequest();

    public customerSearchStepDefinitions() {

        Given("{string}", (String scenario) -> {
        });

        Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
            jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
        });

        /**
         * pass value = 'null' or 'not null'
         */
        And("My user has cardLastScanDt as {string}", (String value) -> {
            jsonObject = testDataUtil.extractTestData(jsonArray);
            testDataUtil.update_xtraCard(jsonObject, "CARD_LAST_SCAN_DT", value);
        });

        And("I have no {string} set for my user {string}", (String tableName, String xtraCard) -> {
            String filePath = "createExtraCard";
            jsonObject = testDataUtil.extractTestData(testDataUtil.extractTestData(filePath, xtraCard));

            int cust_id = Integer.parseInt(jsonObject.get("CUST_ID").toString());
            List<Integer> custIdList = Arrays.asList(cust_id);
            customerDao.deleteCustomer_NEW(custIdList, tableName);
        });

        And("My user with xtraCard {string} has multiple unique {string} records", (String xtraCard, String tableName) -> {
            String filePath = "createExtraCard";
            jsonObject = testDataUtil.extractTestData(testDataUtil.extractTestData(filePath, xtraCard));
            if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_ADDRESS")) {
                testDataUtil.addCustomerData(jsonObject, tableName, "Address_2", "2");
                testDataUtil.addCustomerData(jsonObject, tableName, "Address_3", "3");
                testDataUtil.addCustomerData(jsonObject, tableName, "Address_4", "4");
            } else if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_EMAIL")) {
                testDataUtil.addCustomerData(jsonObject, tableName, "Email_2", "2");
                testDataUtil.addCustomerData(jsonObject, tableName, "Email_3", "3");
                testDataUtil.addCustomerData(jsonObject, tableName, "Email_4", "4");
            } else if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_PHONE")) {
                testDataUtil.addCustomerData(jsonObject, tableName, "Phone_2", "2");
                testDataUtil.addCustomerData(jsonObject, tableName, "Phone_3", "3");
                testDataUtil.addCustomerData(jsonObject, tableName, "Phone_4", "4");
            } else {
                throw new IllegalArgumentException();
            }
        });

        And("I have multiple users with xtraCards {string} having same {string} records", (String xtraCards, String tableName) -> {
            String filePath = "createExtraCard";
            List<String> xtraCards_list = Arrays.asList(xtraCards.split("\\s*,\\s*"));
            for (String xtraCard : xtraCards_list) {
                jsonObject = testDataUtil.extractTestData(testDataUtil.extractTestData(filePath, xtraCard));
                if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_ADDRESS")) {
                    testDataUtil.addCustomerData(jsonObject, tableName, "Address_2", "2");
                    testDataUtil.addCustomerData(jsonObject, tableName, "Address_3", "3");
                    testDataUtil.addCustomerData(jsonObject, tableName, "Address_4", "4");
                } else if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_EMAIL")) {
                    testDataUtil.addCustomerData(jsonObject, tableName, "Email_2", "2");
                    testDataUtil.addCustomerData(jsonObject, tableName, "Email_3", "3");
                    testDataUtil.addCustomerData(jsonObject, tableName, "Email_4", "4");
                } else if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_PHONE")) {
                    testDataUtil.addCustomerData(jsonObject, tableName, "Phone_2", "2");
                    testDataUtil.addCustomerData(jsonObject, tableName, "Phone_3", "3");
                    testDataUtil.addCustomerData(jsonObject, tableName, "Phone_4", "4");
                } else {
                    throw new IllegalArgumentException();
                }
            }
        });

        And("My user with xtraCard {string} has duplicate {string} records", (String xtraCard, String tableName) -> {
            String filePath = "createExtraCard";
            jsonObject = testDataUtil.extractTestData(testDataUtil.extractTestData(filePath, xtraCard));
            if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_ADDRESS")) {
                testDataUtil.addCustomerData(jsonObject, tableName, "Address_2", "2");
                testDataUtil.addCustomerData(jsonObject, tableName, "Address_2", "3");
                testDataUtil.addCustomerData(jsonObject, tableName, "Address_2", "4");
            } else if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_EMAIL")) {
                testDataUtil.addCustomerData(jsonObject, tableName, "Email_2", "2");
                testDataUtil.addCustomerData(jsonObject, tableName, "Email_2", "3");
                testDataUtil.addCustomerData(jsonObject, tableName, "Email_2", "4");
            } else if (tableName.toUpperCase().equalsIgnoreCase("CUSTOMER_PHONE")) {
                testDataUtil.addCustomerData(jsonObject, tableName, "Phone_2", "2");
                testDataUtil.addCustomerData(jsonObject, tableName, "Phone_2", "3");
                testDataUtil.addCustomerData(jsonObject, tableName, "Phone_2", "4");
            } else {
                throw new IllegalArgumentException();
            }
        });

        When("I search for customer using {string} at {string}", (String cardType, String channel) -> {
            jsonObject = testDataUtil.extractTestData(jsonArray);
            custSearchRequest.setXtraCardNbr(Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString()));
            custSearchRequest.setEncodedXtraCardNbr(Long.parseLong(jsonObject.get("ENCODED_XTRA_CARD_NBR").toString()));

            JSONArray jsonArray_Customer = (JSONArray) jsonObject.get("CUSTOMER");
            JSONObject jsonObject_Customer = testDataUtil.extractTestData(jsonArray_Customer);
            custSearchRequest.setFirstName(jsonObject_Customer.get("FIRST_NAME").toString());
            custSearchRequest.setLastName(jsonObject_Customer.get("LAST_NAME").toString());

            JSONArray jsonArray_CustomerAddress = (JSONArray) jsonObject.get("CUSTOMER_ADDRESS");
            JSONObject jsonObject_CustomerAddress = testDataUtil.extractTestData(jsonArray_CustomerAddress);
            custSearchRequest.setZipCd(jsonObject_CustomerAddress.get("ZIP_CD").toString());

            JSONArray jsonArray_CustomerEmail = (JSONArray) jsonObject.get("CUSTOMER_EMAIL");
            JSONObject jsonObject_CustomerEmail = testDataUtil.extractTestData(jsonArray_CustomerEmail);
            custSearchRequest.setEmailAddrTxt(jsonObject_CustomerEmail.get("EMAIL_ADDR_TXT").toString());

            JSONArray jsonArray_CustomerPhone = (JSONArray) jsonObject.get("CUSTOMER_PHONE");
            JSONObject jsonObject_CustomerPhone = testDataUtil.extractTestData(jsonArray_CustomerPhone);
            custSearchRequest.setPhoneAreaCdNbr(Integer.parseInt(jsonObject_CustomerPhone.get("PHONE_AREA_CD_NBR").toString()));
            custSearchRequest.setPhonePfxNbr(Integer.parseInt(jsonObject_CustomerPhone.get("PHONE_PFX_NBR").toString()));
            custSearchRequest.setPhoneSfxNbr(Integer.parseInt(jsonObject_CustomerPhone.get("PHONE_SFX_NBR").toString()));

            JSONArray jsonArray_MaskedXtraCard = (JSONArray) jsonObject.get("MASKED_XTRA_CARD");
            JSONObject jsonObject_MaskedXtraCard = testDataUtil.extractTestData(jsonArray_MaskedXtraCard);
            custSearchRequest.setMaskedXtraCard(jsonObject_MaskedXtraCard.get("XTRA_CARD_SHA2_NBR").toString());
            custSearchRequest.setVersion("1.2");
            custSearchRequest.setChannel(channel);
            custSearchRequest.setTypeCd(custSearchService.set_typeCd(cardType));

            if (custSearchRequest.getTypeCd().equalsIgnoreCase("0002")) {
                custSearchRequest.setCardNbr(custSearchRequest.getXtraCardNbr().toString());
            } else if (custSearchRequest.getTypeCd().equalsIgnoreCase("0004")) {
                custSearchRequest.setCardNbr(custSearchRequest.getEncodedXtraCardNbr().toString());
            } else if (custSearchRequest.getTypeCd().equalsIgnoreCase("0006")) {
                custSearchRequest.setCardNbr(custSearchRequest.getMaskedXtraCard());
            }

            String deviceUniqId = "1234474saf";
            String uniq_User_Id = RandomStringUtils.random(10, true, true);

            custSearchRequest.setDeviceUniqId(deviceUniqId);
            custSearchRequest.setUniqUserId(uniq_User_Id);
            custSearchRequest.setCardLastScanDt(testDataUtil.getData_xtraCard(jsonObject.get("XTRA_CARD_NBR").toString(), "CARD_LAST_SCAN_DT"));

            int regNbr = 2;
            String regTypeCd = "HH";
            int storeNbr = 90067;
            int txnNbr = 4512;
            int cashierNbr = 1;
        });

        And("I use different {string} record with xtraCards {string}", (String tableName, String xtraCard) -> {
            String filePath = "createExtraCard";
            jsonObject = testDataUtil.extractTestData(testDataUtil.extractTestData(filePath, xtraCard));
            if (tableName.equalsIgnoreCase("xtra_card")) {
//                List<XtraCard> xtraCardNbr = testDataUtil.getData_xtraCard(jsonObject.get("XTRA_CARD_NBR").toString(), tableName, "XTRA_CARD_NBR");

                String xtraCardNbr = testDataUtil.getData_xtraCard(jsonObject.get("XTRA_CARD_NBR").toString(),"XTRA_CARD_NBR");
                String encodedXtraCardNbr = testDataUtil.getData_xtraCard(jsonObject.get("XTRA_CARD_NBR").toString(),"ENCODED_XTRA_CARD_NBR");

                custSearchRequest.setXtraCardNbr(Integer.parseInt(xtraCardNbr));
                custSearchRequest.setEncodedXtraCardNbr(Long.parseLong(encodedXtraCardNbr));
            } else if (tableName.equalsIgnoreCase("customer")) {
                List<Customer> customer = testDataUtil.getData_customer(jsonObject.get("XTRA_CARD_NBR").toString(), "FIRST_NAME, LAST_NAME");
                custSearchRequest.setFirstName(customer.get(0).getFirstName());
                custSearchRequest.setLastName(customer.get(0).getLastName());
            } else {
                throw new IllegalStateException("Unexpected tableName : " + tableName);
            }
        });

        And("I use different {string} record with preferred sequence number {string} to search", (String tableName, String prefSeqNbr) -> {
            String xtraCardNbr = jsonObject.get("XTRA_CARD_NBR").toString();
            if (tableName.equalsIgnoreCase("customer_email")) {
                List<CustomerEmail> customerEmail = testDataUtil.getData_customerEmail(xtraCardNbr, "EMAIL_ADDR_TXT");
                custSearchRequest.setEmailAddrTxt(customerEmail.get(Integer.parseInt(prefSeqNbr) - 1).getEmailAddrTxt());
            } else if (tableName.equalsIgnoreCase("customer_phone")) {
                List<CustomerPhone> customerPhone = testDataUtil.getData_customerPhone(xtraCardNbr,"PHONE_AREA_CD_NBR, PHONE_PFX_NBR, PHONE_SFX_NBR");
                custSearchRequest.setPhoneAreaCdNbr(customerPhone.get(Integer.parseInt(prefSeqNbr) - 1).getPhoneAreaCdNbr());
                custSearchRequest.setPhonePfxNbr(customerPhone.get(Integer.parseInt(prefSeqNbr) - 1).getPhonePfxNbr());
                custSearchRequest.setPhoneSfxNbr(customerPhone.get(Integer.parseInt(prefSeqNbr) - 1).getPhoneSfxNbr());
            } else if (tableName.equalsIgnoreCase("customer_address")) {
                List<CustomerAddress> customerAddressList = testDataUtil.getData_customerAddress(xtraCardNbr, "ADDR1_TXT, ADDR2_TXT, CITY_NAME, ST_CD, ZIP_CD");
                custSearchRequest.setAddr1_txt(customerAddressList.get(Integer.parseInt(prefSeqNbr) - 1).getAddr1Txt());
                custSearchRequest.setAddr2_txt(customerAddressList.get(Integer.parseInt(prefSeqNbr) - 1).getAddr2Txt());
                custSearchRequest.setCity(customerAddressList.get(Integer.parseInt(prefSeqNbr) - 1).getCityName());
                custSearchRequest.setStCd(customerAddressList.get(Integer.parseInt(prefSeqNbr) - 1).getStCd());
                custSearchRequest.setZipCd(customerAddressList.get(Integer.parseInt(prefSeqNbr) - 1).getZipCd());
            } else {
                throw new IllegalStateException("Unexpected tableName : " + tableName);
            }
        });

        And("I use {string} look-up for Customer Search", (String lookUp) -> {
            custSearch_lookUp = lookUp;
            if (lookUp.toLowerCase().equalsIgnoreCase("close_match_v1") ||
                    lookUp.toLowerCase().equalsIgnoreCase("close_match_v2")) {
                lookup_type = "close_match";
            } else {
                lookup_type = lookUp.toLowerCase();
            }
            custSearchRequest.setSearchType(lookup_type);
            custSearchRequest.setSearchTypeFieldNames("");
        });

        And("I look-up with {string}", (String fieldNames) -> {
            custSearchRequest.setSearchTypeFieldNames(fieldNames);
        });

        Then("I get response from custSearch API", () -> {
            customerSearchResponse_Actual = mapper.readValue(custSearchService.custSearchResponse(custSearchRequest, custSearch_lookUp).asString(), customerSearchResponse[].class);
            customerSearchResponseList_Actual.addAll(Arrays.asList(customerSearchResponse_Actual));
        });

        Then("API returns a response with status code as {int}", (Integer statusCd) -> {
            custSearchService.getServiceResponse().then().statusCode(statusCd);
        });

        When("I get error response with statusCd {int} from custSearch API", (Integer statusCd) -> {
            customerSearchResponse customerSearchResponse =
            custSearchService.custSearchResponse(custSearchRequest, custSearch_lookUp).as(customerSearchResponse.class);
            String errorDesc = "";
            if(statusCd == 13) {
                errorDesc = "Exact customer match not found";
            } else if (statusCd == 15) {
                errorDesc = "Multiple customer matches found";
            } else {
                throw new IllegalStateException("Unexpected statusCd : " + statusCd);
            }

                Assert.assertTrue("Current StatusCd: " + customerSearchResponse.getStatusCd() +
                                "\n Expected StatusCd: " + statusCd.toString(),
                        customerSearchResponse.getStatusCd().equalsIgnoreCase(statusCd.toString()));
                Assert.assertTrue("Current ErrorDesc: " + customerSearchResponse.getErrorDesc() +
                                "\n Expected ErrorDesc: " + errorDesc,
                        customerSearchResponse.getErrorDesc().equalsIgnoreCase(errorDesc));
        });

        Then("The response should have all mandatory fields for {string} look-up", (String lookUp) -> {
            custSearchService.verifyResponseFields(custSearchRequest, customerSearchResponseList_Actual, custSearch_lookUp);
        });

        Then("The response should have expected data for all mandatory fields", () -> {
            custSearchService.verifyResponseField_values(customerSearchResponseList_Actual, custSearchRequest, custSearch_lookUp);
        });
    }

    /**
     *  Used only for filePath = "./src/test/resources/testdata/searchCustomer.json"
     *  for few of the test cases
     * @param dataObjectName
     * @return
     */
    public JSONArray extractDataAndSetupTestData_searchCustomer(String dataObjectName) {
        String filePath = "./src/test/resources/testdata/searchCustomer.json";
//        String testEnvironment = this.environment.getActiveProfiles()[0];
        inputDataArray = jsonUtil.readJsonFileGetArray(filePath, dataObjectName);
        for (int i = 0; i < inputDataArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) inputDataArray.get(i);
            try {
                custSearchService.createTestDataForCustSearchFromJson(jsonObject);
            } catch (Exception e) {
                e.getMessage();
                log.error("Unable to create test data for " + dataObjectName);
            }
        }
        return inputDataArray;
    }
}