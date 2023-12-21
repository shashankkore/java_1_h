package com.cvs.crm.cukes.SetCustTables.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.SetCustCarepassEnrollmentService;
import com.cvs.crm.service.SetCustTablesService;
import com.cvs.crm.stubs.SetCustCarepassEnrollmentStub;
import com.cvs.crm.util.JsonUtil;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.text.ParseException;

@Slf4j
public class SetCustTablesHook {

	@Autowired
	SetCustTablesService setCustTablesService;

	@Autowired
	ServiceConfig serviceConfig;

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	private Environment environment;

	/*
	 * * Setup Test Data for SetCust Tables
	 */
	@Before(value = "@SetCustTables")
	public void setup() throws ParseException, InterruptedException {
		String filePath = "./src/test/resources/testdata/setCustomer.json";
//		String testEnvironment = this.environment.getActiveProfiles()[0];
		JSONArray inputDataArray = jsonUtil.readJsonFileGetArray(filePath,
				"main_customer_create_onerecord_each");
		System.out.println("Test data setup using json object: main_customer_create_onerecord_each");
		for (int i = 0; i < inputDataArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) inputDataArray.get(i);
			// setup Test Data (at least one customer id) - refer setCustomer.json
			setCustTablesService.createTestDataForSetCustTablesFromJson(jsonObject);
		}

	}

	/**
	 * Tear Down SetCust-tables after Test
	 */
	@After(value = "@SetCustTables")
	public void teardown() {
		setCustTablesService.deleteTestDataForSetCustTables();
	}
}
