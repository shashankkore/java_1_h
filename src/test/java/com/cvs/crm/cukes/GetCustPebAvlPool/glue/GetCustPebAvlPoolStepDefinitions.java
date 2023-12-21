package com.cvs.crm.cukes.GetCustPebAvlPool.glue;

import com.cvs.crm.SpringIntegrationTests;
import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.CarepassEnrollFormHist;
import com.cvs.crm.model.data.CarepassMemberSmry;
import com.cvs.crm.model.data.CarepassMemberStatusHist;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.response.GetCustCusInfRespTablesResponse;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.service.GetCustTablesnCpnsService;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.DateUtil;
import com.cvs.crm.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

@Slf4j
public class GetCustPebAvlPoolStepDefinitions extends SpringIntegrationTests implements En {

    Integer xtraCardNum = null;
    String version = null;
    @Autowired
    GetCustTablesnCpnsService getCustTablesnCpnsService;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    CarePassDateUtil carePassDateUtil;

    @Autowired
    XtraCardActiveCoupon xtraCardActiveCoupon;

    @Autowired
    CarepassMemberSmry carepassMemberSmry;

    @Autowired
    CarepassEnrollFormHist carepassEnrollFormHist;

    @Autowired
    CarepassMemberStatusHist carepassMemberStatusHist;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    private Environment environment;

    @Autowired
    JsonUtil jsonUtil;

/*
    @Autowired
    GetCustCusInfRespCpnsResponse getCustCusInfRespCpnsResponse; */

    String apiRequestValue = "";

