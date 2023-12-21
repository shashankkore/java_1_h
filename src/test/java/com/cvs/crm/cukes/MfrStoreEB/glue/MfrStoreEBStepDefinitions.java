package com.cvs.crm.cukes.MfrStoreEB.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.XtraCardCouponAudit;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.CampaignOMCoupon;
import com.cvs.crm.model.request.MfrCpnRequest;
import com.cvs.crm.model.request.StoreEBRequest;
import com.cvs.crm.model.response.*;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.MfrStoreEBService;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class MfrStoreEBStepDefinitions extends SpringIntegrationTests implements En {

    Integer xtraCardNum = null;
    String version = null;

    @Autowired
    MfrStoreEBService mfrStoreEBService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    XtraCardCouponAudit xtraCardCouponAudit;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CampaignOMCoupon campaignOMCoupon;
    
    @Autowired
    CampaignCoupon campaignCoupon;   


    public MfrStoreEBStepDefinitions() {

        {
            MfrCpnRequest request = new MfrCpnRequest();

            StoreEBRequest request1 = new StoreEBRequest();


            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
                xtraCardNum = xtraCardNbr;
            });

            Given("I use my ExtraCard {int}", (Integer xtraCardNbr) -> {
                request1.setSearchCardType("0002");
                request1.setSearchCardNbr(xtraCardNbr);
                xtraCardNum = xtraCardNbr;
            });
            
            And("I use Campaign ID {int}", (Integer cmpgnID) -> {
                request.setSearchCmpgnId(cmpgnID);
            });
            
            And("I use recent coupon number", () -> {
            	 Long couponSequenceNumberExpected, couponSequenceNumberAuditExpected;
                 if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
//                     couponSequenceNumberExpected = Long.valueOf(couponSequenceNumber.replace("_", "0"));
//                     couponSequenceNumberAuditExpected = Long.valueOf(couponSequenceNumber.replace("_", "0"));
                 } else {
                     request.setSearchCpnSkuNbr(campaignDao.selectRecentCampaignOMCoupon());
//                     System.out.println("coupon code is" + couponSequenceNumberExpected);
                 }
            });


            Given("I use channel {string}", (String channel) -> {
                request.setChannel(channel);
            });

            Given("I use {string}", (String channel) -> {
                request1.setChannel(channel);
            });

            When("I want MFR Coupon to be Created for {int}", (Integer xtraCardNbr) -> {
                mfrStoreEBService.viewMfrCreation(request, xtraCardNbr);
            });

            Then("I see the Coupon got Created", () -> {
                mfrStoreEBService.getServiceResponse().then().log().all().statusCode(200);
            });

