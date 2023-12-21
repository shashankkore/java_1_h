package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.BulkCpnRequest;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.GenerateRandom;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CampaignEarnServiceUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@Data
public class BulkCouponsService {

    private Response serviceResponse;

    @Autowired
    DateUtil dateUtil;
    
    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

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
    ServiceConfig serviceConfig;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraCardSegment xtraCardSegment;
    
    @Autowired
    Campaign campaign;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    XtraParms xtraParms;

    @Autowired
    XtraHotCard xtraHotCard;

    @Autowired
    GenerateRandom generateRandom;

    @Autowired
    CampaignEarnServiceUtil campaignEarnServiceUtil;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;
    
    @Autowired
    CampaignCoupon campaignCoupon;

    Integer cpnSeqNbr1, cpnSeqNbr2 = 0;

    public void viewBulkCoupons(BulkCpnRequest bulkCpnRequest, String prof, Integer xtraCardNbr, String type, Integer cmpgn, Integer cpnNbr1, Integer cpnNbr2, Integer matchCode) throws ParseException {
        Integer cardNum = xtraCardNbr;
        String typ = type;
//        Integer cmpgnId = cmpgn;
//        Integer cpnSkuNbr1 = cpnNbr1;
//        Integer cpnSkuNbr2 = cpnNbr2;
        String profile = prof;
        Integer matchCd = matchCode;
        Long cpnSeqNbr1 = Long.valueOf(0);
        Long cpnSeqNbr2 = Long.valueOf(0);
        Long campaignCpnSeqNbr1 = Long.valueOf(0);
        Long campaignCpnSeqNbr2 = Long.valueOf(0);
        String printStartDt1;
        String printStartDt2;
        Date printEndDt1;
        Date printEndDt2;
        String redeemStartDt1;
        String redeemStartDt2;
        String redeemEndDt1;
        String redeemEndDt2;
        String sRedeemStartDt1 = null;
        String sprintStartDt1 = null;
        String sprintStartDt2 = null;
        String sprintEndDt1 = null;
        String newPrintEndDate = null,newPrintStartDate = null, newRedeemEndDate =null, newRedeemStartDate = null;
        String sprStartDt1 = null, sRedeemEndDt1 = null;
        
//        Select campaign
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("S");
        System.out.println("Campaign ID = "+campaignDao.selectInstantQEBCampaign(campaign));
        Integer cmpgnId = campaignDao.selectInstantQEBCampaign(campaign);
        
//      Select Coupon Nbr
        campaignCoupon.setCmpgnId(cmpgnId);
//        campaignCoupon.setRwrdQty("0.2");
        System.out.println("CPN_NBR = "+campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2));
        Integer cpnSkuNbr1 = campaignDao.selectCampaignCouponBK(campaignCoupon, 0.2);
        
