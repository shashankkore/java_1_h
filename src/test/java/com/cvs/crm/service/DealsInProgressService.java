package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.model.data.MvCampaignActiveInstant;
import com.cvs.crm.model.data.CriteriaSku;
import com.cvs.crm.model.data.CampaignStoreSku;
import com.cvs.crm.model.data.CampaignReward;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.util.CampaignEarnServiceUtil;
import com.cvs.crm.util.DateUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Data
public class DealsInProgressService {
    private Response serviceResponse;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CriteriaSku criteriaSku;

    @Autowired
    Customer customer;

    @Autowired
    CustomerAddress customerAddress;

    @Autowired
    CustomerEmail customerEmail;

    @Autowired
    CustomerPhone customerPhone;

    @Autowired
    CustomerOpt customerOpt;

    @Autowired
    Campaign campaign;

    @Autowired
    CampaignCoupon campaignCoupon;

    @Autowired
    CampaignCouponCriteria campaignCouponCriteria;

    @Autowired
    CampaignRewardThreshold campaignRewardThreshold;

    @Autowired
    CampaignActivePointBase campaignActivePointBase;

    @Autowired
    CampaignPoint campaignPoint;

    @Autowired
    CampaignEarnServiceUtil campaignEarnServiceUtil;

    @Autowired
    CampaignStoreSku campaignStoreSku;

    @Autowired
    MvCampaignActiveInstant mvCampaignActiveInstant;

    @Autowired
    CampaignReward campaignReward;


    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraParms xtraParms;

    @Autowired
    XtraCardSegment xtraCardSegment;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    public void viewDealsInProgress(DashBoardRequest dashBoardRequest) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;

        //TODO - We need a Utility Method to determine attributes
        if ("MOBILE".equalsIgnoreCase(dashBoardRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        }

        requestSpecBuilder.setBaseUri(serviceConfig.getBaseUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}/dashboards")
                .addPathParam("search_card_type", dashBoardRequest.getSearchCardType())
                .addPathParam("search_card_nbr", dashBoardRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).when().get();
    }

    /**
     * Create Test Data For Deals In Progress Scenario
     *
     * @throws InterruptedException
     */
    public void createDealsInProgressTestData() throws ParseException, InterruptedException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = new Date();
        String dateCurrent = simpleDateFormat.format(date);

        String patternTs = "yyyy-MM-dd HH.MM.SS a";
        SimpleDateFormat simpleDateFormatTs = new SimpleDateFormat(patternTs);


        DateTime dateTime = new DateTime();
        String todayDate = dateTime.toString("yyyy-MM-dd");
        String yestarday2Date = dateTime.minusDays(2).toString("yyyy-MM-dd");
        String yestardayDate = dateTime.minusDays(1).toString("yyyy-MM-dd");
        String tomorrowDate = dateTime.plusDays(1).toString("yyyy-MM-dd");
        String sixtyDate = dateTime.plusDays(60).toString("yyyy-MM-dd");
        String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
        String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");

        String todayTimeStamp = dateTime.toString("yyyy-MM-dd HH.MM.SS a");
        String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String tomorrowTimeStamp = dateTime.plusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String prev1yearTimeStamp = dateTime.minusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
        String future1yearTimeStamp = dateTime.plusYears(1).toString("yyyy-MM-dd HH.MM.SS a");

        
        
        //Campaign
//        campaign.setCmpgnId(40225);
        campaign.setCmpgnId(40963);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("T");
        campaign.setCmpgnDsc("Do not disturb - PEB_FF_CYL_$2EB on 4 Candies Purch");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(2);
        campaign.setRecptRxt("PEB_FF_CYL_$2EB on 4 Candies");
        campaign.setRecptScaleNbr(0);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setEndDt(simpleDateFormat.parse("2030-12-31"));
        campaign.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setLastIssueDt(simpleDateFormat.parse(sixtyDate));
        campaign.setPrevIssueDt(simpleDateFormat.parse(yestarday2Date));
        campaign.setNextIssueDt(simpleDateFormat.parse(yestardayDate));
        campaign.setDaysToPrintCnt(700);
        campaign.setDaysToRedeemCnt(100);
        campaign.setInHomeDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setTotlaRwrdEarnAmt(158);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards: PEB_FF_CYL_$2EB on 4");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(35);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd(null);
        campaign.setIndivRwrdAmt(2);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse(yestarday2Date));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt("PEB_FF_CYL_$2EB on 4 Candies Purch");
        campaign.setWebDsc("$2 ExtraBucks Rewards when you spend $15 on PEB");
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd("-1");
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(3);
        campaign.setCatMgrId("3");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(2);
        campaign.setCpnQualTxt("re are your ExtraBucks Rewards for having purchased: PEB_FF_CYL_$2EB on 4 Candies Purch!");
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt("PEB_FF_CYL_$2EB on 4 Candies");
        campaign.setTrgtPrntDestCd(null);
        campaign.setCpnMinPurchAmt(null);
        campaign.setLastFeedAccptDt(null);
        campaign.setAdvMaxRwrdQty(null);
        campaign.setPromoLinkNbr(null);
        campaign.setAmtTypeCd("D");
        campaign.setPctOffAmt(null);
        campaign.setFsaCpnInd(null);
        campaign.setPrtLabrlCpnInd(null);
        campaign.setDfltAlwaysInd(null);
        campaign.setDfltFreqDayCnt(null);
        campaign.setDfltFreqEmpDayCnt(null);
        campaign.setLoadableInd("-1");
        campaign.setCardTypeCd(null);
        campaign.setCpnRecptTxt("PEB_FF_CYL_$2EB on 4 Candies");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd(null);
        campaign.setMktgPrgCd("B");
        campaign.setMobileDispInd("-1");
        campaign.setOvrdPaperLessInd("N");
        campaign.setAnalyticEventTypeCd(null);
        campaign.setWebRedeemableInd("-1");
        campaign.setMfrCpnSrcCd(null);
