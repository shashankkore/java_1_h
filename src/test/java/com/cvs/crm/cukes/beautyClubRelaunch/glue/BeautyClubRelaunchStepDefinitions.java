package com.cvs.crm.cukes.beautyClubRelaunch.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.CmpgnDefnRequest;
import com.cvs.crm.model.response.*;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.service.BeautyClubRelaunchService;
import com.cvs.crm.service.CampaignDefnService;
import com.cvs.crm.service.GetCustBeautyClubRelaunchService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.DelegatingServletInputStream;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.format;

@Slf4j
public class BeautyClubRelaunchStepDefinitions extends SpringIntegrationTests implements En {
    Integer freeCmpgnId, perCmpgnId = null;
    Double thresholdNbr, dbExtrabuckRewardsEarned = null;
    Date cmpgnEndDt, sysDate;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    BeautyClubRelaunchService beautyClubService;

    @Autowired
    CampaignDefnService campaignDefnService;

    @Autowired
    GetCustBeautyClubRelaunchService getCustBeautyClubRelaunchService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    Campaign campaign;

    @Autowired
    CampaignCoupon campaignCoupon;

    @Autowired
    CampaignDao campaignDao;

    DateTime dateTime = new DateTime();
    String todayDate = dateTime.toString("yyyy-MM-dd");

