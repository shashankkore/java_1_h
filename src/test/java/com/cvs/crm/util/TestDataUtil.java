package com.cvs.crm.util;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.CustSearchRequest;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.HRDao;
import com.cvs.crm.repository.XtraCardDao;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Data
@Slf4j
public class TestDataUtil {
    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    XtraCard xtraCard;
    @Autowired
    MaskedXtraCard maskedXtraCard;
    @Autowired
    XtraCardCustomer xtraCardCustomer;
    @Autowired
    Customer customer;
    @Autowired
    CustomerAddress customerAddress;
    @Autowired
    CustomerEmail customerEmail;
    @Autowired
    CustomerPhone customerPhone;
    @Autowired
    XtraCardDao xtraCardDao;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    CarePassDao carePassDao;
    @Autowired
    HRDao hrDao;
    CustSearchRequest custSearchRequest = new CustSearchRequest();


    @Autowired
    JsonUtil jsonUtil;
    DateTime dateTime = new DateTime();

    /**
     * Extract data from filePath > xtraCard_obj
     *
     * @param testData_filePath
     * @param testDataObjectName
     * @return
     */
    public JSONArray extractTestData(String testData_filePath, String testDataObjectName) {
        String filePath_base = "./src/test/resources/testdata/";
        String filePath = filePath_base + testData_filePath + ".json";
        JSONArray inputDataArray = jsonUtil.readJsonFileGetArray(filePath,
                testDataObjectName);
        log.info("\nTest data setup using json object >> [{}]", testDataObjectName);
        return inputDataArray;
    }

