package com.cvs.crm.cukes.phrJ.enrollment.glue;



import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.HREnrollRequest;
import com.cvs.crm.model.request.HREventRequest;
import com.cvs.crm.model.response.ApiErrorResponse;
import com.cvs.crm.model.response.DashboardResponse;
import com.cvs.crm.model.response.PharmacyHealthRewardsResponse;
import com.cvs.crm.service.PharmacyHealthRewardsService;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.PhrTestDataSetupUtil;
import com.cvs.crm.util.TestDataSetupUtil;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class PhrEnrollmentStepDefinitions extends SpringIntegrationTests implements En {

    @Autowired
    PharmacyHealthRewardsService pharmacyHealthRewardsService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;
    
    @Autowired
    PhrTestDataSetupUtil phrTestDataSetupUtil;

    public PhrEnrollmentStepDefinitions() {
        {
        	HREnrollRequest hrrequest = new HREnrollRequest();
        	HREventRequest hreventrequest = new HREventRequest();
        	DashBoardRequest request = new DashBoardRequest();
        	GetCustRequest getcustrequest = new GetCustRequest();
      
        	
            Given("{string}", (String scenario) -> {
            });
            
            Given("I am a CVS user with {int} for phr enroll", (Integer xtraCardNbr) -> {
            	// Create a user with extra card number
//            	phrTestDataSetupUtil.createUserWithXtraCardNbr(xtraCardNbr);
            	log.info("Step 1");
  		  	});
            
            Given("I am a CVS user with {int} for phr enroll with message source code {string}", (Integer xtraCardNbr, String messageSourceCode) -> {
            	hrrequest.setSearchCardNbr(xtraCardNbr);
            	hrrequest.setChannel(messageSourceCode);
            	
//            	hreventrequest.setSearchCardNbr(xtraCardNbr);
            	hreventrequest.setChannel(messageSourceCode);
//            	log.info("Step 1");
  		  	});
            
            And("Set action code {string}", (String actionCode) -> {
            	hrrequest.setActionCode(actionCode);
  		  	});
            
            And("Set patient ID {string}", (String idNumber) -> {
            	hrrequest.setIdNumber(idNumber);
  		  	});
            
            And("Set card type {string}", (String cardType) -> {
            	hrrequest.setSearchCardType(cardType);
  		  	});
            
            When("I use Extra Card {int} and encoded xtra card {string} to enroll in to Phr program", (Integer xtraCardNbr, String encodedXtraCardNbr) -> {
//            	hrrequest.setSearchCardNbr(xtraCardNbr);
            	hrrequest.setSearchEncodedXtraCardNbr(encodedXtraCardNbr);
//            	hrrequest.setSearchCardType("0004");
            	pharmacyHealthRewardsService.phrEnroll(hrrequest);
//            	log.info("Step 2");
            });
            
            Then("The user enrolled in to Phr successfully", () -> {
            	pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(201);
//            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().getString("xtraCardNbr").equalsIgnoreCase(anotherString));
            	System.out.println("Success response");
            	log.info("Step 3");
            });
            
            And("Validate the {int} in the response body", (Integer xtraCardNbr) -> {
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("xtraCardNbr").equals(xtraCardNbr));
            });
            
            Then("Validate the phr enrolled status as {string} for {int} in getcust response", (String getCustStatus, Integer xtraCardNbr) -> {
            	pharmacyHealthRewardsService.getCust(hrrequest);
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.xtraCarePrefs.phr.enrolled").equals(getCustStatus));
            });
            
            Then("I validate the error code", () -> {
            	pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(400);
            });
            
            When("I use Extra Card {int} and encoded xtra card {string} to unenroll in to Phr program", (Integer xtraCardNbr, String encodedXtraCardNbr) -> {
//            	hrrequest.setSearchCardNbr(xtraCardNbr);
            	hrrequest.setSearchEncodedXtraCardNbr(encodedXtraCardNbr);
//            	hrrequest.setSearchCardType("0004");
            	pharmacyHealthRewardsService.phrEnroll(hrrequest);
            	log.info("Step 2");
            });
            
            And("I validate the error message {string}", (String errorMessage) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//            	PharmacyHealthRewardsResponse pharmacyHealthRewardsResponse = pharmacyHealthRewardsService.getServiceResponse().as(PharmacyHealthRewardsResponse.class);
//            	System.out.println("Expected" + errorMessage);
//            	System.out.println("Actual"+ pharmacyHealthRewardsResponse.getErrorMsg());
//            	Assert.assertEquals(errorMessage, pharmacyHealthRewardsResponse.getErrorMsg());
            	
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("errorMsg").equals(errorMessage));
            });
            
            Given("I am a CVS user with {int} for phr unenroll with message source code {string}", (Integer xtraCardNbr, String messageSourceCode) -> {
            	hrrequest.setSearchCardNbr(xtraCardNbr);
            	hrrequest.setChannel(messageSourceCode);
            	log.info("Step 1");
  		  	});
            
