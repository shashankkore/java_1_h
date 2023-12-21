package com.cvs.crm.util;

import com.cvs.crm.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;

@Service
@Data
@Slf4j
public class CarePassEnrollmentUtil {
    private Response serviceResponse;
    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    CarePassDateUtil carePassDateUtil;


    public void enrollUsingSetCust(String cardTyp, int cardNum) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\": \"2020070712:01:50-05:00\", \r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"1\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\": \"2022080712:01:50-05:00\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"E\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"delete_insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"enrollTswtz\": \"2020070712:01:50-05:00\",\r\n" +
                "						\"curStatus\": \"E\",\r\n" +
                "						\"curPlanType\": \"M\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust code: [{}] ", sCode);
    }

    public void enrollUsingSetCustMonthly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"1\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"E\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"delete_insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"enrollTswtz\": \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"curStatus\": \"E\",\r\n" +
                "						\"curPlanType\": \"M\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCustMonthly status code: [{}] ", sCode);
    }

    public void enrollUsingSetCustYearly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"12\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"E\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"delete_insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"enrollTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"curStatus\": \"E\",\r\n" +
                "						\"curPlanType\": \"Y\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void cancelCarepassUsingSetCustYearly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"12\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"U\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"update\",\r\n" +
                "					\"colData\": {\r\n" +
                "                   \"curStatus\": \"U\"\r\n" +
                "					},\r\n" +
                "					\"criterias\": [{\r\n" +
                "						\"colName\": \"curStatus\",\r\n" +
                "						\"operation\": \"=\",\r\n" +
                "						\"value\": \"E\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void cancelCarepassUsingSetCustMonthly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"1\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"U\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"update\",\r\n" +
                "					\"colData\": {\r\n" +
                "                   \"curStatus\": \"U\",\r\n" +
                "					},\r\n" +
                "					\"criterias\": [{\r\n" +
                "						\"colName\": \"curStatus\",\r\n" +
                "						\"operation\": \"=\",\r\n" +
                "						\"value\": \"E\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void reactivateCarepassUsingSetCustYearly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"12\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"E\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"delete_insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"enrollTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"curStatus\": \"E\",\r\n" +
                "						\"curPlanType\": \"Y\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void reactivateCarepassUsingSetCustMonthly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"1\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"E\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"delete_insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"enrollTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"curStatus\": \"E\",\r\n" +
                "						\"curPlanType\": \"M\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void holdCarepassUsingSetCustYearly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"12\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"H\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"update\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"curStatus\": \"H\"\r\n" +
                "					},\r\n" +
                "					\"criterias\": [{\r\n" +
                "						\"colName\": \"curStatus\",\r\n" +
                "						\"operation\": \"=\",\r\n" +
                "						\"value\": \"E\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void holdCarepassUsingSetCustMonthly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"1\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusCd\": \"H\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"update\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"curStatus\": \"H\"\r\n" +
                "					},\r\n" +
                "					\"criterias\": [{\r\n" +
                "						\"colName\": \"curStatus\",\r\n" +
                "						\"operation\": \"=\",\r\n" +
                "						\"value\": \"E\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void holdtoUnenrollCarepassUsingSetCustMonthly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"1\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\",\r\n" +
                "						\"mbrStatusCd\": \"U\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"update\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"curStatus\": \"U\"\r\n" +
                "					},\r\n" +
                "					\"criterias\": [{\r\n" +
                "						\"colName\": \"curStatus\",\r\n" +
                "						\"operation\": \"=\",\r\n" +
                "						\"value\": \"H\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void holdtoUnenrollCarepassUsingSetCustYearly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"12\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\",\r\n" +
                "						\"mbrStatusCd\": \"U\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"update\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"curStatus\": \"U\"\r\n" +
                "					},\r\n" +
                "					\"criterias\": [{\r\n" +
                "						\"colName\": \"curStatus\",\r\n" +
                "						\"operation\": \"=\",\r\n" +
                "						\"value\": \"H\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void M2ACarepassUsingSetCust(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\r\n" +
                "	\"xtraCare\": {\r\n" +
                "		\"carepass\": {\r\n" +
                "			\"tables\": [{\r\n" +
                "				\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"pymtPlanDur\": \"12\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"insert\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						\"actionTswtzSetDt\": \"sysdate\",\r\n" +
                "						\"mbrStatusRsnCd\": \"\",\r\n" +
                "						\"mbrStatusCd\": \"E\"\r\n" +
                "					}\r\n" +
                "				}]\r\n" +
                "			},\r\n" +
                "			{\r\n" +
                "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" +
                "				\"rows\": [{\r\n" +
                "					\"action\": \"update\",\r\n" +
                "					\"colData\": {\r\n" +
                "						\"curPlanType\": \"Y\"\r\n" +
                "					},\r\n" +
                "					\"criterias\": [{\r\n" +
                "						\"colName\": \"curStatus\",\r\n" +
                "						\"operation\": \"=\",\r\n" +
                "						\"value\": \"E\"\r\n" +
                "					},\r\n" +
                "					{\r\n" +
                "						\"colName\": \"curPlanType\",\r\n" +
                "						\"operation\": \"!=\",\r\n" +
                "						\"value\": \"Y\"\r\n" +
                "				}]\r\n" +
                "				}]\r\n" +
                "			}]\r\n" +
                "		}\r\n" +
                "	}\r\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("enrollUsingSetCust status code: [{}] ", sCode);
    }

    public void enrollUsingSetCustFromMonthlyToYearly(String cardTyp, int cardNum, int actionTswtz) throws ParseException {
        String cardType = cardTyp;
        int cardNbr = cardNum;
        int enrollDate = actionTswtz;
        int expDate = actionTswtz + 365;
        String msgSrcCd = "W";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        String jsonString = "{\n" +
                "    \"xtraCare\": {\n" +
                "        \"carepass\": {\n" +
                "            \"tables\": [\n" +
                "                {\n" +
                "                    \"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\n" +
                "                    \"rows\": [\n" +
                "                        {\n" +
                "                            \"action\": \"insert\",\n" +
                "                            \"colData\": {\n" +
                "						         \"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "                                \"actionTswtzSetDt\": \"sysdate\",\n" +
                "                                \"mbrStatusCd\": \"E\"\n" +
                "                            }\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"CAREPASS_MEMBER_SMRY\",\n" +
                "                    \"rows\": [\n" +
                "                        {\n" +
                "                            \"action\": \"update\",\n" +
                "                            \"colData\": {\n" +
                "                                \"curPlanType\": \"Y\",\n" +
                "                                \"curStatus\": \"E\",\n" +
                "						         \"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "						         \"expireTswtz\": \"" + carePassDateUtil.carePassTswtz(expDate, "yyyy-MMM-dd") + "\"\r\n" +
                "                            }\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"CAREPASS_ENROLL_FORM_HIST\",\n" +
                "                    \"rows\": [\n" +
                "                        {\n" +
                "                            \"action\": \"insert\",\n" +
                "                            \"colData\": {\n" +
                "                                \"pymtPlanDur\": \"12\",\n" +
                "						         \"actionTswtz\":  \"" + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(enrollDate)).substring(8, 10) + "00:00:00-05:00" + "\",\r\n" +
                "                                \"actionTswtzSetDt\": \"sysdate\"\n" +
                "                            }\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", cardType)
                .addPathParam("search_card_nbr", cardNbr)
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        log.info("Monthly to Yearly Carepass enroll using setCust - status code: [{}] ", sCode);
    }

}