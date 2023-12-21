package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.model.data.CampaignRewardThreshold;
import com.cvs.crm.model.request.RewardTransactionRequest;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.XtraCardRewardPHRTxnDtl;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.data.XtraCardSegment;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.HrMemberSmry;
import com.cvs.crm.model.data.HrMemberEnroll;
import com.cvs.crm.model.data.HrMemberProfile;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.HRDao;
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
public class PhrTransactionHistoryService {

    private Response serviceResponse;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CampaignRewardThreshold campaignRewardThreshold;

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
    HrMemberSmry hrMemberSmry;

    @Autowired
    HrMemberEnroll hrMemberEnroll;

    @Autowired
    HrMemberProfile hrMemberProfile;

    @Autowired
    XtraCardRewardPHRTxnDtl xtraCardRewardPHRTxnDtl;

    @Autowired
    Campaign campaign;

    @Autowired
    CampaignCoupon campaignCoupon;

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

    @Autowired
    HRDao hRDao;


    public void viewPhrTransactionHistory(RewardTransactionRequest rewardTransactionRequest) {

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
        if (rewardTransactionRequest.getLimit().equals(10)) {
            limit = 10;
        }
        if (rewardTransactionRequest.getLimit().equals(20)) {
            limit = 20;
        }
        requestSpecBuilder.setBaseUri(serviceConfig.getRewardHistoryUrl())
                .setBasePath("api/v1.1/{search_card_type},{search_card_nbr}/rewards/pharmacyHealthRewards/history")
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
     * Create Test Data For PHR Transaction History Scenario
     */
    public void createPhrTransactionHistoryTestData() throws ParseException {

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


        /*  I am an Xtra Card member and redeemed $3 coupons yesterday
         *
         */
        xtraCard.setXtraCardNbr(98158363);
        xtraCard.setCustId(80096);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80096);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80096);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80096);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80096);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018511);
        hrMemberSmry.setXtraCardNbr(98158363);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-4));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-4));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018511);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-4));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-4));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018511);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-4));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018511);
        hrMemberEnroll.setXtraCardNbr(98158363);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158363);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158363);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018511);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(423);
        xtraCardRewardPHRTxnDtl.setPtsQty(10);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        /* I recently enrolled in PHR as targeted customer and made single 90-day prescription,30-day prescription, Online prescription access, Flu Shot and Immunization yesterday
         *
         */
        xtraCard.setXtraCardNbr(98158364);
        xtraCard.setCustId(80097);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80097);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80097);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80097);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80097);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018611);
        hrMemberSmry.setXtraCardNbr(98158364);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-2));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-2));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018611);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-2));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-2));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018611);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-2));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018611);
        hrMemberEnroll.setXtraCardNbr(98158364);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158364);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100016);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(10);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158364);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018611);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(626);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158364);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018611);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(623);
        xtraCardRewardPHRTxnDtl.setPtsQty(10);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158364);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018611);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(622);
        xtraCardRewardPHRTxnDtl.setPtsQty(2);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158364);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018611);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(619);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158364);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018611);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(624);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardSegment.setXtraCardNbr(98158364);
        xtraCardSegment.setXtraCardSegNbr(192);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);




        /* I am a non-targeted customer and made 90-day prescription 2 days ago,30-day prescription every month, Online prescription access last month, Flu Shot in 20 days before and Immunization 10 days before
         *
         */
        xtraCard.setXtraCardNbr(98158365);
        xtraCard.setCustId(80098);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80098);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80098);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80098);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80098);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018711);
        hrMemberSmry.setXtraCardNbr(98158365);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-300));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-300));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018711);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018711);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-300));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018711);
        hrMemberEnroll.setXtraCardNbr(98158365);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(45)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(422);
        xtraCardRewardPHRTxnDtl.setPtsQty(5);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(423);
        xtraCardRewardPHRTxnDtl.setPtsQty(10);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(35)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(65)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(95)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(125)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(155)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(185)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158365);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018711);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(215)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);



        /* I am a non-targeted customer with 1 PHR dependent who recently enrolled and both of us made single Prescription fill yesterday
         *
         */
        xtraCard.setXtraCardNbr(98158366);
        xtraCard.setCustId(80099);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80099);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80099);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80099);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80099);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018811);
        hrMemberSmry.setXtraCardNbr(98158366);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018811);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018811);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018811);
        hrMemberEnroll.setXtraCardNbr(98158366);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80018812);
        hrMemberSmry.setXtraCardNbr(98158366);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018812);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018812);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-3));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018812);
        hrMemberEnroll.setXtraCardNbr(98158366);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158366);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018811);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158366);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018812);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);




        /*I and my wife are non-targeted customers with 2 PHR dependents and I made one 90-day prescription 2 days ago, my wife made 30-day prescription yesterday and my kids had Immunization last month on different days
         *
         */
        xtraCard.setXtraCardNbr(98158367);
        xtraCard.setCustId(80100);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80100);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80100);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80100);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80100);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018911);
        hrMemberSmry.setXtraCardNbr(98158367);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018911);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018911);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018911);
        hrMemberEnroll.setXtraCardNbr(98158367);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80018912);
        hrMemberSmry.setXtraCardNbr(98158367);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018912);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018912);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018911);
        hrMemberEnroll.setXtraCardNbr(98158367);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80018913);
        hrMemberSmry.setXtraCardNbr(98158367);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018913);
        hrMemberProfile.setFirstName("Sam");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018913);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018913);
        hrMemberEnroll.setXtraCardNbr(98158367);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80018914);
        hrMemberSmry.setXtraCardNbr(98158367);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80018914);
        hrMemberProfile.setFirstName("Alisha");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-300));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80018914);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-100));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018914);
        hrMemberEnroll.setXtraCardNbr(98158367);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158367);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(35)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158367);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018911);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158367);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018912);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158367);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018913);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(35)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158367);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80018914);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(45)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        /*I am targeted customer with 2 PHR dependents who recently enrolled and made multiple same Prescription fills on same day and earned Extra bucks
         *
         */
        xtraCard.setXtraCardNbr(98158368);
        xtraCard.setCustId(80101);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80101);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80101);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80101);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80101);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019011);
        hrMemberSmry.setXtraCardNbr(98158368);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019011);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019011);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019011);
        hrMemberEnroll.setXtraCardNbr(98158368);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019012);
        hrMemberSmry.setXtraCardNbr(98158368);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019012);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019012);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019012);
        hrMemberEnroll.setXtraCardNbr(98158368);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019013);
        hrMemberSmry.setXtraCardNbr(98158368);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019013);
        hrMemberProfile.setFirstName("Sam");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019013);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019013);
        hrMemberEnroll.setXtraCardNbr(98158368);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019014);
        hrMemberSmry.setXtraCardNbr(98158368);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019014);
        hrMemberProfile.setFirstName("Alisha");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-7));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019014);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-7));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019014);
        hrMemberEnroll.setXtraCardNbr(98158368);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158368);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100016);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(6);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158368);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019011);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(624);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158368);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019012);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(624);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158368);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019013);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(624);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158368);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019014);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44497);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(624);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardSegment.setXtraCardNbr(98158368);
        xtraCardSegment.setXtraCardSegNbr(192);
        xtraCardSegment.setCtlGrpCd("N");
        xtraCardDao.createXtraCardSegment(xtraCardSegment);



        /* I am a non-targeted customer and made 90-day prescription 2 days ago,30-day prescription every month, Online prescription access last month, Flu Shot in 20 days before and Immunization 10 days before
         *
         */
        xtraCard.setXtraCardNbr(98158369);
        xtraCard.setCustId(80102);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80102);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80102);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80102);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80102);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019111);
        hrMemberSmry.setXtraCardNbr(98158369);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019111);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019111);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019111);
        hrMemberEnroll.setXtraCardNbr(98158369);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019112);
        hrMemberSmry.setXtraCardNbr(98158369);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019112);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(800189112);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019112);
        hrMemberEnroll.setXtraCardNbr(98158369);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(25);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019111);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019112);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019111);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019112);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019112);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(422);
        xtraCardRewardPHRTxnDtl.setPtsQty(5);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019111);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(422);
        xtraCardRewardPHRTxnDtl.setPtsQty(5);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019111);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019112);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019111);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(423);
        xtraCardRewardPHRTxnDtl.setPtsQty(10);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158369);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019112);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(423);
        xtraCardRewardPHRTxnDtl.setPtsQty(10);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);




        /* I have one of the member filled 100(30 days prescriptions) from multiple transactions on multiple days and now I get 100 points
         *
         */
        xtraCard.setXtraCardNbr(98158370);
        xtraCard.setCustId(80103);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80103);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80103);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80103);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80103);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019211);
        hrMemberSmry.setXtraCardNbr(98158370);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019211);
        hrMemberProfile.setFirstName("Sam");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019211);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019211);
        hrMemberEnroll.setXtraCardNbr(98158370);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(25);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158370);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019211);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);



        /* I and my dependents are non-targeted customers and made 50 same transactions across 10 days and want to see with offset 5 and limit 5
         *
         */
        xtraCard.setXtraCardNbr(98158372);
        xtraCard.setCustId(80105);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80105);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80105);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80105);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80105);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019411);
        hrMemberSmry.setXtraCardNbr(98158372);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019411);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019411);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019411);
        hrMemberEnroll.setXtraCardNbr(98158372);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019412);
        hrMemberSmry.setXtraCardNbr(98158372);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019412);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019412);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019412);
        hrMemberEnroll.setXtraCardNbr(98158372);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019413);
        hrMemberSmry.setXtraCardNbr(98158372);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019413);
        hrMemberProfile.setFirstName("Sam");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019413);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019413);
        hrMemberEnroll.setXtraCardNbr(98158372);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019414);
        hrMemberSmry.setXtraCardNbr(98158372);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019414);
        hrMemberProfile.setFirstName("Alisha");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019414);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019414);
        hrMemberEnroll.setXtraCardNbr(98158372);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(6)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(6)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(6)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(6)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(6)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(6)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019413);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019414);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(9)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(9)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(9)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(9)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(9)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019411);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(9)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158372);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019412);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);




        /* I and my dependents are non-targeted customers and made 20 different transactions across 20 days and want to see with offset 0 and limit 10
         *
         */
        xtraCard.setXtraCardNbr(98158371);
        xtraCard.setCustId(80104);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80104);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80104);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80104);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80104);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019311);
        hrMemberSmry.setXtraCardNbr(98158371);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019311);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019311);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019311);
        hrMemberEnroll.setXtraCardNbr(98158371);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019312);
        hrMemberSmry.setXtraCardNbr(98158371);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019312);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019312);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019312);
        hrMemberEnroll.setXtraCardNbr(98158371);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019313);
        hrMemberSmry.setXtraCardNbr(98158371);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019313);
        hrMemberProfile.setFirstName("Sam");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019313);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019313);
        hrMemberEnroll.setXtraCardNbr(98158371);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019314);
        hrMemberSmry.setXtraCardNbr(98158371);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019314);
        hrMemberProfile.setFirstName("Alisha");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019314);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019314);
        hrMemberEnroll.setXtraCardNbr(98158371);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019311);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019312);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019313);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019314);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019311);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(5)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019312);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(6)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019313);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(7)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019314);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(8)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019311);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(9)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019312);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(10)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(11)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019313);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(11)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019314);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(12)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(13)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019311);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(13)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019312);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(14)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019313);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(15)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(16)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019314);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(16)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019311);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(17)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019312);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(18)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(419);
        xtraCardRewardPHRTxnDtl.setPtsQty(1);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(19)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019313);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(19)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158371);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019314);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(20)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);



        /* I and my dependents are non-targeted customers and one of us reached the CAP
         *
         */
        xtraCard.setXtraCardNbr(98158373);
        xtraCard.setCustId(80106);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80106);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80106);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80106);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80106);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019511);
        hrMemberSmry.setXtraCardNbr(98158373);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019511);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019511);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019511);
        hrMemberEnroll.setXtraCardNbr(98158373);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019512);
        hrMemberSmry.setXtraCardNbr(98158373);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019512);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019512);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019512);
        hrMemberEnroll.setXtraCardNbr(98158373);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019513);
        hrMemberSmry.setXtraCardNbr(98158373);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019513);
        hrMemberProfile.setFirstName("Sam");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019513);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019513);
        hrMemberEnroll.setXtraCardNbr(98158373);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019514);
        hrMemberSmry.setXtraCardNbr(98158373);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019514);
        hrMemberProfile.setFirstName("Alisha");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019514);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019414);
        hrMemberEnroll.setXtraCardNbr(98158373);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(15);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019511);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(36);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(10);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019512);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(24);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019513);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(12);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(50);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158373);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019514);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(102);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        /* I and my dependents are non-targeted customers and one of us reached the CAP
         *
         */
        xtraCard.setXtraCardNbr(98158374);
        xtraCard.setCustId(80107);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80107);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80107);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80107);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80107);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019611);
        hrMemberSmry.setXtraCardNbr(98158374);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019611);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019611);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019611);
        hrMemberEnroll.setXtraCardNbr(98158374);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019612);
        hrMemberSmry.setXtraCardNbr(98158374);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019612);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019612);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019612);
        hrMemberEnroll.setXtraCardNbr(98158374);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019613);
        hrMemberSmry.setXtraCardNbr(98158374);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019613);
        hrMemberProfile.setFirstName("Sam");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019613);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019613);
        hrMemberEnroll.setXtraCardNbr(98158374);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        hrMemberSmry.setEphLinkId(80019614);
        hrMemberSmry.setXtraCardNbr(98158374);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019614);
        hrMemberProfile.setFirstName("Alisha");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019614);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019614);
        hrMemberEnroll.setXtraCardNbr(98158374);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(50);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019611);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(102);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(50);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019612);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(102);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(50);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019613);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(102);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(50);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158374);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019614);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(102);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        /* I am a non-targeted customer with PHR dependent who recently enrolled and made 0 transactions
         *
         */
        xtraCard.setXtraCardNbr(98158375);
        xtraCard.setCustId(80108);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80108);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80108);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80108);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80108);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019711);
        hrMemberSmry.setXtraCardNbr(98158375);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberProfile.setEphLinkId(80019711);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberEnroll.setEphLinkId(80019711);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019711);
        hrMemberEnroll.setXtraCardNbr(98158375);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberSmry.setEphLinkId(80019712);
        hrMemberSmry.setXtraCardNbr(98158375);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberProfile.setEphLinkId(80019712);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberEnroll.setEphLinkId(80019712);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019712);
        hrMemberEnroll.setXtraCardNbr(98158375);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);


        /* I have unenrolled from PHR yesterday and want to see my transactions history till yesterday
         *
         */
        xtraCard.setXtraCardNbr(98158376);
        xtraCard.setCustId(80109);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80109);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80109);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80109);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80109);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80019811);
        hrMemberSmry.setXtraCardNbr(98158376);
        hrMemberSmry.setEnrollStatusCd("U");
        hrMemberSmry.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(dateUtil.dealEndDate(330)));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);


        hrMemberProfile.setEphLinkId(80019811);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberProfile.setLastUpdateDttm(carePassDateUtil.carePassEnrollTswtz(-30));
        hRDao.createHrMemberProfile(hrMemberProfile);


        hrMemberEnroll.setEphLinkId(80019811);
        hrMemberEnroll.setEnrollStatusTs(carePassDateUtil.carePassEnrollTswtz(-30));
        hrMemberEnroll.setEnrollStatusCd("U");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80019811);
        hrMemberEnroll.setXtraCardNbr(98158376);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158376);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019811);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(1)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(424);
        xtraCardRewardPHRTxnDtl.setPtsQty(3);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158376);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158376);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019811);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(2)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(423);
        xtraCardRewardPHRTxnDtl.setPtsQty(10);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158376);
        xtraCardRewardPHRTxnDtl.setEphLinkId(null);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(100017);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("RW");
        xtraCardRewardPHRTxnDtl.setEventId(null);
        xtraCardRewardPHRTxnDtl.setPtsQty(null);
        xtraCardRewardPHRTxnDtl.setTxnAmt(5);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158376);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019811);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(3)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(426);
        xtraCardRewardPHRTxnDtl.setPtsQty(6);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);

        xtraCardRewardPHRTxnDtl.setXtraCardNbr(98158376);
        xtraCardRewardPHRTxnDtl.setEphLinkId(80019811);
        xtraCardRewardPHRTxnDtl.setTxnDttm(simpleDateFormat.parse(dateUtil.dealEndDateMinus(4)));
        xtraCardRewardPHRTxnDtl.setCmpgnId(44496);
        xtraCardRewardPHRTxnDtl.setCpnNbr(null);
        xtraCardRewardPHRTxnDtl.setTxnTypCd("TR");
        xtraCardRewardPHRTxnDtl.setEventId(422);
        xtraCardRewardPHRTxnDtl.setPtsQty(5);
        xtraCardRewardPHRTxnDtl.setTxnAmt(null);
        xtraCardRewardPHRTxnDtl.setCreateDttm(null);
        xtraCardDao.createXtraCardRewardPhrTxnDtl(xtraCardRewardPHRTxnDtl);


        /* I have never enrolled into PHR and I haven't made any transactions
         *
         */
        xtraCard.setXtraCardNbr(98158377);
        xtraCard.setCustId(80110);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80110);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80110);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80110);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80110);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