    public BeautyClubRelaunchStepDefinitions() {
        {

            DashBoardRequest request = new DashBoardRequest();

            GetCustRequest gcrequest = new GetCustRequest();

            CmpgnDefnRequest cdRequest = new CmpgnDefnRequest();


            Given("{string}", (String scenario) -> {

            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
                gcrequest.setSearchCardType("0002");
                gcrequest.setSearchCardNbr(xtraCardNbr);
            });

            When("I view Beauty Club Details in DashBoard for my card", () -> {
                beautyClubService.viewBeautyClub(request);
                getCustBeautyClubRelaunchService.viewGetCustBeautyClubRelaunch(gcrequest);
                campaignDefnService.viewCmpgnDefn(cdRequest);
            });

            Then("I see the Beauty Club Rewards", () -> {
                beautyClubService.getServiceResponse().then().log().all().statusCode(200);
                getCustBeautyClubRelaunchService.getServiceResponse().then().log().all().statusCode(200);
                campaignDefnService.getServiceResponse().then().statusCode(200);
            });

            Then("I see my Beauty Club enrollment status as {string}", (String enrolled) -> {
                if (enrolled.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(enrolled, dashboardResponse.getBeautyClubRelaunchResponse().getEnrolled());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        String gcEnrolled = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCarePrefsResponse().getGetCustBeautyClubResponse().getEnrolled();
                        Boolean gcEnroll;
                        if (gcEnrolled.equals("Y")) {
                            gcEnroll = true;
                        } else {
                            gcEnroll = false;
                        }
                        Assert.assertEquals(gcEnroll, dashboardResponse.getBeautyClubRelaunchResponse().getEnrolled());
                    }
                }
            });

            Then("I see my Beauty Club Free Item Campaign Id as {int} {string} {string}", (Integer campaignId, String both_Campaigns, String earned) -> {
                if (campaignId == 00000) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(campaignId, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCampaignId());
                        } else {
                            Assert.assertEquals(campaignId, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCampaignId());
                        }
                    } else {
                        if (earned.equals("N")) {
                            Integer cdCmpgnId;
                            cdCmpgnId = campaignDao.selectBCCampaign(campaign);
                            freeCmpgnId = cdCmpgnId;
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            Assert.assertEquals(cdCmpgnId, beautyClubEarningsProgressResponseList.get(0).getCampaignId());

                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            Integer gcCmpgnId = getCustCusInfRespPtsResponseList.get(0).getCmpgnId();
                            freeCmpgnId = gcCmpgnId;
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            Assert.assertEquals(gcCmpgnId, beautyClubEarningsProgressResponseList.get(0).getCampaignId());
                        }
                    }
                }
            });

            Then("I see my Beauty Club percent Campaign Id as {int} {string} {string}", (Integer campaignId, String both_Campaigns, String earned) -> {
                if (campaignId == 00000) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(campaignId, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(1).getCampaignId());
                        } else {
                            Assert.assertEquals(campaignId, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCampaignId());
                        }
                    } else {
                        if (earned.equals("N")) {
                            Integer cdCmpgnId;
                            cdCmpgnId = campaignDao.selectPercentBCCampaign(campaign);
                            perCmpgnId = cdCmpgnId;
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                            Assert.assertEquals(cdCmpgnId, beautyClubEarningsProgressResponseList.get(0).getCampaignId());
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd);
                            Integer gcCmpgnId = getCustCusInfRespPtsResponseList.get(0).getCmpgnId();
                            perCmpgnId = gcCmpgnId;
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(perCmpgnId, beautyClubEarningsProgressResponseList.get(0).getCampaignId());
                            } else {
                                Assert.assertEquals(perCmpgnId, beautyClubEarningsProgressResponseList.get(0).getCampaignId());
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club Free Item Campaign WebDescription as {string} {string} {string}", (String webDescription, String both_Campaigns, String earned) -> {
                if (webDescription.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(webDescription, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getWebDescription());
                        } else {
                            Assert.assertEquals(webDescription, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getWebDescription());
                        }
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            String cdWebDesc;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("F") && (cdCmpgnSubTypeCd.equals("L")) && cdCmpgnId.equals(freeCmpgnId)) {
                                    cdWebDesc = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("webDsc");
                                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                                    Assert.assertEquals(cdWebDesc, beautyClubEarningsProgressResponseList.get(0).getWebDescription());
                                }
                            }
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            String gcWebDesc = getCustCusInfRespPtsResponseList.get(0).getWebDsc();
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(gcWebDesc, beautyClubEarningsProgressResponseList.get(0).getWebDescription());
                            } else {
                                Assert.assertEquals(gcWebDesc, beautyClubEarningsProgressResponseList.get(0).getWebDescription());
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club percent Campaign WebDescription {string} {string} {string}", (String webDescription, String both_Campaigns, String earned) -> {
                if (webDescription.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(webDescription, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(1).getWebDescription());
                        } else {
                            Assert.assertEquals(webDescription, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getWebDescription());
                        }
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            String cdWebDesc;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("E") && (cdCmpgnSubTypeCd.equals("L") || cdCmpgnSubTypeCd.equals("P")) && cdCmpgnId.equals(perCmpgnId)) {
                                    cdWebDesc = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("webDsc");
                                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                                    Assert.assertEquals(cdWebDesc, beautyClubEarningsProgressResponseList.get(0).getWebDescription());
                                }
                            }
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd);
                            String gcWebDesc = getCustCusInfRespPtsResponseList.get(0).getWebDsc();
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(gcWebDesc, beautyClubEarningsProgressResponseList.get(0).getWebDescription());
                            } else {
                                Assert.assertEquals(gcWebDesc, beautyClubEarningsProgressResponseList.get(0).getWebDescription());
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club Free Item Campaign End Date as {string} {string} {string}", (String CampaignEndDate, String both_Campaigns, String earned) -> {
                if (CampaignEndDate.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(CampaignEndDate, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(1).getCampaignEndDate().replace("-", ""));
                        } else {
                            Assert.assertEquals(CampaignEndDate, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCampaignEndDate().replace("-", ""));
                        }
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            String cdCmpgnEnd;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("F") && (cdCmpgnSubTypeCd.equals("L")) && cdCmpgnId.equals(freeCmpgnId)) {
                                    cdCmpgnEnd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnEndDt");
                                    String cdEndDate = cdCmpgnEnd.substring(0, 8);
                                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                                    Assert.assertEquals(cdEndDate, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCampaignEndDate().replace("-", ""));
                                }
                            }

                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            String gcCmpgnEndDt = getCustCusInfRespPtsResponseList.get(0).getCmpgnEndDt();
                            String endDt = gcCmpgnEndDt.substring(0, 4) + "-" + gcCmpgnEndDt.substring(4, 6) + "-" + gcCmpgnEndDt.substring(6, 8);
                            cmpgnEndDt = new SimpleDateFormat("yyyy-M-dd").parse(endDt);
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(gcCmpgnEndDt, beautyClubEarningsProgressResponseList.get(0).getCampaignEndDate().replace("-", ""));
                            } else {
                                Assert.assertEquals(gcCmpgnEndDt, beautyClubEarningsProgressResponseList.get(0).getCampaignEndDate().replace("-", ""));
                            }
                        }
                    }
                }
            });


            Then("I see my Beauty Club percent Campaign End Date as {string} {string} {string}", (String percentCampaignEndDate, String both_Campaigns, String earned) -> {
                if (percentCampaignEndDate.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(percentCampaignEndDate, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(1).getCampaignEndDate().replace("-", ""));
                        } else {
                            Assert.assertEquals(percentCampaignEndDate, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCampaignEndDate().replace("-", ""));
                        }
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            String cdCmpgnEnd;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("E") && (cdCmpgnSubTypeCd.equals("L") || cdCmpgnSubTypeCd.equals("P")) && cdCmpgnId.equals(perCmpgnId)) {
                                    cdCmpgnEnd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnEndDt");
                                    String cdEndDate = cdCmpgnEnd.substring(0, 8);
                                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                                    if (both_Campaigns.equals("Y")) {
                                        Assert.assertEquals(cdEndDate, beautyClubEarningsProgressResponseList.get(0).getCampaignEndDate().replace("-", ""));
                                    } else {
                                        Assert.assertEquals(cdEndDate, beautyClubEarningsProgressResponseList.get(0).getCampaignEndDate().replace("-", ""));
                                    }
                                }
                            }
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd);
                            String gcCmpgnEndDt = getCustCusInfRespPtsResponseList.get(0).getCmpgnEndDt();
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(gcCmpgnEndDt, beautyClubEarningsProgressResponseList.get(0).getCampaignEndDate().replace("-", ""));
                            } else {
                                Assert.assertEquals(gcCmpgnEndDt, beautyClubEarningsProgressResponseList.get(0).getCampaignEndDate().replace("-", ""));
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club Free Item Offer Limit Reached as {string} {string} {string}", (String offerLimitReached, String both_Campaigns, String earned) -> {
                if (offerLimitReached.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(offerLimitReached, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getOfferLimitReached());
                    } else {
                        if (earned.equals("N")) {
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            Assert.assertEquals(offerLimitReached, beautyClubEarningsProgressResponseList.get(0).getOfferLimitReached());
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            String gcOfferLimitReached = getCustCusInfRespPtsResponseList.get(0).getOfferLimitReachedInd();
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            Assert.assertEquals(gcOfferLimitReached, beautyClubEarningsProgressResponseList.get(0).getOfferLimitReached());
                        }
                    }
                }
            });

            Then("I see my Beauty Club percent Offer Limit Reached as {string} {string} {string}", (String percentOfferLimitReached, String both_Campaigns, String earned) -> {
                if (percentOfferLimitReached.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(percentOfferLimitReached, beautyClubEarningsProgressResponseList.get(1).getOfferLimitReached());
                    } else {
                        if (earned.equals("N")) {
                            Assert.assertEquals(percentOfferLimitReached, beautyClubEarningsProgressResponseList.get(0).getOfferLimitReached());
                        } else {
                            campaign.setCmpgnId(perCmpgnId);
                            Double maxRwrdAmt = Double.valueOf(campaignDao.selectBCMaxRewrdAmtCampaign(campaign));
                            String expOfferLimitReached = String.valueOf(((beautyClubEarningsProgressResponseList.get(0).getExtrabuckRewardsEarned()) / 10) >= maxRwrdAmt);
                            System.out.println("expOfferLimitReached:" + expOfferLimitReached);
                         }
                    }
                }
            });


            Then("I see my Beauty Club Free Item coupon number as {int} {string} {string}", (Integer cpnNbr, String both_Campaigns, String earned) -> {
                if (cpnNbr == 00000) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(cpnNbr, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponNumber());
                        } else {
                            Assert.assertEquals(cpnNbr, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponNumber());
                        }
                    } else {
                        campaignCoupon.setCmpgnId(freeCmpgnId);
                        campaignCoupon.setRwrdQty(1);
                        Integer expCpnNbr = campaignDao.selectCampaignCoupon(campaignCoupon);
                        List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                        Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                        Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                        Assert.assertEquals(expCpnNbr, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponNumber());
                    }
                }
            });


            Then("I see my Beauty Club Free Item Threshold Type Code as {string} {string} {string}", (String thresholdTypeCode, String both_Campaigns, String earned) -> {
                if (thresholdTypeCode.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(thresholdTypeCode, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getThresholdTypeCode());
                        } else {
                            Assert.assertEquals(thresholdTypeCode, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getThresholdTypeCode());
                        }
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            String cdthresholdTypeCode;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("F") && (cdCmpgnSubTypeCd.equals("L")) && cdCmpgnId.equals(freeCmpgnId)) {
                                    cdthresholdTypeCode = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("thrshldTypeCd");
                                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                                    Assert.assertEquals(cdthresholdTypeCode, beautyClubEarningsProgressResponseList.get(0).getThresholdTypeCode());
                                }
                            }
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            String gcthresholdTypeCode = getCustCusInfRespPtsResponseList.get(0).getThrshldTypeCd();
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(gcthresholdTypeCode, beautyClubEarningsProgressResponseList.get(0).getThresholdTypeCode());
                            } else {
                                Assert.assertEquals(gcthresholdTypeCode, beautyClubEarningsProgressResponseList.get(0).getThresholdTypeCode());
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club Free Item First Threshold as {double} {string} {string}", (Double firstThreshold, String both_Campaigns, String earned) -> {
                if (firstThreshold == 000.00) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(firstThreshold, beautyClubEarningsProgressResponseList.get(0).getFirstThreshold());
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            Double cdfirstThreshold;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("F") && (cdCmpgnSubTypeCd.equals("L")) && cdCmpgnId.equals(freeCmpgnId)) {
                                    cdfirstThreshold = (Double) campaignDefnResponse.getRespCmpgnDefs().get(i).get("thrshldLimNbr");
                                    thresholdNbr = cdfirstThreshold;
                                    Assert.assertEquals(cdfirstThreshold, beautyClubEarningsProgressResponseList.get(0).getFirstThreshold());
                                }
                            }
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            Double gcfirstThreshold = getCustCusInfRespPtsResponseList.get(0).getFirstThrshldLimNbr();
                            thresholdNbr = gcfirstThreshold;
                            Assert.assertEquals(gcfirstThreshold, beautyClubEarningsProgressResponseList.get(0).getFirstThreshold());
                        }
                    }
                }
            });

            Then("I see my Beauty Club Free Item Points required to get next coupon as {double} {string} {string}", (Double pointsToNextThreshold, String both_Campaigns, String earned) -> {
                if (pointsToNextThreshold == 000.00) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(pointsToNextThreshold, beautyClubEarningsProgressResponseList.get(0).getPointsToNextThreshold());
                    } else {
                        if (earned.equals("N")) {
                            Assert.assertEquals(pointsToNextThreshold, beautyClubEarningsProgressResponseList.get(0).getPointsToNextThreshold());
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            Double gcpointsToNextThreshold = thresholdNbr - ((getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList().get(0).getPtsQty()) % thresholdNbr);
                            Assert.assertEquals(gcpointsToNextThreshold, beautyClubEarningsProgressResponseList.get(0).getPointsToNextThreshold());
                        }
                    }
                }
            });

            Then("I see my Beauty Club Free Item Points Progress as {double} {string} {string}", (Double pointsProgress, String both_Campaigns, String earned) -> {
                if (pointsProgress == 000.00) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(pointsProgress, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getPointsProgress());
                        } else {
                            Assert.assertEquals(pointsProgress, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getPointsProgress());
                        }
                    } else {
                        if (earned.equals("N")) {
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            Assert.assertEquals(pointsProgress, beautyClubEarningsProgressResponseList.get(0).getPointsToNextThreshold());
                            Assert.assertEquals(pointsProgress, beautyClubEarningsProgressResponseList.get(0).getPointsProgress());
                        } else {

                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            Double gcpointsProgress = ((getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList().get(0).getPtsQty()) % thresholdNbr);
                            List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                            Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                            Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(gcpointsProgress, beautyClubEarningsProgressResponseList.get(0).getPointsProgress());
                            } else {
                                Assert.assertEquals(gcpointsProgress, beautyClubEarningsProgressResponseList.get(0).getPointsProgress());
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club percent extrabuckRewardsEarned as {double} {string} {string}", (Double extrabuckRewardsEarned, String both_Campaigns, String earned) -> {
                if (extrabuckRewardsEarned == 000.00) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(extrabuckRewardsEarned, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(1).getExtrabuckRewardsEarned());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                        Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd);
                        Double gcXtraBucksRewardsEarned = ((getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList().get(0).getPtsQty()) / 10);
                        if (gcXtraBucksRewardsEarned > 20.0) {
                            gcXtraBucksRewardsEarned = 20.0;
                        }
                        List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                        Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                        Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                        dbExtrabuckRewardsEarned = beautyClubEarningsProgressResponseList.get(0).getExtrabuckRewardsEarned();

                        Assert.assertEquals(gcXtraBucksRewardsEarned, dbExtrabuckRewardsEarned);
                    }
                }
            });


            Then("I see my Beauty Club  Reward Amount as {double}", (Double rewardAmount) -> {
                DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(rewardAmount, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getRewardAmount());
            });


            Then("I see my Beauty Club Free Item Coupon loadable Indicator as {string} {string} {string}", (String loadable, String both_Campaigns, String earned) -> {
                if (loadable.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(loadable, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponDetailsResponse().getLoadable());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd.reversed());
                        String expLoadable = getCustCusInfRespCpnsResponseList.get(0).getLoadableInd();
                        String expectedLoadable = null;
                        if (expLoadable.equals("N")) {
                            expectedLoadable = "false";
                        } else {
                            expectedLoadable = "true";
                        }
                        List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                        Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                        Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(expectedLoadable, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadable());
                        } else {
                            Assert.assertEquals(expectedLoadable, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadable());
                        }
                    }
                }
            });


            Then("I see my Beauty Club Free Item Coupon Sequence Number as {long} {string} {string}", (Long freeCouponSeqNbr, String both_Campaigns, String earned) -> {
                if (freeCouponSeqNbr == 00000) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(freeCouponSeqNbr, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponDetailsResponse().getLoadable());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd.reversed());
                        Long expPerCouponSeqNbr = getCustCusInfRespCpnsResponseList.get(0).getCpnSeqNbr();
                        List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                        Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                        Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(expPerCouponSeqNbr, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getCouponSequenceNumber());
                        } else {
                            Assert.assertEquals(expPerCouponSeqNbr, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getCouponSequenceNumber());
                        }
                    }
                }
            });


            Then("I see my Beauty Club percent Coupon Sequence Number as {long} {string} {string}", (Long freeCouponSeqNbr, String both_Campaigns, String earned) -> {
                if (freeCouponSeqNbr == 00000) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(freeCouponSeqNbr, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponDetailsResponse().getCouponSequenceNumber());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd);
                        Long expPerCouponSeqNbr = getCustCusInfRespCpnsResponseList.get(0).getCpnSeqNbr();
                        List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                        Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                        Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(expPerCouponSeqNbr, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getCouponSequenceNumber());
                        } else {
                            Assert.assertEquals(expPerCouponSeqNbr, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getCouponSequenceNumber());
                        }
                    }
                }
            });


            Then("I see my Beauty Club Free Item Coupon Description as {string} {string} {string}", (String couponDescription, String both_Campaigns, String earned) -> {
                if (couponDescription.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(couponDescription, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponDescription());
                    } else {
                        CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                        Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                        String cdCpnDesc;
                        String cdCmpgnTypeCd;
                        String cdCmpgnSubTypeCd;
                        Integer cdCmpgnId;
                        for (int i = 0; i < cmpgnCount; i++) {
                            cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                            cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                            cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                            if (cdCmpgnTypeCd.equals("F") && cdCmpgnSubTypeCd.equals("L") && (cdCmpgnId == freeCmpgnId)) {
                                cdCpnDesc = (String) (campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTermsTxt"));
                                List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                                Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                                Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                                Assert.assertEquals(cdCpnDesc, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponDescription());
                            }
                        }
                    }
                }
            });


            Then("I see my Beauty Club percent Coupon Issue Date as {string} {string} {string}", (String couponIssueDate, String both_Campaigns, String earned) -> {
                if (couponIssueDate.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(couponIssueDate, beautyClubEarningsProgressResponseList.get(0).getCouponIssueDate());
                    } else {
                        CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                        Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                        String cdCpnIssueDt, expectedCpnIssueDt, cdCmpgnTypeCd, cdCmpgnSubTypeCd, cdMktgPrgCd;
                        Integer cdCmpgnId;
                        for (int i = 0; i < cmpgnCount; i++) {
                            cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                            cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                            cdMktgPrgCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("mktgPrgCd");
                            cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                            if ((cdCmpgnId.equals(perCmpgnId))) {
                                try {
                                    if (cdMktgPrgCd.equals("B") && cdCmpgnTypeCd.equals("E") && (cdCmpgnSubTypeCd.equals("P") || cdCmpgnSubTypeCd.equals("L"))) {
                                        cdCpnIssueDt = (String) (campaignDefnResponse.getRespCmpgnDefs().get(i).get("firstIssueDt"));
                                        expectedCpnIssueDt = cdCpnIssueDt.substring(0, 8);
                                        String actCpnIssueDt = beautyClubEarningsProgressResponseList.get(0).getCouponIssueDate().replace("-", "");
                                        Assert.assertEquals(expectedCpnIssueDt, actCpnIssueDt);
                                    }
                                } catch (NullPointerException e) {
                                    System.out.print("Caught NullPointerException");
                                }
                            }
                        }
                    }
                }
            });


            Then("I see my Beauty Club percent Coupon loadable Indicator as {string} {string} {string}", (String loadable, String both_Campaigns, String earned) -> {
                if (loadable.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(loadable, dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList().get(0).getCouponDetailsResponse().getLoadable());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd);
                        String expLoadable = getCustCusInfRespCpnsResponseList.get(0).getLoadableInd();
                        String expectedLoadable = null;
                        if (expLoadable.equals("N")) {
                            expectedLoadable = "false";
                        } else {
                            expectedLoadable = "true";
                        }
                        List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                        Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                        Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                        if (both_Campaigns.equals("Y")) {
                            Assert.assertEquals(expectedLoadable, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadable());
                        } else {
                            Assert.assertEquals(expectedLoadable, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadable());
                        }
                    }
                }
            });


            Then("I see my Beauty Club Free Item Coupon loadActualDate as {string} {string} {string}", (String loadActualDate, String both_Campaigns, String earned) -> {
                if (loadActualDate.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(loadActualDate, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadActualDate());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd.reversed());
                        String expLoadAcDt = getCustCusInfRespCpnsResponseList.get(0).getLoadActlDt();
                        Assert.assertEquals(expLoadAcDt, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadActualDate());
                    }
                }
            });


            Then("I see my Beauty Club percent Coupon loadActualDate as {string} {string} {string}", (String loadActualDate, String both_Campaigns, String earned) -> {
                if (loadActualDate.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(loadActualDate, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadActualDate());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd);
                        String expLoadAcDt = getCustCusInfRespCpnsResponseList.get(0).getLoadActlDt();
                        Assert.assertEquals(expLoadAcDt, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getLoadActualDate());
                    }
                }
            });


            Then("I see my Beauty Club Free Coupon daysLeft as {int} {string} {string}", (Integer daysLeft, String both_Campaigns, String earned) -> {
                if (daysLeft == 12345) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(daysLeft, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            Double cdfirstThreshold;
                            String cdCmpgnTypeCd, cdCmpgnEnd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("F") && cdCmpgnSubTypeCd.equals("L") && cdCmpgnId.equals(freeCmpgnId)) {
                                    cdCmpgnEnd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnEndDt");
                                    String cdEndDate = cdCmpgnEnd.substring(0, 8);
                                    String endDt = cdEndDate.substring(0, 4) + "-" + cdEndDate.substring(4, 6) + "-" + cdEndDate.substring(6, 8);
                                    LocalDate endDate = LocalDate.parse(endDt);
                                    LocalDate currentDate = LocalDate.parse(todayDate);
                                    Integer noOfDaysBetween = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, endDate));
                                    if (both_Campaigns.equals("Y")) {
                                        Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                                    } else {
                                        Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                                    }
                                }
                            }
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            String gcCmpgnEndDt = getCustCusInfRespPtsResponseList.get(0).getCmpgnEndDt();
                            String endDt = gcCmpgnEndDt.substring(0, 4) + "-" + gcCmpgnEndDt.substring(4, 6) + "-" + gcCmpgnEndDt.substring(6, 8);
                            LocalDate endDate = LocalDate.parse(endDt);
                            LocalDate currentDate = LocalDate.parse(todayDate);
                            Integer noOfDaysBetween = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, endDate));
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                            } else {
                                Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club Free Coupon freeItemCoupon Flag as {string} {string} {string}", (String freeItemCouponFlag, String both_Campaigns, String earned) -> {
                if (freeItemCouponFlag.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(freeItemCouponFlag, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            String cdFreeItemCouponFlag = null;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("F") && (cdCmpgnSubTypeCd.equals("L")) && cdCmpgnId.equals(freeCmpgnId)) {
                                    cdFreeItemCouponFlag = "true";
                                    Assert.assertEquals(cdFreeItemCouponFlag, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                                }
                            }
                        } else {
                            String gcFreeItemCouponFlag;
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd.reversed());
                            String cmpgnType = getCustCusInfRespPtsResponseList.get(0).getCmpgnTypeCd();
                            String cmpgnSubType = getCustCusInfRespPtsResponseList.get(0).getCmpgnSubtypeCd();
                            if (cmpgnType.equals("F") && cmpgnSubType.equals("L")) {
                                gcFreeItemCouponFlag = "true";
                            } else {
                                gcFreeItemCouponFlag = "false";
                            }
                            Assert.assertEquals(gcFreeItemCouponFlag, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                        }
                    }
                }
            });

            Then("I see my Beauty Club percent Coupon daysLeft as {int} {string} {string}", (Integer percentDaysLeft, String both_Campaigns, String earned) -> {
                if (percentDaysLeft == 12345) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(percentDaysLeft, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            Double cdfirstThreshold;
                            String cdCmpgnTypeCd, cdCmpgnEnd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("E") && (cdCmpgnSubTypeCd.equals("L") || cdCmpgnSubTypeCd.equals("P")) && cdCmpgnId.equals(perCmpgnId)) {
                                    cdCmpgnEnd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnEndDt");
                                    String cdEndDate = cdCmpgnEnd.substring(0, 8);
                                    String endDt = cdEndDate.substring(0, 4) + "-" + cdEndDate.substring(4, 6) + "-" + cdEndDate.substring(6, 8);
                                    LocalDate endDate = LocalDate.parse(endDt);
                                    LocalDate currentDate = LocalDate.parse(todayDate);
                                    Integer noOfDaysBetween = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, endDate));
                                    if (both_Campaigns.equals("Y")) {
                                        Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                                    } else {
                                        Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                                    }
                                }
                            }
                        } else {
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd);
                            String gcCmpgnEndDt = getCustCusInfRespPtsResponseList.get(0).getCmpgnEndDt();
                            String endDt = gcCmpgnEndDt.substring(0, 4) + "-" + gcCmpgnEndDt.substring(4, 6) + "-" + gcCmpgnEndDt.substring(6, 8);
                            LocalDate endDate = LocalDate.parse(endDt);
                            LocalDate currentDate = LocalDate.parse(todayDate);
                            Integer noOfDaysBetween = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, endDate));
                            if (both_Campaigns.equals("Y")) {
                                Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                            } else {
                                Assert.assertEquals(noOfDaysBetween, beautyClubEarningsProgressResponseList.get(0).getDaysLeft());
                            }
                        }
                    }
                }
            });

            Then("I see my Beauty Club percent Coupon freeItemCoupon Flag as {string} {string} {string}", (String percentFreeItemCouponFlag, String both_Campaigns, String earned) -> {
                if (percentFreeItemCouponFlag.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(percentFreeItemCouponFlag, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                    } else {
                        if (earned.equals("N")) {
                            CampaignDefnResponse campaignDefnResponse = campaignDefnService.getServiceResponse().as(CampaignDefnResponse.class);
                            Integer cmpgnCount = campaignDefnResponse.getRespCmpgnDefs().size();
                            String cdFreeItemCouponFlag = null;
                            String cdCmpgnTypeCd;
                            String cdCmpgnSubTypeCd;
                            Integer cdCmpgnId;
                            for (int i = 0; i < cmpgnCount; i++) {
                                cdCmpgnTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnTypeCd");
                                cdCmpgnSubTypeCd = (String) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnSubTypeCd");
                                cdCmpgnId = (Integer) campaignDefnResponse.getRespCmpgnDefs().get(i).get("cmpgnId");
                                if (cdCmpgnTypeCd.equals("E") && (cdCmpgnSubTypeCd.equals("L") || cdCmpgnSubTypeCd.equals("P")) && cdCmpgnId.equals(perCmpgnId)) {
                                    cdFreeItemCouponFlag = "false";
                                    Assert.assertEquals(cdFreeItemCouponFlag, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                                }
                            }
                        } else {
                            String gcFreeItemCouponFlag;
                            GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                            List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespPtsResponseList();
                            Comparator<GetCustCusInfRespPtsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespPtsResponse::getCmpgnTypeCd);
                            Collections.sort(getCustCusInfRespPtsResponseList, byCmpgnTypeCd);
                            String cmpgnType = getCustCusInfRespPtsResponseList.get(0).getCmpgnTypeCd();
                            String cmpgnSubType = getCustCusInfRespPtsResponseList.get(0).getCmpgnSubtypeCd();
                            if (cmpgnType.equals("E") && (cmpgnSubType.equals("L") || cmpgnSubType.equals("P"))) {
                                gcFreeItemCouponFlag = "false";
                            } else {
                                gcFreeItemCouponFlag = "true";
                            }
                            Assert.assertEquals(gcFreeItemCouponFlag, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                        }
                    }
                }
            });


            Then("I see only one Free Item Campagin and one percent Campaign {int} {string} {string}", (Integer campaignsCount, String both_Campaigns, String earned) -> {
                if (campaignsCount.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    Integer actualCampaignsCount = beautyClubEarningsProgressResponseList.size();
                    Assert.assertEquals(campaignsCount, actualCampaignsCount);
                    if (actualCampaignsCount.equals(campaignsCount)) {
                        String freeCmpgnType = beautyClubEarningsProgressResponseList.get(1).getFreeItemCoupon();
                        String percentCmpgnType = beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon();
                        Assert.assertEquals("true", freeCmpgnType);
                        Assert.assertEquals("false", percentCmpgnType);
                    }
                }
            });
            Then("I see my Beauty Club Free Coupon expire Date as {string} {string} {string}", (String freeExpireDt, String both_Campaigns, String earned) -> {
                if (freeExpireDt.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(freeExpireDt, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                    } else {
                        String gcFreeItemCouponFlag;
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd.reversed());
                        try {
                            String expExpireAcDt = getCustCusInfRespCpnsResponseList.get(0).getExpirDt();
                            Assert.assertEquals(expExpireAcDt, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getExpiryDate());
                        } catch (NullPointerException e) {
                            System.out.print("Caught NullPointerException");
                        }

                    }
                }
            });
            Then("I see my Beauty Club percent Coupon expire Date as {string} {string} {string}", (String percentExpireDt, String both_Campaigns, String earned) -> {
                if (percentExpireDt.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(percentExpireDt, beautyClubEarningsProgressResponseList.get(0).getFreeItemCoupon());
                    } else {
                        String gcFreeItemCouponFlag;
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd);
                        try {
                            String expExpireAcDt = getCustCusInfRespCpnsResponseList.get(0).getExpirDt();
                            Assert.assertEquals(expExpireAcDt, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getExpiryDate());
                        } catch (NullPointerException e) {
                            System.out.print("Caught NullPointerException");
                        }

                    }
                }
            });

            Then("I see my Beauty Club Free Item Coupon Redeemable Indicator as {string} {string} {string}", (String freeRedeemableInd, String both_Campaigns, String earned) -> {
                if (freeRedeemableInd.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(freeRedeemableInd, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemable());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd.reversed());
                        String expRedeemable = getCustCusInfRespCpnsResponseList.get(0).getRedeemableInd();
                        String expectedRedeemable = null;
                        if (expRedeemable.equals("N")) {
                            expectedRedeemable = "false";
                        } else {
                            expectedRedeemable = "true";
                        }
                        Assert.assertEquals(expectedRedeemable, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemable());
                    }
                }
            });

            Then("I see my Beauty Club Free Item Coupon Redeemed Indicator as {string} {string} {string}", (String freeRedeemedInd, String both_Campaigns, String earned) -> {
                if (freeRedeemedInd.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(freeRedeemedInd, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemed());
                    } else {
                        Assert.assertEquals(freeRedeemedInd, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemed());
                    }
                }
            });

            Then("I see my Beauty Club Free Item Coupon redeemActualDate as {string} {string} {string}", (String freeRedeemActualDate, String both_Campaigns, String earned) -> {
                if (freeRedeemActualDate.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon.reversed());
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(freeRedeemActualDate, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemActualDate());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd.reversed());
                        String expRedeemAcDt = getCustCusInfRespCpnsResponseList.get(0).getRedmActlDt();
                        Assert.assertEquals(expRedeemAcDt, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemActualDate());
                    }
                }
            });

            Then("I see my Beauty Club percent Coupon Redeemable Indicator as {string} {string} {string}", (String percentRedeemableInd, String both_Campaigns, String earned) -> {
                if (percentRedeemableInd.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

                        Assert.assertEquals(percentRedeemableInd, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemable());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd);
                        String expRedeemable = getCustCusInfRespCpnsResponseList.get(0).getRedeemableInd();
                        String expectedRedeemable = null;
                        if (expRedeemable.equals("N")) {
                            expectedRedeemable = "false";
                        } else {
                            expectedRedeemable = "true";
                        }
                        Assert.assertEquals(expectedRedeemable, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemable());
                    }
                }
            });

            Then("I see my Beauty Club percent Coupon Redeemed Indicator as {string} {string} {string}", (String percentRedeemedInd, String both_Campaigns, String earned) -> {
                if (percentRedeemedInd.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(percentRedeemedInd, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemed());
                    } else {

                        Assert.assertEquals(percentRedeemedInd, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemed());
                    }
                }
            });

            Then("I see my Beauty Club percent Coupon redeemActualDate as {string} {string} {string}", (String percentRedeemActualDate, String both_Campaigns, String earned) -> {
                if (percentRedeemActualDate.equals("null")) {
                } else {
                    DashboardResponse dashboardResponse = beautyClubService.getServiceResponse().as(DashboardResponse.class);
                    List<BeautyClubEarningsProgressResponse> beautyClubEarningsProgressResponseList = dashboardResponse.getBeautyClubRelaunchResponse().getEarningsProgressList();
                    Comparator<BeautyClubEarningsProgressResponse> byFreeItemCoupon = Comparator.comparing(BeautyClubEarningsProgressResponse::getFreeItemCoupon);
                    Collections.sort(beautyClubEarningsProgressResponseList, byFreeItemCoupon);
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        Assert.assertEquals(percentRedeemActualDate, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemActualDate());
                    } else {
                        GetCustResponse getCustResponse = getCustBeautyClubRelaunchService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList = getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();
                        Comparator<GetCustCusInfRespCpnsResponse> byCmpgnTypeCd = Comparator.comparing(GetCustCusInfRespCpnsResponse::getCmpgnTypeCd);
                        Collections.sort(getCustCusInfRespCpnsResponseList, byCmpgnTypeCd);
                        String expRedeemAcDt = getCustCusInfRespCpnsResponseList.get(0).getRedmActlDt();
                        Assert.assertEquals(expRedeemAcDt, beautyClubEarningsProgressResponseList.get(0).getCouponDetailsResponse().getRedeemActualDate());
                    }
                }
            });


            Then("I do not see the Beauty Club Rewards", () -> {
                beautyClubService.getServiceResponse().then().log().all().statusCode(400);
            });


            Then("I see my Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = beautyClubService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });



        }
    }
}