    /**
     * Extract data from JSONArray > subNode_obj
     *
     * @param jsonArray
     * @return
     */
    public JSONObject extractTestData(JSONArray jsonArray) {
        JSONObject jsonObject = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = (JSONObject) jsonArray.get(i);
        }
        return jsonObject;
    }

    /**
     * Extract data from JSONArray > subNode_obj
     *
     * @param jsonArray
     * @return
     */
    public JSONObject extractTestData(JSONArray jsonArray, String table) {
        JSONObject jsonObject = null;
        JSONObject jsonObject_table = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = (JSONObject) jsonArray.get(i);
        }
        JSONArray jsonArray_table = (JSONArray) jsonObject.get(table.toUpperCase());
        for (int i = 0; i < jsonArray_table.size(); i++) {
            jsonObject_table = (JSONObject) jsonArray_table.get(i);
        }
        return jsonObject_table;
    }


    /**
     * Extract data from filePath > xtraCard_obj > subNode_obj
     *
     * @param testData_filePath
     * @param testDataObjectName
     * @return
     */
    public JSONObject extractTestData_Json(String testData_filePath, String testDataObjectName) {
        String filePath_base = "./src/test/resources/testdata/";
        String filePath = filePath_base + testData_filePath + ".json";
        JSONArray inputDataArray = jsonUtil.readJsonFileGetArray(filePath,
                testDataObjectName);
        JSONObject jsonObject = null;
        for (int i = 0; i < inputDataArray.size(); i++) {
            jsonObject = (JSONObject) inputDataArray.get(i);
        }
        return jsonObject;
    }

    /**
     * Add CustomerData based to a table based on pref_seq_nbr
     * @param jsonObject
     * @param tableName
     * @param table_data
     * @param pref_seq_nbr
     */
    public void addCustomerData(JSONObject jsonObject, String tableName, String table_data, String pref_seq_nbr) {
        JSONArray jsonArray = (JSONArray) jsonObject.get(tableName.toUpperCase());
        switch (tableName.toUpperCase()) {
            case "CUSTOMER_ADDRESS":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_Customer = (JSONObject) jsonArray.get(i);
                    customerAddress.setCustId(Integer.parseInt(jsonObj_Customer.get("CUST_ID").toString()));
                    customerAddress.setAddrPrefSeqNbr(Integer.parseInt(pref_seq_nbr));
                    if(table_data.toLowerCase().equalsIgnoreCase("address_2")) {
                        customerAddress.setAddr1Txt("99999-2, New CVS Drive");
                        customerAddress.setAddr2Txt("APT NO 999-2");
                        customerAddress.setCityName("WOONSOCKET");
                        customerAddress.setStCd("RI");
                        customerAddress.setZipCd("02895");
                        customerDao.createCustomerAddress_NEW(customerAddress);
                    } else if (table_data.toLowerCase().equalsIgnoreCase("address_3")) {
                        customerAddress.setAddr1Txt("99999-3, New CVS Drive");
                        customerAddress.setAddr2Txt("APT NO 999-3");
                        customerAddress.setCityName("WOONSOCKET");
                        customerAddress.setStCd("RI");
                        customerAddress.setZipCd("02895");
                        customerDao.createCustomerAddress_NEW(customerAddress);
                    } else if (table_data.toLowerCase().equalsIgnoreCase("address_4")) {
                        customerAddress.setAddr1Txt("99999-4, New CVS Drive");
                        customerAddress.setAddr2Txt("APT NO 999-4");
                        customerAddress.setCityName("WOONSOCKET");
                        customerAddress.setStCd("RI");
                        customerAddress.setZipCd("02895");
                        customerDao.createCustomerAddress_NEW(customerAddress);
                    }
                }
                break;
            case "CUSTOMER_EMAIL":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_Email = (JSONObject) jsonArray.get(i);
                    customerEmail.setCustId(Integer.parseInt(jsonObj_Email.get("CUST_ID").toString()));
                    customerEmail.setEmailPrefSeqNbr(Integer.parseInt(pref_seq_nbr));
                    if(table_data.toLowerCase().equalsIgnoreCase("email_2")) {
                        customerEmail.setEmailAddrTxt("emailcvs99999_2@gmail.com");
                        customerEmail.setEmailAddrTypeCd("U");
                        customerDao.createCustomerEmail_NEW(customerEmail);
                    } else if (table_data.toLowerCase().equalsIgnoreCase("email_3")) {
                        customerEmail.setEmailAddrTxt("emailcvs99999_3@gmail.com");
                        customerEmail.setEmailAddrTypeCd("U");
                        customerDao.createCustomerEmail_NEW(customerEmail);
                    } else if (table_data.toLowerCase().equalsIgnoreCase("email_4")) {
                        customerEmail.setEmailAddrTxt("emailcvs99999_4@gmail.com");
                        customerEmail.setEmailAddrTypeCd("U");
                        customerDao.createCustomerEmail_NEW(customerEmail);
                    }
                }
                break;
            case "CUSTOMER_PHONE":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_Phone = (JSONObject) jsonArray.get(i);
                    customerPhone.setCustId(Integer.parseInt(jsonObj_Phone.get("CUST_ID").toString()));
                    customerPhone.setPhonePrefSeqNbr(Integer.parseInt(pref_seq_nbr));
                    if(table_data.toLowerCase().equalsIgnoreCase("phone_2")) {
                        customerPhone.setPhoneAreaCdNbr(Integer.parseInt("201"));
                        customerPhone.setPhonePfxNbr(Integer.parseInt("280"));
                        customerPhone.setPhoneSfxNbr(Integer.parseInt("2003"));
                        customerPhone.setPhoneTypeCd("S");
                        customerDao.createCustomerPhone_NEW(customerPhone);
                    } else if (table_data.toLowerCase().equalsIgnoreCase("phone_3")) {
                        customerPhone.setPhoneAreaCdNbr(Integer.parseInt("301"));
                        customerPhone.setPhonePfxNbr(Integer.parseInt("380"));
                        customerPhone.setPhoneSfxNbr(Integer.parseInt("3003"));
                        customerPhone.setPhoneTypeCd("S");
                        customerDao.createCustomerPhone_NEW(customerPhone);
                    } else if (table_data.toLowerCase().equalsIgnoreCase("phone_4")) {
                        customerPhone.setPhoneAreaCdNbr(Integer.parseInt("401"));
                        customerPhone.setPhonePfxNbr(Integer.parseInt("480"));
                        customerPhone.setPhoneSfxNbr(Integer.parseInt("4003"));
                        customerPhone.setPhoneTypeCd("S");
                        customerDao.createCustomerPhone_NEW(customerPhone);
                    }
                }
                break;
        }
    }

    public void createTestData(JSONObject jsonObject) throws ParseException, InterruptedException {
        log.info("*****************************************************************");
        log.info("\nCreating xtraCard : [{}]", jsonObject.get("XTRA_CARD_NBR"));
        setUp_data(jsonObject, "XTRA_CARD");
        setUp_data(jsonObject, "CUSTOMER");
        setUp_data(jsonObject, "CUSTOMER_ADDRESS");
        setUp_data(jsonObject, "CUSTOMER_EMAIL");
        setUp_data(jsonObject, "CUSTOMER_PHONE");
        setUp_data(jsonObject, "XTRA_CUSTOMER");
        setUp_data(jsonObject, "MASKED_XTRA_CARD");
        log.info("\nCompleted creating xtraCard : [{}]", jsonObject.get("XTRA_CARD_NBR"));
        log.info("*****************************************************************");
    }

    public void update_xtraCard(JSONObject jsonObject, String columnName, String value) {
        xtraCardDao.updateXtraCard_NEW(jsonObject, columnName, value);
    }

    public String getData_xtraCard(String xtraCardNbr, String columnName) {
        return xtraCardDao.getData_XtraCard_table(xtraCardNbr, columnName);
    }

    public List<Customer> getData_customer(String xtraCardNbr, String columnName) {
        return customerDao.getData_Customer_table(xtraCardNbr, columnName.toUpperCase());
    }

    public List<CustomerEmail> getData_customerEmail(String xtraCardNbr, String columnName) {
        return customerDao.getData_CustomerEmail_table(xtraCardNbr, columnName.toUpperCase());
    }

    public List<CustomerAddress> getData_customerAddress(String xtraCardNbr, String columnName) {
        return customerDao.getData_CustomerAddress_table(xtraCardNbr, columnName.toUpperCase());
    }

    public List<CustomerPhone> getData_customerPhone(String xtraCardNbr, String columnName) {
        return customerDao.getData_CustomerPhone_table(xtraCardNbr, columnName.toUpperCase());
    }

    public List<CustomerOpt> getData_customerOpt(String xtraCardNbr, String columnName) {
        return customerDao.getData_CustomerOpt_table(xtraCardNbr, columnName.toUpperCase());
    }

    public void setUp_data(JSONObject jsonObject, String tableName) throws ParseException {

        SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat_ddMMMyy = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat simpleDateFormat_Ts = new SimpleDateFormat("yyyy-MM-dd HH.MM.SS a");

//        DateTime dateTime = new DateTime();
        String todayDate = dateTime.toString("yyyy-MM-dd");
        String yesterdayDate = dateTime.minusDays(1).toString("yyyy-MM-dd");
        String tomorrowDate = dateTime.plusDays(1).toString("yyyy-MM-dd");
        String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
        String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");
        // Setting up Birth date as 25yrs past from today
        String birthDate = dateTime.minusYears(25).toString("yyyy-MM-dd");

        String todayTimeStamp = dateTime.toString("yyyy-MM-dd HH.MM.SS a");
        String todayTimeStampZ = dateTime.toString("yyyy-MM-dd HH.MM.SS Z");
        String todayTimeStampXP = dateTime.toString("yyyyMMdd HH:MM:SS");

        JSONArray jsonArray = (JSONArray) jsonObject.get(tableName.toUpperCase());
        switch (tableName.toUpperCase()) {
            case "XTRA_CARD":
                xtraCard.setXtraCardNbr(Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString()));
                xtraCard.setCustId(Integer.parseInt(jsonObject.get("CUST_ID").toString()));
                xtraCard.setTrgtGeoMktCd(jsonObject.get("TRGT_GEO_MKT_CD").toString());
                xtraCard.setEnrollSrcCd(jsonObject.get("ENROLL_SRC_CD").toString());
                xtraCard.setSurveyCompletionDt(simpleDateFormat_yyyyMMdd.parse(prev1yearDate));
//                xtraCard.setSurverCompletionDt(simpleDateFormat_ddMMMyy.parse(jsonObject.get("SURVEY_COMPLETION_DT").toString()));
                xtraCard.setCardMbrDt(simpleDateFormat_yyyyMMdd.parse(prev1yearDate));
//                xtraCard.setCardMbrDt(simpleDateFormat_ddMMMyy.parse(jsonObject.get("CARD_MBR_DT").toString()));
                xtraCard.setCardStatCd(jsonObject.get("CARD_STAT_CD").toString());
                xtraCard.setMktgTypeCd(jsonObject.get("MKTG_TYPE_CD").toString());
                xtraCard.setEncodedXtraCardNbr2(Long.parseLong(jsonObject.get("ENCODED_XTRA_CARD_NBR").toString()));
                xtraCard.setLastUpdtSrcCd(jsonObject.get("LAST_UPDT_SRC_CD").toString());
                xtraCard.setLastUpdtDt(simpleDateFormat_yyyyMMdd.parse(todayDate));
//                xtraCard.setLastUpdtDt(simpleDateFormat_ddMMMyy.parse(jsonObject.get("LAST_UPDT_DT").toString()));
                xtraCard.setLastUpdtById(jsonObject.get("LAST_UPDT_BY_ID").toString());
//                xtraCard.setCardLastScanDt(simpleDateFormat_yyyyMMdd.parse(yesterdayDate));
                xtraCardDao.createXtraCard_NEW(xtraCard);
                break;

            case "CUSTOMER":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_Customer = (JSONObject) jsonArray.get(i);
                    customer.setCustId(Integer.parseInt(jsonObj_Customer.get("CUST_ID").toString()));
                    customer.setGndrCd(jsonObj_Customer.get("GNDR_CD").toString());
                    customer.setFirstName(jsonObj_Customer.get("FIRST_NAME").toString());
                    customer.setMiddleInitialTxt(jsonObj_Customer.get("MIDDLE_INITIAL_TXT").toString());
                    customer.setLastName(jsonObj_Customer.get("LAST_NAME").toString());
                    customer.setSurName(jsonObj_Customer.get("SUR_NAME").toString());
                    customer.setPfxTxt(jsonObj_Customer.get("PFX_TXT").toString());
                    customer.setBirthDt(simpleDateFormat_yyyyMMdd.parse(birthDate));
//                    customer.setBirthDt(simpleDateFormat_ddMMMyy.parse(jsonObj_Customer.get("BIRTH_DT").toString()));
                    customer.setLastUpdtSrcCd(jsonObj_Customer.get("LAST_UPDT_SRC_CD").toString());
                    customer.setLastUpdtDt(simpleDateFormat_yyyyMMdd.parse(todayDate));
//                    customer.setLastUpdtDt(simpleDateFormat_ddMMMyy.parse(jsonObj_Customer.get("LAST_UPDT_DT").toString()));
                    customer.setLastUpdtById(jsonObj_Customer.get("LAST_UPDT_BY_ID").toString());
                    customer.setFirstUpdtById(jsonObj_Customer.get("FIRST_UPDT_BY_ID").toString());
                    customerDao.createCustomer_NEW(customer);
                }
                break;

            case "CUSTOMER_ADDRESS":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_CustomerAddress = (JSONObject) jsonArray.get(i);
                    customerAddress.setCustId(Integer.parseInt(jsonObj_CustomerAddress.get("CUST_ID").toString()));
                    customerAddress.setAddrTypeCd(jsonObj_CustomerAddress.get("ADDR_TYPE_CD").toString());
                    customerAddress.setAddrPrefSeqNbr(Integer.parseInt(jsonObj_CustomerAddress.get("ADDR_PREF_SEQ_NBR").toString()));
                    customerAddress.setAddr1Txt(jsonObj_CustomerAddress.get("ADDR1_TXT").toString());
                    customerAddress.setAddr2Txt(jsonObj_CustomerAddress.get("ADDR2_TXT").toString());
                    customerAddress.setCityName(jsonObj_CustomerAddress.get("CITY_NAME").toString());
                    customerAddress.setStCd(jsonObj_CustomerAddress.get("ST_CD").toString());
                    customerAddress.setZipCd(jsonObj_CustomerAddress.get("ZIP_CD").toString());
                    customerAddress.setLastUpdtSrcCd(jsonObj_CustomerAddress.get("LAST_UPDT_SRC_CD").toString());
                    customerAddress.setLastUpdtDt(simpleDateFormat_yyyyMMdd.parse(todayDate));
