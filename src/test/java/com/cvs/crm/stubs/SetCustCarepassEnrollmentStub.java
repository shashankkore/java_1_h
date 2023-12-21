package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cvs.crm.util.CarePassDateUtil;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class SetCustCarepassEnrollmentStub {
    @Autowired
    CarePassDateUtil carePassDateUtil;
    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Care Pass Enrollment
     */
    public void createSetCustCarepassEnrollmentStubData() {
        wireMockServer.start();


        //I joined ExtraCare program today to participate in CarePass Monthly program and want to see 10$ and 20% coupons
        //TODO - Stub Data has to be modified

        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158393\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158393,\n" +
                        "    \"xtraCardNbr\": 98158393\n" +
                        "}").withStatus(200)));


        //I joined ExtraCare program today to participate in CarePass Yearly program and want to see 10$ and 20% coupons
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158394\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158394,\n" +
                        "    \"xtraCardNbr\": 98158394\n" +
                        "}").withStatus(200)));


        //I am an existing ExtraCare member and joined in CarePass Monthly program today and want to see 10$ and 20% coupons
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158395\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158395,\n" +
                        "    \"xtraCardNbr\": 98158395\n" +
                        "}").withStatus(200)));


        //I am an existing ExtraCare member and joined in CarePass Yearly program today and want to see 10$ and 20% coupons
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158396\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158396,\n" +
                        "    \"xtraCardNbr\": 98158396\n" +
                        "}").withStatus(200)));


        //I am an existing CarePass member and initiated cancellation of my monthly carepass membership today and want to see the status as U and benefit_eligibility_date as future date in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158397\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158397,\n" +
                        "    \"xtraCardNbr\": 98158397\n" +
                        "}").withStatus(200)));


        //I am an existing CarePass member and initiated cancellation of my yearly carepass membership today and want to see the status as U and benefit_eligibility_date as future date in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158398\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158398,\n" +
                        "    \"xtraCardNbr\": 98158398\n" +
                        "}").withStatus(200)));


        //I am an CarePass member and cancelled my monthly carepass membership 3 day ago and want to see the status as U and benefit_eligibility_date as past date in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158399\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158399,\n" +
                        "    \"xtraCardNbr\": 98158399\n" +
                        "}").withStatus(200)));


        //I am an CarePass member and cancelled my yearly carepass membership 5 day ago and want to see the status as U and benefit_eligibility_date as past date in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158400\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158400,\n" +
                        "    \"xtraCardNbr\": 98158400\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with Hold status and benefit_eligibility_date as expire date and want to reactivate my monthly membership and see the status changed to E and benefit_eligibility_date changed to future 30 days from current date in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158401\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158401,\n" +
                        "    \"xtraCardNbr\": 98158401\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with Hold status and benefit_eligibility_date as expire date and want to reactivate my yearly membership and see the status changed to E and benefit_eligibility_date changed to future 30 days from current date in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158402\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158402,\n" +
                        "    \"xtraCardNbr\": 98158402\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with monthly membership program and my membership status went on Hold and want to see the status changed to H and benefit_eligibility_date as today in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158403\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158403,\n" +
                        "    \"xtraCardNbr\": 98158403\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with yearly membership program and my membership status went on Hold and want to see the status changed to H and benefit_eligibility_date as today in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158404\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158404,\n" +
                        "    \"xtraCardNbr\": 98158404\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with Hold status and want to unenroll from monthly membership program and see the status changed to U and benefit_eligibity_date to today in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158405\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158405,\n" +
                        "    \"xtraCardNbr\": 98158405\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with Hold status and want to unenroll from yearly membership program and see the status changed to U and benefit_eligibity_date to today in carepass_member_status_hist and carepass_member_smry tables
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158406\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158406,\n" +
                        "    \"xtraCardNbr\": 98158406\n" +
                        "}").withStatus(200)));


        //I am a CarePass member currently under monthly program and would like to switch to yearly program and see the expire date changed to 365 days and benefit eligibility date changes to 1 month from current date and plan type changes to Y
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158407\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"encodedXtraCardNbr\": 98158407,\n" +
                        "    \"xtraCardNbr\": 98158407\n" +
                        "}").withStatus(200)));


        //I am using invalid search card type and want to see the error response
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0001%2C98158408\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 1,\n" +
                        "    \"errorMsg\": \"ERROR: Unsupported search card type provided: 0001\"\n" +
                        "}").withStatus(400)));


        //     I am using invalid table name in the request payload and want to see the error responseI am using invalid search card type and want to see the error response
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/customers/0002%2C98158409\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=90046"))
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 10,\n" +
                        "    \"errorMsg\": \"ERROR: Invalid table, Invlaid operation on: CAREPASS_ENROLLFORM_HIST\"\n" +
                        "}").withStatus(400)));
    }

    /**
     * Delete Stub data for Care Pass Enrollment
     */
    public void deleteSetCustCarepassEnrollmentStubData() {

        wireMockServer.stop();
    }

}