//        campaign.setXtraCardSegNbr(102);
        campaign.setXtraCardSegNbr(335);
        campaign.setProductCriteriaId(15690);
        campaign.setDfltCpnCatXml(null);
        campaign.setSegIncExcCd(null);
        campaign.setSegSrcOwnerName(null);
        campaign.setSegSrcTableName(null);
        campaign.setSegReloadRqstTs(null);
        campaign.setSegLastProcStartTs(null);
        campaign.setSegLastProcEndTs(null);
        campaign.setSegLastProcStatCd("S");
        campaign.setSegLastProcRowCnt(100);
        campaign.setFixedRedeemStartDt(null);
        campaign.setFixedRedeemEndDt(null);
        campaign.setLastAutoReissueDt(null);
        campaign.setAutoReissueInd(null);
        campaign.setTrgtPrntRegCd("F");
        campaign.setFacebookDispInd(null);
        campaign.setInstantCmpgnEarnigInd("-1");
        campaign.setPeOptimizeTypeCd(null);
//        campaignDao.createCampaign(campaign);
        campaignDao.updateCampaign1(campaign);

//        campaignRewardThreshold.setCmpgnId(40225);
        campaignRewardThreshold.setCmpgnId(40963);
        campaignRewardThreshold.setThrshldLimNbr(4);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("Q");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);
//        campaignDao.updateCampaignRewardThreshold(campaignRewardThreshold);

//        campaignCoupon.setCmpgnId(40225);
        campaignCoupon.setCmpgnId(40963);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(58889);
        campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(tomorrowDate));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards: PEB_FF_CYL_$2EB on 4");
        campaignCoupon.setMaxRedeemAmt(2);
        campaignCoupon.setCpnTermsCd(3);
        campaignCoupon.setAmtTypeCd("Q");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd(null);
        campaignCoupon.setCpnRecptTxt("PEB_FF_CYL_$2EB on 4 Candies");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("3");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(null);
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

//        campaignCoupon.setCmpgnId(40225);
        campaignCoupon.setCmpgnId(40963);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(58890);
        campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(tomorrowDate));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards: PEB_FF_CYL_$2EB on 4");
        campaignCoupon.setMaxRedeemAmt(4);
        campaignCoupon.setCpnTermsCd(3);
        campaignCoupon.setAmtTypeCd("Q");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd(null);
        campaignCoupon.setCpnRecptTxt("PEB_FF_CYL_$2EB on 4 Candies");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("3");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

//        campaignCoupon.setCmpgnId(40225);
        campaignCoupon.setCmpgnId(40963);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(58891);
        campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(tomorrowDate));
        campaignCoupon.setCpnDsc("$6.00 ExtraBucks Rewards: PEB_FF_CYL_$2EB on 4");
        campaignCoupon.setMaxRedeemAmt(6);
        campaignCoupon.setCpnTermsCd(3);
        campaignCoupon.setAmtTypeCd("Q");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd(null);
        campaignCoupon.setCpnRecptTxt("PEB_FF_CYL_$2EB on 4 Candies");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("3");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

        campaignCouponCriteria.setCmpgnId(40963);
        campaignCouponCriteria.setCpnNbr(58889);
        campaignCouponCriteria.setCriteriaUsageCd("GV");
        campaignCouponCriteria.setAllItemInd("N");
        campaignCouponCriteria.setCriteriaId(15690);
        campaignCouponCriteria.setIncExcCd("I");
        campaignCouponCriteria.setRqrdPurchAmt(1);
        campaignCouponCriteria.setAmtTypeCd("Q");
        campaignCouponCriteria.setLastChngDt(simpleDateFormat.parse(yestardayDate));
        campaignDao.CreateCampaignCouponCriteria(campaignCouponCriteria);
//        campaignDao.updateCampaignCouponCriteria(campaignCouponCriteria);


