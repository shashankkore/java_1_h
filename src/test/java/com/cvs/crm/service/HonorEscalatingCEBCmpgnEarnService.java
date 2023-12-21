package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.CmpgnEarnRequest;
import com.cvs.crm.repository.*;
import com.cvs.crm.util.GenerateRandom;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
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
public class HonorEscalatingCEBCmpgnEarnService {

    private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    GenerateRandom generateRandom;

    @Autowired
    CampaignStoreSku campaignStoreSku;

    @Autowired
    Customer customer;

    @Autowired
    CustomerAddress customerAddress;

    @Autowired
    CustomerEmail customerEmail;

    @Autowired
    CustomerPhone customerPhone;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    EmployeeCard employeeCard;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraParms xtraParms;

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
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;


    public void viewHonorEscalatingCEBCmpgnEarnCreation(CmpgnEarnRequest cmpgnEarnRequest, int cardNum, String typ, int amt, int qty, int sku) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd = null;
        String userId = null;
        String jsonString = null;
        int srcLocCd = 0;
        int cardNbr = cardNum;
        int scAmt = amt;
        int scQty = qty;
        int scSku = sku;
        int scTxnNbr = Integer.valueOf(String.valueOf(generateRandom.randomNumberString()).replace("-", ""));
        int scTxnInvoiceNbr = Integer.valueOf(String.valueOf(generateRandom.randomNumberString()).replace("-", ""));
        String type = typ;
        if ("WEB".equalsIgnoreCase(cmpgnEarnRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else if ("STORE".equalsIgnoreCase(cmpgnEarnRequest.getChannel())) {
            msgSrcCd = "R";
            userId = "store";
            srcLocCd = 90037;
        }
        if ("eligible".equalsIgnoreCase(type) || "noneligible".equalsIgnoreCase(type)) {
            jsonString = "{\r\n" +
                    " \"rqstCmpgnEarnings\": {\r\n" +
                    " \"xtraCardNbr\": " + cardNbr + ",\r\n" +
                    " \"txnTs\": \"" + (carePassDateUtil.carePassExpireTswtz(0)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(0)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(0)).substring(8, 10) + "06:00:00-05:00\",\r\n" +
                    " \"storeNbr\": 2695,\r\n" +
                    " \"txnNbr\": " + scTxnNbr + ",\r\n" +
                    " \"txnCompleteInd\": true,\r\n" +
                    " \"txnInvoiceNbr\": " + scTxnInvoiceNbr + ", \r\n" +
                    " \"itemList\": [{\r\n" +
                    " \"skuNbr\": " + scSku + ",\r\n" +
                    " \"extndScanAmt\": " + scAmt + ",\r\n" +
                    " \"scanQty\": " + scQty + "\r\n" +
                    "}],\r\n" +
                    " \"verNbr\": 2,\r\n" +
                    " \"regNbr\": 222\r\n" +
                    "	}\r\n" +
                    "}";
        }
        System.out.println("jsonString:" + jsonString);
        System.out.println("userId:" + userId);
        requestSpecBuilder.setBaseUri(serviceConfig.getCmpgnEarnUrl())
                .setBasePath("api/v1.1/campaign_earnings")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
        System.out.println("statusCode:" + serviceResponse.getBody());
    }

    public void viewHonorEscalatingCEBCmpgnEarnCreationBoth(CmpgnEarnRequest cmpgnEarnRequest, int cardNum, String typ, int amt, int qty, int sku, int amt2, int qty2, int sku2) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd = null;
        String userId = null;
        String jsonString = null;
        int srcLocCd = 0;
        int cardNbr = cardNum;
        int scAmt = amt;
        int scQty = qty;
        int scSku = sku;
        int scAmt2 = amt2;
        int scQty2 = qty2;
        int scSku2 = sku2;
        int scTxnNbr = Integer.valueOf(String.valueOf(generateRandom.randomNumberString()).replace("-", ""));
        int scTxnInvoiceNbr = Integer.valueOf(String.valueOf(generateRandom.randomNumberString()).replace("-", ""));
        String type = typ;
        if ("WEB".equalsIgnoreCase(cmpgnEarnRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else if ("STORE".equalsIgnoreCase(cmpgnEarnRequest.getChannel())) {
            msgSrcCd = "R";
            userId = "store";
            srcLocCd = 90037;
        }
        if ("both".equalsIgnoreCase(type)) {
            jsonString = "{\r\n" +
                    " \"rqstCmpgnEarnings\": {\r\n" +
                    " \"xtraCardNbr\": " + cardNbr + ",\r\n" +
                    " \"txnTs\": \"" + (carePassDateUtil.carePassExpireTswtz(0)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(0)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(0)).substring(8, 10) + "06:00:00-05:00\",\r\n" +
                    " \"storeNbr\": 2695,\r\n" +
                    " \"txnNbr\": " + scTxnNbr + ",\r\n" +
                    " \"txnCompleteInd\": true,\r\n" +
                    " \"txnInvoiceNbr\":\" " + scTxnInvoiceNbr + "\", \r\n" +
                    " \"itemList\": [{\r\n" +
                    " \"skuNbr\": " + scSku + ",\r\n" +
                    " \"extndScanAmt\": " + scAmt + ",\r\n" +
                    " \"scanQty\": " + scQty + "\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    " \"skuNbr\": " + scSku2 + ",\r\n" +
                    " \"extndScanAmt\": " + scAmt2 + ",\r\n" +
                    " \"scanQty\": " + scQty2 + "\r\n" +
                    "}],\r\n" +
                    " \"verNbr\": 2,\r\n" +
                    " \"regNbr\": 222\r\n" +
                    "}\r\n" +
                    "}";

        }
        System.out.println("jsonString:" + jsonString);
        System.out.println("userId:" + userId);
        requestSpecBuilder.setBaseUri(serviceConfig.getCmpgnEarnUrl())
                .setBasePath("api/v1.1/campaign_earnings")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
        System.out.println("statusCode:" + serviceResponse.getBody());
    }

    /**
     * Create Test Data for Honor Escalating CEB Campaign Earn Scenario
     *
     * @param
     * @throws InterruptedException
     */
    public void createHonorEscalatingCEBCmpgnEarnServiceTestData() throws ParseException, InterruptedException {
        //Integer xtraCardNbr = xcNbr;
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

/*
        campaignStoreSku.setCmpgnId(72626);
        campaignStoreSku.setSkuNbr(730027);
        Integer dThresholdTypeSkuCount=  campaignDao.selectCampaignStoreSku(campaignStoreSku);

        if (dThresholdTypeSkuCount.equals(1))
        {
            System.out.println("dThresholdTypeSkuCount:" + dThresholdTypeSkuCount);
        }
        else {
            campaignStoreSku.setCmpgnId(72626);
            campaignStoreSku.setSkuNbr(730027);
            campaignStoreSku.setStoreNbr(2695);
            campaignStoreSku.setStartDt(carePassDateUtil.carePassRedeemEndTswtz(-65));
            campaignStoreSku.setEndDt(carePassDateUtil.carePassRedeemEndTswtz(300));
            campaignDao.createCampaignStoreSku(campaignStoreSku);
        }

        campaignStoreSku.setCmpgnId(72626);
        campaignStoreSku.setSkuNbr(356675);
        Integer dThresholdTypeNonSkuCount=  campaignDao.selectCampaignStoreSku(campaignStoreSku);
        if (dThresholdTypeNonSkuCount.equals(1))
        {
            System.out.println("dThresholdTypeNonSkuCount:" + dThresholdTypeNonSkuCount);
        }
        else {
            campaignStoreSku.setCmpgnId(72626);
            campaignStoreSku.setSkuNbr(730027);
            campaignStoreSku.setStoreNbr(2695);
            campaignStoreSku.setStartDt(carePassDateUtil.carePassRedeemEndTswtz(-65));
            campaignStoreSku.setEndDt(carePassDateUtil.carePassRedeemEndTswtz(300));
            campaignDao.createCampaignStoreSku(campaignStoreSku);
        }

        campaignStoreSku.setCmpgnId(46680);
        campaignStoreSku.setSkuNbr(301807);
        Integer qThresholdTypeSkuCount=  campaignDao.selectCampaignStoreSku(campaignStoreSku);
        if (qThresholdTypeSkuCount.equals(1))
        {
            System.out.println("qThresholdTypeSkuCount:" + qThresholdTypeSkuCount);
        }
        else {
            campaignStoreSku.setCmpgnId(46680);
            campaignStoreSku.setSkuNbr(301807);
            campaignStoreSku.setStoreNbr(2695);
            campaignStoreSku.setStartDt(carePassDateUtil.carePassRedeemEndTswtz(-65));
            campaignStoreSku.setEndDt(carePassDateUtil.carePassRedeemEndTswtz(300));
            campaignDao.createCampaignStoreSku(campaignStoreSku);
        }

        campaignStoreSku.setCmpgnId(46680);
        campaignStoreSku.setSkuNbr(356675);
        Integer qThresholdTypeNonSkuCount=  campaignDao.selectCampaignStoreSku(campaignStoreSku);
        if (qThresholdTypeNonSkuCount.equals(1))
        {
            System.out.println("qThresholdTypeNonSkuCount:" + qThresholdTypeNonSkuCount);
        }
        else {
            campaignStoreSku.setCmpgnId(46680);
            campaignStoreSku.setSkuNbr(356675);
            campaignStoreSku.setStoreNbr(2695);
            campaignStoreSku.setStartDt(carePassDateUtil.carePassRedeemEndTswtz(-65));
            campaignStoreSku.setEndDt(carePassDateUtil.carePassRedeemEndTswtz(300));
            campaignDao.createCampaignStoreSku(campaignStoreSku);
        }
*/

        /*Am a CVS Customer and joined Extra Care Program today and scanned MRF coupon from Store
         *
         */
        xtraCard.setXtraCardNbr(900058720);
        xtraCard.setCustId(80720);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058720);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80720);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80720);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80720);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80720);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $8 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first dollar threshold
        xtraCard.setXtraCardNbr(900058721);
        xtraCard.setCustId(80721);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058721);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80721);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80721);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80721);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80721);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $12 and scanQty as 1 and make sure $4 EB are generated since the customer has met the first dollar threshold and not the second dollar threshold
        xtraCard.setXtraCardNbr(900058722);
        xtraCard.setCustId(80722);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058722);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80722);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80722);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80722);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80722);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 2 and make sure $10 EB are generated since the customer has met the second dollar thresholdd
        xtraCard.setXtraCardNbr(900058723);
        xtraCard.setCustId(80723);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058723);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80723);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80723);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80723);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80723);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $40 and scanQty as 4 and make sure only $10 EB are generated since the customer has met the second dollar threshold
        xtraCard.setXtraCardNbr(900058724);
        xtraCard.setCustId(80724);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058724);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80724);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80724);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80724);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80724);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        //Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $10 and scanQty as 1 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $5 EB are generated since the customer has met the first dollar threshold
        xtraCard.setXtraCardNbr(900058725);
        xtraCard.setCustId(80725);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058725);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80725);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80725);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80725);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80725);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $20 and scanQty as 2 and a non eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 2 and make sure $10 EB are generated since the customer has met the second dollar threshold
        xtraCard.setXtraCardNbr(900058726);
        xtraCard.setCustId(80726);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058726);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80726);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80726);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80726);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80726);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        //Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $6 and scanQty as 1 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first dollar threshold
        xtraCard.setXtraCardNbr(900058727);
        xtraCard.setCustId(80727);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058727);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80727);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80727);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80727);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80727);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with a non eligible product (SKU) in the basket with extndScanAmt as $12 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first dollar threshold using any qualifying products
        xtraCard.setXtraCardNbr(900058728);
        xtraCard.setCustId(80728);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058728);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80728);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80728);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80728);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80728);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer(with an Employee discount) with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $4 EB are generated since the customer has met the first dollar threshold and Employee discount will not be sent as part of the Earn API request
        xtraCard.setXtraCardNbr(900058729);
        xtraCard.setCustId(80729);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058729);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80729);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80729);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80729);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80729);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        employeeCard.setCustId(80729);
        employeeCard.setEmpCardNbr(9004145);
        xtraCardDao.createEmployeeCard(employeeCard);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $4 EB are generated since the customer has met the first dollar threshold and not the second dollar threshold.Run a second online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $40 and scanQty as 1 and make sure $5 EB are generated since the customer has met the second dollar threshold and the points from the previous transaction will rollover to this transaction.Run a third online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $80 and scanQty as 1 and make sure NO EB are generated since the customer has already earned the maximum number of rewards as part of this campaign.Run a fourth online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $120 and scanQty as 2 and make sure NO EB are generated since the customer has already earned the maximum number of rewards as part of this campaign.        xtraCard.setXtraCardNbr(900058729);

        xtraCard.setXtraCardNbr(900058730);
        xtraCard.setCustId(80730);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058730);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80730);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80730);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80730);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80730);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first dollar threshold. Run a second online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure $6 EB are generated since the customer has met the first dollar threshold and the points from the previous transaction will rollover to this transaction. Run a third online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure NO EB are generated since the customer has met the second dollar threshold and the points from the previous transactions will rollover to this transaction.Run a fourth online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure $6 EB are generated since the customer has already earned the maximum number of rewards as part of this campaign.Run a fifth online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure NO EB are generated since the customer has already earned the maximum number of rewards as part of this campaign.

        xtraCard.setXtraCardNbr(900058731);
        xtraCard.setCustId(80731);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058731);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80731);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80731);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80731);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80731);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure $6 EB are generated since the customer has met the first quantity threshold.Run an online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 along with the coupon generated during visit 1 ($6 EB) and make sure $8 EB are generated since the customer has met the third dollar threshold and the discounts are not applicable CEB rewards calculation.
        xtraCard.setXtraCardNbr(900058732);
        xtraCard.setCustId(80732);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058732);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80732);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80732);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80732);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80732);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first quantity threshold.

        xtraCard.setXtraCardNbr(900058733);
        xtraCard.setCustId(80733);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058733);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80733);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80733);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80733);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80733);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 3 and make sure $10 EB are generated since the customer has met the second quantity threshold

        xtraCard.setXtraCardNbr(900058735);
        xtraCard.setCustId(80735);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058735);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80735);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80735);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80735);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80735);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 4 and make sure $14 EB are generated since the customer has met the third quantity threshold.

        xtraCard.setXtraCardNbr(900058736);
        xtraCard.setCustId(80736);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058736);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80736);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80736);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80736);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80736);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 4 and make sure $14 EB are generated since the customer has met the third quantity threshold.

        xtraCard.setXtraCardNbr(900058737);
        xtraCard.setCustId(80737);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058737);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80737);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80737);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80737);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80737);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $10 and scanQty as 2 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure $2 EB are generated since the customer has met the first quantity threshold
        xtraCard.setXtraCardNbr(900058738);
        xtraCard.setCustId(80738);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058738);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80738);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80738);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80738);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80738);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);



        //Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $20 and scanQty as 3 and a non eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 3 and make sure $10 EB are generated since the customer has met the second quantity threshold
        xtraCard.setXtraCardNbr(900058739);
        xtraCard.setCustId(80739);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058739);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80739);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80739);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80739);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80739);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $30 and scanQty as 4 and a non eligible product (SKU) in the basket with extndScanAmt as $30 and scanQty as 4 and make sure $14 EB are generated since the customer has met the third quantity threshold
        xtraCard.setXtraCardNbr(900058740);
        xtraCard.setCustId(80740);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058740);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80740);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80740);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80740);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80740);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first quantity threshold using any qualifying products
        xtraCard.setXtraCardNbr(900058742);
        xtraCard.setCustId(80742);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058742);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80742);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80742);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80742);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80742);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);


        //Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $5 and scanQty as 1 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first quantity threshold
        xtraCard.setXtraCardNbr(900058741);
        xtraCard.setCustId(80741);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058741);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80741);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80741);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80741);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80741);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);
    }


    /**
     * Delete Test Data for Honor Escalating CEB CmpgnEarn Service
     */
    public void deleteHonorEscalatingCEBCmpgnEarnServiceTestData() {
	  /*
	    Purge All Test Cards
	  */
        List<Integer> xtraCardNbrList = Arrays.asList(900058720, 900058721, 900058722, 900058723, 900058724, 900058725, 900058726, 900058727, 900058728, 900058729, 900058730, 900058731, 900058732, 900058733, 900058735, 900058736, 900058737, 900058738, 900058739, 900058740, 900058741, 900058742);
        List<Integer> custIdList = Arrays.asList(80720, 80721, 80722, 80723, 80724, 80725, 80726, 80727, 80728, 80729, 80730, 80731, 80732, 80733, 80735, 80736, 80737, 80738, 80739, 80740, 80741, 80742);
        List<Integer> empIdList = Arrays.asList(9004145);

        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        xtraCardDao.deleteEmployeeCards(empIdList);
        campaignDao.deleteCampaignPointRecords(xtraCardNbrList);
        campaignDao.deleteCampaignRewardsRecords(xtraCardNbrList);
    }
}