/*
        Default Campaign
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
        campaign.setEndDt(simpleDateFormat.parse("2030-12-31"));
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


        Pilot campaign
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
        campaign.setEndDt(simpleDateFormat.parse("2030-12-31"));
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


        Default campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(169232);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(169233);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(169234);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(169235);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-5 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(5);
        campaignCoupon.setCpnNbr(169236);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-6 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(6);
        campaignCoupon.setCpnNbr(169331);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-06"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-7 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(7);
        campaignCoupon.setCpnNbr(169332);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-06"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-8 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(8);
        campaignCoupon.setCpnNbr(170109);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-16"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-9 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(9);
        campaignCoupon.setCpnNbr(169937);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-01-11"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-10 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(10);
        campaignCoupon.setCpnNbr(172875);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-02-02"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-11 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(11);
        campaignCoupon.setCpnNbr(172876);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-02-02"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

Default campaign Coupon-14 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(14);
        campaignCoupon.setCpnNbr(184208);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-03-20"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

        pilot campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(182409);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-03-13"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

pilot campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(185392);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-03-30"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-09-02"));
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

pilot campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(191667);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-06-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-22"));
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

pilot campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(191668);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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


pilot campaign Coupon-5 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(5);
        campaignCoupon.setCpnNbr(191669);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-6 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(6);
        campaignCoupon.setCpnNbr(191670);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-7 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(7);
        campaignCoupon.setCpnNbr(191671);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-8 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(8);
        campaignCoupon.setCpnNbr(191672);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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


pilot campaign Coupon-9 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(9);
        campaignCoupon.setCpnNbr(191673);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-10 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(10);
        campaignCoupon.setCpnNbr(191674);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-12 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(12);
        campaignCoupon.setCpnNbr(191675);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-13 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(13);
        campaignCoupon.setCpnNbr(192430);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-06-09"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-14 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(14);
        campaignCoupon.setCpnNbr(191676);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-06-09"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-15 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(15);
        campaignCoupon.setCpnNbr(191677);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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

pilot campaign Coupon-26 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(26);
        campaignCoupon.setCpnNbr(191678);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2020-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2030-02-01"));
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



Default campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(59726);
        campaignRewardThreshold.setThrshldLimNbr(10);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("Q");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);

        Pilot campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(64355);
        campaignRewardThreshold.setThrshldLimNbr(4);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("Q");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);

 */
    }


    /**
     * Delete Test Data for PHR Transaction History Scenario
     */
    public void deletePHRTransactionHistoryTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158363, 98158364, 98158365, 98158366, 98158367, 98158368, 98158369, 98158370, 98158371, 98158372, 98158373, 98158374, 98158375, 98158376, 98158377);
        List<Integer> custIdList = Arrays.asList(80096, 80097, 80098, 80099, 80100, 80101, 80102, 80103, 80104, 80105, 80106, 80107, 80108, 80109, 80110);
        List<Integer> ephLinkIdList = Arrays.asList(80018511, 80018611, 80018711, 80018811, 80018812, 80018911, 80018912, 80018913, 80018914, 80019011, 80019012, 80019013, 80019014, 80019111, 80019112, 80019211, 80019311, 80019312, 80019313, 80019314, 80019411, 80019412, 80019413, 80019414, 80019511, 80019512, 80019513, 80019514, 80019611, 80019612, 80019613, 80019614, 80019711, 80019712, 80019811);
        customerDao.deleteCustomers(custIdList);
        hRDao.deleteHRRecords(ephLinkIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }
}
