package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.CustomerOpt;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.CampaignRewardThreshold;
import com.cvs.crm.model.data.CampaignActivePointBase;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.repository.CampaignDao;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.cvs.crm.util.CacheRefreshUtil;
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
public class QuarterlyExtraBucksService {

    private Response serviceResponse;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

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
    XtraParms xtraParms;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    public void viewQuarterlyExtraBucks(DashBoardRequest dashBoardRequest) {

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
     * Create Test Data For quarterly Extra Bucks Scenario
     */
    public void createQuarterlyExtraBucksTestData() throws ParseException, InterruptedException {

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

        /*  I am CVS EC card member and made $15 purchase in between Jun 16th to Sep 15th
         *
         */
        xtraCard.setXtraCardNbr(98158300);
        xtraCard.setCustId(80032);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80032);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80032);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80032);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80032);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);



        /*  I am CVS EC card member and made $60 purchase in between Jun 16th to Sep 15th
         *
         */
        xtraCard.setXtraCardNbr(98158301);
        xtraCard.setCustId(80033);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80033);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80033);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80033);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80033);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*  I am CVS EC card member and made $0 purchase in between Jun 16th to Sep 15th
         *
         */
        xtraCard.setXtraCardNbr(98158302);
        xtraCard.setCustId(80034);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80034);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80034);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80034);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80034);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);



        /*  I am CVS EC card member and made $50 purchase in between Jun 16th to Sep 15th
         *
         */
        xtraCard.setXtraCardNbr(98158303);
        xtraCard.setCustId(80035);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80035);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80035);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80035);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80035);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*  I am CVS EC card member and made $25 purchase in between Sep 16th to Sep 30th
         *
         */
        xtraCard.setXtraCardNbr(98158304);
        xtraCard.setCustId(80036);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80036);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80036);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80036);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80036);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*  I am CVS EC card member and made $150 purchase in between Jun 16th to Sep 15th
         *
         */
        xtraCard.setXtraCardNbr(98158305);
        xtraCard.setCustId(80037);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80037);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80037);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80037);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80037);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);



        /*  I am CVS EC card member and made $150 purchase in between Jan 1st to Sep 15th
         *
         */
        xtraCard.setXtraCardNbr(98158306);
        xtraCard.setCustId(80038);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80038);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80038);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80038);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80038);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*  I am CVS EC card member and made $15 purchase in current quarter ending today
         *
         */
        xtraCard.setXtraCardNbr(98158307);
        xtraCard.setCustId(80040);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80040);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80040);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80040);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80040);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*  I am CVS EC card member and made $15 purchase in current quarter starting today
         *
         */
        xtraCard.setXtraCardNbr(98158308);
        xtraCard.setCustId(80041);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80041);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80041);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80041);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80041);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Campaign

        campaign.setCmpgnId(58909);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        campaign.setCmpgnDsc("2020 Q4 FS $");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt("This Quarter Spending for 2021");
        campaign.setRecptScaleNbr(2);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(dateUtil.campaignStartDateToday()));
        campaign.setEndDt(simpleDateFormat.parse(dateUtil.campaignEndDateToday()));
        campaign.setLastUpdtTs(null);
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse(dateUtil.firstIssueDateExisting()));
        campaign.setLastIssueDt(simpleDateFormat.parse(dateUtil.lastIssueDateExisting()));
        campaign.setPrevIssueDt(simpleDateFormat.parse(dateUtil.previousIssueDateExisting()));
        campaign.setNextIssueDt(simpleDateFormat.parse(dateUtil.nextIssueDateExisting()));
        campaign.setDaysToPrintCnt(45);
        campaign.setDaysToRedeemCnt(45);
        campaign.setInHomeDt(simpleDateFormat.parse(dateUtil.inHomeDtDate()));
        campaign.setTotlaRwrdEarnAmt(50447943);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(null);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd("STR");
        campaign.setIndivRwrdAmt(1);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse(future1yearDate));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt("2% of your Fall 2020 Spend will be issued on January 1st.  Rewards are redeemable in-store and online.");
        campaign.setWebDsc("This Quarter Spending for 2021");
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd(null);
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(1);
        campaign.setCatMgrId("S");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(0);
        campaign.setCpnQualTxt("Here are your ExtraBucks Rewards for your Fall 2021 Spending");
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt("2% of your Fall 2021 Spend");
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
        campaign.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd("N");
        campaign.setMktgPrgCd("Q");
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


        campaign.setCmpgnId(51682);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        campaign.setCmpgnDsc("2021 Q1 FS $");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt("This Quarter Spending for 2021");
        campaign.setRecptScaleNbr(2);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(dateUtil.campaignStartDate(89)));
        campaign.setEndDt(simpleDateFormat.parse(dateUtil.campaignEndDate(0)));
        campaign.setLastUpdtTs(null);
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse(dateUtil.firstIssueDateExisting()));
        campaign.setLastIssueDt(simpleDateFormat.parse(dateUtil.lastIssueDateExisting()));
        campaign.setPrevIssueDt(simpleDateFormat.parse(dateUtil.previousIssueDateExisting()));
        campaign.setNextIssueDt(simpleDateFormat.parse(dateUtil.nextIssueDateExisting()));
        campaign.setDaysToPrintCnt(45);
        campaign.setDaysToRedeemCnt(45);
        campaign.setInHomeDt(simpleDateFormat.parse(dateUtil.inHomeDtDate()));
        campaign.setTotlaRwrdEarnAmt(53827882);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(null);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd("STR");
        campaign.setIndivRwrdAmt(1);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse(future1yearDate));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt("2% of your Winter 2021 Spend will be issued on April 1st.  Rewards are redeemable in-store and online.");
        campaign.setWebDsc("This Quarter Spending for 2021");
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd(null);
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(1);
        campaign.setCatMgrId("S");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(0);
        campaign.setCpnQualTxt("Here are your ExtraBucks Rewards for your Winter 2021 Spending");
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt("2% of your Winter 2021 Spend");
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
        campaign.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd(null);
        campaign.setMktgPrgCd("Q");
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


        campaign.setCmpgnId(54001);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        campaign.setCmpgnDsc(dateUtil.cmpgnDscYearExisting());
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt(dateUtil.recptRxtYearExisting());
        campaign.setRecptScaleNbr(2);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(dateUtil.campaignStartDateExisting(91)));
        campaign.setEndDt(simpleDateFormat.parse(dateUtil.campaignEndDateExisting()));
        campaign.setLastUpdtTs(null);
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse(dateUtil.firstIssueDateExisting()));
        campaign.setLastIssueDt(simpleDateFormat.parse(dateUtil.lastIssueDateExisting()));
        campaign.setPrevIssueDt(simpleDateFormat.parse(dateUtil.previousIssueDateExisting()));
        campaign.setNextIssueDt(simpleDateFormat.parse(dateUtil.nextIssueDateExisting()));
        campaign.setDaysToPrintCnt(45);
        campaign.setDaysToRedeemCnt(45);
        campaign.setInHomeDt(simpleDateFormat.parse(dateUtil.inHomeDtDate()));
        campaign.setTotlaRwrdEarnAmt(53059251);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(null);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd("STR");
        campaign.setIndivRwrdAmt(1);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse(dateUtil.inHomeDtDateExisting()));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt(dateUtil.cmpgnTermsTxtYearExisting());
        campaign.setWebDsc(dateUtil.webDescriptionYearExisting());
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd(null);
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(1);
        campaign.setCatMgrId("S");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(0);
        campaign.setCpnQualTxt(dateUtil.cpnQualTxtYearExisting());
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt(dateUtil.cmpgnQualTxtYearExisting());
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
        campaign.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd(null);
        campaign.setMktgPrgCd("Q");
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


        campaign.setCmpgnId(56264);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        campaign.setCmpgnDsc(dateUtil.cmpgnDscYear());
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt(dateUtil.recptRxtYear());
        campaign.setRecptScaleNbr(2);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(dateUtil.campaignStartDate(88)));
        campaign.setEndDt(simpleDateFormat.parse(dateUtil.campaignEndDate(1)));
        campaign.setLastUpdtTs(null);
        campaign.setIssueFreqTypeCd("D");
        campaign.setIssueFreqCnt(1);
        campaign.setFirstIssueDt(simpleDateFormat.parse(dateUtil.firstIssueDate()));
        campaign.setLastIssueDt(simpleDateFormat.parse(dateUtil.lastIssueDate()));
        campaign.setPrevIssueDt(simpleDateFormat.parse(dateUtil.previousIssueDate()));
        campaign.setNextIssueDt(simpleDateFormat.parse(dateUtil.nextIssueDate()));
        campaign.setDaysToPrintCnt(45);
        campaign.setDaysToRedeemCnt(45);
        campaign.setInHomeDt(simpleDateFormat.parse(dateUtil.inHomeDtDate()));
        campaign.setTotlaRwrdEarnAmt(48528575);
        campaign.setBonusSkuCalcDt(null);
        campaign.setCpnRedeemCalcDt(null);
        campaign.setCpnBaseDsc("$ExtraBucks Rewards");
        campaign.setParentCmpgnId(null);
        campaign.setCpnCatNbr(null);
        campaign.setCpnSegNbr(null);
        campaign.setCpnFndgCd("6");
        campaign.setBillingTypeCd("STR");
        campaign.setIndivRwrdAmt(1);
        campaign.setCpnAutoGenInd("-1");
        campaign.setRwrdLastCalcDt(simpleDateFormat.parse(dateUtil.rewLastCalcDtDate()));
        campaign.setCsrVisibleInd("-1");
        campaign.setCmpgnTermsTxt(dateUtil.cmpgnTermsTxtYear());
        campaign.setWebDsc(dateUtil.webDescriptionYear());
        campaign.setWebDispInd("-1");
        campaign.setPayVendorNbr(null);
        campaign.setCpnOltpHoldInd(null);
        campaign.setCpnPurgeCd(null);
        campaign.setDfltCpnTermscd(1);
        campaign.setCatMgrId("S");
        campaign.setVendorNbr(null);
        campaign.setMultiVendorInd("0");
        campaign.setCpnFixedDscInd("0");
        campaign.setCpnPrntStartDelayDayCnt(0);
        campaign.setCpnRedmStartDelayDayCnt(null);
        campaign.setCpnPriorityNbr(0);
        campaign.setCpnQualTxt(dateUtil.cpnQualTxtYear());
        campaign.setReqSkuList(null);
        campaign.setMaxVisitRwrdQty(null);
        campaign.setMaxRwrdQty(null);
        campaign.setRwrdRecalcInd(null);
        campaign.setCmpgnQualTxt(dateUtil.cmpgnQualTxtYear());
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
        campaign.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaign.setCpnValRqrdCd("N");
        campaign.setAbsAmtInd("N");
        campaign.setItemLimitQty(null);
        campaign.setCpnFmtCd("2");
        campaign.setDfltCpnCatJson(null);
        campaign.setFreeItemInd("N");
        campaign.setMktgPrgCd("Q");
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


        // campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(51682);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(112309);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-03-26"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-01-10"));
        campaignCoupon.setCpnDsc("$1.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(1);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(51682);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(112310);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-03-26"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-01-10"));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(2);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(51682);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(112311);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-03-26"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-01-10"));
        campaignCoupon.setCpnDsc("$3.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(3);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(51682);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(112312);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-03-26"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-01-10"));
        campaignCoupon.setCpnDsc("$4.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(4);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(58909);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(166621);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-12-18"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-10-11"));
        campaignCoupon.setCpnDsc("$1.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(1);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
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
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(58909);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(166622);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-12-18"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-10-11"));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(2);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(58909);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(166623);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-12-18"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-10-11"));
        campaignCoupon.setCpnDsc("$3.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(3);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(58909);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(166624);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-12-18"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2021-10-11"));
        campaignCoupon.setCpnDsc("$4.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(4);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(54001);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(124472);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDateExisting()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDateExisting()));
        campaignCoupon.setCpnDsc("$1.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(1);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(54001);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(124473);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDateExisting()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDateExisting()));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(2);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(54001);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(124474);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDateExisting()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDateExisting()));
        campaignCoupon.setCpnDsc("$3.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(3);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(54001);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(124475);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDateExisting()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDateExisting()));
        campaignCoupon.setCpnDsc("$4.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(4);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(56264);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(145180);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDate()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDate()));
        campaignCoupon.setCpnDsc("$1.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(1);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
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
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(56264);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(145181);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDate()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDate()));
        campaignCoupon.setCpnDsc("$2.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(2);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(56264);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(145182);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDate()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDate()));
        campaignCoupon.setCpnDsc("$3.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(3);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        // campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(56264);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(145183);
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.campaignCouponStartDate()));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.campaignCouponEndDate()));
        campaignCoupon.setCpnDsc("$4.00 ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(4);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd("STR");
        campaignCoupon.setCpnRecptTxt("Quarterly ExtraBucks Rewards");
        campaignCoupon.setCpnValRqrdCd(" ");
        campaignCoupon.setAbsAmtInd(" ");
        campaignCoupon.setItemLimitQty(null);
        campaignCoupon.setCpnFmtCd("2");
        campaignCoupon.setFreeItemInd(null);
        campaignCoupon.setImageUrlTxt(" ");
        campaignCoupon.setLastUpdtTs(null);
        campaignCoupon.setCpnCatListXml(" ");
        campaignCoupon.setCpnCatListJson(" ");
        campaignCoupon.setCpnOltpHoldInd(" ");
        campaignCoupon.setCardValCd(null);
        campaignCoupon.setCatMgrId("S");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        //Campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(51682);
        campaignRewardThreshold.setThrshldLimNbr(50);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("D");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);

        //Campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(54001);
        campaignRewardThreshold.setThrshldLimNbr(50);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("D");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);

        //Campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(56264);
        campaignRewardThreshold.setThrshldLimNbr(50);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("D");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);

        //Campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(58909);
        campaignRewardThreshold.setThrshldLimNbr(50);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("D");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);


        /*
         * I am CVS EC card member and made $15 purchase in between Jun 16th to Sep 15th
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158300);
        campaignActivePointBase.setCmpgnId(56264);
        campaignActivePointBase.setPtsQty(15.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I am CVS EC card member and made $60 purchase in between Jun 16th to Sep 15th
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158301);
        campaignActivePointBase.setCmpgnId(56264);
        campaignActivePointBase.setPtsQty(60.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I am CVS EC card member and made $0 purchase in between Jun 16th to Sep 15th
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158302);
        campaignActivePointBase.setCmpgnId(56264);
        campaignActivePointBase.setPtsQty(0.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I am CVS EC card member and made $50 purchase in between Jun 16th to Sep 15th
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158303);
        campaignActivePointBase.setCmpgnId(56264);
        campaignActivePointBase.setPtsQty(50.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         *I am CVS EC card member and made $25 purchase in in-progress and existing campaign
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158304);
        campaignActivePointBase.setCmpgnId(56264);
        campaignActivePointBase.setPtsQty(20.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        //Campaign Active Point Base for Existing Campaign(which just ended)
        campaignActivePointBase.setXtraCardNbr(98158304);
        campaignActivePointBase.setCmpgnId(54001);
        campaignActivePointBase.setPtsQty(5.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I am CVS EC card member and made $150 purchase in between Jun 16th to Sep 15th
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158305);
        campaignActivePointBase.setCmpgnId(56264);
        campaignActivePointBase.setPtsQty(150.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I am CVS EC card member and made $150 purchase in between Jan 1st to Sep 15th
         */
        //Campaign Active Point Base for previous quarter campaign
        campaignActivePointBase.setXtraCardNbr(98158306);
        campaignActivePointBase.setCmpgnId(54001);
        campaignActivePointBase.setPtsQty(150.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        //Campaign Active Point Base for current quarter campaign
        campaignActivePointBase.setXtraCardNbr(98158306);
        campaignActivePointBase.setCmpgnId(56264);
        campaignActivePointBase.setPtsQty(0.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         *I am CVS EC card member and made $15 purchase in current quarter ending today
         */
        //Campaign Active Point Base for previous quarter campaign
        campaignActivePointBase.setXtraCardNbr(98158307);
        campaignActivePointBase.setCmpgnId(51682);
        campaignActivePointBase.setPtsQty(15.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         *I am CVS EC card member and made $15 purchase in current quarter starting today
         */
        //Campaign Active Point Base for previous quarter campaign
        campaignActivePointBase.setXtraCardNbr(98158308);
        campaignActivePointBase.setCmpgnId(58909);
        campaignActivePointBase.setPtsQty(15.00);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        try {
            Thread.sleep(60000);
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
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cacheRefreshUtil.refresCacheusingXtraParms();
        cacheRefreshUtil.refresCacheforCmpgnDefns();
        cacheRefreshUtil.refresCacheforDashboardApi();
    }


    /**
     * Delete Test Data for quarterly Extra Bucks Scenario
     */
    public void deleteQuarterlyExtraBucksTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158300, 98158301, 98158302, 98158303, 98158304, 98158305, 98158306, 98158307, 98158308);
        List<Integer> custIdList = Arrays.asList(80032, 80033, 80034, 80035, 80036, 80037, 80038, 80039, 80040, 80041);
        List<Integer> cmpgnIdList = Arrays.asList(59726, 59727, 64355, 51682, 54001, 56264, 58909);
        campaignDao.deleteCampaignRecords(cmpgnIdList, xtraCardNbrList);
        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}


