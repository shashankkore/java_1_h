package com.cvs.crm.cukes.setCust.DOBCollection.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.model.response.SetCustResponse;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.service.GetCustService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class DOBCollectionStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;
    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    GetCustService getCustService;
    @Autowired
    TestDataUtil testDataUtil;
    @Autowired
    CustomerPhone customerPhone;
    @Autowired
    CustomerDao customerDao;

    String setCust_action = "";
    Integer xtraCardNbr = null;
    Integer custId = null;
    Long encodedXtraCardNbr = null;
    JSONArray jsonArray = null;
    JSONObject jsonObject;
    String birthDt_updated = null;

    GetCustRequest getCustRequest = new GetCustRequest();
    SetCustRequest setCustRequest = new SetCustRequest();
    GetCustResponse getCustResponse;
    SetCustResponse setcustResponse;

    public DOBCollectionStepDefinitions() {

        Given("{string}", (String scenario) -> {
        });

        Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
            jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
            jsonObject = testDataUtil.extractTestData(jsonArray);
            xtraCardNbr = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
            encodedXtraCardNbr = Long.parseLong(jsonObject.get("ENCODED_XTRA_CARD_NBR").toString());
            custId = Integer.parseInt(jsonObject.get("CUST_ID").toString());
            setCustRequest.setSearchCardNbr(xtraCardNbr);
            setCustRequest.setEncodedXtraCardNbr(encodedXtraCardNbr);
        });

        Given("I use my xtraCard with card type {string} at {string}", (String card_type, String channel) -> {
            setCustRequest.setVersion("1.2");
            setCustRequest.setChannel(channel);
            setCustRequest.setSearchCardType(getCustService.set_typeCd(card_type));
        });

        When("I have {string} with prefSeqNbr {int}",(String attribute, Integer prefSeqNbr) -> {
            if (attribute.equalsIgnoreCase("phone number")) {
                if (customerDao.getData_CustomerPhone_table_with_PrefSeqNbr(xtraCardNbr.toString(), prefSeqNbr).isEmpty()) {
                    testDataUtil.addCustomerData(jsonObject, "CUSTOMER_PHONE", "Phone_2", String.valueOf(prefSeqNbr));
                }
            }else if (attribute.equalsIgnoreCase("email")) {
                if (customerDao.getData_CustomerEmail_table_with_PrefSeqNbr(xtraCardNbr.toString(), prefSeqNbr).isEmpty()) {
                    testDataUtil.addCustomerData(jsonObject, "CUSTOMER_EMAIL", "email_2", String.valueOf(prefSeqNbr));
                }
            }
        });

        When("I don't have {string} with prefSeqNbr as {int}",(String attribute, Integer prefSeqNbr) -> {
            if (customerDao.getData_CustomerPhone_table_with_PrefSeqNbr(xtraCardNbr.toString(), prefSeqNbr).isEmpty()) {
                log.info("We don't have any date with preSeqNbr " + prefSeqNbr);
            } else {
                if(attribute.equalsIgnoreCase("phone number")) {
                    customerDao.delete_CustomerPhone_with_PrefSeqNbr(xtraCardNbr, prefSeqNbr);
                } else if (attribute.equalsIgnoreCase("email")) {
                    customerDao.delete_CustomerEmail_with_PrefSeqNbr(xtraCardNbr, prefSeqNbr);
                }
            }
        });

        When("I don't have any {string} for my user",(String attribute) -> {
            List<Integer> custIdList = Arrays.asList(custId);
            if(attribute.equalsIgnoreCase("phone number")) {
                customerDao.deleteCustomer_NEW(custIdList,"CUSTOMER_PHONE");
            } else if (attribute.equalsIgnoreCase("email")) {
                customerDao.deleteCustomer_NEW(custIdList,"CUSTOMER_EMAIL");
            }
        });

        When("I want to update date of birth as {string}",(String updated_dob) -> {
            if (updated_dob.equalsIgnoreCase("") || updated_dob.equalsIgnoreCase("null")) {
                birthDt_updated = "";
            } else if(updated_dob.equalsIgnoreCase("valid")){
                DateTime dateTime = new DateTime();
                birthDt_updated = dateTime.minusYears(30).toString("yyyy-MMM-dd");
            } else
                throw new IllegalArgumentException("Unexpected value passed: " + updated_dob.toLowerCase());
            setCustRequest.setBirthDt(birthDt_updated);
        });

        When("I want to update date of birth as {string} and {string} with prefSeqNbr as {int}",(String updated_dob, String attribute, Integer prefSeqNbr) -> {
            if (updated_dob.equalsIgnoreCase("") || updated_dob.equalsIgnoreCase("null")) {
                birthDt_updated = "";
            } else if(updated_dob.equalsIgnoreCase("valid")){
                DateTime dateTime = new DateTime();
                birthDt_updated = dateTime.minusYears(30).toString("yyyy-MMM-dd");
            } else
                throw new IllegalArgumentException("Unexpected value passed: " + updated_dob.toLowerCase());

            if(attribute.equalsIgnoreCase("phone number")) {
                setCustRequest.setBirthDt(birthDt_updated);
                setCustRequest.setPhonePrefSeqNbr(prefSeqNbr);
                setCustRequest.setPhoneAreaCdNbr(201);
                setCustRequest.setPhonePfxNbr(281);
                setCustRequest.setPhoneSfxNbr(2004);
                if (customerDao.getData_CustomerPhone_table_with_PrefSeqNbr(xtraCardNbr.toString(), prefSeqNbr).isEmpty()) {
                    setCustRequest.setPhoneTypeCd("H");
                } else {
                    String phTypeCd = customerDao.getData_CustomerPhone_table_with_PrefSeqNbr(xtraCardNbr.toString(), prefSeqNbr)
                            .get(0).getPhoneTypeCd();
                    setCustRequest.setPhoneTypeCd(phTypeCd);
                }

            } else if (attribute.equalsIgnoreCase("email")) {
                setCustRequest.setBirthDt(birthDt_updated);
                setCustRequest.setEmailPrefSeqNbr(prefSeqNbr);
                setCustRequest.setEmailAddrTxt("emailcvs@gmail.com");
                setCustRequest.setEmailAddrTypeCd("U");
                setCustRequest.setEmailStatusCd("A");

                if (customerDao.getData_CustomerEmail_table_with_PrefSeqNbr(xtraCardNbr.toString(), prefSeqNbr).isEmpty()) {
                    setCustRequest.setEmailAddrTypeCd("H");
                } else {
                    String emailAddrTypeCd = customerDao.getData_CustomerEmail_table_with_PrefSeqNbr(xtraCardNbr.toString(), prefSeqNbr)
                            .get(0).getEmailAddrTypeCd();
                    setCustRequest.setEmailAddrTypeCd(emailAddrTypeCd);
                }
            } else
                throw new IllegalArgumentException("Unexpected value passed: " + attribute.toLowerCase());
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

        Then("The getCust response {string} have updated date of birth", (String status) -> {
            String current_BirthDt = null;
            String exp_BirthDt = null;
            if(getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).containsKey("birth_dt")) {
                current_BirthDt = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).get("birth_dt").toString();
            }
            if (setCustRequest.getBirthDt() != "") {
                Date exp_dob_date = new SimpleDateFormat("yyyy-MMM-dd").parse(setCustRequest.getBirthDt());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                exp_BirthDt = formatter.format(exp_dob_date);
            }
            if (status.equalsIgnoreCase("should")) {
                if (exp_BirthDt == null) {
                    Assert.assertNull(current_BirthDt);
                } else {
                    Assert.assertTrue("Current BirthDate: " + current_BirthDt +
                                    "\n Expected BirthDate: " + exp_BirthDt,
                            current_BirthDt.equals(exp_BirthDt));
                }
            } else if (status.equalsIgnoreCase("should not")) {
                if (exp_BirthDt == null) {
                    Assert.assertNull(current_BirthDt);
                } else {
                    Assert.assertTrue("Current BirthDate: " + current_BirthDt +
                                    "\n Expected BirthDate: " + exp_BirthDt,
                            !current_BirthDt.equals(exp_BirthDt));
                }
            } else
                throw new IllegalArgumentException("Unexpected value: " + status.toLowerCase());
        });

        Then("The customer table has updated date of birth", () -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
            List<Customer> customer = testDataUtil.getData_customer(xtraCardNbr.toString(), "BIRTH_DT");
            Date current_BirthDt = customer.get(0).getBirthDt();
            if (setCustRequest.getBirthDt() == "") {
                Assert.assertNull(current_BirthDt);
            } else {
                Assert.assertTrue("Current BIRTH_DT:: " + formatter.format(customer.get(0).getBirthDt()) +
                                "\n Expected BIRTH_DT:: " + setCustRequest.getBirthDt(),
                        setCustRequest.getBirthDt().equalsIgnoreCase(formatter.format(customer.get(0).getBirthDt())));
            }
        });

        Then("The customer table has updated phone number", () -> {
            List<CustomerPhone> customerPhone_data = customerDao.getData_CustomerPhone_table_with_PrefSeqNbr(xtraCardNbr.toString(), setCustRequest.getPhonePrefSeqNbr());

            Assert.assertTrue("Current PHONE_PREF_SEQ_NBR:: " +  customerPhone_data.get(0).getPhonePrefSeqNbr() +
                            "\n Expected PHONE_PREF_SEQ_NBR:: " + setCustRequest.getPhonePrefSeqNbr(),
                    setCustRequest.getPhonePrefSeqNbr().equals(customerPhone_data.get(0).getPhonePrefSeqNbr()));

            Assert.assertTrue("Current PHONE_AREA_CD_NBR:: " +  customerPhone_data.get(0).getPhoneAreaCdNbr() +
                            "\n Expected PHONE_AREA_CD_NBR:: " + setCustRequest.getPhoneAreaCdNbr(),
                    setCustRequest.getPhoneAreaCdNbr().equals(customerPhone_data.get(0).getPhoneAreaCdNbr()));

            Assert.assertTrue("Current PHONE_PFX_NBR:: " +  customerPhone_data.get(0).getPhonePfxNbr() +
                            "\n Expected PHONE_PFX_NBR:: " + setCustRequest.getPhonePfxNbr(),
                    setCustRequest.getPhonePfxNbr().equals(customerPhone_data.get(0).getPhonePfxNbr()));

            Assert.assertTrue("Current PHONE_SFX_NBR:: " +  customerPhone_data.get(0).getPhoneSfxNbr() +
                            "\n Expected PHONE_SFX_NBR:: " + setCustRequest.getPhoneSfxNbr(),
                    setCustRequest.getPhoneSfxNbr().equals(customerPhone_data.get(0).getPhoneSfxNbr()));
        });

        Then("The customer table has updated email", () -> {
            List<CustomerEmail> customerEmail_data = customerDao.getData_CustomerEmail_table_with_PrefSeqNbr(xtraCardNbr.toString(), setCustRequest.getEmailPrefSeqNbr());

            Assert.assertTrue("Current EMAIL_PREF_SEQ_NBR:: " +  customerEmail_data.get(0).getEmailPrefSeqNbr() +
                            "\n Expected EMAIL_PREF_SEQ_NBR:: " + setCustRequest.getEmailPrefSeqNbr(),
                    setCustRequest.getEmailPrefSeqNbr().equals(customerEmail_data.get(0).getEmailPrefSeqNbr()));

            Assert.assertTrue("Current EMAIL_ADDR_TXT:: " +  customerEmail_data.get(0).getEmailAddrTxt() +
                            "\n Expected EMAIL_ADDR_TXT:: " + setCustRequest.getEmailAddrTxt(),
                    setCustRequest.getEmailAddrTxt().equals(customerEmail_data.get(0).getEmailAddrTxt()));
        });

        When("I get a response from getCust API",() ->{
            getCustRequest.setSearchCardNbr(xtraCardNbr);
            getCustRequest.setVersion("1.2");
            getCustRequest.setChannel(setCustRequest.getChannel());
            getCustRequest.setSearchCardType(setCustRequest.getSearchCardType());
            getCustResponse = getCustService.getCustResponse(getCustRequest, "profile").as(GetCustResponse.class);
            getCustService.getServiceResponse().then().statusCode(200);
        });

    }
}
