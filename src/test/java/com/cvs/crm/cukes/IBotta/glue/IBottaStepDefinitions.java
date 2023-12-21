package com.cvs.crm.cukes.IBotta.glue;

import java.math.BigInteger;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.cukes.customerSearch.glue.customerSearchStepDefinitions;
import com.cvs.crm.model.request.CustomerProfileSearchRequest;
import com.cvs.crm.model.request.ProvisionCustomerRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.service.AuthorizationService;
import com.cvs.crm.service.CustomerProfileSearchService;
import com.cvs.crm.service.ProvisioningAPIService;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IBottaStepDefinitions extends SpringIntegrationTests implements En{
	
	 @Autowired
	 CustomerProfileSearchService customerProfileSearchService;
	 @Autowired
	 ProvisioningAPIService provisionService;
	 @Autowired
	 AuthorizationService authService;
	 
	 @Autowired
	 ServiceConfig serviceConfig;
	 String testDataFile;
	 @SuppressWarnings("deprecation")
	public IBottaStepDefinitions() {
		 CustomerProfileSearchRequest request = new CustomerProfileSearchRequest();
		 ProvisionCustomerRequest provisioingRequest = new ProvisionCustomerRequest();
		 customerSearchStepDefinitions customerSearchStepDefinitions = new customerSearchStepDefinitions();
		
		  Given("I am a iBotta user with {string}", (String scenario) -> { // Write code
			  		  
		  });
		  
		  When("I use my {string} to link with CVS loyalty account", (String extraCardNum) -> {
			  request.setCardNumber(extraCardNum);
			  request.setPartnerId("c127584ec9ba4487bb359af44d6de9ec");
			  request.setUniqUserId("10:24:123:456");
			  String msgSrcCd = "IB";
			  String userId = "STORE";
			  int srcLocCd= 590;
		 		
			  customerProfileSearchService.viewWrapperAPICustomerSearchByExtraCard(request, msgSrcCd, userId, srcLocCd);
		  });
				
		  When("I use my {string} and {string} to link with CVS loyalty account", (String phNumber, String email) -> {
			  if(!phNumber.equals("")) {request.setPhoneNumber(new BigInteger(phNumber));}
			  request.setEmailAddressText(email);
			  request.setPartnerId("c127584ec9ba4487bb359af44d6de9ec");
			  request.setUniqUserId("10:24:123:456");
			  String msgSrcCd = "IB";
			  String userId = "CVS.COM";
			  int srcLocCd= 2695;
			  customerProfileSearchService.viewWrapperAPICustomerSearchByPhAndEmail(request, msgSrcCd, userId, srcLocCd);
			});
		  
		  Then("I found the customer profile with CVS loyalty account", () -> {
			  customerProfileSearchService.getServiceResponse().then().log().all().statusCode(200);
          });
		  
		  When("CVS sends an email to the customer with the link", () -> {
			  ApiErrorResponse apiErrorResponse = customerProfileSearchService.getServiceResponse().as(ApiErrorResponse.class);
			  Assert.assertTrue(apiErrorResponse.getStatusCd() == 0);
          });
		  
		  When("The customer access the email link", () -> {
			  provisioingRequest.setToken("01N2JiYzcxMGM3MTMzOTdjNmEyZTUxODU5MGRlNGRkY2Q1ZDk4NDgxMDcwMjFhZjMwNGIwZWUwMWM1YTdjMWU4NUJJY2U5ZWQ2ZDQ0ZmE5NTNiYjc4NDRhYjljZTQ4NTcyMWMgICAgODY0NjQ3NzY0ODU2MTQ=");
			  String msgSrcCd = "IB";
			  String userId = "CVS.COM";
			  int srcLocCd= 590;
			  provisionService.provisionCustomerWithToken(provisioingRequest, msgSrcCd, userId, srcLocCd);
          });
		  
		  Then("The customer linked to IBotta successfully", () -> {
			  customerProfileSearchService.getServiceResponse().then().log().all().statusCode(200);
          });
		  
		  Then("I do not found the customer profile with CVS", () -> {
			  customerProfileSearchService.getServiceResponse().then().log().all().statusCode(400);
          });
		  
		  Then("I validate the {string}", (String errorMessage) -> {
			  ApiErrorResponse apiErrorResponse = customerProfileSearchService.getServiceResponse().as(ApiErrorResponse.class);
			  Assert.assertEquals(errorMessage, apiErrorResponse.getErrorMsg());
		  });
		  
		  Then("CVS sends {string} to iBotta", (String errorMessage) -> {
			  ApiErrorResponse apiErrorResponse = customerProfileSearchService.getServiceResponse().as(ApiErrorResponse.class);
			  Assert.assertEquals(errorMessage, apiErrorResponse.getErrorMsg());
		  });
		  
		  Given("I have customer details", () -> {
			  
		  });
		  
		  When("I create a customer with data file {string}", (String str) -> {
			  testDataFile = str;
			  
			  customerSearchStepDefinitions.extractDataAndSetupTestData_searchCustomer(testDataFile);
		  });
		  
		  When("The customer verified using the email link", () -> {
			  provisioingRequest.setToken("01N2JiYzcxMGM3MTMzOTdjNmEyZTUxODU5MGRlNGRkY2Q1ZDk4NDgxMDcwMjFhZjMwNGIwZWUwMWM1YTdjMWU4NUJJY2U5ZWQ2ZDQ0ZmE5NTNiYjc4NDRhYjljZTQ4NTcyMWMgICAgODY0NjQ3NzY0ODU2MTQ=");
			  String msgSrcCd = "IB";
			  String userId = "CVS.COM";
			  int srcLocCd= 590;
			  provisionService.provisionCustomerWithToken(provisioingRequest, msgSrcCd, userId, srcLocCd);
		  });
		  
		  Then("The customer received {string}", (String errorMessage) -> {
			  ApiErrorResponse apiErrorResponse = provisionService.getServiceResponse().as(ApiErrorResponse.class);
			  Assert.assertEquals(errorMessage, apiErrorResponse.getErrorMsg());
		  });
	 }
	 
}
