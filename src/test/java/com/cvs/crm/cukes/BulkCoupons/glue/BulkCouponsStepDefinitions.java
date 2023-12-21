package com.cvs.crm.cukes.BulkCoupons.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.XtraCardSummary;
import com.cvs.crm.model.request.BulkCpnRequest;
import com.cvs.crm.model.response.*;
import com.cvs.crm.service.BulkCouponsService;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CampaignEarnServiceUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.GenerateRandom;

import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class BulkCouponsStepDefinitions extends SpringIntegrationTests implements En {
    Integer xtraCardNum = null;

    @Autowired
    BulkCouponsService bulkCouponsService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    XtraCardSummary xtraCardSummary;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    CampaignDao campaignDao;
    
    @Autowired
    GenerateRandom generateRandom;
    
    @Autowired
    Campaign campaign;
    
    @Autowired
    CampaignCoupon campaignCoupon;
    
    @Autowired
    CampaignEarnServiceUtil campaignEarnServiceUtil;

    public BulkCouponsStepDefinitions() {
        {
            BulkCpnRequest request = new BulkCpnRequest();

            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
                xtraCardNum = xtraCardNbr;
            });
            
            Given("I use channel {string}", (String channel) -> {
                request.setChannel(channel);
            });

            Given("I hit campaign earn1 for card {int}", (Integer xtraCardNbr) -> {
            	System.out.println("Campaign het earn 1");
            	Integer txnNbr1 = generateRandom.randomNumberString();
                Integer txnInvoiceNbr1 = generateRandom.randomNumberString();
                campaignEarnServiceUtil.hitCampaignEarn(xtraCardNbr, 4, 10, 999975, txnNbr1, txnInvoiceNbr1);
                campaignEarnServiceUtil.getServiceResponse().then().log().all();
            });
            
            Given("I hit campaign earn2 for card {int}", (Integer xtraCardNbr) -> {
            	System.out.println("Campaign het earn 2");
            	Integer txnNbr2 = generateRandom.randomNumberString();
                Integer txnInvoiceNbr2 = generateRandom.randomNumberString();
                campaignEarnServiceUtil.hitCampaignEarn(xtraCardNbr, 10, 100, 999975, txnNbr2, txnInvoiceNbr2);
                campaignEarnServiceUtil.getServiceResponse().then().log().all();
            });
           

            When("I want Bulk Coupon to be Created for {int} {string} {int} {int} {int} {int}", (Integer xtraCardNbr, String type, Integer cmpgnId, Integer cpnSkuNbr1, Integer cpnSkuNbr2, Integer matchCode) -> {
            	if (type.equals("view") || type.equals("viewnegative")) {
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2, matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2, matchCode);
                    }
                }
                else if (type.equals("digitize")) {
                      String type2 = "digitize";
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2, matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2, matchCode);
                    }
                }
                else if (type.equals("predigitize")) {
                    String type1="print";
                    String type2 = "digitize";
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type1, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type1, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                }
                else if (type.equals("print") || type.equals("printend") || type.equals("printstart") || type.equals("printnegative")) {
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                }
                else if (type.equals("load") || type.equals("loadredeemend") || type.equals("loadnegative")) {
                  //  String type1="view";
                  //  String type2 = "load";
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                  //      bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type1, cmpgnId, cpnSkuNbr1, cpnSkuNbr2);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                    else {
                        String profile = "uat";
                 //       bulkCouponsService.viewBulkCoupons(request, profile, xtraCardNbr, type1, cmpgnId, cpnSkuNbr1, cpnSkuNbr2);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                }
                else if (type.equals("redeem") ) {
                    String type2 = "load";
                    String type3 = "redeem";
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
//                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type3, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type3, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                }
                else if (type.equals("redeemend") ) {
                    String type2 = "load";
                    String type3 = "redeemend";
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type3, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type3, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                }
                else if (type.equals("redeemstart")) {
                    String type2 = "load";
                    String type3 = "redeemstart";
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type3, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type2, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type3, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                }
                else if (type.equals("notloadedredeem") || type.equals("redeemnegative")) {
                 //   String type3 = "redeem";
                    if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                        String profile = "stub";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                    else {
                        String profile = "uat";
                        bulkCouponsService.viewBulkCoupons(request, profile , xtraCardNbr, type, cmpgnId, cpnSkuNbr1, cpnSkuNbr2,matchCode);
                    }
                }
            });

            Then("I see the Coupon Response", () -> {
                bulkCouponsService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I do not see the Coupon Response", () -> {
                bulkCouponsService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see Error Message as {string}", (String errorMsg) -> {
                String apiErrorResponse = bulkCouponsService.getServiceResponse().getBody().asString();
                Assert.assertEquals(errorMsg, apiErrorResponse);
            });

            Then("I see my cpnStatusCd as {int} {string}", (Integer cpnStatusCd, String type) -> {
                BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                if (type.equals("view")||type.equals("viewnegative")) {
                    for (int i = 0; i < viewCount; i++) {
                        Integer actCpnStatusCd = cpnViewOutputResponseList.get(i).getStatusCd();
                        Assert.assertEquals(cpnStatusCd, actCpnStatusCd);
                    }
                } else if (type.equals("print")||type.equals("printend") || type.equals("printstart") || type.equals("printnegative")) {
                    for (int i = 0; i < printCount; i++) {
                        Integer actCpnStatusCd = cpnPrintOutputResponseList.get(i).getStatusCd();
                        Assert.assertEquals(cpnStatusCd, actCpnStatusCd);
                    }
                } else if (type.equals("load") || type.equals("loadredeemend") || (type.equals("digitize")) || (type.equals("predigitize")) || (type.equals("loadnegative")) ){
                    for (int i = 0; i < loadCount; i++) {
                        Integer actCpnStatusCd = cpnLoadOutputResponseList.get(i).getStatusCd();
                        Assert.assertEquals(cpnStatusCd, actCpnStatusCd);
                    }
                } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart") || type.equals("redeemnegative")) {
                    for (int i = 0; i < redeemCount; i++) {
                        Integer actCpnStatusCd = cpnRedeemOutputResponseList.get(i).getStatusCd();
                        Assert.assertEquals(cpnStatusCd, actCpnStatusCd);
                    }
                }
            });

            Then("I see my cmpgnId as {int} {string}", (Integer campaignId, String type) -> {
                BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                System.out.println("printCount:" + printCount);
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                if (type.equals("view")) {
                    for (int i = 0; i < viewCount; i++) {
                        Integer actCmpgnId = cpnViewOutputResponseList.get(i).getCmpgnId();
                        Assert.assertEquals(campaignId, actCmpgnId);
                    }
                } else if (type.equals("print") || type.equals("printend") || type.equals("printstart")) {
                        Integer actCmpgnId = cpnPrintOutputResponseList.get(0).getCmpgnId();
                        Assert.assertEquals(campaignId, actCmpgnId);
                } else if (type.equals("load") || type.equals("loadredeemend") || (type.equals("digitize")) || (type.equals("predigitize"))) {
                    for (int i = 0; i < loadCount; i++) {
                        Integer actCmpgnId = cpnLoadOutputResponseList.get(i).getCmpgnId();
                        Assert.assertEquals(campaignId, actCmpgnId);
                    }
                } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart")) {
                    for (int i = 0; i < redeemCount; i++) {
                        Integer actCmpgnId = cpnRedeemOutputResponseList.get(i).getCmpgnId();
                        Assert.assertEquals(campaignId, actCmpgnId);
                    }
                }
            });

            Then("I see the record in xtra_card_summary for {int}", (Integer xtracardnbr) -> {
                xtraCardSummary.setXtraCardNbr(xtracardnbr);
                Integer expectedCount = 1;
                Integer actualCount = xtraCardDao.selectCountXtraCardSummary(xtraCardSummary);
               Assert.assertEquals(expectedCount,actualCount);

            });

            Then("I see my cpnSkuNbr1 as {int} {string}", (Integer cpnSkuNbr, String type) -> {
                BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();

//              Select campaign
                campaign.setCmpgnTypeCd("E");
                campaign.setCmpgnSubtypeCd("S");
                System.out.println("Campaign ID = "+campaignDao.selectInstantQEBCampaign(campaign));
                Integer cmpgnId = campaignDao.selectInstantQEBCampaign(campaign);
                
//              Select Coupon Nbr
                campaignCoupon.setCmpgnId(cmpgnId);
                System.out.println("CPN_NBR = "+campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2));
                cpnSkuNbr = campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2);
                
