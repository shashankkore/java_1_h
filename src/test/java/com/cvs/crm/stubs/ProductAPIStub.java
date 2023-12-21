package com.cvs.crm.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@Component
@Slf4j
public class ProductAPIStub {

    private WireMockServer wireMockServer = new WireMockServer();

    /**
     * Create Stub data for Product API
     */
    public void createProductApiStubData() {
        wireMockServer.start();

        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548,\n" +
                        "872080\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd is G (General)
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd parameter is missing
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&src_loc_cd=2695&user_id=CVS.COM")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548,\n" +
                        "872080\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with different Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13993/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with different Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13993/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125507,\n" +
                        "420928,\n" +
                        "872079\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the different healthcare products are present with same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C14618/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "125507,\n" +
                        "999835,\n" +
                        "420406,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "927548\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when only General products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C14608/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when no products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900990061/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant general products when no products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900990061/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = x
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=x")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = h
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=h")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = !
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=!")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));


        //As a hot Card member, I want to see error message when I try to retrieve healthcare and personal care products
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C59000000/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 4,\n" +
                        "    \"errorMsg\": \"Card Not on File\"\n" +
                        "}").withStatus(400)));

        //As a Walmart Card member, I want to see error message when I try to retrieve healthcare and personal care products
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058650/skus?msg_src_cd=W&user_id=CVS.COM&src_loc_cd=2695&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 5,\n" +
                        "    \"errorMsg\": \"HOT XC Card\"\n" +
                        "}").withStatus(400)));



        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548,\n" +
                        "872080\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd is G (General)
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd parameter is missing
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548,\n" +
                        "872080\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with different Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13993/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with different Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13993/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125507,\n" +
                        "420928,\n" +
                        "872079\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the different healthcare products are present with same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C14618/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "125507,\n" +
                        "999835,\n" +
                        "420406,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "927548\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when only General products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C14608/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when no products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900990061/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant general products when no products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900990061/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = x
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=x")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = h
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=h")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = !
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=!")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));


        //As a hot Card member, I want to see error message when I try to retrieve healthcare and personal care products
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C59000000/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 4,\n" +
                        "    \"errorMsg\": \"Card Not on File\"\n" +
                        "}").withStatus(400)));

        //As a Walmart Card member, I want to see error message when I try to retrieve healthcare and personal care products
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058650/skus?msg_src_cd=M&user_id=MOBILE_ENT&src_loc_cd=90042&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 5,\n" +
                        "    \"errorMsg\": \"HOT XC Card\"\n" +
                        "}").withStatus(400)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548,\n" +
                        "872080\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd is G (General)
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd parameter is missing
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548,\n" +
                        "872080\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with different Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13993/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "999835,\n" +
                        "420928,\n" +
                        "125507,\n" +
                        "420406,\n" +
                        "704443,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "872079,\n" +
                        "927548\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with different Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13993/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125507,\n" +
                        "420928,\n" +
                        "872079\n" +
                        "]\n" +
                        "}").withStatus(200)));

        //As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "870284,\n" +
                        "797747,\n" +
                        "927548,\n" +
                        "420406,\n" +
                        "999835,\n" +
                        "704443,\n" +
                        "125500,\n" +
                        "420928,\n" +
                        "872079,\n" +
                        "870222\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the different healthcare products are present with same Rank
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C14618/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "125507,\n" +
                        "999835,\n" +
                        "420406,\n" +
                        "797747,\n" +
                        "870284,\n" +
                        "927548\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when only General products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C14608/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when no products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900990061/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant general products when no products are present
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900990061/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=G")
                .willReturn(okJson("{\n" +
                        " \"skus\": [\n" +
                        "]\n" +
                        "}").withStatus(200)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = x
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=x")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));


        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = h
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=h")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));

        //As a Rx.COM ExtraCare Customers I want to see error message when I try to retrieve healthcare and personal care products with product_type_cd = !
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C13925/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=!")
                .willReturn(okJson("{\n" +
                        " \"errorCd\":1,\n" +
                        " \"errorMsg\":\"Invalid Product type code: \" \n" +
                        "}").withStatus(400)));

        //As a hot Card member, I want to see error message when I try to retrieve healthcare and personal care products
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C59000000/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 4,\n" +
                        "    \"errorMsg\": \"Card Not on File\"\n" +
                        "}").withStatus(400)));

        //As a Walmart Card member, I want to see error message when I try to retrieve healthcare and personal care products
        //TODO - Stub Data has to be modified
        stubFor(get("/api/v1.1/customers/0002%2C900058650/skus?msg_src_cd=GB&user_id=GBI&src_loc_cd=90046&product_type_cd=H")
                .willReturn(okJson("{\n" +
                        "    \"errorCd\": 5,\n" +
                        "    \"errorMsg\": \"HOT XC Card\"\n" +
                        "}").withStatus(400)));
    }

    /**
     * Delete Stub data for Beauty Club
     */
    public void deleteProductApiStubData() {
        wireMockServer.stop();
    }

}

