package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.CampaignOMCoupon;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraCardSegment;
import com.cvs.crm.model.data.XtraHotCard;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.response.GetCustCusInfRespCpnsResponse;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.TestDataUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.internal.OracleTimestampWithTimeZone;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class GetCustService {

    private Response serviceResponse;

    @Autowired
    TestDataUtil testDataUtil;
    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    CampaignDao campaignDao;
    @Autowired
    XtraHotCard xtraHotCard;
    @Autowired
    CampaignCoupon campaignCoupon;
    @Autowired
    CampaignOMCoupon campaignOMCoupon;
    @Autowired
    XtraCardDao xtraCardDao;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    XtraCard xtraCard;
    @Autowired
    Customer customer;
    @Autowired
    CustomerAddress customerAddress;
    @Autowired
    CustomerEmail customerEmail;
    @Autowired
    CustomerPhone customerPhone;
    @Autowired
    CarePassDateUtil carePassDateUtil;
    @Autowired
    XtraCardSegment xtraCardSegment;

    GetCustResponse getCustResponse;

    public String set_typeCd(String cardType) {
        String type_Cd = null;
        switch (cardType.toLowerCase()) {
            case "employeecard":
                type_Cd = "0001";
                break;
            case "xtracard":
                type_Cd = "0002";
                break;
            case "phonenumber":
                type_Cd = "0003";
                break;
            case "encodedxtracard":
                type_Cd = "0004";
                break;
            case "rxc":
                type_Cd = "0005";
                break;
            case "maskedxtracard":
                type_Cd = "0006";
                break;
            default:
                log.info("Incorrect argument passed. cardType = [{}]", cardType);
                break;
        }
        return type_Cd;
    }

    private RequestSpecBuilder getCust_baseUri(GetCustRequest getCustRequest) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri(serviceConfig.getGetcustUrl());
        String msgSrcCd;
        int srcLocCd;
        String userId;
        if (getCustRequest.getChannel().equalsIgnoreCase("M")) {
            msgSrcCd = "M";
            srcLocCd = 90042;
            userId = "MOBILE_ENT";
        } else if (getCustRequest.getChannel().equalsIgnoreCase(("W"))) {
            msgSrcCd = "W";
            srcLocCd = 2695;
            userId = "CVS.COM";
        } else if (getCustRequest.getChannel().equalsIgnoreCase(("R"))){
            msgSrcCd = "R";
            srcLocCd = 68585;
            userId = "STORE";
        } else
            throw new IllegalArgumentException("Invalid Channel passed: " + getCustRequest.getChannel());

        requestSpecBuilder.setBasePath("api/v" + getCustRequest.getVersion() +
                        "/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                .addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("user_id", userId);
        return requestSpecBuilder;
    }

    public Response getCustResponse(GetCustRequest getCustRequest, String node) {
        RequestSpecBuilder requestSpecBuilder = getCust_baseUri(getCustRequest);
        RequestSpecification requestSpecification = requestSpecBuilder.build();
        String getServiceBody = null;

        switch (node.toLowerCase()) {
            case "xtracardciphertxt":
                getServiceBody = getServiceBody_XtraCardCipherTxt(getCustRequest);
                break;
            case "digitizedcpnind":
            case "everdigitizedcpnind":
                getServiceBody = getServiceBody_DigitizedCpnInd(getCustRequest);
                break;
            case "profile":
                getServiceBody = getServiceBody_Profile(getCustRequest);
                break;
            case "pushnotifications":
                getServiceBody = getServiceBody_pushNotifications(getCustRequest);
                break;
            case "mfrcpnavailpool":
                getServiceBody = getServiceBody_mfrCpnAvailPool(getCustRequest);
                break;
            case "cpn":
                getServiceBody = getServiceBody_cpn(getCustRequest);
                break;
            case "extrabuckrewardssummary":
                getServiceBody = getServiceBody_extraBuckRewardsSummary(getCustRequest);
                break;
            case "qebearningtype":
                getServiceBody = getServiceBody_qebEarningType(getCustRequest);
                break;
            case "mktgtypebenefits":
                getServiceBody = getServiceBody_mktgTypeBenefits(getCustRequest);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + node.toLowerCase());
        }
        serviceResponse = given().spec(requestSpecification).contentType("application/json")
                .body(getServiceBody).log().all().post();
//        getServiceResponse().then().statusCode(200);
        log.info("\n GetCust Response Status Code - [{}]", node, serviceResponse.getStatusCode());
        log.info("\n GetCust Response for node - [{}]", node);
        serviceResponse.prettyPrint();
        return serviceResponse;
    }

    private String getServiceBody_XtraCardCipherTxt(GetCustRequest getCustRequest) {
        String json_body = "{\n" +
                "    \"cusInfReq\": {\n" +
                "        \"xtraCard\": [\n" +
                "            \"xtraCardNbr\",\n" +
                "            \"totYtdSaveAmt\",\n" +
                "            \"cardLastScanDt\",\n" +
                "            \"cardMbrDt\",\n" +
                "            \"homeStoreNbr\",\n" +
                "            \"xtraCardCipherTxt\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_pushNotifications(GetCustRequest getCustRequest) {
        String json_body = "{\n" +
                "    \"cusInfReq\": {\n" +
                "        \"pushNotifs\": {\n" +
                "            \"beaconLocId\": 123,\n" +
                "            \"cdcProfileId\": \"123\",\n" +
                "            \"pushNotifUserId\": \"C2Yw7Qw76:UfjevECmlvvHHtC8X:V1\",\n" +
                "            \"userIdType\": \"xtify\",\n" +
                "            \"deviceMetaData\": {\n" +
                "                \"deviceTypeCd\": \"IF\",\n" +
                "                \"deviceVerCd\": \"11.4.1\",\n" +
                "                \"deviceUniqIdTypeCd\": \"VendorID\",\n" +
                "                \"deviceUniqId\": \"EBAB46DD-7D71-4D80-B1E1-CD16F7C94FD6\",\n" +
                "                \"appVer\": \"4.7.1\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        return json_body;
    }
    
    private String getServiceBody_mfrCpnAvailPool(GetCustRequest getCustRequest) {
        String json_body = "{\r\n" + 
        		"    \"cusInfReq\": {\r\n" + 
        		"        \"xtraCard\": [\r\n" + 
        		"            \"xtraCardNbr\"\r\n" + 
        		"        ],\r\n" + 
        		"        \"xtraCare\": [\r\n" + 
        		"            \"mfrCpnAvailPool\"\r\n" + 
        		"        ]        \r\n" + 
        		"    }\r\n" + 
        		"}";
        return json_body;
    }
    
    private String getServiceBody_cpn(GetCustRequest getCustRequest) {
        String json_body = "{\r\n" + 
        		"    \"cusInfReq\": {\r\n" + 
        		"        \"xtraCard\": [\r\n" + 
        		"            \"xtraCardNbr\"\r\n" + 
        		"        ],\r\n" + 
        		"        \"xtraCare\": [\r\n" + 
        		"            \"cpn\"\r\n" + 
        		"        ]\r\n" + 
        		"    } \r\n" + 
        		"}";
        return json_body;
    }
    
    private String getServiceBody_extraBuckRewardsSummary(GetCustRequest getCustRequest) {
        String json_body = "{\r\n" + 
        		"    \"cusInfReq\": {\r\n" + 
        		"        \"xtraCard\": [\r\n" + 
        		"            \"xtraCardNbr\"\r\n" + 
        		"        ],\r\n" + 
        		"        \"xtraCare\": [\r\n" + 
        		"              \"extraBuckRewardsSummary\"\r\n" + 
        		"        ]\r\n" + 
        		"    } \r\n" + 
        		"}";
        return json_body;
    }
    
    private String getServiceBody_qebEarningType(GetCustRequest getCustRequest) {
        String json_body = "{\r\n" + 
        		"    \"cusInfReq\": {\r\n" + 
        		"        \"xtraCard\": [\r\n" + 
        		"            \"xtraCardNbr\"\r\n" + 
        		"        ],\r\n" + 
        		"        \"xtraCare\": [\r\n" + 
        		"              \"qebEarningType\"\r\n" + 
        		"        ]\r\n" + 
        		"    } \r\n" + 
        		"}";
        return json_body;
    }
    
    private String getServiceBody_mktgTypeBenefits(GetCustRequest getCustRequest) {
        String json_body = "{\r\n" + 
        		"    \"cusInfReq\": {\r\n" + 
        		"        \"xtraCard\": [\r\n" + 
        		"            \"xtraCardNbr\"\r\n" + 
        		"        ],\r\n" + 
        		"        \"xtraCare\": [\r\n" + 
        		"              \"mktgTypeBenefits\"\r\n" + 
        		"        ]\r\n" + 
        		"    } \r\n" + 
        		"}";
        return json_body;
    }

    private String getServiceBody_DigitizedCpnInd(GetCustRequest getCustRequest) {
        String json_body = "{\n" +
                "    \"cusInfReq\": {\n" +
                "        \"xtraCard\": [\n" +
                "            \"xtraCardNbr\",\n" +
                "            \"everDigitizedCpnInd\"\n" +
                "        ],\n" +
                "        \"xtraCare\": [\n" +
                "            \"cpns\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        return json_body;
    }

    private String getServiceBody_Profile(GetCustRequest getCustRequest) {
        String json_body = "{\n" +
                "    \"cusInfReq\": {\n" +
                "        \"xtraCard\": [\n" +
                "            \"xtraCardNbr\",\n" +
                "            \"totYtdSaveAmt\",\n" +
                "            \"cardLastScanDt\",\n" +
                "            \"cardMbrDt\",\n" +
                "            \"homeStoreNbr\",\n" +
                "            \"xtraCardCipherTxt\",\n" +
                "            \"everDigitizedCpnInd\"\n" +
                "        ],\n" +
                "        \"xtraCare\": [\n" +
                "            \"mktgTypeBenefits\",\n" +
                "            \"pts\",\n" +
                "            \"cpns\",\n" +
                "            \"mfrCpnAvailPool\",\n" +
                "            \"pebAvailPool\",\n" +
                "            \"hrMembers\",\n" +
                "            \"hrEvtDtl\",\n" +
                "            \"carePassCpns\"\n" +
                "        ],\n" +
                "        \"xtraCarePrefs\": [\n" +
                "            \"optInEc\",\n" +
                "            \"optInEmail\",\n" +
                "            \"beautyClub\",\n" +
                "            \"diabeticClub\",\n" +
                "            \"phr\",\n" +
                "            \"carePass\",\n" +
                "            \"paperlessCpns\",\n" +
                "            \"digitalReceipt\"\n" +
                "        ],\n" +
                "        \"pushNotifs\": {\n" +
                "            \"beaconLocId\": 123,\n" +
                "            \"cdcProfileId\": \"123\",\n" +
                "            \"pushNotifUserId\": \"C2Yw7Qw76:UfjevECmlvvHHtC8X:V1\",\n" +
                "            \"userIdType\": \"xtify\",\n" +
                "            \"deviceMetaData\": {\n" +
                "                \"deviceTypeCd\": \"IF\",\n" +
                "                \"deviceVerCd\": \"11.4.1\",\n" +
                "                \"deviceUniqIdTypeCd\": \"VendorID\",\n" +
                "                \"deviceUniqId\": \"EBAB46DD-7D71-4D80-B1E1-CD16F7C94FD6\",\n" +
                "                \"appVer\": \"4.7.1\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"tables\": [\n" +
                "            {\n" +
                "                \"tableName\": \"customer\",\n" +
                "                \"colNames\": [\n" +
                "                    \"first_name\",\n" +
                "                    \"last_name\",\n" +
                "                    \"middle_initial_txt\",\n" +
                "                    \"birth_dt\",\n" +
                "                    \"gndr_cd\"\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"customer_email\",\n" +
                "                \"colNames\": [\n" +
                "                    \"email_addr_txt\",\n" +
                "                    \"email_addr_type_cd\"\n" +
                "                ],\n" +
                "                \"criteria\": {\n" +
                "                    \"colName\": \"email_pref_seq_nbr\",\n" +
                "                    \"operation\": \"=\",\n" +
                "                    \"value\": \"1\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"customer_phone\",\n" +
                "                \"colNames\": [\n" +
                "                    \"phone_area_cd_nbr\",\n" +
                "                    \"phone_pfx_nbr\",\n" +
                "                    \"phone_type_cd\"\n" +
                "                ],\n" +
                "                \"functionName\": \"LPAD(to_char(phone_sfx_nbr),4,'0')PHONE_SFX_NBR\",\n" +
                "                \"criteria\": {\n" +
                "                    \"colName\": \"phone_pref_seq_nbr\",\n" +
                "                    \"operation\": \"=\",\n" +
                "                    \"value\": \"1\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"customer_address\",\n" +
                "                \"colNames\": [\n" +
                "                    \"addr1_txt\",\n" +
                "                    \"addr2_txt\",\n" +
                "                    \"city_name\",\n" +
                "                    \"st_cd\",\n" +
                "                    \"zip_cd\"\n" +
                "                ],\n" +
                "                \"criteria\": {\n" +
                "                    \"colName\": \"addr_pref_seq_nbr\",\n" +
                "                    \"operation\": \"=\",\n" +
                "                    \"value\": \"1\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"xtra_card_child\",\n" +
                "                \"colNames\": [\n" +
                "                    \"birthday_dt\"\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"xtra_card_analytic_event\",\n" +
                "                \"colNames\": [\n" +
                "                    \"analytic_event_type_cd\",\n" +
                "                    \"cpn_seq_nbr\",\n" +
                "                    \"analytic_event_ts\"\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        return json_body;
    }

    public JsonPath getServiceResponse_jsonPath() {
        return getServiceResponse().jsonPath();
    }

    public List<GetCustCusInfRespCpnsResponse> getCustResponse_Cpns(GetCustResponse getCustResponse, String isDigitized) {
        List<GetCustCusInfRespCpnsResponse> list_Cpn = new ArrayList<>();
        List<GetCustCusInfRespCpnsResponse> list_digitizedCpn = new ArrayList<>();
        List<GetCustCusInfRespCpnsResponse> list_not_digitizedCpn = new ArrayList<>();
        ArrayList<GetCustCusInfRespCpnsResponse> getCpns;
        getCpns = (ArrayList<GetCustCusInfRespCpnsResponse>) getCustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespCpnsResponseList();

        for (GetCustCusInfRespCpnsResponse cpns : getCpns) {
            list_Cpn.add(cpns);
            if (cpns.getDigitizedCpnInd().toString().equalsIgnoreCase("Y")) {
                list_digitizedCpn.add(cpns);
            } else if (cpns.getDigitizedCpnInd().toString().equalsIgnoreCase("N")) {
                list_not_digitizedCpn.add(cpns);
            }
        }
        if (isDigitized.equalsIgnoreCase("Y")) {
            return list_digitizedCpn;
        } else if (isDigitized.equalsIgnoreCase("N")) {
            return list_not_digitizedCpn;
        } else
            return list_Cpn;
    }
    
    public String getCmpgnCpnData(int CmpgnID, int CpnNbr, String value) {
    	List<Map<String, Object>> cmpgn_cpn_data = campaignDao.getCmpgnCpnsData(CmpgnID,CpnNbr);
//    	List<Map<String, Object>> cmpgn_o_m_cpn_data = campaignDao.getCmpgnOMCpnsData(CmpgnID,CpnNbr);
    	String result = null;
    	for(Map<String,Object> map: cmpgn_cpn_data) {
    		if(map.containsKey(value)) {
    			result = map.get(value).toString();
    		}
    	}
		return result;
    }
    
    public String getCmpgnOMCpnData(int CmpgnID, int CpnNbr, String value) {
//    	List<Map<String, Object>> cmpgn_cpn_data = campaignDao.getCmpgnCpnsData(CmpgnID,CpnNbr);
    	List<Map<String, Object>> cmpgn_o_m_cpn_data = campaignDao.getCmpgnOMCpnsData(CmpgnID,CpnNbr);
    	String result = null;
    	for(Map<String,Object> map: cmpgn_o_m_cpn_data) {
    		if(map.containsKey(value)) {
    			result = map.get(value).toString();
    		}
    	}
		return result;
    }
    
    public String getXtraCardActiveCouponData(int XtraCard, String value) {
    	List<Map<String, Object>> xtra_Card_Active_Coupon_data = xtraCardDao.getXtraCardDetails(XtraCard, "XTRA_CARD_ACTIVE_COUPON");
//    	List<Map<String, Object>> cmpgn_o_m_cpn_data = campaignDao.getCmpgnOMCpnsData(CmpgnID,CpnNbr);
    	String result = null;
    	for(Map<String,Object> map: xtra_Card_Active_Coupon_data) {
    		if(map.containsKey(value)) {
    			result = map.get(value).toString();
    		}
    	}
		return result;
    }
    
    
    public void createGetCustMFRAvailPoolAPITestData() throws ParseException, InterruptedException {
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
        
        //  Verify mfrCpnAvailPool when hot XC card number is sent
                
       xtraHotCard.setXtraCardNbr(900058650);
       xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
       xtraCardDao.createXtraHotCard(xtraHotCard);
    }
    
    public void deleteGetCustMFRAvailPoolAPITestData() {
  	  /*
  	    Purge All Test Cards
  	     */
//          List<Integer> xtraCardNbrList = Arrays.asList(900058650);
          List<Integer> xtraCardNbrList = Arrays.asList(900058650);


//          xtraCardDao.deleteXtraCards(xtraCardNbrList);

          xtraCardDao.deleteXtraCards(xtraCardNbrList);
          
      }
    
    public void getCustMFRAvailPoolAPITestDataSetup(int cpn_nbr) throws ParseException, InterruptedException {     

    	DateTime dateTime = new DateTime();
        String tomorrowTimeStamp = dateTime.plusDays(1).toString("dd-MMM-yy hh.mm.ss a");
        System.out.println("Date is : "+tomorrowTimeStamp);

//    	"Verify mfrCpnAvailPool for coupon with everWebRedeemableInd = 'N'"
    	campaignCoupon.setFreeItemInd("Y");
        campaignCoupon.setCmpgnId(43325);
        campaignCoupon.setCpnNbr(cpn_nbr);
        campaignDao.updateCampaignCouponMFRAvailPool(campaignCoupon);
        
//        "Verify mfrCpnAvailPool for coupon with expSoonInd = 'Y'" 
        campaignOMCoupon.setMfrLastRedeemTswtz(tomorrowTimeStamp);
        campaignOMCoupon.setCmpgnId(43325);
        campaignOMCoupon.setCpnNbr(cpn_nbr);
        campaignDao.updateCampaignOMCouponMFRAvailPool(campaignOMCoupon);
        
    }
    
    public void getCustMFRAvailPoolAPITestDataRevert(int cpn_nbr) throws ParseException, InterruptedException {

    	DateTime dateTime = new DateTime();
        String future1yearTimeStamp = dateTime.plusYears(1).toString("dd-MMM-yy hh.mm.ss a");
//        System.out.println("Date is : "+future1yearTimeStamp);
    	
    	//    	"Verify mfrCpnAvailPool for coupon with everWebRedeemableInd = 'N'"
    	campaignCoupon.setFreeItemInd("N");
        campaignCoupon.setCmpgnId(43325);
        campaignCoupon.setCpnNbr(cpn_nbr);
        campaignDao.updateCampaignCouponMFRAvailPool(campaignCoupon);
        
//      "Verify mfrCpnAvailPool for coupon with expSoonInd = 'Y'" 
        campaignOMCoupon.setMfrLastRedeemTswtz(future1yearTimeStamp);
        campaignOMCoupon.setCmpgnId(43325);
        campaignOMCoupon.setCpnNbr(cpn_nbr);
        campaignDao.updateCampaignOMCouponMFRAvailPool(campaignOMCoupon);
  
    }
    
    public void createGetCustExtraBuckRewardsSummaryAPITestData() throws ParseException, InterruptedException {
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
        
        //  Verify mfrCpnAvailPool when hot XC card number is sent
                
       xtraHotCard.setXtraCardNbr(900058650);
       xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
       xtraCardDao.createXtraHotCard(xtraHotCard);
       
       /*Verify extraBuckRewardsSummary node in getCust response at Channel = 'M'
       * Verify extraBuckRewardsSummary node in getCust response for xtra card with no rewards at Channel = 'M'
       */
      xtraCard.setXtraCardNbr(900017210);
      xtraCard.setCustId(80880);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(900017210);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80880);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80880);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80880);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80880);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(900017210);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      /*Verify extraBuckRewardsSummary node in getCust response for xtra card with one coupon at Channel = 'M'
      */
     xtraCard.setXtraCardNbr(900017211);
     xtraCard.setCustId(80881);
     xtraCard.setTotYtdSaveAmt(0.00);
     xtraCard.setTotLifetimeSaveAmt(0.00);
     xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
     xtraCard.setMktgTypeCd(" ");
     xtraCardDao.createXtraCard(xtraCard);
     
     xtraCard.setEncodedXtraCardNbr(900017211);
     xtraCardDao.updateXtraCard(xtraCard);

     customer.setCustId(80881);
     customer.setGndrCd("M");
     customer.setFirstName("John");
     customer.setLastName("Doe");
     customerDao.createCustomer(customer);

     customerEmail.setCustId(80881);
     customerEmail.setEmailAddrTypeCd("H");
     customerEmail.setEmailPrefSeqNbr(1);
     customerEmail.setEmailAddrTxt("abc@CVS.com");
     customerEmail.setEmailStatusCd("A");
     customerDao.createCustomerEmail(customerEmail);

     customerPhone.setCustId(80881);
     customerPhone.setPhoneTypeCd("H");
     customerPhone.setPhonePrefSeqNbr(1);
     customerPhone.setPhoneAreaCdNbr(123);
     customerPhone.setPhonePfxNbr(456);
     customerPhone.setPhoneSfxNbr(7890);
     customerDao.createCustomerPhone(customerPhone);

     customerAddress.setCustId(80881);
     customerAddress.setAddrTypeCd("H");
     customerAddress.setAddrPrefSeqNbr(1);
     customerAddress.setAddr1Txt("951 YORK RD #106");
     customerAddress.setAddr2Txt(" ");
     customerAddress.setCityName("PARMA HTS");
     customerAddress.setStCd("OH");
     customerAddress.setZipCd("44130");
     customerDao.createCustomerAddress(customerAddress);

     xtraCardSegment.setXtraCardNbr(900017211);
     xtraCardSegment.setXtraCardSegNbr(337);
     xtraCardSegment.setCtlGrpCd(null);
     xtraCardDao.createXtraCardSegment(xtraCardSegment);
     
     /*Verify extraBuckRewardsSummary node in getCust response for xtra card with two coupons at Channel = 'M'
     */
    xtraCard.setXtraCardNbr(900017212);
    xtraCard.setCustId(80882);
    xtraCard.setTotYtdSaveAmt(0.00);
    xtraCard.setTotLifetimeSaveAmt(0.00);
    xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
    xtraCard.setMktgTypeCd(" ");
    xtraCardDao.createXtraCard(xtraCard);
    
    xtraCard.setEncodedXtraCardNbr(900017212);
    xtraCardDao.updateXtraCard(xtraCard);

    customer.setCustId(80882);
    customer.setGndrCd("M");
    customer.setFirstName("John");
    customer.setLastName("Doe");
    customerDao.createCustomer(customer);

    customerEmail.setCustId(80882);
    customerEmail.setEmailAddrTypeCd("H");
    customerEmail.setEmailPrefSeqNbr(1);
    customerEmail.setEmailAddrTxt("abc@CVS.com");
    customerEmail.setEmailStatusCd("A");
    customerDao.createCustomerEmail(customerEmail);

    customerPhone.setCustId(80882);
    customerPhone.setPhoneTypeCd("H");
    customerPhone.setPhonePrefSeqNbr(1);
    customerPhone.setPhoneAreaCdNbr(123);
    customerPhone.setPhonePfxNbr(456);
    customerPhone.setPhoneSfxNbr(7890);
    customerDao.createCustomerPhone(customerPhone);

    customerAddress.setCustId(80882);
    customerAddress.setAddrTypeCd("H");
    customerAddress.setAddrPrefSeqNbr(1);
    customerAddress.setAddr1Txt("951 YORK RD #106");
    customerAddress.setAddr2Txt(" ");
    customerAddress.setCityName("PARMA HTS");
    customerAddress.setStCd("OH");
    customerAddress.setZipCd("44130");
    customerDao.createCustomerAddress(customerAddress);

    xtraCardSegment.setXtraCardNbr(900017212);
    xtraCardSegment.setXtraCardSegNbr(337);
    xtraCardSegment.setCtlGrpCd(null);
    xtraCardDao.createXtraCardSegment(xtraCardSegment);

    /*Verify extraBuckRewardsSummary node in getCust response for xtra card with one coupon loaded at Channel = 'M'"
     */
    xtraCard.setXtraCardNbr(900017213);
    xtraCard.setCustId(80883);
    xtraCard.setTotYtdSaveAmt(0.00);
    xtraCard.setTotLifetimeSaveAmt(0.00);
    xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
    xtraCard.setMktgTypeCd(" ");
    xtraCardDao.createXtraCard(xtraCard);
    
    xtraCard.setEncodedXtraCardNbr(900017213);
    xtraCardDao.updateXtraCard(xtraCard);

    customer.setCustId(80883);
    customer.setGndrCd("M");
    customer.setFirstName("John");
    customer.setLastName("Doe");
    customerDao.createCustomer(customer);

    customerEmail.setCustId(80883);
    customerEmail.setEmailAddrTypeCd("H");
    customerEmail.setEmailPrefSeqNbr(1);
    customerEmail.setEmailAddrTxt("abc@CVS.com");
    customerEmail.setEmailStatusCd("A");
    customerDao.createCustomerEmail(customerEmail);

    customerPhone.setCustId(80883);
    customerPhone.setPhoneTypeCd("H");
    customerPhone.setPhonePrefSeqNbr(1);
    customerPhone.setPhoneAreaCdNbr(123);
    customerPhone.setPhonePfxNbr(456);
    customerPhone.setPhoneSfxNbr(7890);
    customerDao.createCustomerPhone(customerPhone);

    customerAddress.setCustId(80883);
    customerAddress.setAddrTypeCd("H");
    customerAddress.setAddrPrefSeqNbr(1);
    customerAddress.setAddr1Txt("951 YORK RD #106");
    customerAddress.setAddr2Txt(" ");
    customerAddress.setCityName("PARMA HTS");
    customerAddress.setStCd("OH");
    customerAddress.setZipCd("44130");
    customerDao.createCustomerAddress(customerAddress);

    xtraCardSegment.setXtraCardNbr(900017213);
    xtraCardSegment.setXtraCardSegNbr(337);
    xtraCardSegment.setCtlGrpCd(null);
    xtraCardDao.createXtraCardSegment(xtraCardSegment);
    
    /*Verify extraBuckRewardsSummary node in getCust response for xtra card with one coupon redeemable at Channel = 'M'"
     */
    xtraCard.setXtraCardNbr(900017220);
    xtraCard.setCustId(80884);
    xtraCard.setTotYtdSaveAmt(0.00);
    xtraCard.setTotLifetimeSaveAmt(0.00);
    xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
    xtraCard.setMktgTypeCd(" ");
    xtraCardDao.createXtraCard(xtraCard);
    
    xtraCard.setEncodedXtraCardNbr(900017220);
    xtraCardDao.updateXtraCard(xtraCard);

    customer.setCustId(80884);
    customer.setGndrCd("M");
    customer.setFirstName("John");
    customer.setLastName("Doe");
    customerDao.createCustomer(customer);

    customerEmail.setCustId(80884);
    customerEmail.setEmailAddrTypeCd("H");
    customerEmail.setEmailPrefSeqNbr(1);
    customerEmail.setEmailAddrTxt("abc@CVS.com");
    customerEmail.setEmailStatusCd("A");
    customerDao.createCustomerEmail(customerEmail);

    customerPhone.setCustId(80884);
    customerPhone.setPhoneTypeCd("H");
    customerPhone.setPhonePrefSeqNbr(1);
    customerPhone.setPhoneAreaCdNbr(123);
    customerPhone.setPhonePfxNbr(456);
    customerPhone.setPhoneSfxNbr(7890);
    customerDao.createCustomerPhone(customerPhone);

    customerAddress.setCustId(80884);
    customerAddress.setAddrTypeCd("H");
    customerAddress.setAddrPrefSeqNbr(1);
    customerAddress.setAddr1Txt("951 YORK RD #106");
    customerAddress.setAddr2Txt(" ");
    customerAddress.setCityName("PARMA HTS");
    customerAddress.setStCd("OH");
    customerAddress.setZipCd("44130");
    customerDao.createCustomerAddress(customerAddress);

    xtraCardSegment.setXtraCardNbr(900017220);
    xtraCardSegment.setXtraCardSegNbr(337);
    xtraCardSegment.setCtlGrpCd(null);
    xtraCardDao.createXtraCardSegment(xtraCardSegment);
    	    
    	    
    }
    
    public void deleteGetCustExtraBuckRewardsSummaryAPITestData() {
  	  /*
  	    Purge All Test Cards
  	  */
    	
          List<Integer> xtraCardNbrList = Arrays.asList(900058650, 900017210, 900017211, 900017212, 900017213, 900017220);
          List<Integer> custIdList = Arrays.asList(80880, 80881, 80882, 80883, 80884);
          
          xtraCardDao.deleteXtraCards(xtraCardNbrList);
          customerDao.deleteCustomers(custIdList);
          
      }
    
    public void createGetCustmktgTypeBenefitsAPITestData() throws ParseException, InterruptedException {
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
        
        //  Verify mfrCpnAvailPool when hot XC card number is sent
                
       xtraHotCard.setXtraCardNbr(900058650);
       xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
       xtraCardDao.createXtraHotCard(xtraHotCard);
       
       /*Verify mktgTypeBenefits node in getCust response at Channel = 'M'
       * Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'E'
       */
      xtraCard.setXtraCardNbr(44380400);
      xtraCard.setCustId(80900);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380400);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80900);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80900);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80900);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80900);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380400);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380400);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "E");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'A'
       */
      xtraCard.setXtraCardNbr(44380401);
      xtraCard.setCustId(80901);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380401);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80901);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80901);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80901);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80901);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380401);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380401);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "A");
    	    
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'D'
       */
      xtraCard.setXtraCardNbr(44380402);
      xtraCard.setCustId(80902);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380402);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80902);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80902);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80902);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80902);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380402);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380402);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "D");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'G1'
       */
      xtraCard.setXtraCardNbr(44380403);
      xtraCard.setCustId(80903);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380403);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80903);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80903);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80903);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80903);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380403);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380403);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "G1");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'G2'
       */
      xtraCard.setXtraCardNbr(44380404);
      xtraCard.setCustId(80904);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380404);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80904);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80904);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80904);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80904);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380404);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380404);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "G2");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'H'
       */
      xtraCard.setXtraCardNbr(44380405);
      xtraCard.setCustId(80905);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380405);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80905);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80905);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80905);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80905);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380405);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380405);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "H");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'M'
       */
      xtraCard.setXtraCardNbr(44380406);
      xtraCard.setCustId(80906);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380406);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80906);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80906);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80906);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80906);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380406);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380406);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "M");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'O'
       */
      xtraCard.setXtraCardNbr(44380407);
      xtraCard.setCustId(80907);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380407);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80907);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80907);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80907);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80907);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380407);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380407);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "O");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'P'
       */
      xtraCard.setXtraCardNbr(44380408);
      xtraCard.setCustId(80908);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380408);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80908);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80908);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80908);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80908);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380408);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380408);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "P");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'S'
       */
      xtraCard.setXtraCardNbr(44380409);
      xtraCard.setCustId(80909);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380409);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80909);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80909);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80909);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80909);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380409);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380409);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "S");
      
      /* Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'W'
       */
      xtraCard.setXtraCardNbr(44380410);
      xtraCard.setCustId(80910);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(44380410);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80910);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80910);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80910);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80910);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(44380410);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(44380410);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "W");
      
      /* Verify mktgTypeBenefits node in getCust response for employee card for MKTG TYPE CD 'E'
       */
      xtraCard.setXtraCardNbr(900017221);
      xtraCard.setCustId(80911);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(900017221);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80911);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80911);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80911);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80911);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(900017221);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCard.setXtraCardNbr(900017221);
      xtraCardDao.updateXtraCard_mktgTypeBenefits(xtraCard, "MKTG_TYPE_CD", "E");


    	    
    }
    
    public void deleteGetCustmktgTypeBenefitsAPITestData() {
  	  /*
  	    Purge All Test Cards
  	  */
    	
    	List<Integer> xtraCardNbrList = Arrays.asList(900058650, 44380400, 44380401, 44380402, 44380403, 44380404, 44380405, 44380406, 44380407, 44380408, 44380409, 44380410, 900017221);
        List<Integer> custIdList = Arrays.asList(80900, 80901, 80902, 80903, 80904, 80905, 80906, 80907, 80908, 80909, 80910, 80911);
          
          xtraCardDao.deleteXtraCards(xtraCardNbrList);
          customerDao.deleteCustomers(custIdList);
          
      }

}
