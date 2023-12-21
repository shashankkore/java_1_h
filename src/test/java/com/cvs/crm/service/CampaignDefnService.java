package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.CmpgnDefnRequest;
import com.cvs.crm.model.request.CpnsRequest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CampaignDefnService {

	private Response serviceResponse;

	@Autowired
	ServiceConfig serviceConfig;

	public void viewCmpgnDefn(CmpgnDefnRequest cmpgnDefnRequest) throws ParseException {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		String msgSrcCd;
		String userId;
		int srcLocCd;
		Boolean compressInd = false;
		Boolean skipExpired = true;
		if ("MOBILE".equalsIgnoreCase(cmpgnDefnRequest.getChannel())) {
			msgSrcCd = "M";
			userId = "MOBILE_ENT";
			srcLocCd = 90042;
		} else {
			msgSrcCd = "W";
			userId = "CVS.COM";
			srcLocCd = 2695;
		}

		requestSpecBuilder.setBaseUri(serviceConfig.getCmpgndefnUrl()).setBasePath("/api/v1.1/campaign_definitions")
				.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
				.addQueryParam("src_loc_cd", srcLocCd).addQueryParam("compress_ind", compressInd)
				.addQueryParam("skip_expired", skipExpired);

		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = (Response) given().spec(spec).when().get();
	}

	public Response singleCampaignDef(CmpgnDefnRequest cmpgnDefnRequest) throws ParseException {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

		requestSpecBuilder.setBaseUri(serviceConfig.getCmpgndefnUrl())
				.setBasePath("/api/v1.1/campaign_definitions/{campaignId}")
				.addPathParam("campaignId", cmpgnDefnRequest.getCampainId())
				.addQueryParam("msg_src_cd", cmpgnDefnRequest.getMsgSrcCd())
				.addQueryParam("user_id", cmpgnDefnRequest.getUserId())
				.addQueryParam("src_loc_cd", cmpgnDefnRequest.getSrcLocCd());

		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = (Response) given().spec(spec).when().get();
		return serviceResponse;
	}

	public Response CmpgnDefnBySubType(CmpgnDefnRequest cmpgnDefnRequest) throws ParseException {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

		requestSpecBuilder.setBaseUri(serviceConfig.getCmpgndefnUrl()).setBasePath("/api/v1.1/campaign_definitions")
				.addQueryParam("msg_src_cd", cmpgnDefnRequest.getMsgSrcCd())
				.addQueryParam("src_loc_cd", cmpgnDefnRequest.getSrcLocCd())
				.addQueryParam("user_id", cmpgnDefnRequest.getUserId())
				.addQueryParam("compress_ind", cmpgnDefnRequest.getCompressInd())
				.addQueryParam("cmpgn_type_cd", cmpgnDefnRequest.getCampaignTypeCode())
				.addQueryParam("cmpgn_subtype_cd", cmpgnDefnRequest.getSubTypeCode())
				.addQueryParam("skip_expired", cmpgnDefnRequest.getSkipExpired());

		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = (Response) given().spec(spec).when().get();
		return serviceResponse;
	}

	public Response bulkCmpgnDefns(CmpgnDefnRequest cmpgnDefnRequest) throws ParseException {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

		requestSpecBuilder.setBaseUri(serviceConfig.getCmpgndefnUrl()).setBasePath("/api/v1.1/campaign_definitions")
				.addQueryParam("msg_src_cd", cmpgnDefnRequest.getMsgSrcCd())
				.addQueryParam("src_loc_cd", cmpgnDefnRequest.getSrcLocCd())
				.addQueryParam("user_id", cmpgnDefnRequest.getUserId())
				.addQueryParam("compress_ind", cmpgnDefnRequest.getCompressInd());

		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = (Response) given().spec(spec).when().get();
		return serviceResponse;
	}

	public Response bulkCmpgnDefnsUpdatedAfterGivenDate(CmpgnDefnRequest cmpgnDefnRequest) throws ParseException {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

		requestSpecBuilder.setBaseUri(serviceConfig.getCmpgndefnUrl()).setBasePath("/api/v1.1/campaign_definitions")
				.addQueryParam("msg_src_cd", cmpgnDefnRequest.getMsgSrcCd())
				.addQueryParam("src_loc_cd", cmpgnDefnRequest.getSrcLocCd())
				.addQueryParam("user_id", cmpgnDefnRequest.getUserId())
				.addQueryParam("compress_ind", cmpgnDefnRequest.getCompressInd())
				.addQueryParam("filter_dt", cmpgnDefnRequest.getFilter_dt());

		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = (Response) given().spec(spec).when().get();
		return serviceResponse;
	}

	public Response CmpgnDefnByRHBEventType(CmpgnDefnRequest cmpgnDefnRequest) throws ParseException {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

		requestSpecBuilder.setBaseUri(serviceConfig.getCmpgndefnUrl()).setBasePath("/api/v1.1/campaign_definitions")
				.addQueryParam("msg_src_cd", cmpgnDefnRequest.getMsgSrcCd())
				.addQueryParam("src_loc_cd", cmpgnDefnRequest.getSrcLocCd())
				.addQueryParam("user_id", cmpgnDefnRequest.getUserId())
				.addQueryParam("compress_ind", cmpgnDefnRequest.getCompressInd())
				.addQueryParam("rhb_event_type", cmpgnDefnRequest.getRhbEventType())
				.addQueryParam("skip_expired", cmpgnDefnRequest.getSkipExpired());

		RequestSpecification spec = requestSpecBuilder.build();
		serviceResponse = (Response) given().spec(spec).when().get();
		return serviceResponse;
	}

	public Response CmpgnDefnForCmpgnIDList(CmpgnDefnRequest cmpgnDefnRequest) throws ParseException {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

		requestSpecBuilder.setBaseUri(serviceConfig.getCmpgndefnUrl()).setBasePath("/api/v1.1/campaign_definitions")
				.setContentType("application/json").setBody(getServiceBody_CmpgnDefnForCmpgnIDList(cmpgnDefnRequest))
				.addQueryParam("msg_src_cd", cmpgnDefnRequest.getMsgSrcCd())
				.addQueryParam("src_loc_cd", cmpgnDefnRequest.getSrcLocCd())
				.addQueryParam("user_id", cmpgnDefnRequest.getUserId());

		RequestSpecification spec = requestSpecBuilder.build();
		System.out.println("Start");
		System.out.println(requestSpecBuilder.build().toString());
		System.out.println("END");
		serviceResponse = (Response) given().spec(spec).when().post();
		return serviceResponse;
	}

	private String getServiceBody_CmpgnDefnForCmpgnIDList(CmpgnDefnRequest cmpgnDefnRequest) {
		String json_body = "{\n" + "    \"rqstCmpgnDefs\": { " + "\n" + " \"cmpgnIds\":" + "["
				+ cmpgnDefnRequest.getCampainIdList() + "]\n" + "}\n" + "}";
		return json_body;
	}
}