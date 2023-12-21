package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.request.CpnsRequest;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.DateUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class CpnsService {

    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    DateUtil dateUtil;
    @Autowired
    XtraCardDao xtraCardDao;
    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    XtraCard xtraCard;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private RequestSpecBuilder cpns_baseUri(CpnsRequest cpnsRequest, String cpns_action) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri(serviceConfig.getCpnapiUrl());
        String msgSrcCd;
        int srcLocCd;
        String userId;

        if (cpnsRequest.getChannel().equalsIgnoreCase("M")) {
            msgSrcCd = "M";
            srcLocCd = 90042;
            userId = "MOBILE_ENT";
        } else if (cpnsRequest.getChannel().equalsIgnoreCase(("W"))) {
            msgSrcCd = "W";
            srcLocCd = 2695;
            userId = "CVS.COM";
        } else {
            msgSrcCd = "R";
            srcLocCd = 68585;
            userId = "STORE";
        }
        requestSpecBuilder.setBasePath("api/v" + cpnsRequest.getVersion() + "/coupons/{cpn_seq_nbr}?")
                .addPathParam("cpn_seq_nbr", cpnsRequest.getCpnSeqNbr())
                .addQueryParam("xtra_card_nbr", cpnsRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("user_id", userId);
        if (cpns_action.equalsIgnoreCase("digitize")) {
            requestSpecBuilder.addQueryParam("referrer_cd", "P");
        }
        return requestSpecBuilder;
    }

    public Response cpnResponse(CpnsRequest cpnsRequest, String cpns_action){
        RequestSpecBuilder requestSpecBuilder = cpns_baseUri(cpnsRequest, cpns_action);
        RequestSpecification requestSpecification = requestSpecBuilder.build();
        String getServiceBody = null;

        if (cpns_action.equalsIgnoreCase("view")) {
            cpnsRequest.setOpCd('V');
            cpnsRequest.setTs(dateUtil.timeStamp(-5, "yyyyMMddHH:mm:ss") + "-05:00");
        } else if (cpns_action.equalsIgnoreCase("print")) {
            cpnsRequest.setOpCd('P');
            cpnsRequest.setTs(dateUtil.timeStamp(-4, "yyyyMMddHH:mm:ss") + "-05:00");
        } else if (cpns_action.equalsIgnoreCase("load")) {
            cpnsRequest.setOpCd('L');
            cpnsRequest.setTs(dateUtil.timeStamp(-2, "yyyyMMddHH:mm:ss") + "-05:00");
        } else if (cpns_action.equalsIgnoreCase("redeem")) {
            cpnsRequest.setOpCd('R');
            // default value
            cpnsRequest.setRedeemActlAmt("-0003.29");
            cpnsRequest.setRedeemActlCashierNbr(46423);
            cpnsRequest.setMatchCd(1);
            cpnsRequest.setTs(dateUtil.timeStamp(-1, "yyyyMMddHH:mm:ss") + "-05:00");
        } else if (cpns_action.equalsIgnoreCase("digitize")) {
            cpnsRequest.setOpCd('L');
            cpnsRequest.setTs(dateUtil.timeStamp(-3, "yyyyMMddHH:mm:ss") + "-05:00");
        }
        switch (cpns_action.toLowerCase()) {
            case "view":
            case "print":
            case "load":
            case "digitize":
                getServiceBody = getServiceBody_singleCoupon(cpnsRequest);
                break;
            case "redeem":
                getServiceBody = getServiceBody_redeemSingleCoupon(cpnsRequest);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cpns_action.toLowerCase());
        }
        log.info("Request Body: [{}] ", getServiceBody);
        serviceResponse = given().spec(requestSpecification).contentType("application/json")
                .body(getServiceBody).log().all().patch();
//        getServiceResponse().then().statusCode(200);
        log.info("\n Cpn Response when [{}]. Status Code - [{}]", cpns_action, serviceResponse.getStatusCode());
        log.info("\n Response When Cpn [{}] :", cpns_action);
        serviceResponse.prettyPrint();
        return serviceResponse;
    }
    // View (or) Print (or) Load Single Coupon
    private String getServiceBody_singleCoupon(CpnsRequest cpnsRequest) {
        String json_body = "{\n" +
                "    \"cmpgnId\": " + cpnsRequest.getCmpgnId() + ",\n" +
                "    \"cpnSeqNbr\": " + cpnsRequest.getCpnSeqNbr() + ",\n" +
                "    \"cpnSkuNbr\": " + cpnsRequest.getCpnSkuNbr() + ",\n" +
                "    \"opCd\": \"" + cpnsRequest.getOpCd() + "\",\n" +
                "    \"ts\": \"" + cpnsRequest.getTs() + "\"\n" +
                "}";
        return json_body;
    }
    private String getServiceBody_redeemSingleCoupon(CpnsRequest cpnsRequest) {
        String json_body = "{\n" +
                "    \"cmpgnId\": " + cpnsRequest.getCmpgnId() + ",\n" +
                "    \"cpnSeqNbr\": " + cpnsRequest.getCpnSeqNbr() + ",\n" +
                "    \"cpnSkuNbr\": " + cpnsRequest.getCpnSkuNbr() + ",\n" +
                "    \"matchCd\": " + cpnsRequest.getMatchCd() + ",\n" +
                "    \"opCd\": \"" + cpnsRequest.getOpCd() + "\",\n" +
                "    \"redeemActlAmt\": \"" + cpnsRequest.getRedeemActlAmt() + "\",\n" +
                "    \"redeemActlCashierNbr\": " + cpnsRequest.getRedeemActlCashierNbr() + ",\n" +
                "    \"ts\": \"" + cpnsRequest.getTs() + "\"\n" +
                "}";
        return json_body;
    }

    public void getCoupon_Eligible_for_Digitize(CpnsRequest cpnsRequest) throws ParseException {
        xtraCardActiveCoupon.setXtraCardNbr(cpnsRequest.getSearchCardNbr());
        xtraCardActiveCoupon.setCmpgnId(cpnsRequest.getCmpgnId());
        xtraCardActiveCoupon.setLoadActlTswtz(null);
        xtraCardActiveCoupon.setLoadActlTswtzSetDt(null);
        xtraCardActiveCoupon.setRedeemActlTswtz(null);
        xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
        xtraCardActiveCoupon.setCpnSrcCd("R");
        xtraCardActiveCoupon.setCpnCreateDt(dateUtil.setTswtzHour(-10,0));
        xtraCardActiveCoupon.setPrntStartTswtz(dateUtil.setTswtzHour(-10,0));
        xtraCardActiveCoupon.setPrntEndTswtz(dateUtil.setTswtzHour(20,0));
        xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);
    }

}
