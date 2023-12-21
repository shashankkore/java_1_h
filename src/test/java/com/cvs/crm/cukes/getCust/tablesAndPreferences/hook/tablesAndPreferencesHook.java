package com.cvs.crm.cukes.getCust.tablesAndPreferences.hook;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.GetCustTablesAndPreferencesService;
import com.cvs.crm.stubs.GetCustTablesandPreferencesStub;
import com.cvs.crm.util.JsonUtil;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class tablesAndPreferencesHook {
    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    TestDataUtil testDataUtil;

    String filePath = "./src/test/resources/testdata/createExtraCard.json";
    List<String> testData_XC = Arrays.asList("XC_99900", "XC_99901");
    /**
     *  Setup Test Data for GetCust Tables and Preferences
     */
    @Before(value = "@tablesAndPrefs")
    public void setup() throws ParseException, InterruptedException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // setup Stubs
//            getCustTablesAndPreferencesService.createStubData();
        }
        else {
            Iterator<String> itr = testData_XC.iterator();
            while (itr.hasNext()){
                String test_XC = itr.next();
                JSONArray inputDataArray = jsonUtil.readJsonFileGetArray(filePath, test_XC);
                log.info("\nTest data setup using json object: {}", test_XC);

                for (int i = 0; i < inputDataArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) inputDataArray.get(i);
                    //  Get XTRA_CARD to delete from tables before test
                    int xtra_card = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
                    List<Integer> xtraCardNbrList = new ArrayList<Integer>();
                    xtraCardNbrList.add(xtra_card);
                    //  Get CUST_ID to delete from tables before test
                    int cust_id = Integer.parseInt(jsonObject.get("CUST_ID").toString());
                    List<Integer> custIdList = new ArrayList<Integer>();
                    custIdList.add(cust_id);

                    testDataUtil.deleteTestData(xtraCardNbrList, custIdList);
                    testDataUtil.createTestData(jsonObject);
                }
            }
        }
    }

    /**
     * Tear Down GetCust Tables and Preferences test data
     */
    @After(value = "@tablesAndPrefs")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // Tear down Stubs
//            pharmacyHealthRewardsStub.deletePharmacyHealthRewardsStubData();
        } else {
            // Tear down Test Data
            Iterator<String> itr = testData_XC.iterator();
            while (itr.hasNext()){
                String test_XC = itr.next();
                JSONArray inputDataArray = jsonUtil.readJsonFileGetArray(filePath, test_XC);
                log.info("\nTest data setup using json object: {}", test_XC);

                for (int i = 0; i < inputDataArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) inputDataArray.get(i);
                    //  Get XTRA_CARD to delete from tables before test
                    int xtra_card = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
                    List<Integer> xtraCardNbrList = new ArrayList<Integer>();
                    xtraCardNbrList.add(xtra_card);
                    //  Get CUST_ID to delete from tables before test
                    int cust_id = Integer.parseInt(jsonObject.get("CUST_ID").toString());
                    List<Integer> custIdList = new ArrayList<Integer>();
                    custIdList.add(cust_id);

                    testDataUtil.deleteTestData(xtraCardNbrList, custIdList);
                }
            }
        }
    }
}
