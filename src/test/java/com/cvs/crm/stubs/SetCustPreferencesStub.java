package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cvs.crm.util.CarePassDateUtil;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class SetCustPreferencesStub {
    @Autowired
    CarePassDateUtil carePassDateUtil;
    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Preferences
     */
    public void createSetCustPreferencesStubData() {
        wireMockServer.start();

        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058444\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058444,\n" +
                        "    \"xtraCardNbr\": 900058444\n" +
                        "}").withStatus(200)));


        //I am a new XtraCare Customer and want to save my preferences information with beautyNotes enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058445\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058445,\n" +
                        "    \"xtraCardNbr\": 900058445\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with diabeticClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058446\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058446,\n" +
                        "    \"xtraCardNbr\": 900058446\n" +
                        "}").withStatus(200)));

        //I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058447\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058447,\n" +
                        "    \"xtraCardNbr\": 900058447\n" +
                        "}").withStatus(200)));

        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058448\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058448,\n" +
                        "    \"xtraCardNbr\": 900058448\n" +
                        "}").withStatus(200)));


        //I am a New XtraCare Customer and want to save my preferences information with beautyNotes enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058449\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058449,\n" +
                        "    \"xtraCardNbr\": 900058449\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with diabeticClub enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058450\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058450,\n" +
                        "    \"xtraCardNbr\": 900058450\n" +
                        "}").withStatus(200)));


        //I am a new XtraCare Customer and want to save my preferences information with paperlessCpns enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058451\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058451,\n" +
                        "    \"xtraCardNbr\": 900058451\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with phrEnroll enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058452\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058452,\n" +
                        "    \"xtraCardNbr\": 900058452\n" +
                        "}").withStatus(200)));


        //I am a new XtraCare Customer and want to save my preferences information with sms enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058453\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058453,\n" +
                        "    \"xtraCardNbr\": 900058453\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058454\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058454,\n" +
                        "    \"xtraCardNbr\": 900058454\n" +
                        "}").withStatus(200)));


        //I am a new XtraCare Customer and want to save my preferences information with sms enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058455\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058455,\n" +
                        "    \"xtraCardNbr\": 900058455\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058456\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058456,\n" +
                        "    \"xtraCardNbr\": 900058456\n" +
                        "}").withStatus(200)));


        //I am a new XtraCare Customer and want to save my preferences information with optInEmail as A
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058457\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058457,\n" +
                        "    \"xtraCardNbr\": 900058457\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as N
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058459\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058459,\n" +
                        "    \"xtraCardNbr\": 900058459\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as P
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058460\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058460,\n" +
                        "    \"xtraCardNbr\": 900058460\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as B
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058461\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058461,\n" +
                        "    \"xtraCardNbr\": 900058461\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058462\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058462,\n" +
                        "    \"xtraCardNbr\": 900058462\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058463\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058463,\n" +
                        "    \"xtraCardNbr\": 900058463\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058464\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058464,\n" +
                        "    \"xtraCardNbr\": 900058464\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058465\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058465,\n" +
                        "    \"xtraCardNbr\": 900058465\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058466\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058466,\n" +
                        "    \"xtraCardNbr\": 900058466\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058467\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058467,\n" +
                        "    \"xtraCardNbr\": 900058467\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058468\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058468,\n" +
                        "    \"xtraCardNbr\": 900058468\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058469\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058469,\n" +
                        "    \"xtraCardNbr\": 900058469\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058470\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058470,\n" +
                        "    \"xtraCardNbr\": 900058470\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058471\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058471,\n" +
                        "    \"xtraCardNbr\": 900058471\n" +
                        "}").withStatus(200)));


        //I am an XtraCare Customer and want to fetch my preferences information with digitalReceipt as D
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058472\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058472,\n" +
                        "    \"xtraCardNbr\": 900058472\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058473\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058473,\n" +
                        "    \"xtraCardNbr\": 900058473\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058474\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058474,\n" +
                        "    \"xtraCardNbr\": 900058474\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058475\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058475,\n" +
                        "    \"xtraCardNbr\": 900058475\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058476\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058476,\n" +
                        "    \"xtraCardNbr\": 900058476\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058477\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058477,\n" +
                        "    \"xtraCardNbr\": 900058477\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058478\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058478,\n" +
                        "    \"xtraCardNbr\": 900058478\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058479\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058479,\n" +
                        "    \"xtraCardNbr\": 900058479\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058480\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058480,\n" +
                        "    \"xtraCardNbr\": 900058480\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058480\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058480,\n" +
                        "    \"xtraCardNbr\": 900058480\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058480\\?msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058480,\n" +
                        "    \"xtraCardNbr\": 900058480\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058481\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058481,\n" +
                        "    \"xtraCardNbr\": 900058481\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058482\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058482,\n" +
                        "    \"xtraCardNbr\": 900058482\n" +
                        "}").withStatus(200)));


        //I am an Existing XtraCare Customer and want to save my preferences information with beautyClub enrolled as true
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058483\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058483,\n" +
                        "    \"xtraCardNbr\": 900058483\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058484\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058484,\n" +
                        "    \"xtraCardNbr\": 900058484\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058485\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058485,\n" +
                        "    \"xtraCardNbr\": 900058485\n" +
                        "}").withStatus(200)));


        //I am a Existing XtraCare Customer and want to save my preferences information with optInEc enrolled as false
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C900058486\\?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 900058486,\n" +
                        "    \"xtraCardNbr\": 900058486\n" +
                        "}").withStatus(200)));


    }

    /**
     * Delete Stub data for Care Pass Preferences
     */
    public void deleteSetCustPreferencesStubData() {
        wireMockServer.stop();
    }
}