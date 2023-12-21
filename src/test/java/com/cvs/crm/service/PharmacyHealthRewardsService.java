package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.CampaignRewardThreshold;
import com.cvs.crm.model.data.CampaignActivePointBase;
import com.cvs.crm.model.data.HrMemberProfile;
import com.cvs.crm.model.data.HrMemberSmry;
import com.cvs.crm.model.data.HrMemberEnroll;
import com.cvs.crm.model.data.HrMemberHippa;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.HREnrollRequest;
import com.cvs.crm.model.request.HREventRequest;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.HRDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
import lombok.extern.slf4j.Slf4j;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.PhrDateUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Data
@Slf4j
public class PharmacyHealthRewardsService {
    private Response serviceResponse;

    @Autowired
    CacheRefreshUtil cacheRefreshUtil;

    @Autowired
    PhrDateUtil phrDateUtil;
    
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
    Campaign campaign;

    @Autowired
    CampaignCoupon campaignCoupon;

    @Autowired
    CampaignRewardThreshold campaignRewardThreshold;

    @Autowired
    CampaignActivePointBase campaignActivePointBase;

    @Autowired
    HrMemberProfile hrMemberProfile;

    @Autowired
    HrMemberSmry hrMemberSmry;

    @Autowired
    HrMemberEnroll hrMemberEnroll;

    @Autowired
    HrMemberHippa hrMemberHippa;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraParms xtraParms;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    HRDao hRDao;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    public void viewPharmacyHealthRewards(DashBoardRequest dashBoardRequest) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;

        //TODO - We need a Utility Method to determine attributes
//        if ("MOBILE".equalsIgnoreCase(dashBoardRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
//        } else {
//            msgSrcCd = "W";
//            userId = "CVS.COM";
//            srcLocCd = 2695;
//        }

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
     * Phr Enroll service
     *
     * @throws InterruptedException
     */
    
