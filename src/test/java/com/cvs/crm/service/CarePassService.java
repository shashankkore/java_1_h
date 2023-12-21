package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.data.CarepassMemberStatusHist;
import com.cvs.crm.model.data.CarepassMemberSmry;
import com.cvs.crm.model.data.CarepassEnrollFormHist;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.util.CarePassDateUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Data
public class CarePassService {

    private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

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
    XtraCardActiveCoupon xtraCardActiveCoupon;

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
    CarepassMemberStatusHist carepassMemberStatusHist;

    @Autowired
    CarePassDao carePassDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    DashBoardRequest dashBoardRequest = new DashBoardRequest();


    public void viewCarePass(DashBoardRequest dashBoardRequest) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
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
     * @param
     * @throws InterruptedException
     */
    public void createCarePassTestData() throws ParseException, InterruptedException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = new Date();
        String dateCurrent = simpleDateFormat.format(date);

        String patternTs = "dd-MMM-yy HH.MM.SS aaa";
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

        /*  I recently enrolled in CVS carepass program under monthly renewal membership and have $10 reward available to redeem in next 5 days
         *
         */
        String cardTyp = "0002";
        int cardNum1 = 98158307;
        xtraCard.setXtraCardNbr(cardNum1);
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

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum1);

        carepassMemberSmry.setXtrCardNbr(cardNum1);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-24));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(5)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(5)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(5)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("E");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum1);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-24));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum1);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-24));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum1);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-24)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(5));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(cardNum1);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-24)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*
         * I recently enrolled in CVS carepass under yearly renewal membership  and have $10 reward available for redeem in next 6 days
         */
        int cardNum2 = 98158308;
        xtraCard.setXtraCardNbr(cardNum2);
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

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum2);

        carepassMemberSmry.setXtrCardNbr(cardNum2);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-23));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(341)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(6)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(6)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("E");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum2);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-23));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum2);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-23));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum2);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-23)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(6));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(cardNum2);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-23)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*
         * I am recently enrolled in CVS carepass program under monthly renewal membership and I have redeemed $10 reward today , next monthly renewable due in next 16 days
         */
        int cardNum3 = 98158309;
        xtraCard.setXtraCardNbr(cardNum3);
        xtraCard.setCustId(80042);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80042);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80042);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80042);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80042);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum3);

        carepassMemberSmry.setXtrCardNbr(cardNum3);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-13));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(16)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(16)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(16)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("E");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum3);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-13));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum3);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-13));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum3);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-13)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(16));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(1)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum3);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-13)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(1)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        /*
         * I am recently enrolled in CVS carepass program under yearly renewal membership and I have redeemed $10 reward today, next yearly renewable due in next 8 days
         */
        int cardNum4 = 98158310;
        xtraCard.setXtraCardNbr(cardNum4);
        xtraCard.setCustId(80043);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80043);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80043);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80043);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80043);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum4);

        carepassMemberSmry.setXtrCardNbr(cardNum4);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-21));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(8)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(8)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(8)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("E");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum4);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-21));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum4);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-21));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum4);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-21)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(8));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(1)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum4);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-21)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(1)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*
         * I cancelled my CVS carepass membership today and I have $10 reward available for redeem in next 30 days
         */
        int cardNum5 = 98158311;
        xtraCard.setXtraCardNbr(cardNum5);
        xtraCard.setCustId(80044);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80044);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80044);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80044);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80044);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum5);

        carepassMemberSmry.setXtrCardNbr(cardNum5);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(30)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(30)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(30)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum5);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum5);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        carepassEnrollFormHist.setXtraCardNbr(cardNum5);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum5);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum5);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(30));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum5);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        /*
         * I have initiated CVS carepass membership cancellation today under monthly membership and have  $10 reward to redeem in next 30 days
         */
        int cardNum6 = 98158312;
        xtraCard.setXtraCardNbr(cardNum6);
        xtraCard.setCustId(80045);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80045);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80045);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80045);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80045);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum6);

        carepassMemberSmry.setXtrCardNbr(cardNum6);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(30)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(30)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(30)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum6);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum6);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        carepassEnrollFormHist.setXtraCardNbr(cardNum6);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum6);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum6);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(30));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum6);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*
         * I have initiated CVS carepass membership cancellation today under yearly membership and have  $10 reward to redeem in next 25 days
         */
        int cardNum7 = 98158313;
        xtraCard.setXtraCardNbr(cardNum7);
        xtraCard.setCustId(80046);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80046);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80046);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80046);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80046);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum7);

        carepassMemberSmry.setXtrCardNbr(cardNum7);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-4));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(360)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(25)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(25)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum7);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-4));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum7);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-4));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        carepassEnrollFormHist.setXtraCardNbr(cardNum7);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum7);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum7);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-4)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(25));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum7);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-4)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*
         * I cancelled my CVS carepass membership yesterday and redeemed $10 monthly reward today
         */
        int cardNum8 = 98158314;
        xtraCard.setXtraCardNbr(cardNum8);
        xtraCard.setCustId(80047);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80047);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80047);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80047);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80047);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum8);

        carepassMemberSmry.setXtrCardNbr(cardNum8);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-10));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(19)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(19)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(19)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum8);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-10));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum8);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-10));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(cardNum8);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(-1));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum8);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(-1));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);


        xtraCardActiveCoupon.setXtraCardNbr(cardNum8);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-10)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(19));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(0)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum8);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-10)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(0)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);



        /*
         * My monthly CVS carepass membership payment did not go through yesterday and no $10 reward is available
         */
        int cardNum9 = 98158315;
        xtraCard.setXtraCardNbr(cardNum9);
        xtraCard.setCustId(80048);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80048);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80048);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80048);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80048);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum9);

        carepassMemberSmry.setXtrCardNbr(cardNum9);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-15));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(14)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(14)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(14)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("H");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum9);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-15));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum9);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-15));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        carepassEnrollFormHist.setXtraCardNbr(cardNum9);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(-1));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum9);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(-1));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("H");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);


        xtraCardActiveCoupon.setXtraCardNbr(cardNum9);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-15)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(14));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-10));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(10)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum9);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-15)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-10));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(10)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*
         * I have initiated CVS carepass membership cancellation today under monthly membership and no $10 reward is available
         */
        int cardNum10 = 98158316;
        xtraCard.setXtraCardNbr(cardNum10);
        xtraCard.setCustId(80049);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80049);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80049);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80049);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80049);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum10);

        carepassMemberSmry.setXtrCardNbr(cardNum10);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-9));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(20)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(20)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(20)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum10);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-9));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum10);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-9));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        carepassEnrollFormHist.setXtraCardNbr(cardNum10);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum10);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum10);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-9)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(20));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(0)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum10);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-9)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(0)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        /*
         * I have initiated CVS carepass membership cancellation today under yearly membership and no $10 reward is available
         */
        int cardNum11 = 98158317;
        xtraCard.setXtraCardNbr(cardNum11);
        xtraCard.setCustId(80050);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80050);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80050);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80050);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80050);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum11);

        carepassMemberSmry.setXtrCardNbr(cardNum11);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(9)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(9)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(9)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum11);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum11);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(cardNum11);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum11);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassUnEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);


        xtraCardActiveCoupon.setXtraCardNbr(cardNum11);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(9));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-5));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(5)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        xtraCardActiveCoupon.setXtraCardNbr(cardNum11);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(330));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-5));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(5)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*
         * I cancelled my CVS carepass membership last year
         */
        int cardNum12 = 98158318;
        xtraCard.setXtraCardNbr(cardNum12);
        xtraCard.setCustId(80051);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80051);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80051);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80051);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80051);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carepassMemberSmry.setXtrCardNbr(cardNum12);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-400));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(35)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(35)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(35)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("U");
        carepassMemberSmry.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(cardNum12);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-400));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum12);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-400));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        carepassEnrollFormHist.setXtraCardNbr(cardNum12);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-410));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(cardNum12);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-410));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carepassMemberStatusHist.setMbrStatusRsnCd(null);
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        cacheRefreshUtil.refresCacheusingXtraParms();
        cacheRefreshUtil.refresCacheforCmpgnDefns();
    }


    /**
     * Delete Test Data for Care Pass Scenario
     */
    public void deleteCarePassTestData() {
	  /*
	    Purge All Test Cards
	  */
        List<Integer> xtraCardNbrList = Arrays.asList(98158307, 98158308, 98158309, 98158310, 98158311, 98158312, 98158313, 98158314, 98158315, 98158316, 98158317, 98158318);
        List<Integer> custIdList = Arrays.asList(80040, 80041, 80042, 80043, 80044, 80045, 80046, 80047, 80048, 80049, 80050, 80051);

        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        carePassDao.deleteCarePass(xtraCardNbrList);
    }

    
    
    
}