        campaignCoupon.setCmpgnId(cmpgnId);
//        campaignCoupon.setRwrdQty(2);
        System.out.println("CPN_NBR = "+campaignDao.selectCampaignCouponBK(campaignCoupon,2));
        Integer cpnSkuNbr2 = campaignDao.selectCampaignCouponBK(campaignCoupon,2);

        
        if ("stub".equals(profile)) {
            cpnSeqNbr1 = Long.valueOf("43916_103884".replace("_", "0"));
            cpnSeqNbr2 = Long.valueOf("43916_104064".replace("_", "0"));
        } else {
            if (typ.equals("viewnegative") || type.equals("printnegative") || type.equals("loadnegative") || type.equals("redeemnegative")) {

            } else {
                xtraCardActiveCoupon.setXtraCardNbr(xtraCardNbr);
                xtraCardActiveCoupon.setCmpgnId(cmpgnId);
                xtraCardActiveCoupon.setCpnNbr(cpnSkuNbr1);
                cpnSeqNbr1 = xtraCardDao.selectCpnSeqNbrXtraCardActiveCoupon(xtraCardActiveCoupon);
                printStartDt1 = xtraCardDao.selectPrintStartXtraCardActiveCoupon(xtraCardActiveCoupon);
                printEndDt1 = xtraCardDao.selectPrintEndXtraCardActiveCoupon(xtraCardActiveCoupon);
                campaignCpnSeqNbr1 = xtraCardDao.selectCampaignCpnSeqNbrXtraCardActiveCoupon(xtraCardActiveCoupon);

                sprintStartDt1 = printStartDt1.replace("-", "").replace(" ", "").substring(0, 16);
                sprintEndDt1 = printEndDt1.toString().substring(0, 10);
                sprStartDt1 = printStartDt1.toString().substring(0, 10);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(sprintEndDt1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Incrementing the date by 1 day
                c.add(Calendar.DAY_OF_MONTH, 1);
                newPrintEndDate = sdf.format(c.getTime()).replace("-", "").replace(" ", "");

                Calendar c1 = Calendar.getInstance();
                try {
                    c1.setTime(sdf.parse(sprStartDt1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Incrementing the date by 1 day
                c1.add(Calendar.DAY_OF_MONTH, -2);
                newPrintStartDate = sdf.format(c1.getTime()).replace("-", "").replace(" ", "");

                xtraCardActiveCoupon.setXtraCardNbr(xtraCardNbr);
                xtraCardActiveCoupon.setCmpgnId(cmpgnId);
                xtraCardActiveCoupon.setCpnNbr(cpnSkuNbr2);
                cpnSeqNbr2 = xtraCardDao.selectCpnSeqNbrXtraCardActiveCoupon(xtraCardActiveCoupon);
                printStartDt2 = xtraCardDao.selectPrintStartXtraCardActiveCoupon(xtraCardActiveCoupon);
                printEndDt2 = xtraCardDao.selectPrintEndXtraCardActiveCoupon(xtraCardActiveCoupon);
                sprintStartDt2 = printStartDt2.replace("-", "").replace(" ", "").substring(0, 16);
                campaignCpnSeqNbr2 = xtraCardDao.selectCampaignCpnSeqNbrXtraCardActiveCoupon(xtraCardActiveCoupon);

            }
        }
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd = null;
        String userId = null;
        String jsonString = null;
        int srcLocCd = 0;
        if ("WEB".equalsIgnoreCase(bulkCpnRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
            System.out.println("WEB:" + msgSrcCd);
        } else if ("STORE".equalsIgnoreCase(bulkCpnRequest.getChannel())) {
            msgSrcCd = "R";
            userId = "store";
            srcLocCd = 90037;
        }

        if (typ.equals("view") || typ.equals("viewnegative")) {
            jsonString = "{\r\n" +
                    "\"viewList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"V\",\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"V\",\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "}],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("print") ) {
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                    "}],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (type.equals("printnegative")) {
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "}],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("printend")) {
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"" + newPrintEndDate + "11:59:59-04:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"" + newPrintEndDate + "11:59:59-04:00\"\r\n" +
                    "}],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("printstart")) {
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"" + newPrintStartDate + "11:59:59-04:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"P\",\r\n" +
                    "\"ts\": \"" + newPrintStartDate + "11:59:59-04:00\"\r\n" +
                    "}],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("load")) {
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [{\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                    "}],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("loadnegative")) {
            System.out.println("loadnegative:"  );
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n"  +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "}],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("loadredeemend")) {
            System.out.println("loadredeemend:" + typ);
            redeemEndDt1 = xtraCardDao.selectRedeemEndXtraCardActiveCoupon(xtraCardActiveCoupon);
            sRedeemEndDt1 = redeemEndDt1.toString().substring(0, 10);
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                c2.setTime(sdf.parse(sRedeemEndDt1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Incrementing the date by 1 day
            c2.add(Calendar.DAY_OF_MONTH, 2);
            newRedeemEndDate = sdf.format(c2.getTime()).replace("-", "").replace(" ", "");
            System.out.println(":newRedeemEndDate:" + newRedeemEndDate);
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [{\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"ts\": \"" + newRedeemEndDate + "11:59:59-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"ts\": \"" + newRedeemEndDate + "11:59:59-02:00\"\r\n" +
                    "}],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("digitize")) {
            System.out.println("digitize:" + typ);
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [{\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"referrerCd\": \"P\",\r\n" +
                    "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"opCd\": \"L\",\r\n" +
                    "\"referrerCd\": \"P\",\r\n" +
                    "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                    "}],\r\n" +
                    "\"redeemList\": []\r\n" +
                    "}";
        }
        else if (typ.equals("redeem") || typ.equals("notloadedredeem")) {
           if (matchCd.equals(2)) {
        	jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cmpgnCpnSeqNbr\": " + campaignCpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cmpgnCpnSeqNbr\": " + campaignCpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                    "}]\r\n" +
                    "}";
           }
           else if (matchCd.equals(3) || matchCd.equals(4)) {
        	   String cpnSeqNbr1_new = Long.toString(cpnSeqNbr1);
        	   int lastThreeIndex_1 = cpnSeqNbr1_new.length()-3; // 3nd character index from last
        	   int lastThreeDigits_1 = Integer.parseInt(cpnSeqNbr1_new.substring(lastThreeIndex_1));
        	   
        	   String cpnSeqNbr2_new = Long.toString(cpnSeqNbr2);
        	   int lastThreeIndex_2 = cpnSeqNbr2_new.length()-3; // 3nd character index from last
        	   int lastThreeDigits_2 = Integer.parseInt(cpnSeqNbr2_new.substring(lastThreeIndex_2));
        	   jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + lastThreeDigits_1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + lastThreeDigits_2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                    "}]\r\n" +
                    "}";
           }
           else if (matchCd.equals(6)) {
           	jsonString = "{\r\n" +
                       "\"viewList\": [],\r\n" +
                       "\"printList\": [],\r\n" +
                       "\"loadList\": [],\r\n" +
                       "\"redeemList\": [{\r\n" +
                       "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                       "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                       "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                       "\"matchCd\": " + matchCd + ",\r\n" +
                       "\"opCd\": \"R\",\r\n" +
                       "\"redeemActlAmt\": 2,\r\n" +
                       "\"redeemOvrdRsnNbr\": 2,\r\n" +
                       "\"redeemActlCashierNbr\": 4642315,\r\n" +
                       "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                       "},\r\n" +
                       "{\r\n" +
                       "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                       "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                       "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                       "\"matchCd\": " + matchCd + ",\r\n" +
                       "\"opCd\": \"R\",\r\n" +
                       "\"redeemActlAmt\": 2,\r\n" +
                       "\"redeemOvrdRsnNbr\": 2,\r\n" +
                       "\"redeemActlCashierNbr\": 4642315,\r\n" +
                       "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                       "}]\r\n" +
                       "}";
              }
           else if (matchCd.equals(7)) {
        	   String cpnSeqNbr1_new = Long.toString(cpnSeqNbr1);
        	   int lastEightIndex_1 = cpnSeqNbr1_new.length()-8; // 3nd character index from last
        	   int lastEightDigits_1 = Integer.parseInt(cpnSeqNbr1_new.substring(lastEightIndex_1));
        	   
        	   String cpnSeqNbr2_new = Long.toString(cpnSeqNbr2);
        	   int lastEightIndex_2 = cpnSeqNbr2_new.length()-8; // 3nd character index from last
        	   int lastEightDigits_2 = Integer.parseInt(cpnSeqNbr2_new.substring(lastEightIndex_2));
        	   jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + lastEightDigits_1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + lastEightDigits_2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                    "}]\r\n" +
                    "}";
           }
           else {
        	jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt1 + "-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + sprintStartDt2 + "-02:00\"\r\n" +
                    "}]\r\n" +
                    "}";
           }
        }
        else if (typ.equals("redeemnegative")) {
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"2021040708:00:0002:00\"\r\n" +
                    "}]\r\n" +
                    "}";
        }
        else if (typ.equals("redeemend")) {
            redeemEndDt1 = xtraCardDao.selectRedeemEndXtraCardActiveCoupon(xtraCardActiveCoupon);
            sRedeemEndDt1 = redeemEndDt1.toString().substring(0, 10);
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                c2.setTime(sdf.parse(sRedeemEndDt1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Incrementing the date by 1 day
            c2.add(Calendar.DAY_OF_MONTH, 2);
            newRedeemEndDate = sdf.format(c2.getTime()).replace("-", "").replace(" ", "");
            System.out.println("redeemend:" + typ);
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + newRedeemEndDate + "11:59:59-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + newRedeemEndDate + "11:59:59-02:00\"\r\n" +
                    "}]\r\n" +
                    "}";
        }
        else if (typ.equals("redeemstart")) {
            redeemStartDt1 = xtraCardDao.selectRedeemStartXtraCardActiveCoupon(xtraCardActiveCoupon);
            sRedeemStartDt1 = redeemStartDt1.toString().substring(0, 10);
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                c2.setTime(sdf.parse(sRedeemStartDt1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Incrementing the date by 1 day
            c2.add(Calendar.DAY_OF_MONTH, -2);
            newRedeemStartDate = sdf.format(c2.getTime()).replace("-", "").replace(" ", "");
            System.out.println("redeemstart:" + typ);
            jsonString = "{\r\n" +
                    "\"viewList\": [],\r\n" +
                    "\"printList\": [],\r\n" +
                    "\"loadList\": [],\r\n" +
                    "\"redeemList\": [{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr1 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr1 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + newRedeemStartDate + "11:59:59-02:00\"\r\n" +
                    "},\r\n" +
                    "{\r\n" +
                    "\"cmpgnId\": " + cmpgnId + ",\r\n" +
                    "\"cpnSeqNbr\": " + cpnSeqNbr2 + ",\r\n" +
                    "\"cpnSkuNbr\":  " + cpnSkuNbr2 + ",\r\n" +
                    "\"matchCd\": " + matchCd + ",\r\n" +
                    "\"opCd\": \"R\",\r\n" +
                    "\"redeemActlAmt\": 2,\r\n" +
                    "\"redeemActlCashierNbr\": 4642315,\r\n" +
                    "\"ts\": \"" + newRedeemStartDate + "11:59:59-02:00\"\r\n" +
                    "}]\r\n" +
                    "}";
        }
        System.out.println("jsonString:" + jsonString);

        requestSpecBuilder.setBaseUri(serviceConfig.getCpnapiUrl())
                .setBasePath("api/v1.1/coupons")
                .addQueryParam("xtra_card_nbr", cardNum)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).patch();
        System.out.println("serviceResponse.statusCode:" + serviceResponse.statusCode());
        String res = serviceResponse.getBody(). asString();
        System.out.println("serviceResponse.body:" + res);
    }

    /**
     * Create Test Data For Bulk Coupon Scenario
     */
    public void createBulkCpnServiceTestData() throws ParseException {

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
        
        cacheRefreshUtil.refresCacheforCmpgnDefns();

        /*I am an existing CVS EC Customer and want to View a Coupon which is not viewed before
         *
         */
        xtraCard.setXtraCardNbr(900017912);
        xtraCard.setCustId(80660);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(900017912);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80660);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80660);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80660);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80660);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017912);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr1 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr1 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900017912, 4, 10, 999975, txnNbr1, txnInvoiceNbr1);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr2 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr2 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017912, 10, 100, 999975, txnNbr2, txnInvoiceNbr2);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to View a Coupon which is already viewed before
         *
         */
        xtraCard.setXtraCardNbr(900058661);
        xtraCard.setCustId(80661);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058661);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80661);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80661);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80661);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80661);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900058661);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr021 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr021 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900058661, 4, 10, 999975, txnNbr021, txnInvoiceNbr021);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr022 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr022 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900058661, 10, 100, 999975, txnNbr022, txnInvoiceNbr022);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Print a Coupon which is not printed/Loaded/Redeemed before
         *
         */
        xtraCard.setXtraCardNbr(900017916);
        xtraCard.setCustId(80662);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017916);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80662);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80662);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80662);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80662);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017916);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr031 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr031 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900017916, 4, 10, 999975, txnNbr031, txnInvoiceNbr031);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr032 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr032 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017916, 10, 100, 999975, txnNbr032, txnInvoiceNbr032);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Print a Coupon which is already printed before
         *
         */
        xtraCard.setXtraCardNbr(900017917);
        xtraCard.setCustId(80663);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017917);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80663);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80663);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80663);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80663);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

          xtraCardSegment.setXtraCardNbr(900017917);