    public void phrEnroll1(HREnrollRequest request) {
    	
    	XtraCard xtracard = new XtraCard();
    	
		Integer xtracard1 = request.getSearchCardNbr();
		String cardType = request.getSearchCardType();
		String encodedXtraCard = request.getSearchEncodedXtraCardNbr();
		String msgSrcCd = "S";
		String userId = "STORE";
		int srcLocCd = 590;
		String jsonString = null;
		
//		jsonString = "{\r\n" + "	\"xtraCare\": {\r\n" + "		\"carepass\": {\r\n"
//				+ "			\"tables\": [{\r\n" + "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n"
//				+ "				\"rows\": [{\r\n" + "					\"action\": \"insert\",\r\n"
//				+ "					\"colData\": {\r\n" + "						\"actionTswtz\":  \""
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(0, 4)
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(5, 7)
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n"
//				+ "						\"actionTswtzSetDt\": \"sysdate\",\r\n"
//				+ "						\"pymtPlanDur\": \"1\"\r\n" + "					}\r\n"
//				+ "				}]\r\n" + "			},\r\n" + "			{\r\n"
//				+ "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" + "				\"rows\": [{\r\n"
//				+ "					\"action\": \"insert\",\r\n" + "					\"colData\": {\r\n"
//				+ "						\"actionTswtz\": \""
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(0, 4)
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(5, 7)
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n"
//				+ "						\"actionTswtzSetDt\": \"sysdate\",\r\n"
//				+ "						\"mbrStatusCd\": \"E\",\r\n"
//				+ "						\"mbrStatusRsnCd\": \"\"\r\n" + "					}\r\n"
//				+ "				}]\r\n" + "			},\r\n" + "			{\r\n"
//				+ "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" + "				\"rows\": [{\r\n"
//				+ "					\"action\": \"delete_insert\",\r\n" + "					\"colData\": {\r\n"
//				+ "						\"enrollTswtz\": \""
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(0, 4)
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(5, 7)
//				+ (carePassDateUtil.carePassExpireTswtz(0)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n"
//				+ "						\"curStatus\": \"E\",\r\n"
//				+ "						\"curPlanType\": \"M\"\r\n" + "					}\r\n"
//				+ "				}]\r\n" + "			}]\r\n" + "		}\r\n" + "	}\r\n" + "}";
		jsonString = "{\r\n" +
	            "    \"actionCd\": \"E\",\r\n" +
	            "    \"idTypeCd\": \"R\",\r\n" +
	            "    \"idNbr\": 123456,\r\n" +
	            "    \"cardTypeCd\": \"" + cardType + "\",\r\n" +
	            "    \"cardNbr\": \"" + encodedXtraCard + "\",\r\n" +
	            "    \"hippaFormVerNbr\": \"2\",\r\n" +
	            "    \"ts\": \""+ (phrDateUtil.phrEnrollmentTS()).substring(0, 4)
	            + (phrDateUtil.phrEnrollmentTS()).substring(5, 7)
	            + (phrDateUtil.phrEnrollmentTS()).substring(8, 10) + "00:00:00-0500" + "\"\r\n" +
	            "}";
		System.out.println("input payload : " + jsonString);

		RequestSpecBuilder requestSpecBuilder1 = new RequestSpecBuilder();
		requestSpecBuilder1.setBaseUri(serviceConfig.getHrenrollUrl())
				.setBasePath("api/v1.1/HREnrollments")
//				.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
				.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
				.addQueryParam("src_loc_cd", srcLocCd);

		RequestSpecification spec1 = requestSpecBuilder1.build();
		System.out.println("Link is " + spec1.toString());
		serviceResponse = (Response) given().spec(spec1).contentType("application/json").body(jsonString).post();
		int sCode = serviceResponse.getStatusCode();

		String res = serviceResponse.toString();
		log.info("phrEnroll status code: [{}] ", sCode +" Service Response: "+res);
		
		String resbody = serviceResponse.jsonPath().getString("xtraCardNbr");
		System.out.println("body is " + resbody);
	}
    
public void phrEnroll(HREnrollRequest request) throws InterruptedException {
    	
//    	XtraCard xtracard = new XtraCard();
    	
		Integer xtracard = request.getSearchCardNbr();
		String cardType = request.getSearchCardType();
		String encodedXtraCard = request.getSearchEncodedXtraCardNbr();
		String actionCode = request.getActionCode();
		String idNumber = request.getIdNumber();
//		Integer cardType = request.getCardType();
//		String msgSrcCd = "S";
//		String userId = "STORE";
//		int srcLocCd = 590;
		String msgSrcCd;
        String userId;
        int srcLocCd;
        String card = null;

        //TODO - We need a Utility Method to determine attributes
        if ("MOBILE".equalsIgnoreCase(request.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else if ("WEB".equalsIgnoreCase(request.getChannel())){
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else {
            msgSrcCd = "S";
            userId = "STORE";
            srcLocCd = 590;
        }
        
        if ("0002".equalsIgnoreCase(cardType)) {
        	card = xtracard.toString();
        } else if (("0004".equalsIgnoreCase(cardType))) {
        	card = encodedXtraCard;
        }
        
		String jsonString = null;
		
		jsonString = "{\r\n" +
	            "    \"actionCd\": \"" + actionCode + "\",\r\n" +
	            "    \"idTypeCd\": \"R\",\r\n" +
	            "    \"idNbr\": \"" + idNumber + "\",\r\n" +
	            "    \"cardTypeCd\": \"" + cardType + "\",\r\n" +
	            "    \"cardNbr\": \"" + card + "\",\r\n" +
	            "    \"hippaFormVerNbr\": \"2\",\r\n" +
	            "    \"ts\": \""+ (phrDateUtil.phrEnrollmentTS()).substring(0, 4)
	            + (phrDateUtil.phrEnrollmentTS()).substring(5, 7)
	            + (phrDateUtil.phrEnrollmentTS()).substring(8, 10) + "00:00:00-0500" + "\"\r\n" +
	            "}";
		System.out.println("input payload : " + jsonString);

		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(serviceConfig.getHrenrollUrl())
				.setBasePath("api/v1.1/HREnrollments")
//				.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
				.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
				.addQueryParam("src_loc_cd", srcLocCd);

		RequestSpecification spec = requestSpecBuilder.build();
//		System.out.println("Link is " + spec.toString());
		serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
		int sCode = serviceResponse.getStatusCode();

		String res = serviceResponse.toString();
		log.info("phrEnroll status code: [{}] ", sCode +" Service Response: "+res);
		
//		String resbody = serviceResponse.jsonPath().getString("errorMsg");
//		System.out.println("body is " + res.toString());
		Thread.sleep(3000);
	}

public void getCust(HREnrollRequest request) {
	
//	XtraCard xtracard = new XtraCard();
	
	Integer xtracard = request.getSearchCardNbr();
	String cardType = request.getSearchCardType();
	String encodedXtraCard = request.getSearchEncodedXtraCardNbr();
	String msgSrcCd = "M";
	String userId = "MOBILE_ENT";
	int srcLocCd = 90046;
	String jsonString = null;
	
	jsonString = "{\r\n" +
            "    \"cusInfReq\": {\r\n" +
            "    \"xtraCarePrefs\": [\r\n" +
            "    \"phr\"\r\n" +
            "	 ]\r\n" + "	}\r\n" + "}";
            
	System.out.println("input payload : " + jsonString);

	RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
	requestSpecBuilder.setBaseUri(serviceConfig.getGetcustUrl())
			.setBasePath("api/v1.2/customers/0002," + xtracard)
//			.addPathParam("search_card_type", "002").addPathParam("search_card_nbr", xtracard)
			.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
			.addQueryParam("src_loc_cd", srcLocCd);

	RequestSpecification spec = requestSpecBuilder.build();
//	System.out.println("Link is " + spec.toString());
	serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
	int sCode = serviceResponse.getStatusCode();

	String res = serviceResponse.toString();
	log.info("Get Cust status code: [{}] ", sCode +" Service Response: "+res);
	
//	String resbody = serviceResponse.jsonPath().getString("cusInfResp.xtraCarePrefs.phr.enrolled");
//	System.out.println("body is " + resbody);
	
//	Map<String, String> company = serviceResponse.jsonPath().getMap("phr");
//	System.out.println("body is " + company.get("enrolled"));
}

public void getCustPHRNode(GetCustRequest getcustrequest) {
		
//	Integer xtracard = getcustrequest.getSearchCardNbr();
	String cardType = getcustrequest.getSearchCardType();
//	String encodedXtraCard = getcustrequest.getSearchEncodedXtraCardNbr();
	Integer idNumber = getcustrequest.getSearchCardNbr();
	String msgSrcCd;
	String userId;
	int srcLocCd;
	String card = null;
	
	if ("MOBILE".equalsIgnoreCase(getcustrequest.getChannel())) {
        msgSrcCd = "M";
        userId = "MOBILE_ENT";
        srcLocCd = 90042;
    } else if ("WEB".equalsIgnoreCase(getcustrequest.getChannel())){
        msgSrcCd = "W";
        userId = "CVS.COM";
        srcLocCd = 2695;
    } else if ("STORE".equalsIgnoreCase(getcustrequest.getChannel())){
        msgSrcCd = "S";
        userId = "STORE";
        srcLocCd = 590;
    } else {
        msgSrcCd = "PD";
        userId = "CVS.COM";
        srcLocCd = 2695;
    }
	
System.out.println("msg src code is: "+msgSrcCd);
	String jsonString = null;
	
	jsonString = "{\r\n" +
            "    \"cusInfReq\": {\r\n" +
            "    \"xtraCare\": [\r\n" +
            "    \"hrEvtDtl\",\r\n" +
            "    \"hrEnrollStatus\"\r\n" +
            "	 ],\r\n" + 
            "    \"xtraCarePrefs\": [\r\n" +
            "    \"phrEnroll\",\r\n" +
            "    \"phr\"\r\n" +
            "	 ]\r\n" + "	}\r\n" + "}";
            
	System.out.println("input payload : " + jsonString);

	RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
	requestSpecBuilder.setBaseUri(serviceConfig.getGetcustUrl())
			.setBasePath("api/v1.2/customers/0005," + idNumber)
//			.addPathParam("search_card_type", "002").addPathParam("search_card_nbr", xtracard)
			.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
			.addQueryParam("src_loc_cd", srcLocCd);

	RequestSpecification spec = requestSpecBuilder.build();
	serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
	int sCode = serviceResponse.getStatusCode();

	String res = serviceResponse.toString();
	log.info("Get Cust status code: [{}] ", sCode +" Service Response: "+res);

}

public void phrEvent(HREventRequest request) throws InterruptedException {
	
//	XtraCard xtracard = new XtraCard();
	
//	Integer xtracard = request.getSearchCardNbr();
//	String cardType = request.getSearchCardType();
//	String encodedXtraCard = request.getSearchEncodedXtraCardNbr();
//	String actionCode = request.getActionCode();
	String idNumber = request.getIdNumber();
	String ephid = request.getEPHID();
	String idType = request.getIdType();
	String eventTypeCode = request.getEventTypeCode();
	String eventEarnCode = request.getEventEarnCode();
	String eventEarnReasonCode = request.getEventEarnReasonCode();
//	Integer cardType = request.getCardType();
//	String msgSrcCd = "W";
//	String userId = "CVS.COM";
//	int srcLocCd = 2137;
	String msgSrcCd;
    String userId;
    int srcLocCd;
    String card = null;
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    //TODO - We need a Utility Method to determine attributes
    if ("MOBILE".equalsIgnoreCase(request.getChannel())) {
        msgSrcCd = "M";
        userId = "MOBILE_ENT";
        srcLocCd = 90042;
    } else if ("WEB".equalsIgnoreCase(request.getChannel())){
        msgSrcCd = "W";
        userId = "CVS.COM";
        srcLocCd = 2137;
    } else {
        msgSrcCd = "S";
        userId = "STORE";
        srcLocCd = 590;
    }
    
//    if ("0002".equalsIgnoreCase(cardType)) {
//    	card = xtracard.toString();
//    } else if (("0004".equalsIgnoreCase(cardType))) {
//    	card = encodedXtraCard;
//    }
    
	String jsonString = null;
	
//	if ((eventTypeCode == "CF") || (eventTypeCode == "CS") || (eventTypeCode == "CM") || (eventTypeCode == "CR"))
	if ("CF".equalsIgnoreCase(eventTypeCode) || "CS".equalsIgnoreCase(eventTypeCode) || "CM".equalsIgnoreCase(eventTypeCode) || "CR".equalsIgnoreCase(eventTypeCode))
	{
	jsonString = "{\r\n" +
            "    \"ephId\": \"" + ephid + "\",\r\n" +
            "    \"idNbr\": \"" + idNumber + "\",\r\n" +
            "    \"idType\": \"" + idType + "\",\r\n" +
            "    \"eventTypeCd\": \"" + eventTypeCode + "\",\r\n" +
            "    \"evntEarnCd\": \"" + eventEarnCode + "\",\r\n" +
            "    \"evntEarnRsnCd\": \"" + eventEarnReasonCode + "\",\r\n" +
            "    \"scriptNbr\": \"59999\",\r\n" +
            "    \"eventTs\": \"" + sdf3.format(timestamp) + "\"\r\n" +
            "}";
	System.out.println("input payload : " + jsonString);
	} else if ("RD".equalsIgnoreCase(eventTypeCode)) {
		jsonString = "{\r\n" +
	            "    \"ephId\": \"" + ephid + "\",\r\n" +
	            "    \"idNbr\": \"" + idNumber + "\",\r\n" +
	            "    \"idType\": \"" + idType + "\",\r\n" +
	            "    \"eventTypeCd\": \"" + eventTypeCode + "\",\r\n" +
	            "    \"eventTs\": \"" + sdf3.format(timestamp) + "\",\r\n" +
	            "    \"miscInfo\": {\r\n" +
	            "    \"shippingAddrStateCd\": \"MA\",\r\n" +
	            "    \"storeAddrStateCd\": \"RI\"\r\n" +
				"    }\r\n" +
	            "}";
		System.out.println("input payload : " + jsonString);	
	} else {
		jsonString = "{\r\n" +
	            "    \"ephId\": \"" + ephid + "\",\r\n" +
	            "    \"idNbr\": \"" + idNumber + "\",\r\n" +
	            "    \"idType\": \"" + idType + "\",\r\n" +
	            "    \"eventTypeCd\": \"" + eventTypeCode + "\",\r\n" +
	            "    \"evntEarnCd\": \"" + eventEarnCode + "\",\r\n" +
	            "    \"evntEarnRsnCd\": \"" + eventEarnReasonCode + "\",\r\n" +
	            "    \"scriptNbr\": \"59999\",\r\n" +
	            "    \"eventTs\": \"" + sdf3.format(timestamp) + "\"\r\n" +
	            "}";
		System.out.println("input payload : " + jsonString);
	}

	RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
	requestSpecBuilder.setBaseUri(serviceConfig.getHreventsUrl())
			.setBasePath("api/v1.1/hr_events")
//			.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
			.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
			.addQueryParam("src_loc_cd", srcLocCd);
//
	RequestSpecification spec = requestSpecBuilder.build();
	serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
//	int sCode = serviceResponse.getStatusCode();
//
//	String res = serviceResponse.toString();
//	log.info("phrEnroll status code: [{}] ", sCode +" Service Response: "+res);
//	
//	Thread.sleep(3000);
}



    /**
     * Create Test Data For Pharmacy Health Rewards Scenario
     *
     * @throws InterruptedException
     */
    
	public void createhRMemberProfileAndcampaignActivePointBaseTestData() throws ParseException, InterruptedException {
	
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
	
	    /*
	     * Create HR Member Profile
	     */
	    hrMemberProfile.setEphLinkId(87654321);
	    hrMemberProfile.setFirstName("Hari");
	    hrMemberProfile.setLastName("Veeram2");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654322);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram3");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654323);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram4");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654324);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram5");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654325);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram6");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654326);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram6");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654327);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram7");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654328);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram7");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654329);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram8");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654330);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram8");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654331);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram9");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654332);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram10");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654333);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram10");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654334);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram11");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654335);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram11");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654336);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram12");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654337);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram13");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654338);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram14");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654339);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram15");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654340);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram16");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654341);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram17");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654342);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram18");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654343);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram19");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654344);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram20");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654345);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram21");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654346);
	    hrMemberProfile.setFirstName("Hari1");
	    hrMemberProfile.setLastName("Veeram22");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
	    hrMemberProfile.setEphLinkId(87654347);
	    hrMemberProfile.setFirstName("Hari2");
	    hrMemberProfile.setLastName("Veeram23");
	    hrMemberProfile.setPrefPhoneNbr(null);
	    hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
	    hRDao.createHrMemberProfile(hrMemberProfile);
	    
