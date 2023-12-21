package com.cvs.crm.stubs;

import com.cvs.crm.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@Component
@Slf4j
public class QuarterlyExtraBucksStub {
    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Beauty Club
     */
    public void createQuarterlyExtraBucksStubData() {
        wireMockServer.start();

        //I am CVS EC card member and made $15 purchase in between Jun 16th to Sep 15th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158300/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 56264,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(1) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 35.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 15.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I am CVS EC card member and made $60 purchase in between Jun 16th to Sep 15th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158301/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 56264,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(1) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 40.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 1.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 10.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am CVS EC card member and made $0 purchase in between Jun 16th to Sep 15th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158302/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 56264,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(1) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 50.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I am CVS EC card member and made $50 purchase in between Jun 16th to Sep 15th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158303/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 56264,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(1) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 50.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 1.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am CVS EC card member and made $25 purchase in between Sep 16th to Sep 30th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158304/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 56264,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(1) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 30.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 20.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am CVS EC card member and made $150 purchase in between Jun 16th to Sep 15th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158305/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 56264,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(1) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 50.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 3.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I am CVS EC card member and made $150 purchase in between Jun 16th to Sep 15th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158306/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 56264,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(1) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 50.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I am CVS EC card member and made $15 purchase in current quarter starting today
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158308/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 58909,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(89) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 35.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 15.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I am CVS EC card member and made $15 purchase in current quarter ending today
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158307/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"quarterlyExtraBucks\": {\n" +
                        "    \"campaignId\": 51682,\n" +
                        "    \"webDescription\": \"" + dateUtil.webDescriptionYear() + "\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.campaignEndDate(0) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 50.00,\n" +
                        "    \"rewardAmount\": 1.00,\n" +
                        "    \"pointsToNextThreshold\": 35.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.00,\n" +
                        "    \"couponIssueDate\": \"" + dateUtil.couponIssueDate(104) + "\",\n" +
                        "    \"pointsProgress\": 15.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I am CVS EC card member and made $150 purchase in between Jan 1st to Sep 15th
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C500001669/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 4,\n" +
                        "    \"errorMsg\": \"Card Not on File\"\n" +
                        "}").withStatus(400)));
    }

    /**
     * Delete Stub data for Beauty Club
     */
    public void deleteQuarterlyExtraBucksStubData() {
        wireMockServer.stop();
    }

}
