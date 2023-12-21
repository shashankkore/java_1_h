package com.cvs.crm.cukes.opticalintegration.glue;

import java.math.BigInteger;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.CustomerProfileSearchRequest;
import com.cvs.crm.model.request.OpticalIntegrationRequest;
import com.cvs.crm.model.response.OpticalResponse;
import com.cvs.crm.service.AuthorizationService;
import com.cvs.crm.service.CustomerProfileSearchService;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpticalIntegrationStepDefinitions extends SpringIntegrationTests implements En{
	String username, pwd, tkn;
	Integer xtraCardNum;
	Integer phoneNum;
	String emailAdd;
	String msgSrcCd, msgSrcSubCode, userId;
	Integer srcLocCd;
	
	@Autowired
	CustomerProfileSearchService customerProfileSearchService;
	
	@Autowired
	AuthorizationService authorizationService;
	
	@Autowired
	ServiceConfig serviceConfig;

	String token = "";
	public OpticalIntegrationStepDefinitions() {
		OpticalIntegrationRequest request = new OpticalIntegrationRequest();
		CustomerProfileSearchRequest customerProfileSearchRequest = new CustomerProfileSearchRequest();
	
		Given("{string}", (String scenario) -> {
		});
	 
	 Given("I use my user_id as {string} and Password as {string}", (String userId, String password) -> {
		 request.setUserId(userId);
		 request.setPassword(password);
		 username = userId;
		 pwd = password;
     });
	 
	 When("I use my Extra Card number as {string} for {string}", (String xtraCardNum, String user) -> {
		 customerProfileSearchRequest.setCardNumber(xtraCardNum);
		 token = authorizationService.getServiceResponse().then().extract().response()
         .jsonPath().getString("accessToken");
		 if(user.equalsIgnoreCase("Ocuco")) {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="O";
			 userId = "CVS.COM";
			 srcLocCd= 2695;
		 }else {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="A";
			 userId = "STORE";
			 srcLocCd= 590;
		 }
		 
		customerProfileSearchService.viewWrapperAPICustomerSearchByExtraCard(customerProfileSearchRequest, token, srcLocCd, msgSrcCd, msgSrcSubCode, userId);
    
	 });
	 
	 When("I fetch my auth token", () -> {
		 authorizationService.getOAuthToken("CLIENT_CREDENTIALS", request.getUserId(), request.getPassword());
     });
	 
	 When("I use invalid AuthToken", () -> {
		 token = "123456";
     });
	 
	 When("I use my {string} as {string} for {string}", (String input, String inputValue, String user) -> {
		 
		 switch(input) {
			 case "Phone_number":
				 customerProfileSearchRequest.setPhoneNumber(new BigInteger(inputValue));
				 break;
			 case "Email_Address":
				 customerProfileSearchRequest.setEmailAddressText(inputValue);
				 break;
			 case "Extra_Card":
				 customerProfileSearchRequest.setCardNumber(inputValue);
				 break;
			default:
				break;
		 }
		 
		 if(user.equalsIgnoreCase("Ocuco")) {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="O";
			 userId = "CVS.COM";
			 srcLocCd= 2695;
		 }else {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="A";
			 userId = "STORE";
			 srcLocCd= 590;
		 }
		 
		customerProfileSearchService.viewWrapperAPICustomerSearchByExtraCard(customerProfileSearchRequest, token, srcLocCd, msgSrcCd, msgSrcSubCode, userId);
     });
	 
	 When("I use my Phone_number as {string} for {string}", (String phNumber, String user) -> {
		 customerProfileSearchRequest.setPhoneNumber(new BigInteger(phNumber));
		 token = authorizationService.getServiceResponse().then().extract().response()
		         .jsonPath().getString("accessToken");
		 if(user.equalsIgnoreCase("Ocuco")) {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="O";
			 userId = "CVS.COM";
			 srcLocCd= 2695;
		 }else {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="A";
			 userId = "STORE";
			 srcLocCd= 590;
		 }
		 
		customerProfileSearchService.viewWrapperAPICustomerSearchByExtraCard(customerProfileSearchRequest, token, srcLocCd, msgSrcCd, msgSrcSubCode, userId);
     });
	 
	 When("I use my email as {string} for {string}", (String email, String user) -> {
		 customerProfileSearchRequest.setEmailAddressText(email);
		 token = authorizationService.getServiceResponse().then().extract().response()
		         .jsonPath().getString("accessToken");
		 if(user.equalsIgnoreCase("Ocuco")) {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="O";
			 userId = "CVS.COM";
			 srcLocCd= 2695;
		 }else {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="A";
			 userId = "STORE";
			 srcLocCd= 590;
		 }
		 
		customerProfileSearchService.viewWrapperAPICustomerSearchByExtraCard(customerProfileSearchRequest, token, srcLocCd, msgSrcCd, msgSrcSubCode, userId);
     });
	 
	 When("I use my {string} and {string} for {string}", (String email, String phNumber, String user) -> {
		 customerProfileSearchRequest.setEmailAddressText(email);
		 customerProfileSearchRequest.setPhoneNumber(new BigInteger(phNumber));
		 token = authorizationService.getServiceResponse().then().extract().response()
		         .jsonPath().getString("accessToken");
		 if(user.equalsIgnoreCase("Ocuco")) {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="O";
			 userId = "CVS.COM";
			 srcLocCd= 2695;
		 }else {
			 msgSrcCd = "OP";
			 msgSrcSubCode ="A";
			 userId = "STORE";
			 srcLocCd= 590;
		 }
		 
		customerProfileSearchService.viewWrapperAPICustomerSearchByExtraCard(customerProfileSearchRequest, token, srcLocCd, msgSrcCd, msgSrcSubCode, userId);
     });
	 
	 When("I use my Email_Address as {string}", (String email_add) -> {
			request.setSearchEmailAddress(email_add);
			emailAdd = email_add;
	 });
	 
	 When("I validate my credentials", () -> {
		 customerProfileSearchService.getServiceResponse().then().log().all().statusCode(200);
     });
	 
	 When("I find a matching profile", () -> {
		 customerProfileSearchService.getServiceResponse().then().log().all().statusCode(200);
     });
	 
	 Then("I dont find a matching profile", () -> {
		 customerProfileSearchService.getServiceResponse().then().log().all().statusCode(400);
     });
	 
	 Then("I do get unauthorized error", () -> {
		 customerProfileSearchService.getServiceResponse().then().log().all().statusCode(401);
     });
	 
	 Then("I receive encoded XtraCardNbr as {string}", (String encoded_xtra_cardnbr) -> {
		 OpticalResponse opticalResponse = customerProfileSearchService.getServiceResponse()
					.as(OpticalResponse.class);
			String actualEncodedXtraCardNbr = opticalResponse.getEncodedXtraCardNumber();
			Assert.assertEquals(encoded_xtra_cardnbr, actualEncodedXtraCardNbr);
		});
	 
	 Then("I dont find the Customer details", () -> {
		 customerProfileSearchService.getServiceResponse().then().log().all().statusCode(400);
     });
	 
	 Then("I find the Customer details", () -> {
		 customerProfileSearchService.getServiceResponse().then().log().all().statusCode(200);
     });
	 
	 Then("I receive a token", () -> {
		 authorizationService.getServiceResponse().then().log().all().statusCode(200);
     });

	 Then("I see an error_code as {int}", (Integer error_code) -> {
		 customerProfileSearchService.getServiceResponse().then().log().all().statusCode(error_code);
     });
	 
	 Then("I see unauthorized error_code as {int}", (Integer error_code) -> {
		 authorizationService.getServiceResponse().then().log().all().statusCode(error_code);
     });
	 
	 Then("I validate my Extra card number {string}", (String xtra_cardnbr) -> {
		 OpticalResponse opticalResponse = customerProfileSearchService.getServiceResponse()
					.as(OpticalResponse.class);
			String actualXtraCardNbr = opticalResponse.getEncodedXtraCardNumber();
			Assert.assertEquals(xtra_cardnbr, actualXtraCardNbr);
     });

	 
}

}