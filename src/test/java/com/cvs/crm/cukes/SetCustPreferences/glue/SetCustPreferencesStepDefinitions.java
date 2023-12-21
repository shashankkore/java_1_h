package com.cvs.crm.cukes.SetCustPreferences.glue;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.CarepassEnrollFormHist;
import com.cvs.crm.model.data.CarepassMemberSmry;
import com.cvs.crm.model.data.CarepassMemberStatusHist;
import com.cvs.crm.model.data.CustomerOpt;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.SetCustResponse;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.SetCustPreferencesService;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetCustPreferencesStepDefinitions extends SpringIntegrationTests implements En {

	Integer xtraCardNum, custId = null;

	@Autowired
	SetCustPreferencesService setCustPreferencesService;

	@Autowired
	ServiceConfig serviceConfig;

	@Autowired
	DateUtil dateUtil;

	@Autowired
	CarePassDateUtil carePassDateUtil;

	@Autowired
	XtraCardActiveCoupon xtraCardActiveCoupon;

	@Autowired
	CarepassMemberSmry carepassMemberSmry;

	@Autowired
	CarepassEnrollFormHist carepassEnrollFormHist;

	@Autowired
	CarepassMemberStatusHist carepassMemberStatusHist;

	@Autowired
	XtraCardDao xtraCardDao;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	CustomerOpt customerOpt;

	public SetCustPreferencesStepDefinitions() {

		{
			SetCustRequest request = new SetCustRequest();

			Given("{string}", (String scenario) -> {
			});

			Given("I use {string}", (String channel) -> {
				request.setChannel(channel);
			});

			Given("I use my Extra Card {int} and {int}", (Integer xtraCardNbr, Integer custid) -> {
				request.setSearchCardType("0002");
				request.setSearchCardNbr(xtraCardNbr);
				xtraCardNum = xtraCardNbr;
				custId = custid;

			});

			Then("I see the SetCust Response", () -> {
				setCustPreferencesService.getServiceResponse().then().log().all().statusCode(200);
			});

			Then("I don't see the SetCust Response", () -> {
				setCustPreferencesService.getServiceResponse().then().log().all().statusCode(400);
			});

			Then("I see the xtra card nbr as {int}", (Integer xtra_card_nbr) -> {
				SetCustResponse setcustResponse = setCustPreferencesService.getServiceResponse()
						.as(SetCustResponse.class);
				Assert.assertEquals(xtra_card_nbr, setcustResponse.getXtraCardNbr());

			});

			Then("I see the encoded XtraCardNbr as {long}", (Long encoded_xtra_cardnbr) -> {
				SetCustResponse setcustResponse = setCustPreferencesService.getServiceResponse()
						.as(SetCustResponse.class);
				Long actualEncodedXtraCardNbr = setcustResponse.getEncodedXtraCardNbr();
				Assert.assertEquals(encoded_xtra_cardnbr, actualEncodedXtraCardNbr);
			});

			Then("I see my Error Code as {int}", (Integer errorCd) -> {

				ApiErrorResponse apiErrorResponse = setCustPreferencesService.getServiceResponse()
						.as(ApiErrorResponse.class);
				Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
			});

			Then("I see Error Message as {string}", (String errorMsg) -> {
				ApiErrorResponse apiErrorResponse = setCustPreferencesService.getServiceResponse()
						.as(ApiErrorResponse.class);
				Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
			});

			When("I want to save preferences for {string} as {string}", (String type, String opt) -> {
				setCustPreferencesService.viewSetCustPreferences(request, type, opt);
			});

			Then("I save opt cd as {string}", (String opt_cd) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					customerOpt.setCustId(custId);
					String actualOptCd = customerDao.selectCustomerOptCd(customerOpt);
					Assert.assertEquals(actualOptCd, opt_cd);
				}
			});

			Then("I save opt type cd as {string}", (String opt_type_cd) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					customerOpt.setCustId(custId);
					String actualOptTypCd = customerDao.selectCustomerOptTypCd(customerOpt);
					Assert.assertEquals(actualOptTypCd, opt_type_cd);
				}
			});

		}
	}
}
