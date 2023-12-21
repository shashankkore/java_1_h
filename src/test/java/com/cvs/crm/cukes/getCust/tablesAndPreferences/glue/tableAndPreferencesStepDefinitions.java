package com.cvs.crm.cukes.getCust.tablesAndPreferences.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.request.HREnrollRequest;
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.service.GetCustTablesAndPreferencesService;
import com.cvs.crm.service.PharmacyHealthRewardsService;
import com.cvs.crm.service.SetCustCarepassEnrollmentService2;
import com.cvs.crm.util.TestDataUtil;
import com.sun.xml.bind.v2.TODO;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class tableAndPreferencesStepDefinitions  extends SpringIntegrationTests implements En {

    @Autowired
    GetCustTablesAndPreferencesService getCustTablesAndPreferencesService;
    @Autowired
    PharmacyHealthRewardsService pharmacyHealthRewardsService;
    @Autowired
    SetCustCarepassEnrollmentService2 setCustCarepassEnrollmentService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    TestDataUtil testDataUtil;

    String requestParam = "";
    Integer xtraCardNbr = null;
    JSONArray jsonArray = null;
    JSONObject jsonObject;

    HREnrollRequest hrEnrollRequest = new HREnrollRequest();
    SetCustRequest setCustRequest = new SetCustRequest();

    public tableAndPreferencesStepDefinitions () {

        GetCustRequest getCustRequest = new GetCustRequest();
//        AtomicReference<GetCustResponse> getCustResponse = new AtomicReference<>(new GetCustResponse());

        Given("I have my user with xtraCard {string} from {string} testDataFile", (String testDataObjectName, String testDataFile) -> {
            jsonArray = testDataUtil.extractTestData(testDataFile, testDataObjectName);
        });

        And("I have card type {string} at {string}",(String card_type, String channel1) -> {
            jsonObject = testDataUtil.extractTestData(jsonArray);
            xtraCardNbr = Integer.parseInt(jsonObject.get("XTRA_CARD_NBR").toString());
            getCustRequest.setSearchCardNbr(xtraCardNbr);
            getCustRequest.setSearchCardType(card_type);
            getCustRequest.setChannel(channel1);
            getCustRequest.setVersion("1.2");
        });

        Given("I use my extra card {string} and card type {string} at {string}", (String xtra_card_nbr, String card_type, String channel) -> {
            getCustRequest.setSearchCardType(card_type);
            if (xtra_card_nbr.length() != 0) {
                getCustRequest.setSearchCardNbr(Integer.parseInt(xtra_card_nbr));
                xtraCardNbr = Integer.parseInt(xtra_card_nbr);
                getCustRequest.setChannel(channel);
                getCustRequest.setVersion("1.2");
            }
        });

        // Filter with request parameter
        Given("I filter with {string} in the request", (String search_term) -> {
            requestParam = search_term;
        });

        // Get Response for a getCust POST request
        When("I get a response from getCust API", () -> {
            getCustTablesAndPreferencesService.getServiceResponse(getCustRequest);
//            getCustResponse.set(getCustTablesAndPreferencesService.getServiceResponse(getCustRequest).as(GetCustResponse.class));
        });

        // Verify Response with expected statusCd
        Then("API returns a response with status code as {int}", (Integer statusCd) -> {
            getCustTablesAndPreferencesService.getServiceResponse().then().statusCode(statusCd);
//            System.out.println("CHECK RESP :: >> " + getCustResponse.get().getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCardResponse().getXtraCardNbr());
        });

        // Verify the Response of a Node
        And("The response of the node {string} should be {string}", (String node, String resp) -> {
            Assert.assertTrue("The response contain " + node + " node.",
                    getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp").contains(node));
            Assert.assertTrue("Expected to have extra card digitized = " + resp + " - FAILED",
                    getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp.xtraCard." + node)
                            .equalsIgnoreCase(resp));
        });

        // Verify the Response of a subNode
        And("The response should have {string} at {string} as {string}", (String subNode, String node, String resp) -> {
            if (resp.equalsIgnoreCase("default")){
                resp = xtraCardNbr.toString();
            }
            Assert.assertTrue("The response doesn't contain the node - " + subNode + "\n ACTUAL :: " +
                            getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp"),
                    getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp").contains(subNode));
            Assert.assertTrue("The response doesn't match for - "+ subNode + "\n ACTUAL :: " +
                            getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp." + node +"."+ subNode) + "\n EXPECTED :: " + resp,
                    getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp." + node +"."+ subNode)
                            .equalsIgnoreCase(resp));
        });

        // Verify the Response of a SubNode enroll status
        And("The response should have {string} at {string} has {string} status {string}", (String subNode, String node, String enrolled, String resp) -> {
            Assert.assertTrue("The response contain " + node + " > " +subNode + " node.",
                    getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp").contains(subNode));
            Assert.assertTrue("Response doesn't match.\n"+ node + " > " + subNode + " > "+ enrolled +" ::" +" \nACTUAL :: " + getCustTablesAndPreferencesService
                            .getServiceResponse_Node("cusInfResp." + node +"."+ subNode + "." + enrolled) + "\nEXPECTED :: " + resp,
                    getCustTablesAndPreferencesService.getServiceResponse_Node("cusInfResp." + node +"."+ subNode + "." + enrolled)
                            .equalsIgnoreCase(resp));
        });

        // Verify the CUSTOMER Table Response
        Then("I get Customer table information as", (DataTable dt) -> {
            List<Map<String, String>> exp_data = dt.asMaps(String.class, String.class);
            List<Map<String, String>> actual_data = getCustTablesAndPreferencesService.getServiceResponse_node_from_Table("customer");
            Assert.assertTrue("firstName doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "first_name") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "first_name"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "first_name").equalsIgnoreCase(
                    getCustTablesAndPreferencesService.get_value(exp_data, "first_name")));
            Assert.assertTrue("lastName doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "last_name") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "last_name"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "last_name").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "last_name")));
            Assert.assertTrue("middleInitialTxt doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "middle_initial_txt") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "middle_initial_txt"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "middle_initial_txt").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "middle_initial_txt")));
            Assert.assertTrue("gndrCd doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "gndr_cd") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "gndr_cd"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "gndr_cd").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "gndr_cd")));

//            String dateInString = getCustTablesAndPreferencesService.get_value(actual_data, "birth_dt");
//            LocalDate date = LocalDate.parse(dateInString, DateTimeFormatter.BASIC_ISO_DATE);
//            Assert.assertTrue("birthDt doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "birth_dt") +
//                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "birth_dt"),
//                    String.valueOf(date.getDayOfMonth()).equalsIgnoreCase(
//                            getCustTablesAndPreferencesService.get_value(exp_data, "birth_dt")));
        });

        // Verify the CUSTOMER_EMAIL Table Response
        Then("I get Customer Email table information as", (DataTable dt) -> {
            List<Map<String, String>> exp_data = dt.asMaps(String.class, String.class);
            List<Map<String, String>> actual_data = getCustTablesAndPreferencesService.getServiceResponse_node_from_Table("customer_email");
            Assert.assertTrue("emailAddrTypeCd doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "email_addr_type_cd") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "email_addr_type_cd"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "email_addr_type_cd").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "email_addr_type_cd")));
            Assert.assertTrue("emailAddrTxt doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "email_addr_txt") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "email_addr_txt"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "email_addr_txt").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "email_addr_txt")));
        });

        // Verify the CUSTOMER_PHONE Table Response
        Then("I get Customer Phone table information as", (DataTable dt) -> {
            List<Map<String, String>> exp_data = dt.asMaps(String.class, String.class);
            List<Map<String, String>> actual_data = getCustTablesAndPreferencesService.getServiceResponse_node_from_Table("customer_phone");
            Assert.assertTrue("PhoneTypeCd doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "phone_type_cd") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "phone_type_cd"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "phone_type_cd").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "phone_type_cd")));
            Assert.assertTrue("PhoneSfxNbr doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "phone_sfx_nbr") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "phone_sfx_nbr"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "phone_sfx_nbr").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "phone_sfx_nbr")));
            Assert.assertTrue("PhonePfxNbr doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "phone_pfx_nbr") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "phone_pfx_nbr"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "phone_pfx_nbr").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "phone_pfx_nbr")));
            Assert.assertTrue("PhoneAreaCdNbr doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "phone_area_cd_nbr") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "phone_area_cd_nbr"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "phone_area_cd_nbr").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "phone_area_cd_nbr")));
        });

        // Verify the CUSTOMER_ADDRESS Table Response
        Then("I get Customer Address table information as", (DataTable dt) -> {
            List<Map<String, String>> exp_data = dt.asMaps(String.class, String.class);
            List<Map<String, String>> actual_data = getCustTablesAndPreferencesService.getServiceResponse_node_from_Table("customer_address");
            Assert.assertTrue("addr1_txt doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "addr1_txt") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "addr1_txt"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "addr1_txt").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "addr1_txt")));
            Assert.assertTrue("addr2_txt doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "addr2_txt") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "addr2_txt"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "addr2_txt").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "addr2_txt")));
            Assert.assertTrue("city_name doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "city_name") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "city_name"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "city_name").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "city_name")));
            Assert.assertTrue("st_cd doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "st_cd") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "st_cd"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "st_cd").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "st_cd")));
            Assert.assertTrue("zip_cd doesn't match. \nACTUAL :: " + getCustTablesAndPreferencesService.get_value(actual_data, "zip_cd") +
                            "\nEXPECTED :: " + getCustTablesAndPreferencesService.get_value(exp_data, "zip_cd"),
                    getCustTablesAndPreferencesService.get_value(actual_data, "zip_cd").equalsIgnoreCase(
                            getCustTablesAndPreferencesService.get_value(exp_data, "zip_cd")));
        });

//        Enroll into PHR
        Given("I enrolled into PHR", () -> {
            hrEnrollRequest.setSearchCardNbr(getCustRequest.getSearchCardNbr());
            hrEnrollRequest.setSearchCardType(getCustRequest.getSearchCardType());
            hrEnrollRequest.setActionCode("E");
            hrEnrollRequest.setIdNumber("87654347");
            pharmacyHealthRewardsService.phrEnroll(hrEnrollRequest);
            pharmacyHealthRewardsService.getServiceResponse().then().statusCode(201);
        });

//      SetCust Actions - optInEmail, optOutEmail, optInMail, optOutMail, optInDigitalReceipt, optOutDigitalReceipt, carePass_Enroll_Monthly, carePass_UnEnroll_Monthly
        Given("I {string} via setCust", (String setCust_action) -> {
            setCustRequest.setSearchCardNbr(getCustRequest.getSearchCardNbr());
            setCustRequest.setSearchCardType(getCustRequest.getSearchCardType());
            setCustRequest.setVersion(getCustRequest.getVersion());
            setCustRequest.setChannel(getCustRequest.getChannel());
            try {
                setCustCarepassEnrollmentService.setCust(setCustRequest, setCust_action);
            }catch (Exception ex) {
                log.info("Exception - " + ex);
                throw ex;
            }
        });
    }
}
