package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.repository.*;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import com.cvs.crm.util.JsonUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class GetCustTablesnCpnsService {

    private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    HrMemberProfile hrMemberProfile;

    @Autowired
    HrMemberSmry hrMemberSmry;

    @Autowired
    HrMemberEnroll hrMemberEnroll;

    @Autowired
    HrMemberHippa hrMemberHippa;

    @Autowired
    HRDao hRDao;

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
    XtraCardChild xtraCardChild;

    @Autowired
    CarepassEnrollFormHist carepassEnrollFormHist;

    @Autowired
    CarepassMemberSmry carepassMemberSmry;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraParms xtraParms;

    @Autowired
    EmployeeCard employeeCard;

    @Autowired
    XtraCardAnalyticEvent xtraCardAnalyticEvent;

    @Autowired
    CarepassMemberStatusHist carepassMemberStatusHist;

    @Autowired
    Campaign campaign;

    @Autowired
    CampaignCoupon campaignCoupon;

    @Autowired
    CampaignRewardThreshold campaignRewardThreshold;

    @Autowired
    CampaignActivePointBase campaignActivePointBase;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CarePassDao carePassDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    private Environment environment;

    @Autowired
    JsonUtil jsonUtil;

    public void viewGetCustTablesnPreferences(GetCustRequest getCustRequest) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
        if ("M".equalsIgnoreCase(getCustRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else if ("W".equalsIgnoreCase(getCustRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else {
            msgSrcCd = "R";
            userId = "STORE";
            srcLocCd = 68585;
        }
        String jsonRequest = "{\n" +
                " \"cusInfReq\": {\n" +
                "xtraCare\": [\n" +
                " \"cpns\"\n" +
                " ]}\n" +
                "}";

        String ver = getCustRequest.getVersion();

        String expver = "\"one\"";

        if (ver.equals(expver)) {

            requestSpecBuilder.setBaseUri(serviceConfig.getGetcustUrl())
                    .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                    .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                    .addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr())
                    .addQueryParam("msg_src_cd", msgSrcCd)
                    .addQueryParam("user_id", userId)
                    .addQueryParam("src_loc_cd", srcLocCd);

            RequestSpecification spec = requestSpecBuilder.build();
            serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonRequest).post();
        } else {

            requestSpecBuilder.setBaseUri(serviceConfig.getGetcustUrl())
                    .setBasePath("api/v1.2/customers/" + getCustRequest.getSearchCardType() + "," + getCustRequest.getSearchCardNbr())
                    //.addPathParam("search_card_type", getCustRequest.getSearchCardType())
                    //.addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr())
                    .addQueryParam("msg_src_cd", msgSrcCd)
                    .addQueryParam("user_id", userId)
                    .addQueryParam("src_loc_cd", srcLocCd);

            RequestSpecification spec = requestSpecBuilder.build();
            serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonRequest).post();
        }
    }

    public void viewGetCustTablesnPreferencesOne(GetCustRequest getCustRequest, String xtraCarePref, String msgSrcCd) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String userId;
        int srcLocCd;
        if ("MOBILE".equalsIgnoreCase(getCustRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        }
        String jsonRequest = "{\n" +
                " \"cusInfReq\": {\n" +
                " \"xtraCare\": [\n" +
                " \"" + xtraCarePref + "\"\n" +
                "]\n" +
                " }\n" +
                "}";
        System.out.println(jsonRequest + "  cardNumber: " + getCustRequest.getSearchCardNbr());
        String ver = getCustRequest.getVersion();
        String cardNbr= "";
        if(getCustRequest.getSearchCardNbr()==null)
            cardNbr="abc";
        else
            cardNbr = getCustRequest.getSearchCardNbr().toString();
        requestSpecBuilder.setBaseUri(serviceConfig.getGetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonRequest).post();
        serviceResponse.prettyPrint();
    }

    /**
     * Create Test Data for getCust Tables and Preferences Scenario
     *
     * @param
     * @throws InterruptedException
     */
    public void createGetCustTablesnPreferencesTestData() throws ParseException, InterruptedException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = new Date();
        String dateCurrent = simpleDateFormat.format(date);

        String patternTs = "yyyy-MM-dd HH.MM.SS a";
        SimpleDateFormat simpleDateFormatTs = new SimpleDateFormat(patternTs);


        DateTime dateTime = new DateTime();
        String todayDate = dateTime.toString("yyyy-MM-dd");
        String yestardayDate = dateTime.minusDays(1).toString("yyyy-MM-dd");
        String tomorrowDate = dateTime.plusDays(1).toString("yyyy-MM-dd");
        String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
        String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");

        String todayTimeStamp = dateTime.toString("yyyy-MM-dd HH.MM.SS a");
        String todayTimeStampXP = dateTime.toString("yyyyMMdd HH:MM:SS");
        String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String tomorrowTimeStamp = dateTime.plusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String prev1yearTimeStamp = dateTime.minusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
        String future1yearTimeStamp = dateTime.plusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
        String todayTimeStampzn = dateTime.toString("yyyy-MM-dd HH.MM.SS a");


        //Default Campaign
        campaign.setCmpgnId(59726);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("H");
        campaign.setCmpgnDsc("2020 ExtraCare Pharmacy and Health Rewards");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt("Pharmacy and Health ExtraBucks");
        campaign.setRecptScaleNbr(0);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse("2020-01-01"));
        campaign.setEndDt(simpleDateFormat.parse("2020-12-31"));
        campaign.setLastUpdtTs(null);
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse("2020-01-01"));
        campaign.setLastIssueDt(simpleDateFormat.parse("2021-01-07"));
        campaign.setPrevIssueDt(simpleDateFormat.parse("2020-06-17"));
        campaign.setNextIssueDt(simpleDateFormat.parse("2020-06-18"));
        campaign.setDaysToPrintCnt(30);
        campaign.setDaysToRedeemCnt(14);
        campaign.setInHomeDt(simpleDateFormat.parse("2020-01-01"));
        campaign.setTotlaRwrdEarnAmt(33117612);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(null);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd("STR");
        campaign.setIndivRwrdAmt(5);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse("2020-06-17"));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt("Fill 10 prescriptions and get $5 Pharmacy and Health ExtraBucks Rewards");
        campaign.setWebDsc("$5 Pharmacy and Health ExtraBucks Rewards");
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd(null);
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(1);
        campaign.setCatMgrId("K");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(2);
        campaign.setCpnQualTxt("Here are your ExtraBucks Rewards earned from the ExtraCare Pharmacy and Health Rewards program by all enrolled individuals using this ExtraCare card.");
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt("Fill 10 prescriptions Get $5EB");
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
        campaign.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd("N");
        campaign.setMktgPrgCd("H");
        campaign.setMobileDispInd("-1");
        campaign.setOvrdPaperLessInd("N");
        campaign.setAnalyticEventTypeCd(null);
        campaign.setWebRedeemableInd("-1");
        campaign.setMfrCpnSrcCd(null);
        campaign.setXtraCardSegNbr(null);
        campaign.setProductCriteriaId(null);
        campaign.setDfltCpnCatXml(null);
        campaign.setSegIncExcCd(null);
        campaign.setSegSrcOwnerName(null);
        campaign.setSegSrcTableName(null);
        campaign.setSegReloadRqstTs(null);
        campaign.setSegLastProcStartTs(null);
        campaign.setSegLastProcEndTs(null);
        campaign.setSegLastProcStatCd(null);
        campaign.setSegLastProcRowCnt(null);
        campaign.setFixedRedeemStartDt(null);
        campaign.setFixedRedeemEndDt(null);
        campaign.setLastAutoReissueDt(null);
        campaign.setAutoReissueInd(null);
        campaign.setTrgtPrntRegCd(null);
        campaign.setFacebookDispInd(null);
        campaign.setInstantCmpgnEarnigInd("0");
        campaign.setPeOptimizeTypeCd(null);
        campaignDao.createCampaign(campaign);

        //Pilot campaign
        campaign.setCmpgnId(64355);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("H");
        campaign.setCmpgnDsc("2020 ExtraCare Pharmacy and Health Rewards Pilot");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt("Pharmacy and Health ExtraBucks");
        campaign.setRecptScaleNbr(0);
        campaign.setRwrdRedirInd(null);
        campaign.setStartDt(simpleDateFormat.parse("2020-01-01"));
        campaign.setEndDt(simpleDateFormat.parse("2020-12-31"));
        campaign.setLastUpdtTs(null);
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse("2020-05-27"));
        campaign.setLastIssueDt(simpleDateFormat.parse("2021-06-08"));
        campaign.setPrevIssueDt(simpleDateFormat.parse("2020-06-17"));
        campaign.setNextIssueDt(simpleDateFormat.parse("2020-06-18"));
        campaign.setDaysToPrintCnt(30);
        campaign.setDaysToRedeemCnt(14);
        campaign.setInHomeDt(simpleDateFormat.parse("2020-01-01"));
        campaign.setTotlaRwrdEarnAmt(451925);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(null);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd("STR");
        campaign.setIndivRwrdAmt(2);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse("2020-06-17"));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt("Fill 4 prescriptions and get $2 Pharmacy and Health ExtraBucks Rewards");
        campaign.setWebDsc("$2 Pharmacy and Health ExtraBucks Rewards");
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd("0");
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(1);
        campaign.setCatMgrId("K");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(0);
        campaign.setCpnQualTxt("Here are your ExtraBucks Rewards earned from the ExtraCare Pharmacy and Health Rewards program by all enrolled individuals using this ExtraCare card.");
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt("Fill 4 prescriptions Get $2EB");
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
        campaign.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd("N");
        campaign.setMktgPrgCd("H");
        campaign.setMobileDispInd("-1");
        campaign.setOvrdPaperLessInd("N");
        campaign.setAnalyticEventTypeCd(null);
        campaign.setWebRedeemableInd("-1");
        campaign.setMfrCpnSrcCd(null);
        campaign.setXtraCardSegNbr(663);
        campaign.setProductCriteriaId(null);
        campaign.setDfltCpnCatXml(null);
        campaign.setSegIncExcCd(null);
        campaign.setSegSrcOwnerName("P_ETL_ST");
        campaign.setSegSrcTableName("PHR_PILOT_2020_CMPGN_64355");
        campaign.setSegReloadRqstTs(null);
        campaign.setSegLastProcStartTs(simpleDateFormatTs.parse("2020-05-31 08.24.55 PM"));
        campaign.setSegLastProcEndTs(simpleDateFormatTs.parse("2020-05-31 08.26.27 PM"));
        campaign.setSegLastProcStatCd("S");
        campaign.setSegLastProcRowCnt(560006);
        campaign.setFixedRedeemStartDt(null);
        campaign.setFixedRedeemEndDt(null);
        campaign.setLastAutoReissueDt(null);
        campaign.setAutoReissueInd(null);
        campaign.setTrgtPrntRegCd(null);
        campaign.setFacebookDispInd(null);
        campaign.setInstantCmpgnEarnigInd(null);
        campaign.setPeOptimizeTypeCd(null);
        campaignDao.createCampaign(campaign);


