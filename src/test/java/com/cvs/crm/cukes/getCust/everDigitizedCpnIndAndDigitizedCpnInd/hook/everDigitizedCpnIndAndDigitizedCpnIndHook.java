package com.cvs.crm.cukes.getCust.everDigitizedCpnIndAndDigitizedCpnInd.hook;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.util.TestDataUtil;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Slf4j
public class everDigitizedCpnIndAndDigitizedCpnIndHook {

    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    TestDataUtil testDataUtil;
    String testDataFile = "createExtraCard";
    List<String> testData_XC = Arrays.asList("XC_99901");

    @Before(value = "@GetCust_EverDigitizedCpnInd")
    public void setup() throws ParseException, InterruptedException, SQLException {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
            // Setup Stubs
        } else {
            Iterator<String> itr = testData_XC.iterator();
            while (itr.hasNext()) {
                String test_XC = itr.next();
                JSONObject jsonObject = testDataUtil.extractTestData(testDataUtil.extractTestData(testDataFile, test_XC));

                //  Get XTRA_CARD to delete from tables before test
                List<Integer> xtraCardNbrList = new ArrayList<Integer>();
                xtraCardNbrList.add(Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString()));

                //  Get CUST_ID to delete from tables before test
                List<Integer> custIdList = new ArrayList<Integer>();
                custIdList.add(Integer.parseInt(jsonObject.get("CUST_ID").toString()));

                testDataUtil.deleteTestData(xtraCardNbrList, custIdList);
                testDataUtil.createTestData(jsonObject);

            }
        }
    }

    @After(value = "@GetCust_EverDigitizedCpnInd")
    public void teardown() {
        if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {

            // Teardown Stubs
        } else {
            Iterator<String> itr = testData_XC.iterator();
            while (itr.hasNext()) {
                String test_XC = itr.next();
                JSONObject jsonObject = testDataUtil.extractTestData(testDataUtil.extractTestData(testDataFile, test_XC));

                //  Get XTRA_CARD to delete from tables before test
                List<Integer> xtraCardNbrList = new ArrayList<Integer>();
                xtraCardNbrList.add(Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString()));

                //  Get CUST_ID to delete from tables before test
                List<Integer> custIdList = new ArrayList<Integer>();
                custIdList.add(Integer.parseInt(jsonObject.get("CUST_ID").toString()));

                testDataUtil.deleteTestData(xtraCardNbrList, custIdList);
            }

        }
    }
}
