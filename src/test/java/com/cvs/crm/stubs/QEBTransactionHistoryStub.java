package com.cvs.crm.stubs;

import com.cvs.crm.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class QEBTransactionHistoryStub {
    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for QEB Transaction History
     */
    public void createQEBTransactionHistoryStubData() {
        wireMockServer.start();

        //    Given "Iam an xtracard member and made single transaction in a day of total $20 purchase of qualified QEB items in previous quarter"
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158378/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(60) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "20\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Iam an xtracard member and made multiple transactions on single day of total $40 purchase  of qualified QEB items in previous quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158379/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(65) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10,\n" +
                        "29,\n" +
                        "1\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Iam an xtracard member and made multiple transactions on multiple days of total $15 purchase of qualified QEB items in previous quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158380/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(50) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(60) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(70) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "5\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(80) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(90) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "4\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and earned quarterly Extra bucks for previous quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158381/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 1,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(30) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(50) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "27\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(80) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "25\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made single transaction in a day of total $20 purchase of qualified QEB items in current quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158382/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "20\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Iam an xtracard member and made multiple transactions on single day of total $40 purchase  of qualified QEB items in current quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158383/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10,\n" +
                        "29,\n" +
                        "1\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Iam an xtracard member and made multiple transactions on multiple days of total $35 purchase of qualified QEB items in current quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158384/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "13,\n" +
                        "7\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "5\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(30) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "6,\n" +
                        "4\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and eligible for quarterly Extra bucks for current quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158385/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(15) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "45,\n" +
                        "15\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made single transaction in a day of total $15 purchase of qualified QEB items in current and $6 purchase of qualified QEB item in previous quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158386/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "15\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(90) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "6\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made multiple transactions in a day of total $25 purchase of qualified QEB items in current and $36 purchase  of qualified QEB item in previous quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158387/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=10")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3,\n" +
                        "12,\n" +
                        "10\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(50) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "5,\n" +
                        "6,\n" +
                        "25\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made multiple transactions on multiple days of total $40 purchase of qualified QEB items in current and $45 purchase of qualified QEB item in previous quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158388/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=10")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "8\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "12\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(25) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(30) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "10\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(48) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "5\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(50) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "6\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(55) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "9\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(60) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "25\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //Iam an xtracard member and eligible for quarterly Extra bucks for current quarter and earned quarterly Extra bucks for previous quarter
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158389/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "  \"extraBucksEarned\": 3,\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(30) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "50\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(60) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "150\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made multiple transactions on multiple days of total $25 purchase of qualified QEB items after the quarter ends during 15 days rolling period
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158390/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=10")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(31) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(32) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2,\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(33) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2,\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(34) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(35) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(36) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(37) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(38) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "3\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made 20 different transactions across 20 days of qualified QEB items and want to see with offset 0 and limit 10
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158391/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=10")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(13) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(14) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(15) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(16) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(17) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(18) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(19) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(21) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(22) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made 20 different transactions across 20 days of qualified QEB items and want to see with offset 0 and limit 10
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158391/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=10&limit=10")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(23) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(24) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(25) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(26) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3,\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(27) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(28) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(29) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "3\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made 20 different transactions across 10 days of qualified QEB items and want to see with offset 5 and limit 5
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158392/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(11) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(22) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2,\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(23) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2,\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(24) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(25) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "2,\n" +
                        "2\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //Iam an xtracard member and made 20 different transactions across 10 days of qualified QEB items and want to see with offset 5 and limit 5
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158392/rewards/quarterlyExtraBucks/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=5&limit=5")
                .willReturn(okJson("{\n" +
                        " \"quarterlyExtraBucks\": [\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(26) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "2\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(27) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2,\n" +
                        "1\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(28) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "2,\n" +
                        "2,\n" +
                        "3\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "	\"transactionDate\": \"" + dateUtil.dealEndDateMinus(29) + "\",\n" +
                        "	\"transactionAmount\": [\n" +
                        "1,\n" +
                        "2,\n" +
                        "2\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


    }

    /**
     * Delete Stub data for QEB Transaction History
     */
    public void deleteQEBTransactionHistoryStubData() {
        wireMockServer.stop();
    }

}