//Default campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(169232);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$5.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(5);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

        //Default campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(169233);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$10.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(10);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(169234);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$15.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(15);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(169235);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$20.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(20);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-5 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(5);
        campaignCoupon.setCpnNbr(169236);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$25.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(25);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-6 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(6);
        campaignCoupon.setCpnNbr(169331);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-06"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$30.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(30);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-7 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(7);
        campaignCoupon.setCpnNbr(169332);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-06"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$35.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(35);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-8 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(8);
        campaignCoupon.setCpnNbr(170109);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-16"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$40.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(40);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-9 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(9);
        campaignCoupon.setCpnNbr(169937);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-11"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$45.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(45);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-10 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(10);
        campaignCoupon.setCpnNbr(172875);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-02-02"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$50.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(50);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-11 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(11);
        campaignCoupon.setCpnNbr(172876);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-02-02"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$55.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(55);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//Default campaign Coupon-14 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(14);
        campaignCoupon.setCpnNbr(184208);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-03-20"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$70.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(70);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


//pilot campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(182409);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-03-13"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(2);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(185392);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-03-30"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-09-02"));
        campaignCoupon.setCpnDsc("$4.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(4);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(191667);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-06-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-22"));
        campaignCoupon.setCpnDsc("$6.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(6);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(191668);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$8.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(8);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


//pilot campaign Coupon-5 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(5);
        campaignCoupon.setCpnNbr(191669);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$10.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(10);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-6 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(6);
        campaignCoupon.setCpnNbr(191670);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$12.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(12);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-7 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(7);
        campaignCoupon.setCpnNbr(191671);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$14.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(14);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-8 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(8);
        campaignCoupon.setCpnNbr(191672);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$16.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(16);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


//pilot campaign Coupon-9 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(9);
        campaignCoupon.setCpnNbr(191673);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$18.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(18);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-10 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(10);
        campaignCoupon.setCpnNbr(191674);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$20.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(20);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-12 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(12);
        campaignCoupon.setCpnNbr(191675);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$24.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(24);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-13 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(13);
        campaignCoupon.setCpnNbr(192430);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-06-09"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$26.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(26);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-14 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(14);
        campaignCoupon.setCpnNbr(191676);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-06-09"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$28.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(28);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-15 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(15);
        campaignCoupon.setCpnNbr(191677);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$30.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(30);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);

