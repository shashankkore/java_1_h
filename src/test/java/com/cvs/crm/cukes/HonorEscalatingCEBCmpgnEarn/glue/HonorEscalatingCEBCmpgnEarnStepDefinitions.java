package com.cvs.crm.cukes.HonorEscalatingCEBCmpgnEarn.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.request.BulkCpnRequest;
import com.cvs.crm.model.request.CmpgnEarnRequest;
import com.cvs.crm.model.response.*;
import com.cvs.crm.service.HonorEscalatingCEBCmpgnEarnService;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import cucumber.api.java8.En;
import lombok.extern.slf4j.Slf4j;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class HonorEscalatingCEBCmpgnEarnStepDefinitions extends SpringIntegrationTests implements En {
    Integer xtraCardNum = null;

    @Autowired
    HonorEscalatingCEBCmpgnEarnService honorEscalatingCEBCmpgnEarnService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    Campaign campaign;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    CampaignDao campaignDao;

    public HonorEscalatingCEBCmpgnEarnStepDefinitions() {
        {
            CmpgnEarnRequest request = new CmpgnEarnRequest();

            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                xtraCardNum = xtraCardNbr;
            });

            Given("I use channel {string}", (String channel) -> {
                request.setChannel(channel);
            });

            When("I want Honor escalating circular ExtraBucks to be Created for {int} {string} {int} {int} {int}", (Integer xtracardnbr, String type, Integer amount, Integer scanqty, Integer contSkuNbrList) -> {
                honorEscalatingCEBCmpgnEarnService.viewHonorEscalatingCEBCmpgnEarnCreation(request, xtracardnbr, type, amount, scanqty, contSkuNbrList);
            });

            When("I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible {int} {string} {int} {int} {int} {int} {int} {int}", (Integer xtracardnbr, String type, Integer amount, Integer scanqty, Integer contSkuNbrList, Integer amount2, Integer scanqty2, Integer contSkuNbrList2) -> {
                honorEscalatingCEBCmpgnEarnService.viewHonorEscalatingCEBCmpgnEarnCreationBoth(request, xtracardnbr, type, amount, scanqty, contSkuNbrList, amount2, scanqty2, contSkuNbrList2);
            });

            Then("I see the Campaign Earn Response", () -> {
                honorEscalatingCEBCmpgnEarnService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I see {int} earnings", (Integer expEarnings) -> {
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
               Integer actEarnings=  campaignEarnResponse.getRespCmpgnEarningsResponseList().size();
                Assert.assertEquals(expEarnings, actEarnings);
            });

            Then("I see my prevPtsAmt as {int} for campaign {int}", (Integer prevPtsAmt, Integer cmpgnId) -> {
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
                Integer actCmpgnEarn = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCmpgnId();
                if (cmpgnId.equals(actCmpgnEarn)) {
                    Integer actPrevPtsAmt = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getPtsResponse().getPrevPtsAmt();
                    Assert.assertEquals(prevPtsAmt, actPrevPtsAmt);
                }
            });

            Then("I see my newPtsAmt as {int} for campaign {int}", (Integer newPtsAmt, Integer cmpgnId) -> {
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
                Integer actCmpgnEarn = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCmpgnId();
                if (cmpgnId.equals(actCmpgnEarn)) {
                    Integer actNewPtsAmt = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getPtsResponse().getNewPtsAmt();
                    Assert.assertEquals(newPtsAmt, actNewPtsAmt);
                }
            });

            Then("I see my prevRwrdsQty as {int} for campaign {int}", (Integer prevRwrdsQty, Integer cmpgnId) -> {
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
                Integer actCmpgnEarn = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCmpgnId();
                if (cmpgnId.equals(actCmpgnEarn)) {
                    Integer actPrevRwrdsQty = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getRwrdsResponse().getPrevRwrdsQty();
                    Assert.assertEquals(prevRwrdsQty, actPrevRwrdsQty);
                }
            });

            Then("I see my newRwrdsQty as {int} for campaign {int}", (Integer newRwrdsQty, Integer cmpgnId) -> {
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
                Integer actCmpgnEarn = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCmpgnId();
                if (cmpgnId.equals(actCmpgnEarn)) {
                    Integer actPrevRwrdsQty = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getRwrdsResponse().getNewRwrdsQty();
                    Assert.assertEquals(newRwrdsQty, actPrevRwrdsQty);
                }
            });

            Then("I see my cpnNbr got inserted in XtraCardActiveCoupon for {int} {string}", (Integer campaignId, String cpnSeqNbr) -> {
                Long cpnSeqNumExp = null;
                cpnSeqNumExp = Long.valueOf(cpnSeqNbr.replace("_", "0"));
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                    cpnSeqNumExp = Long.valueOf(cpnSeqNbr.replace("_", "0"));

                } else {
                    String[] cardNbrCpnNbr = cpnSeqNbr.split("_");
                    int cardNbr = Integer.parseInt(cardNbrCpnNbr[0]);
                    int cpnNbr = Integer.parseInt(cardNbrCpnNbr[1]);
                    System.out.println("cardNbr:" + cardNbr);
                    System.out.println("cpnNbr:" + cpnNbr);
                    System.out.println("campaignId:" + campaignId);
                    xtraCardActiveCoupon.setXtraCardNbr(cardNbr);
                    xtraCardActiveCoupon.setCmpgnId(campaignId);
                    xtraCardActiveCoupon.setCpnNbr(cpnNbr);
                    cpnSeqNumExp = xtraCardDao.selectCpnSeqNbrXtraCardActiveCoupon(xtraCardActiveCoupon);
                }
                Long actCpnSeqNbr = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCpnsResponse().getCpnSeqNbr();
                Assert.assertEquals(cpnSeqNumExp, actCpnSeqNbr);
            });

            Then("I see my offerLimitReached as {string} for campaign {int}", (String offerLimitReached, Integer cmpgnId) -> {
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
                Integer actCmpgnEarn = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCmpgnId();
                if (cmpgnId.equals(actCmpgnEarn)) {
                    String actOfferLimitReached = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getRwrdsResponse().getOfferLimitReachedInd();
                    Assert.assertEquals(offerLimitReached, actOfferLimitReached);
                }
            });

            Then("I see my expireDt as {string} for campaign {int}", (String printenddt, Integer cmpgnId) -> {
                CampaignEarnResponse campaignEarnResponse = honorEscalatingCEBCmpgnEarnService.getServiceResponse().as(CampaignEarnResponse.class);
                Integer actCmpgnEarn = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCmpgnId();
                if (cmpgnId.equals(actCmpgnEarn)) {
                    campaign.setCmpgnId(cmpgnId);
                  Integer printCnt =   campaignDao.selectDaystoPrintCntCampaign(campaign);
                 String expExpDt =   (carePassDateUtil.carePassExpireTswtz(printCnt)).replace("-","");
                 System.out.println("expExpDt:" + expExpDt);
                    String actOfferLimitReached = campaignEarnResponse.getRespCmpgnEarningsResponseList().get(0).getCpnsResponse().getExpDt();
                    Assert.assertEquals(expExpDt, actOfferLimitReached);
                }
            });



        }
    }
}
