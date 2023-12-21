package com.cvs.crm.util;

import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;


@Service
@Data
@Slf4j
public class SetCustPreferencesUtil {
    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    CarePassDateUtil carePassDateUtil;


    public void enrollBCPrefSetCust(String cardTyp, int cardNum, String msgSrCd, String opt) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        String msgSrcCd = msgSrCd;
        String userId = "CVS.COM";
        int srcLocCd = 90046;
        String jsonString = "{\r\n" +
                " \"xtraCare\": {\n" +
                " \"prefs\" : {\n" +
                " \"beautyClub\": {\n" +
                " \"optCd\": \"" + opt + "\"\n" +
                " }\n" +
                " }\n" +
                " },\n" +
                " \"deviceInfo\": {\n" +
                " \"deviceTypeCd\": \"\",\n" +
                " \"deviceVerCd\": \"\",\n" +
                " \"deviceUniqIdTypeCd\": \"\",\n" +
                " \"deviceUniqId\": \"\",\n" +
                " \"appVer\": \"\"\n" +
                " }\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("SetCustEnrollBeautyClub code: [{}] ", sCode);

    }
}
