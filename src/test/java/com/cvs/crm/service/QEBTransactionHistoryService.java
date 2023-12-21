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

import java.io.IOException;
import java.util.Date;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import static io.restassured.RestAssured.given;

@Service
@Data
public class QEBTransactionHistoryService {

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
    XtraCardRewardQEBTxnDtl xtraCardRewardQEBTxnDtl;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    public void viewQEBTransactionHistory(RewardTransactionRequest rewardTransactionRequest) {

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
        if (rewardTransactionRequest.getOffset().equals(10)) {
            offset = 10;
        }
        if (rewardTransactionRequest.getOffset().equals(20)) {
            offset = 20;
        }
        if (rewardTransactionRequest.getLimit().equals(10)) {
            limit = 10;
        }
        requestSpecBuilder.setBaseUri(serviceConfig.getRewardHistoryUrl())
                .setBasePath("api/v1.1/{search_card_type},{search_card_nbr}/rewards/quarterlyExtraBucks/history")
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
     * Create Test Data For QEB Transaction History Scenario
     */
    public void createQEBTransHistoryTestData() throws ParseException, IOException {
        //  readFromExcel();

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

        campaign.setCmpgnId(43916);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        campaign.setMktgPrgCd("Q");
        Integer cmpgn =  campaignDao.checkQEBCampaign(campaign);
       // for (int i = 0; i < cmpgns.size(); i++) {
         //   cmpgn = cmpgns.get(i);
            campaign.setCmpgnId(cmpgn);
            campaign.setEndDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireDate(-365)));
            campaignDao.updateCampaign(campaign);
      //  }

