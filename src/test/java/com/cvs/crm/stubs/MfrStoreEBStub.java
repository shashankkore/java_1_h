package com.cvs.crm.stubs;

import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
@Slf4j
public class MfrStoreEBStub {
    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for MFR and StoreEB coupon Creations
     */
    public void createMfrStoreEBStubData() {
        wireMockServer.start();


        //Am a CVS Customer and joined Extra Care Program today and scanned MRF coupon from Store
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/coupons?xtra_card_nbr=900058490&msg_src_cd=R&user_id=store&src_loc_cd=90037&cmpgn_id=43325&cpn_sku_nbr=58580")
                .willReturn(okJson("[\n" +
                        "    {\n" +
                        "    \"cpnSeqNbr\": \"900058490043325\",\n" +
                        "    \"cpnStatusCd\":\"0\",\n" +
                        "    \"cmpgnId\": \"43325\",\n" +
                        "    \"cpnSkuNbr\": \"58580\",\n" +
                        "    \"redeemEndDt\": \"2022092607:10:07\"\n" +
                        "    }\n" +
                        "]").withStatus(200)));



        //Am an existing CVS EC Customer and want to get my MFR coupon created through online
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/coupons?xtra_card_nbr=900058491&msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&cmpgn_id=43325&cpn_sku_nbr=58580")
                .willReturn(okJson("[\n" +
                        "    {\n" +
                        "    \"cpnSeqNbr\": \"900058491043325\",\n" +
                        "    \"cpnStatusCd\":\"0\",\n" +
                        "    \"cmpgnId\": \"43325\",\n" +
                        "    \"cpnSkuNbr\": \"58580\",\n" +
                        "    \"redeemEndDt\": \"2022092607:10:07\"\n" +
                        "    }\n" +
                        "]").withStatus(200)));






        //Am an existing CVS EC Customer and want to get my MFR coupon created from store when its already created through online
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/coupons?xtra_card_nbr=900058492&msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&cmpgn_id=43325&cpn_sku_nbr=58580")
                .willReturn(okJson("[\n" +
                        "    {\n" +
                        "    \"cpnSeqNbr\": \"9000584920433250\",\n" +
                        "    \"cpnStatusCd\":\"0\",\n" +
                        "    \"cmpgnId\": \"43325\",\n" +
                        "    \"cpnSkuNbr\": \"58580\",\n" +
                        "    \"redeemEndDt\": \"2022092607:10:07\"\n" +
                        "    }\n" +
                        "]").withStatus(200)));



        //Am an existing CVS EC Customer and want to get my MFR coupon created from store when its already created through online
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/coupons?xtra_card_nbr=900058492&msg_src_cd=R&user_id=store&src_loc_cd=90037&cmpgn_id=43325&cpn_sku_nbr=58580")
                .willReturn(okJson("[\n" +
                        "    {\n" +
                        "    \"cpnSeqNbr\": \"0\",\n" +
                        "    \"cpnStatusCd\":\"14\",\n" +
                        "    \"cmpgnId\": \"43325\",\n" +
                        "    \"cpnSkuNbr\": \"58580\",\n" +
                        "    \"redeemEndDt\": \"2022092607:10:07\"\n" +
                        "    }\n" +
                        "]").withStatus(200)));





        //Am an CVS Employee and want to get my MFR coupon created from store
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/coupons?xtra_card_nbr=900058493&msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&cmpgn_id=43325&cpn_sku_nbr=58580")
                .willReturn(okJson("[\n" +
                        "    {\n" +
                        "    \"cpnSeqNbr\": \"900058493043325\",\n" +
                        "    \"cpnStatusCd\":\"0\",\n" +
                        "    \"cmpgnId\": \"43325\",\n" +
                        "    \"cpnSkuNbr\": \"58580\",\n" +
                        "    \"redeemEndDt\": \"2022092607:10:07\"\n" +
                        "    }\n" +
                        "]").withStatus(200)));



        //Am an Walgreens customer and want to get my MFR coupon created through online
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/coupons?xtra_card_nbr=900058494&msg_src_cd=R&user_id=store&src_loc_cd=90037&cmpgn_id=43325&cpn_sku_nbr=58580")
                .willReturn(okJson("[\n" +
                        "    {\n" +
                        "    \"cpnSeqNbr\": \"900058494043325\",\n" +
                        "    \"cpnStatusCd\":\"0\",\n" +
                        "    \"cmpgnId\": \"43325\",\n" +
                        "    \"cpnSkuNbr\": \"58580\",\n" +
                        "    \"redeemEndDt\": \"2022092607:10:07\"\n" +
                        "    }\n" +
                        "]").withStatus(200)));



        //Am an Hot Card member and want to get my MFR coupon created from store
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/coupons?xtra_card_nbr=900058495&msg_src_cd=R&user_id=store&src_loc_cd=90037&cmpgn_id=43325&cpn_sku_nbr=58580")
                .willReturn(okJson("[\n" +
                        "    {\n" +
                        "    \"cpnSeqNbr\": \"900058495043325\",\n" +
                        "    \"cpnStatusCd\":\"0\",\n" +
                        "    \"cmpgnId\": \"43325\",\n" +
                        "    \"cpnSkuNbr\": \"58580\",\n" +
                        "    \"redeemEndDt\": \"2022092607:10:07\"\n" +
                        "    }\n" +
                        "]").withStatus(200)));


        //I am an existing CVS Customer and joined Extra Care Program today and want to get Store ExtraBucks coupons created for a purchase at a store today and returned at same store on same day
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058496?msg_src_cd=R&user_id=store&src_loc_cd=90037")
                .willReturn(okJson("{\n" +
                        "    \"xtraCardNbr\": 900058496,\n" +
                        "    \"cmpgnId\":40893,\n" +
                        "    \"cpnNbr\": 59296,\n" +
                        "    \"cpnSeqNbr\": 900058496040893,\n" +
                        "    \"cpnFlag\": \"P\",\n" +
                        "    \"firstName\": \"auto\",\n" +
                        "    \"lastName\": \"test\"\n" +
                        "}").withStatus(200)));


        //I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at a store yesterday and returned at different store today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058497?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"xtraCardNbr\": \"900058497\",\n" +
                        "    \"cmpgnId\":\"40893\",\n" +
                        "    \"cpnNbr\": \"59342\",\n" +
                        "    \"cpnSeqNbr\": \"900058497040893\",\n" +
                        "    \"cpnFlag\": \"P\",\n" +
                        "    \"firstName\": \"auto\",\n" +
                        "    \"lastName\": \"test\"\n" +
                        "}").withStatus(200)));

        //I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchased online last week and returned at store today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058498?msg_src_cd=R&user_id=store&src_loc_cd=90037")
                .willReturn(okJson("{\n" +
                        "    \"xtraCardNbr\": \"900058498\",\n" +
                        "    \"cmpgnId\":\"40893\",\n" +
                        "    \"cpnNbr\": \"59300\",\n" +
                        "    \"cpnSeqNbr\": \"900058498040893\",\n" +
                        "    \"cpnFlag\": \"P\",\n" +
                        "    \"firstName\": \"auto\",\n" +
                        "    \"lastName\": \"test\"\n" +
                        "}").withStatus(200)));


        //I am an existing CVS Customer and joined Extra Care Program today and want to get Store ExtraBucks coupons created for a purchase at a store today and returned at same store on same day
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058499?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"xtraCardNbr\": \"900058499\",\n" +
                        "    \"cmpgnId\":\"40893\",\n" +
                        "    \"cpnNbr\": \"59298\",\n" +
                        "    \"cpnSeqNbr\": \"900058499040893\",\n" +
                        "    \"cpnFlag\": \"P\",\n" +
                        "    \"firstName\": \"auto\",\n" +
                        "    \"lastName\": \"test\"\n" +
                        "}").withStatus(200)));


        //I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at a store yesterday and returned at different store today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058500?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"xtraCardNbr\": \"900058500\",\n" +
                        "    \"cmpgnId\":\"40893\",\n" +
                        "    \"cpnNbr\": \"59300\",\n" +
                        "    \"cpnSeqNbr\": \"900058500040893\",\n" +
                        "    \"cpnFlag\": \"P\",\n" +
                        "    \"firstName\": \"auto\",\n" +
                        "    \"lastName\": \"test\"\n" +
                        "}").withStatus(200)));


        //I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchased online last week and returned at store today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058501?msg_src_cd=R&user_id=store&src_loc_cd=90037")
                .willReturn(okJson("{\n" +
                        "    \"xtraCardNbr\": \"900058501\",\n" +
                        "    \"cmpgnId\":\"40893\",\n" +
                        "    \"cpnNbr\": \"59299\",\n" +
                        "    \"cpnSeqNbr\": \"900058501040893\",\n" +
                        "    \"cpnFlag\": \"P\",\n" +
                        "    \"firstName\": \"auto\",\n" +
                        "    \"lastName\": \"test\"\n" +
                        "}").withStatus(200)));


        //I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchased online last week and returned at store today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058502?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"xtraCardNbr\": \"900058502\",\n" +
                        "    \"cmpgnId\":\"40893\",\n" +
                        "    \"cpnNbr\": \"59295\",\n" +
                        "    \"cpnSeqNbr\": \"900058502040893\",\n" +
                        "    \"cpnFlag\": \"P\",\n" +
                        "    \"firstName\": \"auto\",\n" +
                        "    \"lastName\": \"test\"\n" +
                        "}").withStatus(200)));


        //I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchased online last week and returned at store today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058503?msg_src_cd=R&user_id=store&src_loc_cd=90037")
                .willReturn(okJson("Card Not on File").withStatus(400)));


        //Am an Hot Card member and want to get my Store ExtraBucks coupons created
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.2/coupons/0002%2C900058504?msg_src_cd=R&user_id=store&src_loc_cd=90037")
                .willReturn(okJson("HOT XC Card").withStatus(400)));
    }

    /**
     * Delete Stub data for MFR and StoreEB coupon Creations
     */
    public void deleteMfrStoreEBStubData() {
        wireMockServer.stop();
    }

}
