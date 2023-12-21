package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import com.cvs.crm.util.CarePassDateUtil;

@Component
@Slf4j
public class CarePassStub {
    @Autowired
    CarePassDateUtil carePassDateUtil;
    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Care Pass
     */
    public void createCarePassStubData() {
        wireMockServer.start();

        //I recently enrolled in CVS carepass program under monthly renewal membership and have $10 reward available to redeem in next 5 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158307/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"rewardNextIssueDaysCount\": 5,\n" +
                        "    \"couponSequenceNumber\": 98158307040666,\n" +
                        "    \"statusCode\": \"E\",\n" +
                        "	 \"enrollmentExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(5) + "\",\n" +
                        "    \"couponExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(5) + "\",\n" +
                        "    \"campaignId\": 40666,\n" +
                        "    \"couponNumber\": 59121\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I recently enrolled in CVS carepass under yearly renewal membership  and have $10 reward available for redeem in next 6 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158308/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"rewardNextIssueDaysCount\": 6,\n" +
                        "    \"couponSequenceNumber\": 98158308040666,\n" +
                        "    \"statusCode\": \"E\",\n" +
                        "	 \"enrollmentExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(341) + "\",\n" +
                        "    \"couponExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(6) + "\",\n" +
                        "    \"campaignId\": 40666,\n" +
                        "    \"couponNumber\": 59121\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am recently enrolled in CVS carepass program under monthly renewal membership and I have redeemed $10 reward today , next monthly renewable due in next 16 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158309/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"rewardNextIssueDaysCount\": 16,\n" +
                        "    \"statusCode\": \"E\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am recently enrolled in CVS carepass program under yearly renewal membership and I have redeemed $10 reward today, next yearly renewable due in next 8 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158310/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"rewardNextIssueDaysCount\": 8,\n" +
                        "    \"statusCode\": \"E\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I cancelled my CVS carepass membership today and I have $10 reward available for redeem in next 30 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158311/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"couponSequenceNumber\": 98158311040666,\n" +
                        "    \"statusCode\": \"CI\",\n" +
                        "	 \"enrollmentExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(30) + "\",\n" +
                        "    \"couponExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(30) + "\",\n" +
                        "    \"campaignId\": 40666,\n" +
                        "    \"couponNumber\": 59121\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have initiated CVS carepass membership cancellation today under monthly membership and have  $10 reward to redeem in next 30 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158312/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"couponSequenceNumber\": 98158312040666,\n" +
                        "    \"statusCode\": \"CI\",\n" +
                        "	 \"enrollmentExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(30) + "\",\n" +
                        "    \"couponExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(30) + "\",\n" +
                        "    \"campaignId\": 40666,\n" +
                        "    \"couponNumber\": 59121\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have initiated CVS carepass membership cancellation today under yearly membership and have  $10 reward to redeem in next 25 days"
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158313/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"couponSequenceNumber\": 98158313040666,\n" +
                        "    \"statusCode\": \"CI\",\n" +
                        "	 \"enrollmentExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(360) + "\",\n" +
                        "    \"couponExpiryDate\": \"" + carePassDateUtil.carePassExpireDate(25) + "\",\n" +
                        "    \"campaignId\": 40666,\n" +
                        "    \"couponNumber\": 59121\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I cancelled my CVS carepass membership yesterday and redeemed $10 monthly reward today
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158314/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"statusCode\": \"CI\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //My monthly CVS carepass membership payment did not go through yesterday and no $10 reward is available
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158315/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"statusCode\": \"H\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have initiated CVS carepass membership cancellation today under monthly membership and no $10 reward is available
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158316/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"statusCode\": \"CI\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have initiated CVS carepass membership cancellation today under yearly membership and no $10 reward is available
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158317/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"statusCode\": \"CI\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I cancelled my CVS carepass membership last year
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158318/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"carepass\": {\n" +
                        "    \"statusCode\": \"U\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));

    }

    /**
     * Delete Stub data for Care Pass
     */
    public void deleteCarePassStubData() {
        wireMockServer.stop();
    }

}

