package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.util.CacheRefreshUtil;
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
import lombok.Data;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Data
public class BeautyClubService {

    private Response serviceResponse;

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

    public void viewBeautyClub(DashBoardRequest dashBoardRequest) {

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
     * Create Test Data For Beauty Club Scenario
     *
     * @throws InterruptedException
     */
    public void createBeautyClubTestData() throws ParseException, InterruptedException {

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

        /*  I am active Beauty Club member
         *
         */
        xtraCard.setXtraCardNbr(98158292);
        xtraCard.setCustId(80024);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80024);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80024);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80024);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80024);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80024);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        /*  I have enrolled in Beauty club program but not made any purchased related to beauty items
         *
         */
        xtraCard.setXtraCardNbr(98158293);
        xtraCard.setCustId(80025);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80025);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80025);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80025);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80025);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80025);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        /*  I have enrolled in Beauty club program and spend on beauty items
         *
         */
        xtraCard.setXtraCardNbr(98158294);
        xtraCard.setCustId(80026);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80026);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80026);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80026);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80026);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80026);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        /*  I am in Beauty Club program since last year
         *
         */
        xtraCard.setXtraCardNbr(98158295);
        xtraCard.setCustId(80027);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80027);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80027);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80027);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80027);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80027);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        /*  I am in Beauty Club program from Feb 2020 and spent more than $65
         *
         */
        xtraCard.setXtraCardNbr(98158296);
        xtraCard.setCustId(80028);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80028);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80028);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80028);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80028);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80028);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        /*  I am in Beauty Club program from May 2020 and spent less than $30
         *
         */
        xtraCard.setXtraCardNbr(98158297);
        xtraCard.setCustId(80029);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80029);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80029);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80029);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80029);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80029);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        /*  I am Not enrolled in Beauty Club program now
         *
         */
        xtraCard.setXtraCardNbr(98158298);
        xtraCard.setCustId(80030);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80030);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80030);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80030);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80030);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80030);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);



        /*  I have never enrolled in Beauty Club program
         *
         */
        xtraCard.setXtraCardNbr(98158299);
        xtraCard.setCustId(80031);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80031);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80031);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80031);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80031);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Campaign
       /* campaign.setCmpgnId(59727);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("L");
        campaign.setCmpgnDsc("1/1/18 BEAUTY CLUB REWARDS");
        campaign.setRecptPrntInd("0");
        campaign.setRecptPrntPriorityNbr(2);
        campaign.setRecptRxt("Beauty Club");
        campaign.setRecptScaleNbr(2);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse("2021-01-09"));
        campaign.setEndDt(simpleDateFormat.parse("2021-12-31"));
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
        campaign.setTotlaRwrdEarnAmt(5);
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
        campaign.setCpnPrntStartDelayDayCnt(0);
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
        campaign.setAmtTypeCd("D");
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
        campaign.setAutoReissueInd(null);
        campaign.setTrgtPrntRegCd(null);
        campaign.setFacebookDispInd(null);
        campaign.setInstantCmpgnEarnigInd("0");
        campaign.setPeOptimizeTypeCd(null);
        campaignDao.createCampaign(campaign);
*/
        // campaign Coupon-1 qty
        /*
        campaignCoupon.setCmpgnId(59727);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(1692320);
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
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        //Campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(59727);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(1692321);
        campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
        campaignCoupon.setEndDt(simpleDateFormat.parse(future1yearDate));
        campaignCoupon.setCpnDsc("$9.00 Beauty Club ExtraBucks Rewards");
        campaignCoupon.setMaxRedeemAmt(9);
        campaignCoupon.setCpnTermsCd(1);
        campaignCoupon.setAmtTypeCd("D");
        campaignCoupon.setPctOffAmt(null);
        campaignCoupon.setLoadableInd("-1");
        campaignCoupon.setFndgCd("6");
        campaignCoupon.setBillingTypeCd(null);
        campaignCoupon.setCpnRecptTxt("Beauty Club ExtraBucks Rewards");
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
        campaignCoupon.setCatMgrId("K");
        campaignCoupon.setMaxIssueCnt(null);
        campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
        campaignCoupon.setLastUpdtById(" ");
        campaignCoupon.setCpnPrntSuppressInd("-1");
        campaignCoupon.setDisclaimerTxt(" ");
        campaignDao.createCampaignCoupon(campaignCoupon);


        //Campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(59727);
        campaignRewardThreshold.setThrshldLimNbr(30);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("D");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);
/*
        /*
         * I am active Beauty Club member
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158292);
        campaignActivePointBase.setCmpgnId(59727);
        campaignActivePointBase.setPtsQty(4.57);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         * I have enrolled in Beauty club program but not made any purchased related to beauty items
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158293);
        campaignActivePointBase.setCmpgnId(59727);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I have enrolled in Beauty club program and spend on beauty items
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158294);
        campaignActivePointBase.setCmpgnId(59727);
        campaignActivePointBase.setPtsQty(24.57);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I am in Beauty Club program since last year
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158295);
        campaignActivePointBase.setCmpgnId(59727);
        campaignActivePointBase.setPtsQty(14.97);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         *I am in Beauty Club program from Feb 2020 and spent more than $65
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158296);
        campaignActivePointBase.setCmpgnId(59727);
        campaignActivePointBase.setPtsQty(20.01);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I am in Beauty Club program from May 2020 and spent less than $30
         */
        //Campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158297);
        campaignActivePointBase.setCmpgnId(59727);
        campaignActivePointBase.setPtsQty(0.50);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*  As a BeautyClub Program member I spend on Beauty products and started earning for both free item and 10% Coupons when both Campaigns are Active
         *
         */
        xtraCard.setXtraCardNbr(900058601);
        xtraCard.setCustId(80601);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80601);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80601);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80601);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80601);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80601);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        campaign.setCmpgnTypeCd("F");
        campaign.setCmpgnSubtypeCd("L");
        campaign.setMktgPrgCd("B");
        Integer freeItemCampaignCount = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign = null;
        if (freeItemCampaignCount == 1) {
            freeItemCampaign = campaignDao.selectBCCampaign(campaign);
        }

        campaignActivePointBase.setXtraCardNbr(900058601);
        campaignActivePointBase.setCmpgnId(freeItemCampaign);
        campaignActivePointBase.setPtsQty(4.57);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("L");
        campaign.setCmpgnSubtypeCd("P");
        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount = campaignDao.checkBCCampaign(campaign);
        Integer percentCampaign = null;
        if (percentCampaignCount == 1) {
            percentCampaign = campaignDao.selectBCCampaign(campaign);
        }

        campaignActivePointBase.setXtraCardNbr(900058601);
        campaignActivePointBase.setCmpgnId(percentCampaign);
        campaignActivePointBase.setPtsQty(80.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        cacheRefreshUtil.refresCacheusingXtraParms();
        cacheRefreshUtil.refresCacheforCmpgnDefns();
    }


    /**
     * Delete Test Data for Beauty club Scenario
     */
    public void deleteBeautyClubTestData() {
	  /*
	    Purge All Test Cards
	     */
      //  List<Integer> xtraCardNbrList = Arrays.asList(98158260, 98158261, 98158262, 98158263, 98158264, 98158265, 98158266, 98158267, 98158268, 98158269, 98158270, 98158271, 98158272, 98158273, 98158274, 98158275, 98158276, 98158277, 98158278, 98158279, 98158280, 98158281, 98158282, 98158283, 98158284, 98158285, 98158286, 98158287, 98158288, 98158289, 98158290, 98158291, 98158292, 98158293, 98158294, 98158295, 98158296, 98158297, 98158298, 98158299, 900058601, 900058602, 900058603, 900058604, 900058605, 900058606, 900058607, 900058608, 900058609, 900058610, 900058611, 900058612, 900058613, 900058614, 900058615, 900058616, 900058617, 900058618, 900058619, 900058620, 900058621, 900058622, 900058623, 900058624, 900058625, 900058626, 900058627, 900058628, 900058629, 900058630, 900058631, 900058632, 900058633, 900058634, 900058635, 900058636, 900058637, 900058638, 900058639, 900058640, 900058641, 900058642, 900058643, 900058644, 900058645, 900058646, 900058647, 900058648, 900058649, 900058650, 900058651, 900058652);
      //  List<Integer> custIdList = Arrays.asList(80000, 80001, 80002, 80003, 80004, 80005, 80006, 80007, 80008, 80009, 80010, 80011, 80012, 80013, 80014, 80015, 80016, 80017, 80018, 80019, 80020, 80021, 80022, 80023, 80024, 80025, 80026, 80027, 80028, 80029, 80030, 80031, 80601);
        //   List<Integer> cmpgnIdList = Arrays.asList(59726, 59727, 64355);
        //   campaignDao.deleteCampaignRecords(cmpgnIdList, xtraCardNbrList);
        //   customerDao.deleteCustomers(custIdList);
        //   xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}
