package com.cvs.crm.util;

import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service
@Data
@Slf4j
public class CampaignEarnServiceUtil {
    private Response serviceResponse;
    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    CarePassDateUtil carePassDateUtil;
    

    public void hitCampaignEarn(int cardNum, int qty, int amt, int sku) throws ParseException {
        int cardNbr = cardNum;
        int scAmt = amt;
        int scQty = qty;
        int scSku = sku;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;
        
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHH:mm:ss");
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//	    System.out.println(sdf3.format(timestamp));

        String jsonString = "{\r\n" +
                " \"rqstCmpgnEarnings\": {\r\n" +
                " \"xtraCardNbr\": " + cardNbr + ",\r\n" +
//                " \"txnTs\": \"2023041715:48:27-04:00\",\r\n" +
				" \"txnTs\":  \"" + sdf3.format(timestamp) + "-04:00\",\r\n" +
                " \"storeNbr\": 2695,\r\n" +
                " \"txnNbr\": 1598736214,\r\n" +
                " \"txnCompleteInd\": true,\r\n" +
                " \"txnInvoiceNbr\": \"36578\", \r\n" +
                " \"itemList\": [{\r\n" +
                " \"skuNbr\": " + scSku + ",\r\n" +
                " \"extndScanAmt\": " + scAmt + ",\r\n" +
                " \"scanQty\": " + scQty + "\r\n" +
                "}],\r\n" +
                " \"verNbr\": 2,\r\n" +
                " \"regNbr\": 222\r\n" +
                "	}\r\n" +
                "}";


        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getCmpgnEarnUrl())
                .setBasePath("api/v1.1/campaign_earnings")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).post();

        int sCode = serviceResponse.getStatusCode();
        String res = String.valueOf(serviceResponse.getBody());
        log.info("CampaignEarnServiceUtil status code: [{}] ", sCode);
        log.info("CampaignEarnServiceUtil status res: [{}] ", res);
    }

    public void hitCampaignEarn(int cardNum, int qty, int amt, int sku, int txnNbr, int txnInvoiceNbr) throws ParseException {
        int cardNbr = cardNum;
        int scAmt = amt;
        int scQty = qty;
        int scSku = sku;
        int scTxnNbr = txnNbr;
        int scTxnInvoiceNbr =  txnInvoiceNbr;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;
        
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHH:mm:ss");
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//	    System.out.println(sdf3.format(timestamp));

        String jsonString = "{\r\n" +
                " \"rqstCmpgnEarnings\": {\r\n" +
                " \"xtraCardNbr\": " + cardNbr + ",\r\n" +
//                " \"txnTs\": \"2023042215:48:27-04:00\",\r\n" +
				" \"txnTs\":  \"" + sdf3.format(timestamp) + "-04:00\",\r\n" +
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

        System.out.println("campaig earn payload: " + jsonString);
        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getCmpgnEarnUrl())
                .setBasePath("api/v1.1/campaign_earnings")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).post();

        int sCode = serviceResponse.getStatusCode();
        String res = String.valueOf(serviceResponse.getBody());
        log.info("CampaignEarnServiceUtil status code: [{}] ", sCode);
        log.info("CampaignEarnServiceUtil status res: [{}] ", res);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}