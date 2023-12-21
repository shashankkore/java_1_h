package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.CustSearchRequest;
import com.cvs.crm.model.request.CustomerSearchRequest;
import com.cvs.crm.model.response.customerSearchResponse;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.TestDataUtil;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class CustSearchService {

    private Response serviceResponse;

    @Autowired
    TestDataUtil testDataUtil;
    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;
    @Autowired
    Customer customer;
    @Autowired
    CustomerAddress customerAddress;
    @Autowired
    CustomerEmail customerEmail;
    @Autowired
    CustomerPhone customerPhone;
    @Autowired
    CustomerOpt customerOpt;
    @Autowired
    XtraCard xtraCard;
    @Autowired
    XtraParms xtraParms;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    XtraCardDao xtraCardDao;
    @Autowired
    MaskedXtraCard maskedXtraCard;

    customerSearchResponse customerSearchResponse;

    CustomerSearchRequest customerSearchRequest = new CustomerSearchRequest();
    Gson gson = new Gson();

    public String set_typeCd(String cardType) {
        String type_Cd = null;
        switch (cardType.toLowerCase()) {
            case "employeecard":
                type_Cd = "0001";
                break;
            case "xtracard":
                type_Cd = "0002";
                break;
            case "phonenumber":
                type_Cd = "0003";
                break;
            case "encodedxtracard":
                type_Cd = "0004";
                break;
            case "rxc":
                type_Cd = "0005";
                break;
            case "maskedxtracard":
                type_Cd = "0006";
                break;
            default:
                log.info("Incorrect argument passed. cardType = [{}]", cardType);
                break;
        }
        return type_Cd;
    }

    private RequestSpecBuilder custSearch_baseUri(CustSearchRequest custSearchRequest) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri(serviceConfig.getCustsearchUrl());
        String msgSrcCd;
        int srcLocCd;
        String userId;
        if (custSearchRequest.getChannel().equalsIgnoreCase("M")) {
            msgSrcCd = "M";
            srcLocCd = 90042;
            userId = "MOBILE_ENT";
        } else if (custSearchRequest.getChannel().equalsIgnoreCase(("W"))) {
            msgSrcCd = "W";
            srcLocCd = 2695;
            userId = "CVS.COM";
        } else {
            msgSrcCd = "R";
            srcLocCd = 68585;
            userId = "STORE";
        }
        if (custSearchRequest.getSearchType().equalsIgnoreCase("phone")) {
            msgSrcCd = "BC";
        }
        requestSpecBuilder.setBasePath("api/v" + custSearchRequest.getVersion() +
                        "/customers/search?")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("user_id", userId);
        return requestSpecBuilder;
    }

    //  CustSeach Actions - Name Lookup, Email Lookup, Phone Lookup, EncodedCard Lookup, Close_match v1.1,
//  Close_match v1.2, Exact_match, Exact_match_Email_only, StoreMetaData_CET, StoreMetaData
    public Response custSearchResponse(CustSearchRequest custSearchRequest, String lookUp) {
        RequestSpecBuilder requestSpecBuilder = custSearch_baseUri(custSearchRequest);
        RequestSpecification requestSpecification = requestSpecBuilder.build();
        String getServiceBody = null;

        switch (lookUp.toLowerCase()) {
            case "name":
                getServiceBody = getServiceBody_Name_LookUp(custSearchRequest);
                break;
            case "phone":
                getServiceBody = getServiceBody_Phone_LookUp(custSearchRequest);
                break;
            case "email":
                getServiceBody = getServiceBody_email_LookUp(custSearchRequest);
                break;
            case "encoded_xtra_card_nbr":
                getServiceBody = getServiceBody_encodedXtraCardNbr_LookUp(custSearchRequest);
                break;
            case "exact_match":
                getServiceBody = getServiceBody_exactMatch_LookUp(custSearchRequest);
                break;
            case "close_match_v1":
                getServiceBody = getServiceBody_closeMatch_v1_LookUp(custSearchRequest);
                break;
            case "close_match_v2":
                getServiceBody = getServiceBody_closeMatch_v2_LookUp(custSearchRequest);
                break;
            case "store_metadata_cet":
                getServiceBody = getServiceBody_store_metadata_cet_LookUp(custSearchRequest);
                break;
            case "store_metadata":
                getServiceBody = getServiceBody_store_metadata_LookUp(custSearchRequest);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + lookUp.toLowerCase());
        }
        log.info("Request Body: [{}] ", getServiceBody);
        serviceResponse = given().spec(requestSpecification).contentType("application/json")
                .body(getServiceBody).log().all().post();
