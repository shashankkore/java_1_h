package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.response.ProductResponse;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DataSetupUtil;
import com.cvs.crm.model.data.CampaignCouponCriteria;
import com.cvs.crm.model.data.CampaignCouponSkuRank;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.FrequentlyBoughtTogetherSku;
import com.cvs.crm.model.data.StoreSkuRank;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.data.XtraHotCard;
import com.cvs.crm.model.data.XtraCardRcmddSkus;
import com.cvs.crm.model.data.XtraCardRecentlyViewedSku;
import com.cvs.crm.model.data.XtraCardSegment;
import com.cvs.crm.model.data.XtraCardSkuRank;
import com.cvs.crm.model.request.ProductRequest;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.sql.Types;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Data
public class ProductAPIService {

    private Response serviceResponse;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    DataSetupUtil dataSetupUtil;

    @Autowired
    Customer customer;

    @Autowired
    CustomerAddress customerAddress;

    @Autowired
    CustomerEmail customerEmail;

    @Autowired
    CustomerPhone customerPhone;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    XtraCard xtraCard;
    
    @Autowired
    XtraCardSegment xtraCardSegment;

    @Autowired
    XtraParms xtraParms;

    @Autowired
    XtraHotCard xtraHotCard;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;
    
    @Autowired
    CampaignDao campaignDao;
    
    @Autowired
    XtraCardSkuRank xtraCardSkuRank;
    
    @Autowired
    XtraCardRecentlyViewedSku xtraCardRecentlyViewedSku;
    
    @Autowired
    FrequentlyBoughtTogetherSku frequentlyBoughtTogetherSku;
    
    @Autowired
    StoreSkuRank storeSkuRank;
    
    @Autowired
    CampaignCouponCriteria campaignCouponCriteria;
    
    @Autowired
    CampaignCouponSkuRank campaignCouponSkuRank;


    public void viewProductAPI(ProductRequest productRequest) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
        String productTypeCd = productRequest.getProductTypeCd();