//              Select Coupon Nbr
                campaignCoupon.setCmpgnId(cmpgnId);
                System.out.println("CPN_NBR = "+campaignDao.selectCampaignCouponBK(campaignCoupon,2));
                int cpnSkuNbr2 = campaignDao.selectCampaignCouponBK(campaignCoupon,2);
                
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                Comparator<CpnViewOutputResponse> byCpnSkuNbr = Comparator.comparing(CpnViewOutputResponse::getCpnSkuNbr);
                Collections.sort(cpnViewOutputResponseList, byCpnSkuNbr);
                if (type.equals("view")) {
                    Integer actCpnSkuNbr = cpnViewOutputResponseList.get(0).getCpnSkuNbr();
                    System.out.println("expected cpn = "+cpnSkuNbr);
                    System.out.println("actual cpn = "+actCpnSkuNbr);
                    System.out.println("CPN_NBR = "+campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2));
//                    Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                    Assert.assertTrue(cpnSkuNbr == actCpnSkuNbr || cpnSkuNbr2==actCpnSkuNbr);
                } else if (type.equals("print")|| type.equals("printend") || type.equals("printstart")) {
                    Integer actCpnSkuNbr = cpnPrintOutputResponseList.get(0).getCpnSkuNbr();
                    Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                } else if (type.equals("load") || type.equals("loadredeemend") || type.equals("digitize") || type.equals("predigitize")) {
                        Integer actCpnSkuNbr = cpnLoadOutputResponseList.get(0).getCpnSkuNbr();
                        Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart")) {
                        Integer actCpnSkuNbr = cpnRedeemOutputResponseList.get(0).getCpnSkuNbr();
                        Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                }
            });

            Then("I see my cpnSkuNbr2 as {int} {string}", (Integer cpnSkuNbr, String type) -> {
                BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();

//              Select campaign
                campaign.setCmpgnTypeCd("E");
                campaign.setCmpgnSubtypeCd("S");
                System.out.println("Campaign ID = "+campaignDao.selectInstantQEBCampaign(campaign));
                Integer cmpgnId = campaignDao.selectInstantQEBCampaign(campaign);
                
//              Select Coupon Nbr
                campaignCoupon.setCmpgnId(cmpgnId);
                System.out.println("CPN_NBR = "+campaignDao.selectCampaignCouponBK(campaignCoupon,2));
                cpnSkuNbr = campaignDao.selectCampaignCouponBK(campaignCoupon,2);
                
//              Select Coupon Nbr
                campaignCoupon.setCmpgnId(cmpgnId);
                System.out.println("CPN_NBR = "+campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2));
                int cpnSkuNbr2 = campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2);
                
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                Comparator<CpnViewOutputResponse> byCpnSkuNbr = Comparator.comparing(CpnViewOutputResponse::getCpnSkuNbr);
                Collections.sort(cpnViewOutputResponseList, byCpnSkuNbr);
                if (type.equals("view")) {
                        Integer actCpnSkuNbr = cpnViewOutputResponseList.get(1).getCpnSkuNbr();
                        System.out.println("actCpnSkuNbr:" + actCpnSkuNbr);
//                        Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                        Assert.assertTrue(cpnSkuNbr == actCpnSkuNbr || cpnSkuNbr2==actCpnSkuNbr);
                } else if (type.equals("print")||type.equals("printend") || type.equals("printstart")) {
                        Integer actCpnSkuNbr = cpnPrintOutputResponseList.get(1).getCpnSkuNbr();
                        Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                } else if (type.equals("load") || type.equals("loadredeemend") || type.equals("digitize")  || type.equals("predigitize")) {
                        Integer actCpnSkuNbr = cpnLoadOutputResponseList.get(1).getCpnSkuNbr();
                        Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart")) {
                        Integer actCpnSkuNbr = cpnRedeemOutputResponseList.get(1).getCpnSkuNbr();
                        Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
                }
            });

            Then("I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as {int} {string} {string} {int}", (Integer xtraCardNbr, String cpnSeqNbr, String type, Integer matchCd) -> {
                BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                Long couponSequenceNumberExpected, couponSequenceNumberAuditExpected;
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                    couponSequenceNumberExpected = Long.valueOf(cpnSeqNbr.replace("_", "0"));
                    couponSequenceNumberAuditExpected = Long.valueOf(cpnSeqNbr.replace("_", "0"));
                } else {
//                    String[] cardNbrCampNbr = cpnSeqNbr.split("_");
//                    int campNbr = Integer.parseInt(cardNbrCampNbr[0]);
//                    int cpnSkuNbr = Integer.parseInt(cardNbrCampNbr[1]);
                	
//                  Select campaign
                    campaign.setCmpgnTypeCd("E");
                    campaign.setCmpgnSubtypeCd("S");
                    int campNbr = campaignDao.selectInstantQEBCampaign(campaign);
                    
//                  Select Coupon Nbr
                    campaignCoupon.setCmpgnId(campNbr);
                    int cpnSkuNbr = campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2);
                    
                    xtraCardActiveCoupon.setXtraCardNbr(xtraCardNbr);
                    xtraCardActiveCoupon.setCmpgnId(campNbr);
                    xtraCardActiveCoupon.setCpnNbr(cpnSkuNbr);
                    couponSequenceNumberExpected = xtraCardDao.selectCpnSeqNbrXtraCardActiveCoupon(xtraCardActiveCoupon);
                }
                    if (type.equals("view")) {
                        Long actCpnSeqNbr = cpnViewOutputResponseList.get(0).getCpnSeqNbr();
                        Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    } else if (type.equals("print")||type.equals("printend") || type.equals("printstart")) {
                        Long actCpnSeqNbr = cpnPrintOutputResponseList.get(0).getCpnSeqNbr();
                        Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    } else if (type.equals("load") || type.equals("loadredeemend") || type.equals("digitize")  || type.equals("predigitize")) {
                        Long actCpnSeqNbr = cpnLoadOutputResponseList.get(0).getCpnSeqNbr();
                        Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart")) {
                    	if (matchCd.equals(2)) {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(0).getCpnSeqNbr();
                    		Assert.assertEquals(Long.valueOf("0"), actCpnSeqNbr);
                    	} else if (matchCd.equals(3) || matchCd.equals(4)) {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(0).getCpnSeqNbr();
                    		String cpnSeqNbr1_new = Long.toString(couponSequenceNumberExpected);
                     	    int lastThreeIndex_1 = cpnSeqNbr1_new.length()-3; // 3nd character index from last
                     	    Long lastThreeDigits_1 = (long) Integer.parseInt(cpnSeqNbr1_new.substring(lastThreeIndex_1));
                    		Assert.assertEquals(lastThreeDigits_1, actCpnSeqNbr);
                    	} else if (matchCd.equals(7)) {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(0).getCpnSeqNbr();
                    		String cpnSeqNbr1_new = Long.toString(couponSequenceNumberExpected);
                     	    int lastEightIndex_1 = cpnSeqNbr1_new.length()-8; // 3nd character index from last
                     	    Long lastEightDigits_1 = (long) Integer.parseInt(cpnSeqNbr1_new.substring(lastEightIndex_1));
                    		Assert.assertEquals(lastEightDigits_1, actCpnSeqNbr);
                    	} else {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(0).getCpnSeqNbr();
                    		Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    	}         
                    }
            });

            Then("I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as {int} {string} {string} {int}", (Integer xtraCardNbr, String cpnSeqNbr, String type, Integer matchCd) -> {
                BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                Long couponSequenceNumberExpected, couponSequenceNumberAuditExpected;
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                    couponSequenceNumberExpected = Long.valueOf(cpnSeqNbr.replace("_", "0"));
                    couponSequenceNumberAuditExpected = Long.valueOf(cpnSeqNbr.replace("_", "0"));
                } else {
//                    String[] cardNbrCampNbr = cpnSeqNbr.split("_");
//                    int campNbr = Integer.parseInt(cardNbrCampNbr[0]);
//                    int cpnSkuNbr = Integer.parseInt(cardNbrCampNbr[1]);
                	
//                  Select campaign
                    campaign.setCmpgnTypeCd("E");
                    campaign.setCmpgnSubtypeCd("S");
                    int campNbr = campaignDao.selectInstantQEBCampaign(campaign);
                    
//                  Select Coupon Nbr
                    campaignCoupon.setCmpgnId(campNbr);
                    int cpnSkuNbr = campaignDao.selectCampaignCouponBK(campaignCoupon, 2);
                    
                    xtraCardActiveCoupon.setXtraCardNbr(xtraCardNbr);
                    xtraCardActiveCoupon.setCmpgnId(campNbr);
                    xtraCardActiveCoupon.setCpnNbr(cpnSkuNbr);
                    couponSequenceNumberExpected = xtraCardDao.selectCpnSeqNbrXtraCardActiveCoupon(xtraCardActiveCoupon);
                }
                    if (type.equals("view")) {
                        Long actCpnSeqNbr = cpnViewOutputResponseList.get(1).getCpnSeqNbr();
                        Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    } else if (type.equals("print")||type.equals("printend") || type.equals("printstart")) {
                        Long actCpnSeqNbr = cpnPrintOutputResponseList.get(1).getCpnSeqNbr();
                        Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    } else if (type.equals("load") || type.equals("loadredeemend") || type.equals("digitize")  || type.equals("predigitize")) {
                        Long actCpnSeqNbr = cpnLoadOutputResponseList.get(1).getCpnSeqNbr();
                        Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart")) {
                    	if (matchCd.equals(2)) {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(1).getCpnSeqNbr();
                    		Assert.assertEquals(Long.valueOf("0"), actCpnSeqNbr);
                    	} else if (matchCd.equals(3) || matchCd.equals(4)) {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(1).getCpnSeqNbr();
                    		String cpnSeqNbr1_new = Long.toString(couponSequenceNumberExpected);
                     	    int lastThreeIndex_1 = cpnSeqNbr1_new.length()-3; // 3nd character index from last
                     	    Long lastThreeDigits_1 = (long) Integer.parseInt(cpnSeqNbr1_new.substring(lastThreeIndex_1));
                    		Assert.assertEquals(lastThreeDigits_1, actCpnSeqNbr);
                    	} else if (matchCd.equals(7)) {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(1).getCpnSeqNbr();
                    		String cpnSeqNbr1_new = Long.toString(couponSequenceNumberExpected);
                     	    int lastEightIndex_1 = cpnSeqNbr1_new.length()-8; // 3nd character index from last
                     	    Long lastEightDigits_1 = (long) Integer.parseInt(cpnSeqNbr1_new.substring(lastEightIndex_1));
                    		Assert.assertEquals(lastEightDigits_1, actCpnSeqNbr);
                    	} else {
                    		Long actCpnSeqNbr = cpnRedeemOutputResponseList.get(1).getCpnSeqNbr();
                    		Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    	}
                    }
            });

            Then("I see my redeemStartDt as {string} {string}", (String redeemStartDt, String type) -> {
                if (redeemStartDt.equals("null")) {
                } else {
                BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                } else {
                    if (type.equals("view")) {
                        for (int i = 0; i < viewCount; i++) {
                            String actRedeemStartDt = cpnViewOutputResponseList.get(i).getRedeemStartDt();
                            Assert.assertEquals(redeemStartDt, actRedeemStartDt);
                        }
                    } else if (type.equals("print")||type.equals("printend") || type.equals("printstart")) {
                        for (int i = 0; i < printCount; i++) {
                            String actRedeemStartDt = cpnPrintOutputResponseList.get(i).getRedeemStartDt();
                            Assert.assertEquals(redeemStartDt, actRedeemStartDt);
                        }
                    } else if (type.equals("load") || type.equals("loadredeemend") || type.equals("digitize")  || type.equals("predigitize")) {
                        for (int i = 0; i < loadCount; i++) {
                            String actRedeemStartDt = cpnLoadOutputResponseList.get(i).getRedeemStartDt();
                            Assert.assertEquals(redeemStartDt, actRedeemStartDt);
                        }
                    } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart")) {
                        for (int i = 0; i < redeemCount; i++) {
                            String actRedeemStartDt = cpnRedeemOutputResponseList.get(i).getRedeemStartDt();
                            Assert.assertEquals(redeemStartDt, actRedeemStartDt);
                        }
                    }
                }
                }
            });

            Then("I see my redeemEndDt as {string} {string}", (String redeemEndDt, String type) -> {
                if (redeemEndDt.equals("null")) {
                } else {
                    BulkCpnsResponse bulkCpnsResponse = bulkCouponsService.getServiceResponse().as(BulkCpnsResponse.class);
                List<CpnViewOutputResponse> cpnViewOutputResponseList = bulkCpnsResponse.getViewResponseList();
                List<CpnPrintOutputResponse> cpnPrintOutputResponseList = bulkCpnsResponse.getPrintResponseList();
                List<CpnLoadOutputResponse> cpnLoadOutputResponseList = bulkCpnsResponse.getLoadResponseList();
                List<CpnRedeemOutputResponse> cpnRedeemOutputResponseList = bulkCpnsResponse.getRedeemResponseList();
                int viewCount = cpnViewOutputResponseList.size();
                int printCount = cpnPrintOutputResponseList.size();
                int loadCount = cpnLoadOutputResponseList.size();
                int redeemCount = cpnRedeemOutputResponseList.size();
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                } else {
                    if (type.equals("view")) {
                        for (int i = 0; i < viewCount; i++) {
                            String actRedeemEndDt = cpnViewOutputResponseList.get(i).getRedeemEndDt();
                            Assert.assertEquals(redeemEndDt, actRedeemEndDt);
                        }
                    } else if (type.equals("print")||type.equals("printend") || type.equals("printstart")) {
                        for (int i = 0; i < printCount; i++) {
                            String actRedeemEndDt = cpnPrintOutputResponseList.get(i).getRedeemEndDt();
                            Assert.assertEquals(redeemEndDt, actRedeemEndDt);
                        }
                    } else if (type.equals("load") || type.equals("loadredeemend") || type.equals("digitize") || type.equals("predigitize")) {
                        for (int i = 0; i < loadCount; i++) {
                            String actRedeemEndDt = cpnLoadOutputResponseList.get(i).getRedeemEndDt();
                            Assert.assertEquals(redeemEndDt, actRedeemEndDt);
                        }
                    } else if (type.equals("redeem") || type.equals("notloadedredeem")  || type.equals("redemend") || type.equals("redeemstart")) {
                        for (int i = 0; i < redeemCount; i++) {
                            String actRedeemEndDt = cpnRedeemOutputResponseList.get(i).getRedeemEndDt();
                            Assert.assertEquals(redeemEndDt, actRedeemEndDt);
                        }
                    }
                }
                }
            });
        }
    }
}