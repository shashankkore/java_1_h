package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.model.request.RewardTransactionRequest;
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
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.XtraHotCard;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Data
public class RecentlyRedeemedTransactionHistoryService {

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
    CampaignDao campaignDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    public void viewRecentlyRedeemedTransactionHistory(RewardTransactionRequest rewardTransactionRequest) {

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
                .setBasePath("api/v1.1/{search_card_type},{search_card_nbr}/rewards/redeemedDeals/history")
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
     * Create Test Data For quarterly Extra Bucks Scenario
     */
    public void createRecentlyRedeemedHistoryTestData() throws ParseException {

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

        Long cpnSeqNbr;
        Long nextCpnSeqNbr;

//        campaignCoupon.setCmpgnId(10572);
        campaignCoupon.setCmpgnId(40910);
        campaignCoupon.setCpnNbr(43739);
        campaignCoupon.setCpnRecptTxt("$2 off 2 Lysol Wipes");
        campaignCoupon.setCpnDsc("$2 off 2 Lysol Wipes. One Day Only");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);


        campaignCoupon.setCmpgnId(39853);
        campaignCoupon.setCpnNbr(58150);
        campaignCoupon.setCpnRecptTxt("20% Off any Vicks_txt");
        campaignCoupon.setCpnDsc("20% Off any Vicks");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);


        campaignCoupon.setCmpgnId(40790);
        campaignCoupon.setCpnNbr(59190);
        campaignCoupon.setCpnRecptTxt("CAREPASS 20%OFF CVS HEALTH");
        campaignCoupon.setCpnDsc("20% off CVS Health brand benefit for CarePass members");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);

//        campaignCoupon.setCmpgnId(2587);
        campaignCoupon.setCmpgnId(49227);
        campaignCoupon.setCpnNbr(47630);
        campaignCoupon.setCpnRecptTxt("$10 off Purchase of $50");
        campaignCoupon.setCpnDsc("$10 off Purchase of $50 or More");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);

        campaignCoupon.setCmpgnId(40666);
        campaignCoupon.setCpnNbr(59121);
        campaignCoupon.setCpnRecptTxt("CarePass $10 reward");
        campaignCoupon.setCpnDsc("CarePass $10 Reward");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);


//        campaignCoupon.setCmpgnId(20374);
        campaignCoupon.setCmpgnId(39199);
        campaignCoupon.setCpnNbr(45671);
        campaignCoupon.setCpnRecptTxt("$3 off ThermaCare Heatwrap");
        campaignCoupon.setCpnDsc("$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);

        campaignCoupon.setCmpgnId(41551);
        campaignCoupon.setCpnNbr(72371);
        campaignCoupon.setCpnRecptTxt("$2 off LOREAL LIPSTICK");
        campaignCoupon.setCpnDsc("$2 OFF (3) LOREAL LIPSTICK");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);


        campaignCoupon.setCmpgnId(42342);
        campaignCoupon.setCpnNbr(73891);
        campaignCoupon.setCpnRecptTxt("2 Dollars off Cosmetics");
        campaignCoupon.setCpnDsc("You received a 2 dollar off cosmetics coupon!!");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);

        campaignCoupon.setCmpgnId(41624);
        campaignCoupon.setCpnNbr(73239);
        campaignCoupon.setCpnRecptTxt("PE coupon $5");
        campaignCoupon.setCpnDsc("$5.00 ExtraBucks Rewards");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);

        campaignCoupon.setCmpgnId(42545);
        campaignCoupon.setCpnNbr(74026);
        campaignCoupon.setCpnDsc("$3 off purchase");
        campaignCoupon.setCpnRecptTxt("$3 OFF");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);

        campaignCoupon.setCmpgnId(41222);
        campaignCoupon.setCpnNbr(71812);
        campaignCoupon.setCpnRecptTxt("BOGO CANDY");
        campaignCoupon.setCpnDsc("BOGO Candy Offer");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);


//        campaignCoupon.setCmpgnId(15413);
        campaignCoupon.setCmpgnId(41838);
        campaignCoupon.setCpnNbr(42052);
        campaignCoupon.setCpnRecptTxt("$1 off Nature's Bounty");
        campaignCoupon.setCpnDsc("$1 off Nature's Bounty or Radiance Black Cohosh");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);