//pilot campaign Coupon-26 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(26);
        campaignCoupon.setCpnNbr(191678);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
        campaignCoupon.setCpnDsc("$52.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(52);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Pharmacy and Health Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


//Default campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(59726);
        campaignRewardThreshold.setThrshldLimNbr(10);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("Q");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);

//Pilot campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(64355);
        campaignRewardThreshold.setThrshldLimNbr(4);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("Q");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);


        /*  I am a carepass member enrolled under monthly program where 10$ coupon hasn’t been redeemed and want to fetch $10 and 20% coupons and status as E and benefit eligibility date as 30 days and expire date as 30 days
         *
         */

        xtraCard.setXtraCardNbr(900058421);
        xtraCard.setCustId(80154);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058421);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80154);
        customer.setGndrCd("M");
        customer.setFirstName("Mary");
        customer.setLastName("Klein");
        customerDao.createCustomer(customer);

        customer.setBirthDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(10)));
        customer.setMiddleInitialTxt("M");
        customerDao.updateCustomer(customer);

        customerEmail.setCustId(80154);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("TEST.AUTO@GMAIL.COM");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80154);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(617);
        customerPhone.setPhonePfxNbr(259);
        customerPhone.setPhoneSfxNbr(2591);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80154);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("1532, CVS Drive");
        customerAddress.setAddr2Txt("APT NO 1532");
        customerAddress.setCityName("WOONSOCKET");
        customerAddress.setStCd("RI");
        customerAddress.setZipCd("02895");
        customerDao.createCustomerAddress(customerAddress);


        /* I am an XtraCare Customer and want to fetch xtra card child table information
         *
         */

        xtraCard.setXtraCardNbr(900058422);
        xtraCard.setCustId(80155);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058422);
        xtraCardDao.updateXtraCard(xtraCard);

        xtraCardChild.setXtraCardNbr(900058422);
        xtraCardChild.setBirthdayDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-910)));
        xtraCardDao.createXtraCardChild(xtraCardChild);


        /* "I am an XtraCare Customer and want to fetch xtra card analytic event table information
         *
         */
