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
import com.cvs.crm.model.data.XtraHotCard;
import com.cvs.crm.model.data.XtraCardSegment;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.CampaignRewardThreshold;
import com.cvs.crm.model.data.CampaignActivePointBase;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.util.DateUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Data
public class BeautyClubRelaunchService {

    private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    DateUtil dateUtil;

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
    XtraHotCard xtraHotCard;

    @Autowired
    XtraCardSegment xtraCardSegment;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

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
    public void createBeautyClubTestData() throws ParseException, InterruptedException, IncorrectResultSizeDataAccessException, SQLException {
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
        String lastWeekTimeStamp = dateTime.minusDays(7).toString("yyyy-MM-dd HH.MM.SS a");
        String tomorrowTimeStamp = dateTime.plusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String nextWeekTimeStamp = dateTime.plusDays(7).toString("yyyy-MM-dd HH.MM.SS a");
        String next2WeekTimeStamp = dateTime.plusDays(14).toString("yyyy-MM-dd HH.MM.SS a");
        String prev1yearTimeStamp = dateTime.minusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
        String future1yearTimeStamp = dateTime.plusYears(1).toString("yyyy-MM-dd HH.MM.SS a");

        Long freeCpnSeqNbr, nextFreeCpnSeqNbr, percentCpnSeqNbr, nextPercentCpnSeqNbr;
        Integer freeSeg, percentSeg;


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

        Integer freeItemCampaignCount1 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign1 = null;
        if (freeItemCampaignCount1 == 1) {
            freeItemCampaign1 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign1);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058601);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058601);
            campaignActivePointBase.setCmpgnId(freeItemCampaign1);
            campaignActivePointBase.setPtsQty(15.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount1 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount1);
        } else if (freeItemCampaignCount1 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount1);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount1 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign1 = null;
        if (percentCampaignCount1 == 1) {
            percentCampaign1 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign1);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058601);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058601);
            campaignActivePointBase.setCmpgnId(percentCampaign1);
            campaignActivePointBase.setPtsQty(80.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign1);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr1 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr1 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr1 = percentCpnSeqNbr1 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058601);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign1);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr1);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr1);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount1 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount1);
        } else if (percentCampaignCount1 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount1);
        } else {
            throw new SQLException();
        }

        /*  As a BeautyClub Program member I spent on Beauty products only for Free item Coupon when both Free item and 10% Campaigns are Active
         *
         */
        xtraCard.setXtraCardNbr(900058602);
        xtraCard.setCustId(80602);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80602);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80602);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80602);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80602);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80602);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount2 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign2 = null;
        if (freeItemCampaignCount2 == 1) {
            freeItemCampaign2 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign2);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058602);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058602);
            campaignActivePointBase.setCmpgnId(freeItemCampaign2);
            campaignActivePointBase.setPtsQty(25.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign2);
            campaignCoupon.setRwrdQty(1);
            Integer CpnNbr2 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr2 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr2 = freeCpnSeqNbr2 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058602);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign2);
            xtraCardActiveCoupon.setCpnNbr(CpnNbr2);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr2);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount2 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount2);
        } else if (freeItemCampaignCount2 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount2);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount2 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign2 = null;
        if (percentCampaignCount2 == 1) {
            percentCampaign2 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign2);
            percentSeg = campaignDao.selectSegBCCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058602);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount2 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount2);
        } else if (percentCampaignCount2 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount2);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spent on Beauty products only for 10% campaign when both Free item and 10% Campaigns are Active
         *
         */
        xtraCard.setXtraCardNbr(900058603);
        xtraCard.setCustId(80603);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80603);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80603);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80603);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80603);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80603);
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
        Integer freeItemCampaignCount3 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign3 = null;
        if (freeItemCampaignCount3 == 1) {
            freeItemCampaign3 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign3);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058603);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount3 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount3);
        } else if (freeItemCampaignCount3 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount3);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount3 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign3 = null;
        if (percentCampaignCount3 == 1) {
            percentCampaign3 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign3);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058603);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058603);
            campaignActivePointBase.setCmpgnId(percentCampaign3);
            campaignActivePointBase.setPtsQty(120.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign3);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr3 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr3 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr3 = percentCpnSeqNbr3 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058603);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign3);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr3);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr3);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount3 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount3);
        } else if (percentCampaignCount3 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount3);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and reached limit for only Free Item Coupon and want to see only Free Item coupon with CpnNbr, Loadable as Y and pts
         *
         */
        xtraCard.setXtraCardNbr(900058604);
        xtraCard.setCustId(80604);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80604);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80604);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80604);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80604);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80604);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount4 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign4 = null;
        if (freeItemCampaignCount4 == 1) {
            freeItemCampaign4 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign4);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058604);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058604);
            campaignActivePointBase.setCmpgnId(freeItemCampaign4);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign4);
            campaignCoupon.setRwrdQty(1);
            Integer CpnNbr4 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr4 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr4 = freeCpnSeqNbr4 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058604);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign4);
            xtraCardActiveCoupon.setCpnNbr(CpnNbr4);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr4);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(simpleDateFormat.parse(todayTimeStamp));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount4 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount4);
        } else if (freeItemCampaignCount4 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount4);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount4 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign4 = null;
        if (percentCampaignCount4 == 1) {
            percentCampaign4 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign4);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058604);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount4 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount4);
        } else if (percentCampaignCount4 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount4);
        } else {
            throw new SQLException();
        }



        /*  As a BeautyClub Program member I spend on Beauty products and reached limit for both Free Item and 10% coupon and want to see Free Item and 10% coupons with CpnNbr, Loadable as Y and pts
         *
         */
        xtraCard.setXtraCardNbr(900058605);
        xtraCard.setCustId(80605);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80605);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80605);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80605);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80605);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80605);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount5 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign5 = null;
        if (freeItemCampaignCount5 == 1) {
            freeItemCampaign5 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign5);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058605);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058605);
            campaignActivePointBase.setCmpgnId(freeItemCampaign5);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign5);
            campaignCoupon.setRwrdQty(1);
            Integer CpnNbr5 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr5 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr5 = freeCpnSeqNbr5 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058605);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign5);
            xtraCardActiveCoupon.setCpnNbr(CpnNbr5);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr5);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(simpleDateFormat.parse(todayTimeStamp));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount5 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount5);
        } else if (freeItemCampaignCount5 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount5);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount5 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign5 = null;
        if (percentCampaignCount5 == 1) {
            percentCampaign5 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign5);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058605);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058605);
            campaignActivePointBase.setCmpgnId(percentCampaign5);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign5);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr5 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr5 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr5 = percentCpnSeqNbr5 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058605);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign5);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr5);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr5);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(simpleDateFormat.parse(todayTimeStamp));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount5 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount5);
        } else if (percentCampaignCount5 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount5);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and started earning for both Free Item and 10% coupon and want to see Free Item and 10% coupons with CpnNbr and pts
         *
         */
        xtraCard.setXtraCardNbr(900058606);
        xtraCard.setCustId(80606);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80606);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80606);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80606);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80606);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80606);
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
        Integer freeItemCampaignCount6 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign6 = null;
        if (freeItemCampaignCount6 == 1) {
            freeItemCampaign6 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign6);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058606);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058606);
            campaignActivePointBase.setCmpgnId(freeItemCampaign6);
            campaignActivePointBase.setPtsQty(20.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount6 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount6);
        } else if (freeItemCampaignCount6 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount6);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount6 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign6 = null;
        if (percentCampaignCount6 == 1) {
            percentCampaign6 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign6);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058606);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058606);
            campaignActivePointBase.setCmpgnId(percentCampaign6);
            campaignActivePointBase.setPtsQty(100.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign6);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr6 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr6 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr6 = percentCpnSeqNbr6 + 2;
            xtraCardActiveCoupon.setXtraCardNbr(900058606);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign6);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr6);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr6);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount6 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount6);
        } else if (percentCampaignCount6 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount6);
        } else {
            throw new SQLException();
        }


        /* As a BeautyClub Program member I spend on Beauty products and started earning for free item when only free item campaign is Active and want to see Free Item Coupon with CpnNbr and pts
         *
         */
        xtraCard.setXtraCardNbr(900058607);
        xtraCard.setCustId(80607);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80607);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80607);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80607);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80607);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80607);
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
        Integer freeItemCampaignCount7 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign7 = null;
        if (freeItemCampaignCount7 == 1) {
            freeItemCampaign7 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign7);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058607);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058607);
            campaignActivePointBase.setCmpgnId(freeItemCampaign7);
            campaignActivePointBase.setPtsQty(4.57);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount7 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount7);
        } else if (freeItemCampaignCount7 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount7);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount7 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign7 = null;
        if (percentCampaignCount7 == 1) {
            percentCampaign7 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign7);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058607);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount7 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount7);
        } else if (percentCampaignCount7 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount7);
        } else {
            throw new SQLException();
        }

        /*  As a BeautyClub Program member I spend on Beauty products and started earning for 10% Coupon and dont want to see loadActualDate, couponIssueDate and couponDescription
         *
         */
        xtraCard.setXtraCardNbr(900058608);
        xtraCard.setCustId(80608);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80608);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80608);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80608);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80608);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80608);
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
        Integer freeItemCampaignCount8 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign8 = null;
        if (freeItemCampaignCount8 == 1) {
            freeItemCampaign8 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign8);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058608);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount8 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount8);
        } else if (freeItemCampaignCount8 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount8);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount8 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign8 = null;
        if (percentCampaignCount8 == 1) {
            percentCampaign8 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign8);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058608);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058608);
            campaignActivePointBase.setCmpgnId(percentCampaign8);
            campaignActivePointBase.setPtsQty(20.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign8);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr8 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr8 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr8 = percentCpnSeqNbr8 + 2;
            xtraCardActiveCoupon.setXtraCardNbr(900058608);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign8);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr8);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr8);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount8 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount8);
        } else if (percentCampaignCount8 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount8);
        } else {
            throw new SQLException();
        }

        /*  As a BeautyClub Program member I reached limit and earned 10% Coupon and want to see couponSequenceNumber ,couponIssueDate and loadActualDate but dont want to see couponDescription
         *
         */
        xtraCard.setXtraCardNbr(900058609);
        xtraCard.setCustId(80609);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80609);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80609);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80609);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80609);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80609);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount9 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign9 = null;
        if (freeItemCampaignCount9 == 1) {
            freeItemCampaign9 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign9);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058609);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount9 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount9);
        } else if (freeItemCampaignCount9 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount9);
        } else {
            throw new SQLException();
        }
        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount9 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign9 = null;
        if (percentCampaignCount9 == 1) {
            percentCampaign9 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign9);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058609);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058609);
            campaignActivePointBase.setCmpgnId(percentCampaign9);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign9);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr9 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr9 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr9 = percentCpnSeqNbr9 + 2;
            xtraCardActiveCoupon.setXtraCardNbr(900058609);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign9);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr9);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr9);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(simpleDateFormat.parse(todayTimeStamp));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount9 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount9);
        } else if (percentCampaignCount9 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount9);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and didnt earn points for Free Item Coupon and want to see couponDescription but dont want to see couponIssueDate,loadActualDate
         *
         */
        xtraCard.setXtraCardNbr(900058610);
        xtraCard.setCustId(80610);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80610);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80610);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80610);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80610);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80610);
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
        Integer freeItemCampaignCount10 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign10 = null;
        if (freeItemCampaignCount10 == 1) {
            freeItemCampaign10 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign10);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058610);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount10 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount10);
        } else if (freeItemCampaignCount10 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount10);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount10 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign10 = null;
        if (percentCampaignCount10 == 1) {
            percentCampaign10 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign10);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058610);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount10 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount10);
        } else if (percentCampaignCount10 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount10);
        } else {
            throw new SQLException();
        }


        /* As a BeautyClub Program member I spend on Beauty products and started earning points for Free Item Coupon and want to see couponDescription but dont want to see couponIssueDate and loadActualDate
         *
         */
        xtraCard.setXtraCardNbr(900058611);
        xtraCard.setCustId(80611);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80611);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80611);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80611);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80611);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80611);
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
        Integer freeItemCampaignCount11 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign11 = null;
        if (freeItemCampaignCount11 == 1) {
            freeItemCampaign11 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign11);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058611);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058611);
            campaignActivePointBase.setCmpgnId(freeItemCampaign11);
            campaignActivePointBase.setPtsQty(24.57);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount11 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount11);
        } else if (freeItemCampaignCount11 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount11);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount11 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign11 = null;
        if (percentCampaignCount11 == 1) {
            percentCampaign11 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign11);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058611);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount11 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount11);
        } else if (percentCampaignCount11 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount11);
        } else {
            throw new SQLException();
        }

        /* As a BeautyClub Program member I spend on Beauty products and reached limit and earned Free Item Coupon and want to see couponSequenceNumber ,couponDescription and loadActualDate but dont want to see couponIssueDate
         *
         */
        xtraCard.setXtraCardNbr(900058612);
        xtraCard.setCustId(80612);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80612);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80612);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80612);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80612);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80612);
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
        Integer freeItemCampaignCount12 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign12 = null;
        if (freeItemCampaignCount12 == 1) {
            freeItemCampaign12 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign12);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058612);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058612);
            campaignActivePointBase.setCmpgnId(freeItemCampaign12);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign12);
            campaignCoupon.setRwrdQty(1);
            Integer CpnNbr12 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr12 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr12 = freeCpnSeqNbr12 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058612);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign12);
            xtraCardActiveCoupon.setCpnNbr(CpnNbr12);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr12);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(simpleDateFormat.parse(todayTimeStamp));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount12 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount12);
        } else if (freeItemCampaignCount12 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount12);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount12 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign12 = null;
        if (percentCampaignCount12 == 1) {
            percentCampaign12 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign12);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058612);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount12 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount12);
        } else if (percentCampaignCount12 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount12);
        } else {
            throw new SQLException();
        }


        /* As a BeautyClub Program member I didnt spend on Beauty products for Free Item Coupon and want to see only Free Item Coupon with default response including CpnNbr and no pts
         *
         */
        xtraCard.setXtraCardNbr(900058613);
        xtraCard.setCustId(80613);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80613);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80613);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80613);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80613);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80613);
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
        Integer freeItemCampaignCount13 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign13 = null;
        if (freeItemCampaignCount13 == 1) {
            freeItemCampaign13 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign13);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058613);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount13 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount13);
        } else if (freeItemCampaignCount13 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount13);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I didnt spend on Beauty products for Free Item Coupon and 10% coupon and want to see Free Item Coupon and 10% coupon with default fields with CpnNbr and no pts
         *
         */
        xtraCard.setXtraCardNbr(900058614);
        xtraCard.setCustId(80614);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80614);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80614);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80614);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80614);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80614);
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
        Integer freeItemCampaignCount14 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign14 = null;
        if (freeItemCampaignCount14 == 1) {
            freeItemCampaign14 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign14);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058614);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount14 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount14);
        } else if (freeItemCampaignCount14 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount14);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount14 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign14 = null;
        if (percentCampaignCount14 == 1) {
            percentCampaign14 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign14);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058614);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount14 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount14);
        } else if (percentCampaignCount14 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount14);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see extrabuckRewardsEarned as 0$ when I didn't start earning for 10% Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058615);
        xtraCard.setCustId(80615);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80615);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80615);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80615);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80615);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80615);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount15 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign15 = null;
        if (percentCampaignCount15 == 1) {
            percentCampaign15 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign15);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058615);
            xtraCardSegment.setXtraCardSegNbr(percentSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (percentCampaignCount15 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount15);
        } else if (percentCampaignCount15 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount15);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see extrabuckRewardsEarned as 10$ when I earned 100pts for 10% Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058616);
        xtraCard.setCustId(80616);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80616);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80616);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80616);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80616);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80616);
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
        Integer freeItemCampaignCount16 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign16 = null;
        if (freeItemCampaignCount16 == 1) {
            freeItemCampaign16 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign16);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058616);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount16 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount16);
        } else if (freeItemCampaignCount16 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount16);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount16 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign16 = null;
        if (percentCampaignCount16 == 1) {
            percentCampaign16 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign16);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (freeSeg.equals(percentSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058616);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058616);
            campaignActivePointBase.setCmpgnId(percentCampaign16);
            campaignActivePointBase.setPtsQty(100.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign16);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr16 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr16 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr16 = percentCpnSeqNbr16 + 2;
            xtraCardActiveCoupon.setXtraCardNbr(900058616);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign16);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr16);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr16);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount16 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount16);
        } else if (percentCampaignCount16 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount16);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see extrabuckRewardsEarned as 20$ and offer LimitReached as true when I earned 201pts for 10% Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058617);
        xtraCard.setCustId(80617);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80617);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80617);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80617);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80617);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80617);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount17 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign17 = null;
        if (freeItemCampaignCount17 == 1) {
            freeItemCampaign17 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign17);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058617);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount1 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount1);
        } else if (freeItemCampaignCount1 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount1);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount17 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign17 = null;
        if (percentCampaignCount17 == 1) {
            percentCampaign17 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign17);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (freeSeg.equals(percentSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058617);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058617);
            campaignActivePointBase.setCmpgnId(percentCampaign17);
            campaignActivePointBase.setPtsQty(201.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign17);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr17 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr17 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr17 = percentCpnSeqNbr17 + 2;
            xtraCardActiveCoupon.setXtraCardNbr(900058617);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign17);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr17);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr17);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(simpleDateFormat.parse(todayTimeStamp));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount17 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount17);
        } else if (percentCampaignCount17 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount17);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see freeItemCoupon flag as true when Free Item Campaign is active
         *
         */
        xtraCard.setXtraCardNbr(900058618);
        xtraCard.setCustId(80618);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80618);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80618);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80618);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80618);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80618);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount18 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign18 = null;
        if (freeItemCampaignCount18 == 1) {
            freeItemCampaign18 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign18);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058618);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058618);
            campaignActivePointBase.setCmpgnId(freeItemCampaign18);
            campaignActivePointBase.setPtsQty(0.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount18 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount18);
        } else if (freeItemCampaignCount18 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount18);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see freeItemCoupon flag as false when 10% Campaign is active
         *
         */
        xtraCard.setXtraCardNbr(900058619);
        xtraCard.setCustId(80619);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80619);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80619);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80619);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80619);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80619);
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
        Integer freeItemCampaignCount19 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign19 = null;
        if (freeItemCampaignCount19 == 1) {
            freeItemCampaign19 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign19);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);
            xtraCardSegment.setXtraCardNbr(900058619);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount19 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount19);
        } else if (freeItemCampaignCount19 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount19);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount19 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign19 = null;
        if (percentCampaignCount19 == 1) {
            percentCampaign19 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign19);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (freeSeg.equals(percentSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058619);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058619);
            campaignActivePointBase.setCmpgnId(percentCampaign19);
            campaignActivePointBase.setPtsQty(0.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (percentCampaignCount19 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount19);
        } else if (percentCampaignCount19 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount19);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see daysLeft as 0 on last day of month for Free Item coupon
         *
         */
        xtraCard.setXtraCardNbr(900058620);
        xtraCard.setCustId(80620);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80620);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80620);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80620);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80620);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80620);
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
        Integer freeItemCampaignCount20 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign20 = null;
        if (freeItemCampaignCount20 == 1) {
            freeItemCampaign20 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign20);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058620);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);
        } else if (freeItemCampaignCount20 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount20);
        } else if (freeItemCampaignCount20 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount20);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount20 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign20 = null;
        if (percentCampaignCount20 == 1) {
            percentCampaign20 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign20);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (freeSeg.equals(percentSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058620);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
        } else if (percentCampaignCount20 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount20);
        } else if (percentCampaignCount20 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount20);
        } else {
            throw new SQLException();
        }

        /*  As a BeautyClub Program member I want to see daysLeft as 29 on first day of month for Free Item coupon
         *
         */
        xtraCard.setXtraCardNbr(900058621);
        xtraCard.setCustId(80621);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80621);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80621);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80621);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80621);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80621);
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
        Integer freeItemCampaignCount21 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign21 = null;
        if (freeItemCampaignCount21 == 1) {
            freeItemCampaign21 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign21);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058621);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058621);
            campaignActivePointBase.setCmpgnId(freeItemCampaign21);
            campaignActivePointBase.setPtsQty(0.50);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount21 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount21);
        } else if (freeItemCampaignCount21 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount21);
        } else {
            throw new SQLException();
        }

        /*  As a BeautyClub Program member I want to see daysLeft as 0 on last day of month for 10% coupon
         *
         */
        xtraCard.setXtraCardNbr(900058622);
        xtraCard.setCustId(80622);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80622);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80622);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80622);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80622);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80622);
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
        Integer freeItemCampaignCount22 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign22 = null;
        if (freeItemCampaignCount22 == 1) {
            freeItemCampaign22 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign22);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058622);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058622);
            campaignActivePointBase.setCmpgnId(freeItemCampaign22);
            campaignActivePointBase.setPtsQty(3.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount22 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount22);
        } else if (freeItemCampaignCount22 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount22);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount22 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign22 = null;
        if (percentCampaignCount22 == 1) {
            percentCampaign22 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign22);
            percentSeg = campaignDao.selectSegBCCampaign(campaign);
            if (freeSeg.equals(percentSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058622);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058622);
            campaignActivePointBase.setCmpgnId(percentCampaign22);
            campaignActivePointBase.setPtsQty(80.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign22);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr22 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr22 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr22 = percentCpnSeqNbr22 + 2;
            xtraCardActiveCoupon.setXtraCardNbr(900058622);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign22);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr22);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr22);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount22 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount22);
        } else if (percentCampaignCount22 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount22);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see daysLeft as 13 on first day of month for 10% coupon
         *
         */
        xtraCard.setXtraCardNbr(900058623);
        xtraCard.setCustId(80623);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80623);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80623);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80623);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80623);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80623);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount23 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign23 = null;
        if (percentCampaignCount23 == 1) {
            percentCampaign23 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign23);
            percentSeg = campaignDao.selectSegBCCampaign(campaign);

                xtraCardSegment.setXtraCardNbr(900058623);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058623);
            campaignActivePointBase.setCmpgnId(percentCampaign23);
            campaignActivePointBase.setPtsQty(80.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign23);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr23 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr23 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr23 = percentCpnSeqNbr23 + 2;
            xtraCardActiveCoupon.setXtraCardNbr(900058623);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign23);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr23);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr23);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount23 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount23);
        } else if (percentCampaignCount23 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount23);
        } else {
            throw new SQLException();
        }

        /*  As a BeautyClub Program Member I want to see loadable, redeemable indicators as N when the print date expires
         *
         */
        xtraCard.setXtraCardNbr(900058626);
        xtraCard.setCustId(80626);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80626);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80626);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80626);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80626);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80626);
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
        Integer freeItemCampaignCount26 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign26 = null;
        if (freeItemCampaignCount26 == 1) {
            freeItemCampaign26 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign26);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058626);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058626);
            campaignActivePointBase.setCmpgnId(freeItemCampaign26);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign26);
            campaignCoupon.setRwrdQty(1);
            Integer CpnNbr26 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr26 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr26 = freeCpnSeqNbr26 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058626);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign26);
            xtraCardActiveCoupon.setCpnNbr(CpnNbr26);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr26);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-14, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(14)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-14, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-1, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount26 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount26);
        } else if (freeItemCampaignCount26 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount26);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount26 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign26 = null;
        if (percentCampaignCount26 == 1) {
            percentCampaign26 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign26);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058626);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058626);
            campaignActivePointBase.setCmpgnId(percentCampaign26);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign26);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr26 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr26 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr26 = percentCpnSeqNbr26 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058626);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign26);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr26);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr26);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-14, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(14)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-14, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-1, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount26 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount26);
        } else if (percentCampaignCount26 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount26);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program Member I want to see loadable, redeemable indicators as N when the redeem date expires
         *
         */
        xtraCard.setXtraCardNbr(900058627);
        xtraCard.setCustId(80627);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80627);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80627);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80627);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80627);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80627);
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
        Integer freeItemCampaignCount27 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign27 = null;
        if (freeItemCampaignCount27 == 1) {
            freeItemCampaign27 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign27);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058627);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058627);
            campaignActivePointBase.setCmpgnId(freeItemCampaign27);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign27);
            campaignCoupon.setRwrdQty(1);
            Integer CpnNbr27 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr27 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr27 = freeCpnSeqNbr27 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058627);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign27);
            xtraCardActiveCoupon.setCpnNbr(CpnNbr27);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr27);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-31, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-16, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-15, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(-1, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardActiveCoupon.setRedeemActlAmt(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount27 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount27);
        } else if (freeItemCampaignCount27 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount27);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount27 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign27 = null;
        if (percentCampaignCount27 == 1) {
            percentCampaign27 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign27);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058627);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058627);
            campaignActivePointBase.setCmpgnId(percentCampaign27);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign27);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr27 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr27 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr27 = percentCpnSeqNbr27 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058627);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign27);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr27);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr27);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-31, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-16, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-15, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(-1, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount27 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount27);
        } else if (percentCampaignCount27 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount27);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program Member I want to see redeemable Ind as N  and redeemed Ind as Y when redeemActlDt is not null and coupon is present in redeemed_coupons table
         *
         */
        xtraCard.setXtraCardNbr(900058628);
        xtraCard.setCustId(80628);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80628);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80628);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80628);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80628);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80628);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount28 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign28 = null;
        if (freeItemCampaignCount28 == 1) {
            freeItemCampaign28 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign28);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058628);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058628);
            campaignActivePointBase.setCmpgnId(freeItemCampaign28);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign28);
            campaignCoupon.setRwrdQty(1);
            Integer CpnNbr28 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr28 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr28 = freeCpnSeqNbr28 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058628);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign28);
            xtraCardActiveCoupon.setCpnNbr(CpnNbr28);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr28);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(-3, -30));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-2, -10));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(0, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, -10));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount28 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount28);
        } else if (freeItemCampaignCount28 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount28);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount28 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign28 = null;
        if (percentCampaignCount28 == 1) {
            percentCampaign28 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign28);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058628);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058628);
            campaignActivePointBase.setCmpgnId(percentCampaign28);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign28);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr28 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr28 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr28 = percentCpnSeqNbr28 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058628);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign28);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr28);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr28);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(-3, -30));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-2, -10));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(0, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, -10));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount28 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount28);
        } else if (percentCampaignCount28 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount28);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see expiryDate same as printEndDate when coupon is not s2c for Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058629);
        xtraCard.setCustId(80629);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80629);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80629);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80629);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80629);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80629);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount29 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign29 = null;
        if (freeItemCampaignCount29 == 1) {
            freeItemCampaign29 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign29);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058629);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058629);
            campaignActivePointBase.setCmpgnId(freeItemCampaign29);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign29);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr29 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr29 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr29 = freeCpnSeqNbr29 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058629);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign29);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr29);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr29);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount29 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount29);
        } else if (freeItemCampaignCount29 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount29);
        } else {
            throw new SQLException();
        }

        /*  As a BeautyClub Program member I want to see expiryDate same as redeemEndDate when coupon after coupon s2c for Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058630);
        xtraCard.setCustId(80630);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80630);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80630);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80630);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80630);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80630);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount30 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign30 = null;
        if (freeItemCampaignCount30 == 1) {
            freeItemCampaign30 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign30);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058630);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058630);
            campaignActivePointBase.setCmpgnId(freeItemCampaign30);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign30);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr30 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr30 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr30 = freeCpnSeqNbr30 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058630);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign30);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr30);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr30);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(0, -30));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(0, -10));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(0, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount30 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount30);
        } else if (freeItemCampaignCount30 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount30);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see expiryDate same as printEndDate when coupon is not s2c for 10% Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058631);
        xtraCard.setCustId(80631);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80631);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80631);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80631);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80631);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80631);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount31 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign31 = null;
        if (percentCampaignCount31 == 1) {
            percentCampaign31 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign31);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058631);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }
            campaignActivePointBase.setXtraCardNbr(900058631);
            campaignActivePointBase.setCmpgnId(percentCampaign31);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign31);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr31 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr31 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr31 = percentCpnSeqNbr31 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058631);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign31);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr31);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr31);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount31 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount31);
        } else if (percentCampaignCount31 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount31);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see expiryDate same as redeemEndDate when coupon after coupon s2c for 10% Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058632);
        xtraCard.setCustId(80632);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80632);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80632);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80632);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80632);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80632);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount32 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign32 = null;
        if (percentCampaignCount32 == 1) {
            percentCampaign32 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign32);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058632);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058632);
            campaignActivePointBase.setCmpgnId(percentCampaign32);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign32);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr32 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr32 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr32 = percentCpnSeqNbr32 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058632);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign32);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr32);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr32);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(0, -30));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(0, -10));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(0, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount32 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount32);
        } else if (percentCampaignCount32 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount32);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I don't want to see 10% Coupon when 10% Campaign is Inactive
         *
         */
        xtraCard.setXtraCardNbr(900058633);
        xtraCard.setCustId(80633);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80633);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80633);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80633);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80633);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80633);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        xtraCardSegment.setXtraCardNbr(900058633);
        xtraCardSegment.setXtraCardSegNbr(195);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

        xtraCardSegment.setXtraCardNbr(900058633);
        xtraCardSegment.setXtraCardSegNbr(196);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);


        /*  As a BeautyClub Program member I don't want to see 10% Coupon when 10% Campaign is Inactive
         *
         */
        xtraCard.setXtraCardNbr(900058634);
        xtraCard.setCustId(80634);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80634);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80634);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80634);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80634);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80634);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        xtraCardSegment.setXtraCardNbr(900058634);
        xtraCardSegment.setXtraCardSegNbr(196);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);


        /*  As a BeautyClub Program member I don't want to see Free Item Coupon when Free Item Campaign is Inactive
         *
         */
        xtraCard.setXtraCardNbr(900058635);
        xtraCard.setCustId(80635);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80635);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80635);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80635);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80635);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80635);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        xtraCardSegment.setXtraCardNbr(900058635);
        xtraCardSegment.setXtraCardSegNbr(195);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);


        /*  As a BeautyClub Program member I spend on Beauty products and started earning for both free item and 10% Coupons when both Campaigns are Active
         *
         */
        xtraCard.setXtraCardNbr(900058636);
        xtraCard.setCustId(80636);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80636);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80636);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80636);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80636);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80636);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount36 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign36 = null;
        if (freeItemCampaignCount36 == 1) {
            freeItemCampaign36 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign36);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058636);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058636);
            campaignActivePointBase.setCmpgnId(freeItemCampaign36);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign36);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr36 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr36 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr36 = freeCpnSeqNbr36 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058636);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign36);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr36);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr36);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount36 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount36);
        } else if (freeItemCampaignCount36 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount36);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount36 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign36 = null;
        if (percentCampaignCount36 == 1) {
            percentCampaign36 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign36);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058636);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058636);
            campaignActivePointBase.setCmpgnId(percentCampaign36);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign36);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr36 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr36 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr36 = percentCpnSeqNbr36 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058636);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign36);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr36);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr36);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(null);
            xtraCardActiveCoupon.setViewActlTswtzSetDt(null);
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(null);
            xtraCardActiveCoupon.setPrntEndTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount36 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount36);
        } else if (percentCampaignCount36 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount36);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see expiryDate same as redeemEndDate after coupon is s2c
         *
         */
        xtraCard.setXtraCardNbr(900058637);
        xtraCard.setCustId(80637);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80637);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80637);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80637);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80637);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80637);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);
        Integer freeItemCampaignCount37 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign37 = null;
        if (freeItemCampaignCount37 == 1) {
            freeItemCampaign37 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign37);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058637);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058637);
            campaignActivePointBase.setCmpgnId(freeItemCampaign37);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign37);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr37 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr37 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr37 = freeCpnSeqNbr37 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058637);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign37);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr37);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr37);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(0, -30));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(0, -10));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(0, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount37 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount37);
        } else if (freeItemCampaignCount37 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount37);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount37 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign37 = null;
        if (percentCampaignCount37 == 1) {
            percentCampaign37 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign37);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {
            } else {
                xtraCardSegment.setXtraCardNbr(900058637);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058637);
            campaignActivePointBase.setCmpgnId(percentCampaign37);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign37);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr37 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr37 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr37 = percentCpnSeqNbr37 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058637);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign37);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr37);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr37);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(0, -30));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(0, -10));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(0, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount37 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount37);
        } else if (percentCampaignCount37 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount37);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I want to see expiryDate same as printEndDate when coupon is not s2c for both the Campaigns
         *
         */
        xtraCard.setXtraCardNbr(900058638);
        xtraCard.setCustId(80638);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80638);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80638);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80638);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80638);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80638);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

        Integer freeItemCampaignCount38 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign38 = null;
        if (freeItemCampaignCount38 == 1) {
            freeItemCampaign38 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign38);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058638);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058638);
            campaignActivePointBase.setCmpgnId(freeItemCampaign38);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign38);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr38 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr38 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr38 = freeCpnSeqNbr38 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058638);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign38);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr38);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr38);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount38 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount38);
        } else if (freeItemCampaignCount38 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount38);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount38 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign38 = null;
        if (percentCampaignCount38 == 1) {
            percentCampaign38 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign38);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058638);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058638);
            campaignActivePointBase.setCmpgnId(percentCampaign38);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign38);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr38 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr38 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr38 = percentCpnSeqNbr38 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058638);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign38);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr38);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr38);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount38 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount38);
        } else if (percentCampaignCount38 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount38);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and reached limit and redeemed on 1st day of the month and want to see Redeem Expire Date
         *
         */
        xtraCard.setXtraCardNbr(900058639);
        xtraCard.setCustId(80639);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80639);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80639);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80639);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80639);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80639);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount39 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign39 = null;
        if (freeItemCampaignCount39 == 1) {
            freeItemCampaign39 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign39);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058639);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058639);
            campaignActivePointBase.setCmpgnId(freeItemCampaign39);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign39);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr39 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr39 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr39 = freeCpnSeqNbr39 + 1;
            campaign.setCmpgnId(freeItemCampaign39);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();
            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058639);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign39);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr39);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr39);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allStartDate + 14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount39 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount39);
        } else if (freeItemCampaignCount39 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount39);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount39 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign39 = null;
        if (percentCampaignCount39 == 1) {
            percentCampaign39 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign39);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058639);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058639);
            campaignActivePointBase.setCmpgnId(percentCampaign39);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign39);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr39 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr39 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr39 = percentCpnSeqNbr39 + 1;
            campaign.setCmpgnId(percentCampaign39);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();

            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058639);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign39);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr39);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr39);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allStartDate + 14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount39 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount39);
        } else if (percentCampaignCount39 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount39);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and reached limit on 14th day of the month and want to see Redeem Expire Date
         *
         */
        xtraCard.setXtraCardNbr(900058640);
        xtraCard.setCustId(80640);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80640);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80640);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80640);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80640);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80640);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount40 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign40 = null;
        if (freeItemCampaignCount40 == 1) {
            freeItemCampaign40 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign40);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058640);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058640);
            campaignActivePointBase.setCmpgnId(freeItemCampaign40);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign40);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr40 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr40 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr40 = freeCpnSeqNbr40 + 1;
            campaign.setCmpgnId(freeItemCampaign40);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();
            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058640);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign40);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr40);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr40);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allStartDate + 14 + 13, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount40 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount40);
        } else if (freeItemCampaignCount40 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount40);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount40 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign40 = null;
        if (percentCampaignCount40 == 1) {
            percentCampaign40 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign40);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058640);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058640);
            campaignActivePointBase.setCmpgnId(percentCampaign40);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign40);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr40 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr40 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr40 = percentCpnSeqNbr40 + 1;
            campaign.setCmpgnId(percentCampaign40);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();

            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058640);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign40);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr40);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr40);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allStartDate + 13 + 13, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allStartDate + 13, 0));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount40 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount40);
        } else if (percentCampaignCount40 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount40);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and reached limit at 11:59PM on last day of the month and want to see Redeem Expire Date
         *
         */
        xtraCard.setXtraCardNbr(900058641);
        xtraCard.setCustId(80641);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80641);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80641);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80641);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80641);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80641);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount41 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign41 = null;
        if (freeItemCampaignCount41 == 1) {
            freeItemCampaign41 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign41);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058641);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058641);
            campaignActivePointBase.setCmpgnId(freeItemCampaign41);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign41);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr41 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr41 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr41 = freeCpnSeqNbr41 + 1;
            campaign.setCmpgnId(freeItemCampaign41);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();
            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058641);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign41);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr41);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr41);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate - 2)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allEndDate - 2, -1430));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate - 2)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allEndDate - 2, -1435));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate - 2)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allEndDate - 2, -1435));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate - 2)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allEndDate + 11, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allEndDate + 14 + 11, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allEndDate + 11, -1439));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate + 11)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount41 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount41);
        } else if (freeItemCampaignCount41 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount41);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount41 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign41 = null;
        System.out.println("percentCampaignCount41:" + percentCampaignCount41);
        if (percentCampaignCount41 == 1) {
            percentCampaign41 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign41);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058641);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058641);
            campaignActivePointBase.setCmpgnId(percentCampaign41);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign41);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr41 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr41 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr41 = percentCpnSeqNbr41 + 1;
            campaign.setCmpgnId(percentCampaign41);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();
            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058641);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign41);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr41);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr41);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allStartDate + 13)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allEndDate - 2, -1430));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate - 2)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allEndDate - 2, -1435));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate - 2)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allEndDate - 2, -1435));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate - 2)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allEndDate + 11, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allEndDate + 14 + 11, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allEndDate + 11, -1439));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate + 11)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (percentCampaignCount41 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount41);
        } else if (percentCampaignCount41 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount41);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and haven't reached limit at 11:59PM on last day of the month and want to see Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058642);
        xtraCard.setCustId(80642);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80642);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80642);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80642);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80642);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80642);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount42 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign42 = null;
        if (freeItemCampaignCount42 == 1) {
            freeItemCampaign42 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign42);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058642);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058642);
            campaignActivePointBase.setCmpgnId(freeItemCampaign42);
            campaignActivePointBase.setPtsQty(20.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);
        } else if (freeItemCampaignCount42 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount42);
        } else if (freeItemCampaignCount42 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount42);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount42 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign42 = null;
        if (percentCampaignCount42 == 1) {
            percentCampaign42 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign42);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058642);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058642);
            campaignActivePointBase.setCmpgnId(percentCampaign42);
            campaignActivePointBase.setPtsQty(100.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign42);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr42 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr42 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr42 = percentCpnSeqNbr42 + 1;
            campaign.setCmpgnId(percentCampaign42);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();
            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058642);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign42);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr42);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr42);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allEndDate, -1430));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allEndDate + 14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
        } else if (percentCampaignCount42 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount42);
        } else if (percentCampaignCount42 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount42);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and reached limit at 11:59PM on lastday-2 of the month and want to see Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058643);
        xtraCard.setCustId(80643);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80643);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80643);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80643);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80643);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80643);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount43 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign43 = null;
        if (freeItemCampaignCount43 == 1) {
            freeItemCampaign43 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign43);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058643);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058643);
            campaignActivePointBase.setCmpgnId(freeItemCampaign43);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign43);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr43 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr43 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr43 = freeCpnSeqNbr43 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058643);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign43);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr43);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr43);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount43 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount43);
        } else if (freeItemCampaignCount43 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount43);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount43 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign43 = null;
        if (percentCampaignCount43 == 1) {
            percentCampaign43 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign43);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058643);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058643);
            campaignActivePointBase.setCmpgnId(percentCampaign43);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign43);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr43 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr43 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr43 = percentCpnSeqNbr43 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058643);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign43);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr43);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr43);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount43 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount43);
        } else if (percentCampaignCount43 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount43);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I spend on Beauty products and reached limit at 12:00AM on last day of the month and want to see Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058644);
        xtraCard.setCustId(80644);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80644);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80644);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80644);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80644);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80644);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount44 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign44 = null;
        if (freeItemCampaignCount44 == 1) {
            freeItemCampaign44 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign44);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058644);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058644);
            campaignActivePointBase.setCmpgnId(freeItemCampaign44);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign44);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr44 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr44 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr44 = freeCpnSeqNbr44 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058644);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign44);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr44);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr44);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount44 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount44);
        } else if (freeItemCampaignCount44 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount44);
        } else {
            throw new SQLException();
        }

        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount44 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign44 = null;
        if (percentCampaignCount44 == 1) {
            percentCampaign44 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign44);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058644);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058644);
            campaignActivePointBase.setCmpgnId(percentCampaign44);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign44);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr44 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr44 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr44 = percentCpnSeqNbr44 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058644);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign44);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr44);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr44);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardActiveCoupon.setRedeemActlAmt(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount44 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount44);
        } else if (percentCampaignCount44 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount44);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I redeemed at 11:59PM on last day of the month and want to see Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058645);
        xtraCard.setCustId(80645);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80645);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80645);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80645);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80645);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80645);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount45 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign45 = null;
        if (freeItemCampaignCount45 == 1) {
            freeItemCampaign45 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign45);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058645);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058645);
            campaignActivePointBase.setCmpgnId(freeItemCampaign45);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign45);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr45 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr45 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr45 = freeCpnSeqNbr45 + 1;
            campaign.setCmpgnId(freeItemCampaign45);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();
            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058645);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign45);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr45);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr45);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allEndDate, -1430));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allEndDate, -1435));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allEndDate, -1435));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allEndDate + 14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allEndDate + 14, -1439));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate + 14)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardActiveCoupon.setRedeemActlAmt(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount45 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount45);
        } else if (freeItemCampaignCount45 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount45);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount45 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign45 = null;
        if (percentCampaignCount45 == 1) {
            percentCampaign45 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign45);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058645);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058645);
            campaignActivePointBase.setCmpgnId(percentCampaign45);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign45);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr45 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr45 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr45 = percentCpnSeqNbr45 + 1;
            campaign.setCmpgnId(percentCampaign45);
            String stDt = (campaignDao.selectStartDtBCCampaign(campaign)).toString();
            String startDt = stDt.substring(0, 4) + "-" + stDt.substring(5, 7) + "-" + stDt.substring(8, 10);
            LocalDate redeemStartDate = LocalDate.parse(startDt);
            LocalDate currentDate = LocalDate.parse(todayDate);
            Integer allStartDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemStartDate));
            String edDt = (campaignDao.selectEndDtBCCampaign(campaign)).toString();
            String endDt = edDt.substring(0, 4) + "-" + edDt.substring(5, 7) + "-" + edDt.substring(8, 10);
            LocalDate redeemEndDate = LocalDate.parse(endDt);
            Integer allEndDate = Math.toIntExact(ChronoUnit.DAYS.between(currentDate, redeemEndDate));

            xtraCardActiveCoupon.setXtraCardNbr(900058645);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign45);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr45);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr45);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(allEndDate, -1430));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(allStartDate, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(dateUtil.setTswtzHour(allEndDate, -1435));
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(allEndDate, -1435));
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(allEndDate, 0));
            xtraCardActiveCoupon.setRedeemEndTswtz(dateUtil.setTswtzHour(allEndDate + 14, 0));
            xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(allEndDate, -1439));
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(allEndDate)));
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardActiveCoupon.setRedeemActlAmt(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount45 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount45);
        } else if (percentCampaignCount45 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount45);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I redeemed on 4th day of month and after Campaign expired and want to see Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058646);
        xtraCard.setCustId(80646);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80646);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80646);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80646);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80646);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80646);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount46 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign46 = null;
        if (freeItemCampaignCount46 == 1) {
            freeItemCampaign46 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign46);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058646);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058646);
            campaignActivePointBase.setCmpgnId(freeItemCampaign46);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign46);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr46 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr46 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr46 = freeCpnSeqNbr46 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058646);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign46);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr46);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr46);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount46 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount46);
        } else if (freeItemCampaignCount46 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount46);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount46 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign46 = null;
        if (percentCampaignCount46 == 1) {
            percentCampaign46 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign46);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058646);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058646);
            campaignActivePointBase.setCmpgnId(percentCampaign46);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign46);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr46 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr46 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr46 = percentCpnSeqNbr46 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058646);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign46);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr46);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr46);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount46 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount46);
        } else if (percentCampaignCount46 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount46);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I redeemed at 29th day of the month and want to see Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058647);
        xtraCard.setCustId(80647);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80647);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80647);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80647);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80647);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80647);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount47 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign47 = null;
        if (freeItemCampaignCount47 == 1) {
            freeItemCampaign47 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign47);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058647);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058647);
            campaignActivePointBase.setCmpgnId(freeItemCampaign47);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign47);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr47 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr47 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr47 = freeCpnSeqNbr47 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058647);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign47);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr47);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr47);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount47 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount47);
        } else if (freeItemCampaignCount47 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount47);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount47 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign47 = null;
        if (percentCampaignCount47 == 1) {
            percentCampaign47 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign47);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058647);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058647);
            campaignActivePointBase.setCmpgnId(percentCampaign47);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign47);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr47 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr47 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr47 = percentCpnSeqNbr47 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058647);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign47);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr47);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr47);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount47 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount47);
        } else if (percentCampaignCount47 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount47);
        } else {
            throw new SQLException();
        }


        /*  As a BeautyClub Program member I redeemed on 5th day of next month after Campaign expired and want to see Free Item Coupon
         *
         */
        xtraCard.setXtraCardNbr(900058648);
        xtraCard.setCustId(80648);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80648);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80648);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80648);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80648);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        customerOpt.setCustId(80648);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        Integer freeItemCampaignCount48 = campaignDao.checkBCCampaign(campaign);
        Integer freeItemCampaign48 = null;
        if (freeItemCampaignCount48 == 1) {
            freeItemCampaign48 = campaignDao.selectBCCampaign(campaign);
            campaign.setCmpgnId(freeItemCampaign48);
            freeSeg = campaignDao.selectSegBCCampaign(campaign);

            xtraCardSegment.setXtraCardNbr(900058648);
            xtraCardSegment.setXtraCardSegNbr(freeSeg);
            xtraCardSegment.setCtlGrpCd(null);
            xtraCardDao.createXtraCardSegment(xtraCardSegment);

            campaignActivePointBase.setXtraCardNbr(900058648);
            campaignActivePointBase.setCmpgnId(freeItemCampaign48);
            campaignActivePointBase.setPtsQty(30.00);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(freeItemCampaign48);
            campaignCoupon.setRwrdQty(1);
            Integer freeCpnNbr48 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long freeCpnSeqNbr48 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextFreeCpnSeqNbr48 = freeCpnSeqNbr48 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058648);
            xtraCardActiveCoupon.setCmpgnId(freeItemCampaign48);
            xtraCardActiveCoupon.setCpnNbr(freeCpnNbr48);
            xtraCardActiveCoupon.setCpnseqNbr(nextFreeCpnSeqNbr48);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        } else if (freeItemCampaignCount48 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount48);
        } else if (freeItemCampaignCount48 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, freeItemCampaignCount48);
        } else {
            throw new SQLException();
        }


        campaign.setMktgPrgCd("B");
        Integer percentCampaignCount48 = campaignDao.checkBCPercentCampaign(campaign);
        Integer percentCampaign48 = null;
        if (percentCampaignCount48 == 1) {
            percentCampaign48 = campaignDao.selectPercentBCCampaign(campaign);
            campaign.setCmpgnId(percentCampaign48);
            percentSeg = campaignDao.selectPercentBCSegCampaign(campaign);
            if (percentSeg.equals(freeSeg)) {

            } else {
                xtraCardSegment.setXtraCardNbr(900058648);
                xtraCardSegment.setXtraCardSegNbr(percentSeg);
                xtraCardSegment.setCtlGrpCd(null);
                xtraCardDao.createXtraCardSegment(xtraCardSegment);
            }

            campaignActivePointBase.setXtraCardNbr(900058648);
            campaignActivePointBase.setCmpgnId(percentCampaign48);
            campaignActivePointBase.setPtsQty(200.0);
            campaignActivePointBase.setActivationTs(null);
            campaignActivePointBase.setActivationSrcCd(null);
            campaignDao.createCampaignActivePointBase(campaignActivePointBase);

            campaignCoupon.setCmpgnId(percentCampaign48);
            campaignCoupon.setRwrdQty(20);
            Integer percentCpnNbr48 = campaignDao.selectCampaignCoupon(campaignCoupon);
            Long percentCpnSeqNbr48 = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
            Long nextPercentCpnSeqNbr48 = percentCpnSeqNbr48 + 1;
            xtraCardActiveCoupon.setXtraCardNbr(900058648);
            xtraCardActiveCoupon.setCmpgnId(percentCampaign48);
            xtraCardActiveCoupon.setCpnNbr(percentCpnNbr48);
            xtraCardActiveCoupon.setCpnseqNbr(nextPercentCpnSeqNbr48);
            xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
            xtraCardActiveCoupon.setCpnSrcCd("S");
            xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
            xtraCardActiveCoupon.setRegRetlAmt(null);
            xtraCardActiveCoupon.setEmailCmpgnId(null);
            xtraCardActiveCoupon.setReIssueNbr(null);
            xtraCardActiveCoupon.setStoreSeenNbr(null);
            xtraCardActiveCoupon.setStoreSeenTs(null);
            xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
            xtraCardActiveCoupon.setViewActlDestCd(null);
            xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setViewActlStoreNbr(null);
            xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-7, 0));
            xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(7, 0));
            xtraCardActiveCoupon.setPrntActlTswtz(null);
            xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
            xtraCardActiveCoupon.setPrntDestCd(null);
            xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setPrntPriorityNbr(null);
            xtraCardActiveCoupon.setPrntActlReferByCd(null);
            xtraCardActiveCoupon.setPrntStoreNbr(null);
            xtraCardActiveCoupon.setLoadActlTswtz(null);
            xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
            xtraCardActiveCoupon.setLoadActlDestCd(null);
            xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
            xtraCardActiveCoupon.setLoadActlReferByCd(null);
            xtraCardActiveCoupon.setLoadActlStoreNbr(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
            xtraCardActiveCoupon.setMfrLoadNotifStatusTs(null);
            xtraCardActiveCoupon.setRedeemStartTswtz(null);
            xtraCardActiveCoupon.setRedeemEndTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtz(null);
            xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
            xtraCardActiveCoupon.setRedeemStoreNbr(null);
            xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);

        } else if (percentCampaignCount48 > 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount48);
        } else if (percentCampaignCount48 < 1) {
            throw new IncorrectResultSizeDataAccessException(1, percentCampaignCount48);
        } else {
            throw new SQLException();
        }


        /*
         *  Mobile - I am a fraudulent user, I would like to see my getcust response
         */
        xtraHotCard.setXtraCardNbr(900058650);
        xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
        xtraCardDao.createXtraHotCard(xtraHotCard);


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
        List<Integer> xtraCardNbrList = Arrays.asList(900058601, 900058602, 900058603, 900058604, 900058605, 900058606, 900058607, 900058608, 900058609, 900058610, 900058611, 900058612, 900058613, 900058614, 900058615, 900058616, 900058617, 900058618, 900058619, 900058620, 900058621, 900058622, 900058623, 900058624, 900058625, 900058626, 900058627, 900058628, 900058629, 900058630, 900058631, 900058632, 900058633, 900058634, 900058635, 900058636, 900058637, 900058638, 900058639, 900058640, 900058641, 900058642, 900058643, 900058644, 900058645, 900058646, 900058647, 900058648, 900058649, 900058650, 900058651, 900058652);
        List<Integer> custIdList = Arrays.asList(80601, 80602, 80603, 80604, 80605, 80606, 80607, 80608, 80609, 80610, 80611, 80612, 80613, 80614, 80615, 80616, 80617, 80618, 80619, 80620, 80621, 80622, 80623, 80624, 80625, 80626, 80627, 80628, 80629, 80630, 80631, 80632, 80633, 80634, 80635, 80636, 80637, 80638, 80639, 80640, 80641, 80642, 80643, 80644, 80645, 80646, 80647, 80648, 80649, 80650, 80651, 80652);
        campaignDao.deleteCampaignActivePointBaseRecords(xtraCardNbrList);
        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}