//            When("I use Extra Card {int} and encoded xtra card {string} to unenroll in to Phr program", (Integer xtraCardNbr, String encodedXtraCardNbr) -> {
////            	hrrequest.setSearchCardNbr(xtraCardNbr);
//            	hrrequest.setSearchEncodedXtraCardNbr(encodedXtraCardNbr);
////            	hrrequest.setSearchCardType("0004");
//            	pharmacyHealthRewardsService.phrEnroll(hrrequest);
//            	log.info("Step 2");
//            });
            
            Then("The user unenrolled from Phr successfully", () -> {
            	pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(201);
//            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().getString("xtraCardNbr").equalsIgnoreCase(anotherString));
            	System.out.println("Success response");
            	log.info("Step 3");
            });
            
            And("Set EPHID {string}", (String ephid) -> {
            	hreventrequest.setEPHID(ephid);
  		  	});
            
            And("Set id number {string}", (String idNumber) -> {
            	hreventrequest.setIdNumber(idNumber);
  		  	});
            
            And("Set id type {string}", (String idType) -> {
            	hreventrequest.setIdType(idType);
  		  	});
            
            And("Set event type code {string}", (String eventTypeCode) -> {
            	hreventrequest.setEventTypeCode(eventTypeCode);
  		  	});
            
            And("Set event earn code {string}", (String eventEarnCode) -> {
            	hreventrequest.setEventEarnCode(eventEarnCode);
  		  	});
            
            And("Set event earn reason code {string}", (String eventEarnReasonCode) -> {
            	hreventrequest.setEventEarnReasonCode(eventEarnReasonCode);
            	Thread.sleep(2000);
  		  	});
            
            When("I create HR event", () -> {
//            	hrrequest.setSearchCardNbr(xtraCardNbr);
//            	hrrequest.setSearchEncodedXtraCardNbr(encodedXtraCardNbr);
//            	hrrequest.setSearchCardType("0004");
            	pharmacyHealthRewardsService.phrEvent(hreventrequest);
            	log.info("Step 2");
            });
            
            Then("The HR event is successfully created", () -> {
            	pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(201);
//            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().getString("xtraCardNbr").equalsIgnoreCase(anotherString));
            	System.out.println("Success response");
//            	log.info("Step 3");
            });
            
            And("I validate the error message for blank EPHID or IDnbr {string}", (String errorMessage) -> {
          	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().getBody().asString().contains(errorMessage));
          });
            
            And("I validate the error message for blank id type or event type code {string}", (String errorMessage) -> {          	
          	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("errorMsg").equals(errorMessage));
          });
            