//          xtraCardSegment.setXtraCardSegNbr(194);
          xtraCardSegment.setXtraCardSegNbr(337);
          xtraCardSegment.setCtlGrpCd(null);
          xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr041 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr041 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900017917, 4, 10, 999975, txnNbr041, txnInvoiceNbr041);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr042 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr042 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017917, 10, 100, 999975, txnNbr042, txnInvoiceNbr042);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Print a Coupon which is already loaded before
         *
         */
        xtraCard.setXtraCardNbr(900017918);
        xtraCard.setCustId(80664);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017918);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80664);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80664);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80664);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80664);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017918);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr051 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr051 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900017918, 4, 10, 999975, txnNbr051, txnInvoiceNbr051);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr052 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr052 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017918, 10, 100, 999975, txnNbr052, txnInvoiceNbr052);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Print a Coupon which is already redeemed before
         *
         */
        xtraCard.setXtraCardNbr(900017919);
        xtraCard.setCustId(80665);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017919);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80665);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80665);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80665);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80665);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017919);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr061 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr061 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017919, 4, 10, 999975, txnNbr061, txnInvoiceNbr061);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr062 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr062 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017919, 10, 100, 999975, txnNbr062, txnInvoiceNbr062);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Print a Coupon when Print actual timestamp is greater than print end date
         *
         */
        xtraCard.setXtraCardNbr(900017920);
        xtraCard.setCustId(80666);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017920);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80666);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80666);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80666);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80666);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017920);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr071 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr071 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017920, 4, 10, 999975, txnNbr071, txnInvoiceNbr071);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr072 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr072 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017920, 10, 100, 999975, txnNbr072, txnInvoiceNbr072);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Print a Coupon when Print actual timestamp is greater than print end date
         *
         */
        xtraCard.setXtraCardNbr(900017921);
        xtraCard.setCustId(80667);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017921);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80667);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80667);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80667);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80667);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017921);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr081 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr081 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017921, 4, 10, 999975, txnNbr081, txnInvoiceNbr081);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr082 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr082 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017921, 10, 100, 999975, txnNbr082, txnInvoiceNbr082);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Print a Coupon when Print actual timestamp is greater than print end date
         *
         */
        xtraCard.setXtraCardNbr(900017922);
        xtraCard.setCustId(80668);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017922);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80668);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80668);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80668);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80668);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017922);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr091 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr091 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017922, 4, 10, 999975, txnNbr091, txnInvoiceNbr091);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr092 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr092 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017922, 10, 100, 999975, txnNbr092, txnInvoiceNbr092);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and I don't want to Load a Coupon which is already Printed before
         *
         */
        xtraCard.setXtraCardNbr(900017025);
        xtraCard.setCustId(80670);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017025);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80670);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80670);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80670);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80670);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017025);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr101 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr101 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017025, 4, 10, 999975, txnNbr101, txnInvoiceNbr101);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr102 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr102 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017025, 10, 100, 999975, txnNbr102, txnInvoiceNbr102);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

       /*I am an existing CVS EC Customer and I don't want to Load a Coupon which is already Redeemed before
         *
         */
        xtraCard.setXtraCardNbr(900017024);
        xtraCard.setCustId(80671);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017024);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80671);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80671);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80671);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80671);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017024);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr111 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr111 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017024, 4, 10, 999975, txnNbr111, txnInvoiceNbr111);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr112 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr112 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017024, 10, 100, 999975, txnNbr112, txnInvoiceNbr112);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();


        /*I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P First time
         *
         */
        xtraCard.setXtraCardNbr(900017026);
        xtraCard.setCustId(80680);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017026);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80680);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80680);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80680);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80680);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017026);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr121 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr121 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017026, 4, 10, 999975, txnNbr121, txnInvoiceNbr121);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr122 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr122 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017026, 10, 100, 999975, txnNbr122, txnInvoiceNbr122);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();


        /*I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P Second time
         *
         */
        xtraCard.setXtraCardNbr(900017911);
        xtraCard.setCustId(80681);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017911);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80681);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80681);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80681);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80681);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017911);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr131 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr131 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017911, 4, 10, 999975, txnNbr131, txnInvoiceNbr131);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr132 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr132 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017911, 10, 100, 999975, txnNbr132, txnInvoiceNbr132);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();


        /*I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P and Redeem the same
         *
         */
        xtraCard.setXtraCardNbr(900017930);
        xtraCard.setCustId(80682);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017930);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80682);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80682);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80682);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80682);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017930);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr141 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr141 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017930, 4, 10, 999975, txnNbr141, txnInvoiceNbr141);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr142 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr142 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017930, 10, 100, 999975, txnNbr142, txnInvoiceNbr142);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();


        /*I am an existing CVS EC Customer and I don't want to Redeem a Coupon which is not loaded/printed before with match code 1
         *
         */
        xtraCard.setXtraCardNbr(900017931);
        xtraCard.setCustId(80683);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017931);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80683);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80683);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80683);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80683);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017931);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr151 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr151 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017931, 4, 10, 999975, txnNbr151, txnInvoiceNbr151);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr152 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr152 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017931, 10, 100, 999975, txnNbr152, txnInvoiceNbr152);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and I don't want to Redeem a Coupon which is not loaded/printed before with match code 1
         *
         */
        xtraCard.setXtraCardNbr(900017932);
        xtraCard.setCustId(80684);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017932);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80684);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80684);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80684);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80684);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017932);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr161 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr161 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017932, 4, 10, 999975, txnNbr161, txnInvoiceNbr161);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr162 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr162 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017932, 10, 100, 999975, txnNbr162, txnInvoiceNbr162);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Redeem a Coupon which is already printed before match code 1
         *
         */
        xtraCard.setXtraCardNbr(900017933);
        xtraCard.setCustId(80685);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017933);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80685);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80685);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80685);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80685);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017933);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr171 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr171 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017933, 4, 10, 999975, txnNbr171, txnInvoiceNbr171);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr172 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr172 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017933, 10, 100, 999975, txnNbr172, txnInvoiceNbr172);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();


        /*I am an existing CVS EC Customer and I don't want to Redeem a Coupon which is already redeemed before match code 1
         *
         */
        xtraCard.setXtraCardNbr(900017934);
        xtraCard.setCustId(80686);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017934);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80686);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80686);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80686);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80686);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017934);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr181 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr181 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017934, 4, 10, 999975, txnNbr181, txnInvoiceNbr181);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr182 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr182 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017934, 10, 100, 999975, txnNbr182, txnInvoiceNbr182);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Redeem a Coupon when Redeem actual timestamp is greater than Redeem end date with match code 1
         *
         */
        xtraCard.setXtraCardNbr(900017935);
        xtraCard.setCustId(80691);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017935);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80691);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80691);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80691);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80691);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017935);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr191 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr191 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017935, 4, 10, 999975, txnNbr191, txnInvoiceNbr191);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr192 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr192 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017935, 10, 100, 999975, txnNbr192, txnInvoiceNbr192);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Redeem a Coupon when Redeem actual timestamp is less than Redeem start date with match code 1
         *
         */
        xtraCard.setXtraCardNbr(900017936);
        xtraCard.setCustId(80692);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017936);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80692);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80692);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80692);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80692);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017936);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr201 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr201 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017936, 4, 10, 999975, txnNbr201, txnInvoiceNbr201);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr202 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr202 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017936, 10, 100, 999975, txnNbr202, txnInvoiceNbr202);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and don't want to Redeem a Coupon when Redeem actual timestamp is less than Redeem start date with match code 1
         *
         */
        xtraCard.setXtraCardNbr(900017938);
        xtraCard.setCustId(80700);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017938);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80700);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80700);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80700);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80700);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017938);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr211 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr211 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017938, 4, 10, 999975, txnNbr211, txnInvoiceNbr211);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr212 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr212 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017938, 10, 100, 999975, txnNbr212, txnInvoiceNbr212);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 13
         *
         */
        xtraCard.setXtraCardNbr(900017939);
        xtraCard.setCustId(80701);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017939);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80701);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80701);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80701);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80701);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

          xtraCardSegment.setXtraCardNbr(900017939);