//	    Campaign Active Point Base
	    
	    campaignActivePointBase.setXtraCardNbr(298344809);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(6.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344810);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(16.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344811);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(103.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344813);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(6.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344814);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(203.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344953);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(6.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344955);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(6.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344957);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(6.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298344993);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(3.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298345035);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(7.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298345036);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(103.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298345054);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(7.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298345055);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(103.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298345056);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(3.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298345057);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(3.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
	    campaignActivePointBase.setXtraCardNbr(298345058);
	    campaignActivePointBase.setCmpgnId(44497);
	    campaignActivePointBase.setPtsQty(3.0);
	    campaignActivePointBase.setActivationTs(simpleDateFormatTs.parse(todayTimeStamp));
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	    
//	    Create HR Member Summary
	    
	    hrMemberSmry.setEphLinkId(87654331);
        hrMemberSmry.setXtraCardNbr(298344953);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654332);
        hrMemberSmry.setXtraCardNbr(298344955);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654333);
        hrMemberSmry.setXtraCardNbr(298344955);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd(null);
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654334);
        hrMemberSmry.setXtraCardNbr(298344957);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654335);
        hrMemberSmry.setXtraCardNbr(298344957);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654343);
        hrMemberSmry.setXtraCardNbr(298345056);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654344);
        hrMemberSmry.setXtraCardNbr(298345057);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654345);
        hrMemberSmry.setXtraCardNbr(298345057);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd(null);
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654346);
        hrMemberSmry.setXtraCardNbr(298345058);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
        
        hrMemberSmry.setEphLinkId(87654347);
        hrMemberSmry.setXtraCardNbr(298345058);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setLastUpdtTs(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberSmry(hrMemberSmry);
	}
	
	public void campaignActivePointBaseTestData(double points) throws ParseException, InterruptedException {
		
		campaignActivePointBase.setXtraCardNbr(298344419);
	    campaignActivePointBase.setCmpgnId(44496);
	    campaignActivePointBase.setPtsQty(points);
	    campaignActivePointBase.setActivationTs(null);
	    campaignActivePointBase.setActivationSrcCd(null);
	    campaignDao.createCampaignActivePointBase(campaignActivePointBase);
	}
	
	
	public void deleteHRMemberProfileAndCampaignActivePointBaseTestData() {
		  /*
		    Purge All Test Cards
		     */
	        List<Integer> xtraCardNbrList = Arrays.asList(298344807,298344808,298344809,298344810,298344811,298344812,298344813,298344814,298344953,298344955,298344957,298344993,298345035,298345036,298345054,298345055,298345056,298345057,298345058);
//	        List<Integer> custIdList = Arrays.asList(79998, 79999, 80000, 80001, 80002, 80003, 80004, 80005, 80006, 80007, 80008, 80009, 80010, 80011, 80012, 80013, 80014, 80015, 80016, 80017, 80018, 80019, 80020, 80021, 80022, 80023, 80024, 80025, 80026, 80027, 80028, 80029, 80030, 80031);
	        List<Integer> cmpgnIdList = Arrays.asList(44496);
	        List<Integer> ephLinkIdList = Arrays.asList(87654321,87654322,87654323,87654324,87654325,87654326,87654327,87654328,87654329,87654330,87654331,87654332,87654333,87654334,87654335,87654336,87654337,87654338,87654339,87654340,87654341,87654342,87654343,87654344,87654345,87654346,87654347);
	        List<Integer> ephLinkIdList1 = Arrays.asList(87654331,87654332,87654333,87654334,87654335,87654343,87654344,87654345,87654346,87654347);
	        campaignDao.deleteCampaignActivePointBaseRecords(xtraCardNbrList);
//	        customerDao.deleteCustomers(custIdList);
	        hRDao.deleteHRMemberProfiles(ephLinkIdList);
	        hRDao.deleteHRMemberSummary(ephLinkIdList1);
//	        xtraCardDao.deleteXtraCards(xtraCardNbrList);
	    }

    public void createPharmacyHealthRewardsEnrollmentTestData() throws ParseException, InterruptedException {

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

        /*
         * I am a non-targered customer with PHR dependent who recently enrolled
         */
        xtraCard.setXtraCardNbr(98158276);
        xtraCard.setCustId(80016);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80016);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80016);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80016);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80016);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);
    }
    
    public void deletePharmacyHealthRewardsEnrollmentTestData() {
  	  /*
  	    Purge All Test Cards
  	     */
          List<Integer> xtraCardNbrList = Arrays.asList(98158276);
          List<Integer> custIdList = Arrays.asList(80016);
//          List<Integer> cmpgnIdList = Arrays.asList(59726, 64355);
          List<Integer> ephLinkIdList = Arrays.asList(80017011, 80017012, 80017111, 80017112, 80017113, 80017114, 80017211, 80017212, 80017311, 80017312, 80017313, 80017314, 80017411, 80017412, 80017511, 80017512, 80017611, 80017711, 80017811, 80017911, 80018011, 80018111, 80018211, 80018311, 80018312, 80018411, 80018412);
//          campaignDao.deleteCampaignRecords(cmpgnIdList, xtraCardNbrList);
          customerDao.deleteCustomers(custIdList);
//          hRDao.deleteHRRecords(ephLinkIdList);
          xtraCardDao.deleteXtraCards(xtraCardNbrList);
      }
    
    public void createPharmacyHealthRewardsTestData() throws ParseException, InterruptedException {

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

        /*
         * I am a non-targered customer with PHR dependent who recently enrolled
         */
        xtraCard.setXtraCardNbr(98158276);
        xtraCard.setCustId(80016);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80016);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80016);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80016);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80016);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017011);
        hrMemberSmry.setXtraCardNbr(98158276);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017011);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017011);
        hrMemberEnroll.setXtraCardNbr(98158276);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017011);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017011);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017011);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017012);
        hrMemberSmry.setXtraCardNbr(98158276);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017012);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017012);
        hrMemberEnroll.setXtraCardNbr(98158276);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017012);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017012);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017012);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         *I and my wife are non-targered customers with 2 PHR dependents who recently enrolled
         */
        xtraCard.setXtraCardNbr(98158277);
        xtraCard.setCustId(80017);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80017);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80017);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80017);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80017);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017111);
        hrMemberSmry.setXtraCardNbr(98158277);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017111);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017111);
        hrMemberEnroll.setXtraCardNbr(98158277);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017111);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017111);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017111);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017112);
        hrMemberSmry.setXtraCardNbr(98158277);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017112);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017112);
        hrMemberEnroll.setXtraCardNbr(98158277);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017112);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017112);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017112);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017113);
        hrMemberSmry.setXtraCardNbr(98158277);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017113);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017113);
        hrMemberEnroll.setXtraCardNbr(98158277);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017113);
        hrMemberProfile.setFirstName("Donna");
        hrMemberProfile.setLastName("Furter");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017113);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017113);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);


        hrMemberSmry.setEphLinkId(80017114);
        hrMemberSmry.setXtraCardNbr(98158277);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017114);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017114);
        hrMemberEnroll.setXtraCardNbr(98158277);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017114);
        hrMemberProfile.setFirstName("Tim");
        hrMemberProfile.setLastName("Richards");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017114);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017114);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);




        /*
         *I am targered customer with PHR dependent who recently enrolled
         */
        xtraCard.setXtraCardNbr(98158278);
        xtraCard.setCustId(80018);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80018);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80018);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80018);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80018);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017211);
        hrMemberSmry.setXtraCardNbr(98158278);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017211);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017211);
        hrMemberEnroll.setXtraCardNbr(98158278);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017211);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017211);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017211);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017212);
        hrMemberSmry.setXtraCardNbr(98158278);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017212);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017212);
        hrMemberEnroll.setXtraCardNbr(98158278);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017212);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017212);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017212);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         *I and my wife are targered customers with 2 PHR dependents who recently enrolled
         */
        xtraCard.setXtraCardNbr(98158279);
        xtraCard.setCustId(80020);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80020);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80020);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80020);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80020);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017311);
        hrMemberSmry.setXtraCardNbr(98158279);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017311);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017311);
        hrMemberEnroll.setXtraCardNbr(98158279);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017311);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017311);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017311);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017312);
        hrMemberSmry.setXtraCardNbr(98158279);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017312);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017312);
        hrMemberEnroll.setXtraCardNbr(98158279);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017312);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017312);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017312);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017313);
        hrMemberSmry.setXtraCardNbr(98158279);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017313);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017313);
        hrMemberEnroll.setXtraCardNbr(98158279);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017313);
        hrMemberProfile.setFirstName("Donna");
        hrMemberProfile.setLastName("Furter");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017313);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017313);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);


        hrMemberSmry.setEphLinkId(80017314);
        hrMemberSmry.setXtraCardNbr(98158279);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017314);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017314);
        hrMemberEnroll.setXtraCardNbr(98158279);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017314);
        hrMemberProfile.setFirstName("Tim");
        hrMemberProfile.setLastName("Richards");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017314);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017314);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);




        /*
         * "I and my wife joined PHR as non-targeted customer in 2015"
         */
        xtraCard.setXtraCardNbr(98158280);
        xtraCard.setCustId(80019);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80019);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80019);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80019);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80019);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017411);
        hrMemberSmry.setXtraCardNbr(98158280);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017411);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017411);
        hrMemberEnroll.setXtraCardNbr(98158280);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017411);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017411);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017411);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017412);
        hrMemberSmry.setXtraCardNbr(98158280);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017412);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017412);
        hrMemberEnroll.setXtraCardNbr(98158280);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017412);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017412);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017412);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         * "I and my wife joined PHR as targeted customer in 2015"
         */
        xtraCard.setXtraCardNbr(98158281);
        xtraCard.setCustId(80021);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80021);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80021);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80021);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80021);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017511);
        hrMemberSmry.setXtraCardNbr(98158281);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017511);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017511);
        hrMemberEnroll.setXtraCardNbr(98158281);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017511);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017511);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017511);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80017512);
        hrMemberSmry.setXtraCardNbr(98158281);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017512);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017512);
        hrMemberEnroll.setXtraCardNbr(98158281);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017512);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017512);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2015-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017512);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         * "I recently enrolled in PHR as non-targeted customer"
         */
        xtraCard.setXtraCardNbr(98158284);
        xtraCard.setCustId(80024);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80024);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80024);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80024);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80024);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017611);
        hrMemberSmry.setXtraCardNbr(98158284);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017611);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017611);
        hrMemberEnroll.setXtraCardNbr(98158284);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017611);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017611);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017611);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         * "I recently enrolled in PHR as targeted customer"
         */
        xtraCard.setXtraCardNbr(98158285);
        xtraCard.setCustId(80025);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80025);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80025);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80025);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80025);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017711);
        hrMemberSmry.setXtraCardNbr(98158285);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017711);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017711);
        hrMemberEnroll.setXtraCardNbr(98158285);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017711);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017711);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017711);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         * I am a targeted customer who joined PHR in 2015
         */
        xtraCard.setXtraCardNbr(98158286);
        xtraCard.setCustId(80026);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80026);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80026);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80026);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80026);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017811);
        hrMemberSmry.setXtraCardNbr(98158286);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017811);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017811);
        hrMemberEnroll.setXtraCardNbr(98158286);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017811);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017811);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017811);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         * "I am a non-targeted customer who joined PHR in 2015"
         */
        xtraCard.setXtraCardNbr(98158287);
        xtraCard.setCustId(80027);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80027);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80027);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80027);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80027);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80017911);
        hrMemberSmry.setXtraCardNbr(98158287);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80017911);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80017911);
        hrMemberEnroll.setXtraCardNbr(98158287);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80017911);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse(todayTimeStamp));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80017911);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse(todayTimeStamp));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse(future1yearDate));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80017911);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);



        /*
         * "I am a non-targeted customer who filled 150 prescriptions"
         */
        xtraCard.setXtraCardNbr(98158288);
        xtraCard.setCustId(80028);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80028);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80028);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80028);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80028);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018011);
        hrMemberSmry.setXtraCardNbr(98158288);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80018011);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018011);
        hrMemberEnroll.setXtraCardNbr(98158288);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80018011);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80018011);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80018011);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);





        /*
         * "I have never enrolled into PHR"
         */
        xtraCard.setXtraCardNbr(98158289);
        xtraCard.setCustId(80029);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80029);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80029);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80029);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80029);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        /*
         * "I have unenrolled from PHR"
         */
        xtraCard.setXtraCardNbr(98158290);
        xtraCard.setCustId(80030);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80030);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80030);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80030);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80030);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018111);
        hrMemberSmry.setXtraCardNbr(98158290);
        hrMemberSmry.setEnrollStatusCd("U");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80018111);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("U");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018111);
        hrMemberEnroll.setXtraCardNbr(98158290);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80018111);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80018111);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80018111);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);




        /*
         * "I have enrolled in PHR but my HIPPA expired"
         */
        xtraCard.setXtraCardNbr(98158291);
        xtraCard.setCustId(80031);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80031);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80031);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80031);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80031);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018211);
        hrMemberSmry.setXtraCardNbr(98158291);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("E");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2020-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80018211);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018211);
        hrMemberEnroll.setXtraCardNbr(98158291);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80018211);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80018211);
        hrMemberHippa.setHippaStatusCd("E");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2020-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80018211);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);


        /*
         * "I and my wife joined PHR as non-targeted customer in 2015 but my wife's HIPPA expired"
         */
        xtraCard.setXtraCardNbr(98158282);
        xtraCard.setCustId(80022);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80022);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80022);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80022);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80022);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018311);
        hrMemberSmry.setXtraCardNbr(98158282);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80018311);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018311);
        hrMemberEnroll.setXtraCardNbr(98158282);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80018311);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80018311);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80018311);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80018312);
        hrMemberSmry.setXtraCardNbr(98158282);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("E");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2020-04-11"));
        hrMemberSmry.setRxCapStatusCd("");
        hrMemberSmry.setRxCapTs(null);
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80018312);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018312);
        hrMemberEnroll.setXtraCardNbr(98158282);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80018312);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80018312);
        hrMemberHippa.setHippaStatusCd("E");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2020-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80018312);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);




        /*
         * "I have one of the member filled 100 prescriptions and now I get 50 points"
         */
        xtraCard.setXtraCardNbr(98158283);
        xtraCard.setCustId(80023);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);

        customer.setCustId(80023);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80023);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80023);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80023);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        hrMemberSmry.setEphLinkId(80018411);
        hrMemberSmry.setXtraCardNbr(98158283);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80018411);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018411);
        hrMemberEnroll.setXtraCardNbr(98158283);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80018411);
        hrMemberProfile.setFirstName("John");
        hrMemberProfile.setLastName("Doe");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80018411);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80018411);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);

        hrMemberSmry.setEphLinkId(80018412);
        hrMemberSmry.setXtraCardNbr(98158283);
        hrMemberSmry.setEnrollStatusCd("E");
        hrMemberSmry.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaStatusCd("C");
        hrMemberSmry.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberSmry.setHippaExpireDt(simpleDateFormat.parse("2023-04-11"));
        hrMemberSmry.setRxCapStatusCd("C");
        hrMemberSmry.setRxCapTs(carePassDateUtil.carePassRedeemEndTswtz(0));
        hrMemberSmry.setLastUpdtTs(null);
        hRDao.createHrMemberSmry(hrMemberSmry);

        hrMemberEnroll.setEphLinkId(80018412);
        hrMemberEnroll.setEnrollStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberEnroll.setEnrollStatusCd("E");
        hrMemberEnroll.setIdTypeCd("R");
        hrMemberEnroll.setIdNbr(80018412);
        hrMemberEnroll.setXtraCardNbr(98158283);
        hrMemberEnroll.setEnrollSrcCd("R");
        hrMemberEnroll.setEnrollStoreNbr(374);
        hrMemberEnroll.setEnrollSrcAcctNbr(0);
        hRDao.createHrMemberEnroll(hrMemberEnroll);

        hrMemberProfile.setEphLinkId(80018412);
        hrMemberProfile.setFirstName("Mary");
        hrMemberProfile.setLastName("Krisher");
        hrMemberProfile.setPrefPhoneNbr("4545454545");
        hrMemberProfile.setCreateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberProfile.setLastUpdateDttm(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hRDao.createHrMemberProfile(hrMemberProfile);

        hrMemberHippa.setHippaSignTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setIdTypeCd("R");
        hrMemberHippa.setIdNbr(80018412);
        hrMemberHippa.setHippaStatusCd("C");
        hrMemberHippa.setHippaStatusRsnCd(null);
        hrMemberHippa.setHippaStatusTs(simpleDateFormatTs.parse("2018-04-11 08.13.52 PM"));
        hrMemberHippa.setHippaExpireDt(simpleDateFormat.parse("2023-04-26"));
        hrMemberHippa.setHippaFormVerNbr(1);
        hrMemberHippa.setHippaFormLocCd("1210430122");
        hrMemberHippa.setEphLinkId(80018412);
        hrMemberHippa.setHippaSignByCd(null);
        hrMemberHippa.setEsigFmtCd(null);
        hrMemberHippa.setEsigBlob(null);
        hRDao.createHrMemberHippa(hrMemberHippa);


//Default Campaign
        campaign.setCmpgnId(59726);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("H");
        campaign.setCmpgnDsc("2021 ExtraCare Pharmacy and Health Rewards");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt("Pharmacy and Health ExtraBucks");
        campaign.setRecptScaleNbr(0);
        campaign.setRwrdRedirInd(" ");
        campaign.setStartDt(simpleDateFormat.parse("2021-01-01"));
        campaign.setEndDt(simpleDateFormat.parse("2021-12-31"));
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

//Pilot campaign
        campaign.setCmpgnId(64355);
        campaign.setCmpgnTypeCd("E");
        campaign.setCmpgnSubtypeCd("H");
        campaign.setCmpgnDsc("2021 ExtraCare Pharmacy and Health Rewards Pilot");
        campaign.setRecptPrntInd("-1");
        campaign.setRecptPrntPriorityNbr(0);
        campaign.setRecptRxt("Pharmacy and Health ExtraBucks");
        campaign.setRecptScaleNbr(0);
        campaign.setRwrdRedirInd(null);
        campaign.setStartDt(simpleDateFormat.parse("2021-01-01"));
        campaign.setEndDt(simpleDateFormat.parse("2021-12-31"));
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
        campaign.setSegSrcTableName("PHR_PILOT_2021_CMPGN_64355");
        campaign.setSegReloadRqstTs(null);
        campaign.setSegLastProcStartTs(simpleDateFormatTs.parse("2020-05-31 08.24.55 PM"));
        campaign.setSegLastProcEndTs(simpleDateFormatTs.parse("2021-05-31 08.26.27 PM"));
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


//Default campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(169232);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(169233);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(169234);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(169235);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-5 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(5);
        campaignCoupon.setCpnNbr(169236);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-04"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-6 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(6);
        campaignCoupon.setCpnNbr(169331);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-06"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-7 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(7);
        campaignCoupon.setCpnNbr(169332);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-06"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-8 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(8);
        campaignCoupon.setCpnNbr(170109);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-16"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-9 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(9);
        campaignCoupon.setCpnNbr(169937);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-01-11"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-10 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(10);
        campaignCoupon.setCpnNbr(172875);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-02-02"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-11 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(11);
        campaignCoupon.setCpnNbr(172876);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-02-02"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//Default campaign Coupon-14 qty
        campaignCoupon.setCmpgnId(59726);
        campaignCoupon.setRwrdQty(14);
        campaignCoupon.setCpnNbr(184208);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-03-20"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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


//pilot campaign Coupon-1 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(1);
        campaignCoupon.setCpnNbr(182409);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-03-13"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//pilot campaign Coupon-2 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(2);
        campaignCoupon.setCpnNbr(185392);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-03-30"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-09-02"));
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

