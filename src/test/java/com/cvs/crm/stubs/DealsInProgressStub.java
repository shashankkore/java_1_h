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
public class DealsInProgressStub {
    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Deals In Progress
     */
    public void createDealsInProgressStubData() {
        wireMockServer.start();

        //I am able to see deals mapped to correct type based on threshold Q (Qty) and D (Dollar)
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158322/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "    \"campaignId\": 41624,\n" +
                        "    \"webDescription\": \"PE coupon $5\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.dealEndDate(60) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"campaignTypeCode\": \"E\",\n" +
                        "    \"campaignSubtypeCode\": \"T\",\n" +
                        "    \"pointsQuantity\": 30.0,\n" +
                        "    \"newDeal\": false,\n" +
                        "    \"dealEndingSoon\": false\n" +
                        "  }]\n" +
                        "}").withStatus(200)));

        //I am CVS EC member and have at least one deals in progress
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158323/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "    \"campaignId\": 41624,\n" +
                        "    \"webDescription\": \"PE coupon $5\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.dealEndDate(60) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 20.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"campaignTypeCode\": \"E\",\n" +
                        "    \"campaignSubtypeCode\": \"T\",\n" +
                        "    \"pointsQuantity\": 40.0,\n" +
                        "    \"newDeal\": false,\n" +
                        "    \"dealEndingSoon\": false\n" +
                        "  }]\n" +
                        "}").withStatus(200)));

        //I am CVS EC member but deal is expiring soon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158324/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "    \"campaignId\": 40225,\n" +
                        "    \"webDescription\": \"PEB_FF_CYL_$2EB on 4 Candies\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.dealEndDate(2) + "\",\n" +
                        "    \"thresholdTypeCode\": \"Q\",\n" +
                        "    \"firstThreshold\": 4.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 2.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"campaignTypeCode\": \"E\",\n" +
                        "    \"campaignSubtypeCode\": \"T\",\n" +
                        "    \"pointsQuantity\": 10.0,\n" +
                        "    \"newDeal\": false,\n" +
                        "    \"dealEndingSoon\": true\n" +
                        "  }]\n" +
                        "}").withStatus(200)));

        //I am CVS EC member joined recently in deal
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158325/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "    \"campaignId\": 40226,\n" +
                        "    \"webDescription\": \"PEB_FF_Cyl_$2EB on $3\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.dealEndDate(60) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 28.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"campaignTypeCode\": \"E\",\n" +
                        "    \"campaignSubtypeCode\": \"T\",\n" +
                        "    \"pointsQuantity\": 32.0,\n" +
                        "    \"newDeal\": true,\n" +
                        "    \"dealEndingSoon\": false\n" +
                        "  }]\n" +
                        "}").withStatus(200)));

        //I am CVS EC member and showing deals in progress points near threshold
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158326/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "    \"campaignId\": 41624,\n" +
                        "    \"webDescription\": \"PE coupon $5\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.dealEndDate(60) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 1.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"campaignTypeCode\": \"E\",\n" +
                        "    \"campaignSubtypeCode\": \"T\",\n" +
                        "    \"pointsQuantity\": 29.0,\n" +
                        "    \"newDeal\": false,\n" +
                        "    \"dealEndingSoon\": false\n" +
                        "  }]\n" +
                        "}").withStatus(200)));

        //I am CVS EC member but there are no active deals in progress - Zero State
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158319/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "    \"campaignId\": 41624,\n" +
                        "    \"webDescription\": \"PE coupon $5\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.dealEndDate(60) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"campaignTypeCode\": \"E\",\n" +
                        "    \"campaignSubtypeCode\": \"T\",\n" +
                        "    \"pointsQuantity\": 0.0,\n" +
                        "    \"newDeal\": false,\n" +
                        "    \"dealEndingSoon\": false\n" +
                        "  }]\n" +
                        "}").withStatus(200)));

        //I am CVS EC member but does not have any activity towards Circular EB and Personalized EB campaigns
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158320/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "  }]\n" +
                        "}").withStatus(200)));

        //I am CVS EC member and showing deals in progress point is zero
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158321/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"dealsInProgress\": [{\n" +
                        "    \"campaignId\": 41624,\n" +
                        "    \"webDescription\": \"PE coupon $5\",\n" +
                        "    \"campaignEndDate\": \"" + dateUtil.dealEndDate(60) + "\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 2.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"campaignTypeCode\": \"E\",\n" +
                        "    \"campaignSubtypeCode\": \"T\",\n" +
                        "    \"pointsQuantity\": 0.0,\n" +
                        "    \"newDeal\": false,\n" +
                        "    \"dealEndingSoon\": false\n" +
                        "  }]\n" +
                        "}").withStatus(200)));
    }

    /**
     * Delete Stub data for Deals in Progress
     */
    public void deleteDealsInProgressStubData() {
        wireMockServer.stop();
    }

}