//                    customerAddress.setLastUpdtDt(simpleDateFormat_ddMMMyy.parse(jsonObj_CustomerAddress.get("LAST_UPDT_DT").toString()));
                    customerAddress.setLastUpdtById(jsonObj_CustomerAddress.get("LAST_UPDT_BY_ID").toString());
                    customerDao.createCustomerAddress_NEW(customerAddress);
                }
                break;

            case "CUSTOMER_EMAIL":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_CustomerEmail = (JSONObject) jsonArray.get(i);
                    customerEmail.setCustId(Integer.parseInt(jsonObj_CustomerEmail.get("CUST_ID").toString()));
                    customerEmail.setEmailAddrTypeCd(jsonObj_CustomerEmail.get("EMAIL_ADDR_TYPE_CD").toString());
                    customerEmail.setEmailPrefSeqNbr(Integer.parseInt(jsonObj_CustomerEmail.get("EMAIL_PREF_SEQ_NBR").toString()));
                    customerEmail.setEmailAddrTxt(jsonObj_CustomerEmail.get("EMAIL_ADDR_TXT").toString());
                    customerEmail.setEmailStatusCd(jsonObj_CustomerEmail.get("EMAIL_STATUS_CD").toString());
                    customerEmail.setLastUpdtSrcCd(jsonObj_CustomerEmail.get("LAST_UPDT_SRC_CD").toString());
                    customerEmail.setLastUpdtDt(simpleDateFormat_yyyyMMdd.parse(todayDate));