//pilot campaign Coupon-3 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(3);
        campaignCoupon.setCpnNbr(191667);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-06-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-22"));
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

//pilot campaign Coupon-4 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(4);
        campaignCoupon.setCpnNbr(191668);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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


//pilot campaign Coupon-5 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(5);
        campaignCoupon.setCpnNbr(191669);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-6 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(6);
        campaignCoupon.setCpnNbr(191670);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-7 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(7);
        campaignCoupon.setCpnNbr(191671);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-8 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(8);
        campaignCoupon.setCpnNbr(191672);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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


//pilot campaign Coupon-9 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(9);
        campaignCoupon.setCpnNbr(191673);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-10 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(10);
        campaignCoupon.setCpnNbr(191674);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-12 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(12);
        campaignCoupon.setCpnNbr(191675);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-13 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(13);
        campaignCoupon.setCpnNbr(192430);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-06-09"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-14 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(14);
        campaignCoupon.setCpnNbr(191676);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-06-09"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-15 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(15);
        campaignCoupon.setCpnNbr(191677);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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

//pilot campaign Coupon-26 qty
        campaignCoupon.setCmpgnId(64355);
        campaignCoupon.setRwrdQty(26);
        campaignCoupon.setCpnNbr(191678);
        campaignCoupon.setStartDt(simpleDateFormat.parse("2021-05-31"));
        campaignCoupon.setEndDt(simpleDateFormat.parse("2022-02-01"));
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


