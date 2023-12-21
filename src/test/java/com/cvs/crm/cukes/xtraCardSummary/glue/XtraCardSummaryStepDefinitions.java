package com.cvs.crm.cukes.xtraCardSummary.glue;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCardSegment;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.XtraCardSummaryService;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XtraCardSummaryStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    XtraCardSummaryService xcSmryService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    XtraCardSegment xtraCardSegment;

    @Autowired
    XtraCardDao xtraCardDao;

    public XtraCardSummaryStepDefinitions() {
        {
            DashBoardRequest request = new DashBoardRequest();

            Given("{string}", (String scenario) -> {
            });

            Given("I use {string}", (String channel) -> {
                request.setChannel(channel);
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });

            When("I view Summary in Extracare DashBoard for my card", () -> {
                xcSmryService.viewXtraCardSummary(request);
            });

            Then("^I see the Summary Details$", () -> {
                xcSmryService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I see Total Year to date save Amount as {double}", (Double totYtdSaveAmt) -> {
                DashboardResponse dashboardResponse = xcSmryService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(totYtdSaveAmt, dashboardResponse.getXtraCardSummaryResponse().getTotalYearToDateSaving());
            });

            Then("I see Total Lifetime Savings Amount as {double}", (Double totLifetimeSaveAmt) -> {
                DashboardResponse dashboardResponse = xcSmryService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(totLifetimeSaveAmt, dashboardResponse.getXtraCardSummaryResponse().getTotalLifetimeSaving());
            });

            Then("I see Available Extra Bucks as {double}", (Double availEbs) -> {
                DashboardResponse dashboardResponse = xcSmryService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(availEbs, dashboardResponse.getXtraCardSummaryResponse().getAvailableExtraBucks());
            });

            Then("I see Card Member Date as {string}", (String cardMbrDt) -> {
                DashboardResponse dashboardResponse = xcSmryService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(cardMbrDt, dashboardResponse.getXtraCardSummaryResponse().getLifetimeSavingStartDate());
            });

            Then("I see Year to Date Savings Start Date as {string}", (String ytdStartDt) -> {
                DashboardResponse dashboardResponse = xcSmryService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(ytdStartDt, dashboardResponse.getXtraCardSummaryResponse().getYearToDateStartDate());
            });

            Then("I do not see the Summary Details", () -> {
                xcSmryService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see Error Code as {int}", (Integer errorCd) -> {
                ApiErrorResponse apiErrorResponse = xcSmryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });

            Then("I see Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = xcSmryService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });


            Then("I see earningsType indicator as {int} {string}", (Integer xtraCardNbr, String bcEarningsType) -> {
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                } else {
                    xtraCardSegment.setXtraCardNbr(xtraCardNbr);
                    Integer segCount = xtraCardDao.selectXtraCardSegment(xtraCardSegment);
                    if (segCount == 0) {
                        String expectedBcEarningsType = "D";
                        DashboardResponse dashboardResponse = xcSmryService.getServiceResponse().as(DashboardResponse.class);
                        Assert.assertEquals(expectedBcEarningsType, dashboardResponse.getXtraCardSummaryResponse().getBcEarningsType());
                    } else if (segCount == 1 || segCount == 2) {
                        String expectedBcEarningsType = "P";
                        DashboardResponse dashboardResponse = xcSmryService.getServiceResponse().as(DashboardResponse.class);
                        Assert.assertEquals(expectedBcEarningsType, dashboardResponse.getXtraCardSummaryResponse().getBcEarningsType());
                    }
                }
            });
        }
    }
}