//            Then("I see my cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as {string}", (String couponSequenceNumber) -> {
//                Long couponSequenceNumberExpected, couponSequenceNumberAuditExpected;
//                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
////                    couponSequenceNumberExpected = Long.valueOf(couponSequenceNumber.replace("_", "0"));
////                    couponSequenceNumberAuditExpected = Long.valueOf(couponSequenceNumber.replace("_", "0"));
//                } else {
//                    String[] cardNbrCampNbr = couponSequenceNumber.split("_");
//                    int cardNbr = Integer.parseInt(cardNbrCampNbr[0]);
//                    int campNbr = Integer.parseInt(cardNbrCampNbr[1]);
//                    xtraCardActiveCoupon.setXtraCardNbr(cardNbr);
//                    xtraCardActiveCoupon.setCmpgnId(campNbr);
//                    couponSequenceNumberExpected = xtraCardDao.selectXtraCardActiveCoupon(xtraCardActiveCoupon);
//                    xtraCardCouponAudit.setXtraCardNbr(cardNbr);
//                    couponSequenceNumberAuditExpected = xtraCardDao.selectXtraCardCouponAudit(xtraCardCouponAudit);
//                    MfrCpnsResponse[] mfrCpnsResponse = mfrStoreEBService.getServiceResponse().as(MfrCpnsResponse[].class);
//                    Integer icount = mfrCpnsResponse.length;
//                    Long actCpnSeqNbr = mfrCpnsResponse[icount - 1].getCpnSeqNbr();
//                    Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
//                    Assert.assertEquals(couponSequenceNumberAuditExpected, actCpnSeqNbr);
//                }
//
//            });
            
            Then("I see my cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit", () -> {
                Long couponSequenceNumberExpected, couponSequenceNumberAuditExpected;
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
//                    couponSequenceNumberExpected = Long.valueOf(couponSequenceNumber.replace("_", "0"));
//                    couponSequenceNumberAuditExpected = Long.valueOf(couponSequenceNumber.replace("_", "0"));
                } else {
//                    String[] cardNbrCampNbr = couponSequenceNumber.split("_");
//                    int cardNbr = Integer.parseInt(cardNbrCampNbr[0]);
//                    int campNbr = Integer.parseInt(cardNbrCampNbr[1]);
                    Thread.sleep(3000);
                	xtraCardActiveCoupon.setXtraCardNbr(request.getSearchCardNbr());
                    xtraCardActiveCoupon.setCmpgnId(request.getSearchCmpgnId());
                    couponSequenceNumberExpected = xtraCardDao.selectXtraCardActiveCoupon(xtraCardActiveCoupon);
                    xtraCardCouponAudit.setXtraCardNbr(request.getSearchCardNbr());
                    couponSequenceNumberAuditExpected = xtraCardDao.selectXtraCardCouponAudit(xtraCardCouponAudit);
                    MfrCpnsResponse[] mfrCpnsResponse = mfrStoreEBService.getServiceResponse().as(MfrCpnsResponse[].class);
                    Integer icount = mfrCpnsResponse.length;
                    Long actCpnSeqNbr = mfrCpnsResponse[icount - 1].getCpnSeqNbr();
                    Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                    Assert.assertEquals(couponSequenceNumberAuditExpected, actCpnSeqNbr);
//                    System.out.println("couponSequenceNumberExpected "+couponSequenceNumberExpected);
//                    System.out.println("couponSequenceNumberAuditExpected "+couponSequenceNumberAuditExpected);
//                    System.out.println("actCpnSeqNbr "+actCpnSeqNbr);
                }

            });

            Then("I see my cpnStatusCd as {int}", (Integer cpnStatusCd) -> {
                MfrCpnsResponse[] mfrCpnsResponse = mfrStoreEBService.getServiceResponse().as(MfrCpnsResponse[].class);
                Integer icount = mfrCpnsResponse.length;
                Integer actCpnStatusCd = mfrCpnsResponse[icount - 1].getCpnStatusCd();
                Assert.assertEquals(cpnStatusCd, actCpnStatusCd);
            });

            Then("I see my cmpgnId as {int}", (Integer campaignId) -> {

                MfrCpnsResponse[] mfrCpnsResponse = mfrStoreEBService.getServiceResponse().as(MfrCpnsResponse[].class);
                Integer icount = mfrCpnsResponse.length;
                Integer actCmpgnId = mfrCpnsResponse[icount - 1].getCmpgnId();
                Assert.assertEquals(campaignId, actCmpgnId);
            });

            Then("I see my cpnSkuNbr", () -> {
                MfrCpnsResponse[] mfrCpnsResponse = mfrStoreEBService.getServiceResponse().as(MfrCpnsResponse[].class);
                Integer icount = mfrCpnsResponse.length;
                Integer actCpnSkuNbr = mfrCpnsResponse[icount - 1].getCpnSkuNbr();
                Integer cpnSkuNbr = campaignDao.selectRecentCampaignOMCoupon();
                Assert.assertEquals(cpnSkuNbr, actCpnSkuNbr);
//                System.out.println("cpnSkuNbr1 "+cpnSkuNbr1);
//                System.out.println("actCpnSkuNbr "+actCpnSkuNbr);
//                System.out.println("cpnSkuNbr "+cpnSkuNbr);
            });

            Then("I see my redeemEndDt as {string}", (String redeemEndDt) -> {
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                } else {
                    String[] cpnNbrCampNbr = redeemEndDt.split("_");
                    int campNbr = Integer.parseInt(cpnNbrCampNbr[0]);
                    int cpnNbr = Integer.parseInt(cpnNbrCampNbr[1]);
                    campaignOMCoupon.setCpnNbr(cpnNbr);
                    campaignOMCoupon.setCmpgnId(campNbr);
                    String expRedeemEndDt = campaignDao.selectCampaignOMCoupon(campaignOMCoupon);
                    String expectedRedeemEndDate = expRedeemEndDt.substring(0, 4) + expRedeemEndDt.substring(5, 7) + expRedeemEndDt.substring(8, 10);
                    MfrCpnsResponse[] mfrCpnsResponse = mfrStoreEBService.getServiceResponse().as(MfrCpnsResponse[].class);
                    Integer icount = mfrCpnsResponse.length;
                    String actRedeemEndDt = (mfrCpnsResponse[icount - 1].getRedeemEndDt()).substring(0, 8);
                    Assert.assertEquals(expectedRedeemEndDate, actRedeemEndDt);
                }
            });
            
            Then("I see my coupon sequence number as {long}", (Long cpnSeqNbr) -> {
                MfrCpnsResponse[] mfrCpnsResponse = mfrStoreEBService.getServiceResponse().as(MfrCpnsResponse[].class);
                Integer icount = mfrCpnsResponse.length;
                Long actCpnSeqNbr = mfrCpnsResponse[icount - 1].getCpnSeqNbr();
                Assert.assertEquals(cpnSeqNbr, actCpnSeqNbr);
            });


            Then("I do not see the Coupon got created", () -> {
                mfrStoreEBService.getServiceResponse().then().log().all().statusCode(400);
            });

            When("I want storeEB Coupon to be Created for amount {double}", (Double amount) -> {
                mfrStoreEBService.viewStoreEBCreation(request1, amount);
            });


            Then("I see storeEB Coupon got Created", () -> {
                mfrStoreEBService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I see valid coupon is being applied for amount {string}", (String amount) -> {
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Integer cmpgnId = storeEBCpnCreationResponse.getCmpgnId();
                Integer cpnNbr = storeEBCpnCreationResponse.getCpnNbr();
                campaignCoupon.setCmpgnId(cmpgnId);
                campaignCoupon.setCpnNbr(cpnNbr);
                String cpnDsc = campaignDao.selectCampaignCouponDesc(campaignCoupon);
                System.out.println("expected: "+ amount);
                System.out.println("actual: "+ cpnDsc);
                Assert.assertTrue(cpnDsc.contains(amount));
                });
            
            Then("I see my cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit for StoreEB coupon", () -> {
                Long couponSequenceNumberExpected, couponSequenceNumberAuditExpected;
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Integer cmpgnId = storeEBCpnCreationResponse.getCmpgnId();
                Integer xtraCardNbr = storeEBCpnCreationResponse.getXtraCardNbr();
                xtraCardActiveCoupon.setXtraCardNbr(xtraCardNbr);
                xtraCardActiveCoupon.setCmpgnId(cmpgnId);
                couponSequenceNumberExpected = xtraCardDao.selectXtraCardActiveCoupon(xtraCardActiveCoupon);
                xtraCardCouponAudit.setXtraCardNbr(xtraCardNbr);	
                couponSequenceNumberAuditExpected = xtraCardDao.selectXtraCardCouponAudit(xtraCardCouponAudit);
                Long actCpnSeqNbr = storeEBCpnCreationResponse.getCpnSeqNbr();
                Assert.assertEquals(couponSequenceNumberExpected, actCpnSeqNbr);
                Assert.assertEquals(couponSequenceNumberAuditExpected, actCpnSeqNbr);
            });
            
