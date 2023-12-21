package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.RewardTransactionRequest;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@Data
public class BeautyClubTransactionHistoryService {

    private Response serviceResponse;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

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
    CampaignRewardThreshold campaignRewardThreshold;

    @Autowired
    CampaignActivePointBase campaignActivePointBase;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    XtraParms xtraParms;

    @Autowired
    XtraHotCard xtraHotCard;

    @Autowired
    XtraCardRewardBCTxnDtl xtraCardRewardBCTxnDtl;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    public void viewBeautyClubTransactionHistory(RewardTransactionRequest rewardTransactionRequest) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
        int offset = 0;
        int limit = 5;

        //TODO - We need a Utility Method to determine attributes
        if ("MOBILE".equalsIgnoreCase(rewardTransactionRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;

        } else {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        }
        if (rewardTransactionRequest.getOffset().equals(5)) {
            offset = 5;
        }
        requestSpecBuilder.setBaseUri(serviceConfig.getRewardHistoryUrl())
                .setBasePath("api/v1.1/{search_card_type},{search_card_nbr}/rewards/beautyClub/history")
                .addPathParam("search_card_type", rewardTransactionRequest.getSearchCardType())
                .addPathParam("search_card_nbr", rewardTransactionRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("offset", offset)
                .addQueryParam("limit", limit);

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).when().get();
    }

