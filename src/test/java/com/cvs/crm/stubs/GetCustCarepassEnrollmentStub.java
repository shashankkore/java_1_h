package com.cvs.crm.stubs;

import com.cvs.crm.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import com.cvs.crm.util.CarePassDateUtil;

@Component
@Slf4j
public class GetCustCarepassEnrollmentStub {
    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    DateUtil dateUtil;

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Deals In Progress
     */
    public void createGetCustCarepassEnrollmentStubData() {
        wireMockServer.start();

        //I am a carepass member enrolled under monthly program where 10$ coupon hasn’t been redeemed and want to fetch $10 and 20% coupons and status as E and benefit eligibility date as 30 days and expire date as 30 days
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158410?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158410,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"Y\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"N\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"E\",\n" +
                        "    \"planType\":\"M\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "   \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am a carepass member who is an employee and enrolled under monthly program and want to fetch only $10 coupon and status as E and benefit eligibility date as 30 days and expire date as 30 days
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C900058411?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 900058411,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(1)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"Y\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"E\",\n" +
                        "    \"planType\":\"M\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(1)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(1)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(1)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(1)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"Y\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am a carepass member enrolled under yearly program where 10$ coupon hasn’t been redeemed and want to fetch $10 and 20% coupons and status as E and benefit eligibility date as 30 days and expire date as 365 days
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158412?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158412,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(30)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"Y\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"N\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(30)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"E\",\n" +
                        "    \"planType\":\"Y\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(29)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(29)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(364)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(30)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an existing CarePass member and initiated cancellation of my monthly carepass membership today where I haven’t redeemed my 10$ coupon and want to fetch 10$ and 20% coupons and status as CI and benefit_eligibility_date as future date
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158413?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158413,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"Y\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"N\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"CI\",\n" +
                        "    \"planType\":\"M\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an existing CarePass member and initiated cancellation of my yearly carepass membership today where I have redeemed my 10$ coupon and want to fetch 20% coupon and status as CI and benefit_eligibility_date as future date
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158414?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158414,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"Y\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"N\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"N\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"CI\",\n" +
                        "    \"planType\":\"Y\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(40)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(40)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(40)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(10)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"N\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an CarePass member and cancelled my monthly carepass membership 5 day ago and want to fetch the status as U and benefit_eligibility_date as past date and redeemable Ind as N
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158415?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158415,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"N\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"N\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"N\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"U\",\n" +
                        "    \"planType\":\"M\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"N\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an CarePass member and cancelled my yearly carepass membership 5 days ago and want to fetch the status as U and benefit_eligibility_date as past date and redeemable Ind as N
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158416?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158416,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"N\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"N\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"N\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"U\",\n" +
                        "    \"planType\":\"Y\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-340)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-340)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(-5)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"N\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with Hold status under monthly membership program and want to fetch $10 and 20% coupons with expirDt as today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158417?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158417,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"Y\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"Y\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"Y\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"H\",\n" +
                        "    \"planType\":\"M\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"Y\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am a CarePass member with Hold status under yearly membership program and want to fetch $10 and 20% coupons with expirDt as today
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158418?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158418,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"Y\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"Y\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"Y\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"H\",\n" +
                        "    \"planType\":\"Y\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(0)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"Y\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am a CarePass member and switch to yearly program and want to see the expire date didn't changed to 365 days but plan type changed to Y
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158419?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "       \"cusInfResp\": {\n" +
                        "       \"xtraCard\": {\n" +
                        "       \"xtraCardNbr\": 98158419,\n" +
                        "       \"totYtdSaveAmt\": \"0.00\",\n" +
                        "       \"cardMbrDt\": \"2020-11-19 00:00:00.0\"\n" +
                        "   },\n" +
                        "   \"cpns\": [\n" +
                        "   {\n" +
                        "   \"cmpgnId\":41083,\n" +
                        "   \"cpnNbr\":61622,\n" +
                        "   \"cpnSeqNbr\":10000000708864,\n" +
                        "   \"maxRedeemAmt\":\"99.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(26)).toString().substring(0, 10) + "\",\n" +
                        "   \"cmpgnTypeCd\":\"C\",\n" +
                        "   \"cmpgnSubtypeCd\":\"H\",\n" +
                        "   \"mktgPrgCd\":\"C\",\n" +
                        "   \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "   \"cpnDsc\":\"CarePass_20% Off\",\n" +
                        "   \"cpnTermsCd\":\"1\",\n" +
                        "   \"amtTypeCd\":\"P\",\n" +
                        "   \"pctOffAmt\":20.0,\n" +
                        "   \"loadActlDt\":\"2020-12-09\",\n" +
                        "   \"newCpnInd\":\"Y\",\n" +
                        "   \"cpnSuppressCd\":\"N\",\n" +
                        "   \"viewableInd\":\"Y\",\n" +
                        "   \"printableInd\":\"N\",\n" +
                        "   \"loadableInd\":\"N\",\n" +
                        "   \"redeemableInd\":\"Y\",\n" +
                        "   \"cpnGroupingCd\":\"O\",\n" +
                        "   \"everWebRedeemableInd\":\"Y\",\n" +
                        "   \"homeStoreNbr\":590,\n" +
                        "   \"cpnFmtCd\":\"3\",\n" +
                        "   \"prodCpnInd\":\"Y\",\n" +
                        "   \"appOnlyInd\":\"N\",\n" +
                        "   \"itemLimitQty\":0,\n" +
                        "   \"cpnValRqrdCd\":\"Y\",\n" +
                        "   \"absAmtInd\":\"N\",\n" +
                        "   \"expSoonInd\":\"N\",\n" +
                        "   \"cpnRecptTxt\":\"CarePass_20% Off\",\n" +
                        "   \"fndgCd\":\"6\",\n" +
                        "   \"mfrInd\":\"N\",\n" +
                        "   \"autoReissueInd\":\"Y\",\n" +
                        "   \"cpnCats\": [\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":1,\n" +
                        "   \"cpnCatDsc\":\"Baby and Child\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":2,\n" +
                        "   \"cpnCatDsc\":\"Beauty\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":3,\n" +
                        "   \"cpnCatDsc\":\"Grocery\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":4,\n" +
                        "   \"cpnCatDsc\":\"Health and Medicine\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "   \"cpnCatNbr\":5,\n" +
                        "   \"cpnCatDsc\":\"Household\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":6,\n" +
                        "    \"cpnCatDsc\":\"Personal Care\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":7,\n" +
                        "    \"cpnCatDsc\":\"Cold and Allergy\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":8,\n" +
                        "    \"cpnCatDsc\":\"Diet, Nutrition and Fitness\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cpnCatNbr\":12,\n" +
                        "    \"cpnCatDsc\":\"Vitamins and Supplements\"\n" +
                        "    }\n" +
                        "    ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "   \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(26)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnCreateDt\":\"2020-11-19\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"cpnSuppressCd\":\"N\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"cpnGroupingCd\":\"O\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"homeStoreNbr\":590,\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\",\n" +
                        "    \"mfrInd\":\"N\",\n" +
                        "    \"autoReissueInd\":\"N\"\n" +
                        "    }\n" +
                        "    ],\n" +
                        "    \"xtraCarePrefs\":\n" +
                        "    {\n" +
                        "    \"carePass\":{\n" +
                        "    \"statusCd\":\"E\",\n" +
                        "    \"planType\":\"Y\",\n" +
                        "    \"enrollDt\":\"2020-11-19\",\n" +
                        "    \"nextRewardIssueDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(26)).toString().substring(0, 10) + "\",\n" +
                        "    \"benefitEligibilityDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(26)).toString().substring(0, 10) + "\",\n" +
                        "    \"expiryDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(26)).toString().substring(0, 10) + "\",\n" +
                        "    \"statusDt\":\"2020-11-19\"\n" +
                        "    }\n" +
                        "    },\n" +
                        "    \"carePassCpns\":[\n" +
                        "    {\n" +
                        "    \"cmpgnId\":40666,\n" +
                        "    \"cpnNbr\":59121,\n" +
                        "    \"cpnSeqNbr\":10000000708863,\n" +
                        "    \"maxRedeemAmt\":\"10.00\",\n" +
                        "    \"expirDt\":\"" + (carePassDateUtil.carePassRedeemEndTswtz(26)).toString().substring(0, 10) + "\",\n" +
                        "    \"cmpgnTypeCd\":\"E\",\n" +
                        "    \"cmpgnSubtypeCd\":\"M\",\n" +
                        "    \"mktgPrgCd\":\"C\",\n" +
                        "    \"cpnDsc\":\"CarePass $10 reward.\",\n" +
                        "    \"webDsc\":\"CarePass $10 reward\",\n" +
                        "    \"cpnTermsCd\":\"1\",\n" +
                        "    \"amtTypeCd\":\"D\",\n" +
                        "    \"loadActlDt\":\"2020-12-09\",\n" +
                        "    \"newCpnInd\":\"Y\",\n" +
                        "    \"viewableInd\":\"Y\",\n" +
                        "    \"printableInd\":\"N\",\n" +
                        "    \"loadableInd\":\"N\",\n" +
                        "    \"redeemableInd\":\"Y\",\n" +
                        "    \"everWebRedeemableInd\":\"Y\",\n" +
                        "    \"cpnFmtCd\":\"2\",\n" +
                        "    \"prodCpnInd\":\"N\",\n" +
                        "    \"appOnlyInd\":\"N\",\n" +
                        "    \"itemLimitQty\":0,\n" +
                        "    \"cpnValRqrdCd\":\"N\",\n" +
                        "    \"expSoonInd\":\"N\",\n" +
                        "    \"cpnQualTxt\":\"You have not redeemed this month's CarePass $10 promotional reward\",\n" +
                        "    \"cpnRecptTxt\":\"CarePass $10 reward\",\n" +
                        "    \"fndgCd\":\"6\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "}").withStatus(200)));


        //I am an CarePass member and cancelled my yearly carepass membership 5 days ago and want to fetch the status as U and benefit_eligibility_date as past date and no 10$ and 20% coupons
        //TODO - Stub Data has to be modified
        stubFor(post("/api/v1.1/customers/0002%2C98158420?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\":\"5\",\n" +
                        "    \"errorMsg\": \"HOT XC Card\"\n" +
                        "}").withStatus(400)));


    }

    /**
     * Delete Stub data for GetCustCarepassEnrollment
     */
    public void deleteGetCustCarepassEnrollmentStubData() {
        wireMockServer.stop();
    }

}