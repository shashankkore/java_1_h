package com.cvs.crm.stubs;

import com.cvs.crm.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class PHRTransactionHistoryStub {
    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for PHR Transaction History
     */
    public void createPHRHistoryStubData() {
        wireMockServer.start();

        //I recently enrolled in PHR as non-targeted customer and made single Flu Shot transaction 3 days ago
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158363/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"5\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"10\",\n" +
                        "  \"eventType\": \"Flu shot\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I recently enrolled in PHR as targeted customer and made single 90-day prescription,30-day prescription, Online prescription access, Flu Shot and Immunization yesterday
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158364/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 10 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"2\",\n" +
                        "  \"eventType\": \"Online prescription access added\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"10\",\n" +
                        "  \"eventType\": \"Flu shot\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am a non-targeted customer and made 90-day prescription 2 days ago,30-day prescription every month, Online prescription access last month, Flu Shot in 20 days before and Immunization 10 days before
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158365/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=20")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"10\",\n" +
                        "  \"eventType\": \"Flu shot\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(35) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(45) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"5\",\n" +
                        "  \"eventType\": \"Online prescription access added\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(65) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(95) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(125) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(155) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(185) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(215) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am a non-targeted customer with 1 PHR dependent who recently enrolled and both of us made single Prescription fill yesterday
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158366/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I and my wife are non-targeted customers with 2 PHR dependents and I made one 90-day prescription 2 days ago, my wife made 30-day prescription yesterday and my kids had Immunization last month on different days
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158367/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(35) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(45) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am targeted customer with 2 PHR dependents who recently enrolled and made multiple same Prescription fills on same day and earned Extra bucks
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158368/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 6 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I and my wife joined PHR as non-targeted customers and made single 90-day prescription,30-day prescription, Online prescription access, Flu Shot and Immunization last week"
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158369/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 25 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"5\",\n" +
                        "  \"eventType\": \"Online prescription access added\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"5\",\n" +
                        "  \"eventType\": \"Online prescription access added\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"10\",\n" +
                        "  \"eventType\": \"Flu shot\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"10\",\n" +
                        "  \"eventType\": \"Flu shot\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I have one of the member filled 100(30 days prescriptions) from multiple transactions on multiple days and now I get 100 points
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158370/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"15\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"10\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 25 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"45\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(4) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"15\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I and my dependents are non-targeted customers and made 20 different transactions across 20 days and want to see with offset 0 and limit 10
        //TODO - Stub Data has to be modifieid
        stubFor(get("/api/v1.1/0002%2C98158371/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=10")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(4) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(6) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(7) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(8) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(9) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I and my dependents are non-targeted customers and made 20 different transactions across 20 days and want to see with offset 0 and limit 10
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158371/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=10&limit=10")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(11) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(12) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(13) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(14) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(15) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(16) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(17) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(18) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"1\",\n" +
                        "  \"eventType\": \"30-day prescription\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(19) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(20) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I and my dependents are non-targeted customers and made 50 same transactions across 10 days and want to see with offset 5 and limit 5
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158372/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(4) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(5) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I and my dependents are non-targeted customers and made 50 same transactions across 10 days and want to see with offset 5 and limit 5
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158372/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=5&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(6) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(7) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(8) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(9) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(10) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"30\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //I and my dependents are non-targeted customers and one of us reached the CAP
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158373/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 15 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"36\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 10 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"24\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"12\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 50 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(4) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"102\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I and my dependents are non-targeted customers and all of us reached the CAP
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158374/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 50 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"102\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 50 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"102\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Mary\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 50 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"102\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Sam\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 50 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(4) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"102\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"Alisha\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I am a non-targeted customer with PHR dependent who recently enrolled and made 0 transactions
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158375/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I have unenrolled from PHR yesterday and want to see my transactions history till yesterday
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158376/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(1) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"3\",\n" +
                        "  \"eventType\": \"90-day prescription\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(2) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"10\",\n" +
                        "  \"eventType\": \"Flu shot\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"extraBucksEarned\": \"" + 5 + "\",\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(3) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"6\",\n" +
                        "  \"eventType\": \"Immunization\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        " \"transactionDate\": \"" + dateUtil.dealEndDateMinus(4) + "\",\n" +
                        " \"creditsSummary\": [\n" +
                        "{\n" +
                        "  \"creditsEarned\": \"5\",\n" +
                        "  \"eventType\": \"Online prescription access added\",\n" +
                        "  \"firstName\": \"John\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //I have never enrolled into PHR and I haven't made any transactions
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/0002%2C98158377/rewards/pharmacyHealthRewards/history?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&offset=0&limit=5")
                .willReturn(okJson("{\n" +
                        "  \"pharmacyHealthRewards\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


    }

    /**
     * Delete Stub data for PHR Transaction History
     */
    public void deletePHRHistoryStubData() {
        wireMockServer.stop();
    }

}