// *******************Dashboard********************
            
            
            And("I use my Extra Card {int}", (Integer xtraCardNbr) -> {
                request.setSearchCardType("0002");
                request.setSearchCardNbr(xtraCardNbr);
            });

            When("I view PHR Summary in DashBoard for my card", () -> {
                pharmacyHealthRewardsService.viewPharmacyHealthRewards(request);
            });

            Then("I see the Pharmacy Health Rewards", () -> {
                pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I see my PHR enrollment status as {string}", (String enrolled) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertTrue(dashboardResponse.getPharmacyHealthRewardsResponse().getEnrolled());
            	Assert.assertEquals(enrolled,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.enrolled").toString());
//            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.enrolled").equals(enrolled));
//            	boolean x = pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.enrolled");
//            	System.out.println("String 1 = " + x);
//            	System.out.println("String 2 = " + enrolled);
            });

            Then("I see my PHR enrollment as {string}", (String enrolled) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getEnrolled());
            });

            Then("I see my PHR Campaign Id as {int}", (Integer campaignId) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(campaignId, dashboardResponse.getPharmacyHealthRewardsResponse().getCampaignId());
            	Assert.assertEquals(campaignId,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.campaignId"));
            });

            Then("I see my PHR {string}", (String webDescription) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(webDescription, dashboardResponse.getPharmacyHealthRewardsResponse().getWebDescription());
            	Assert.assertEquals(webDescription,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.webDescription"));
            });

            Then("I see my PHR Campaign End Date as {string}", (String campaignEndDate) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(campaignEndDate, dashboardResponse.getPharmacyHealthRewardsResponse().getCampaignEndDate());
            });

            Then("I see my PHR Threshold Type Code as {string}", (String thresholdTypeCode) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(thresholdTypeCode, dashboardResponse.getPharmacyHealthRewardsResponse().getThresholdTypeCode());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.thresholdTypeCode").equals(thresholdTypeCode));

            });

            Then("I see my PHR First Threshold as {float}", (Float firstThreshold) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(firstThreshold, dashboardResponse.getPharmacyHealthRewardsResponse().getFirstThreshold());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.firstThreshold").equals(firstThreshold));

            });

            Then("I see my PHR Reward Amount as {float}", (Float rewardAmount) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(rewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getRewardAmount());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.rewardAmount").equals(rewardAmount));

            });

            Then("I see my PHR Points required to get next coupon as {float}", (Float pointsToNextThreshold) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(pointsToNextThreshold, dashboardResponse.getPharmacyHealthRewardsResponse().getPointsToNextThreshold());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.pointsToNextThreshold").equals(pointsToNextThreshold));

            });


            Then("I see my PHR Offer Limit Reached as {string}", (String offerLimitReached) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getOfferLimitReached());
            	Assert.assertEquals(offerLimitReached,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.offerLimitReached").toString());

            });

            Then("I see my PHR Points Progress as {float}", (Float pointsProgress) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(pointsProgress, dashboardResponse.getPharmacyHealthRewardsResponse().getPointsProgress());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.pointsProgress").equals(pointsProgress));

            });

            Then("I do not see the Pharmacy Health Rewards", () -> {
                pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(404);
            });


            Then("I see under my PHR who all enrolled as John Doe, Mary Krisher, Donna Furter and Tim Richards", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String fName = list.get(i).get("firstName");
                    //	String lName = list.get(i).get("lastName");
                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    Assert.assertEquals(fName, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getFirstName());
                    //        Assert.assertEquals(lName,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getLastName());
                }
            });

            Then("I see under my PHR who all enrolled as John Doe and Mary Krisher", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String fName = list.get(i).get("firstName");
                    //	String lName = list.get(i).get("lastName");
                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    Assert.assertEquals(fName, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getFirstName());
                    //        Assert.assertEquals(lName,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getLastName());
                }
            });


            Then("I see under my PHR who all enrolled true as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String capped = list.get(i).get("capped");
                    //String cappedDate = list.get(i).get("cappedDate");
                    Integer cappedDate = Integer.valueOf(list.get(i).get("cappedDate"));
                    String expectedCappedDate = dateUtil.dealEndDate(cappedDate);
                    Double maxCredits = Double.valueOf(list.get(i).get("maxCredits"));
                    Double maxRewardAmount = Double.valueOf(list.get(i).get("maxRewardAmount"));
                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    Assert.assertEquals(expectedCappedDate, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCappedDate());
                    Assert.assertTrue(dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCapped());
                    Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxCredits());
                    Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxRewardAmount());
                }
            });


            Then("I see under my PHR who all enrolled as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String capped = list.get(i).get("capped");
                    Double maxCredits = Double.valueOf(list.get(i).get("maxCredits"));
                    Double maxRewardAmount = Double.valueOf(list.get(i).get("maxRewardAmount"));

                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                    // Assert.assertEquals(capped,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCapped());
                    Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getCapped());
                    Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxCredits());
                    Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getMaxRewardAmount());
                }
            });
            
            Then("I see under my PHR who all enrolled as Hari1 and Hari2", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String fName = list.get(i).get("firstName");
                    String capped = list.get(i).get("capped");
                    //	String lName = list.get(i).get("lastName");
//                    DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                    Assert.assertEquals(fName, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(i).getFirstName());
                	Assert.assertEquals(fName, pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.members["+i+"].firstName"));
                	Assert.assertEquals(capped, pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.members["+i+"].capped").toString());

                }
            });

            Then("I see under my PHR who all enrolled as: {string}", (String flName) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertEquals(flName, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getFirstName());
                //Assert.assertEquals(flName,dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getMaxCredits());

            });

            Then("I see under my PHR who all enrolled as:", () -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                int members = dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().size();
                Assert.assertEquals(0, members);

            });

            Then("I see PHR Error Code as {int}", (Integer errorCd) -> {
                ApiErrorResponse apiErrorResponse = pharmacyHealthRewardsService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorCd, apiErrorResponse.getErrorCd());
            });

            Then("I see PHR Error Message as {string}", (String errorMsg) -> {
                ApiErrorResponse apiErrorResponse = pharmacyHealthRewardsService.getServiceResponse().as(ApiErrorResponse.class);
                Assert.assertEquals(errorMsg, apiErrorResponse.getErrorMsg());
            });

            Then("I see my PHR maxCredits as {float}", (Float maxCredits) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMaxCredits());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.maxCredits").equals(maxCredits));
