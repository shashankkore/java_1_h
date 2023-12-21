package com.cvs.crm.service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.CustomerProfileSearchRequest;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class AuthorizationService {
	private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;
    Gson gson = new Gson();
    
    public String getOAuthToken( ) throws ParseException {
		String token="";
    	
		token = given().log().all().auth().none()
    			.formParam("grantType", "CLIENT_CREDENTIALS")
    			.formParam("clientId", "520da66c-f270-4a63-8f18-1f3f934093fb")
    			.formParam("clientSecret", "d94917c3-0bd2-4cf0-86f8-d3ea9a53021b")
    			.post(serviceConfig.getCrmapiUrl()+"oauth/token")
    			.then().extract().response()
                .jsonPath().getString("accessToken");
    	System.out.println("----token----"+ token);
    	return token;
    }
    
    public void getOAuthToken(String grantType, String clinetId, String clientSecret) throws ParseException {
		serviceResponse = given().log().all().auth().none()
    			.formParam("grantType", grantType)
    			.formParam("clientId", clinetId)
    			.formParam("clientSecret", clientSecret)
    			.post(serviceConfig.getCrmapiUrl()+"oauth/token");
    }
    
}
