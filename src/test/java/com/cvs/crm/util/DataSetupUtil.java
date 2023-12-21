package com.cvs.crm.util;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.response.dataResponse;
import com.cvs.crm.model.response.DataSetupResponse;
import lombok.extern.slf4j.Slf4j;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;
import java.text.ParseException;

@Service
@Data
@Slf4j
public class DataSetupUtil {
    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;

    public void hitDataApiPost(String api) throws ParseException {
        String apiRequested = api;
        String userId = "CVS.COM";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getDataapiUrl())
                .setBasePath("api/v1.1/data/{api_type}")
           //     .addPathParam("api_type", apiRequested)
                .addQueryParam("user_id", userId);
        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").post();

        int sCode = serviceResponse.getStatusCode();
        String res = String.valueOf(serviceResponse.getBody());
        log.info("hitDataApiPost status code: [{}] ", sCode);
        log.info("hitDataApiPost status res: [{}] ", res);
    }

    public void hitDataApiDelete(String api) throws ParseException {
        String apiRequested = api;
        String userId = "CVS.COM";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getDataapiUrl())
                .setBasePath("api/v1.1/data/{api_type}")
        //      .addPathParam("api_type", apiRequested)
                .addQueryParam("user_id", userId);
        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").delete();

        int sCode = serviceResponse.getStatusCode();
        String res = String.valueOf(serviceResponse.getBody());
        log.info("hitDataApiDelete status code: [{}] ", sCode);
        log.info("hitDataApiDelete status res: [{}] ", res);
    }
}