//                    customerEmail.setLastUpdtDt(simpleDateFormat_ddMMMyy.parse(jsonObj_CustomerEmail.get("LAST_UPDT_DT").toString()));
                    customerEmail.setLastUpdtById(jsonObj_CustomerEmail.get("LAST_UPDT_BY_ID").toString());
                    customerEmail.setFirstUpdtDt(simpleDateFormat_yyyyMMdd.parse(prev1yearDate));
//                    customerEmail.setFirstUpdtDt(simpleDateFormat_ddMMMyy.parse(jsonObj_CustomerEmail.get("FIRST_UPDT_DT").toString()));
                    customerEmail.setFirstUpdtSrcCd(jsonObj_CustomerEmail.get("FIRST_UPDT_SRC_CD").toString());
                    customerEmail.setFirstUpdtById(jsonObj_CustomerEmail.get("FIRST_UPDT_BY_ID").toString());
                    customerDao.createCustomerEmail_NEW(customerEmail);
                }
                break;

            case "CUSTOMER_PHONE":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_CustomerPhone = (JSONObject) jsonArray.get(i);
                    customerPhone.setCustId(Integer.parseInt(jsonObj_CustomerPhone.get("CUST_ID").toString()));
                    customerPhone.setPhoneTypeCd(jsonObj_CustomerPhone.get("PHONE_TYPE_CD").toString());
                    customerPhone.setPhonePrefSeqNbr(Integer.parseInt(jsonObj_CustomerPhone.get("PHONE_PREF_SEQ_NBR").toString()));
                    customerPhone.setPhoneAreaCdNbr(Integer.parseInt(jsonObj_CustomerPhone.get("PHONE_AREA_CD_NBR").toString()));
                    customerPhone.setPhonePfxNbr(Integer.parseInt(jsonObj_CustomerPhone.get("PHONE_PFX_NBR").toString()));
                    customerPhone.setPhoneSfxNbr(Integer.parseInt(jsonObj_CustomerPhone.get("PHONE_SFX_NBR").toString()));
                    customerPhone.setLastUpdtSrcCd(jsonObj_CustomerPhone.get("LAST_UPDT_SRC_CD").toString());
                    customerPhone.setLastUpdtDt(simpleDateFormat_yyyyMMdd.parse(todayDate));