//        campaign.setCmpgnId(40226);
        campaign.setCmpgnId(40780);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("T");
        campaign.setCmpgnDsc("Do not disturb - PEB_FF_Cyl_$2EB on $30 Cold");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(2);
        campaign.setRecptRxt("PEB_FF_Cyl_$2EB on $30 Cold");
        campaign.setRecptScaleNbr(0);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(yestardayDate));
        campaign.setEndDt(simpleDateFormat.parse(sixtyDate));
        campaign.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setLastIssueDt(simpleDateFormat.parse(sixtyDate));
        campaign.setPrevIssueDt(simpleDateFormat.parse(yestarday2Date));
        campaign.setNextIssueDt(simpleDateFormat.parse(yestardayDate));
        campaign.setDaysToPrintCnt(700);
        campaign.setDaysToRedeemCnt(100);
        campaign.setInHomeDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setTotlaRwrdEarnAmt(158);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards: PEB_FF_Cyl_$2EB on $30 Cold Rem");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(35);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd(null);
        campaign.setIndivRwrdAmt(2);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse(yestarday2Date));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt("PEB_FF_Cyl_$2EB on $30 Cold");
        campaign.setWebDsc("$2 ExtraBucks Rewards when you spend $30 on PEB");
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd("-1");
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(3);
        campaign.setCatMgrId("3");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(2);
        campaign.setCpnQualTxt("re are your ExtraBucks Rewards for having purchased: PEB_FF_Cyl_$2EB on $30 Cold Rem!");
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt("PEB_FF_Cyl_$2EB on $30 Cold");
        campaign.setTrgtPrntDestCd(null);
        campaign.setCpnMinPurchAmt(null);
        campaign.setLastFeedAccptDt(null);
        campaign.setAdvMaxRwrdQty(null);
        campaign.setPromoLinkNbr(null);
        campaign.setAmtTypeCd("D");
        campaign.setPctOffAmt(null);
        campaign.setFsaCpnInd(null);
        campaign.setPrtLabrlCpnInd(null);
        campaign.setDfltAlwaysInd(null);
        campaign.setDfltFreqDayCnt(null);
        campaign.setDfltFreqEmpDayCnt(null);
        campaign.setLoadableInd("-1");
        campaign.setCardTypeCd(null);
        campaign.setCpnRecptTxt("PEB_FF_Cyl_$2EB on $30 Cold");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd(null);
        campaign.setMktgPrgCd("B");
        campaign.setMobileDispInd("-1");
        campaign.setOvrdPaperLessInd("N");
        campaign.setAnalyticEventTypeCd(null);
        campaign.setWebRedeemableInd("-1");
        campaign.setMfrCpnSrcCd(null);
//        campaign.setXtraCardSegNbr(105);
        campaign.setXtraCardSegNbr(336);
        campaign.setProductCriteriaId(15691);
        campaign.setDfltCpnCatXml(null);
        campaign.setSegIncExcCd(null);
        campaign.setSegSrcOwnerName(null);
        campaign.setSegSrcTableName(null);
        campaign.setSegReloadRqstTs(null);
        campaign.setSegLastProcStartTs(null);
        campaign.setSegLastProcEndTs(null);
        campaign.setSegLastProcStatCd("S");
        campaign.setSegLastProcRowCnt(100);
        campaign.setFixedRedeemStartDt(null);
        campaign.setFixedRedeemEndDt(null);
        campaign.setLastAutoReissueDt(null);
        campaign.setAutoReissueInd(null);
        campaign.setTrgtPrntRegCd("F");
        campaign.setFacebookDispInd(null);
        campaign.setInstantCmpgnEarnigInd("-1");
        campaign.setPeOptimizeTypeCd(null);
//        campaignDao.createCampaign(campaign);
        campaignDao.updateCampaign1(campaign);

//        campaignRewardThreshold.setCmpgnId(40226);
        campaignRewardThreshold.setCmpgnId(40780);
        campaignRewardThreshold.setThrshldLimNbr(30);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("D");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);
//        campaignDao.updateCampaignRewardThreshold(campaignRewardThreshold);

