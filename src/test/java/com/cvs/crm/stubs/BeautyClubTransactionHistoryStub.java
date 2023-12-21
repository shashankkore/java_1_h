package com.cvs.crm.stubs;

import com.cvs.crm.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class BeautyClubTransactionHistoryStub {
    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Recently Redeemed Transaction History
     */
    public void createBeautyClubTransactionHistoryStubData() {
        wireMockServer.start();

        //I enrolled in Beauty Club Program and made single transaction of $10 purchase of qualified Beauty Club items dated today-1 day
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158347/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and made two transaction of $10 purchase each for qualified Beauty Club items dated today-1 day
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158348/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10,\n" +
                        "10\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and have made $5 progress is already available, made single $35 purchase of qualified Beauty Club items dated today–1 day
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158349/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 3,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "35\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(30) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "5\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I enrolled in Beauty Club Program and made two separate transactions each $15 and $25 of qualified Beauty Club items dated today-1 day
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158350/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 3,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "15,\n" +
                        "25\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I enrolled in Beauty Club Program have $25 progress is already available and made 4 separate transactions each $5, $15, $25 & $15 of qualified Beauty Club items dated today-1 day
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158351/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 6,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "15,\n" +
                        "15,\n" +
                        "25,\n" +
                        "5\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card existing BC member have $25 progress is already available and made 4 separate transactions each $5, $15, $25 & $15 of BC qualified items dated current date - 1 day
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158351/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 6,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "5,\n" +
                        "15,\n" +
                        "25,\n" +
                        "15\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(100) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "25\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and made 1 single transaction of $100 of qualified Beauty Club items dated today-5 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158352/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 9,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "100\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I am an Xtra Card BC member and made single $10 purchase of BC qualified items dated current date - 2 day and $40 purchase of BC qualified items dated current date-30 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158353/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 3,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(30) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "40\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and made two $10 purchases of qualified Beauty Club items dated today-3 days and today-200 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158354/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(200) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and made single $35 purchase of qualified Beauty Club items dated current date–367 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158355/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and made two separate transactions each $1 and $2 of qualified Beauty Club items dated today-5 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158356/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2,\n" +
                        "1\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and made 4 separate transactions $5, $15, $25 & $15 of qualified Beauty Club items dated today-5 days, today-10 days, today-15 days and today-20 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158357/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "5\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 3,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "15\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 3,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(15) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "25\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "15\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program and made 1 single transaction of $1 of qualified Beauty Club items dated today-90 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158358/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(90) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I enrolled in Beauty Club Program had current progress $25, make purchase of $6 for qualified Beauty Club today–20 days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158359/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 3,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "6\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(90) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "25\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I enrolled in Beauty Club Program but has NOT made BC purchase this year
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158360/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"beautyClub\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am using my Walgreens card and want to see my redeem transaction history
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158361/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"errorCd\": 4,\n" +
                        " \"errorMsg\": \"Card Not on File" + "\"\n" +
                        "}").withStatus(400)));

        //I am an Hot card member and want to see  BC transaction history for YTD
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C900058504/rewards/beautyClub/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 5,\n" +
                        "    \"errorMsg\": \"HOT XC Card\"\n" +
                        "}").withStatus(400)));

    }


    /**
     * Delete Stub data for Beauty Club Transaction History
     */
    public void deleteBeautyClubTransactionHistoryStubData() {
        wireMockServer.stop();
    }

}
