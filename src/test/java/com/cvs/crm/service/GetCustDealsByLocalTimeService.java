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
public class GetCustDealsByLocalTimeService {

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

    public void getServiceResponse(GetCustRequest getCustRequest, String requestParams,String localTime) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(serviceConfig.getGetcustUrl());
        String msgSrcCd;
        String userId;
        int srcLocCd;
        if (getCustRequest.getChannel().equalsIgnoreCase("M")) {
            msgSrcCd = "M";
            srcLocCd = 90042;
            userId = "MOBILE_ENT";
        } else if (getCustRequest.getChannel().equalsIgnoreCase(("W"))) {
            msgSrcCd = "W";
            srcLocCd = 2695;
            userId = "CVS.COM";
        } else if (getCustRequest.getChannel().equalsIgnoreCase(("R"))){
            msgSrcCd = "R";
            srcLocCd = 68585;
            userId = "STORE";
        } else  if ("I".equalsIgnoreCase(getCustRequest.getChannel())) {
            msgSrcCd = "I";
            userId = "IVR";
            srcLocCd = 0;

        }
          else
              throw new IllegalArgumentException("Invalid Channel passed - " + getCustRequest.getChannel());


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
        String jsonRequest = buildRequestJsonStr(requestParams,localTime);

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

    private String buildRequestJsonStr(String filter,String localTime) {
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
                case "dealByLocalTime":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"mktgTypeBenefits\"," +
                            "\"pts\"," +
                            "\"cpns\"," +
                            "\"mfrCpnAvailPool\"," +
                            "\"pebAvailPool\"," +
                            "\"hrMembers\"," +
                            "\"hrEvtDtl\"," +
                            "\"carePassCpns\"" +
                            "]");
                    break;
                case "pts":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"pts\"" +
                            "]");
                    break;
                case "cpns":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"cpns\"" +
                            "]");
                    break;
                case "mfrCpnAvailPool":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"mfrCpnAvailPool\"" +
                            "]");
                    break;
                case "pebAvailPool":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"pebAvailPool\"" +
                            "]");
                    break;
                case "hrMembers":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"hrMembers\"" +
                            "]");
                    break;
                case "hrEvtDtl":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"hrEvtDtl\"" +
                            "]");
                    break;
                case "carePassCpns":
                    builder.append("\"txnTs\":\"" + localTime+ "\"," +
                            "\"xtraCare\": [" +
                            "\"carePassCpns\"" +
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