/*
        xtraCard.setXtraCardNbr(900058423);
        xtraCard.setCustId(80156);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058423);
        xtraCardDao.updateXtraCard(xtraCard);

        xtraCardAnalyticEvent.setXtraCardNbr(900058423);
        xtraCardAnalyticEvent.setAnalyticEventTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardAnalyticEvent.setCpnSeqNbr(null);
        xtraCardAnalyticEvent.setAnalyticEventTypeCd(1);
        xtraCardAnalyticEvent.setLoadTs(carePassDateUtil.carePassEnrollTswtz(0));
        xtraCardAnalyticEvent.setFirstUpdtSrcCd("042");
        xtraCardAnalyticEvent.setEventDataJson("{}");
        xtraCardAnalyticEvent.setEventMetaDataJson("{}");
        xtraCardDao.createXtraCardAnalyticEvent(xtraCardAnalyticEvent);
*/


        /* "I am an XtraCare Customer and want to fetch my preferences information with beautyClub enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058424);
        xtraCard.setCustId(80157);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058424);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80157);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with beautyNotes enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058425);
        xtraCard.setCustId(80158);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058425);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80158);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("35");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with diabeticClub  enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058426);
        xtraCard.setCustId(80159);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058426);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80159);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("27");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with paperlessCpns  enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058427);
        xtraCard.setCustId(80160);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058427);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80160);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("34");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with beautyClub  enrolled false
         *
         */

        xtraCard.setXtraCardNbr(900058428);
        xtraCard.setCustId(80161);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058428);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80161);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        /* "I am an XtraCare Customer and want to fetch my preferences information with beautyNotes enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058429);
        xtraCard.setCustId(80162);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058429);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80162);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("35");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with diabeticClub enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058430);
        xtraCard.setCustId(80163);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058430);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80163);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("27");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with paperlessCpns enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058431);
        xtraCard.setCustId(80164);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058431);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80164);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("34");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with phrEnroll enrolled N
         *
         */

        xtraCard.setXtraCardNbr(900058432);
        xtraCard.setCustId(80165);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058432);
        xtraCardDao.updateXtraCard(xtraCard);


        /* "I am an XtraCare Customer and want to fetch my preferences information with paperlessCpns enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058433);
        xtraCard.setCustId(80166);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058433);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80166);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("37");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058434);
        xtraCard.setCustId(80167);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058434);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80167);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("29");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEmail enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058435);
        xtraCard.setCustId(80168);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058435);
        xtraCardDao.updateXtraCard(xtraCard);

        customerEmail.setCustId(80168);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("TEST.AUTO@GMAIL.COM");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);


        /* "I am an XtraCare Customer and want to fetch my preferences information with sms enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058436);
        xtraCard.setCustId(80169);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058436);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80169);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("37");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058437);
        xtraCard.setCustId(80170);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058437);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80170);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("29");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058439);
        xtraCard.setCustId(80172);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058439);
        xtraCardDao.updateXtraCard(xtraCard);

        hrMemberSmry.setEphLinkId(90019811);
        hrMemberSmry.setXtraCardNbr(900058439);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(90019811);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(90019811);
        hrMemberEnroll.setXtraCardNbr(900058439);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(90019811);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(90019811);
        hrMemberHippa.setHippaStatusCd("E");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(90019811);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        //Default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(900058439);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058440);
        xtraCard.setCustId(80173);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058440);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80173);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("36");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058438);
        xtraCard.setCustId(80171);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058438);
        xtraCardDao.updateXtraCard(xtraCard);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058441);
        xtraCard.setCustId(80175);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058441);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80175);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("B");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("36");
        customerDao.createCustomerOpt(customerOpt);


        /* "I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
         *
         */

        xtraCard.setXtraCardNbr(900058442);
        xtraCard.setCustId(80176);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-2000)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058442);
        xtraCardDao.updateXtraCard(xtraCard);

        customerOpt.setCustId(80176);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("D");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("36");
        customerDao.createCustomerOpt(customerOpt);

        //     cacheRefreshUtil.refresCacheusingXtraParms();
        //     cacheRefreshUtil.refresCacheforCmpgnDefns();

    }





}