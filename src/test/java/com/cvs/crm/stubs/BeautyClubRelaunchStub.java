package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@Component
@Slf4j
public class BeautyClubRelaunchStub {

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Beauty Club
     */
    public void createBeautyClubStubData() {
        wireMockServer.start();

        //As a BeautyClub Program member I spend on Beauty products and started earning for both free item and 10% Coupons when both Campaigns are Active
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058601/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 25.43,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 4.57\n" +
                        "  },\n" +
                        "    {\n" +
                        "     \"daysLeft\": 10,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"$10.00 ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 8.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spent on Beauty products only for Free item Coupon when both Free item and 10% Campaigns are Active
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058602/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 12,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 15.43,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 14.57\n" +
                        "  },\n" +
                        "    {\n" +
                        "     \"daysLeft\": 5,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"$10.00 ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spent on Beauty products only for 10% campaign when both Free item and 10% Campaigns are Active
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058603/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 12,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  },\n" +
                        "    {\n" +
                        "     \"daysLeft\": 5,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"$10.00 ExtraBucks Rewards\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 12.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and reached limit for only Free Item Coupon and want to see only Free Item coupon with CpnNbr, Loadable as Y and pts
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058604/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"couponDetails\" : {\n" +
                        "     \"loadable\": true,\n" +
                        "     \"redeemable\": false,\n" +
                        "     \"redeemed\": false,\n" +
                        "     \"expiryDate\": \"nextmonth\",\n" +
                        "     \"couponSequenceNumber\": 90005860411111\n" +
                        "  },\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 0.0,\n" +
                        "    \"offerLimitReached\": true,\n" +
                        "    \"pointsProgress\": 30.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and reached limit for both Free Item and 10% coupon and want to see Free Item and 10% coupons with CpnNbr, Loadable as Y and pts
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058605/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"couponDetails\" : {\n" +
                        "     \"loadable\": true,\n" +
                        "     \"redeemable\": false,\n" +
                        "     \"redeemed\": false,\n" +
                        "     \"expiryDate\": \"nextmonth\",\n" +
                        "     \"couponSequenceNumber\": 90005860511111\n" +
                        "    },\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 0.00,\n" +
                        "    \"offerLimitReached\": true,\n" +
                        "    \"pointsProgress\": 30.0\n" +
                        "  },\n" +
                        "    {\n" +
                        "     \"daysLeft\": 2,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"couponDetails\" : {\n" +
                        "     \"loadable\": true,\n" +
                        "     \"redeemable\": false,\n" +
                        "     \"redeemed\": false,\n" +
                        "     \"expiryDate\": \"2weeks\",\n" +
                        "     \"couponSequenceNumber\": 90005860522222\n" +
                        "    },\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": true,\n" +
                        "    \"extrabuckRewardsEarned\": 20.0,\n" +
                        "    \"couponIssueDate\": \"today\"\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and started earning for both Free Item and 10% coupon and want to see Free Item and 10% coupons with CpnNbr and pts
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058606/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"rewardAmount\": 3.0,\n" +
                        "    \"pointsToNextThreshold\": 9.43,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 20.57\n" +
                        "  },\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 12.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and started earning for free item when only free item campaign is Active and want to see Free Item Coupon with CpnNbr and pts
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058607/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 25.43,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 4.57\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and started earning for 10% Coupon and dont want to see loadActualDate, couponIssueDate and couponDescription
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058608/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 2.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I reached limit and earned 10% Coupon and want to see couponSequenceNumber ,couponIssueDate and loadActualDate but dont want to see couponDescription
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058609/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"couponDetails\" : {\n" +
                        "     \"loadable\": false,\n" +
                        "     \"redeemable\": true,\n" +
                        "     \"redeemed\": false,\n" +
                        "     \"expiryDate\": \"2weeks\",\n" +
                        "     \"couponSequenceNumber\": 90005860922222,\n" +
                        "     \"loadActualDate\": \"today\"\n" +
                        "    },\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": true,\n" +
                        "    \"extrabuckRewardsEarned\": 20.0,\n" +
                        "    \"couponIssueDate\": \"today\"\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and didnt earn points for Free Item Coupon and want to see couponDescription and loadActualDate but dont want to see couponIssueDate
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058610/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "    \"daysLeft\": 22,\n" +
                        "    \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and started earning points for Free Item Coupon and want to see couponDescription but dont want to see couponIssueDate and loadActualDate
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058611/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 5.43,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 24.57\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I spend on Beauty products and reached limit and earned Free Item Coupon and want to see couponSequenceNumber ,couponDescription and loadActualDate but dont want to see couponIssueDate
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058612/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"couponDetails\" : {\n" +
                        "     \"loadable\": false,\n" +
                        "     \"redeemable\": true,\n" +
                        "     \"redeemed\": false,\n" +
                        "     \"expiryDate\": \"nextmonth\",\n" +
                        "     \"couponSequenceNumber\": 90005861211111,\n" +
                        "     \"loadActualDate\": \"today\"\n" +
                        "    },\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 0.00,\n" +
                        "    \"offerLimitReached\": true,\n" +
                        "    \"pointsProgress\": 30.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I didnt spend on Beauty products for Free Item Coupon and want to see only Free Item Coupon with default response including CpnNbr and no pts
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058613/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "    \"daysLeft\": 22,\n" +
                        "    \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I didnt spend on Beauty products for Free Item Coupon and 10% coupon and want to see Free Item Coupon and 10% coupon with default fields with CpnNbr and no pts
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058614/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 22,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0\n" +
                        "  },\n" +
                        "    {\n" +
                        "    \"daysLeft\": 22,\n" +
                        "    \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\"\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see extrabuckRewardsEarned as 0$ when I didn't started earning for 10% Coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058615/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 2,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 0.00\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //"As a BeautyClub Program member I want to see extrabuckRewardsEarned as 10$ when I earned 100pts for 10% Coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058616/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 2,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 10.00\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see extrabuckRewardsEarned as 20$ and offer LimitReached as true when I earned 201pts for 10% Coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058617/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 2,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"couponDetails\" : {\n" +
                        "     \"loadable\": true,\n" +
                        "     \"redeemable\": false,\n" +
                        "     \"redeemed\": false,\n" +
                        "     \"expiryDate\": \"2weeks\",\n" +
                        "     \"couponSequenceNumber\": 90005861733333\n" +
                        "    },\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": true,\n" +
                        "    \"extrabuckRewardsEarned\": 20.0,\n" +
                        "    \"couponIssueDate\": \"today\"\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see freeItemCoupon flag as true when Free Item Campaign is active
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058618/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 10,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see freeItemCoupon flag as false when 10% Campaign is active
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058619/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 2,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 4.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see daysLeft as 0 on last day of month for Free Item coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058620/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 0,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"today\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see daysLeft as 29 on first day of month for Free Item coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058621/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 29,\n" +
                        "     \"freeItemCoupon\": true,\n" +
                        "    \"campaignId\": 11111,\n" +
                        "    \"couponNumber\": 33333,\n" +
                        "    \"webDescription\": \"Beauty Club 2021\",\n" +
                        "    \"couponDescription\": \"Free item for Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"nextmonth\",\n" +
                        "    \"thresholdTypeCode\": \"D\",\n" +
                        "    \"firstThreshold\": 30.0,\n" +
                        "    \"pointsToNextThreshold\": 30.0,\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"pointsProgress\": 0.00\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see daysLeft as 0 on last day of month for 10% coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058622/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 0,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"today\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 4.0\n" +
                        "  }\n" +
                        "  ]\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //As a BeautyClub Program member I want to see daysLeft as 13 on first day of month for 10% coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058623/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"beautyClub\": {\n" +
                        "    \"enrolled\": true,\n" +
                        "    \"earningsProgress\" : [\n" +
                        "    {\n" +
                        "     \"daysLeft\": 13,\n" +
                        "     \"freeItemCoupon\": false,\n" +
                        "    \"campaignId\": 22222,\n" +
                        "    \"couponNumber\": 44444,\n" +
                        "    \"webDescription\": \"10% Off EB Beauty Club campaign\",\n" +
                        "    \"couponDescription\": \"10% Off EB Beauty Club\",\n" +
                        "    \"campaignEndDate\": \"2weeks\",\n" +
                        "    \"offerLimitReached\": false,\n" +
                        "    \"extrabuckRewardsEarned\": 5.0\n" +
                        "  }\n" +
                        "  ]\n" +
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
