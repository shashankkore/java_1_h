package com.cvs.crm.cukes.campaign.glue;

import java.util.ArrayList;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.model.request.CmpgnDefnRequest;
import com.cvs.crm.service.CampaignDefnService;

import cucumber.api.java8.En;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CampaignStepDefinitions extends SpringIntegrationTests implements En {

	@Autowired
	CampaignDefnService campaignDefnService;

	@SuppressWarnings("unchecked")
	public CampaignStepDefinitions() {
		{

			CmpgnDefnRequest campRequest = new CmpgnDefnRequest();
			Given("CVS campaign id  with {int}", (Integer campaignId) -> {
				campRequest.setCampainId(campaignId.toString());

			});

			When("campaign message source code as {string}", (String msgSourceCode) -> {
				campRequest.setMsgSrcCd(msgSourceCode);
			});

			Then("source location code as {int}", (Integer sourceLoactionCode) -> {
				campRequest.setSrcLocCd(sourceLoactionCode);
			});

			Then("user id as {string}", (String userId) -> {
				campRequest.setUserId(userId);
			});

			Then("fetch campaign details successfully", () -> {
				Response campResponse = campaignDefnService.singleCampaignDef(campRequest);

			});

			Then("Validate campaign details retrieved successfully", () -> {
				Assert.assertEquals(200, campaignDefnService.singleCampaignDef(campRequest).getStatusCode());
			});

			Given("CVS invalid campaign id  with {int}", (Integer campaignId) -> {
				campRequest.setCampainId(campaignId.toString());

			});

			Then("Validate campaign details not retrieved successfully", () -> {

				if (null != (campaignDefnService.singleCampaignDef(campRequest).getBody().jsonPath()
						.get("respCmpgnDefs"))
						&& ((ArrayList<Object>) (campaignDefnService.singleCampaignDef(campRequest).getBody().jsonPath()
								.get("respCmpgnDefs"))).size() > 0) {
					Assert.assertTrue(false);
				} else
					Assert.assertTrue(true);
			});

			When("campaign invalid message source code as {string}", (String msgSourceCode) -> {
				campRequest.setMsgSrcCd(msgSourceCode);
			});

			Then("Validate campaign throw Error", () -> {
				Assert.assertEquals(400, campaignDefnService.singleCampaignDef(campRequest).getStatusCode());
			});

			Then("invalid user id as {string}", (String userId) -> {
				campRequest.setUserId(userId);
			});
			Then("Validate campaign details throw access forbidden", () -> {
				Assert.assertEquals(403, campaignDefnService.CmpgnDefnBySubType(campRequest).getStatusCode());
			});

			Then("compress_ind as {string}", (String compressIndicator) -> {
				campRequest.setCompressInd(compressIndicator);
			});

			Then("campaign type code as {string} and campaing subtype code as {string}",
					(String cmpnTypeCode, String subTypeCode) -> {
						campRequest.setCampaignTypeCode(cmpnTypeCode);
						campRequest.setSubTypeCode(subTypeCode);
					});

			Then("skip expired  as {string}", (String skipExpired) -> {
				campRequest.setSkipExpired(skipExpired);
			});

			Then("submit a request to CmpgnDefnBySubType", () -> {
				Response campResponse = campaignDefnService.CmpgnDefnBySubType(campRequest);

			});

			Then("Validate campaign definations retrieved successfully", () -> {
				Assert.assertEquals(200, campaignDefnService.CmpgnDefnBySubType(campRequest).getStatusCode());
			});

			Then("Validate campaign definations not retrieved successfully", () -> {
				Assert.assertEquals(400, campaignDefnService.CmpgnDefnBySubType(campRequest).getStatusCode());
			});

			Then("invalid campaign type code as {string} and campaing subtype code as {string}",
					(String cmpnTypeCode, String subTypeCode) -> {
						campRequest.setCampaignTypeCode(cmpnTypeCode);
						campRequest.setSubTypeCode(subTypeCode);
					});

			Then("Validate no campaign Definations retrieved successfully", () -> {

				if (null != (campaignDefnService.CmpgnDefnBySubType(campRequest).getBody().jsonPath()
						.get("respCmpgnDefs"))
						&& ((ArrayList<Object>) (campaignDefnService.CmpgnDefnBySubType(campRequest).getBody()
								.jsonPath().get("respCmpgnDefs"))).size() > 0) {
					Assert.assertTrue(false);
				} else
					Assert.assertTrue(true);
			});

			Then("campaign type code as {string} and campaing invalid subtype code as {string}",
					(String cmpnTypeCode, String subTypeCode) -> {
						campRequest.setCampaignTypeCode(cmpnTypeCode);
						campRequest.setSubTypeCode(subTypeCode);
					});

			Then("submit Fetch all campaign Definations request", () -> {
				Response campResponse = campaignDefnService.bulkCmpgnDefns(campRequest);

			});

			Then("validate all campaign definations retrieved successfully", () -> {
				Assert.assertEquals(200, campaignDefnService.bulkCmpgnDefns(campRequest).getStatusCode());
			});

			Then("validate no campaign definations  retrieved successfully", () -> {
				Assert.assertEquals(400, campaignDefnService.bulkCmpgnDefns(campRequest).getStatusCode());
			});

			Then("validate access forbidden error thrown", () -> {
				Assert.assertEquals(403, campaignDefnService.bulkCmpgnDefns(campRequest).getStatusCode());
			});

			Then("filter date  as {string}", (String filter_dt) -> {
				campRequest.setFilter_dt(filter_dt);
			});

			When("submit Fetch all campaign Definations after givendate request", () -> {
				Response campResponse = campaignDefnService.bulkCmpgnDefnsUpdatedAfterGivenDate(campRequest);

			});

			Then("validate  campaign Definations after givendate retrieved successfully", () -> {
				Assert.assertEquals(200,
						campaignDefnService.bulkCmpgnDefnsUpdatedAfterGivenDate(campRequest).getStatusCode());
			});

			Then("validate campaign Definations after givendate not retrieved", () -> {
				Assert.assertEquals(400,
						campaignDefnService.bulkCmpgnDefnsUpdatedAfterGivenDate(campRequest).getStatusCode());
			});

			Then("validate access forbidden error thrown for campaign Definations after givendate request", () -> {
				Assert.assertEquals(403,
						campaignDefnService.bulkCmpgnDefnsUpdatedAfterGivenDate(campRequest).getStatusCode());
			});
			Then("rhb event type as {string}", (String rhb_event_type) -> {
				campRequest.setRhbEventType(rhb_event_type);
			});

			When("submit Fetch all campaign Definations ByRHBEventType", () -> {
				Response campResponse = campaignDefnService.CmpgnDefnByRHBEventType(campRequest);

			});

			Then("Validate  campaign definations ByRHBEventType retrieved successfully", () -> {
				Assert.assertEquals(200, campaignDefnService.CmpgnDefnByRHBEventType(campRequest).getStatusCode());
			});

			Then("Validate  campaign definations ByRHBEventType not retrieved", () -> {
				Assert.assertEquals(400, campaignDefnService.CmpgnDefnByRHBEventType(campRequest).getStatusCode());
			});

			Then("validate access forbidden error thrown for campaign Definations ByRHBEventType", () -> {
				Assert.assertEquals(403, campaignDefnService.CmpgnDefnByRHBEventType(campRequest).getStatusCode());
			});

			Then("campingId list as {string}", (String campIdList) -> {
				campRequest.setCampainIdList(campIdList);
			});
			When("submit Fetch all campaign Definations by campid list", () -> {
				Response campResponse = campaignDefnService.CmpgnDefnForCmpgnIDList(campRequest);

			});

			Then("invalid campingId list as {string}", (String campIdList) -> {
				campRequest.setCampainIdList(campIdList);
			});

			Then("validate campaign details by list  retrieved successfully", () -> {
				Assert.assertEquals(200, campaignDefnService.CmpgnDefnForCmpgnIDList(campRequest).getStatusCode());
			});

			Then("validate all campaign details by list  retrieved successfully", () -> {
				Assert.assertEquals(200, campaignDefnService.CmpgnDefnForCmpgnIDList(campRequest).getStatusCode());
			});

			Then("validate no campaign details by list  retrieved successfully", () -> {

				if (null != (campaignDefnService.CmpgnDefnForCmpgnIDList(campRequest).getBody().jsonPath()
						.get("respCmpgnDefs"))
						&& ((ArrayList<Object>) (campaignDefnService.CmpgnDefnForCmpgnIDList(campRequest).getBody()
								.jsonPath().get("respCmpgnDefs"))).size() > 0) {
					Assert.assertTrue(false);
				} else
					Assert.assertTrue(true);
			});

		}
	}
}
