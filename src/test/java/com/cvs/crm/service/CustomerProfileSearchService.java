package com.cvs.crm.service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.CustomerProfileSearchRequest;
import com.cvs.crm.model.request.OpticalIntegrationRequest;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class CustomerProfileSearchService {
	private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;
    Gson gson = new Gson();
    @Autowired
	AuthorizationService authService;
    
    public void viewWrapperAPICustomerSearchByExtraCard(CustomerProfileSearchRequest getIBottaCustRequest, String msgSrcCd, String userId, int srcLocCd) throws ParseException {
    	String jsonBody = gson.toJson(getIBottaCustRequest);
        invokeCustomerProfileSearchAPI(jsonBody, srcLocCd, msgSrcCd, userId);
    }
    
    public void viewWrapperAPICustomerSearchByExtraCard(CustomerProfileSearchRequest opticalIntegrationRequest, String token, int srcLocCd, String msgSrcCd, String msgSrcSubCode, String userId) throws ParseException {
    	String jsonBody = gson.toJson(opticalIntegrationRequest);
        invokeCustomerProfileSearchAPI(jsonBody, token, srcLocCd, msgSrcCd, msgSrcSubCode, userId);
    }
    
    public void viewWrapperAPICustomerSearchByPhAndEmail(CustomerProfileSearchRequest getIBottaCustRequest, String msgSrcCd, String userId, int srcLocCd) throws ParseException {     
        String jsonBody = gson.toJson(getIBottaCustRequest);        
		invokeCustomerProfileSearchAPI(jsonBody, srcLocCd, msgSrcCd, userId);
    }
    
    private void invokeCustomerProfileSearchAPI(String json, int srcLocCd, String msgSrcCd,String userId) throws ParseException{
    	RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    	requestSpecBuilder.setBaseUri(serviceConfig.getCrmapiUrl())
        .addHeader("authorization", "Bearer "+authService.getOAuthToken())
        .addHeader("Content-Type", "application/json");
		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = given().log().all().spec(spec)
				.queryParam("src_loc_cd", srcLocCd)
				.queryParam("msg_src_cd", msgSrcCd)
				.queryParam("user_id", userId).body(json)
				.post("api/v1.1/customers/profile/search");
    }
    
    private void invokeCustomerProfileSearchAPI(String json, String token, int srcLocCd, String msgSrcCd, String msgSrcSubCode, String userId) throws ParseException{
    	RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    	requestSpecBuilder.setBaseUri(serviceConfig.getCrmapiUrl())
        .addHeader("authorization", "Bearer "+token)
        .addHeader("Content-Type", "application/json");
		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = given().log().all().spec(spec)
				.queryParam("src_loc_cd", srcLocCd)
				.queryParam("msg_src_cd", msgSrcCd)
				.queryParam("msg_sub_src_cd", msgSrcSubCode)
				.queryParam("user_id", userId).body(json)
				.post("api/v1.1/customers/profile/search");
    }
}
