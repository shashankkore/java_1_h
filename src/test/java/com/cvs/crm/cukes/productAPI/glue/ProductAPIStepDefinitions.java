package com.cvs.crm.cukes.productAPI.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.ProductRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.ProductResponse;
import com.cvs.crm.service.ProductAPIService;
import com.cvs.crm.util.DateUtil;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class ProductAPIStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    ProductAPIService productAPIService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    public ProductAPIStepDefinitions() {
        {
            ProductRequest request = new ProductRequest();

            Given("{string}", (String scenario) -> {
            });


            Given("I use {string}", (String channel) -> {
                request.setChannel(channel);
            });

            Given("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });
            
            Given("I use Sku Number {int}", (Integer skuNbr) -> {
                request.setSkuNumber(skuNbr);
            });
            
            Given("I use search card type as {string}", (String searchCardType) -> {
                request.setSearchCardType(searchCardType);
            });

            Given("product_type_cd as {string}", (String productTypeCode) -> {
             request.setProductTypeCd(productTypeCode);
            });
            
            Given("Recommendation Type as {string}", (String productTypeCode) -> {
                request.setProductTypeCd(productTypeCode);
               });


            When("I want to view Recommended SKUs", () -> {
                productAPIService.viewProductAPI(request);
            });
            
            When("I want to view {string} SKUs", (String recommendationType) -> {
                productAPIService.viewProductAPIPost(request, recommendationType);
            });
            
            When("I want to post {string} SKU", (String products) -> {
                productAPIService.viewRecentlyViewedProductsAPI(request, products);
            });

            Then("I see relevant healthcare and personal care products", () -> {
                productAPIService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            Then("I see relevant products", () -> {
                productAPIService.getServiceResponse().then().log().all().statusCode(200);
            });
            
            Then("I see relevant status code", () -> {
                productAPIService.getServiceResponse().then().log().all().statusCode(201);
            });

            Then("I see my products as", (DataTable dt) -> {
                List<Integer> expectedlist = dt.asList(Integer.class);
                try {
                    ProductResponse productResponse = productAPIService.getServiceResponse().as(ProductResponse.class);
                    List<Integer> Skus = productResponse.getSkus();
//                    for (int i = 0; i < Skus.size(); i++) {
                    for (int i = 0; i < expectedlist.size(); i++) {
//                        Assert.assertEquals(expectedlist.get(i-1), Skus.get(i-1));
                        Assert.assertEquals(expectedlist.get(i), Skus.get(i));
//                        System.out.println("expected is " + expectedlist.get(i));
//                        System.out.println("actual is " + Skus.get(i));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    log.info("The index you have entered is invalid");
                }
            });
            
            Then("I see my {string} products as", (String recommendationType, DataTable dt) -> {
                List<Integer> expectedlist = dt.asList(Integer.class);
                try {
                	if (recommendationType.equals("buyItAgain")) {
                    for (int i = 0; i < expectedlist.size(); i++) {
                        Assert.assertEquals(expectedlist.get(i), productAPIService.getServiceResponse().jsonPath().get("buyItAgainSkus["+i+"]"));
                    }
                	} else if (recommendationType.equals("recentlyViewed")) {
                        for (int i = 0; i < expectedlist.size(); i++) {
                            Assert.assertEquals(expectedlist.get(i), productAPIService.getServiceResponse().jsonPath().get("recentlyViewedSkus["+i+"]"));
                    }
                    } else if (recommendationType.equals("frequentlyBoughtTogether")) {
                        for (int i = 0; i < expectedlist.size(); i++) {
                            Assert.assertEquals(expectedlist.get(i), productAPIService.getServiceResponse().jsonPath().get("frequentlyBoughtTogetherSkus["+i+"]"));
                    }
                    } else if (recommendationType.equals("couponRecommendations")) {
                        for (int i = 0; i < expectedlist.size(); i++) {
                            Assert.assertEquals(expectedlist.get(i), productAPIService.getServiceResponse().jsonPath().get("couponRecommendations["+i+"]"));
                    }
                    }
                	if (recommendationType.equals("affinity")) {
                        for (int i = 0; i < expectedlist.size(); i++) {
                            Assert.assertEquals(expectedlist.get(i), productAPIService.getServiceResponse().jsonPath().get("affinitySkus["+i+"]"));
                    }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    log.info("The index you have entered is invalid");
                }
            });


            Then("I see my products as empty", () -> {
                ProductResponse productResponse = productAPIService.getServiceResponse().as(ProductResponse.class);
                List<Integer> Skus = productResponse.getSkus();
                    Assert.assertEquals(0, Skus.size());
            });
            
            Then("I see no products in response", () -> {
            	HashMap<String,Integer> Skus = productAPIService.getServiceResponse().jsonPath().get();
//                System.out.println("skus is " + Skus);
                    Assert.assertEquals(0, Skus.size());
            });

            Then("I don't see my products", () -> {
                productAPIService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see my Error Code as {int}", (Integer errorCd) -> {

                ApiErrorResponse apiErrorResponse = productAPIService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });

            Then("I see Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = productAPIService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });
        }
    }
}