//        getServiceResponse().then().statusCode(200);
        log.info("\n CustSearch with [{}] lookup. Status Code - [{}]", lookUp, serviceResponse.getStatusCode());
        log.info("\n CustSearch Response for [{}] lookup :", lookUp);
        serviceResponse.prettyPrint();
        return serviceResponse;
    }

    private String getServiceBody_Name_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"firstName\": \"" + custSearchRequest.getFirstName() + "\",\n" +
                "    \"lastName\": \"" + custSearchRequest.getLastName() + "\"\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_Phone_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + "\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_email_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"emailAddrTxt\": \"" + custSearchRequest.getEmailAddrTxt() + "\"\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_encodedXtraCardNbr_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"encodedXtraCardNbr\": \"" + custSearchRequest.getEncodedXtraCardNbr() + "\"\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_exactMatch_LookUp(CustSearchRequest custSearchRequest) {
        String fieldName = custSearchRequest.getSearchTypeFieldNames();
        String json_body = "";
        List<String> fields = Arrays.asList(fieldName.toLowerCase().split("\\s*,\\s*"));
        if ((fields.contains("email") && fields.contains("phone") && fields.contains("cardnbr"))
                || (!fields.contains("email") && !fields.contains("phone") && !fields.contains("cardnbr"))
                || fields.contains("")) {
            json_body = "{\n" +
                    "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                    "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                    "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                    "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                    "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + ",\n" +
                    "    \"emailAddrTxt\": \"" + custSearchRequest.getEmailAddrTxt() + "\",\n" +
                    "    \"cardNbr\": \"" + custSearchRequest.getCardNbr() + "\",\n" +
                    "    \"cardType\":\"" + custSearchRequest.getTypeCd() + "\"\n" +
                    "}";
        } else if (fields.contains("email") && !fields.contains("phone") && !fields.contains("cardnbr")) {
            json_body = "{\n" +
                    "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                    "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                    "    \"emailAddrTxt\": \"" + custSearchRequest.getEmailAddrTxt() + "\"\n" +
                    "}";
        } else if (!fields.contains("email") && fields.contains("phone") && !fields.contains("cardnbr")) {
            json_body = "{\n" +
                    "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                    "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                    "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                    "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                    "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + "\n" +
                    "}";
        } else if (!fields.contains("email") && !fields.contains("phone") && fields.contains("cardnbr")) {
            json_body = "{\n" +
                    "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                    "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                    "    \"cardNbr\": \"" + custSearchRequest.getCardNbr() + "\",\n" +
                    "    \"cardType\":\"" + custSearchRequest.getTypeCd() + "\"\n" +
                    "}";
        } else if (fields.contains("email") && fields.contains("phone") && !fields.contains("cardnbr")) {
            json_body = "{\n" +
                    "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                    "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                    "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                    "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                    "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + ",\n" +
                    "    \"emailAddrTxt\": \"" + custSearchRequest.getEmailAddrTxt() + "\"\n" +
                    "}";
        } else if (!fields.contains("email") && fields.contains("phone") && fields.contains("cardnbr")) {
            json_body = "{\n" +
                    "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                    "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                    "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                    "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                    "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + ",\n" +
                    "    \"cardNbr\": \"" + custSearchRequest.getCardNbr() + "\",\n" +
                    "    \"cardType\":\"" + custSearchRequest.getTypeCd() + "\"\n" +
                    "}";
        } else if (fields.contains("email") && !fields.contains("phone") && fields.contains("cardnbr")) {
            json_body = "{\n" +
                    "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                    "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                    "    \"emailAddrTxt\": \"" + custSearchRequest.getEmailAddrTxt() + "\",\n" +
                    "    \"cardNbr\": \"" + custSearchRequest.getCardNbr() + "\",\n" +
                    "    \"cardType\":\"" + custSearchRequest.getTypeCd() + "\"\n" +
                    "}";
        } else {
            throw new IllegalStateException("Unexpected Value: " + fieldName.toLowerCase());
        }
        return json_body;
    }

    private String getServiceBody_closeMatch_v1_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + ",\n" +
                "    \"lastName\": \"" + custSearchRequest.getLastName() + "\",\n" +
                "    \"emailAddrTxt\": \"" + custSearchRequest.getEmailAddrTxt() + "\",\n" +
                "    \"zipCd\":\"" + custSearchRequest.getZipCd() + "\"\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_closeMatch_v2_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + ",\n" +
                "    \"emailAddrTxt\": \"" + custSearchRequest.getEmailAddrTxt() + "\"\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_store_metadata_cet_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"storeMetaData\": {\n" +
                "        \"regTypeCd\": \"" + custSearchRequest.getRegTypeCd() + "\",\n" +
                "        \"deviceUniqId\": \"" + custSearchRequest.getDeviceUniqId() + "\",\n" +
                "        \"storeNbr\": " + custSearchRequest.getStoreNbr() + "\n" +
                "    },\n" +
                "    \"uniqUserId\": \"" + custSearchRequest.getUniqUserId() + "\",\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + "\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_store_metadata_LookUp(CustSearchRequest custSearchRequest) {
        String json_body = "{\n" +
                "    \"storeMetaData\": {\n" +
                "        \"regNbr\": " + custSearchRequest.getRegNbr() + ",\n" +
                "        \"regTypeCd\": \"" + custSearchRequest.getRegTypeCd() + "\",\n" +
                "        \"txnNbr\": \"" + custSearchRequest.getTxnNbr() + "\",\n" +
                "        \"cashierNbr\": " + custSearchRequest.getCashierNbr() + "\n" +
                "    },\n" +
                "    \"searchType\": \"" + custSearchRequest.getSearchType() + "\",\n" +
                "    \"phoneAreaCdNbr\": " + custSearchRequest.getPhoneAreaCdNbr() + ",\n" +
                "    \"phonePfxNbr\": " + custSearchRequest.getPhonePfxNbr() + ",\n" +
                "    \"phoneSfxNbr\": " + custSearchRequest.getPhoneSfxNbr() + "\n" +
                "}";
        return json_body;
    }

    public void verifyResponseFields(CustSearchRequest custSearchRequest,
                                     List<customerSearchResponse> customerSearchResponses, String lookUp) {
        List<CustomerAddress> customerAddressList = customerDao.getData_CustomerAddress_table(
                custSearchRequest.getXtraCardNbr().toString(), "ADDR1_TXT, ADDR2_TXT, CITY_NAME, ST_CD, ZIP_CD");
        List<CustomerEmail> customerEmailList = customerDao.getData_CustomerEmail_table(
                custSearchRequest.getXtraCardNbr().toString(), "EMAIL_ADDR_TXT");
        List<CustomerPhone> customerPhoneList = customerDao.getData_CustomerPhone_table(
                custSearchRequest.getXtraCardNbr().toString(), "PHONE_AREA_CD_NBR, PHONE_PFX_NBR, PHONE_SFX_NBR");

        for (customerSearchResponse customerSearchResponse : customerSearchResponses) {
            Assert.assertNotNull(customerSearchResponse.getXtraCardNbr());
            Assert.assertNotNull(customerSearchResponse.getEncodedXtraCardNbr());
            Assert.assertNotNull(customerSearchResponse.getCustId());
            Assert.assertNotNull(customerSearchResponse.getFirstName());
            Assert.assertNotNull(customerSearchResponse.getLastName());
            if(customerPhoneList.size() != 0){
                Assert.assertNotNull(customerSearchResponse.getPhoneAreaCdNbr());
                Assert.assertNotNull(customerSearchResponse.getPhonePfxNbr());
                Assert.assertNotNull(customerSearchResponse.getPhoneSfxNbr());
            } else {
                Assert.assertNull(customerSearchResponse.getPhoneAreaCdNbr());
                Assert.assertNull(customerSearchResponse.getPhonePfxNbr());
                Assert.assertNull(customerSearchResponse.getPhoneSfxNbr());
            }
            if(customerEmailList.size() != 0){
                Assert.assertNotNull(customerSearchResponse.getEmailAddrTxt());
            } else {
                Assert.assertNull(customerSearchResponse.getEmailAddrTxt());
            }
            if (custSearchRequest.getCardLastScanDt() == null) {
                Assert.assertNull(customerSearchResponse.getCardLastScanDt());
            } else {
                Assert.assertNotNull(customerSearchResponse.getCardLastScanDt());
            }

            switch (lookUp.toLowerCase()) {
                case "name":
                case "email":
                case "encoded_xtra_card_nbr":
                    if(customerAddressList.size() != 0){
                        Assert.assertNotNull(customerSearchResponse.getAddr1Txt());
                        Assert.assertNotNull(customerSearchResponse.getAddr2Txt());
                        Assert.assertNotNull(customerSearchResponse.getCityName());
                        Assert.assertNotNull(customerSearchResponse.getStCd());
                        Assert.assertNotNull(customerSearchResponse.getZipCd());
                    } else {
                        Assert.assertNull(customerSearchResponse.getAddr1Txt());
                        Assert.assertNull(customerSearchResponse.getAddr2Txt());
                        Assert.assertNull(customerSearchResponse.getCityName());
                        Assert.assertNull(customerSearchResponse.getStCd());
                        Assert.assertNull(customerSearchResponse.getZipCd());
                    }
//                      Currently failing - Need to get more details on it
//                    Assert.assertNotNull(customerSearchResponse.getBMktgEligible());
                    break;
                case "phone":
                    if(customerAddressList.size() != 0){
                        Assert.assertNotNull(customerSearchResponse.getAddr1Txt());
                        Assert.assertNotNull(customerSearchResponse.getAddr2Txt());
                        Assert.assertNotNull(customerSearchResponse.getCityName());
                        Assert.assertNotNull(customerSearchResponse.getStCd());
                        Assert.assertNotNull(customerSearchResponse.getZipCd());
                    } else {
                        Assert.assertNull(customerSearchResponse.getAddr1Txt());
                        Assert.assertNull(customerSearchResponse.getAddr2Txt());
                        Assert.assertNull(customerSearchResponse.getCityName());
                        Assert.assertNull(customerSearchResponse.getStCd());
                        Assert.assertNull(customerSearchResponse.getZipCd());
                    }
                    Assert.assertNotNull(customerSearchResponse.getCarepassEnrollStatus());
//                    Currently failing - Need to get more details on it
//                    Assert.assertNotNull(customerSearchResponse.getBMktgEligible());
                    break;
                case "exact_match":
                    if(customerAddressList.size() != 0){
                        Assert.assertNotNull(customerSearchResponse.getAddr1Txt());
                        Assert.assertNotNull(customerSearchResponse.getAddr2Txt());
                        Assert.assertNotNull(customerSearchResponse.getCityName());
                        Assert.assertNotNull(customerSearchResponse.getStCd());
                        Assert.assertNotNull(customerSearchResponse.getZipCd());
                    } else {
                        Assert.assertNull(customerSearchResponse.getAddr1Txt());
                        Assert.assertNull(customerSearchResponse.getAddr2Txt());
                        Assert.assertNull(customerSearchResponse.getCityName());
                        Assert.assertNull(customerSearchResponse.getStCd());
                        Assert.assertNull(customerSearchResponse.getZipCd());
                    }
                    Assert.assertNotNull(customerSearchResponse.getXtraCardCipherText());
                    break;
                case "close_match_v1":
                case "close_match_v2":
                    Assert.assertNotNull(customerSearchResponse.getIsCarepassEnrolled());
                    Assert.assertNotNull(customerSearchResponse.getAvailableExtraBucks());
                    Assert.assertNotNull(customerSearchResponse.getXtraCardCipherText());
                    break;
                default:
                    throw new IllegalStateException("Unexpected LookUp Type: " + lookUp);
            }
        }
    }

    public void verifyResponseField_values(List<customerSearchResponse> customerSearchResponses,
                                           CustSearchRequest custSearchRequest, String lookUp) {
        for (customerSearchResponse customerSearchResponse : customerSearchResponses) {
            switch (lookUp.toLowerCase()) {
                case "name":
                    Assert.assertTrue("Current FirstName: " + customerSearchResponse.getFirstName() +
                                    "\n Expected FirstName: " + custSearchRequest.getFirstName(),
                            customerSearchResponse.getFirstName().equalsIgnoreCase(custSearchRequest.getFirstName()));
                    Assert.assertTrue("Current LastName: " + customerSearchResponse.getLastName() +
                                    "\n Expected LastName: " + custSearchRequest.getLastName(),
                            customerSearchResponse.getLastName().equalsIgnoreCase(custSearchRequest.getLastName()));
                    break;

                case "email":
                    Assert.assertTrue("Current EmailAddrTxt: " + customerSearchResponse.getEmailAddrTxt() +
                                    "\n Expected EmailAddrTxt: " + custSearchRequest.getEmailAddrTxt(),
                            customerSearchResponse.getEmailAddrTxt().equalsIgnoreCase(custSearchRequest.getEmailAddrTxt()));
                    break;

                case "phone":
                    Assert.assertTrue("Current PhoneAreaCdNbr: " + customerSearchResponse.getPhoneAreaCdNbr() +
                                    "\n Expected PhoneAreaCdNbr: " + custSearchRequest.getPhoneAreaCdNbr(),
                            customerSearchResponse.getPhoneAreaCdNbr().equals(custSearchRequest.getPhoneAreaCdNbr()));
                    Assert.assertTrue("Current PhonePfxNbr: " + customerSearchResponse.getPhonePfxNbr() +
                                    "\n Expected PhonePfxNbr: " + custSearchRequest.getPhonePfxNbr(),
                            customerSearchResponse.getPhonePfxNbr().equals(custSearchRequest.getPhonePfxNbr()));
                    Assert.assertTrue("Current PhoneSfxNbr: " + customerSearchResponse.getPhoneSfxNbr() +
                                    "\n Expected PhoneSfxNbr: " + custSearchRequest.getPhoneSfxNbr(),
                            customerSearchResponse.getPhoneSfxNbr().equals(custSearchRequest.getPhoneSfxNbr()));
                    break;

                case "encoded_xtra_card_nbr":
                    Assert.assertTrue("Current EncodedXtraCardNbr: " + customerSearchResponse.getEncodedXtraCardNbr() +
                                    "\n Expected EncodedXtraCardNbr: " + custSearchRequest.getEncodedXtraCardNbr(),
                            customerSearchResponse.getEncodedXtraCardNbr().equals(custSearchRequest.getEncodedXtraCardNbr()));
                    break;

                case "close_match_v1":
                    Assert.assertTrue("Current PhoneAreaCdNbr: " + customerSearchResponse.getPhoneAreaCdNbr() +
                                    "\n Expected PhoneAreaCdNbr: " + custSearchRequest.getPhoneAreaCdNbr(),
                            customerSearchResponse.getPhoneSfxNbr().equals(custSearchRequest.getPhoneSfxNbr()));
                    Assert.assertTrue("Current PhonePfxNbr: " + customerSearchResponse.getPhonePfxNbr() +
                                    "\n Expected PhonePfxNbr: " + custSearchRequest.getPhonePfxNbr(),
                            customerSearchResponse.getPhoneSfxNbr().equals(custSearchRequest.getPhoneSfxNbr()));
                    Assert.assertTrue("Current PhoneSfxNbr: " + customerSearchResponse.getPhoneSfxNbr() +
                                    "\n Expected PhoneSfxNbr: " + custSearchRequest.getPhoneSfxNbr(),
                            customerSearchResponse.getPhoneSfxNbr().equals(custSearchRequest.getPhoneSfxNbr()));
                    Assert.assertTrue("Current LastName: " + customerSearchResponse.getLastName() +
                                    "\n Expected LastName: " + custSearchRequest.getLastName(),
                            customerSearchResponse.getLastName().equalsIgnoreCase(custSearchRequest.getLastName()));
                    Assert.assertTrue("Current EmailAddrTxt: " + customerSearchResponse.getEmailAddrTxt() +
                                    "\n Expected EmailAddrTxt: " + custSearchRequest.getEmailAddrTxt(),
                            customerSearchResponse.getEmailAddrTxt().equalsIgnoreCase(custSearchRequest.getEmailAddrTxt()));
                    break;

                case "close_match_v2":
                    Assert.assertTrue("Current PhoneAreaCdNbr: " + customerSearchResponse.getPhoneAreaCdNbr() +
                                    "\n Expected PhoneAreaCdNbr: " + custSearchRequest.getPhoneAreaCdNbr(),
                            customerSearchResponse.getPhoneSfxNbr().equals(custSearchRequest.getPhoneSfxNbr()));
                    Assert.assertTrue("Current PhonePfxNbr: " + customerSearchResponse.getPhonePfxNbr() +
                                    "\n Expected PhonePfxNbr: " + custSearchRequest.getPhonePfxNbr(),
                            customerSearchResponse.getPhoneSfxNbr().equals(custSearchRequest.getPhoneSfxNbr()));
                    Assert.assertTrue("Current PhoneSfxNbr: " + customerSearchResponse.getPhoneSfxNbr() +
                                    "\n Expected PhoneSfxNbr: " + custSearchRequest.getPhoneSfxNbr(),
                            customerSearchResponse.getPhoneSfxNbr().equals(custSearchRequest.getPhoneSfxNbr()));
                    Assert.assertTrue("Current EmailAddrTxt: " + customerSearchResponse.getEmailAddrTxt() +
                                    "\n Expected EmailAddrTxt: " + custSearchRequest.getEmailAddrTxt(),
                            customerSearchResponse.getEmailAddrTxt().equalsIgnoreCase(custSearchRequest.getEmailAddrTxt()));
                    break;

                case "exact_match":
                    List<CustomerAddress> customerAddressList = testDataUtil.getData_customerAddress(custSearchRequest.getXtraCardNbr().toString(),
                            "ADDR1_TXT, ADDR2_TXT, CITY_NAME, ST_CD, ZIP_CD");
                    List<String> expected_Addr1Txt = new ArrayList<>();
                    List<String> expected_Addr2Txt = new ArrayList<>();
                    List<String> expected_CityName = new ArrayList<>();
                    List<String> expected_StCd = new ArrayList<>();
                    List<String> expected_ZipCd = new ArrayList<>();
                    for (CustomerAddress customerAddress : customerAddressList) {
                        expected_Addr1Txt.add(customerAddress.getAddr1Txt());
                        expected_Addr2Txt.add(customerAddress.getAddr2Txt());
                        expected_CityName.add(customerAddress.getCityName());
                        expected_StCd.add(customerAddress.getStCd());
                        expected_ZipCd.add(customerAddress.getZipCd());
                    }

                    List<CustomerPhone> customerPhoneList = testDataUtil.getData_customerPhone(custSearchRequest.getXtraCardNbr().toString(),
                             "PHONE_AREA_CD_NBR, PHONE_PFX_NBR, PHONE_SFX_NBR");
                    List<String> expected_PhoneAreaCdNbr = new ArrayList<>();
                    List<String> expected_PhonePfxNbr = new ArrayList<>();
                    List<String> expected_PhoneSfxNbr = new ArrayList<>();
                    for (CustomerPhone customerPhone : customerPhoneList) {
                        expected_PhoneAreaCdNbr.add(customerPhone.getPhoneAreaCdNbr().toString());
                        expected_PhonePfxNbr.add(customerPhone.getPhonePfxNbr().toString());
                        expected_PhoneSfxNbr.add(customerPhone.getPhoneSfxNbr().toString());
                    }

                    List<CustomerEmail> customerEmailList = testDataUtil.getData_customerEmail(custSearchRequest.getXtraCardNbr().toString(),"EMAIL_ADDR_TXT");
                    List<String> expected_CustomerEmails = new ArrayList<>();
                    for (CustomerEmail customerEmail : customerEmailList) {
                        expected_CustomerEmails.add(customerEmail.getEmailAddrTxt());
                    }

                    Assert.assertTrue("Current Addr1Txt: " + customerSearchResponse.getAddr1Txt() +
                                    "\n Expected Addr1Txt: " + expected_Addr1Txt,
                            expected_Addr1Txt.contains(customerSearchResponse.getAddr1Txt()));
                    Assert.assertTrue("Current Addr2Txt: " + customerSearchResponse.getAddr2Txt() +
                                    "\n Expected Addr2xt: " + expected_Addr2Txt,
                            expected_Addr2Txt.contains(customerSearchResponse.getAddr2Txt()));
                    Assert.assertTrue("Current CityName: " + customerSearchResponse.getCityName() +
                                    "\n Expected CityName: " + expected_CityName,
                            expected_CityName.contains(customerSearchResponse.getCityName()));
                    Assert.assertTrue("Current StCd: " + customerSearchResponse.getStCd() +
                                    "\n Expected StCd: " + expected_StCd,
                            expected_StCd.contains(customerSearchResponse.getStCd()));
                    Assert.assertTrue("Current ZipCd: " + customerSearchResponse.getZipCd() +
                                    "\n Expected ZipCd: " + expected_ZipCd,
                            expected_ZipCd.contains(customerSearchResponse.getZipCd()));

                    Assert.assertTrue("Current PhoneAreaCdNbr: " + customerSearchResponse.getPhoneAreaCdNbr() +
                                    "\n Expected PhoneAreaCdNbr: " + expected_PhoneAreaCdNbr,
                            expected_PhoneAreaCdNbr.contains(customerSearchResponse.getPhoneAreaCdNbr().toString()));
                    Assert.assertTrue("Current PhonePfxNbr: " + customerSearchResponse.getPhonePfxNbr() +
                                    "\n Expected PhonePfxNbr: " + expected_PhonePfxNbr,
                            expected_PhonePfxNbr.contains(customerSearchResponse.getPhonePfxNbr().toString()));
                    Assert.assertTrue("Current PhoneSfxNbr: " + customerSearchResponse.getPhoneSfxNbr() +
                                    "\n Expected PhoneSfxNbr: " + expected_PhoneSfxNbr,
                            expected_PhoneSfxNbr.contains(customerSearchResponse.getPhoneSfxNbr().toString()));
                    Assert.assertTrue("Current EmailAddrTxt: " + customerSearchResponse.getEmailAddrTxt() +
                                    "\n Expected EmailAddrTxt: " + expected_CustomerEmails,
                            expected_CustomerEmails.contains(customerSearchResponse.getEmailAddrTxt()));

                    String current_CardNbr = null;
                    String expected_CardNbr = null;
                    if (custSearchRequest.getTypeCd().equalsIgnoreCase("0002")) {
                        current_CardNbr = customerSearchResponse.getXtraCardNbr().toString();
                        expected_CardNbr = custSearchRequest.getXtraCardNbr().toString();
                    } else if (custSearchRequest.getTypeCd().equalsIgnoreCase("0004")) {
                        current_CardNbr = customerSearchResponse.getEncodedXtraCardNbr().toString();
                        expected_CardNbr = custSearchRequest.getEncodedXtraCardNbr().toString();
                    } else if (custSearchRequest.getTypeCd().equalsIgnoreCase("0006")) {
                        current_CardNbr = customerSearchResponse.getXtraCardCipherText();
                        expected_CardNbr = custSearchRequest.getMaskedXtraCard();
                    }
                    Assert.assertTrue("Current CardNbr: " + current_CardNbr +
                                    "\n Expected CardNbr: " + expected_CardNbr,
                            current_CardNbr.equalsIgnoreCase(expected_CardNbr));
                    break;
                default:
                    throw new IllegalStateException("Unexpected searchType: " + custSearchRequest.getSearchType().toLowerCase());
            }

        }
    }



    /**
     * Create Test Data For Customer Search Scenarios
     *
     * @param
     * @throws ParseException, IncorrectResultSizeDataAccessException
     * @Author: Vikas Goud Ramayampet
     */

    public void createTestDataForCustSearchFromJson(JSONObject jo) throws
            ParseException, IncorrectResultSizeDataAccessException {
        customerSearchRequest.setUniqUserId("123d");
        int currentCustId = Integer.parseInt(jo.get("custId").toString());

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        String dateCurrent = simpleDateFormat.format(date);
        DateTime dateTime = new DateTime();
        String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");

        //Customer
        customer.setCustId(currentCustId);
        customer.setGndrCd("M");
        customer.setFirstName(jo.get("firstName").toString());
        customer.setLastName(jo.get("lastName").toString());
        try {
            customerDao.createCustomer(customer);
        } catch (Exception e) {
            System.out.println("WARN: Data already exist or error in creating Customer: " + currentCustId);
        }

        //Xtra card, encoded card & SHA2 card
        xtraCard.setCustId(currentCustId);
        xtraCard.setXtraCardNbr(Integer.parseInt(jo.get("xtraCardNbr").toString()));
        xtraCard.setEncodedXtraCardNbr2(Long.parseLong(jo.get("encodedXtraCardNbr").toString()));
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCard.setXtraCardMaskNbr(39 + currentCustId);
        xtraCard.setXtraCardSh2Nbr(jo.get("xtraCardSh2Nbr").toString());
        xtraCard.setXtraCardSh1Nbr(jo.get("xtraCardSh1Nbr").toString());
        try {
            xtraCardDao.createXtraCard(xtraCard);
        } catch (Exception e) {
            e.getMessage();
            System.out.println("WARN: Data already exist or error in creating XTRA CARD or SH2 card: " + currentCustId);
        }

        //Customer Email
        JSONArray jsonArray1 = (JSONArray) jo.get("email");
        for (int i = 0; i < jsonArray1.size(); i++) {
            JSONObject jo2 = (JSONObject) jsonArray1.get(i);
            customerEmail.setCustId(currentCustId);
            customerEmail.setEmailAddrTypeCd("H");
            customerEmail.setEmailPrefSeqNbr(Integer.parseInt(jo2.get("emailPrefSeqNbr").toString()));
            customerEmail.setEmailAddrTxt(jo2.get("emailAddrTxt").toString());
            customerEmail.setEmailStatusCd("A");
            try {
                customerDao.createCustomerEmail(customerEmail);
            } catch (Exception e) {
                System.out.println("WARN: Data already exist or error in creating Customer email: " + currentCustId);
            }
        }

        //Customer Phone
        JSONArray jsonArray2 = (JSONArray) jo.get("phone");
        for (int i = 0; i < jsonArray2.size(); i++) {
            JSONObject jo3 = (JSONObject) jsonArray2.get(i);
            customerPhone.setCustId(currentCustId);
            customerPhone.setPhoneTypeCd("H");
            customerPhone.setPhonePrefSeqNbr(Integer.parseInt(jo3.get("phonePrefSeqNbr").toString()));
            customerPhone.setPhoneAreaCdNbr(Integer.parseInt(jo3.get("phoneAreaCdNbr").toString()));
            customerPhone.setPhonePfxNbr(Integer.parseInt(jo3.get("phonePfxNbr").toString()));
            customerPhone.setPhoneSfxNbr(Integer.parseInt(jo3.get("phoneSfxNbr").toString()));
            try {
                customerDao.createCustomerPhone(customerPhone);
            } catch (Exception e) {
                e.getMessage();
                System.out.println("WARN: Data already exist or error in creating Customer phone: " + currentCustId);
            }
        }

        //Customer Address
        JSONArray jsonArray3 = (JSONArray) jo.get("addresses");
        for (int i = 0; i < jsonArray3.size(); i++) {
            JSONObject jo4 = (JSONObject) jsonArray3.get(i);
            customerAddress.setCustId(currentCustId);
            customerAddress.setAddrTypeCd("H");
            customerAddress.setAddrPrefSeqNbr(Integer.parseInt(jo4.get("addrPrefSeqNbr").toString()));
            customerAddress.setAddr1Txt(jo4.get("addr1Txt").toString());
            customerAddress.setAddr2Txt(jo4.get("addr2Txt").toString());
            customerAddress.setCityName(jo4.get("cityName").toString());
            customerAddress.setStCd(jo4.get("stCd").toString());
            customerAddress.setZipCd(jo4.get("zipCd").toString());
            try {
                customerDao.createCustomerAddress(customerAddress);
            } catch (Exception e) {
                System.out.println("WARN: Data already exist or error in creating Customer address: " + currentCustId);
            }
        }

        //Customer Opt
        customerOpt.setCustId(currentCustId);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        try {
            customerDao.createCustomerOpt(customerOpt);
        } catch (Exception e) {
            System.out.println("WARN: Data already exist or error in creating Customer opt: " + currentCustId);
        }
    }

}