//        campaignCoupon.setCmpgnId(40226);
        campaignCoupon.setCmpgnId(40780);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(58892);
        campaignCoupon.setStartDt(simpleDateFormat.parse(yestardayDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(sixtyDate));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards: PEB_FF_Cyl_$2EB on $3");
        campaignCoupon.setMaxRedeemAmt(2);
        campaignCoupon.setCpnTermsCd(4);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd(null);
        campaignCoupon.setCpnRecptTxt("PEB_FF_Cyl_$2EB on $30 CldRem");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("F");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

//        campaignCoupon.setCmpgnId(40226);
        campaignCoupon.setCmpgnId(40780);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(58893);
        campaignCoupon.setStartDt(simpleDateFormat.parse(yestardayDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(sixtyDate));
        campaignCoupon.setCpnDsc("$4.00 ExtraBucks Rewards: PEB_FF_Cyl_$2EB on $3");
        campaignCoupon.setMaxRedeemAmt(4);
        campaignCoupon.setCpnTermsCd(4);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd(null);
        campaignCoupon.setCpnRecptTxt("PEB_FF_Cyl_$2EB on $30 CldRem");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("F");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

//        campaignCoupon.setCmpgnId(40226);
        campaignCoupon.setCmpgnId(40780);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(58894);
        campaignCoupon.setStartDt(simpleDateFormat.parse(yestardayDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(sixtyDate));
        campaignCoupon.setCpnDsc("$6.00 ExtraBucks Rewards: PEB_FF_Cyl_$2EB on $3");
        campaignCoupon.setMaxRedeemAmt(6);
        campaignCoupon.setCpnTermsCd(4);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd(null);
        campaignCoupon.setCpnRecptTxt("PEB_FF_Cyl_$2EB on $3");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("F");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

        campaignCouponCriteria.setCmpgnId(40780);
        campaignCouponCriteria.setCpnNbr(58892);
        campaignCouponCriteria.setCriteriaUsageCd("GV");
        campaignCouponCriteria.setAllItemInd("N");
        campaignCouponCriteria.setCriteriaId(15691);
        campaignCouponCriteria.setIncExcCd("I");
        campaignCouponCriteria.setRqrdPurchAmt(1);
        campaignCouponCriteria.setAmtTypeCd("D");
        campaignCouponCriteria.setLastChngDt(simpleDateFormat.parse(yestardayDate));
        campaignDao.CreateCampaignCouponCriteria(campaignCouponCriteria);
//        campaignDao.updateCampaignCouponCriteria(campaignCouponCriteria);


//        campaign.setCmpgnId(41624);
        campaign.setCmpgnId(49213);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("T");
        campaign.setCmpgnDsc("Do not Delete - Personalized_Extrabucks_product_criteria_field");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(1);
        campaign.setRecptRxt("PE coupon $5");
        campaign.setRecptScaleNbr(0);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(60)));
        campaign.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setLastIssueDt(simpleDateFormat.parse(sixtyDate));
        campaign.setPrevIssueDt(simpleDateFormat.parse(yestarday2Date));
        campaign.setNextIssueDt(simpleDateFormat.parse(yestardayDate));
        campaign.setDaysToPrintCnt(365);
        campaign.setDaysToRedeemCnt(100);
        campaign.setInHomeDt(simpleDateFormat.parse(prev1yearDate));
        campaign.setTotlaRwrdEarnAmt(158);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards: PEB_FF_Cyl_$2EB on $30 Cold Rem");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(35);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd(null);
        campaign.setIndivRwrdAmt(2);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse(yestarday2Date));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt("PE coupon $5");
        campaign.setWebDsc("PE coupon $5");
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd("-1");
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(2);
        campaign.setCatMgrId("3");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(2);
        campaign.setCpnQualTxt("Here are yoPE coupon $5ur ExtraBucks Rewards for having purchased: PE coupon $5");
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt("PE coupon $5");
        campaign.setTrgtPrntDestCd(null);
        campaign.setCpnMinPurchAmt(null);
        campaign.setLastFeedAccptDt(null);
        campaign.setAdvMaxRwrdQty(null);
        campaign.setPromoLinkNbr(null);
        campaign.setAmtTypeCd("D");
        campaign.setPctOffAmt(null);
        campaign.setFsaCpnInd(null);
        campaign.setPrtLabrlCpnInd(null);
        campaign.setDfltAlwaysInd(null);
        campaign.setDfltFreqDayCnt(null);
        campaign.setDfltFreqEmpDayCnt(null);
        campaign.setLoadableInd("-1");
        campaign.setCardTypeCd(null);
        campaign.setCpnRecptTxt("PE coupon $5");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(10);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd(null);
        campaign.setMktgPrgCd("B");
        campaign.setMobileDispInd("-1");
        campaign.setOvrdPaperLessInd("N");
        campaign.setAnalyticEventTypeCd(null);
        campaign.setWebRedeemableInd("-1");
        campaign.setMfrCpnSrcCd(null);
//        campaign.setXtraCardSegNbr(178);
        campaign.setXtraCardSegNbr(337);
        campaign.setProductCriteriaId(26708);
        campaign.setDfltCpnCatXml(null);
        campaign.setSegIncExcCd(null);
        campaign.setSegSrcOwnerName(null);
        campaign.setSegSrcTableName(null);
        campaign.setSegReloadRqstTs(null);
        campaign.setSegLastProcStartTs(null);
        campaign.setSegLastProcEndTs(null);
        campaign.setSegLastProcStatCd("S");
        campaign.setSegLastProcRowCnt(100);
        campaign.setFixedRedeemStartDt(null);
        campaign.setFixedRedeemEndDt(null);
        campaign.setLastAutoReissueDt(null);
        campaign.setAutoReissueInd(null);
        campaign.setTrgtPrntRegCd("F");
        campaign.setFacebookDispInd(null);
        campaign.setInstantCmpgnEarnigInd("-1");
        campaign.setPeOptimizeTypeCd(null);
//        campaignDao.createCampaign(campaign);
        campaignDao.updateCampaign1(campaign);

//        campaignRewardThreshold.setCmpgnId(41624);
        campaignRewardThreshold.setCmpgnId(49213);
        campaignRewardThreshold.setThrshldLimNbr(30);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("D");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);
//        campaignDao.updateCampaignRewardThreshold(campaignRewardThreshold);

//        campaignCoupon.setCmpgnId(41624);
        campaignCoupon.setCmpgnId(49213);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(73239);
        campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(60)));
        campaignCoupon.setCpnDsc("$5.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(5);
        campaignCoupon.setCpnTermsCd(2);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("OTR");
        campaignCoupon.setCpnRecptTxt("PE coupon $5");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(" ");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("F");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

//        campaignCoupon.setCmpgnId(41624);
        campaignCoupon.setCmpgnId(49213);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(73240);
        campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(60)));
        campaignCoupon.setCpnDsc("$10.00 ExtraBucks Rewards_GMARTS");
        campaignCoupon.setMaxRedeemAmt(10);
        campaignCoupon.setCpnTermsCd(2);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("OTR");
        campaignCoupon.setCpnRecptTxt("PE coupon $5");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(" ");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("F");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

