package com.cvs.crm.service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.ProvisionCustomerRequest;
import com.google.gson.Gson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class ProvisioningAPIService {
	private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;
    Gson gson = new Gson();
    @Autowired
	AuthorizationService authService;
    
    public void provisionCustomerWithToken(ProvisionCustomerRequest getProvisioningAPIRequest, String msgSrcCd, String userId, int srcLocCd) throws ParseException {
    	String jsonBody = gson.toJson(getProvisioningAPIRequest);
    	invokeProvisioningAPI(jsonBody, srcLocCd, msgSrcCd, userId);
    }
    
    public void viewWrapperAPICustomerSearchByExtraCard(ProvisionCustomerRequest getProvisioningAPIRequest, String token, int srcLocCd, String msgSrcCd, String msgSrcSubCode, String userId) throws ParseException {
    	String jsonBody = gson.toJson(getProvisioningAPIRequest);
        invokeCustomerProfileSearchAPI(jsonBody, token, srcLocCd, msgSrcCd, msgSrcSubCode, userId);
    }
    
    public void viewWrapperAPICustomerSearchByPhAndEmail(ProvisionCustomerRequest getProvisioningAPIRequest, String msgSrcCd, String userId, int srcLocCd) throws ParseException {     
        String jsonBody = gson.toJson(getProvisioningAPIRequest);        
        invokeProvisioningAPI(jsonBody, srcLocCd, msgSrcCd, userId);
    }
    
    private void invokeProvisioningAPI(String json, int srcLocCd, String msgSrcCd,String userId) throws ParseException{
    	RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    	requestSpecBuilder.setBaseUri(serviceConfig.getCrmapiUrl())
        .addHeader("authorization", "Bearer "+authService.getOAuthToken())
        .addHeader("Content-Type", "application/json");
		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = given().log().all().spec(spec)
				.queryParam("src_loc_cd", srcLocCd)
				.queryParam("msg_src_cd", msgSrcCd)
				.queryParam("user_id", userId).body(json)
				.post("api/v1.1/customers/0006,58e1c7a5c10ee0b403fa1207018489d5dcdd4ed095815e2a6c793317c017cbb7/provision");
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
				.post("api/v1.1/customers/0006,58e1c7a5c10ee0b403fa1207018489d5dcdd4ed095815e2a6c793317c017cbb7/provision");
    }
}
