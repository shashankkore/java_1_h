package com.cvs.crm.cukes.digitalreceipts.glue;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.cukes.customerSearch.glue.customerSearchStepDefinitions;
import com.cvs.crm.model.request.CustRecPrefRequest;
import com.cvs.crm.model.request.HREnrollRequest;
import com.cvs.crm.model.request.ProvisionCustomerRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.request.TLogRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.service.AuthorizationService;
import com.cvs.crm.service.BeautyClubService;
import com.cvs.crm.service.CustomerProfileSearchService;
import com.cvs.crm.service.CustomerReceiptPreferenceService;
import com.cvs.crm.service.PharmacyHealthRewardsService;
import com.cvs.crm.service.ProvisioningAPIService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.service.TransactionLogService;
import com.cvs.crm.util.TestDataSetupUtil;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DigitalReceiptsStepDefinitions extends SpringIntegrationTests implements En{
	
	 @Autowired
	 CustomerProfileSearchService customerProfileSearchService;
	 @Autowired
	 ProvisioningAPIService provisionService;
	 @Autowired
	 AuthorizationService authService;
	 
	 @Autowired
	 TestDataSetupUtil testDataSetupUtil;
	 
	 @Autowired
	 SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;
	 
	 @Autowired
	 BeautyClubService beautyClubService;
	 
	 @Autowired
	 TransactionLogService transactionLogService;
	 
	 @Autowired
	 CustomerReceiptPreferenceService customerReceiptPreferenceService;
	 
	 @Autowired
	 PharmacyHealthRewardsService pharmacyHealthRewardsService;
	 @Autowired
	 ServiceConfig serviceConfig;
	 String testDataFile;
	 
	@SuppressWarnings("deprecation")
	public DigitalReceiptsStepDefinitions() {
		 ProvisionCustomerRequest provisioingRequest = new ProvisionCustomerRequest();
		 HREnrollRequest hrrequest = new HREnrollRequest();
		 customerSearchStepDefinitions customerSearchStepDefinitions = new customerSearchStepDefinitions();
		 SetCustRequest setCustRequest = new SetCustRequest();
		 TLogRequest tlogRequest = new TLogRequest();
		 CustRecPrefRequest custRecPrefRequest = new CustRecPrefRequest();
		 
		  Given("I am a CVS user with {int}", (Integer xtraCardNumber) -> { 
			  testDataSetupUtil.createUserWithXtraCardNbr(xtraCardNumber);
			  setCustRequest.setSearchCardNbr(xtraCardNumber);
			  tlogRequest.setXtraCardNbr(xtraCardNumber);
			  tlogRequest.setEncodedXtraCardNbr(testDataSetupUtil.getEncodedExtraCardNumber(setCustRequest.getSearchCardNbr()));
		  });
		  
		  Given("The customer is enrollend in Carepass {string}", (String carepassEnrolled) -> { 
			 if(carepassEnrolled.equalsIgnoreCase("Yes")) {
				 setCustRequest.setSearchCardType("0002");
	          	 setCustCarepassEnrollmentService.setCustCarePassEnrollMonthly(setCustRequest);
			 }
		  });
		  
		  Given("The customer is enrollend in Beauty Club {string}", (String bcEnrolled) -> { 
			  if(bcEnrolled.equalsIgnoreCase("Yes")) {
				  beautyClubService.createBeautyClubTestData();
			  }
		  });
		  
		  Given("The customer is enrollend in PHR {string}", (String phrEnrolled) -> { 
			  if(phrEnrolled.equalsIgnoreCase("Yes")) {
				  hrrequest.setSearchEncodedXtraCardNbr((tlogRequest.getEncodedXtraCardNbr()).toString());
				  pharmacyHealthRewardsService.phrEnroll(hrrequest);
			  }
		  });
		  
		  When("The customer make the transaction with digital receipts", () -> {
			  transactionLogService.createTransactionforGivenXTraCardNbr(tlogRequest);
		  });
				
		  Then("The user verify the transaction is completed successfully", () -> {
			  transactionLogService.getServiceResponse().then().log().all().statusCode(200);
          });
		  
		  When("The customer access the digital receipts", () -> {
		   		customerReceiptPreferenceService.extractXMLForDigitalReceipts(custRecPrefRequest);
		  });
				
		  Then("The user verify the saving with digital receipts", () -> {
			  customerReceiptPreferenceService.getServiceResponse().then().log().all().statusCode(200);
          });
		  
		  Then("The user verified no savings with digital receipts", () -> {
			  customerReceiptPreferenceService.getServiceResponse().then().log().all().statusCode(200);
          });
		  
		  Then("The customer received {string}", (String errorMessage) -> {
			  ApiErrorResponse apiErrorResponse = provisionService.getServiceResponse().as(ApiErrorResponse.class);
			  Assert.assertEquals(errorMessage, apiErrorResponse.getErrorMsg());
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
