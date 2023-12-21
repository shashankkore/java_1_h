package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GetCustCarepassEnrollmentService {

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
    EmployeeCard employeeCard;

    @Autowired
    XtraHotCard xtraHotCard;

    @Autowired
    CarepassMemberStatusHist carepassMemberStatusHist;

    @Autowired
    CarePassDao carePassDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    public void viewGetCustCarePassEnrollment(GetCustRequest getCustRequest) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
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
                "  \"cusInfReq\": {\n" +
                "    \"xtraCard\": [\n" +
                "      \"xtraCardNbr\",\n" +
                "      \"totYtdSaveAmt\",\n" +
                "      \"cardLastScanDt\",\n" +
                "      \"lifetimesavings\",\n" +
                "      \"cardMbrDt\",\n" +
                "      \"homeStoreNbr\"\n" +
                "    ],\n" +
                "    \"xtraCare\": [\n" +
                "      \"cpns\",\n" +
                "      \"carePassCpns\"\n" +
                "    ],\n" +
                "    \"xtraCarePrefs\": [\n" +
                "      \"carePass\"\n" +
                "    ]\n" +
                "  }\n" +
                "}\n";


        requestSpecBuilder.setBaseUri(serviceConfig.getGetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                .addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonRequest).post();
    }

    /**
     * Create Test Data For Beauty Club Scenario
     *
     * @param
     * @throws InterruptedException
     */
    public void createGetCustCarepassEnrollmentTestData() throws ParseException, InterruptedException {
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

        /*  I am a carepass member enrolled under monthly program where 10$ coupon hasn’t been redeemed and want to fetch $10 and 20% coupons and status as E and benefit eligibility date as 30 days and expire date as 30 days
         *
         */
        String cardTyp = "0002";
        int cardNum1 = 98158410;

        xtraCard.setXtraCardNbr(98158410);
        xtraCard.setCustId(80144);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584109);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80144);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80144);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80144);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80144);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, cardNum1);

        carepassMemberSmry.setXtrCardNbr(98158410);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(10)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(10)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(10)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("E");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158410);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158410);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(98158410);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(10));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(98158410);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(10));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*  I am a carepass member who is an employee and enrolled under monthly program and want to fetch only $10 coupon and status as E and benefit eligibility date as 30 days and expire date as 30 days
         *
         */

        xtraCard.setXtraCardNbr(900058411);
        xtraCard.setCustId(80145);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900584116);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80145);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80145);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80145);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80145);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        employeeCard.setCustId(80145);
        employeeCard.setEmpCardNbr(80145145);
        xtraCardDao.createEmployeeCard(employeeCard);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 900058411);

        carepassMemberSmry.setXtrCardNbr(900058411);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-28));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(1)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(1)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(1)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("E");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(900058411);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-28));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(900058411);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-28));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(900058411);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-28)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(1));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(900058411);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-28)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(1));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /*  I am a carepass member enrolled under yearly program where 10$ coupon hasn’t been redeemed and want to fetch $10 and 20% coupons and status as E and benefit eligibility date as 30 days and expire date as 365 days
         *
         */

        xtraCard.setXtraCardNbr(98158412);
        xtraCard.setCustId(80146);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584123);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80146);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80146);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80146);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80146);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 98158412);

        carepassMemberSmry.setXtrCardNbr(98158412);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(364)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(29)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(29)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("E");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158412);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158412);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(98158412);
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


        xtraCardActiveCoupon.setXtraCardNbr(98158412);
        xtraCardActiveCoupon.setCmpgnId(41083);
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


        /* I am an existing CarePass member and initiated cancellation of my monthly carepass membership today where I haven’t redeemed my 10$ coupon and want to fetch 10$ and 20% coupons and status as CI and benefit_eligibility_date as future date
         *
         */

        xtraCard.setXtraCardNbr(98158413);
        xtraCard.setCustId(80147);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(20)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584130);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80147);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80147);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80147);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80147);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 98158413);

        carepassMemberSmry.setXtrCardNbr(98158413);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-25));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(10)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(10)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(10)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158413);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-25));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158413);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-25));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(98158413);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158413);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-20));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(98158413);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(10));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(98158413);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(10));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /* I am an existing CarePass member and initiated cancellation of my yearly carepass membership today where I have redeemed my 10$ coupon and want to fetch 20% coupon and status as CI and benefit_eligibility_date as future date
         *
         */

        xtraCard.setXtraCardNbr(98158414);
        xtraCard.setCustId(80148);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-324)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584147);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80148);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80148);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80148);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80148);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 98158414);

        carepassMemberSmry.setXtrCardNbr(98158414);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-324));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(40)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(40)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(40)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158414);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-324));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158414);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-324));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(98158414);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-300));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158414);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-300));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);


        xtraCardActiveCoupon.setXtraCardNbr(98158414);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-324)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(10));
        xtraCardActiveCoupon.setRedeemActlTswtz(carePassDateUtil.carePassRedeemEndTswtz(-1));
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassRedeemActlTswtzSetDt(1)));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(98158414);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-20)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(10));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);



        /* I am an CarePass member and cancelled my monthly carepass membership 5 day ago and want to fetch the status as U and benefit_eligibility_date as past date and redeemable Ind as N
         *
         */

        xtraCard.setXtraCardNbr(98158415);
        xtraCard.setCustId(80149);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-45)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584147);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80149);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80149);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80149);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80149);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 98158415);

        carepassMemberSmry.setXtrCardNbr(98158415);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-35));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-5)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-5)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-5)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158415);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-35));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158415);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-35));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(98158415);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-5));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158415);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-5));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(98158415);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-35)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(-5));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(98158415);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-35)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(-5));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /* I am an CarePass member and cancelled my yearly carepass membership 5 days ago and want to fetch the status as U and benefit_eligibility_date as past date and redeemable Ind as N
         *
         */

        xtraCard.setXtraCardNbr(98158416);
        xtraCard.setCustId(80150);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-380)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584161);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80150);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80150);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80150);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80150);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 98158416);

        carepassMemberSmry.setXtrCardNbr(98158416);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-370));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-5)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-340)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-340)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("U");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158416);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-370));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158416);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-370));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(98158416);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-5));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158416);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-5));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("U");
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(98158416);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-370)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(-5));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(98158416);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-370)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(-5));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        /*  I am a CarePass member with Hold status under monthly membership program and want to fetch $10 and 20% coupons with expirDt as today
         *
         */

        xtraCard.setXtraCardNbr(98158417);
        xtraCard.setCustId(80151);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-30)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584171);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80151);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80151);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80151);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80151);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 98158417);

        carepassMemberSmry.setXtrCardNbr(98158417);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-30));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        carepassMemberSmry.setCurPlanType("M");
        carepassMemberSmry.setCurStatus("H");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158417);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-30));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158417);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-30));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(98158417);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(1);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158417);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("H");
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);

        xtraCardActiveCoupon.setXtraCardNbr(98158417);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-30)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(98158417);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-30)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);

        /*  I am a CarePass member with Hold status under yearly membership program and want to fetch $10 and 20% coupons with expirDt as today
         *
         */

        xtraCard.setXtraCardNbr(98158418);
        xtraCard.setCustId(80152);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-370)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584181);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80152);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80152);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80152);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80152);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        carePassEnrollmentUtil.enrollUsingSetCust(cardTyp, 98158418);

        carepassMemberSmry.setXtrCardNbr(98158418);
        carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(-365));
        carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-335)));
        carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-335)));
        carepassMemberSmry.setCurPlanType("Y");
        carepassMemberSmry.setCurStatus("H");
        carePassDao.updateCarePassMemberSmry(carepassMemberSmry);

        carepassEnrollFormHist.setXtraCardNbr(98158418);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-365));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158418);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(-365));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("E");
        carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);


        carepassEnrollFormHist.setXtraCardNbr(98158418);
        carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassEnrollFormHist.setPymtPlanDur(12);
        carepassEnrollFormHist.setEnrollSrcCd("W");
        carepassEnrollFormHist.setEnrollStoreNbr(2695);
        carepassEnrollFormHist.setMemberEnrollPriceTypeCd(null);
        carePassDao.createCarepassEnrollFormHist(carepassEnrollFormHist);

        carepassMemberStatusHist.setXtraCardNbr(98158418);
        carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0));
        carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        carepassMemberStatusHist.setMbrStatusCd("H");
        carePassDao.createCarepassMemberStatusHist(carepassMemberStatusHist);


        xtraCardActiveCoupon.setXtraCardNbr(98158418);
        xtraCardActiveCoupon.setCmpgnId(40666);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-365)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        xtraCardActiveCoupon.setXtraCardNbr(98158418);
        xtraCardActiveCoupon.setCmpgnId(41083);
        xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-365)));
        xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.carePassPrntStartEndTswtz());
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(carePassDateUtil.carePassActionTswtzSetDt()));
        xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.carePassRedeemEndTswtz(0));
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);


        /* I am a CarePass member and switch to yearly program and want to see the expire date didn't changed to 365 days but plan type changed to Y
         *
         */

        xtraCard.setXtraCardNbr(98158419);
        xtraCard.setCustId(80153);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(-10)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(981584191);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80153);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80153);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80153);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80153);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        carePassEnrollmentUtil.enrollUsingSetCustMonthly(cardTyp, 98158419,-5);
        carePassEnrollmentUtil.M2ACarepassUsingSetCust(cardTyp, 98158419,0);



        /*
         *  Mobile - I am a fraudulent user, I would like to see my getcust response
         */
        xtraHotCard.setXtraCardNbr(98158420);
        xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
        xtraCardDao.createXtraHotCard(xtraHotCard);

    }


    /**
     * Delete Test Data for Care Pass Scenario
     */
    public void deleteGetCustCarepassEnrollmentTestData() {
	  /*
	    Purge All Test Cards
	  */
        List<Integer> xtraCardNbrList = Arrays.asList(98158410, 900058411, 98158412, 98158413, 98158414, 98158415, 98158416, 98158417, 98158418, 98158419,98158420);
        List<Integer> custIdList = Arrays.asList(80144, 80145, 80146, 80147, 80148, 80149, 80150, 80151, 80152, 80153);
        List<Integer> empIdList = Arrays.asList(80145145);

          customerDao.deleteCustomers(custIdList);
          xtraCardDao.deleteXtraCards(xtraCardNbrList);
          carePassDao.deleteCarePass(xtraCardNbrList);
          xtraCardDao. deleteEmployeeCards(empIdList);
    }
}