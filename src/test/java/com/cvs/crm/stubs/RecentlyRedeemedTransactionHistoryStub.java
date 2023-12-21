package com.cvs.crm.stubs;

import com.cvs.crm.util.CarePassDateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.cvs.crm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@Component
@Slf4j
public class RecentlyRedeemedTransactionHistoryStub {
    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Recently Redeemed Transaction History
     */
    public void createRecentlyRedeemedHistoryStubData() {
        wireMockServer.start();

        //I joined ExtraCare program today to participate in CarePass Yearly program and want to see 10$ and 20% coupons
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158327/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 OFF\",\n" +
                        "  \"couponDescription\": \"$3 off purchase\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 3.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I joined ExtraCare program today to participate in CarePass Yearly program and want to see 10$ and 20% coupons
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158328/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$2 off LOREAL LIPSTICK\",\n" +
                        "  \"couponDescription\": \"$2 OFF (3) LOREAL LIPSTICK\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 OFF\",\n" +
                        "  \"couponDescription\": \"$3 off purchase\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 5.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am an Xtra Card member and redeemed three $1, $2 coupon and $3EB in three seperate transaction today
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158329/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(0) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"Save $1 on Ice Cubes\",\n" +
                        "  \"couponDescription\": \"Save $1 on Ice Cubes 3pk\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$2 off LOREAL LIPSTICK\",\n" +
                        "  \"couponDescription\": \"$2 OFF (3) LOREAL LIPSTICK\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 OFF\",\n" +
                        "  \"couponDescription\": \"$3 off purchase\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 6.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am an Xtra Card member and redeemed $2 coupons $10 EB carepass coupon
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158330/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$2 off LOREAL LIPSTICK\",\n" +
                        "  \"couponDescription\": \"$2 OFF (3) LOREAL LIPSTICK\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"CarePass $10 reward\",\n" +
                        "  \"couponDescription\": \"CarePass $10 Reward\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 12.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am an Xtra Card member and redeemed $10 off on $50 above 3 days before
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158331/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$10 off Purchase of $50\",\n" +
                        "  \"couponDescription\": \"$10 off Purchase of $50 or More\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 10.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am an Xtra Card member and redeemed 20% carepass coupon within 14 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158332/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(14) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"CAREPASS 20%OFF CVS HEALTH\",\n" +
                        "  \"couponDescription\": \"20% off CVS Health brand benefit for CarePass members\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 10.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am an Xtra Card member and redeemed BOGO offer or %50 on 2nd item within 14 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158333/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(13) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"BOGO CANDY\",\n" +
                        "  \"couponDescription\": \"BOGO Candy Offer\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 2.99\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card member and redeemed $1 off MFR offer on Bounty within 14 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158334/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(14) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$1 off Nature's Bounty\",\n" +
                        "  \"couponDescription\": \"$1 off Nature's Bounty or Radiance Black Cohosh\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 1.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card member and redeemed $2 off MFR lysol offer and $2 CVS offer in same transaction within last 14 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158335/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(13) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$2 off 2 Lysol Wipes\",\n" +
                        "  \"couponDescription\": \"$2 off 2 Lysol Wipes. One Day Only\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$2 off LOREAL LIPSTICK\",\n" +
                        "  \"couponDescription\": \"$2 OFF (3) LOREAL LIPSTICK\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 4.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card member and redeemed 20% non carepass coupon within 14 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158336/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(12) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"20% Off any Vicks_txt\",\n" +
                        "  \"couponDescription\": \"20% Off any Vicks\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 20.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card member and redeemed $5 EB 10 days before
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158337/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"PE coupon $5\",\n" +
                        "  \"couponDescription\": \"$5.00 ExtraBucks Rewards\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 5.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card member and made one $3 coupon transaction online and $2 coupon in store yesterday
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158338/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"2 Dollars off Cosmetics\",\n" +
                        "  \"couponDescription\": \"You received a 2 dollar off cosmetics coupon!!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 5.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card member and made one $3 coupon transaction online and $2 coupon in store today
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158339/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(0) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"2 Dollars off Cosmetics\",\n" +
                        "  \"couponDescription\": \"You received a 2 dollar off cosmetics coupon!!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 5.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am an Xtra Card member have request for XX limit  transactions (Check for response is sorted in descending order by date
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158340/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"2 Dollars off Cosmetics\",\n" +
                        "  \"couponDescription\": \"You received a 2 dollar off cosmetics coupon!!\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 2.0\n" +
                        "},\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$1 off Nature's Bounty\",\n" +
                        "  \"couponDescription\": \"$1 off Nature's Bounty or Radiance Black Cohosh\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 1.0\n" +
                        "},\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"2 Dollars off Cosmetics\",\n" +
                        "  \"couponDescription\": \"You received a 2 dollar off cosmetics coupon!!\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 2.0\n" +
                        "},\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(4) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 3.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Check if limit is more than number of record during first calls vs next calls
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158341/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(0) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 15.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Check if limit is more than number of record during first calls vs next calls
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158341/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=5&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(0) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$1 off Nature's Bounty\",\n" +
                        "  \"couponDescription\": \"$1 off Nature's Bounty or Radiance Black Cohosh\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$3 off ThermaCare Heatwrap\",\n" +
                        "  \"couponDescription\": \"$3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon!\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 13.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Check if limit is more than number of record during first calls vs next calls
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158342/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$1 off Nature's Bounty\",\n" +
                        "  \"couponDescription\": \"$1 off Nature's Bounty or Radiance Black Cohosh\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$1 off Nature's Bounty\",\n" +
                        "  \"couponDescription\": \"$1 off Nature's Bounty or Radiance Black Cohosh\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$1 off Nature's Bounty\",\n" +
                        "  \"couponDescription\": \"$1 off Nature's Bounty or Radiance Black Cohosh\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$1 off Nature's Bounty\",\n" +
                        "  \"couponDescription\": \"$1 off Nature's Bounty or Radiance Black Cohosh\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 4.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am an Xtra Card member and redeemed three $ 10 coupons in single transaction 14 days before
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158343/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "{\n" +
                        " \"redeemDate\": \"" + dateUtil.dealEndDateMinus(15) + "\",\n" +
                        " \"couponInfo\": [\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$10 off Purchase of $50\",\n" +
                        "  \"couponDescription\": \"$10 off Purchase of $50 or More\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$10 off Purchase of $50\",\n" +
                        "  \"couponDescription\": \"$10 off Purchase of $50 or More\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"couponHeader\": \"$10 off Purchase of $50\",\n" +
                        "  \"couponDescription\": \"$10 off Purchase of $50 or More\"\n" +
                        "}\n" +
                        "],\n" +
                        "  \"savedAmount\": 30.0\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra card member and didnt redeem any coupon in last 14 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158344/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"redeemedDeals\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am using my walgreens card and want to see my redeem transaction history
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158345/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 4,\n" +
                        "    \"errorMsg\": \"Card Not on File\"\n" +
                        "}").withStatus(400)));


        //I am an Hot card member and want to see  my redeem transaction history for witnin 14 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158346/rewards/redeemedDeals/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 5,\n" +
                        "    \"errorMsg\": \"HOT XC Card\"\n" +
                        "}").withStatus(400)));


    }

    /**
     * Delete Stub data for Recently Redeemed Transaction History
     */
    public void deleteRecentlyRedeemedHistoryStubData() {
        wireMockServer.stop();
    }

}
