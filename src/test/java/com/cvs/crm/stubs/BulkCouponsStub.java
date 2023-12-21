package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class BulkCouponsStub {


    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Beauty Club
     */
    public void createBulkCpnStubData() {
        wireMockServer.start();

        //I am an existing CVS EC Customer and want to View a Coupon which is not viewed before
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/coupons\\?xtra_card_nbr=900058660&msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "   \"viewOutList\": [{\n" +
                        " \"cpnSeqNbr\": 439160103884,\n" +
                        " \"statusCd\": 0,\n" +
                        " \"cmpgnId\":43916,\n" +
                        " \"cpnSkuNbr\":103884,\n" +
                        " \"redeemStartDt\":null,\n" +
                        " \"redeemEndDt\":null\n" +
                        " },\n" +
                        " { \n" +
                        " \"cpnSeqNbr\":439160104064,\n" +
                        " \"statusCd\":0,\n" +
                        "\"cmpgnId\":43916,\n" +
                        "\"cpnSkuNbr\":104064,\n" +
                        "\"redeemStartDt\":null,\n" +
                        "\"redeemEndDt\":null\n" +
                        "}],\n" +
                        " \"printOutList\": [],\n" +
                        " \"loadOutList\": [],\n" +
                        "  \"redeemOutList\": []\n" +
                        "}").withStatus(200)));


        //I am an existing CVS EC Customer and want to View a Coupon which is not viewed before
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/coupons\\?xtra_card_nbr=900058661&msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "   \"viewOutList\": [{\n" +
                        " \"cpnSeqNbr\": 439160103884,\n" +
                        " \"statusCd\": 11,\n" +
                        " \"redeemStartDt\":null,\n" +
                        " \"redeemEndDt\":null\n" +
                        " },\n" +
                        " { \n" +
                        " \"cpnSeqNbr\":439160104064,\n" +
                        " \"statusCd\":11,\n" +
                        "\"redeemStartDt\":null,\n" +
                        "\"redeemEndDt\":null\n" +
                        "}],\n" +
                        " \"printOutList\": [],\n" +
                        " \"loadOutList\": [],\n" +
                        "  \"redeemOutList\": []\n" +
                        "}").withStatus(200)));


        //I am an existing CVS EC Customer and want to View a Coupon which is not viewed before
        //TODO - Stub Data has to be modified
        stubFor(patch(urlMatching("/api/v1.1/coupons\\?xtra_card_nbr=900058662&msg_src_cd=R&user_id=store&src_loc_cd=90037"))
                .willReturn(okJson("{\n" +
                        "   \"viewOutList\": [],\n" +
                        " \"printOutList\": [{\n" +
                        "                       \"cpnSeqNbr\": 439160103884,\n" +
                        "                       \"statusCd\": 0,\n" +
                        "                       \"cmpgnId\":43916,\n" +
                        "                       \"cpnSkuNbr\":103884,\n" +
                        "                       \"redeemStartDt\":null,\n" +
                        "                       \"redeemEndDt\":null\n" +
                        "                       },\n" +
                        "                       { \n" +
                        "                        \"cpnSeqNbr\":439160104064,\n" +
                        "                        \"statusCd\":0,\n" +
                        "                        \"cmpgnId\":43916,\n" +
                        "                        \"cpnSkuNbr\":104064,\n" +
                        "                        \"redeemStartDt\":null,\n" +
                        "                        \"redeemEndDt\":null\n" +
                        "                        }],\n" +
                        " \"loadOutList\": [],\n" +
                        "  \"redeemOutList\": []\n" +
                        "}").withStatus(200)));


    }

    /**
     * Delete Stub data for
     */
    public void deleteBulkCpnStubData() {
        wireMockServer.stop();
    }

}