//                    customerPhone.setLastUpdtDt(simpleDateFormat_ddMMMyy.parse(jsonObj_CustomerPhone.get("LAST_UPDT_DT").toString()));
                    customerPhone.setLastUpdtById(jsonObj_CustomerPhone.get("LAST_UPDT_BY_ID").toString());
                    customerDao.createCustomerPhone_NEW(customerPhone);
                }
                break;

            case "XTRA_CUSTOMER":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_XtraCustomer = (JSONObject) jsonArray.get(i);
                    xtraCardCustomer.setCustId(Integer.parseInt(jsonObj_XtraCustomer.get("CUST_ID").toString()));
                    xtraCardCustomer.setHhNbr(jsonObj_XtraCustomer.get("HH_NBR").toString());
                    xtraCardCustomer.setHeadOfHHInd(jsonObj_XtraCustomer.get("HEAD_OF_HH_IND").toString());
                    xtraCardCustomer.setCustStatCd(jsonObj_XtraCustomer.get("CUST_STAT_CD").toString());
                    xtraCardCustomer.setCustStatUpdtDt(simpleDateFormat_yyyyMMdd.parse(prev1yearDate));
                    xtraCardCustomer.setCustStatUpdtDt(simpleDateFormat_ddMMMyy.parse(jsonObj_XtraCustomer.get("CUST_STAT_UPDT_DT").toString()));
                    xtraCardDao.createXtraCardCustomer_NEW(xtraCardCustomer);
                }
                break;

            case "MASKED_XTRA_CARD":
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj_MaskedXtraCard = (JSONObject) jsonArray.get(i);
                    maskedXtraCard.setXtraCardNbr(Integer.parseInt(jsonObj_MaskedXtraCard.get("XTRA_CARD_NBR").toString()));
                    maskedXtraCard.setXtraCardMaskNbr(jsonObj_MaskedXtraCard.get("XTRA_CARD_MASK_NBR").toString());
                    maskedXtraCard.setVisibleIndicator(jsonObj_MaskedXtraCard.get("VISIBLE_IND").toString());
                    maskedXtraCard.setXtraCardSh1Nbr(jsonObj_MaskedXtraCard.get("XTRA_CARD_SHA1_NBR").toString());
                    maskedXtraCard.setXtraCardSh2Nbr(jsonObj_MaskedXtraCard.get("XTRA_CARD_SHA2_NBR").toString());
                    xtraCardDao.createMaskedXtraCard_NEW(maskedXtraCard);
                }
                break;
            default:
                System.out.println("TABLE NAME: [{}] not ready yet to setup data>> " + tableName);
                break;
        }
    }

    /**
     * Delete TestData
     */
    public void deleteTestData(List<Integer> xtraCardNbrList, List<Integer> custIdList) {
        log.info("*****************************************************************");
        log.info("\nDeleting xtraCard details: [{}]", xtraCardNbrList);
        xtraCardDao.delete_xtraCard(xtraCardNbrList, custIdList);
        customerDao.delete_customer(custIdList);
        carePassDao.deleteCarePass(xtraCardNbrList);
        hrDao.delete_HRRecords(xtraCardNbrList);
        log.info("\nCompleted deleting xtraCard details: [{}]", xtraCardNbrList);
        log.info("*****************************************************************");
    }

}