//            	float x = pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.maxCredits");
//            	System.out.println("String 1 = " + x);
//            	System.out.println("String 2 = " + maxCredits);

            });


            Then("I see my PHR maxRewardAmount as {float}", (Float maxRewardAmount) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMaxRewardAmount());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.maxRewardAmount").equals(maxRewardAmount));

            });


            Then("I see my PHR capped as {string}", (String capped) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getCapped());
            	Assert.assertEquals(capped,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.capped").toString());

            });

            Then("I see my PHR capped status as {string}", (String capped) -> {
                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
                Assert.assertTrue(dashboardResponse.getPharmacyHealthRewardsResponse().getCapped());

            });


            Then("I see my PHR year To Date Earned as {float}", (Float yearToDateEarned) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(yearToDateEarned, dashboardResponse.getPharmacyHealthRewardsResponse().getYearToDateEarned());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.yearToDateEarned").equals(yearToDateEarned));

            });


            Then("I see my PHR year To Date Credits as {float}", (Float yearToDateCredits) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(yearToDateCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getYearToDateCredits());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.yearToDateCredits").equals(yearToDateCredits));

            });


            Then("I see my Member maxCredits as {float}", (Float maxCredits) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(maxCredits, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getMaxCredits());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.members[0].maxCredits").equals(maxCredits));

            });

            Then("I see my Member maxRewardAmount as {float}", (Float maxRewardAmount) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertEquals(maxRewardAmount, dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getMaxRewardAmount());
            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.members[0].maxRewardAmount").equals(maxRewardAmount));

            });

            Then("I see my first Member capped as {string}", (String capped) -> {
//                DashboardResponse dashboardResponse = pharmacyHealthRewardsService.getServiceResponse().as(DashboardResponse.class);
//                Assert.assertFalse(dashboardResponse.getPharmacyHealthRewardsResponse().getMembers().get(0).getCapped());
            	Assert.assertEquals(capped,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("pharmacyHealthRewards.members[0].capped").toString());

            });
            
//            *********************GetCust tests****************

            Given("I am a CVS user with {int} for getcust with message source code {string}", (Integer id_number, String messageSourceCode) -> {
            	getcustrequest.setSearchCardNbr(id_number);
            	getcustrequest.setChannel(messageSourceCode);
            	
//            	hreventrequest.setSearchCardNbr(xtraCardNbr);
//            	hreventrequest.setChannel(messageSourceCode);
//            	log.info("Step 1");
  		  	});
            
            When("I execute getcust service", () -> {
//            	hrrequest.setSearchCardNbr(xtraCardNbr);
//            	hrrequest.setSearchEncodedXtraCardNbr(encodedXtraCardNbr);
//            	hrrequest.setSearchCardType("0004");
            	pharmacyHealthRewardsService.getCustPHRNode(getcustrequest);
//            	log.info("Step 2");
            });
            
            Then("Check getcust response", () -> {
            	pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(200);
//            	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().getString("xtraCardNbr").equalsIgnoreCase(anotherString));
            	System.out.println("Success response");
            	log.info("Step 3");
            });
            
            And("Validate the phr enrollment status as {string} in the getcust response body", (String enrolled) -> {
            	Assert.assertEquals(enrolled,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.xtraCarePrefs.phr.enrolled").toString());
            });
//            
            And("Validate the campaign id as {int} in the getcust response body", (Integer campaignId) -> {
            	Assert.assertEquals(campaignId,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.xtraCarePrefs.phr.cmpgnId"));
            });
            
            And("Validate the encoded Xtra card number {string} in the getcust response body", (String encodedXtraCardNbr) -> {
            	Assert.assertEquals(encodedXtraCardNbr,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.hrEnrollStatus[0].encodedXtraCardNbr").toString());
            });
            
            Then("Check getcust error response", () -> {
            	pharmacyHealthRewardsService.getServiceResponse().then().log().all().statusCode(400);
            });
            
            And("Validate getcust error code as {int}", (Integer errorcd) -> {
          	Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("errorCd").equals(errorcd));
            });

            And("Validate getcust error message as {string}", (String errorMsg) -> {
            Assert.assertTrue(pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("errorMsg").equals(errorMsg));
            });
            
            And("Validate the ephLinkId under HR Event details {int} as in the getcust response body", (Integer ephLinkId) -> {
            Assert.assertEquals(ephLinkId,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.hrEvtDtl[0].ephLinkId"));            
            });
            
            And("Validate the idNbr under HR Event details {int} as in the getcust response body", (Integer idNbr) -> {
            Assert.assertEquals(idNbr,pharmacyHealthRewardsService.getServiceResponse().jsonPath().get("cusInfResp.hrEvtDtl[0].idNbr"));            
            });

        }
    }
}