//Default campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(59726);
        campaignRewardThreshold.setThrshldLimNbr(10);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("Q");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);

//Pilot campaign rewards threshold
        campaignRewardThreshold.setCmpgnId(64355);
        campaignRewardThreshold.setThrshldLimNbr(4);
        campaignRewardThreshold.setRwrdQty(1);
        campaignRewardThreshold.setRwrdThrshldCyclInd("-1");
        campaignRewardThreshold.setThrshldTypeCd("Q");
        campaignDao.createCampaignRewardThreshold(campaignRewardThreshold);


        /*
         * I am a non-targered customer with PHR dependent who recently enrolled
         */

//Default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158276);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         *I and my wife are non-targered customers with 2 PHR dependents who recently enrolled
         */


//Default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158277);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         *I am targered customer with PHR dependent who recently enrolled
         */

//Pilot campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158278);
        campaignActivePointBase.setCmpgnId(64355);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         *I and my wife are targered customers with 2 PHR dependents who recently enrolled
         */

//Pilot campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158279);
        campaignActivePointBase.setCmpgnId(64355);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);



        /*
         * "I and my wife joined PHR as non-targeted customer in 2015"
         */
//Default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158280);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(6.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);



        /*
         * "I and my wife joined PHR as targeted customer in 2015"
         */
