package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@Component
@Slf4j
public class BeautyClubStub {

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Beauty Club
     */
    public void createBeautyClubStubData() {
        wireMockServer.start();

        //I am active Beauty Club member
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158292/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"campaignId\": 59727,\n" +
                        "    \"webDescription\": \"$3 ExtraBucks Rewards when you spend $30 on beauty\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0,\n" +
                        "    \"pointsToNextThreshold\": 25.43,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 4.57\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I have enrolled in Beauty club program but not made any purchased related to beauty items
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158293/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"campaignId\": 59727,\n" +
                        "    \"webDescription\": \"$3 ExtraBucks Rewards when you spend $30 on beauty\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0,\n" +
                        "    \"pointsToNextThreshold\": 30.00,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I have enrolled in Beauty club program and spend on beauty items
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158294/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"campaignId\": 59727,\n" +
                        "    \"webDescription\": \"$3 ExtraBucks Rewards when you spend $30 on beauty\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0,\n" +
                        "    \"pointsToNextThreshold\": 5.43,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 24.57\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I am in Beauty Club program since last year
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158295/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"campaignId\": 59727,\n" +
                        "    \"webDescription\": \"$3 ExtraBucks Rewards when you spend $30 on beauty\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0,\n" +
                        "    \"pointsToNextThreshold\": 15.03,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 14.97\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am in Beauty Club program from Feb 2020 and spent more than $65
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158296/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"campaignId\": 59727,\n" +
                        "    \"webDescription\": \"$3 ExtraBucks Rewards when you spend $30 on beauty\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0,\n" +
                        "    \"pointsToNextThreshold\": 9.99,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 20.01\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am in Beauty Club program from May 2020 and spent less than $30
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158297/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"campaignId\": 59727,\n" +
                        "    \"webDescription\": \"$3 ExtraBucks Rewards when you spend $30 on beauty\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0,\n" +
                        "    \"pointsToNextThreshold\": 29.50,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.50\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //I am Not enrolled in Beauty Club program now
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158298/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": false,\n" +
                        "    \"campaignId\": 44498,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //I have never enrolled in Beauty Club program
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158299/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": false,\n" +
                        "    \"campaignId\": 44498,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"campaignEndDate\": \"2021-12-31\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0\n" +
                        "  }\n" +
                        "}").withStatus(200)));
    }

    /**
     * Delete Stub data for Beauty Club
     */
    public void deleteBeautyClubStubData() {
        wireMockServer.stop();
    }

}
