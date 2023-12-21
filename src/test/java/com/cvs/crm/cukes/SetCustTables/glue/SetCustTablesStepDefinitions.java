package com.cvs.crm.cukes.SetCustTables.glue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerOpt;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.TableColumnData;
import com.cvs.crm.model.request.SetCustTablesRequest;
import com.cvs.crm.service.SetCustTablesService;
import com.cvs.crm.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java8.En;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetCustTablesStepDefinitions extends SpringIntegrationTests implements En {

	@Autowired
	SetCustTablesService setCustTablesService;

	@Autowired
	ServiceConfig serviceConfig;

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	private Environment environment;

	@Autowired
	Customer customerExpected;

	@Autowired
	CustomerAddress customerAddressExpected;

	@Autowired
	CustomerEmail customerEmailExpected;

	@Autowired
	CustomerPhone customerPhoneExpected;

	@Autowired
	CustomerOpt customerOptExpected;
	
	SetCustTablesRequest request = new SetCustTablesRequest();
	JSONObject expectedData;
	ObjectMapper mapper = new ObjectMapper();
	JSONArray inputDataArray = null;
	Response response;
	
	
	String testDataFile;
	Integer xtraCardNbr;
	String cardType = "0002";
	String msgSrcCd = "M";
	String userId = "MOBILE_ENT";
	int srcLocCd = 90046;

	public SetCustTablesStepDefinitions() {

		{

			Given("{string}", (String scenario) -> {
			});

			Given("I use my Extra Card {int}", (Integer cardNbr) -> {

				xtraCardNbr = cardNbr;
			});

			Given("I use EC {int} from the channel {string}", (Integer int1, String string) -> {
				xtraCardNbr = int1;
				msgSrcCd = string;
				if (string.toUpperCase().contentEquals("M")) {
					userId = "MOBILE_ENT";
					srcLocCd = 90046;
				} else if (string.toUpperCase().contentEquals("W")) {
					userId = "CVS.COM";
					srcLocCd = 2695;
				} else if (string.toUpperCase().contentEquals("R")) {
					userId = "STORE";
					srcLocCd = 68585;
				} else if (string.toUpperCase().contentEquals("A")) {
					userId = "CALL_CENTER";
					srcLocCd = 68585;
				}

			});

			When("I try to {string} details in the {string} table through SetCust API using the data {string}", (String string, String string2, String string3) -> {
						testDataFile = string3;
						request = buildARequestForGivenFile(testDataFile, string, string2);
						expectedData = setCustTablesService.getTableColumnDataFromDb(request, true, testDataFile);
						setCustTablesService.patchSetCustomer(request, xtraCardNbr, cardType, msgSrcCd,
								userId, srcLocCd);
					});

			Then("I receive a response with success status", () -> {
				setCustTablesService.getServiceResponse().then().log().all().statusCode(200);
			});

			Then("I see the {int} and {string} in the response", (Integer int1, String expEncodedXtraNbr) -> {
				int actualXtraCardNbr = setCustTablesService.getServiceResponse().jsonPath().getInt("xtraCardNbr");
				String actualEncodedXtraCardNbr = setCustTablesService.getServiceResponse().jsonPath().get("encodedXtraCardNbr").toString();
				Assert.assertEquals("The xtra card number is Incorrect", int1.intValue(), actualXtraCardNbr);
				Assert.assertEquals("The encoded xtra card number is Incorrect", expEncodedXtraNbr, actualEncodedXtraCardNbr);
				
			});

			Then("I see that the respective columns in the {string} table updated", (String tableName) -> {
				JSONObject actualData = setCustTablesService.getTableColumnDataFromDb(request, false, testDataFile);
				//expectedData = setCustTablesService.getExpectedData(request);
				TableColumnData actual = mapper.readValue(actualData.toString(), TableColumnData.class);
				TableColumnData expected = mapper.readValue(expectedData.toString(), TableColumnData.class);
				expectedData = setCustTablesService.getExpectedData(expected);
				Assert.assertEquals(expectedData, actualData);
			});
			
			When("I try to {string} details from the {string} table through SetCust API", (String string, String string2) -> {
				request = buildARequestForGivenFile("", string, string2);
				setCustTablesService.patchSetCustomer(request, xtraCardNbr, cardType, msgSrcCd,
						userId, srcLocCd);
			});
			
			Then("I see a new row inserted into the {string} table", (String string) -> {
				int actualCount = setCustTablesService.getRowCountOfGivenTable(string);
				Assert.assertEquals(2, actualCount);
			});


			// BadRequest
			Then("I see a Bad Request response with the {string}", (String string) -> {
				setCustTablesService.getServiceResponse().then().log().all().statusCode(400);
				String errorActual = setCustTablesService.getServiceResponse().jsonPath().getString("errorMsg");
				int errorCode = setCustTablesService.getServiceResponse().jsonPath().getInt("errorCd");
				Assert.assertEquals("Invalid Error description", string.toLowerCase(), errorActual.toLowerCase());
			});

		}
	}

	public SetCustTablesRequest buildARequestForGivenFile(String dataObjectName, String action, String table) {

		String filePath = "./src/test/resources/testdata/setCustomer.json";
		JSONObject jsonObject = new JSONObject();
//		String testEnvironment = this.environment.getActiveProfiles()[0];
		inputDataArray = jsonUtil.readJsonFileGetArray(filePath, dataObjectName);
		if (!(action.toLowerCase().contains("delete"))) {
			for (int i = 0; i < inputDataArray.size(); i++) {
				jsonObject = (JSONObject) inputDataArray.get(i);
				try {
					request = setCustTablesService.generateARequestBody(jsonObject, action, table);
				} catch (Exception e) {
					e.getMessage();
					log.error("Unable to create jsonBody " + dataObjectName);
				}
			}
			/*
			if(table.equalsIgnoreCase("customer"))
				customerExpected = setCustTablesService.setCustomerObject(request, true);
			else if(table.equalsIgnoreCase("customer_address"))
				customerAddressExpected = setCustTablesService.setCustomerAddressObject(request, true);
			else if(table.equalsIgnoreCase("customer_email"))
				customerEmailExpected = setCustTablesService.setCustomerEmailObject(request, true);
			else if(table.equalsIgnoreCase("customer_opt"))
				customerOptExpected = setCustTablesService.setCustomerOptObject(request, true);
			else if(table.equalsIgnoreCase("customer_phone"))
				customerPhoneExpected = setCustTablesService.setCustomerPhoneObject(request, true); */
		}
		else {
			request = setCustTablesService.generateARequestBody(jsonObject, action, table);
		}
		return request;
	}
}
