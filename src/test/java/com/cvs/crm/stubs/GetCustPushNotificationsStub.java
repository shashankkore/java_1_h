package com.cvs.crm.stubs;

import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class GetCustPushNotificationsStub {
    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    DateUtil dateUtil;

    private static WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for GetCust PushNotifications
     */
    public static void createGetCustPushNotificationsStubData() {
        wireMockServer.start();

        //I am a XtraCare Customer and want to see Push Notification response
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C8159?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90046")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        " \"pushNotifs\":{\n" +
                        "   {\n" +
                        "       \"msgId\": 4,\n" +
                        "       \"pushNotifSeqNbr\": 92168,\n" +
                        "       \"cpnCnt\": 1,\n" +
                        "   }\n" +
                        "   \"paperlessCpns\":{\n" +
                        "       \"enrolled\":\"N\"\n" +
                        "   },\n" +
                        "}\n" +
                        "}").withStatus(200)));


       /* //I am an XtraCare Customer and want to fetch xtra card child table information
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058422?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058422,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        "  \"tables\": \n" +
                        "  [\n" +
                        " {\n" +
                        " \"tableName\": \"xtra_card_child\",\n" +
                        " \"row\": [\n" +
                        " {\n" +
                        " \"birthday_dt\":\"" + (carePassDateUtil.carePassExpireTswtz(-910)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(-910)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(-910)).substring(8, 10) + "\"\n" +
                        " }\n" +
                        " ]\n" +
                        " }\n" +
                        " ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch xtra card child table information
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058423?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058423,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        "  \"tables\": \n" +
                        "  [\n" +
                        " {\n" +
                        " \"tableName\": \"xtra_card_analytic_event\",\n" +
                        " \"row\": [\n" +
                        " {\n" +
                        " \"analytic_event_type_cd\":1,\n" +
                        " \"cpn_seq_nbr\":10000000671377,\n" +
                        " \"analytic_event_ts\":\"" + (carePassDateUtil.carePassExpireTswtz(-650)).substring(5, 7) + "/" + (carePassDateUtil.carePassExpireTswtz(-650)).substring(8, 10) + "/" + (carePassDateUtil.carePassExpireTswtz(-650)).substring(2, 4) + " 2:00:00 PM +0530" + "\"\n" +
                        " }\n" +
                        " ]\n" +
                        " }\n" +
                        " ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with beautyClub enrolled true
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058424?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058424,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"beautyClub\":{\n" +
                        "   \"enrolled\":\"Y\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with beautyNotes enrolled true
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058425?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058425,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"beautyNotes\":{\n" +
                        "   \"enrolled\":\"Y\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with diabeticClub enrolled true
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058426?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058426,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"diabeticClub\":{\n" +
                        "   \"enrolled\":\"Y\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with paperlessCpns enrolled true
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058427?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058427,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"paperlessCpns\":{\n" +
                        "   \"enrolled\":\"Y\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with beautyClub enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058428?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058428,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"beautyClub\":{\n" +
                        "   \"enrolled\":\"N\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));

        //I am an XtraCare Customer and want to fetch my preferences information with beautyNotes enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058429?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058429,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"beautyNotes\":{\n" +
                        "   \"enrolled\":\"N\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with diabeticClub enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058430?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058429,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"diabeticClub\":{\n" +
                        "   \"enrolled\":\"N\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with diabeticClub enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058430?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058430,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"diabeticClub\":{\n" +
                        "   \"enrolled\":\"N\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with paperlessCpns enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058431?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058431,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"paperlessCpns\":{\n" +
                        "   \"enrolled\":\"N\"\n" +
                        "   }\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with phrEnroll enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058432?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058432,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"phrEnroll\":\"N\"\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with sms enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058433?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058433,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"sms\":\"N\"\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled true
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058434?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058434,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"optInEc\":\"Y\"\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with optInEmail enrolled true
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058435?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058435,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"optInEmail\":\"A\"\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with sms enrolled true
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058436?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058436,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"sms\":\"Y\"\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with optInEc enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058437?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058437,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"optInEc\":\"N\"\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with optInEmail enrolled false
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058438?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058438,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"digitalReceipt\":\"P\"\n" +
                        " }\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with phr enrolled true and cmpgnId
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058439?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058439,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"phr\":{\n" +
                        "   \"cmpgnId\":\"59726\",\n" +
                        "   \"enrolled\":\"Y\"\n" +
                        "   }\n" +
                        "}\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as B
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058440?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058440,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"digitalReceipt\":\"N\"\n" +
                        "}\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058441?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058441,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"digitalReceipt\":\"B\"\n" +
                        "}\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as P
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058442?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"cusInfResp\": \n" +
                        "{\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058442,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "       },\n" +
                        " \"xtraCarePrefs\":\n" +
                        " {\n" +
                        "   \"digitalReceipt\":\"D\"\n" +
                        "}\n" +
                        "}\n" +
                        "}").withStatus(200)));


    }*/
    }

    /**
     * Delete Stub data for GetCustTablesnPreferences
     */
    public void deleteGetCustTablesnPreferencesStubData() {
        wireMockServer.stop();
    }

}