//          xtraCardSegment.setXtraCardSegNbr(194);
          xtraCardSegment.setXtraCardSegNbr(337);
          xtraCardSegment.setCtlGrpCd(null);
          xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr221 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr221 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900017939, 4, 10, 999975, txnNbr221, txnInvoiceNbr221);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr222 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr222 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017939, 10, 100, 999975, txnNbr222, txnInvoiceNbr222);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 14
         *
         */
        xtraCard.setXtraCardNbr(900017940);
        xtraCard.setCustId(80702);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017940);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80702);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80702);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80702);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80702);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017940);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr231 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr231 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900017940, 4, 10, 999975, txnNbr231, txnInvoiceNbr231);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr232 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr232 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017940, 10, 100, 999975, txnNbr232, txnInvoiceNbr232);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 2
         *
         */
        xtraCard.setXtraCardNbr(900058704);
        xtraCard.setCustId(80703);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058704);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80703);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80703);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80703);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80703);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900058704);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr241 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr241 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900058704, 4, 10, 999975, txnNbr241, txnInvoiceNbr241);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr242 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr242 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900058704, 10, 100, 999975, txnNbr242, txnInvoiceNbr242);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 3
         *
         */
        xtraCard.setXtraCardNbr(900017901);
        xtraCard.setCustId(80704);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900017901);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80704);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80704);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80704);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80704);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900017901);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr251 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr251 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900017901, 4, 10, 999975, txnNbr251, txnInvoiceNbr251);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr252 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr252 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900017901, 10, 100, 999975, txnNbr252, txnInvoiceNbr252);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 4
         *
         */
        xtraCard.setXtraCardNbr(900058705);
        xtraCard.setCustId(80705);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058705);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80705);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80705);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80705);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80705);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900058705);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr261 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr261 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900058705, 4, 10, 999975, txnNbr261, txnInvoiceNbr261);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr262 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr262 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900058705, 10, 100, 999975, txnNbr262, txnInvoiceNbr262);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 5
         *
         */
        xtraCard.setXtraCardNbr(900058706);
        xtraCard.setCustId(80706);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058706);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80706);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80706);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80706);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80706);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900058706);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr271 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr271 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900058706, 4, 10, 999975, txnNbr271, txnInvoiceNbr271);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr272 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr272 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900058706, 10, 100, 999975, txnNbr272, txnInvoiceNbr272);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 6
         *
         */
        xtraCard.setXtraCardNbr(900058707);
        xtraCard.setCustId(80707);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058707);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80707);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80707);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80707);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80707);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900058707);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr281 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr281 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900058707, 4, 10, 999975, txnNbr281, txnInvoiceNbr281);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr282 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr282 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900058707, 10, 100, 999975, txnNbr282, txnInvoiceNbr282);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();

        /*I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 7
         *
         */
        xtraCard.setXtraCardNbr(900058708);
        xtraCard.setCustId(80708);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCard.setEncodedXtraCardNbr(900058708);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80708);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80708);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80708);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80708);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(900058708);