//        campaignCoupon.setCmpgnId(41624);
        campaignCoupon.setCmpgnId(49213);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(73241);
        campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(60)));
        campaignCoupon.setCpnDsc("$15.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(15);
        campaignCoupon.setCpnTermsCd(2);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("OTR");
        campaignCoupon.setCpnRecptTxt("PE coupon $5");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(" ");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd("-1");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("F");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);
//        campaignDao.updateCampaignCoupon1(campaignCoupon);

        campaignCouponCriteria.setCmpgnId(49213);
        campaignCouponCriteria.setCpnNbr(73239);
        campaignCouponCriteria.setCriteriaUsageCd("GV");
        campaignCouponCriteria.setAllItemInd("N");
        campaignCouponCriteria.setCriteriaId(26708);
        campaignCouponCriteria.setIncExcCd("I");
        campaignCouponCriteria.setRqrdPurchAmt(1);
        campaignCouponCriteria.setAmtTypeCd("D");
        campaignCouponCriteria.setLastChngDt(simpleDateFormat.parse(yestardayDate));
        campaignDao.CreateCampaignCouponCriteria(campaignCouponCriteria);
//        campaignDao.updateCampaignCouponCriteria(campaignCouponCriteria);



        /*  I am CVS EC member but deal is expiring soon
         *
         */
        xtraCard.setXtraCardNbr(98158324);
        xtraCard.setCustId(80057);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80057);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80057);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80057);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80057);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158324);
//        xtraCardSegment.setXtraCardSegNbr(102);
        xtraCardSegment.setXtraCardSegNbr(335);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        campaignPoint.setCmpgnId(40225);
        campaignPoint.setCmpgnId(40963);
        campaignPoint.setXtraCardNbr(98158324);
        campaignPoint.setPtsQty(10);
        campaignPoint.setPtsAdjQty(null);
        campaignPoint.setPtsOtherQty(null);
        campaignPoint.setLastLastUpdtDt(null);
        campaignPoint.setPtSpeakQty(null);
        campaignPoint.setLastChngPtsQty(null);
        campaignPoint.setPtsXferInQty(null);
        campaignPoint.setPtsXrefOutQty(null);
        campaignPoint.setPtsOtherXferInqty(null);
        campaignPoint.setPtsOtherXferOutQty(null);
        campaignPoint.setActivationTs(null);
        campaignPoint.setActivationSrcCd("W");
//        if(campaignDao.selectCampaignPoint(campaignPoint)!=0) {
//        	campaignDao.updateCampaignPoint(campaignPoint);
//        } else {
        	campaignDao.createCampaignPoint(campaignPoint);
//        }

//        campaignReward.setCmpgnId(40225);
        campaignReward.setCmpgnId(40963);
        campaignReward.setXtraCardNbr(98158324);
        campaignReward.setRwrdEarnQty(2);
        campaignReward.setRwrdIssuedQty(2);
        campaignReward.setLastEarnUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastIssuedUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastEarnchngQty(0);
        campaignReward.setMaxCmpgnCpnSeqNbr(3);
//        if(campaignDao.selectCampaignReward(campaignPoint)!=0) {
//        	campaignDao.updateCampaignReward(campaignReward);
//        } else {
        	campaignDao.createCampaignReward(campaignReward);
//        }


//        campaignEarnServiceUtil.hitCampaignEarn(98158324, 4, 10, 999975);

        /*
        mvCampaignActiveInstant.setCmpgnId(40225);
        mvCampaignActiveInstant.setCmpgnId(53700);
        mvCampaignActiveInstant.setWebDesc("PEB_FF_CYL_$2EB on 4 Candies");
        mvCampaignActiveInstant.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(60)));
        mvCampaignActiveInstant.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(2)));
        campaignDao.updateMvCampaignActiveInstant(mvCampaignActiveInstant);
*/


        /*  I am CVS EC member joined recently in deal
         *
         */
        xtraCard.setXtraCardNbr(98158325);
        xtraCard.setCustId(80058);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80058);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80058);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80058);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80058);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158325);
//        xtraCardSegment.setXtraCardSegNbr(105);
        xtraCardSegment.setXtraCardSegNbr(336);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);


//        campaignPoint.setCmpgnId(40226);
        campaignPoint.setCmpgnId(40780);
        campaignPoint.setXtraCardNbr(98158325);
        campaignPoint.setPtsQty(32);
        campaignPoint.setPtsAdjQty(null);
        campaignPoint.setPtsOtherQty(null);
        campaignPoint.setLastLastUpdtDt(null);
        campaignPoint.setPtSpeakQty(null);
        campaignPoint.setLastChngPtsQty(null);
        campaignPoint.setPtsXferInQty(null);
        campaignPoint.setPtsXrefOutQty(null);
        campaignPoint.setPtsOtherXferInqty(null);
        campaignPoint.setPtsOtherXferOutQty(null);
        campaignPoint.setActivationTs(null);
        campaignPoint.setActivationSrcCd("W");
//        if(campaignDao.selectCampaignPoint(campaignPoint)!=0) {
//        	campaignDao.updateCampaignPoint(campaignPoint);
//        } else {
        	campaignDao.createCampaignPoint(campaignPoint);
//        }

