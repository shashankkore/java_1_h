package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@Component
@Slf4j
public class XtraCardSummaryStub {

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Xtra Card Summary
     */
    public void createXtraCardSummaryStubData() {
        wireMockServer.start();

        //WEB - I recently joined as Extracare Member
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158260/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));

        //WEB - I am an Existing Extracare Member since Feb 2020
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158261/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 9.50,\n" +
                        "    \"totalLifetimeSaving\": 10.00,\n" +
                        "    \"availableExtraBucks\": 7.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-02-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //WEB - I am an Existing Extracare Member since March 2018
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158262/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 12.00,\n" +
                        "    \"totalLifetimeSaving\": 50.00,\n" +
                        "    \"availableExtraBucks\": 9.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2018-03-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //WEB - I am an Existing Extracare Member since March 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158263/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 6.00,\n" +
                        "    \"totalLifetimeSaving\": 50000.00,\n" +
                        "    \"availableExtraBucks\": 59.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2015-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //WEB - I am an Existing Extracare Member since December 31st 2014
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158264/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 7.00,\n" +
                        "    \"totalLifetimeSaving\": 100.00,\n" +
                        "    \"availableExtraBucks\": 38.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2015-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //WEB - I am an Existing Extracare Member and an Employee
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158271/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 2.00,\n" +
                        "    \"totalLifetimeSaving\": 10.00,\n" +
                        "    \"availableExtraBucks\": 3.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2019-12-31\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //WEB - I am an Existing Extracare Member and have Caremark Customer Heatlh Card
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158273/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 17.00,\n" +
                        "    \"totalLifetimeSaving\": 40.00,\n" +
                        "    \"availableExtraBucks\": 8.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2017-11-30\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //WEB - I will be a New Extracare Member planning to join from Jan 2nd 2021
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158275/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 17.00,\n" +
                        "    \"totalLifetimeSaving\": 40.00,\n" +
                        "    \"availableExtraBucks\": 8.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2021-01-02\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //WEB - I am an Existing Extracare Member since Jan 1st 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158258/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 7.00,\n" +
                        "    \"totalLifetimeSaving\": 100.00,\n" +
                        "    \"availableExtraBucks\": 38.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2015-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));


        //WEB - I am an Existing Extracare Member since Jun 28th 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158259/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 7.00,\n" +
                        "    \"totalLifetimeSaving\": 100.00,\n" +
                        "    \"availableExtraBucks\": 38.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2015-06-28\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));


        //MOBILE - I recently joined as Extracare Member
        stubFor(get("/api/v1.1/customers/0002%2C98158265/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //MOBILE - I am an Existing Extracare Member since Feb 2020
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158266/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 9.50,\n" +
                        "    \"totalLifetimeSaving\": 10.00,\n" +
                        "    \"availableExtraBucks\": 7.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-02-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //MOBILE - I am an Existing Extracare Member since March 2018
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158267/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 12.00,\n" +
                        "    \"totalLifetimeSaving\": 50.00,\n" +
                        "    \"availableExtraBucks\": 9.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2018-03-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //MOBILE - I am an Existing Extracare Member since March 2015
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158268/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 6.00,\n" +
                        "    \"totalLifetimeSaving\": 50000.00,\n" +
                        "    \"availableExtraBucks\": 59.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2015-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //MOBILE - I am an Existing Extracare Member since December 31st 2014
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158269/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 7.00,\n" +
                        "    \"totalLifetimeSaving\": 100.00,\n" +
                        "    \"availableExtraBucks\": 38.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2015-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));

        //MOBILE - I am an Existing Extracare Member and an Employee
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158270/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 2.00,\n" +
                        "    \"totalLifetimeSaving\": 10.00,\n" +
                        "    \"availableExtraBucks\": 3.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2019-12-31\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));


        //MOBILE - I am an Existing Extracare Member and have Caremark Customer Health Card
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158272/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 17.00,\n" +
                        "    \"totalLifetimeSaving\": 40.00,\n" +
                        "    \"availableExtraBucks\": 8.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2017-11-30\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}")));


        //MOBILE - I am using my Walgreens card to look my Dashboard
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C500001668/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 4,\n" +
                        "    \"errorMsg\": \"Card Not on File\"\n" +
                        "}").withStatus(400)));

//MOBILE - As a fraudulent user, I would like to see my Dashboard
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C98158274/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 5,\n" +
                        "    \"errorMsg\": \"HOT XC Card\"\n" +
                        "}").withStatus(400)));

//MOBILE - After I purge my profile in CVS , I would like to verify Dashboard for XtraCard Summary
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C990090723/dashboards?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 4,\n" +
                        "    \"errorMsg\": \"CPA\"\n" +
                        "}").withStatus(400)));


        //WEB - I am existing Default BC Customer and want to see earningsType indicator as D
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058661/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //WEB - I am existing XC customer but never enrolled in any BC program and want to see earningsType indicator as D
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058662/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //WEB - I am new XC Customer and enrolled in 10% BC program and want to see earningsType indicator as P
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058663/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"P\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //WEB - I am new XC Customer and enrolled in Free Item BC program and want to see earningsType indicator as P
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058664/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"P\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //WEB - I am new XC Customer and enrolled in both Free Item and 10% BC programs and want to see earningsType indicator as P
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058665/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"P\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));


        //WEB - I am new XC Customer I have unenrolled from both Free Item and 10% BC programs and want to see earningsType indicator as D
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058666/dashboards?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"extraCareCardSummary\": {\n" +
                        "    \"bcEarningsType\": \"D\",\n" +
                        "    \"totalYearToDateSaving\": 0.00,\n" +
                        "    \"totalLifetimeSaving\": 0.00,\n" +
                        "    \"availableExtraBucks\": 0.00,\n" +
                        "    \"lifetimeSavingStartDate\": \"2020-01-01\",\n" +
                        "    \"yearToDateStartDate\": \"2021-01-01\"\n" +
                        "  }\n" +
                        "}").withStatus(200)));
    }

    /**
     * Delete Stub data for Xtra Card Summary
     */
    public void deleteXtraCardSummaryStubData() {
        wireMockServer.stop();
    }
}
