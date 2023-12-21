package com.cvs.crm.service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignActivePointBase;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.CampaignRewardThreshold;
import com.cvs.crm.model.data.CarepassEnrollFormHist;
import com.cvs.crm.model.data.CarepassMemberSmry;
import com.cvs.crm.model.data.CarepassMemberStatusHist;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerOpt;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.EmployeeCard;
import com.cvs.crm.model.data.HrMemberEnroll;
import com.cvs.crm.model.data.HrMemberHippa;
import com.cvs.crm.model.data.HrMemberProfile;
import com.cvs.crm.model.data.HrMemberSmry;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraCardAnalyticEvent;
import com.cvs.crm.model.data.XtraCardChild;
import com.cvs.crm.model.data.XtraHotCard;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.request.RHBCouponRequest;
import com.cvs.crm.model.request.StoreEBRequest;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.HRDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class RHBCouponsService {

    private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    CarePassEnrollmentUtil carePassEnrollmentUtil;

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


    public void issueRHBCoupons(RHBCouponRequest rhbCouponRequest) throws ParseException {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd = null;
        String userId = null;
        int srcLocCd = 0;

        msgSrcCd = "M";
        userId = "MOBILE_ENT";
        srcLocCd = 590;
        
        String jsonString = "{\r\n" +
                "\"cardNbr\": \""+rhbCouponRequest.getSearchCardNbr()+"\",\r\n" +
                "\"cardType\":  \""+rhbCouponRequest.getSearchCardType()+"\"\r\n" +
                "}";
        
        requestSpecBuilder.setBaseUri(serviceConfig.getCpnapiUrl())
                .setBasePath("api/v1.2/coupons/carepass")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("prg_type", rhbCouponRequest.getProgType_Code())
                .addQueryParam("cpn_event_type", rhbCouponRequest.getCouponEventType());

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).log().all().contentType("application/json").body(jsonString).post();
    }


    public void viewStoreEBCreation(StoreEBRequest storeEBRequest, Double amount) throws ParseException {
        Integer cardNum = storeEBRequest.getSearchCardNbr();
        String cardTyp = storeEBRequest.getSearchCardType();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
        Double refundamount = amount;

        if ("WEB".equalsIgnoreCase(storeEBRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;

            String jsonString = "{\r\n" +
                    "\"regRelease\":  1,\r\n" +
                    "\"storeTswtz\": \"2020122008:00:0002:00\",\r\n" +
                    "\"rqstAmt\": " + refundamount + ",\n" +
                    "\"rsnCd\": \"RF\",\r\n" +
                    "\"origTxnDt\":  \"20201015\",\r\n" +
                    "\"origTxnNbr\":  7031,\r\n" +
                    "\"origCmpgnId\": 19700\r\n" +
                    "}";


            requestSpecBuilder.setBaseUri(serviceConfig.getCpnapiUrl())
                    .setBasePath("api/v1.2/coupons/{search_card_type},{search_card_nbr}")
                    .addPathParam("search_card_type", cardTyp)
                    .addPathParam("search_card_nbr", cardNum)
                    .addQueryParam("msg_src_cd", msgSrcCd)
                    .addQueryParam("user_id", userId)
                    .addQueryParam("src_loc_cd", srcLocCd);

            RequestSpecification spec = requestSpecBuilder.build();
            serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
        } else if ("STORE".equalsIgnoreCase(storeEBRequest.getChannel())) {
            msgSrcCd = "R";
            userId = "store";
            srcLocCd = 90037;
            Integer mgr = 12345678;
            Integer emp = 999999999;
            Integer strNbr = 12345;
            Integer regNbr = 2;

            String jsonString = "{\r\n" +
                    "\"regRelease\":  1,\r\n" +
                    "\"storeTswtz\": \"2020122008:00:0002:00\",\r\n" +
                    "\"mgrId\": " + mgr + ",\n" +
                    "\"empId\": " + emp + ",\n" +
                    "\"rqstAmt\": " + refundamount + ",\n" +
                    "\"rsnCd\": \"RF\",\r\n" +
                    "\"origTxnDt\":  \"20201015\",\r\n" +
                    "\"origStrNbr\": " + strNbr + ",\n" +
                    "\"origRegNbr\": " + regNbr + ",\n" +
                    "\"origTxnNbr\":  7031,\r\n" +
                    "\"origCmpgnId\": 19700\r\n" +
                    "}";

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
    }

}