        //TODO - We need a Utility Method to determine attributes
        if ("MOBILE".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else  if ("WEB".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else {
            msgSrcCd = "GB";
            userId = "GBI";
            srcLocCd = 90046;
        }

        requestSpecBuilder.setBaseUri(serviceConfig.getProductapiUrl())
                .setBasePath("api/v1.2/customers/{search_card_type},{search_card_nbr}/skus")
                .addPathParam("search_card_type", productRequest.getSearchCardType())
                .addPathParam("search_card_nbr", productRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("product_type_cd", productRequest.getProductTypeCd());

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).when().get();
    }
    
    public void viewProductAPIPost(ProductRequest productRequest, String recommendationType) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
        String jsonString = null;
//        String productTypeCd = productRequest.getProductTypeCd();

        //TODO - We need a Utility Method to determine attributes
        if ("MOBILE".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else  if ("WEB".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else  if ("GB".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "GB";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else {
            msgSrcCd = "Z";
            userId = "CVS.COM";
            srcLocCd = 2695;
        }
        
        if (recommendationType.equals("buyItAgain")) {
        		if (productRequest.getProductTypeCd().equals("buyItAgain")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("buyItAgainInvalidXCCard")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"abc\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("buyItAgainBlankXCCard")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("buyItAgainNoFilter")) {
                    jsonString = "{\r\n" +
                            "\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
                            "\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
                            "\"recommendations\": [{\r\n" +
                            "\"recommendationType\":  \"buyItAgain\"\r\n" +
                            "}]\r\n" +
                            "}";
                }
        } else if (recommendationType.equals("recentlyViewed")) {
        		if (productRequest.getProductTypeCd().equals("recentlyViewed")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\"\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("recentlyViewedInvalidXC")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"abc\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\"\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("recentlyViewedBlankXC")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\"\r\n" +
        					"}]\r\n" +
        					"}";
        		} 
        } else if (recommendationType.equals("frequentlyBoughtTogether")) {
        		if (productRequest.getProductTypeCd().equals("frequentlyBoughtTogether")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"\"skuNumber\": \"" + productRequest.getSkuNumber() + "\"\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("frequentlyBoughtTogetherBlankSKU")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"\"skuNumber\":  \"\"\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("frequentlyBoughtTogetherinvalidSKU")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"\"skuNumber\":  \"abc\"\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("frequentlyBoughtTogetherBlankXcNbr")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"\"skuNumber\": \"" + productRequest.getSkuNumber() + "\"\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		} else if (productRequest.getProductTypeCd().equals("frequentlyBoughtTogetherInvalidXcNbr")) {
        			jsonString = "{\r\n" +
        					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
        					"\"cardNumber\": \"abc\",\r\n" +
        					"\"recommendations\": [{\r\n" +
        					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
        					"\"filters\": {\r\n" +
        					"\"skuNumber\": \"" + productRequest.getSkuNumber() + "\"\r\n" +
        					"}\r\n" +
        					"}]\r\n" +
        					"}";
        		}
        } else if (recommendationType.equals("affinity")) {
    		if (productRequest.getProductTypeCd().equals("affinity")) {
    			jsonString = "{\r\n" +
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"storeNumber\":  \"50\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("affinitymissingXC")) {
    			jsonString = "{\r\n" +
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"skuNumber\":  \"\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("affinityblankXC")) {
    			jsonString = "{\r\n" +
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \" \",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"storeNumber\":  \"50\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("affinityinvalidXC")) {
    			jsonString = "{\r\n" +
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \"ABC\",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"storeNumber\":  \"50\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("affinityHOTXCandNotPresent")) {
    			jsonString = "{\r\n" +
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"storeNumber\":  \"50\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("cardTypeMissing")) {
    			jsonString = "{\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"storeNumber\":  \"50\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("cardTypeBlank")) {
    			jsonString = "{\r\n" +
    					"\"cardType\": \" \",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"storeNumber\":  \"50\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("cardTypeInvalid")) {
    			jsonString = "{\r\n" +
    					"\"cardType\": \"ABC\",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
    					"\"recommendations\": [{\r\n" +
    					"\"recommendationType\": \"" + recommendationType + "\",\r\n" +
    					"\"filters\": {\r\n" +
    					"\"storeNumber\":  \"50\"\r\n" +
    					"}\r\n" +
    					"}]\r\n" +
    					"}";
    		}
        } else if (recommendationType.equals("topSelling")) {
    		if (productRequest.getProductTypeCd().equals("topSellingSingle")) {
    			jsonString = "{\r\n"
    					+ "  \"recommendations\": [\r\n"
    					+ "     {\r\n"
    					+ "     \"recommendationType\": \"topSelling\",\r\n      "
    					+ "      \"filters\": {\r\n        "
    					+ "        \"storeNumber\": 5610\r\n       "
    					+ "     }\r\n    "
    					+ "    }\r\n    ]\r\n}";
    		} else if (productRequest.getProductTypeCd().equals("topSellingMultiple")) {
    			jsonString = "{\r\n"
    					+ "  \"recommendations\": [\r\n"
    					+ "     {\r\n"
    					+ "     \"recommendationType\": \"topSelling\",\r\n      "
    					+ "      \"filters\": {\r\n        "
    					+ "        \"storeNumber\": 5620\r\n       "
    					+ "     }\r\n    "
    					+ "    }\r\n    ]\r\n}";
    		} else if (productRequest.getProductTypeCd().equals("topSellingnoSKU")) {
    			jsonString = "{\r\n"
    					+ "  \"recommendations\": [\r\n"
    					+ "     {\r\n"
    					+ "     \"recommendationType\": \"topSelling\",\r\n      "
    					+ "      \"filters\": {\r\n        "
    					+ "        \"storeNumber\": 5630\r\n       "
    					+ "     }\r\n    "
    					+ "    }\r\n    ]\r\n}";
    		} else if (productRequest.getProductTypeCd().equals("topSellingBlankStoreNbr")) {
    			jsonString = "{\r\n"
    					+ "  \"recommendations\": [\r\n"
    					+ "     {\r\n"
    					+ "     \"recommendationType\": \"topSelling\",\r\n      "
    					+ "      \"filters\": {\r\n        "
    					+ "        \"storeNumber\": \"\"\r\n       "
    					+ "     }\r\n    "
    					+ "    }\r\n    ]\r\n}";
    		}
        } else if (recommendationType.equals("couponRecommendations")) {
        	if (productRequest.getProductTypeCd().equals("validating")) {
    			jsonString = "{\r\n" + 
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" + 
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"campaignId\": 35412,\r\n" + 
    					"                \"couponNumber\": 148468,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("nonExistingXCCard")) {
    			jsonString = "{\r\n" + 
    					"    \"cardNumber\": \"21212121\",\r\n" + 
    					"    \"cardType\": \"0002\",\r\n" + 
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"campaignId\": 40666,\r\n" + 
    					"                \"couponNumber\": 59121,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("invalidXC")) {
    			jsonString = "{\r\n" + 
    					"    \"cardNumber\": \"s2233oisajcnss2s2ohnsu2sn2uoynsu2yhn\",\r\n" + 
    					"    \"cardType\": \"0002\",\r\n" + 
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"campaignId\": 40666,\r\n" + 
    					"                \"couponNumber\": 59121,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("hotXC")) {
    			jsonString = "{\r\n" + 
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" + 
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"campaignId\": 40666,\r\n" + 
    					"                \"couponNumber\": 59121,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("invalidCardType")) {
    			jsonString = "{\r\n" + 
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" + 
    					"    \"cardType\": \"0006\",\r\n" + 
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"campaignId\": 40666,\r\n" + 
    					"                \"couponNumber\": 59121,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("blankXtraCard")) {
    			jsonString = "{\r\n" + 
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"campaignId\": 40666,\r\n" + 
    					"                \"couponNumber\": 59121,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("blankCoupon")) {
    			jsonString = "{\r\n" + 
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" + 
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"campaignId\": 40666,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		} else if (productRequest.getProductTypeCd().equals("blankCampaign")) {
    			jsonString = "{\r\n" + 
    					"\"cardType\": \"" + productRequest.getSearchCardType() + "\",\r\n" +
    					"\"cardNumber\": \"" + productRequest.getSearchCardNbr() + "\",\r\n" +
    					"    \"recommendations\": [\r\n" + 
    					"        {\r\n" + 
    					"            \"recommendationType\": \"coupon\",\r\n" + 
    					"            \"filters\": {\r\n" + 
    					"                \"couponNumber\": 59121,\r\n" + 
    					"                \"storeNumber\": 590\r\n" + 
    					"            }\r\n" + 
    					"        }\r\n" + 
    					"    ]\r\n" + 
    					"}";
    		}
    }
        
        System.out.println("Json is "+ jsonString);
        requestSpecBuilder.setBaseUri(serviceConfig.getProductapiUrl())
                .setBasePath("api/v1.1/products/recommendations/search")
//                .addPathParam("search_card_type", productRequest.getSearchCardType())
//                .addPathParam("search_card_nbr", productRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
//                .addQueryParam("product_type_cd", productRequest.getProductTypeCd());

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
    }
    
    public void viewRecentlyViewedProductsAPI(ProductRequest productRequest, String products) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;
        String jsonString = null;
        
//        Date date = new Date();
        DateTime dateTime = new DateTime();
	    String patternTs = "yyyy-MM-dd HH.MM.SS a";
	    SimpleDateFormat simpleDateFormatTs = new SimpleDateFormat(patternTs);
	    String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyyMMdd hh:mm:ss");
	    String TimeStampminus2 = dateTime.minusDays(2).toString("yyyyMMdd hh:mm:ss");
        String TimeStampminus3 = dateTime.minusDays(3).toString("yyyyMMdd hh:mm:ss");


        if ("MOBILE".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else  if ("WEB".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else  if ("GB".equalsIgnoreCase(productRequest.getChannel())) {
            msgSrcCd = "GB";
            userId = "CVS.COM";
            srcLocCd = 2695;
        } else {
            msgSrcCd = "Z";
            userId = "CVS.COM";
            srcLocCd = 2695;
        }
        
        if (products.equals("single")) {
        	jsonString = "[\r\n" +
        			"{\r\n" +
        			"\"skuNumber\": \"12361\",\r\n" +
        			"\"dateViewed\": \"" + yestardayTimeStamp + "\"\r\n" +
        			"}]";
        } else if (products.equals("multiple")) {
        	jsonString = "[\r\n  "
    				+ "  {\r\n  "
    				+ "  \"skuNumber\": 12362,\r\n   "
    				+ "  \"dateViewed\": \"" + yestardayTimeStamp + "\"\r\n"
    				+ "  },\r\n  "
    				+ "  {\r\n   "
    				+ "  \"skuNumber\": 12363,\r\n     "
    				+ "  \"dateViewed\": \"" + TimeStampminus2 + "\"\r\n"
    				+ "  },\r\n   "
    				+ "  {\r\n      "
    				+ "  \"skuNumber\": 12364,\r\n    "
    				+ "  \"dateViewed\": \"" + TimeStampminus3 + "\"\r\n"
    				+ "  }\r\n]";
        } else if (products.equals("blankdateViewed")) {
        	jsonString = "[\r\n" +
        			"{\r\n" +
        			"\"skuNumber\": \"12361\"\r\n" +
        			"}]";
        } else if (products.equals("blankSKUNumber")) {
        	jsonString = "[\r\n" +
        			"{\r\n" +
        			"\"dateViewed\": \"" + yestardayTimeStamp + "\"\r\n" +
        			"}]";
        }
        
        System.out.println("Json is "+ jsonString);
        requestSpecBuilder.setBaseUri(serviceConfig.getProductapiUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}/products/recentlyViewed")
                .addPathParam("search_card_type", productRequest.getSearchCardType())
                .addPathParam("search_card_nbr", productRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);
//                .addQueryParam("product_type_cd", productRequest.getProductTypeCd());

        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).contentType("application/json").body(jsonString).post();
    }


    /**
     * Create Test Data For Product API Scenarios
     * @throws InterruptedException 
     */
    public void createProductAPITestData() throws ParseException, InterruptedException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = new Date();
        String dateCurrent = simpleDateFormat.format(date);

        String patternTs = "yyyy-MM-dd HH.MM.SS a";
        SimpleDateFormat simpleDateFormatTs = new SimpleDateFormat(patternTs);

        DateTime dateTime = new DateTime();
        String todayDate = dateTime.toString("yyyy-MM-dd");
        String yestarday2Date = dateTime.minusDays(2).toString("yyyy-MM-dd");
        String yestardayDate = dateTime.minusDays(1).toString("yyyy-MM-dd");
        String tomorrowDate = dateTime.plusDays(1).toString("yyyy-MM-dd");
        String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
        String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");

        String todayTimeStamp = dateTime.toString("yyyy-MM-dd HH.MM.SS a");
        String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String tomorrowTimeStamp = dateTime.plusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
        String prev1yearTimeStamp = dateTime.minusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
        String future1yearTimeStamp = dateTime.plusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
        
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd is G (General)
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when product_type_cd parameter is missing
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the same product is present in HealthCare SKU and General SKU columns with different Rank
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with different Rank
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant general products when the same product is present in HealthCare SKU and General SKU columns with Same Rank
        */
        
       xtraCard.setXtraCardNbr(98168319);
       xtraCard.setCustId(80711);
       xtraCard.setTotYtdSaveAmt(0.00);
       xtraCard.setTotLifetimeSaveAmt(0.00);
       xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
       xtraCard.setMktgTypeCd(" ");
       xtraCardDao.createXtraCard(xtraCard);
       
       xtraCard.setEncodedXtraCardNbr(98168319);
       xtraCardDao.updateXtraCard(xtraCard);

       customer.setCustId(80711);
       customer.setGndrCd("M");
       customer.setFirstName("John");
       customer.setLastName("Doe");
       customerDao.createCustomer(customer);

       customerEmail.setCustId(80711);
       customerEmail.setEmailAddrTypeCd("H");
       customerEmail.setEmailPrefSeqNbr(1);
       customerEmail.setEmailAddrTxt("abc@CVS.com");
       customerEmail.setEmailStatusCd("A");
       customerDao.createCustomerEmail(customerEmail);

       customerPhone.setCustId(80711);
       customerPhone.setPhoneTypeCd("H");
       customerPhone.setPhonePrefSeqNbr(1);
       customerPhone.setPhoneAreaCdNbr(123);
       customerPhone.setPhonePfxNbr(456);
       customerPhone.setPhoneSfxNbr(7890);
       customerDao.createCustomerPhone(customerPhone);

       customerAddress.setCustId(80711);
       customerAddress.setAddrTypeCd("H");
       customerAddress.setAddrPrefSeqNbr(1);
       customerAddress.setAddr1Txt("951 YORK RD #106");
       customerAddress.setAddr2Txt(" ");
       customerAddress.setCityName("PARMA HTS");
       customerAddress.setStCd("OH");
       customerAddress.setZipCd("44130");
       customerDao.createCustomerAddress(customerAddress);

       xtraCardSegment.setXtraCardNbr(98168319);
       xtraCardSegment.setXtraCardSegNbr(337);
       xtraCardSegment.setCtlGrpCd(null);
       xtraCardDao.createXtraCardSegment(xtraCardSegment);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999835);
       xtraCardSkuRank.setSkuRankNbr(1);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999836);
       xtraCardSkuRank.setSkuRankNbr(2);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999837);
       xtraCardSkuRank.setSkuRankNbr(3);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999838);
       xtraCardSkuRank.setSkuRankNbr(4);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999839);
       xtraCardSkuRank.setSkuRankNbr(5);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999840);
       xtraCardSkuRank.setSkuRankNbr(6);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999841);
       xtraCardSkuRank.setSkuRankNbr(7);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999842);
       xtraCardSkuRank.setSkuRankNbr(8);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999843);
       xtraCardSkuRank.setSkuRankNbr(9);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("H");
       xtraCardSkuRank.setSkuNbr(999844);
       xtraCardSkuRank.setSkuRankNbr(10);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889835);
       xtraCardSkuRank.setSkuRankNbr(1);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(999836);
       xtraCardSkuRank.setSkuRankNbr(2);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889837);
       xtraCardSkuRank.setSkuRankNbr(3);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889838);
       xtraCardSkuRank.setSkuRankNbr(4);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(999837);
       xtraCardSkuRank.setSkuRankNbr(5);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889840);
       xtraCardSkuRank.setSkuRankNbr(6);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889841);
       xtraCardSkuRank.setSkuRankNbr(7);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889842);
       xtraCardSkuRank.setSkuRankNbr(8);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889843);
       xtraCardSkuRank.setSkuRankNbr(9);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       xtraCardSkuRank.setXtraCardNbr(98168319);
       xtraCardSkuRank.setRankTypeCode("G");
       xtraCardSkuRank.setSkuNbr(889844);
       xtraCardSkuRank.setSkuRankNbr(10);
       xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
       
       /*
        * As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when the different healthcare products are present with same Rank
       */
       
      xtraCard.setXtraCardNbr(98168320);
      xtraCard.setCustId(80712);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(98168320);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80712);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80712);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80712);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80712);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(98168320);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCardSkuRank.setXtraCardNbr(98168320);
      xtraCardSkuRank.setRankTypeCode("H");
      xtraCardSkuRank.setSkuNbr(999845);
      xtraCardSkuRank.setSkuRankNbr(1);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168320);
      xtraCardSkuRank.setRankTypeCode("H");
      xtraCardSkuRank.setSkuNbr(999846);
      xtraCardSkuRank.setSkuRankNbr(1);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168320);
      xtraCardSkuRank.setRankTypeCode("H");
      xtraCardSkuRank.setSkuNbr(999847);
      xtraCardSkuRank.setSkuRankNbr(1);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168320);
      xtraCardSkuRank.setRankTypeCode("H");
      xtraCardSkuRank.setSkuNbr(999848);
      xtraCardSkuRank.setSkuRankNbr(2);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168320);
      xtraCardSkuRank.setRankTypeCode("H");
      xtraCardSkuRank.setSkuNbr(999849);
      xtraCardSkuRank.setSkuRankNbr(2);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
      
      /*
       * As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when only General products are present
      */
      
      xtraCard.setXtraCardNbr(98168321);
      xtraCard.setCustId(80713);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(98168321);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80713);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80713);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80713);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80713);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(98168321);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);
      
      xtraCardSkuRank.setXtraCardNbr(98168321);
      xtraCardSkuRank.setRankTypeCode("G");
      xtraCardSkuRank.setSkuNbr(889850);
      xtraCardSkuRank.setSkuRankNbr(1);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168321);
      xtraCardSkuRank.setRankTypeCode("G");
      xtraCardSkuRank.setSkuNbr(889851);
      xtraCardSkuRank.setSkuRankNbr(2);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168321);
      xtraCardSkuRank.setRankTypeCode("G");
      xtraCardSkuRank.setSkuNbr(889852);
      xtraCardSkuRank.setSkuRankNbr(3);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168321);
      xtraCardSkuRank.setRankTypeCode("G");
      xtraCardSkuRank.setSkuNbr(889853);
      xtraCardSkuRank.setSkuRankNbr(4);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);

      xtraCardSkuRank.setXtraCardNbr(98168321);
      xtraCardSkuRank.setRankTypeCode("G");
      xtraCardSkuRank.setSkuNbr(889854);
      xtraCardSkuRank.setSkuRankNbr(5);
      xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
      
      /*
       * As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant healthcare and personal care products when no products are present
       * As a Rx.COM ExtraCare Customers I don’t want to retrieve relevant general products when no products are present
      */
      
      xtraCard.setXtraCardNbr(98168322);
      xtraCard.setCustId(80714);
      xtraCard.setTotYtdSaveAmt(0.00);
      xtraCard.setTotLifetimeSaveAmt(0.00);
      xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
      xtraCard.setMktgTypeCd(" ");
      xtraCardDao.createXtraCard(xtraCard);
      
      xtraCard.setEncodedXtraCardNbr(98168322);
      xtraCardDao.updateXtraCard(xtraCard);

      customer.setCustId(80714);
      customer.setGndrCd("M");
      customer.setFirstName("John");
      customer.setLastName("Doe");
      customerDao.createCustomer(customer);

      customerEmail.setCustId(80714);
      customerEmail.setEmailAddrTypeCd("H");
      customerEmail.setEmailPrefSeqNbr(1);
      customerEmail.setEmailAddrTxt("abc@CVS.com");
      customerEmail.setEmailStatusCd("A");
      customerDao.createCustomerEmail(customerEmail);

      customerPhone.setCustId(80714);
      customerPhone.setPhoneTypeCd("H");
      customerPhone.setPhonePrefSeqNbr(1);
      customerPhone.setPhoneAreaCdNbr(123);
      customerPhone.setPhonePfxNbr(456);
      customerPhone.setPhoneSfxNbr(7890);
      customerDao.createCustomerPhone(customerPhone);

      customerAddress.setCustId(80714);
      customerAddress.setAddrTypeCd("H");
      customerAddress.setAddrPrefSeqNbr(1);
      customerAddress.setAddr1Txt("951 YORK RD #106");
      customerAddress.setAddr2Txt(" ");
      customerAddress.setCityName("PARMA HTS");
      customerAddress.setStCd("OH");
      customerAddress.setZipCd("44130");
      customerDao.createCustomerAddress(customerAddress);

      xtraCardSegment.setXtraCardNbr(98168322);
      xtraCardSegment.setXtraCardSegNbr(337);
      xtraCardSegment.setCtlGrpCd(null);
      xtraCardDao.createXtraCardSegment(xtraCardSegment);





        /*  As a Rx.COM ExtraCare Customers I want to retrieve relevant healthcare and personal care products when product_type_cd is H (Healthy products)
         *
         */
        xtraHotCard.setXtraCardNbr(900058650);
        xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
        xtraCardDao.createXtraHotCard(xtraHotCard);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Buy It Again products when multiple products are present
        */
        
        xtraCard.setXtraCardNbr(98168323);
        xtraCard.setCustId(80715);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168323);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80715);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80715);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80715);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80715);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168323);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789830);
        xtraCardSkuRank.setSkuRankNbr(1);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789831);
        xtraCardSkuRank.setSkuRankNbr(2);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789832);
        xtraCardSkuRank.setSkuRankNbr(3);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789833);
        xtraCardSkuRank.setSkuRankNbr(4);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789834);
        xtraCardSkuRank.setSkuRankNbr(5);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789835);
        xtraCardSkuRank.setSkuRankNbr(6);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789836);
        xtraCardSkuRank.setSkuRankNbr(7);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789837);
        xtraCardSkuRank.setSkuRankNbr(8);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789838);
        xtraCardSkuRank.setSkuRankNbr(9);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168323);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789839);
        xtraCardSkuRank.setSkuRankNbr(10);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Buy It Again products when single product is present
        */
        
        xtraCard.setXtraCardNbr(98168324);
        xtraCard.setCustId(80716);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168324);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80716);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80716);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80716);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80716);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168324);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        xtraCardSkuRank.setXtraCardNbr(98168324);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(789840);
        xtraCardSkuRank.setSkuRankNbr(1);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Buy It Again products when no product is present
        */
        
        xtraCard.setXtraCardNbr(98168325);
        xtraCard.setCustId(80717);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168325);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80717);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80717);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80717);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80717);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168325);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Recently Viewed products when single product is present
        */
        
        xtraCard.setXtraCardNbr(98168326);
        xtraCard.setCustId(80718);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168326);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80718);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80718);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80718);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80718);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168326);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168326);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689840);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Recently Viewed products when multiple skus are present and max 20 are displayed
        */
        
        xtraCard.setXtraCardNbr(98168327);
        xtraCard.setCustId(80719);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168327);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80719);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80719);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80719);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80719);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168327);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689840);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689841);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689842);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689843);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689844);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689845);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689846);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689847);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689848);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689849);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689850);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689851);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689852);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689853);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689854);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689855);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689856);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689857);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689858);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689859);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168327);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(689860);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Frequently Bought Together skus when single product is present
        */
        
        frequentlyBoughtTogetherSku.setSkuNbr(105712);
        frequentlyBoughtTogetherSku.setAssociatedSkuNbr(123456);
        frequentlyBoughtTogetherSku.setSkuRankNbr(1);
        xtraCardDao.createFrequentlyBoughtTogetherSku(frequentlyBoughtTogetherSku);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Frequently Bought Together skus when multiple products are present
        */
        
        frequentlyBoughtTogetherSku.setSkuNbr(105711);
        frequentlyBoughtTogetherSku.setAssociatedSkuNbr(123451);
        frequentlyBoughtTogetherSku.setSkuRankNbr(1);
        xtraCardDao.createFrequentlyBoughtTogetherSku(frequentlyBoughtTogetherSku);
        
        frequentlyBoughtTogetherSku.setSkuNbr(105711);
        frequentlyBoughtTogetherSku.setAssociatedSkuNbr(123452);
        frequentlyBoughtTogetherSku.setSkuRankNbr(2);
        xtraCardDao.createFrequentlyBoughtTogetherSku(frequentlyBoughtTogetherSku);
        
        frequentlyBoughtTogetherSku.setSkuNbr(105711);
        frequentlyBoughtTogetherSku.setAssociatedSkuNbr(123453);
        frequentlyBoughtTogetherSku.setSkuRankNbr(3);
        xtraCardDao.createFrequentlyBoughtTogetherSku(frequentlyBoughtTogetherSku);
        
        frequentlyBoughtTogetherSku.setSkuNbr(105711);
        frequentlyBoughtTogetherSku.setAssociatedSkuNbr(123454);
        frequentlyBoughtTogetherSku.setSkuRankNbr(4);
        xtraCardDao.createFrequentlyBoughtTogetherSku(frequentlyBoughtTogetherSku);
        
        frequentlyBoughtTogetherSku.setSkuNbr(105711);
        frequentlyBoughtTogetherSku.setAssociatedSkuNbr(123455);
        frequentlyBoughtTogetherSku.setSkuRankNbr(5);
        xtraCardDao.createFrequentlyBoughtTogetherSku(frequentlyBoughtTogetherSku);

        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Affinity skus when single product is present
        */
        
        xtraCard.setXtraCardNbr(98168328);
        xtraCard.setCustId(80720);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168328);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80720);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80720);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80720);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80720);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168328);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        xtraCardSkuRank.setXtraCardNbr(98168328);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(599844);
        xtraCardSkuRank.setSkuRankNbr(1);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);        
        
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Affinity skus when multiple products are present
        */
        
        xtraCard.setXtraCardNbr(98168329);
        xtraCard.setCustId(80721);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168329);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80721);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80721);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80721);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80721);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168329);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        xtraCardSkuRank.setXtraCardNbr(98168329);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(599845);
        xtraCardSkuRank.setSkuRankNbr(1);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168329);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(599846);
        xtraCardSkuRank.setSkuRankNbr(2);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168329);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(599847);
        xtraCardSkuRank.setSkuRankNbr(3);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168329);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(599848);
        xtraCardSkuRank.setSkuRankNbr(4);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168329);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(599849);
        xtraCardSkuRank.setSkuRankNbr(5);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to Post  recently viewed products when single product is present
        */
        
        xtraCard.setXtraCardNbr(98168330);
        xtraCard.setCustId(80722);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168330);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80722);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80722);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80722);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80722);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168330);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

        /*
         * As a Rx.COM ExtraCare Customers I want to Post  recently viewed products when multiple products are present
        */
        
        xtraCard.setXtraCardNbr(98168331);
        xtraCard.setCustId(80723);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168331);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80723);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80723);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80723);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80723);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168331);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);

        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Top Selling skus when single SKU is present
        */

        storeSkuRank.setStoreNbr(5610);
        storeSkuRank.setRankTypeCode("T");
        storeSkuRank.setSkuNbr(4001);
        storeSkuRank.setSkuRankNbr(1);
        xtraCardDao.createStoreSkuRank(storeSkuRank);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Top Selling skus when multiple SKUs are present
        */

        storeSkuRank.setStoreNbr(5620);
        storeSkuRank.setRankTypeCode("T");
        storeSkuRank.setSkuNbr(4010);
        storeSkuRank.setSkuRankNbr(1);
        xtraCardDao.createStoreSkuRank(storeSkuRank);
        
        storeSkuRank.setStoreNbr(5620);
        storeSkuRank.setRankTypeCode("T");
        storeSkuRank.setSkuNbr(4011);
        storeSkuRank.setSkuRankNbr(2);
        xtraCardDao.createStoreSkuRank(storeSkuRank);
        
        storeSkuRank.setStoreNbr(5620);
        storeSkuRank.setRankTypeCode("T");
        storeSkuRank.setSkuNbr(4012);
        storeSkuRank.setSkuRankNbr(3);
        xtraCardDao.createStoreSkuRank(storeSkuRank);
        
        storeSkuRank.setStoreNbr(5620);
        storeSkuRank.setRankTypeCode("T");
        storeSkuRank.setSkuNbr(4013);
        storeSkuRank.setSkuRankNbr(4);
        xtraCardDao.createStoreSkuRank(storeSkuRank);
        
        storeSkuRank.setStoreNbr(5620);
        storeSkuRank.setRankTypeCode("T");
        storeSkuRank.setSkuNbr(4014);
        storeSkuRank.setSkuRankNbr(5);
        xtraCardDao.createStoreSkuRank(storeSkuRank);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Coupon Recommended skus for validating YES
        */
        
        xtraCard.setXtraCardNbr(98168332);
        xtraCard.setCustId(80724);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168332);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80724);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80724);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80724);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80724);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168332);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        campaignCouponCriteria.setCmpgnId(35412);
        campaignCouponCriteria.setCpnNbr(148468);
        campaignCouponCriteria.setCriteriaUsageCd("GA");
        campaignCouponCriteria.setAllItemInd("N");
        campaignCouponCriteria.setCriteriaId(10022);
        campaignCouponCriteria.setIncExcCd("I");
        campaignCouponCriteria.setRqrdPurchAmt(2);
        campaignCouponCriteria.setAmtTypeCd("D");
        campaignCouponCriteria.setLastChngDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        campaignDao.CreateCampaignCouponCriteria(campaignCouponCriteria);
        
        campaignCouponCriteria.setCmpgnId(35412);
        campaignCouponCriteria.setCpnNbr(148468);
        campaignCouponCriteria.setCriteriaUsageCd("GV");
        campaignCouponCriteria.setAllItemInd("N");
        campaignCouponCriteria.setCriteriaId(10023);
        campaignCouponCriteria.setIncExcCd("I");
        campaignCouponCriteria.setRqrdPurchAmt(2);
        campaignCouponCriteria.setAmtTypeCd("D");
        campaignCouponCriteria.setLastChngDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        campaignDao.CreateCampaignCouponCriteria(campaignCouponCriteria);

        campaignCouponSkuRank.setCmpgnId(35412);
        campaignCouponSkuRank.setCpnNbr(148468);
        campaignCouponSkuRank.setSkuNbr(101011);
        campaignCouponSkuRank.setSkuRankNbr(1);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35412);
        campaignCouponSkuRank.setCpnNbr(148468);
        campaignCouponSkuRank.setSkuNbr(101012);
        campaignCouponSkuRank.setSkuRankNbr(2);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35412);
        campaignCouponSkuRank.setCpnNbr(148468);
        campaignCouponSkuRank.setSkuNbr(101013);
        campaignCouponSkuRank.setSkuRankNbr(3);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35412);
        campaignCouponSkuRank.setCpnNbr(148468);
        campaignCouponSkuRank.setSkuNbr(101014);
        campaignCouponSkuRank.setSkuRankNbr(4);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35412);
        campaignCouponSkuRank.setCpnNbr(148468);
        campaignCouponSkuRank.setSkuNbr(101015);
        campaignCouponSkuRank.setSkuRankNbr(5);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35412);
        campaignCouponSkuRank.setCpnNbr(148468);
        campaignCouponSkuRank.setSkuNbr(101016);
        campaignCouponSkuRank.setSkuRankNbr(6);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35412);
        campaignCouponSkuRank.setCpnNbr(148468);
        campaignCouponSkuRank.setSkuNbr(101017);
        campaignCouponSkuRank.setSkuRankNbr(7);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168332);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(101012);
        xtraCardSkuRank.setSkuRankNbr(2);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168332);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(101013);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardSkuRank.setXtraCardNbr(98168332);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(101011);
        xtraCardSkuRank.setSkuRankNbr(1);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        /*
         * As a Rx.COM ExtraCare Customers I want to retrieve relevant Coupon Recommended skus for validating NO
        */
        
        xtraCard.setXtraCardNbr(98168333);
        xtraCard.setCustId(80725);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        xtraCard.setMktgTypeCd(" ");
        xtraCardDao.createXtraCard(xtraCard);
        
        xtraCard.setEncodedXtraCardNbr(98168333);
        xtraCardDao.updateXtraCard(xtraCard);

        customer.setCustId(80725);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerEmail.setCustId(80725);
        customerEmail.setEmailAddrTypeCd("H");
        customerEmail.setEmailPrefSeqNbr(1);
        customerEmail.setEmailAddrTxt("abc@CVS.com");
        customerEmail.setEmailStatusCd("A");
        customerDao.createCustomerEmail(customerEmail);

        customerPhone.setCustId(80725);
        customerPhone.setPhoneTypeCd("H");
        customerPhone.setPhonePrefSeqNbr(1);
        customerPhone.setPhoneAreaCdNbr(123);
        customerPhone.setPhonePfxNbr(456);
        customerPhone.setPhoneSfxNbr(7890);
        customerDao.createCustomerPhone(customerPhone);

        customerAddress.setCustId(80725);
        customerAddress.setAddrTypeCd("H");
        customerAddress.setAddrPrefSeqNbr(1);
        customerAddress.setAddr1Txt("951 YORK RD #106");
        customerAddress.setAddr2Txt(" ");
        customerAddress.setCityName("PARMA HTS");
        customerAddress.setStCd("OH");
        customerAddress.setZipCd("44130");
        customerDao.createCustomerAddress(customerAddress);

        xtraCardSegment.setXtraCardNbr(98168333);
        xtraCardSegment.setXtraCardSegNbr(337);
        xtraCardSegment.setCtlGrpCd(null);
        xtraCardDao.createXtraCardSegment(xtraCardSegment);
        
        campaignCouponCriteria.setCmpgnId(35414);
        campaignCouponCriteria.setCpnNbr(148469);
        campaignCouponCriteria.setCriteriaUsageCd("GA");
        campaignCouponCriteria.setAllItemInd("Y");
        campaignCouponCriteria.setCriteriaId(10024);
        campaignCouponCriteria.setIncExcCd("I");
        campaignCouponCriteria.setRqrdPurchAmt(1);
        campaignCouponCriteria.setAmtTypeCd("D");
        campaignCouponCriteria.setLastChngDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        campaignDao.CreateCampaignCouponCriteria(campaignCouponCriteria);
        
        campaignCouponCriteria.setCmpgnId(35414);
        campaignCouponCriteria.setCpnNbr(148469);
        campaignCouponCriteria.setCriteriaUsageCd("GV");
        campaignCouponCriteria.setAllItemInd("Y");
        campaignCouponCriteria.setCriteriaId(10025);
        campaignCouponCriteria.setIncExcCd("I");
        campaignCouponCriteria.setRqrdPurchAmt(1);
        campaignCouponCriteria.setAmtTypeCd("D");
        campaignCouponCriteria.setLastChngDt(simpleDateFormat.parse(carePassDateUtil.carePassExpireTswtz(0)));
        campaignDao.CreateCampaignCouponCriteria(campaignCouponCriteria);

        campaignCouponSkuRank.setCmpgnId(35414);
        campaignCouponSkuRank.setCpnNbr(148469);
        campaignCouponSkuRank.setSkuNbr(101021);
        campaignCouponSkuRank.setSkuRankNbr(1);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35414);
        campaignCouponSkuRank.setCpnNbr(148469);
        campaignCouponSkuRank.setSkuNbr(101022);
        campaignCouponSkuRank.setSkuRankNbr(2);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35414);
        campaignCouponSkuRank.setCpnNbr(148469);
        campaignCouponSkuRank.setSkuNbr(101023);
        campaignCouponSkuRank.setSkuRankNbr(3);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35414);
        campaignCouponSkuRank.setCpnNbr(148469);
        campaignCouponSkuRank.setSkuNbr(101024);
        campaignCouponSkuRank.setSkuRankNbr(4);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35414);
        campaignCouponSkuRank.setCpnNbr(148469);
        campaignCouponSkuRank.setSkuNbr(101025);
        campaignCouponSkuRank.setSkuRankNbr(5);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35414);
        campaignCouponSkuRank.setCpnNbr(148469);
        campaignCouponSkuRank.setSkuNbr(101026);
        campaignCouponSkuRank.setSkuRankNbr(6);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        campaignCouponSkuRank.setCmpgnId(35414);
        campaignCouponSkuRank.setCpnNbr(148469);
        campaignCouponSkuRank.setSkuNbr(101027);
        campaignCouponSkuRank.setSkuRankNbr(7);
        campaignDao.CreateCampaignCouponSkuRank(campaignCouponSkuRank);
        
        xtraCardSkuRank.setXtraCardNbr(98168333);
        xtraCardSkuRank.setRankTypeCode("B");
        xtraCardSkuRank.setSkuNbr(101022);
        xtraCardSkuRank.setSkuRankNbr(2);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        xtraCardRecentlyViewedSku.setXtraCardNbr(98168333);
        xtraCardRecentlyViewedSku.setViewActlDestCd("W");
        xtraCardRecentlyViewedSku.setLastViewSrcCd(null);
        xtraCardRecentlyViewedSku.setSkuNbr(101023);
        xtraCardRecentlyViewedSku.setViewedTS((carePassDateUtil.carePassPrntStartEndTswtz()));
        xtraCardRecentlyViewedSku.setTotalViewedNbr(null);
        xtraCardDao.createXtraCardRecentlyViewedSku(xtraCardRecentlyViewedSku);
        Thread.sleep(1000);
        
        xtraCardSkuRank.setXtraCardNbr(98168333);
        xtraCardSkuRank.setRankTypeCode("A");
        xtraCardSkuRank.setSkuNbr(101021);
        xtraCardSkuRank.setSkuRankNbr(1);
        xtraCardDao.createXtraCardSkuRank(xtraCardSkuRank);
        
        
        
    }

    /**
     * Create Test Data For Product API Scenarios
     */
    public void callTestDataAPIPost() throws ParseException {
        String api = "products";
        dataSetupUtil.hitDataApiDelete(api);
        dataSetupUtil.hitDataApiPost(api);
    }

    public void callTestDataAPIDelete(String api) throws ParseException {
        //    dataSetupUtil.hitDataApiDelete("product");
        //    dataSetupUtil.hitDataApiPost("product");
        String apiRequested = api;
        String userId = "CVS.COM";

        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri(serviceConfig.getDataapiUrl())
                .setBasePath("api/v1.1/data/{api_type}")
       //         .addPathParam("api_type", apiRequested)
                .addQueryParam("user_id", userId);
        RequestSpecification spec2 = requestSpecBuilder2.build();
        serviceResponse = (Response) given().spec(spec2).contentType("application/json").delete();
    }


    /**
     * Delete Test Data for PHR Transaction History Scenario
     */
    public void deleteProductAPITestData() {
	  /*
	    Purge All Test Cards
	     */
//        List<Integer> xtraCardNbrList = Arrays.asList(900058650);
        List<Integer> xtraCardNbrList = Arrays.asList(900058650, 98168319, 98168320, 98168321, 98168322, 98168323, 98168324, 98168325, 98168326, 98168327, 98168328, 98168329, 98168330, 98168331, 98168332, 98168333);
        List<Integer> custIdList = Arrays.asList(80711, 80712, 80713, 80714, 80715, 80716, 80717, 80718, 80719, 80720, 80721, 80722, 80723, 80724, 80725);
        List<Integer> skuList = Arrays.asList(105712, 105711);
        List<Integer> storeNbrList = Arrays.asList(5610, 5620);
        List<Integer> cmpgnList = Arrays.asList(35412, 35414);

//        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        customerDao.deleteCustomers(custIdList);
        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        xtraCardDao.deleteXtraCardSkuRanks(xtraCardNbrList);
        xtraCardDao.deleteXtraCardRecentlyViewedSkus(xtraCardNbrList);
        xtraCardDao.deleteFrequentlyBoughtTogetherSku(skuList);
        xtraCardDao.deleteStoreSkuRanks(storeNbrList);
        campaignDao.deleteCampaignCouponCriteria(cmpgnList);
        campaignDao.deleteCampaignCouponSkuRank(cmpgnList);
    }
}