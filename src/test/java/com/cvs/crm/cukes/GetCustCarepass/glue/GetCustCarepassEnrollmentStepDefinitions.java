package com.cvs.crm.cukes.GetCustCarepass.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.response.*;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.GetCustCarepassEnrollmentService;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import com.cvs.crm.util.DateUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
public class GetCustCarepassEnrollmentStepDefinitions extends SpringIntegrationTests implements En {

    Integer xtraCardNum = null;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

    @Autowired
    GetCustCarepassEnrollmentService getCustCarepassEnrollmentService;

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

    public GetCustCarepassEnrollmentStepDefinitions() {

        {
            GetCustRequest request = new GetCustRequest();

            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
                xtraCardNum = xtraCardNbr;
            });

            When("I get a response from getCust API for carepass Enrollment", () -> {
                getCustCarepassEnrollmentService.viewGetCustCarePassEnrollment(request);
            });

            Then("I see the GetCust Response", () -> {
                getCustCarepassEnrollmentService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I do not see the GetCust Response", () -> {
                getCustCarepassEnrollmentService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see the xtra card nbr as {int}", (Integer xtra_card_nbr) -> {
                GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(xtra_card_nbr, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCardResponse().getXtraCardNbr());
            });

            Then("I see the status cd as {string}", (String status_cd) -> {
                GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(status_cd, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCarePrefsResponse().getGetCustCarepassResponse().getStatusCd());
            });

            Then("I switch Carepass enrollment from Monthly to yearly with next reward issue date in {int} days", (Integer eligibility_date) ->  {
                carePassEnrollmentUtil.enrollUsingSetCustFromMonthlyToYearly("0002", 98158419,eligibility_date);
            });