//Pilot campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158281);
        campaignActivePointBase.setCmpgnId(64355);
        campaignActivePointBase.setPtsQty(1.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         * "I and my wife joined PHR as non-targeted customer in 2015 but my wife's HIPPA expired"
         */
//default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158282);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         * "I have one of the member filled 100 prescriptions and now I get 50 points"
         */
//default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158283);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(100.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         * "I recently enrolled in PHR as non-targeted customer"
         */
//Default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158284);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         * "I recently enrolled in PHR as targeted customer"
         */
//Pilot campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158285);
        campaignActivePointBase.setCmpgnId(64355);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         * I am a targeted customer who joined PHR in 2015
         */
//Pilot campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158286);
        campaignActivePointBase.setCmpgnId(64355);
        campaignActivePointBase.setPtsQty(3.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         * "I am a non-targeted customer who joined PHR in 2015"
         */
//Default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158287);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(2.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         * "I am a non-targeted customer who filled 150 prescriptions"
         */
//default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158288);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(150.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);

        /*
         * "I have unenrolled from PHR"
         */
//default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158290);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        /*
         * "I have enrolled in PHR but my HIPPA expired"
         */
//default campaign Active Point Base
        campaignActivePointBase.setXtraCardNbr(98158291);
        campaignActivePointBase.setCmpgnId(59726);
        campaignActivePointBase.setPtsQty(0.0);
        campaignActivePointBase.setActivationTs(null);
        campaignActivePointBase.setActivationSrcCd(null);
        campaignDao.createCampaignActivePointBase(campaignActivePointBase);


        cacheRefreshUtil.refresCacheusingXtraParms();
        cacheRefreshUtil.refresCacheforCmpgnDefns();

    }

    
    /**
     * Delete Test Data for Xtra Card Summary Scenario
     */
    public void deletePharmacyHealthRewardsTestData() {
	  /*
	    Purge All Test Cards
	     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158258, 98158259, 98158260, 98158261, 98158262, 98158263, 98158264, 98158265, 98158266, 98158267, 98158268, 98158269, 98158270, 98158271, 98158272, 98158273, 98158274, 98158275, 98158276, 98158277, 98158278, 98158279, 98158280, 98158281, 98158282, 98158283, 98158284, 98158285, 98158286, 98158287, 98158288, 98158289, 98158290, 98158291, 98158292, 98158294, 98158295, 98158296, 98158297, 98158298, 98158299);
        List<Integer> custIdList = Arrays.asList(79998, 79999, 80000, 80001, 80002, 80003, 80004, 80005, 80006, 80007, 80008, 80009, 80010, 80011, 80012, 80013, 80014, 80015, 80016, 80017, 80018, 80019, 80020, 80021, 80022, 80023, 80024, 80025, 80026, 80027, 80028, 80029, 80030, 80031);
        List<Integer> cmpgnIdList = Arrays.asList(59726, 64355);
        List<Integer> ephLinkIdList = Arrays.asList(80017011, 80017012, 80017111, 80017112, 80017113, 80017114, 80017211, 80017212, 80017311, 80017312, 80017313, 80017314, 80017411, 80017412, 80017511, 80017512, 80017611, 80017711, 80017811, 80017911, 80018011, 80018111, 80018211, 80018311, 80018312, 80018411, 80018412);
        campaignDao.deleteCampaignRecords(cmpgnIdList, xtraCardNbrList);
        customerDao.deleteCustomers(custIdList);
        hRDao.deleteHRRecords(ephLinkIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
    }

}