//        campaignCoupon.setCmpgnId(5722);
        campaignCoupon.setCmpgnId(41838);
        campaignCoupon.setCpnNbr(42677);
        campaignCoupon.setCpnRecptTxt("Save $1 on Ice Cubes");
        campaignCoupon.setCpnDsc("Save $1 on Ice Cubes 3pk");
        campaignCoupon.setStartDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        campaignCoupon.setEndDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        campaignDao.updateCampaignCoupon(campaignCoupon);


        /*  I am an Xtra Card member and redeemed $3 coupons yesterday
         *
         */
        xtraCard.setXtraCardNbr(98158327);
        xtraCard.setCustId(80060);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80060);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80060);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80060);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80060);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 1;

        xtraCardActiveCoupon.setXtraCardNbr(98158327);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(42545);
        xtraCardActiveCoupon.setCpnNbr(74026);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*  I am an Xtra Card member and redeemed two $ 2 and $3 coupons in single transaction two days before
         *
         */
        xtraCard.setXtraCardNbr(98158328);
        xtraCard.setCustId(80061);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80061);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80061);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80061);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80061);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 2;

        xtraCardActiveCoupon.setXtraCardNbr(98158328);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(42545);
        xtraCardActiveCoupon.setCpnNbr(74026);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-2, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 3;

        xtraCardActiveCoupon.setXtraCardNbr(98158328);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(41551);
        xtraCardActiveCoupon.setCpnNbr(72371);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-2, 1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*  I am an Xtra Card member and redeemed three $1, $2 coupon and $3EB in three seperate transaction today
         *
         */
        xtraCard.setXtraCardNbr(98158329);
        xtraCard.setCustId(80062);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");

        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80062);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80062);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80062);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80062);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 4;

        xtraCardActiveCoupon.setXtraCardNbr(98158329);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(5722);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42677);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 2));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 5;

        xtraCardActiveCoupon.setXtraCardNbr(98158329);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(41551);
        xtraCardActiveCoupon.setCpnNbr(72371);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 6;

        xtraCardActiveCoupon.setXtraCardNbr(98158329);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(42545);
        xtraCardActiveCoupon.setCpnNbr(74026);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*  I am an Xtra Card member and redeemed $2 coupons $10 EB carepass coupon
         *
         */
        xtraCard.setXtraCardNbr(98158330);
        xtraCard.setCustId(80063);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80063);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80063);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80063);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80063);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 7;

        xtraCardActiveCoupon.setXtraCardNbr(98158330);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnNbr(59121);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(10.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 8;

        xtraCardActiveCoupon.setXtraCardNbr(98158330);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(41551);
        xtraCardActiveCoupon.setCpnNbr(72371);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, 1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        /*  I am an Xtra Card member and redeemed $10 off on $50 above 3 days before
         *
         */
        xtraCard.setXtraCardNbr(98158331);
        xtraCard.setCustId(80064);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80064);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80064);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80064);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80064);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 9;

        xtraCardActiveCoupon.setXtraCardNbr(98158331);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(2587);
        xtraCardActiveCoupon.setCmpgnId(49227);
        xtraCardActiveCoupon.setCpnNbr(47630);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-3, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(10.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        /*  I am an Xtra Card member and redeemed 20% carepass coupon within 14 days
         *
         */
        xtraCard.setXtraCardNbr(98158332);
        xtraCard.setCustId(80065);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80065);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80065);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80065);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80065);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 10;

        xtraCardActiveCoupon.setXtraCardNbr(98158332);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(40790);
        xtraCardActiveCoupon.setCpnNbr(59190);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-14, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(14)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(10.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*  I am an Xtra Card member and redeemed BOGO offer or %50 on 2nd item within 14 days
         *
         */
        xtraCard.setXtraCardNbr(98158333);
        xtraCard.setCustId(80066);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80066);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80066);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80066);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80066);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 11;

        xtraCardActiveCoupon.setXtraCardNbr(98158333);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(41222);
        xtraCardActiveCoupon.setCpnNbr(71812);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-13, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(13)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.99);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*  I am an Xtra Card member and redeemed $1 off MFR offer on Bounty within 14 days
         *
         */
        xtraCard.setXtraCardNbr(98158334);
        xtraCard.setCustId(80067);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80067);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80067);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80067);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80067);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 12;

        xtraCardActiveCoupon.setXtraCardNbr(98158334);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(15413);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42052);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-14, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(14)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*  I am an Xtra Card member and redeemed $2 off MFR lysol offer and $2 CVS offer in same transaction within last 14 days
         *
         */
        xtraCard.setXtraCardNbr(98158335);
        xtraCard.setCustId(80068);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80068);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80068);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80068);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80068);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 13;

        xtraCardActiveCoupon.setXtraCardNbr(98158335);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(10572);
        xtraCardActiveCoupon.setCmpgnId(40910);
        xtraCardActiveCoupon.setCpnNbr(43739);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-13, 1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(13)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 14;

        xtraCardActiveCoupon.setXtraCardNbr(98158335);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(41551);
        xtraCardActiveCoupon.setCpnNbr(72371);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-13, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(13)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        /*  I am an Xtra Card member and redeemed 20% non carepass coupon within 14 days
         *
         */
        xtraCard.setXtraCardNbr(98158336);
        xtraCard.setCustId(80069);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80069);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80069);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80069);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80069);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 15;

        xtraCardActiveCoupon.setXtraCardNbr(98158336);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(39853);
        xtraCardActiveCoupon.setCpnNbr(58150);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-12, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(12)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(20.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*  I am an Xtra Card member and redeemed $5 EB 10 days before
         *
         */
        xtraCard.setXtraCardNbr(98158337);
        xtraCard.setCustId(80070);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80070);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80070);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80070);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80070);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 16;

        xtraCardActiveCoupon.setXtraCardNbr(98158337);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(41624);
        xtraCardActiveCoupon.setCpnNbr(73239);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-10, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(5.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*  I am an Xtra Card member and made one $3 coupon transaction online and $2 coupon in store yesterday
         *
         */
        xtraCard.setXtraCardNbr(98158338);
        xtraCard.setCustId(80071);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80071);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80071);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80071);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80071);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 17;

        xtraCardActiveCoupon.setXtraCardNbr(98158338);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(42342);
        xtraCardActiveCoupon.setCpnNbr(73891);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, 1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardActiveCoupon.setRedeemStoreNbr(590);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 18;

        xtraCardActiveCoupon.setXtraCardNbr(98158338);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardActiveCoupon.setRedeemStoreNbr(2695);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*  I am an Xtra Card member and made one $3 coupon transaction online and $2 coupon in store today
         *
         */
        xtraCard.setXtraCardNbr(98158339);
        xtraCard.setCustId(80072);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80072);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80072);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80072);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80072);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 19;

        xtraCardActiveCoupon.setXtraCardNbr(98158339);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(42342);
        xtraCardActiveCoupon.setCpnNbr(73891);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(590);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 20;

        xtraCardActiveCoupon.setXtraCardNbr(98158339);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(2695);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*  I am an Xtra Card member have request for XX limit  transactions (Check for response is sorted in descending order by date
         *
         */
        xtraCard.setXtraCardNbr(98158340);
        xtraCard.setCustId(80073);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80073);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80073);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80073);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80073);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 21;

        xtraCardActiveCoupon.setXtraCardNbr(98158340);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(42342);
        xtraCardActiveCoupon.setCpnNbr(73891);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-1, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 22;

        xtraCardActiveCoupon.setXtraCardNbr(98158340);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(15413);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42052);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-2, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 23;

        xtraCardActiveCoupon.setXtraCardNbr(98158340);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
        xtraCardActiveCoupon.setCmpgnId(42342);
        xtraCardActiveCoupon.setCpnNbr(73891);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-3, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(2.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 24;

        xtraCardActiveCoupon.setXtraCardNbr(98158340);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-4, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        /*  Check for offset & limit for service response for first call vs subsequent calls
         *
         */
        xtraCard.setXtraCardNbr(98158341);
        xtraCard.setCustId(80074);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80074);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80074);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80074);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80074);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 25;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 9));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 26;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 8));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 27;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 7));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 28;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 6));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 29;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 5));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 30;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(15413);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42052);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 4));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 31;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 3));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 32;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 2));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 33;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 34;

        xtraCardActiveCoupon.setXtraCardNbr(98158341);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(20374);
        xtraCardActiveCoupon.setCmpgnId(39199);
        xtraCardActiveCoupon.setCpnNbr(45671);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(0, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(0)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(3.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*  Check if limit is more than number of record during first calls vs next calls
         *
         */
        xtraCard.setXtraCardNbr(98158342);
        xtraCard.setCustId(80075);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80075);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80075);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80075);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80075);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 35;

        xtraCardActiveCoupon.setXtraCardNbr(98158342);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(15413);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42052);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-5, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 36;

        xtraCardActiveCoupon.setXtraCardNbr(98158342);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(15413);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42052);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-5, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 37;

        xtraCardActiveCoupon.setXtraCardNbr(98158342);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(15413);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42052);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-5, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 38;

        xtraCardActiveCoupon.setXtraCardNbr(98158342);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(15413);
        xtraCardActiveCoupon.setCmpgnId(41838);
        xtraCardActiveCoupon.setCpnNbr(42052);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-5, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(1.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        /*  I am an Xtra Card member and redeemed three $ 10 coupons in single transaction 14 days before
         *
         */
        xtraCard.setXtraCardNbr(98158343);
        xtraCard.setCustId(80076);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80076);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80076);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80076);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80076);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 39;

        xtraCardActiveCoupon.setXtraCardNbr(98158343);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(2587);
        xtraCardActiveCoupon.setCmpgnId(49227);
        xtraCardActiveCoupon.setCpnNbr(47630);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-15, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(10.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 40;

        xtraCardActiveCoupon.setXtraCardNbr(98158343);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(2587);
        xtraCardActiveCoupon.setCmpgnId(49227);
        xtraCardActiveCoupon.setCpnNbr(47630);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-15, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(10.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cpnSeqNbr = xtraCardDao.selectMaxXtraCardActiveCoupon(xtraCardActiveCoupon);
        nextCpnSeqNbr = cpnSeqNbr + 41;

        xtraCardActiveCoupon.setXtraCardNbr(98158343);
        xtraCardActiveCoupon.setCpnseqNbr(nextCpnSeqNbr);
//        xtraCardActiveCoupon.setCmpgnId(2587);
        xtraCardActiveCoupon.setCmpgnId(49227);
        xtraCardActiveCoupon.setCpnNbr(47630);
        xtraCardActiveCoupon.setCmpgnCpnSeqNbr(null);
        xtraCardActiveCoupon.setCpnSrcCd("S");
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setRegRetlAmt(null);
        xtraCardActiveCoupon.setEmailCmpgnId(null);
        xtraCardActiveCoupon.setReIssueNbr(null);
        xtraCardActiveCoupon.setStoreSeenNbr(null);
        xtraCardActiveCoupon.setStoreSeenTs(null);
        xtraCardActiveCoupon.setViewActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setViewActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setViewActlDestCd("W");
        xtraCardActiveCoupon.setViewActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setViewActlStoreNbr(90046);
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setPrntActlTswtz(null);
        xtraCardActiveCoupon.setPrntActlTswtzSetDt(null);
        xtraCardActiveCoupon.setPrntDestCd(null);
        xtraCardActiveCoupon.setPrntActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setPrntPriorityNbr(null);
        xtraCardActiveCoupon.setPrntActlReferByCd(null);
        xtraCardActiveCoupon.setPrntStoreNbr(null);
        xtraCardActiveCoupon.setLoadActlTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardActiveCoupon.setLoadActlDestCd(null);
        xtraCardActiveCoupon.setLoadActlDestSubSrcCd(null);
        xtraCardActiveCoupon.setLoadActlReferByCd(null);
        xtraCardActiveCoupon.setLoadActlStoreNbr(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrLoadNotifStatusTs(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemStartTswtz(dateUtil.setTswtzHour(-30, 0));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(300));
        xtraCardActiveCoupon.setRedeemActlTswtz(dateUtil.setTswtzHour(-15, 0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardActiveCoupon.setRedeemStoreNbr(null);
        xtraCardActiveCoupon.setRedeemActlAmt(10.0);
        xtraCardActiveCoupon.setRedeemPrgCd(null);
        xtraCardActiveCoupon.setRedeemMatchCd(null);
        xtraCardActiveCoupon.setReprntCd(null);
        xtraCardActiveCoupon.setRedeemActlXtraCardNbr(null);
        xtraCardActiveCoupon.setRedeemOvrdRsnNbr(null);
        xtraCardActiveCoupon.setRedeemActlCashierNbr(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifCspStatusCd(null);
        xtraCardActiveCoupon.setMfrRedeemNotifStatusTs(null);
        xtraCardDao.createXtraCardActiveCoupon(xtraCardActiveCoupon);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*  I am an Xtra card member and didnt redeem any coupon in last 14 days
         *
         */
        xtraCard.setXtraCardNbr(98158344);
        xtraCard.setCustId(80077);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80077);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80077);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80077);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80077);
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
        xtraHotCard.setXtraCardNbr(98158346);
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
    public void deleteRecentlyRedeemedHistoryTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158327, 98158328, 98158329, 98158330, 98158331, 98158332, 98158333, 98158334, 98158335, 98158336, 98158337, 98158338, 98158339, 98158340, 98158341, 98158342, 98158343, 98158344, 98158345, 98158346);
        List<Integer> custIdList = Arrays.asList(80060, 80061, 80062, 80063, 80064, 80065, 80066, 80067, 80068, 80069, 80070, 80071, 80072, 80073, 80074, 80075, 80076, 80077, 80078, 80079);
        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}
