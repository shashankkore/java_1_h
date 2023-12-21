package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.GetCustRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.*;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class GetCustXtraCardCipherTxtService {

    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;

    public void getServiceResponse(GetCustRequest getCustRequest, String requestParam) {
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
        String jsonRequest = "{\n" +
                " \"cusInfReq\": {\n" +
                "\"xtraCard\": [\n" +
                " \""+requestParam+"\"\n" +
                " ]}\n" +
                "}";

        if (getCustRequest.getVersion().equals("1.1")) {
            requestSpecBuilder.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                    .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                    .addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr());
            addQueryParams(requestSpecBuilder, msgSrcCd, userId, srcLocCd);
            getPostResponse(requestSpecBuilder, pretty(jsonRequest));
        } else {
            requestSpecBuilder.setBasePath("api/v1.2/customers/" + getCustRequest.getSearchCardType() + "," + getCustRequest.getSearchCardNbr());
            addQueryParams(requestSpecBuilder, msgSrcCd, userId, srcLocCd);
            getPostResponse(requestSpecBuilder, pretty(jsonRequest));
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

    private String pretty(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(json);
        return gson.toJson(je);
    }
}