        /*  Iam an xtracard member and made single transaction in a day of total $20 purchase of qualified QEB items in previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158378);
        xtraCard.setCustId(80111);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80111);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80111);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80111);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80111);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158378);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(60)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(20);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        /*  Iam an xtracard member and made multiple transactions on single day of total $40 purchase  of qualified QEB items in previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158379);
        xtraCard.setCustId(80112);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80112);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80112);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80112);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80112);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158379);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(65)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158379);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(65)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(29);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158379);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(65)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        /*  Iam an xtracard member and made multiple transactions on multiple days of total $15 purchase of qualified QEB items in previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158380);
        xtraCard.setCustId(80113);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80113);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80113);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80113);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80113);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158380);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(50)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158380);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(60)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158380);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(70)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(5);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158380);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(80)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158380);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(90)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(4);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        /*  Iam an xtracard member and earned quarterly Extra bucks for previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158381);
        xtraCard.setCustId(80114);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80114);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80114);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80114);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80114);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158381);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158381);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("RW");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158381);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(50)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(27);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158381);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(80)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        /*  Iam an xtracard member and made single transaction in a day of total $20 purchase of qualified QEB items in current quarter
         *
         */
        xtraCard.setXtraCardNbr(98158382);
        xtraCard.setCustId(80115);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80115);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80115);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80115);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80115);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158382);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(20);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        /*  Iam an xtracard member and made multiple transactions on single day of total $40 purchase  of qualified QEB items in current quarter
         *
         */
        xtraCard.setXtraCardNbr(98158383);
        xtraCard.setCustId(80116);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80116);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80116);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80116);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80116);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158383);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158383);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(29);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158383);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        /*  Iam an xtracard member and made multiple transactions on multiple days of total $35 purchase of qualified QEB items in current quarter
         *
         */
        xtraCard.setXtraCardNbr(98158384);
        xtraCard.setCustId(80117);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80117);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80117);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80117);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80117);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158384);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(13);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158384);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(7);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158384);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(5);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158384);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(6);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158384);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(4);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        /*  Iam an xtracard member and eligible for quarterly Extra bucks for current quarter
         *
         */
        xtraCard.setXtraCardNbr(98158385);
        xtraCard.setCustId(80118);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80118);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80118);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80118);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80118);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158385);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(45);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158385);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(15);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        /* Iam an xtracard member and made single transaction in a day of total $15 purchase of qualified QEB items in current and $6 purchase of qualified QEB item in previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158386);
        xtraCard.setCustId(80119);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80119);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80119);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80119);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80119);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158386);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(15);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158386);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(90)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(6);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);



        /* Iam an xtracard member and made multiple transactions in a day of total $25 purchase of qualified QEB items in current and $36 purchase  of qualified QEB item in previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158387);
        xtraCard.setCustId(80120);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80120);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80120);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80120);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80120);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158387);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158387);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158387);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(12);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158387);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(50)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(5);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158387);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(50)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(6);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158387);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(50)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);




        /* Iam an xtracard member and made multiple transactions on multiple days of total $40 purchase of qualified QEB items in current and $45 purchase of qualified QEB item in previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158388);
        xtraCard.setCustId(80121);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80121);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80121);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80121);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80121);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(8);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(12);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(25)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(10);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(48)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(5);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(50)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(6);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(55)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(9);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158388);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(60)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(25);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);




        /* Iam an xtracard member and eligible for quarterly Extra bucks for current quarter and earned quarterly Extra bucks for previous quarter
         *
         */
        xtraCard.setXtraCardNbr(98158389);
        xtraCard.setCustId(80122);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80122);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80122);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80122);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80122);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158389);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(50);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158389);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(30)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("RW");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158389);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(60)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(54001);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(150);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);



        /* Iam an xtracard member and made multiple transactions on multiple days of total $25 purchase of qualified QEB items after the quarter ends during 15 days rolling period
         *
         */
        xtraCard.setXtraCardNbr(98158390);
        xtraCard.setCustId(80123);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80123);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80123);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80123);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80123);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(31)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(31)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(32)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(32)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(33)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(33)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(34)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(35)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(35)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(36)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(37)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(38)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158390);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(38)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);



        /* Iam an xtracard member and made 20 different transactions across 20 days of qualified QEB items and want to see with offset 0 and limit 10
         *
         */
        xtraCard.setXtraCardNbr(98158391);
        xtraCard.setCustId(80124);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80124);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80124);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80124);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80124);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(13)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(14)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(16)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(17)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(18)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(19)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(21)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(22)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(23)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(24)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(25)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(25)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(26)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(26)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(27)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(28)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(29)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158391);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(29)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        /*Iam an xtracard member and made 20 different transactions across 10 days of qualified QEB items and want to see with offset 5 and limit 5
         *
         */
        xtraCard.setXtraCardNbr(98158392);
        xtraCard.setCustId(80125);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80125);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80125);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80125);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80125);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(11)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(11)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(22)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(22)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(23)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(23)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(24)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(25)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(25)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(25)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(26)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(26)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(27)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(27)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(28)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(28)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(28)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(3);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(29)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(1);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(29)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);

        xtraCardRewardQEBTxnDtl.setXtraCardNbr(98158392);
        xtraCardRewardQEBTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(29)));
        xtraCardRewardQEBTxnDtl.setCmpgnId(56264);
        xtraCardRewardQEBTxnDtl.setCpnNbr(null);
        xtraCardRewardQEBTxnDtl.setTxnTypCd("TR");
        xtraCardRewardQEBTxnDtl.setQualifiedTxnAmt(2);
        xtraCardRewardQEBTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardQebTxnDtl(xtraCardRewardQEBTxnDtl);


        //Campaign
        campaign.setCmpgnId(56264);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        campaign.setCmpgnDsc(dateUtil.cmpgnDscYear());
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt(dateUtil.recptRxtYear());
        campaign.setRecptScaleNbr(2);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(dateUtil.campaignStartDate(44)));
        campaign.setEndDt(simpleDateFormat.parse(dateUtil.campaignEndDate(45)));
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


        campaign.setCmpgnId(54001);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        campaign.setCmpgnDsc(dateUtil.cmpgnDscYearExisting());
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt(dateUtil.recptRxtYearExisting());
        campaign.setRecptScaleNbr(2);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse(dateUtil.campaignStartDate(135)));
        campaign.setEndDt(simpleDateFormat.parse(dateUtil.campaignStartDate(45)));
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
    public void deleteQEBTransHistoryTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158378, 98158379, 98158380, 98158381, 98158382, 98158383, 98158384, 98158385, 98158386, 98158387, 98158388, 98158389, 98158390, 98158391, 98158392);
        List<Integer> custIdList = Arrays.asList(80111, 80112, 80113, 80114, 80115, 80116, 80117, 80118, 80119, 80120, 80121, 80122, 80123, 80124, 80125);
        List<Integer> cmpgnIdList = Arrays.asList(68740, 65981, 54001, 56264, 44209, 41963);
        campaignDao.deleteCampaignRecords(cmpgnIdList, xtraCardNbrList);
        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}