//        campaignReward.setCmpgnId(40226);
        campaignReward.setCmpgnId(40780);
        campaignReward.setXtraCardNbr(98158325);
        campaignReward.setRwrdEarnQty(2);
        campaignReward.setRwrdIssuedQty(2);
        campaignReward.setLastEarnUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastIssuedUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastEarnchngQty(0);
        campaignReward.setMaxCmpgnCpnSeqNbr(3);
//        if(campaignDao.selectCampaignReward(campaignPoint)!=0) {
//        	campaignDao.updateCampaignReward(campaignReward);
//        } else {
        	campaignDao.createCampaignReward(campaignReward);
//        }

//        campaignEarnServiceUtil.hitCampaignEarn(98158325, 4, 32, 2030);

        /*
        mvCampaignActiveInstant.setCmpgnId(40226);
        mvCampaignActiveInstant.setCmpgnId(49106);
        mvCampaignActiveInstant.setWebDesc("PEB_FF_Cyl_$2EB on $3");
        mvCampaignActiveInstant.setStartDt(simpleDateFormat.parse(yestardayDate));
        mvCampaignActiveInstant.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(60)));
        campaignDao.updateMvCampaignActiveInstant(mvCampaignActiveInstant);
*/

        /*  I am CVS EC member and showing deals in progress points near threshold
         *
         */
        xtraCard.setXtraCardNbr(98158326);
        xtraCard.setCustId(80059);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80059);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80059);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80059);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80059);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158326);
//        xtraCardSegment.setXtraCardSegNbr(178);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        campaignPoint.setCmpgnId(41624);
        campaignPoint.setCmpgnId(49213);
        campaignPoint.setXtraCardNbr(98158326);
        campaignPoint.setPtsQty(29);
        campaignPoint.setPtsAdjQty(null);
        campaignPoint.setPtsOtherQty(null);
        campaignPoint.setLastLastUpdtDt(null);
        campaignPoint.setPtSpeakQty(null);
        campaignPoint.setLastChngPtsQty(null);
        campaignPoint.setPtsXferInQty(null);
        campaignPoint.setPtsXrefOutQty(null);
        campaignPoint.setPtsOtherXferInqty(null);
        campaignPoint.setPtsOtherXferOutQty(null);
        campaignPoint.setActivationTs(null);
        campaignPoint.setActivationSrcCd("W");
//        if(campaignDao.selectCampaignPoint(campaignPoint)!=0) {
//        	campaignDao.updateCampaignPoint(campaignPoint);
//        } else {
        	campaignDao.createCampaignPoint(campaignPoint);
//        }

//        campaignReward.setCmpgnId(41624);
        campaignReward.setCmpgnId(49213);
        campaignReward.setXtraCardNbr(98158326);
        campaignReward.setRwrdEarnQty(0);
        campaignReward.setRwrdIssuedQty(0);
        campaignReward.setLastEarnUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastIssuedUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastEarnchngQty(0);
        campaignReward.setMaxCmpgnCpnSeqNbr(3);
//        if(campaignDao.selectCampaignReward(campaignPoint)!=0) {
//        	campaignDao.updateCampaignReward(campaignReward);
//        } else {
        	campaignDao.createCampaignReward(campaignReward);
//        }

//        campaignEarnServiceUtil.hitCampaignEarn(98158326, 29, 10, 200926);
/*
//        mvCampaignActiveInstant.setCmpgnId(41624);
//        mvCampaignActiveInstant.setCmpgnId(49213);
//        mvCampaignActiveInstant.setWebDesc("PE coupon $5");
//        mvCampaignActiveInstant.setStartDt(simpleDateFormat.parse(prev1yearDate));
//        mvCampaignActiveInstant.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(60)));
//        campaignDao.updateMvCampaignActiveInstant(mvCampaignActiveInstant);
*/

        /*  I am able to see deals mapped to correct type based on threshold Q (Qty) and D (Dollar)
         *
         */
        xtraCard.setXtraCardNbr(98158322);
        xtraCard.setCustId(80055);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80055);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80055);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80055);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80055);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158322);
//        xtraCardSegment.setXtraCardSegNbr(178);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        campaignPoint.setCmpgnId(41624);
        campaignPoint.setCmpgnId(49213);
        campaignPoint.setXtraCardNbr(98158322);
        campaignPoint.setPtsQty(30);
        campaignPoint.setPtsAdjQty(null);
        campaignPoint.setPtsOtherQty(null);
        campaignPoint.setLastLastUpdtDt(null);
        campaignPoint.setPtSpeakQty(null);
        campaignPoint.setLastChngPtsQty(null);
        campaignPoint.setPtsXferInQty(null);
        campaignPoint.setPtsXrefOutQty(null);
        campaignPoint.setPtsOtherXferInqty(null);
        campaignPoint.setPtsOtherXferOutQty(null);
        campaignPoint.setActivationTs(null);
        campaignPoint.setActivationSrcCd("W");
//        if(campaignDao.selectCampaignPoint(campaignPoint)!=0) {
//        	campaignDao.updateCampaignPoint(campaignPoint);
//        } else {
        	campaignDao.createCampaignPoint(campaignPoint);
//        }