    public GetCustPebAvlPoolStepDefinitions() {

        {
            GetCustRequest request = new GetCustRequest();

            Given("{string}", (String scenario) -> {
            });

            Given("I use my Extra Card {string}", (String string) -> {
                request.setSearchCardType("0002");
                if (string != "")
                    request.setSearchCardNbr(Integer.parseInt(string));
                xtraCardNum = Integer.parseInt(string);
            });

            Given("I use my card number {string} and card type {string}", (String string, String string2) -> {
                request.setSearchCardType(string2);
                if (string.length() != 0) {
                    request.setSearchCardNbr(Integer.parseInt(string));
                    xtraCardNum = Integer.parseInt(string);
                }
            });


            Given("I filter for  {string} {string} in the request", (String string, String string2) -> {
                apiRequestValue = string2;
            });

            When("I use the get customer API for channel {string}", (String string) -> {
                request.setVersion("1.1");
                request.setChannel(string);
                getCustTablesnCpnsService.viewGetCustTablesnPreferencesOne(request, apiRequestValue, string);

            });

            Then("I receive a response with success status", () -> {
                getCustTablesnCpnsService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I receive a response with bad request status", () -> {
                getCustTablesnCpnsService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I receive a response with bad request status 500", () -> {
                getCustTablesnCpnsService.getServiceResponse().then().log().all().statusCode(500);
            });

            Then("The response should match with database tables data {string}", (String string) -> {
                System.out.println(getCustTablesnCpnsService.getServiceResponse());
                compareTheOutput(string, getCustTablesnCpnsService.getServiceResponse().prettyPrint());
            });

            Then("The response should be empty", () -> {
                String responseCpns = getCustTablesnCpnsService.getServiceResponse().jsonPath().getString("cusInfResp");
                Assert.assertTrue("The response is NOT empty", !responseCpns.contains("cpns"));
            });

            Then("The response should have status code {int} with message {string}", (Integer int1, String string) -> {
                String errorActual = getCustTablesnCpnsService.getServiceResponse().jsonPath().getString("errorMsg");
                int errorCode = getCustTablesnCpnsService.getServiceResponse().jsonPath().getInt("errorCd");
                Assert.assertEquals("Invalid Error code", int1.intValue(), errorCode);
                Assert.assertEquals("Invalid Error description", string, errorActual);
            });


            Then("I use the get customer API {string} for channel {string}", (String preference, String channel) -> {
                request.setVersion("1.1");
                getCustTablesnCpnsService.viewGetCustTablesnPreferencesOne(request, preference, channel);
            });

            Then("I see the GetCust Response", () -> {
                getCustTablesnCpnsService.getServiceResponse().then().log().all().statusCode(200);
            });

            Then("I do not see the GetCust Response", () -> {
                getCustTablesnCpnsService.getServiceResponse().then().log().all().statusCode(400);
            });

            Then("I see the xtra card nbr as {int}", (Integer xtra_card_nbr) -> {
                GetCustResponse getcustResponse = getCustTablesnCpnsService.getServiceResponse().as(GetCustResponse.class);
                Assert.assertEquals(xtra_card_nbr, getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespXtraCardResponse().getXtraCardNbr());
            });


            When("I want to see tables and preferences", () -> {
                request.setVersion("1.1");
                getCustTablesnCpnsService.viewGetCustTablesnPreferences(request);
            });

            When("I want to see tables and preferences for", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                String ver = String.valueOf((list.get(0).get("version")));
                request.setVersion(ver);
                version = ver;
                getCustTablesnCpnsService.viewGetCustTablesnPreferences(request);
            });


            Then("I get Customer Table Information as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String expectedFirstName = String.valueOf((list.get(i).get("first_name")));
                    String expectedLastName = String.valueOf(list.get(i).get("last_name"));
                    String expectedMiddleInitialTxt = String.valueOf(list.get(i).get("middle_initial_txt"));
                    String expectedgndrCd = String.valueOf(list.get(i).get("gndr_cd"));
                    Integer birth_dt = Integer.valueOf(list.get(i).get("birth_dt"));
                    String expectedbirthDt = (carePassDateUtil.carePassExpireTswtz(birth_dt)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(birth_dt)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(birth_dt)).substring(8, 10);
                    GetCustResponse getcustResponse = getCustTablesnCpnsService.getServiceResponse().as(GetCustResponse.class);
                    String actualFirstName = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).get("first_name");
                    String actualLastName = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).get("last_name");
                    String actualMiddleIniTxt = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).get("middle_initial_txt");
                    String actualGndrCd = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).get("gndr_cd");
                    String actualbirthDt = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).get("birth_dt");
                    Assert.assertEquals(expectedFirstName, actualFirstName);
                    Assert.assertEquals(expectedLastName, actualLastName);
                    Assert.assertEquals(expectedMiddleInitialTxt, actualMiddleIniTxt);
                    Assert.assertEquals(expectedgndrCd, actualGndrCd);
                    Assert.assertEquals(expectedbirthDt, actualbirthDt);
                }
            });

            Then("I get Customer Phone Table Information as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);

                for (int i = 0; i < list.size(); i++) {
                    String expectedPhoneAreaCdNbr = String.valueOf((list.get(i).get("phone_area_cd_nbr")));
                    Integer expectedPhonePfxNbr = Integer.valueOf(list.get(i).get("phone_pfx_nbr"));
                    String expectedPhoneTypeCd = String.valueOf(list.get(i).get("phone_type_cd"));
                    String expectedPhoneSfxNbr = String.valueOf(list.get(i).get("phone_sfx_nbr"));
                    GetCustResponse getcustResponse = getCustTablesnCpnsService.getServiceResponse().as(GetCustResponse.class);
                    String actualPhoneAreaCdNbr = String.valueOf(getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(2).getRow().get(0).get("phone_area_cd_nbr"));
                    Integer actualPhonePfxNbr = (Integer) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(2).getRow().get(0).get("phone_pfx_nbr");
                    String actualPhoneTypeCd = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(2).getRow().get(0).get("phone_type_cd");
                    String actualPhoneSfxNbr = String.valueOf(getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(2).getRow().get(0).get("phone_sfx_nbr"));
                    Assert.assertEquals(expectedPhoneAreaCdNbr, actualPhoneAreaCdNbr);
                    Assert.assertEquals(expectedPhonePfxNbr, actualPhonePfxNbr);
                    Assert.assertEquals(expectedPhoneTypeCd, actualPhoneTypeCd);
                    Assert.assertEquals(expectedPhoneSfxNbr, actualPhoneSfxNbr);
                }
            });

            Then("I get Customer Email Table Information as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String expectedEmailAddrTxt = String.valueOf((list.get(i).get("email_addr_txt")));
                    String expectedEmailAddrTypeCd = String.valueOf(list.get(i).get("email_addr_type_cd"));
                    GetCustResponse getcustResponse = getCustTablesnCpnsService.getServiceResponse().as(GetCustResponse.class);
                    List<GetCustCusInfRespTablesResponse> getGetCustCusInfRespTablesResponseList = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList();
                    String actualEmailAddrTxt = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(1).getRow().get(0).get("email_addr_txt");
                    String actualEmailAddrTypeCd = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(1).getRow().get(0).get("email_addr_type_cd");
                    Assert.assertEquals(expectedEmailAddrTxt, actualEmailAddrTxt);
                    Assert.assertEquals(expectedEmailAddrTypeCd, expectedEmailAddrTypeCd);
                }
            });


            Then("I get Customer Email Table Information with email as <email_addr_txt>", (DataTable dt) -> {
                if ("stub".equalsIgnoreCase(serviceConfig.getActiveProfile())) {
                } else {
                    List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                    for (int i = 0; i < list.size(); i++) {
                        String expectedEmailAddrTxt = String.valueOf((list.get(i).get("email_addr_txt")));
                        GetCustResponse getcustResponse = getCustTablesnCpnsService.getServiceResponse().as(GetCustResponse.class);
                        List<GetCustCusInfRespTablesResponse> getGetCustCusInfRespTablesResponseList = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList();
                        String actualEmailAddrTxt = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(0).getRow().get(0).get("email_addr_txt");
                        Assert.assertEquals(expectedEmailAddrTxt, actualEmailAddrTxt);

                    }
                }
            });

            Then("I get Customer Address Table Information as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    String expectedAddr1Txt = String.valueOf((list.get(i).get("addr1_txt")));
                    String expectedAddr2Txt = String.valueOf(list.get(i).get("addr2_txt"));
                    String expectedCityName = String.valueOf(list.get(i).get("city_name"));
                    String expectedStCd = String.valueOf(list.get(i).get("st_cd"));
                    String expectedZipCd = String.valueOf(list.get(i).get("zip_cd"));
                    GetCustResponse getcustResponse = getCustTablesnCpnsService.getServiceResponse().as(GetCustResponse.class);
                    List<GetCustCusInfRespTablesResponse> getGetCustCusInfRespTablesResponseList = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList();
                    String actualAddr1Txt = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(3).getRow().get(0).get("addr1_txt");
                    String actualAddr2Txt = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(3).getRow().get(0).get("addr2_txt");
                    String actualCityName = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(3).getRow().get(0).get("city_name");
                    String actualStCd = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(3).getRow().get(0).get("st_cd");
                    String actualZipCd = String.valueOf(getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(3).getRow().get(0).get("zip_cd"));

                    Assert.assertEquals(expectedAddr1Txt, actualAddr1Txt);
                    Assert.assertEquals(expectedAddr2Txt, actualAddr2Txt);
                    Assert.assertEquals(expectedCityName, actualCityName);
                    Assert.assertEquals(expectedStCd, actualStCd);
                    Assert.assertEquals(expectedZipCd, actualZipCd);
                }
            });

            Then("I get xtra_card_child Table Information as", (DataTable dt) -> {
                List<Map<String, String>> list = dt.asMaps(String.class, String.class);
                for (int i = 0; i < list.size(); i++) {
                    Integer expectedBirthdayDt = Integer.valueOf((list.get(i).get("birthday_dt")));
                    String expectedbirthDt = (carePassDateUtil.carePassExpireTswtz(expectedBirthdayDt)).substring(0, 4) + (carePassDateUtil.carePassExpireTswtz(expectedBirthdayDt)).substring(5, 7) + (carePassDateUtil.carePassExpireTswtz(expectedBirthdayDt)).substring(8, 10);
                    GetCustResponse getcustResponse = getCustTablesnCpnsService.getServiceResponse().as(GetCustResponse.class);
                    List<GetCustCusInfRespTablesResponse> getGetCustCusInfRespTablesResponseList = getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList();
                    int tableno = getGetCustCusInfRespTablesResponseList.size();
                    String actualBirthdayDt = (String) getcustResponse.getGetCustCusInfRespResponse().getGetCustCusInfRespTablesResponseList().get(tableno - 1).getRow().get(0).get("birthday_dt");
                    Assert.assertEquals(expectedbirthDt, actualBirthdayDt);
                }
            });

        }

    }

    public boolean compareTheOutput(String expected, String actual) {
        ObjectMapper mapper = new ObjectMapper();
        String filePath = "./src/test/resources/testdata/PEBAvlblPool.json";
//        String testEnvironment = this.environment.getActiveProfiles()[0];
        JSONObject expectedJo = jsonUtil.readJsonFileGetObject(filePath, expected);
        JsonNode tree1=null;
        JsonNode tree2=null;

        try {
            tree1 = mapper.readTree(expectedJo.toString());
            tree2 = mapper.readTree(actual);
        } catch (JsonProcessingException jpe) {
            jpe.getMessage();
        }

        return tree1.equals(tree2);
    }
}