    /**
     * Create Test Data For BC Transaction History Scenario
     */
    public void createBeautyClubTransHistoryTestData() throws ParseException {

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
        String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
        String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");

        String todayTimeStamp = dateTime.toString("yyyy-MM-dd HH.MM.SS a");
        String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String tomorrowTimeStamp = dateTime.plusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String prev1yearTimeStamp = dateTime.minusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
        String future1yearTimeStamp = dateTime.plusYears(1).toString("yyyy-MM-dd HH.MM.SS a");

        campaign.setCmpgnId(40465);
        Integer rowexists = campaignDao.checkCampaign(campaign);
        if (rowexists == 1) {
            campaign.setCmpgnId(40465);
            campaign.setEndDt(simpleDateFormat.parse("2030-12-31"));
            campaignDao.updateCampaign(campaign);
        } else if (rowexists == 0) {
            campaign.setCmpgnId(40465);
            campaign.setCmpgnTypeCd("E");
            campaign.setCmpgnSubtypeCd("L");
            campaign.setCmpgnDsc("1/1/18 BEAUTY CLUB REWARDS");
            campaign.setRecptPrntInd("-1");
            campaign.setRecptPrntPriorityNbr(2);
            campaign.setRecptRxt("Beauty Club");
            campaign.setRecptScaleNbr(2);
            campaign.setRwrdRedirInd(" ");
            campaign.setStartDt(simpleDateFormat.parse("2021-01-01"));
            campaign.setEndDt(simpleDateFormat.parse("2030-12-31"));
            campaign.setLastUpdtTs(null);
            campaign.setIssueFreqTypeCd("D");
            campaign.setIssueFreqCnt(1);
            campaign.setFirstIssueDt(simpleDateFormat.parse(prev1yearDate));
            campaign.setLastIssueDt(simpleDateFormat.parse(future1yearDate));
            campaign.setPrevIssueDt(simpleDateFormat.parse(yestarday2Date));
            campaign.setNextIssueDt(simpleDateFormat.parse(yestardayDate));
            campaign.setDaysToPrintCnt(999);
            campaign.setDaysToRedeemCnt(14);
            campaign.setInHomeDt(simpleDateFormat.parse(prev1yearDate));
            campaign.setTotlaRwrdEarnAmt(195);
            campaign.setBonusSkuCalcDt(null);
            campaign.setCpnRedeemCalcDt(null);
            campaign.setCpnBaseDsc("$Beauty Club ExtraBucks Rewards");
            campaign.setParentCmpgnId(null);
            campaign.setCpnCatNbr(null);
            campaign.setCpnSegNbr(null);
            campaign.setCpnFndgCd("6");
            campaign.setBillingTypeCd(null);
            campaign.setIndivRwrdAmt(3);
            campaign.setCpnAutoGenInd("-1");
            campaign.setRwrdLastCalcDt(simpleDateFormat.parse(yestarday2Date));
            campaign.setCsrVisibleInd("-1");
            campaign.setCmpgnTermsTxt("Spend $30 on beauty products and earn $3 ExtraBucks Rewards");
            campaign.setWebDsc("$3 ExtraBucks Rewards when you spend $30 on beauty");
            campaign.setWebDispInd("-1");
            campaign.setPayVendorNbr(null);
            campaign.setCpnOltpHoldInd(null);
            campaign.setCpnPurgeCd(null);
            campaign.setDfltCpnTermscd(1);
            campaign.setCatMgrId("K");
            campaign.setVendorNbr(null);
            campaign.setMultiVendorInd("0");
            campaign.setCpnFixedDscInd("0");
            campaign.setCpnPrntStartDelayDayCnt(2);
            campaign.setCpnRedmStartDelayDayCnt(null);
            campaign.setCpnPriorityNbr(2);
            campaign.setCpnQualTxt("Congratulations! Here are you ExtraBucks Rewards for qualifying beauty purchases. Every $30 spent on beauty gets you $3 ExtraBucks Rewards!");
            campaign.setReqSkuList(null);
            campaign.setMaxVisitRwrdQty(null);
            campaign.setMaxRwrdQty(null);
            campaign.setRwrdRecalcInd(null);
            campaign.setCmpgnQualTxt("Spend $30 on Beauty, Get $3 EB");
            campaign.setTrgtPrntDestCd(null);
            campaign.setCpnMinPurchAmt(null);
            campaign.setLastFeedAccptDt(null);
            campaign.setAdvMaxRwrdQty(null);
            campaign.setPromoLinkNbr(null);
            campaign.setAmtTypeCd(null);
            campaign.setPctOffAmt(null);
            campaign.setFsaCpnInd(null);
            campaign.setPrtLabrlCpnInd(null);
            campaign.setDfltAlwaysInd(null);
            campaign.setDfltFreqDayCnt(null);
            campaign.setDfltFreqEmpDayCnt(null);
            campaign.setLoadableInd("-1");
            campaign.setCardTypeCd(null);
            campaign.setCpnRecptTxt("Beauty Club ExtraBucks Rewards");
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
            campaign.setXtraCardSegNbr(0);
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
            campaign.setAutoReissueInd("Y");
            campaign.setTrgtPrntRegCd(null);
            campaign.setFacebookDispInd(null);
            campaign.setInstantCmpgnEarnigInd("0");
            campaign.setPeOptimizeTypeCd(null);
            campaignDao.createCampaign(campaign);

        }


        campaignCoupon.setCmpgnId(40465);
        campaignCoupon.setCpnNbr(59045);
        Integer cpnrowexists = campaignDao.checkCampaignCoupon(campaignCoupon);
        if (cpnrowexists == 1) {
            campaignCoupon.setCmpgnId(40465);
            campaignCoupon.setCpnNbr(59045);
            campaignCoupon.setCpnDsc("$3.00 Beauty Club ExtraBucks Rewards_GMARTS");
            campaignCoupon.setCpnRecptTxt("Beauty Club ExtraBucks Rewards");
            campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
            campaignCoupon.setEndDt(simpleDateFormat.parse(future1yearDate));
            campaignDao.updateCampaignCoupon(campaignCoupon);
        } else if (cpnrowexists == 0) {
            campaignCoupon.setCmpgnId(40465);
            campaignCoupon.setRwrdQty(1);
            campaignCoupon.setCpnNbr(59045);
            campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
            campaignCoupon.setEndDt(simpleDateFormat.parse(future1yearDate));
            campaignCoupon.setCpnDsc("$3.00 Beauty Club ExtraBucks Rewards_GMARTS");
            campaignCoupon.setMaxRedeemAmt(3);
            campaignCoupon.setCpnTermsCd(1);
            campaignCoupon.setAmtTypeCd("D");
            campaignCoupon.setPctOffAmt(null);
            campaignCoupon.setLoadableInd("-1");
            campaignCoupon.setFndgCd("6");
            campaignCoupon.setBillingTypeCd(null);
            campaignCoupon.setCpnRecptTxt("Beauty Club ExtraBucks Rewards");
            campaignCoupon.setCpnValRqrdCd("Y");
            campaignCoupon.setAbsAmtInd("N");
            campaignCoupon.setItemLimitQty(null);
            campaignCoupon.setCpnFmtCd("2");
            campaignCoupon.setFreeItemInd(null);
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

        }


        /*  I enrolled in Beauty Club Program and made single transaction of $10 purchase of qualified Beauty Club items dated today-1 day
         *
         */
        xtraCard.setXtraCardNbr(98158347);
        xtraCard.setCustId(80080);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80080);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80080);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80080);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80080);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158347);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        /*  I enrolled in Beauty Club Program and made two transaction of $10 purchase each for qualified Beauty Club items dated today-1 day
         *
         */
        xtraCard.setXtraCardNbr(98158348);
        xtraCard.setCustId(80081);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80081);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80081);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80081);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80081);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158348);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158348);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        /*  I enrolled in Beauty Club Program and have made $5 progress is already available, made single $35 purchase of qualified Beauty Club items dated today–1 day
         *
         */
        xtraCard.setXtraCardNbr(98158349);
        xtraCard.setCustId(80082);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80082);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80082);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80082);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80082);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158349);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(35);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158349);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(5);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158349);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(59045);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /*  I enrolled in Beauty Club Program and made two separate transactions each $15 and $25 of qualified Beauty Club items dated today-1 day
         *
         */
        xtraCard.setXtraCardNbr(98158350);
        xtraCard.setCustId(80083);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80083);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80083);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80083);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80083);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158350);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(15);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158350);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158350);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(59045);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        /*  I enrolled in Beauty Club Program have $25 progress is already available and made 4 separate transactions each $5, $15, $25 & $15 of qualified Beauty Club items dated today-1 day
         *
         */
        xtraCard.setXtraCardNbr(98158351);
        xtraCard.setCustId(80084);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80084);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80084);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80084);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80084);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158351);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(5);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158351);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(15);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158351);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158351);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(15);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158351);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(100)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158351);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(100392);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(6);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /*  I enrolled in Beauty Club Program and made 1 single transaction of $100 of qualified Beauty Club items dated today-5 days
         *
         */
        xtraCard.setXtraCardNbr(98158352);
        xtraCard.setCustId(80085);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80085);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80085);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80085);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80085);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158352);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(100);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158352);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(59074);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(9);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /*  I enrolled in Beauty Club Program and made single $10 purchase of qualified Beauty Club items dated today-2 days and $40 purchase of qualified Beauty Club items dated today-30 days
         *
         */
        xtraCard.setXtraCardNbr(98158353);
        xtraCard.setCustId(80086);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80086);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80086);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80086);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80086);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158353);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158353);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(40);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158353);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(59045);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /*  I enrolled in Beauty Club Program and made two $10 purchases of qualified Beauty Club items dated today-3 days and today-200 days
         *
         */
        xtraCard.setXtraCardNbr(98158354);
        xtraCard.setCustId(80087);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80087);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80087);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80087);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80087);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158354);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158354);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(200)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /*  I enrolled in Beauty Club Program and made single $35 purchase of qualified Beauty Club items dated current date–367 days
         *
         */
        xtraCard.setXtraCardNbr(98158355);
        xtraCard.setCustId(80088);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80088);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80088);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80088);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80088);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158355);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(367)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(35);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        /*  I enrolled in Beauty Club Program and made two separate transactions each $1 and $2 of qualified Beauty Club items dated today-5 days
         *
         */
        xtraCard.setXtraCardNbr(98158356);
        xtraCard.setCustId(80089);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80089);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80089);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80089);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80089);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158356);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158356);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /* I enrolled in Beauty Club Program and made 4 separate transactions $5, $15, $25 & $15 of qualified Beauty Club items dated today-5 days, today-10 days, today-15 days and today-20 days
         *
         */
        xtraCard.setXtraCardNbr(98158357);
        xtraCard.setCustId(80090);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80090);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80090);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80090);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80090);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158357);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(5);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158357);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(15);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158357);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158357);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158357);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158357);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(15);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /*  I enrolled in Beauty Club Program and made 1 single transaction of $1 of qualified Beauty Club items dated today-90 days
         *
         */
        xtraCard.setXtraCardNbr(98158358);
        xtraCard.setCustId(80091);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80091);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80091);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80091);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80091);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158358);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(90)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        /*  I enrolled in Beauty Club Program had current progress $25, make purchase of $6 for qualified Beauty Club today–20 days
         *
         */
        xtraCard.setXtraCardNbr(98158359);
        xtraCard.setCustId(80092);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80092);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80092);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80092);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80092);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158359);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(6);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);

        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158359);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("RW");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        xtraCardRewardBCTxnDtl.setXtraCardNbr(98158359);
        xtraCardRewardBCTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(90)));
        xtraCardRewardBCTxnDtl.setCmpgnId(40465);
        xtraCardRewardBCTxnDtl.setCpnNbr(null);
        xtraCardRewardBCTxnDtl.setTxnTypCd("TR");
        xtraCardRewardBCTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardBCTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardBcTxnDtl(xtraCardRewardBCTxnDtl);


        /*  I enrolled in Beauty Club Program but has NOT made BC purchase this year
         *
         */
        xtraCard.setXtraCardNbr(98158360);
        xtraCard.setCustId(80093);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80093);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80093);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80093);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80093);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);



        /* I am an Hot card member and want to see  my redeem transaction history for witnin 14 days
         *
         */
        xtraHotCard.setXtraCardNbr(98158362);
        xtraHotCard.setAddedDt(simpleDateFormat.parse("2019-11-30"));
        xtraCardDao.createXtraHotCard(xtraHotCard);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Xtra_Parms
        DateTime dateTimep = new DateTime();
        String todayDateXp = dateTimep.toString("yyyyMMdd");
        Integer sec = dateTimep.getSecondOfMinute();
        Integer min = dateTimep.getMinuteOfHour();
        Integer hour = dateTimep.getHourOfDay();
        String paddedMin;
        String paddedHour;
        String paddedSec;
        if (sec < 10) {
            paddedSec = String.format("%02d", sec);
        } else {
            paddedSec = Integer.toString(sec);
        }
        if (min < 10) {
            paddedMin = String.format("%02d", min);
        } else {
            paddedMin = Integer.toString(min);
        }
        if (hour < 10) {
            paddedHour = String.format("%02d", hour);
        } else {
            paddedHour = Integer.toString(hour);
        }
        String xpTime = todayDateXp + " " + paddedHour + ":" + paddedMin + ":" + paddedSec;
        xtraParms.setParmName("LST_CLONE_DT");
        xtraParms.setParmValueTxt(xpTime);
        xtraCardDao.updateXtraParms(xtraParms);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Delete Test Data for quarterly Extra Bucks Scenario
     */
    public void deleteBeautyClubTransHistoryTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158347, 98158348, 98158349, 98158350, 98158351, 98158352, 98158353, 98158354, 98158355, 98158356, 98158357, 98158358, 98158359, 98158360, 98158361, 98158362);
        List<Integer> custIdList = Arrays.asList(80080, 80081, 80082, 80083, 80084, 80085, 80086, 80087, 80088, 80089, 80090, 80091, 80092, 80093, 80094, 80095);
        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}