//        campaignReward.setCmpgnId(41624);
        campaignReward.setCmpgnId(49213);
        campaignReward.setXtraCardNbr(98158322);
        campaignReward.setRwrdEarnQty(1);
        campaignReward.setRwrdIssuedQty(1);
        campaignReward.setLastEarnUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastIssuedUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastEarnchngQty(0);
        campaignReward.setMaxCmpgnCpnSeqNbr(3);
//        if(campaignDao.selectCampaignReward(campaignPoint)!=0) {
//        	campaignDao.updateCampaignReward(campaignReward);
//        } else {
        	campaignDao.createCampaignReward(campaignReward);
//        }

//        campaignEarnServiceUtil.hitCampaignEarn(98158322, 4, 30, 200926);
/*
//        mvCampaignActiveInstant.setCmpgnId(41624);
//        mvCampaignActiveInstant.setCmpgnId(49213);
//        mvCampaignActiveInstant.setWebDesc("PE coupon $5");
//        mvCampaignActiveInstant.setStartDt(simpleDateFormat.parse(prev1yearDate));
//        mvCampaignActiveInstant.setEndDt(simpleDateFormat.parse(sixtyDate));
//        campaignDao.updateMvCampaignActiveInstant(mvCampaignActiveInstant);
*/

        /* I am CVS EC member and have at least one deals in progress
         *
         */
        xtraCard.setXtraCardNbr(98158323);
        xtraCard.setCustId(80056);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80056);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80056);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80056);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80056);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158323);
//        xtraCardSegment.setXtraCardSegNbr(178);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);


//        campaignPoint.setCmpgnId(41624);
        campaignPoint.setCmpgnId(49213);
        campaignPoint.setXtraCardNbr(98158323);
        campaignPoint.setPtsQty(40);
        campaignPoint.setPtsAdjQty(null);
        campaignPoint.setPtsOtherQty(null);
        campaignPoint.setLastLastUpdtDt(null);
        campaignPoint.setPtSpeakQty(null);
        campaignPoint.setLastChngPtsQty(null);
        campaignPoint.setPtsXferInQty(null);
        campaignPoint.setPtsXrefOutQty(null);
        campaignPoint.setPtsOtherXferInqty(null);
        campaignPoint.setPtsOtherXferOutQty(null);
        campaignPoint.setActivationTs(null);
        campaignPoint.setActivationSrcCd("W");
//        if(campaignDao.selectCampaignPoint(campaignPoint)!=0) {
//        	campaignDao.updateCampaignPoint(campaignPoint);
//        } else {
        	campaignDao.createCampaignPoint(campaignPoint);
//        }

//        campaignReward.setCmpgnId(41624);
        campaignReward.setCmpgnId(49213);
        campaignReward.setXtraCardNbr(98158323);
        campaignReward.setRwrdEarnQty(1);
        campaignReward.setRwrdIssuedQty(1);
        campaignReward.setLastEarnUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastIssuedUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastEarnchngQty(0);
        campaignReward.setMaxCmpgnCpnSeqNbr(3);
//        if(campaignDao.selectCampaignReward(campaignPoint)!=0) {
//        	campaignDao.updateCampaignReward(campaignReward);
//        } else {
        	campaignDao.createCampaignReward(campaignReward);
//        }


//        campaignEarnServiceUtil.hitCampaignEarn(98158323, 4, 40, 200926);
/*
//        mvCampaignActiveInstant.setCmpgnId(41624);
//        mvCampaignActiveInstant.setCmpgnId(49213);
//        mvCampaignActiveInstant.setWebDesc("PE coupon $5");
//        mvCampaignActiveInstant.setStartDt(simpleDateFormat.parse(prev1yearDate));
//        mvCampaignActiveInstant.setEndDt(simpleDateFormat.parse(sixtyDate));
//        campaignDao.updateMvCampaignActiveInstant(mvCampaignActiveInstant);
*/

        /*  I am CVS EC member but there are no active deals in progress - Zero State
         *
         */
        xtraCard.setXtraCardNbr(98158319);
        xtraCard.setCustId(80052);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80052);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80052);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80052);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80052);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158319);
//        xtraCardSegment.setXtraCardSegNbr(178);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);


//        campaignPoint.setCmpgnId(41624);
        campaignPoint.setCmpgnId(49213);
        campaignPoint.setXtraCardNbr(98158319);
        campaignPoint.setPtsQty(0);
        campaignPoint.setPtsAdjQty(null);
        campaignPoint.setPtsOtherQty(null);
        campaignPoint.setLastLastUpdtDt(null);
        campaignPoint.setPtSpeakQty(null);
        campaignPoint.setLastChngPtsQty(null);
        campaignPoint.setPtsXferInQty(null);
        campaignPoint.setPtsXrefOutQty(null);
        campaignPoint.setPtsOtherXferInqty(null);
        campaignPoint.setPtsOtherXferOutQty(null);
        campaignPoint.setActivationTs(null);
        campaignPoint.setActivationSrcCd("W");
//        if(campaignDao.selectCampaignPoint(campaignPoint)!=0) {
//        	campaignDao.updateCampaignPoint(campaignPoint);
//        } else {
        	campaignDao.createCampaignPoint(campaignPoint);
//        }

