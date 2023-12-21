package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class GetCustBeautyClubRelaunchService {

    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;


    public void viewGetCustBeautyClubRelaunch(GetCustRequest getCustRequest) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
        if ("MOBILE".equalsIgnoreCase(getCustRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        }
        String jsonRequest = "{\n" +
                "  \"cusInfReq\": {\n" +
                "    \"xtraCard\": [\n" +
                "      \"xtraCardNbr\"\n" +
                "    ],\n" +
                "    \"xtraCare\": [\n" +
                "      \"pts\",\n" +
                "      \"cpns\"\n" +
                "    ],\n" +
                "   \"xtraCarePrefs\": [\n" +
                "   \"beautyClub\"\n" +
                "  ]\n" +
                "  }\n" +
                "}\n";

        requestSpecBuilder.setBaseUri(serviceConfig.getGetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                .addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonRequest).post();

    }
}