//        xtraCardSegment.setXtraCardSegNbr(194);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

//        Integer txnNbr291 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr291 = generateRandom.randomNumberString();
//
//        campaignEarnServiceUtil.hitCampaignEarn(900058708, 4, 10, 999975, txnNbr291, txnInvoiceNbr291);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
//
//        Integer txnNbr292 = generateRandom.randomNumberString();
//        Integer txnInvoiceNbr292 = generateRandom.randomNumberString();
//        campaignEarnServiceUtil.hitCampaignEarn(900058708, 10, 100, 999975, txnNbr292, txnInvoiceNbr292);
//        campaignEarnServiceUtil.getServiceResponse().then().log().all();
        
//        cacheRefreshUtil.refresCacheforCmpgnDefns();

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
     * Delete Test Data for Bulk Coupon Scenario
     */
    public void deleteBulkCpnServiceTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(900058660,900017912,900017913, 900058661, 900058662, 900017916, 900058663, 900017917, 900017918, 900017919, 900017920, 900017921, 900017922, 900058669, 900017025, 900017024, 900058672, 900058673, 900058674, 900058675, 900058676, 900058677, 900058678, 900058679, 900017026, 900017911, 900017930, 900017931, 900017932, 900017933, 900017934, 900058687, 900058688, 900058689, 900058690, 900017935, 900017936, 900058693, 900058694, 900058695, 900017937, 900058697, 900058698, 900058699, 900017938, 900017939, 900017940, 900058704, 900017901, 900058705, 900058706, 900058707, 900058708, 900058709, 900058710);
        List<Integer> custIdList = Arrays.asList(80660, 80661, 80662, 80663, 80664, 80665, 80666, 80667, 80668, 80669, 80670, 80671, 80672, 80673, 80674, 80675, 80676, 80677, 80678, 80679, 80680, 80681, 80682, 80683, 80684, 80685, 80686, 80687, 80688, 80689, 80690, 80691, 80692, 80693, 80694, 80695, 80696, 80697, 80698, 80699, 80700, 80701, 80702, 80703, 80704, 80705, 80706, 80707, 80708, 80709, 80710);
            customerDao.deleteCustomers(custIdList);
           xtraCardDao.deleteXtraCards(xtraCardNbrList);
           campaignDao.deleteCampaignPointRecords(xtraCardNbrList);
           campaignDao.deleteCampaignActivePointBaseRecords(xtraCardNbrList);
           campaignDao.deleteCampaignRewardsRecords(xtraCardNbrList);
    }
}