//        campaignReward.setCmpgnId(41624);
        campaignReward.setCmpgnId(49213);
        campaignReward.setXtraCardNbr(98158319);
        campaignReward.setRwrdEarnQty(0);
        campaignReward.setRwrdIssuedQty(0);
        campaignReward.setLastEarnUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastIssuedUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastEarnchngQty(0);
        campaignReward.setMaxCmpgnCpnSeqNbr(3);
//        if(campaignDao.selectCampaignReward(campaignPoint)!=0) {
//        	campaignDao.updateCampaignReward(campaignReward);
//        } else {
        	campaignDao.createCampaignReward(campaignReward);
//        }

//        campaignEarnServiceUtil.hitCampaignEarn(98158319, 0, 0, 200926);
/*
//        mvCampaignActiveInstant.setCmpgnId(41624);
//        mvCampaignActiveInstant.setCmpgnId(49213);
//        mvCampaignActiveInstant.setWebDesc("PE coupon $5");
//        mvCampaignActiveInstant.setStartDt(simpleDateFormat.parse(prev1yearDate));
//        mvCampaignActiveInstant.setEndDt(simpleDateFormat.parse(sixtyDate));
//        campaignDao.updateMvCampaignActiveInstant(mvCampaignActiveInstant);
*/

/*
I am CVS EC member but does not have any activity towards Circular EB and Personalized EB campaigns
 */


        xtraCard.setXtraCardNbr(98158320);
        xtraCard.setCustId(80053);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80053);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80053);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80053);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80053);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158320);
//        xtraCardSegment.setXtraCardSegNbr(178);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);



/*
I am CVS EC member and showing deals in progress point is zero
 */


        xtraCard.setXtraCardNbr(98158321);
        xtraCard.setCustId(80054);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80054);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80054);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80054);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80054);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98158321);
//        xtraCardSegment.setXtraCardSegNbr(178);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);


//        campaignPoint.setCmpgnId(41624);
        campaignPoint.setCmpgnId(49213);
        campaignPoint.setXtraCardNbr(98158321);
        campaignPoint.setPtsQty(0);
        campaignPoint.setPtsAdjQty(null);
        campaignPoint.setPtsOtherQty(null);
        campaignPoint.setLastLastUpdtDt(null);
        campaignPoint.setPtSpeakQty(null);
        campaignPoint.setLastChngPtsQty(null);
        campaignPoint.setPtsXferInQty(null);
        campaignPoint.setPtsXrefOutQty(null);
        campaignPoint.setPtsOtherXferInqty(null);
        campaignPoint.setPtsOtherXferOutQty(null);
        campaignPoint.setActivationTs(null);
        campaignPoint.setActivationSrcCd("W");
//        if(campaignDao.selectCampaignPoint(campaignPoint)!=0) {
//        	campaignDao.updateCampaignPoint(campaignPoint);
//        } else {
        	campaignDao.createCampaignPoint(campaignPoint);
//        }

//        campaignReward.setCmpgnId(41624);
        campaignReward.setCmpgnId(49213);
        campaignReward.setXtraCardNbr(98158321);
        campaignReward.setRwrdEarnQty(0);
        campaignReward.setRwrdIssuedQty(0);
        campaignReward.setLastEarnUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastIssuedUpdtDt(simpleDateFormat.parse(dateUtil.dealEndDate(0)));
        campaignReward.setLastEarnchngQty(0);
        campaignReward.setMaxCmpgnCpnSeqNbr(3);
//        if(campaignDao.selectCampaignReward(campaignPoint)!=0) {
//        	campaignDao.updateCampaignReward(campaignReward);
//        } else {
        	campaignDao.createCampaignReward(campaignReward);
//        }

//        campaignEarnServiceUtil.hitCampaignEarn(98158321, 0, 0, 200926);
/*
//        mvCampaignActiveInstant.setCmpgnId(41624);
//        mvCampaignActiveInstant.setCmpgnId(49213);
//        mvCampaignActiveInstant.setWebDesc("PE coupon $5");
//        mvCampaignActiveInstant.setStartDt(simpleDateFormat.parse(prev1yearDate));
//        mvCampaignActiveInstant.setEndDt(simpleDateFormat.parse(sixtyDate));
//        campaignDao.updateMvCampaignActiveInstant(mvCampaignActiveInstant);
*/
      /*

        criteriaSku.setCriteriaId(15690);
        campaignDao.selectCriteriaSku(criteriaSku);
*/

//        cacheRefreshUtil.refresCacheusingXtraParms();
//        cacheRefreshUtil.refresCacheforCmpgnDefns();
    }


    /**
     * Delete Test Data for Deals In Progress Scenario
     */
    public void deleteDealsInProgressTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158319, 98158320, 98158321, 98158322, 98158323, 98158324, 98158325, 98158326);
        List<Integer> custIdList = Arrays.asList(80052, 80053, 80054, 80055, 80056, 80057, 80058, 80059);
//        List<Integer> cmpgnIdList = Arrays.asList(40225, 40226, 41624, 53700, 49106, 49213);
        List<Integer> cmpgnIdList = Arrays.asList(49213, 49218, 40963, 40780);

//        campaignDao.deleteCampaignRecordsDealsInProgress(cmpgnIdList, xtraCardNbrList);
        campaignDao.deleteCampaignRecordsDealsInProgress1(cmpgnIdList, xtraCardNbrList);

        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}

