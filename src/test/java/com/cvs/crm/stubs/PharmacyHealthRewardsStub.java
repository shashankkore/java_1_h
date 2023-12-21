package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cvs.crm.util.DateUtil;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@Component
@Slf4j
public class PharmacyHealthRewardsStub {
    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Pharmacy Health Rewards
     */
    public void createPharmacyHealthRewardsStubData() {
        wireMockServer.start();

        //I am a non-targered customer with PHR dependent who recently enrolled
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158276/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 200.0,\n" +
                        "    \"maxRewardAmount\": 100.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I and my wife are non-targered customers with 2 PHR dependents who recently enrolled
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158277/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"Donna\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Tim\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 400.0,\n" +
                        "    \"maxRewardAmount\": 200.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am targered customer with PHR dependent who recently enrolled
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158278/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 200.0,\n" +
                        "    \"maxRewardAmount\": 100.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 64355,\n" +
                        "    \"webDescription\": \"$2 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 4.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 4.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I and my wife are targered customers with 2 PHR dependents who recently enrolled
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158279/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"Donna\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Tim\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 400.0,\n" +
                        "    \"maxRewardAmount\": 200.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 64355,\n" +
                        "    \"webDescription\": \"$2 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 4.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 4.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I and my wife joined PHR as non-targeted customer in 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158280/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 200.0,\n" +
                        "    \"maxRewardAmount\": 100.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 4.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 6.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 6.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I and my wife joined PHR as targeted customer in 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158281/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 200.0,\n" +
                        "    \"maxRewardAmount\": 100.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 64355,\n" +
                        "    \"webDescription\": \"$2 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 4.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 3.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 1.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 1.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I and my wife joined PHR as non-targeted customer in 2015 but my wife's HIPPA expired
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158282/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 200.0,\n" +
                        "    \"maxRewardAmount\": 100.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have one of the member filled 100 prescriptions and now I get 50 points
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158283/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": true,\n" +
                        "    \"cappedDate\": \"" + dateUtil.dealEndDate(0) + "\",\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            },\n" +
                        "            {\n" +
                        "    \"firstName\": \"Mary\",\n" +
                        "    \"capped\": true,\n" +
                        "    \"cappedDate\": \"" + dateUtil.dealEndDate(0) + "\",\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 200.0,\n" +
                        "    \"maxRewardAmount\": 100.0,\n" +
                        "    \"capped\": true,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 50.0,\n" +
                        "    \"yearToDateCredits\": 100.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I recently enrolled in PHR as non-targeted customer
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158284/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I recently enrolled in PHR as targeted customer
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158285/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 64355,\n" +
                        "    \"webDescription\": \"$2 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 4.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 4.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am a targeted customer who joined PHR in 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158286/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 64355,\n" +
                        "    \"webDescription\": \"$2 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 4.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 1.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 3.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 3.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am a non-targeted customer who joined PHR in 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158287/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 8.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 2.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 2.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am a non-targeted customer who filled 150 prescriptions
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158288/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": true,\n" +
                        "    \"cappedDate\": \"" + dateUtil.dealEndDate(0) + "\",\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0,\n" +
                        "    \"capped\": true,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 75.0,\n" +
                        "    \"yearToDateCredits\": 150.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have never enrolled into PHR
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158289/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": false,\n" +
                        "    \"members\": [\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 0.0,\n" +
                        "    \"maxRewardAmount\": 0.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 44496,\n" +
                        "    \"webDescription\": \"2021 - PHR Regular\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have unenrolled from PHR
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158290/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": false,\n" +
                        "    \"members\": [\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 0.0,\n" +
                        "    \"maxRewardAmount\": 0.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 5.0,\n" +
                        "    \"pointsToNextThreshold\": 10.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have enrolled in PHR but my HIPPA expired
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158291/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"pharmacyHealthRewards\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"members\": [\n" +
                        "            {\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"capped\": false,\n" +
                        "    \"maxCredits\": 100.0,\n" +
                        "    \"maxRewardAmount\": 50.0\n" +
                        "            }\n" +
                        "            ],\n" +
                        "    \"maxCredits\": 200.0,\n" +
                        "    \"maxRewardAmount\": 100.0,\n" +
                        "    \"capped\": false,\n" +
                        "    \"campaignId\": 59726,\n" +
                        "    \"webDescription\": \"$5 Pharmacy and Health ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 10.0,\n" +
                        "    \"rewardAmount\": 0.0,\n" +
                        "    \"pointsToNextThreshold\": 0.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0,\n" +
                        "    \"yearToDateEarned\": 0.0,\n" +
                        "    \"yearToDateCredits\": 0.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));
    }

    /**
     * Delete Stub data for Pharmacy Health Rewards
     */
    public void deletePharmacyHealthRewardsStubData() {
        wireMockServer.stop();
    }
}