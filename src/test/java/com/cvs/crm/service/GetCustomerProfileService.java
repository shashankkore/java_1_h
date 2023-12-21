package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.repository.CustomerDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class GetCustomerProfileService {

    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    CustomerAddress customerAddress;

    int addr_seq_num = 1;
    int phone_seq_num = 1;
    int email_seq_num = 1;

    public void getServiceResponse(GetCustRequest getCustRequest, String requestParams) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(serviceConfig.getGetcustUrl());
        String msgSrcCd;
        String userId;
        int srcLocCd;

        if ("M".equalsIgnoreCase(getCustRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else if ("W".equalsIgnoreCase(getCustRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else {
            msgSrcCd = "R";
            userId = "STORE";
            srcLocCd = 68585;
        }

        if(requestParams.contains("addr_pref_seq_nbr=2")) {
            addr_seq_num = 2;
        } else if(requestParams.contains("email_pref_seq_nbr=2")) {
            email_seq_num = 2;
        } else if(requestParams.contains("phone_pref_seq_nbr=2")) {
            phone_seq_num = 2;
        } else {
             addr_seq_num = 1;
             phone_seq_num = 1;
             email_seq_num = 1;
        }
        String jsonRequest = buildRequestJsonStr(requestParams);

        if (getCustRequest.getVersion().equals("1.1")) {
            requestSpecBuilder.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                    .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                    .addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr());
            addQueryParams(requestSpecBuilder, msgSrcCd, userId, srcLocCd);
            getPostResponse(requestSpecBuilder, jsonRequest);
        } else {
            requestSpecBuilder.setBasePath("api/v1.2/customers/" + getCustRequest.getSearchCardType() + "," + getCustRequest.getSearchCardNbr());
            addQueryParams(requestSpecBuilder, msgSrcCd, userId, srcLocCd);
            getPostResponse(requestSpecBuilder, jsonRequest);
        }
    }

    public void updateCustomerAddress(String addr2Txt, Integer xtraCardNumber) {
        customerDao.updateCustomerAddress(addr2Txt, xtraCardNumber);
    }

    public void updateCustomerEmail(String email, Integer xtraCardNumber) {
        customerDao.updateCustomerEmail(email, xtraCardNumber);
    }

    public void updateCustomerPhone(String phone, Integer xtraCareNumber) {
        customerDao.updateCustomerPhone(phone, xtraCareNumber);
    }

    public void updateCustomerDOB(String dob, Integer xtraCareNumber) {
        customerDao.updateCustomerDOB(dob, xtraCareNumber);
    }

    public List<CustomerAddress> getCustomerAddress(Integer xtraCardNumber) {
        return customerDao.getDataFromCustomerAddressTable(xtraCardNumber);
    }

    public List<CustomerEmail> getCustomerEmail(Integer xtraCardNumber) {
        return customerDao.getDataFromCustomerEmailTable(xtraCardNumber);
    }

    public List<CustomerPhone> getCustomerPhoneNumber(Integer xtraCardNumber) {
        return customerDao.getDataFromCustomerPhoneTable(xtraCardNumber);
    }

    public List<Customer> getCustomerInformation(Integer xtraCardNumber) {
        return customerDao.getDataFromCustomerTable(xtraCardNumber);
    }

    public void addCustomerAddress(Integer xtraCardNumber) {
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustId(getCustomerAddress(xtraCardNumber).get(0).getCustId());
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(2);
        customerAddress.setAddr1Txt("1128 Tower Ln");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("Bensenville");
        customerAddress.setStCd("IL");
        customerAddress.setZipCd("60106");
        customerDao.createCustomerAddress(customerAddress);
    }

    public void addCustomerPhoneNumber(Integer xtraCardNumber) {
        CustomerPhone customerPhone = new CustomerPhone();
        customerPhone.setCustId(getCustomerPhoneNumber(xtraCardNumber).get(0).getCustId());
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(2);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);
    }

    public void addCustomerEmailAddress(Integer xtraCardNumber) {
        CustomerEmail customerEmail = new CustomerEmail();
        customerEmail.setCustId(getCustomerEmail(xtraCardNumber).get(0).getCustId());
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(2);
        customerEmail.setEmailAddrTxt("test123@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);
    }

    public void deleteCustomerAddress(Integer xtraCardNumber, int seqNum) {
        customerDao.deleteCustomerAddress(xtraCardNumber,seqNum);
    }

    public void deleteCustomerPhoneNumber(Integer xtraCardNumber, int seqNum) {
        customerDao.deleteCustomerPhoneNumber(xtraCardNumber,seqNum);
    }

    public void deleteCustomerEmailAddress(Integer xtraCardNumber, int seqNum) {
        customerDao.deleteCustomerEmailAddress(xtraCardNumber,seqNum);
    }

    private void addQueryParams(RequestSpecBuilder requestSpecBuilder, String msgSrcCd, String userId, int srcLocCd) {
        requestSpecBuilder.addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
    }

    private Response getPostResponse(RequestSpecBuilder requestSpecBuilder, String jsonRequest) {
        RequestSpecification spec = requestSpecBuilder.build();
        System.out.println(requestSpecBuilder.log(LogDetail.ALL));
        serviceResponse = given().spec(spec).contentType("application/json").body(jsonRequest).post();
        return serviceResponse;
    }

    private String buildRequestJsonStr(String filter) {
        String requestJSONStr = null;
        String[] fields = filter.split(",");
        StringBuilder builder = new StringBuilder("{\"cusInfReq\": {");

        for(String field: fields) {
            switch (field.trim()) {
                case "xtraCard":
                    builder.append("\"xtraCard\": [" +
                            "\"xtraCardNbr\"," +
                            "\"totYtdSaveAmt\"," +
                            "\"cardLastScanDt\"," +
                            "\"cardMbrDt\"," +
                            "\"homeStoreNbr\"," +
                            "\"xtraCardCipherTxt\"" +
                            "],");
                    break;
                case "xtraCare":
                    builder.append("\"xtraCare\": [" +
                            "\"mktgTypeBenefits\"," +
                            "\"pts\"," +
                            "\"cpns\"," +
                            "\"mfrCpnAvailPool\"," +
                            "\"pebAvailPool\"," +
                            "\"hrMembers\"," +
                            "\"hrEvtDtl\"," +
                            "\"carePassCpns\"" +
                            "],");
                    break;
                case "xtraCarePrefs":
                    builder.append(" \"xtraCarePrefs\": [" +
                            "\"optInEc\"," +
                            "\"optInEmail\"," +
                            "\"beautyClub\"," +
                            "\"diabeticClub\"," +
                            "\"phr\"," +
                            "\"carePass\"," +
                            "\"paperlessCpns\"," +
                            "\"digitalReceipt\"" +
                            "],");
                    break;
                case "tables":
                    builder.append(" \"tables\": [" +
                            "{" +
                            "\"tableName\": \"customer\"," +
                            "\"colNames\": [" +
                            "\"first_name\"," +
                            "\"last_name\"," +
                            "\"middle_initial_txt\"," +
                            "\"birth_dt\"," +
                            "\"gndr_cd\"" +
                            "]" +
                            "}," +
                            "{" +
                            "\"tableName\": \"customer_email\"," +
                            "\"colNames\": [" +
                            "\"email_addr_txt\"," +
                            "\"email_addr_type_cd\"" +
                            "]," +
                            "\"criteria\": {" +
                            "\"colName\": \"email_pref_seq_nbr\"," +
                            "\"operation\": \"=\"," +
                            "\"value\": \""+email_seq_num+"\"" +
                            "}" +
                            "}," +
                            "{" +
                            "\"tableName\": \"customer_phone\"," +
                            "\"colNames\": [" +
                            "\"phone_area_cd_nbr\"," +
                            "\"phone_pfx_nbr\"," +
                            "\"phone_type_cd\"" +
                            "]," +
                            "\"functionName\": \"LPAD(to_char(phone_sfx_nbr),4,'0')PHONE_SFX_NBR\"," +
                            "\"criteria\": {" +
                            "\"colName\": \"phone_pref_seq_nbr\"," +
                            "\"operation\": \"=\"," +
                            "\"value\": \""+phone_seq_num+"\"" +
                            "}" +
                            "}," +
                            "{" +
                            "\"tableName\": \"customer_address\"," +
                            "\"colNames\": [" +
                            "\"addr1_txt\"," +
                            "\"addr2_txt\"," +
                            "\"city_name\"," +
                            "\"st_cd\"," +
                            "\"zip_cd\"" +
                            "]," +
                            "\"criteria\": {" +
                            "\"colName\": \"addr_pref_seq_nbr\"," +
                            "\"operation\": \"=\"," +
                            "\"value\": \""+addr_seq_num+"\"" +
                            "}" +
                            "}," +
                            "{" +
                            "\"tableName\": \"xtra_card_child\"," +
                            "\"colNames\": [" +
                            "\"birthday_dt\"" +
                            "]" +
                            "}," +
                            "{" +
                            "\"tableName\": \"xtra_card_analytic_event\"," +
                            "\"colNames\": [" +
                            "\"analytic_event_type_cd\"," +
                            "\"cpn_seq_nbr\"," +
                            "\"analytic_event_ts\"" +
                            "]" +
                            "}" +
                            "]");
                    break;
            }
        }
        builder.append(" }}");

        try {
            requestJSONStr = pretty(builder.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return requestJSONStr;
    }

    private String pretty(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode jsonNode = objectMapper.readTree(json);
        String prettyJson = objectMapper.writeValueAsString(jsonNode);

        return prettyJson;
    }
}