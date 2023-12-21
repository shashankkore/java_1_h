package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.CarePassDateUtil;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class HonorEscalatingCEBCmpgnEarnStub {
    @Autowired
    DateUtil dateUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Beauty Club
     */
    public void createHonorEscalatingCEBCmpgnEarnStubData() {
        wireMockServer.start();

        //I am an existing CVS EC Customer and want to View a Coupon which is not viewed before
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/campaign_earnings\\?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "\"respCmpgnEarnings\": [{\n" +
                        " \"cmpgnId\": 46680,\n" +
                        " \"pts\": {\n" +
                        " \"prevPtsAmt\": 0,\n" +
                        " \"newPtsAmt\":80,\n" +
                        " \"deltaPtsAmt\":80,\n" +
                        " \"contSkuNbrList\": []\n" +
                        " },\n" +
                        "\"rwrds\": { \n" +
                        " \"prevRwrdsQty\":0,\n" +
                        " \"newRwrdsQty\":5,\n" +
                        "\"deltaRwrdsQty\":5,\n" +
                        "\"offerLimitReachedInd\":false\n" +
                        "},\n" +
                        "\"cpns\": { \n" +
                        " \"cpnNbr\":107098,\n" +
                        " \"cpnSeqNbr\":900058720_107098,\n" +
                        "\"cmpgnCpnSeqNbr\":1,\n" +
                        "\"expDt\": \"DAYS_TO_PRNT_CNT\"\n" +
                        "},\n" +
                        "}],\n" +
                        "}").withStatus(200)));

    }

    /**
     * Delete Stub data for
     */
    public void deleteHonorEscalatingCEBCmpgnEarnStubData() {
        wireMockServer.stop();
    }

}