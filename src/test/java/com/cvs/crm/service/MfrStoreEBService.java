package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.MfrCpnRequest;
import com.cvs.crm.model.request.StoreEBRequest;
import com.cvs.crm.repository.*;
import com.cvs.crm.model.data.XtraHotCard;
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
import com.cvs.crm.util.PhrDateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class MfrStoreEBService {

    private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

    @Autowired
    PhrDateUtil phrDateUtil;
    
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
    XtraHotCard xtraHotCard;

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


    public void viewMfrCreation(MfrCpnRequest mfrCpnRequest, Integer xtraCardNbr) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd = null;
        String userId = null;
        int srcLocCd = 0;
//        int cmpgnId = 43325;
        int cmpgnId = mfrCpnRequest.getSearchCmpgnId();
        int cpnSkuNbr = mfrCpnRequest.getSearchCpnSkuNbr();


        if ("WEB".equalsIgnoreCase(mfrCpnRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else if ("STORE".equalsIgnoreCase(mfrCpnRequest.getChannel())) {
            msgSrcCd = "R";
            userId = "store";
            srcLocCd = 90037;
        }
        requestSpecBuilder.setBaseUri(serviceConfig.getCpnapiUrl())
                .setBasePath("api/v1.1/coupons")
                .addQueryParam("xtra_card_nbr", xtraCardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("cmpgn_id", cmpgnId)
                .addQueryParam("cpn_sku_nbr", cpnSkuNbr);

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body("").post();
        serviceResponse.prettyPrint();
    
    }
    
    public void viewStoreEBCreation(StoreEBRequest storeEBRequest, Double amount) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        Integer cardNum = storeEBRequest.getSearchCardNbr();
        String cardTyp = storeEBRequest.getSearchCardType();
        String msgSrcCd = null;
        String userId = null;
        int srcLocCd = 0;
        Double refundamount = amount;
        Integer mgr = 12345678;
        Integer emp = 999999999;
        String jsonString = null;
        
        if ("WEB".equalsIgnoreCase(storeEBRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else if ("STORE".equalsIgnoreCase(storeEBRequest.getChannel())) {
            msgSrcCd = "R";
            userId = "store";
            srcLocCd = 90037;
        }
        
//        if (refundamount<1) {
//        	jsonString = "{\r\n" +
//                    "\"regRelease\":  1,\r\n" +
//                    "\"storeTswtz\": \""+ (phrDateUtil.phrEnrollmentTS()).substring(0, 4)
//      	            + (phrDateUtil.phrEnrollmentTS()).substring(5, 7)
//      	            + (phrDateUtil.phrEnrollmentTS()).substring(8, 10) + "00:00:00-0500" + "\",\r\n" +
//                    "\"rqstAmt\": 0" + refundamount + ",\n" +
//                    "\"mgrId\": " + mgr + ",\n" +
//                    "\"empId\": " + emp + "\n" +
//                    "}";
//        }
//        else {
//        	jsonString = "{\r\n" +
//                    "\"regRelease\":  1,\r\n" +
//                    "\"storeTswtz\": \""+ (phrDateUtil.phrEnrollmentTS()).substring(0, 4)
//      	            + (phrDateUtil.phrEnrollmentTS()).substring(5, 7)
//      	            + (phrDateUtil.phrEnrollmentTS()).substring(8, 10) + "00:00:00-0500" + "\",\r\n" +
//                    "\"rqstAmt\": " + refundamount + ",\n" +
//                    "\"mgrId\": " + mgr + ",\n" +
//                    "\"empId\": " + emp + "\n" +
//                    "}";
//        }
        
        jsonString = "{\r\n" +
              "\"regRelease\":  1,\r\n" +
              "\"storeTswtz\": \""+ (phrDateUtil.phrEnrollmentTS()).substring(0, 4)
	            + (phrDateUtil.phrEnrollmentTS()).substring(5, 7)
	            + (phrDateUtil.phrEnrollmentTS()).substring(8, 10) + "00:00:00-0500" + "\",\r\n" +
              "\"rqstAmt\": " + refundamount + ",\n" +
              "\"mgrId\": " + mgr + ",\n" +
              "\"empId\": " + emp + "\n" +
              "}";
        System.out.println("input payload : " + jsonString);
          requestSpecBuilder.setBaseUri(serviceConfig.getCpnapiUrl())
                    .setBasePath("api/v1.2/coupons/{search_card_type},{search_card_nbr}")
                    .addPathParam("search_card_type", cardTyp)
                    .addPathParam("search_card_nbr", cardNum)
                    .addQueryParam("msg_src_cd", msgSrcCd)
                    .addQueryParam("user_id", userId)
                    .addQueryParam("src_loc_cd", srcLocCd);

            RequestSpecification spec = requestSpecBuilder.build();
            serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
    }


//    public void viewStoreEBCreation(StoreEBRequest storeEBRequest, Double amount) throws ParseException {
//        Integer cardNum = storeEBRequest.getSearchCardNbr();
//        String cardTyp = storeEBRequest.getSearchCardType();
//        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
//        String msgSrcCd;
//        String userId;
//        int srcLocCd;
//        Double refundamount = amount;
//
//        if ("WEB".equalsIgnoreCase(storeEBRequest.getChannel())) {
//            msgSrcCd = "W";
//            userId = "CVS.COM";
//            srcLocCd = 2695;
//
//            String jsonString = "{\r\n" +
//                    "\"regRelease\":  1,\r\n" +
//                    "\"storeTswtz\": \"2020122008:00:0002:00\",\r\n" +
//                    "\"rqstAmt\": " + refundamount + ",\n" +
//                    "\"rsnCd\": \"RF\",\r\n" +
//                    "\"origTxnDt\":  \"20201015\",\r\n" +
//                    "\"origTxnNbr\":  7031,\r\n" +
//                    "\"origCmpgnId\": 19700\r\n" +
//                    "}";
//
//
//            requestSpecBuilder.setBaseUri(serviceConfig.getCpnapiUrl())
//                    .setBasePath("api/v1.2/coupons/{search_card_type},{search_card_nbr}")
//                    .addPathParam("search_card_type", cardTyp)
//                    .addPathParam("search_card_nbr", cardNum)
//                    .addQueryParam("msg_src_cd", msgSrcCd)
//                    .addQueryParam("user_id", userId)
//                    .addQueryParam("src_loc_cd", srcLocCd);
//
//            RequestSpecification spec = requestSpecBuilder.build();
//            serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
//        } else if ("STORE".equalsIgnoreCase(storeEBRequest.getChannel())) {
//            msgSrcCd = "R";
//            userId = "store";
//            srcLocCd = 90037;
//            Integer mgr = 12345678;
//            Integer emp = 999999999;
//            Integer strNbr = 12345;
//            Integer regNbr = 2;
//
//            String jsonString = "{\r\n" +
//                    "\"regRelease\":  1,\r\n" +
//                    "\"storeTswtz\": \"2020122008:00:0002:00\",\r\n" +
//                    "\"mgrId\": " + mgr + ",\n" +
//                    "\"empId\": " + emp + ",\n" +
//                    "\"rqstAmt\": " + refundamount + ",\n" +
//                    "\"rsnCd\": \"RF\",\r\n" +
//                    "\"origTxnDt\":  \"20201015\",\r\n" +
//                    "\"origStrNbr\": " + strNbr + ",\n" +
//                    "\"origRegNbr\": " + regNbr + ",\n" +
//                    "\"origTxnNbr\":  7031,\r\n" +
//                    "\"origCmpgnId\": 19700\r\n" +
//                    "}";
//
//            requestSpecBuilder.setBaseUri(serviceConfig.getCpnapiUrl())
//                    .setBasePath("api/v1.2/coupons/{search_card_type},{search_card_nbr}")
//                    .addPathParam("search_card_type", cardTyp)
//                    .addPathParam("search_card_nbr", cardNum)
//                    .addQueryParam("msg_src_cd", msgSrcCd)
//                    .addQueryParam("user_id", userId)
//                    .addQueryParam("src_loc_cd", srcLocCd);
//
//            RequestSpecification spec = requestSpecBuilder.build();
//            serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
//        }
//    }

    /**
     * Create Test Data for MFR and StoreEB Scenario
     *
     * @param
     * @throws InterruptedException
     */
    public void createMfrStoreEBServiceTestData() throws ParseException, InterruptedException {
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

        /*Am a CVS Customer and joined Extra Care Program today and scanned MFR coupon from Store
         *
         */

        xtraCard.setXtraCardNbr(900058490);
        xtraCard.setCustId(80221);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058490);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80221);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80221);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80221);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80221);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*Am an existing CVS EC Customer and want to get my MFR coupon created through online
         *
         */

        xtraCard.setXtraCardNbr(900058491);
        xtraCard.setCustId(80222);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058491);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80222);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80222);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80222);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80222);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*Am a CVS Customer and joined Extra Care Program today and scanned MRF coupon from Store
         *
         */

        xtraCard.setXtraCardNbr(900058492);
        xtraCard.setCustId(80223);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058492);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80223);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80223);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80223);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80223);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*Am an CVS Employee and want to get my MFR coupon created from store
         *
         */

        xtraCard.setXtraCardNbr(900058493);
        xtraCard.setCustId(80224);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058493);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80224);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80224);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80224);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80224);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        employeeCard.setCustId(80224);
        employeeCard.setEmpCardNbr(80224145);
        xtraCardDao.createEmployeeCard(employeeCard);



        /*Am an Hot Card member and want to get my MFR coupon created from store
         *
         */

        xtraHotCard.setXtraCardNbr(900058495);
        xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
        xtraCardDao.createXtraHotCard(xtraHotCard);

        /*I am an existing CVS Customer and joined Extra Care Program today and want to get Store ExtraBucks coupons created for a purchase at a store today and returned at same store on same day
         *
         */

        xtraCard.setXtraCardNbr(900058496);
        xtraCard.setCustId(80226);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058496);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80226);
        customer.setGndrCd("M");
        customer.setFirstName("auto");
        customer.setLastName("test");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80226);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80226);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80226);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at a store yesterday and returned at different store today
         *
         */

        xtraCard.setXtraCardNbr(900058497);
        xtraCard.setCustId(80227);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058497);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80227);
        customer.setGndrCd("M");
        customer.setFirstName("auto");
        customer.setLastName("test");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80227);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80227);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80227);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchased online last week and returned at store today
         *
         */

        xtraCard.setXtraCardNbr(900058498);
        xtraCard.setCustId(80228);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058498);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80228);
        customer.setGndrCd("M");
        customer.setFirstName("auto");
        customer.setLastName("test");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80228);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80228);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80228);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at a store which is Refundable
         *
         */

        xtraCard.setXtraCardNbr(900058499);
        xtraCard.setCustId(80229);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058499);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80229);
        customer.setGndrCd("M");
        customer.setFirstName("auto");
        customer.setLastName("test");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80229);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80229);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80229);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at online which is non-Refundable
         *
         */

        xtraCard.setXtraCardNbr(900058500);
        xtraCard.setCustId(80230);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058500);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80230);
        customer.setGndrCd("M");
        customer.setFirstName("auto");
        customer.setLastName("test");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80230);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80230);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80230);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        /*I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at online which is Refundable and Customer Cancelled the order
         *
         */

        xtraCard.setXtraCardNbr(900058501);
        xtraCard.setCustId(80231);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058490);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80231);
        customer.setGndrCd("M");
        customer.setFirstName("auto");
        customer.setLastName("test");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80231);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80231);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80231);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        /*I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at online which is non-Refundable and Customer Cancelled the order
         *
         */

        xtraCard.setXtraCardNbr(900058502);
        xtraCard.setCustId(80232);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058502);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80232);
        customer.setGndrCd("M");
        customer.setFirstName("auto");
        customer.setLastName("test");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80232);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80232);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80232);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        /*Am an Hot Card member and want to get my Store ExtraBucks coupons created
         *
         */
        xtraHotCard.setXtraCardNbr(900058504);
        xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
        xtraCardDao.createXtraHotCard(xtraHotCard);


    }


    /**
     * Delete Test Data for MFR and StoreEB coupons
     */
    public void deleteMfrStoreEBServiceTestData() {
	  /*
	    Purge All Test Cards
	  */
        List<Integer> xtraCardNbrList = Arrays.asList(900058490, 900058491, 900058492, 900058493, 900058494, 900058495, 900058496, 900058497, 900058498, 900058499, 900058500, 900058501, 900058502, 900058503, 900058504);
//        List<Integer> xtraCardNbrList = Arrays.asList(900058490, 900058491, 900058493, 900058492, 900058495, 900058496);
        List<Integer> custIdList = Arrays.asList(80221, 80222, 80223, 80224, 80225, 80226, 80227, 80228, 80229, 80230, 80231, 80232, 80233);
//        List<Integer> custIdList = Arrays.asList(80221, 80222, 80224, 80223, 80226);
                List<Integer> empIdList = Arrays.asList(80224145);

        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        xtraCardDao.deleteEmployeeCards(empIdList);
    }
}