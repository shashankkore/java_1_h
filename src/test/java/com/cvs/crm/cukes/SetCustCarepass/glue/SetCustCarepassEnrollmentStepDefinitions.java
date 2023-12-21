package com.cvs.crm.cukes.SetCustCarepass.glue;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.CarepassEnrollFormHist;
import com.cvs.crm.model.data.CarepassMemberSmry;
import com.cvs.crm.model.data.CarepassMemberStatusHist;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.SetCustResponse;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.SetCustCarepassEnrollmentService;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetCustCarepassEnrollmentStepDefinitions extends SpringIntegrationTests implements En {

	Integer xtraCardNum = null;

	@Autowired
	SetCustCarepassEnrollmentService setCustCarepassEnrollmentService;

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
	CarePassDao carePassDao;

	public SetCustCarepassEnrollmentStepDefinitions() {

		{
			SetCustRequest request = new SetCustRequest();

			Given("{string}", (String scenario) -> {
			});

			Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
				request.setSearchCardType("0002");
				request.setSearchCardNbr(xtraCardNbr);
				xtraCardNum = xtraCardNbr;
			});

			When("I enroll in carepass using setCust for type {string}", (String type) -> {

				setCustCarepassEnrollmentService.viewSetCustCarePassEnrollment(request, type);
			});

			Then("I see the SetCust Response", () -> {
				setCustCarepassEnrollmentService.getServiceResponse().then().log().all().statusCode(200);
			});

			Then("I don't see the SetCust Response", () -> {
				setCustCarepassEnrollmentService.getServiceResponse().then().log().all().statusCode(400);
			});

			Then("I see the xtra card nbr as {int}", (Integer xtra_card_nbr) -> {
				SetCustResponse setcustResponse = setCustCarepassEnrollmentService.getServiceResponse()
						.as(SetCustResponse.class);
				Assert.assertEquals(xtra_card_nbr, setcustResponse.getXtraCardNbr());

			});

			Then("I see the encoded XtraCardNbr as {long}", (Long encoded_xtra_cardnbr) -> {
				SetCustResponse setcustResponse = setCustCarepassEnrollmentService.getServiceResponse()
						.as(SetCustResponse.class);
				Long actualEncodedXtraCardNbr = setcustResponse.getEncodedXtraCardNbr();
				Assert.assertEquals(encoded_xtra_cardnbr, actualEncodedXtraCardNbr);
			});

			Then("I get 10 $ coupon {string}", (String coupon_10) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					String[] cardNbrCampNbr = coupon_10.split("_");
					int cardNbr = Integer.parseInt(cardNbrCampNbr[0]);
					int campNbr = Integer.parseInt(cardNbrCampNbr[1]);
					xtraCardActiveCoupon.setXtraCardNbr(cardNbr);
					xtraCardActiveCoupon.setCmpgnId(campNbr);
					Integer ExpectedCpnCnt = 1;
					Integer ActualCpnCnt = xtraCardDao.selectCouponsXtraCardActiveCoupon(xtraCardActiveCoupon);
					Assert.assertEquals(ExpectedCpnCnt, ActualCpnCnt);
				}

			});

			Then("I get 20 % coupon {string}", (String coupon_20) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					String[] cardNbrCampNbr = coupon_20.split("_");
					int cardNbr = Integer.parseInt(cardNbrCampNbr[0]);
					int campNbr = Integer.parseInt(cardNbrCampNbr[1]);
					xtraCardActiveCoupon.setXtraCardNbr(cardNbr);
					xtraCardActiveCoupon.setCmpgnId(campNbr);
					Integer ExpectedCpnCnt = 1;
					Integer ActualCpnCnt = xtraCardDao.selectCouponsXtraCardActiveCoupon(xtraCardActiveCoupon);
					Assert.assertEquals(ExpectedCpnCnt, ActualCpnCnt);
				}

			});

			Then("I see enroll status as {string}", (String status) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					carepassMemberSmry.setXtrCardNbr(xtraCardNum);
					carepassMemberStatusHist.setXtraCardNbr(xtraCardNum);
					carepassMemberStatusHist.setMbrStatusCd("E");
					String actualStatusSummary = carePassDao.selectCarepassMemberSmryStat(carepassMemberSmry);
					Assert.assertEquals(actualStatusSummary, status);
				}
			});

			Then("I see reactivate status as {string}", (String status) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					carepassMemberSmry.setXtrCardNbr(xtraCardNum);
					String actualStatusSummary = carePassDao.selectCarepassMemberSmryStat(carepassMemberSmry);
					Assert.assertEquals(actualStatusSummary, status);
				}
			});

			Then("I see status as {string}", (String status) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					carepassMemberSmry.setXtrCardNbr(xtraCardNum);
					carepassMemberStatusHist.setXtraCardNbr(xtraCardNum);
					carepassMemberStatusHist.setMbrStatusCd("U");
					String actualStatusSummary = carePassDao.selectCarepassMemberSmryStat(carepassMemberSmry);
					String actualStatusHist = carePassDao.selectcarepassMemberStatusHistStat(carepassMemberStatusHist);
					Assert.assertEquals(actualStatusSummary, status);
					Assert.assertEquals(actualStatusHist, status);
				}
			});

			Then("I see hold status as {string}", (String status) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					carepassMemberSmry.setXtrCardNbr(xtraCardNum);
					String actualStatusSummary = carePassDao.selectCarepassMemberSmryStat(carepassMemberSmry);
					Assert.assertEquals(actualStatusSummary, status);
				}
			});

			Then("I see benefit eligibility dt as {int}", (Integer benefiteligibilitydt) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					String expectedBenefitEligibilityDt = (carePassDateUtil.carePassExpireTswtz(benefiteligibilitydt))
							.substring(0, 10);
					carepassMemberSmry.setXtrCardNbr(xtraCardNum);
					String actualBenefitEligibilityDtSummary = carePassDao
							.selectCarepassMemberSmryBEdt(carepassMemberSmry);
					System.out.println("actualBenefitEligibilityDtSummary----:"+actualBenefitEligibilityDtSummary);
					Assert.assertEquals(actualBenefitEligibilityDtSummary.substring(0, 10),
							expectedBenefitEligibilityDt);
				}
			});

			Then("I see expire date as {int}", (Integer expiredt) -> {
				if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
				} else {
					String expectedexpiredt = (carePassDateUtil.carePassExpireTswtz(expiredt)).substring(0, 10);
					carepassMemberSmry.setXtrCardNbr(xtraCardNum);
					String actualexpiredtSummary = carePassDao.selectCarepassMemberSmryBEdt(carepassMemberSmry);
					System.out.println("actualexpiredtSummary----:"+actualexpiredtSummary);
					Assert.assertEquals(actualexpiredtSummary.substring(0, 10), expectedexpiredt.substring(0, 10));
				}
			});

			Then("I see my Error Code as {int}", (Integer errorCd) -> {

				ApiErrorResponse apiErrorResponse = setCustCarepassEnrollmentService.getServiceResponse()
						.as(ApiErrorResponse.class);
				Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
			});

			Then("I see Error Message as {string}", (String errorMsg) -> {
				ApiErrorResponse apiErrorResponse = setCustCarepassEnrollmentService.getServiceResponse()
						.as(ApiErrorResponse.class);
				Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
			});

		}
	}
}