            Then("I get coupons as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                Integer actualCmpgnId = null;
                Integer actualCpnNbr = null;
                String actualExpirDt = null;
                String actualSoonInd = null;

                for (i = 0; i < list.size(); i++) {
                    Integer cmpgnId = Integer.valueOf(list.get(i).get("cmpgnId"));
                    Integer cpnNbr = Integer.valueOf(list.get(i).get("cpnNbr"));
                    Integer expirDt = Integer.valueOf(list.get(i).get("expirDt"));
                    String expectedexpiredt = (carePassDateUtil.carePassExpireTswtz(expirDt)).substring(0, 10);
                    String expSoonInd = list.get(i).get("expSoonInd");
                    GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                    List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                    Comparator<GetCustCusInfRespCpnsResponse> byCmpgnId = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnId);
                    Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnId);
                    actualCmpgnId = getCustCusInfRespCpnsResponseList.get(i).getCmpgnId();
                    actualCpnNbr = getCustCusInfRespCpnsResponseList.get(i).getCpnNbr();
                    actualExpirDt = getCustCusInfRespCpnsResponseList.get(i).getExpirDt();
                    actualSoonInd = getCustCusInfRespCpnsResponseList.get(i).getExpSoonInd();
                    Assert.assertEquals(cmpgnId, actualCmpgnId);
                    Assert.assertEquals(cpnNbr, actualCpnNbr);
                    Assert.assertEquals(expectedexpiredt, actualExpirDt);
                    Assert.assertEquals(expSoonInd, actualSoonInd);
                }
            });

            Then("I get yearly coupons as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                Integer actualCmpgnId = null;
                Integer actualCpnNbr = null;
                String actualExpirDt = null;
                for (i = 0; i < list.size(); i++) {
                    Integer cmpgnId = Integer.valueOf(list.get(i).get("cmpgnId"));
                    Integer cpnNbr = Integer.valueOf(list.get(i).get("cpnNbr"));
                    Integer expirDt = Integer.valueOf(list.get(i).get("expirDt"));
                    String expectedexpiredt = (carePassDateUtil.carePassExpireTswtz(expirDt)).substring(0, 10);
                    GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                    List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                    Comparator<GetCustCusInfRespCpnsResponse> byCmpgnId = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnId);
                    Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnId);
                    actualCmpgnId = getCustCusInfRespCpnsResponseList.get(i).getCmpgnId();
                    actualCpnNbr = getCustCusInfRespCpnsResponseList.get(i).getCpnNbr();
                    actualExpirDt = getCustCusInfRespCpnsResponseList.get(i).getExpirDt();

                    Assert.assertEquals(cmpgnId, actualCmpgnId);
                    Assert.assertEquals(cpnNbr, actualCpnNbr);
                    Assert.assertEquals(expectedexpiredt, actualExpirDt);
                }
            });

            Then("I get redeemed coupons as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                int i = 0;
                Integer actualCmpgnId = null;
                Integer actualCpnNbr = null;
                String actualExpirDt = null;
                String actualredeemableInd = null;
                for (i = 0; i < list.size(); i++) {
                    Integer cmpgnId = Integer.valueOf(list.get(i).get("cmpgnId"));
                    Integer cpnNbr = Integer.valueOf(list.get(i).get("cpnNbr"));
                    Integer expirDt = Integer.valueOf(list.get(i).get("expirDt"));
                    String expectedexpiredt = (carePassDateUtil.carePassExpireTswtz(expirDt)).substring(0, 10);
                    String redeemableInd = list.get(i).get("redeemableInd");
                    GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                    List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                    Comparator<GetCustCusInfRespCpnsResponse> byCmpgnId = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnId);
                    Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnId);
                    actualCmpgnId = getCustCusInfRespCpnsResponseList.get(i).getCmpgnId();
                    actualCpnNbr = getCustCusInfRespCpnsResponseList.get(i).getCpnNbr();
                    actualExpirDt = getCustCusInfRespCpnsResponseList.get(i).getExpirDt();
                    actualredeemableInd = getCustCusInfRespCpnsResponseList.get(i).getRedeemableInd();
                    Assert.assertEquals(cmpgnId, actualCmpgnId);
                    Assert.assertEquals(cpnNbr, actualCpnNbr);
                    Assert.assertEquals(expectedexpiredt, actualExpirDt);
                    Assert.assertEquals(redeemableInd, actualredeemableInd);
                }
            });

            Then("I get Carepass coupon as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    Integer cmpgnId = Integer.valueOf(list.get(i).get("cmpgnId"));
                    Integer cpnNbr = Integer.valueOf(list.get(i).get("cpnNbr"));
                    Integer expirDt = Integer.valueOf(list.get(i).get("expirDt"));
                    String expectedexpiredt = (carePassDateUtil.carePassExpireTswtz(expirDt)).substring(0, 10);
                    String webDsc = list.get(i).get("webDsc");

                    GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                    String actualExpiredDt = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getExpirDt();
                    Assert.assertEquals(cmpgnId, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getCmpgnId());
                    Assert.assertEquals(cpnNbr, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getCpnNbr());
                    Assert.assertEquals(expectedexpiredt, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getExpirDt());
                    Assert.assertEquals(webDsc, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getWebDsc());
                }
            });

            Then("I get Carepass redeemed coupon as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    Integer cmpgnId = Integer.valueOf(list.get(i).get("cmpgnId"));
                    Integer cpnNbr = Integer.valueOf(list.get(i).get("cpnNbr"));
                    Integer expirDt = Integer.valueOf(list.get(i).get("expirDt"));
                    String expectedexpiredt = (carePassDateUtil.carePassExpireTswtz(expirDt)).substring(0, 10);
                    String redeemableInd = list.get(i).get("redeemableInd");
                    GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                    Assert.assertEquals(cmpgnId, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getCmpgnId());
                    Assert.assertEquals(cpnNbr, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getCpnNbr());
                    Assert.assertEquals(expectedexpiredt, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getExpirDt());
                    Assert.assertEquals(redeemableInd, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCarePassCpnsResponseList().get(0).getRedeemableInd());
                }
            });

            Then("I see plan Type as {string}", (String plan_type) -> {
                GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(plan_type, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCarePrefsResponse().getGetCustCarepassResponse().getPlanType());
            });

            Then("I see benefit Eligibility Dt as {int}", (Integer benefiteligibilitydt) -> {
                String expectedBenefitEligibilityDt = (carePassDateUtil.carePassExpireTswtz(benefiteligibilitydt)).substring(0, 10);
                GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(expectedBenefitEligibilityDt, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCarePrefsResponse().getGetCustCarepassResponse().getBenefitEligibilityDt());
            });

            Then("I see expiry Dt as {int}", (Integer expiredt) -> {
                String expectedExpireDt = (carePassDateUtil.carePassExpireTswtz(expiredt)).substring(0, 10);
                GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(expectedExpireDt, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCarePrefsResponse().getGetCustCarepassResponse().getExpiryDt());
            });

            Then("I see benefit Eligibility Dt for yearly subscription as {int}", (Integer benefiteligibilitydt) -> {
                String expectedBenefitEligibilityDt = (carePassDateUtil.carePassExpireTswtz(benefiteligibilitydt+30)).substring(0, 10);
                GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(expectedBenefitEligibilityDt, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCarePrefsResponse().getGetCustCarepassResponse().getBenefitEligibilityDt());
            });

            Then("I see expiry Dt for yearly subscription as {int}", (Integer expiredt) -> {
                String expectedExpireDt = (carePassDateUtil.carePassExpireTswtz(expiredt+365)).substring(0, 10);
                GetCustResponse getcustResponse = getCustCarepassEnrollmentService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(expectedExpireDt, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCarePrefsResponse().getGetCustCarepassResponse().getExpiryDt());
            });

            Then("I see Error Code as {int}", (Integer error_cd) -> {
                ApiErrorResponse apiErrorResponse = getCustCarepassEnrollmentService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(error_cd, apiErrorResponse.getErrorCd());
            });

            Then("I see Error Message as {string}", (String error_msg) -> {
                ApiErrorResponse apiErrorResponse = getCustCarepassEnrollmentService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(error_msg, apiErrorResponse.getErrorMsg());
            });
        }
    }
}