//            Then("I see my storeEB cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as {string}", (String cpnSeqNbr) -> {
//                Long couponSequenceNumberExpected, couponSequenceNumberAuditExpected;
//                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
//                    couponSequenceNumberExpected = Long.valueOf(cpnSeqNbr.replace("_", "0"));
//                    couponSequenceNumberAuditExpected = Long.valueOf(cpnSeqNbr.replace("_", "0"));
//                } else {
//                    String[] cardNbrCampNbr = cpnSeqNbr.split("_");
//                    int cardNbr = Integer.parseInt(cardNbrCampNbr[0]);
//                    int campNbr = Integer.parseInt(cardNbrCampNbr[1]);
//                    xtraCardActiveCoupon.setXtraCardNbr(cardNbr);
//                    xtraCardActiveCoupon.setCmpgnId(campNbr);
//                    couponSequenceNumberExpected = xtraCardDao.selectXtraCardActiveCoupon(xtraCardActiveCoupon);
//                    xtraCardCouponAudit.setXtraCardNbr(cardNbr);
//                    couponSequenceNumberAuditExpected = xtraCardDao.selectXtraCardCouponAudit(xtraCardCouponAudit);
//                }
//
//                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
//                Assert.assertEquals(couponSequenceNumberExpected, storeEBCpnCreationResponse.getCpnSeqNbr());
//                Assert.assertEquals(couponSequenceNumberAuditExpected, storeEBCpnCreationResponse.getCpnSeqNbr());
//            });
            
            Then("I see my ExtraCard as {int}", (Integer xtraCardNbr) -> {
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Assert.assertEquals(xtraCardNbr, storeEBCpnCreationResponse.getXtraCardNbr());
            });

            Then("I see my storeEB cpnFlag as {string}", (String cpnFlag) -> {
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Assert.assertEquals(cpnFlag, storeEBCpnCreationResponse.getCpnFlag());
            });

            Then("I see my storeEB cmpgnId as {int}", (Integer cmpgnId) -> {
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Assert.assertEquals(cmpgnId, storeEBCpnCreationResponse.getCmpgnId());
            });

            Then("I see my storeEB cpnSkuNbr as {int}", (Integer cpnSkuNbr) -> {
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Assert.assertEquals(cpnSkuNbr, storeEBCpnCreationResponse.getCpnNbr());
            });

            Then("I see my storeEB firstName as {string}", (String firstName) -> {
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Assert.assertEquals(firstName, storeEBCpnCreationResponse.getFirstName());
            });

            Then("I see my storeEB lastName as {string}", (String lastName) -> {
                StoreEBCpnCreationResponse storeEBCpnCreationResponse = mfrStoreEBService.getServiceResponse().as(StoreEBCpnCreationResponse.class);
                Assert.assertEquals(lastName, storeEBCpnCreationResponse.getLastName());
            });


            Then("I see Error Message as {string}", (String errorMsg) -> {
                String apiErrorResponse = mfrStoreEBService.getServiceResponse().getBody().asString();
                Assert.assertEquals(errorMsg, apiErrorResponse);
            });

        }
    }
}