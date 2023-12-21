package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.GetCustRequest;
import com.cvs.crm.model.response.GetCustResponse;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class GetCustTablesAndPreferencesService {
    private Response serviceResponse;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    Customer customer;

    @Autowired
    CustomerAddress customerAddress;

    @Autowired
    CustomerEmail customerEmail;

    @Autowired
    CustomerPhone customerPhone;

    @Autowired
    XtraCardCustomer xtraCardCustomer;

    @Autowired
    MaskedXtraCard maskedXtraCard;

    private RequestSpecBuilder getService_baseUri(GetCustRequest getCustRequest) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri(serviceConfig.getGetcustUrl());
        String msgSrcCd;
        int srcLocCd;
        String userId;

        if (getCustRequest.getChannel().equalsIgnoreCase("M")) {
            msgSrcCd = "M";
            srcLocCd = 90042;
            userId = "MOBILE_ENT";
        } else if (getCustRequest.getChannel().equalsIgnoreCase(("W"))) {
            msgSrcCd = "W";
            srcLocCd = 2695;
            userId = "CVS.COM";
        } else {
            msgSrcCd = "R";
            srcLocCd = 68585;
            userId = "STORE";
        }
        requestSpecBuilder.setBasePath("api/v" + getCustRequest.getVersion() +
                "/customers/{search_card_type},{search_card_nbr}")
                .addPathParam("search_card_type", getCustRequest.getSearchCardType())
                .addPathParam("search_card_nbr", getCustRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("src_loc_cd", srcLocCd)
                .addQueryParam("user_id", userId);
        return requestSpecBuilder;
    }

    private String getService_body() {
        String json_body = "{\n" +
                "    \"cusInfReq\": {\n" +
                "        \"xtraCard\": [\n" +
                "            \"xtraCardNbr\",\n" +
                "            \"totYtdSaveAmt\",\n" +
                "            \"cardLastScanDt\",\n" +
                "            \"cardMbrDt\",\n" +
                "            \"homeStoreNbr\",\n" +
                "            \"xtraCardCipherTxt\"\n" +
                "        ],\n" +
                "        \"xtraCare\": [\n" +
                "            \"mktgTypeBenefits\",\n" +
                "            \"pts\",\n" +
                "            \"cpns\",\n" +
                "            \"mfrCpnAvailPool\",\n" +
                "            \"pebAvailPool\",\n" +
                "            \"hrMembers\",\n" +
                "            \"hrEvtDtl\",\n" +
                "            \"carePassCpns\"\n" +
                "        ],\n" +
                "        \"xtraCarePrefs\": [\n" +
                "            \"optInEc\",\n" +
                "            \"optInEmail\",\n" +
                "            \"beautyClub\",\n" +
                "            \"diabeticClub\",\n" +
                "            \"phr\",\n" +
                "            \"carePass\",\n" +
                "            \"paperlessCpns\",\n" +
                "            \"digitalReceipt\"\n" +
                "        ],\n" +
                "        \"pushNotifs\": {\n" +
                "            \"beaconLocId\": 123,\n" +
                "            \"cdcProfileId\": \"123\",\n" +
                "            \"pushNotifUserId\": \"C2Yw7Qw76:UfjevECmlvvHHtC8X:V1\",\n" +
                "            \"userIdType\": \"xtify\",\n" +
                "            \"deviceMetaData\": {\n" +
                "                \"deviceTypeCd\": \"IF\",\n" +
                "                \"deviceVerCd\": \"11.4.1\",\n" +
                "                \"deviceUniqIdTypeCd\": \"VendorID\",\n" +
                "                \"deviceUniqId\": \"EBAB46DD-7D71-4D80-B1E1-CD16F7C94FD6\",\n" +
                "                \"appVer\": \"4.7.1\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"tables\": [\n" +
                "            {\n" +
                "                \"tableName\": \"customer\",\n" +
                "                \"colNames\": [\n" +
                "                    \"first_name\",\n" +
                "                    \"last_name\",\n" +
                "                    \"middle_initial_txt\",\n" +
                "                    \"birth_dt\",\n" +
                "                    \"gndr_cd\"\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"customer_email\",\n" +
                "                \"colNames\": [\n" +
                "                    \"email_addr_txt\",\n" +
                "                    \"email_addr_type_cd\"\n" +
                "                ],\n" +
                "                \"criteria\": {\n" +
                "                    \"colName\": \"email_pref_seq_nbr\",\n" +
                "                    \"operation\": \"=\",\n" +
                "                    \"value\": \"1\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"customer_phone\",\n" +
                "                \"colNames\": [\n" +
                "                    \"phone_area_cd_nbr\",\n" +
                "                    \"phone_pfx_nbr\",\n" +
                "                    \"phone_type_cd\"\n" +
                "                ],\n" +
                "                \"functionName\": \"LPAD(to_char(phone_sfx_nbr),4,'0')PHONE_SFX_NBR\",\n" +
                "                \"criteria\": {\n" +
                "                    \"colName\": \"phone_pref_seq_nbr\",\n" +
                "                    \"operation\": \"=\",\n" +
                "                    \"value\": \"1\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"customer_address\",\n" +
                "                \"colNames\": [\n" +
                "                    \"addr1_txt\",\n" +
                "                    \"addr2_txt\",\n" +
                "                    \"city_name\",\n" +
                "                    \"st_cd\",\n" +
                "                    \"zip_cd\"\n" +
                "                ],\n" +
                "                \"criteria\": {\n" +
                "                    \"colName\": \"addr_pref_seq_nbr\",\n" +
                "                    \"operation\": \"=\",\n" +
                "                    \"value\": \"1\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"xtra_card_child\",\n" +
                "                \"colNames\": [\n" +
                "                    \"birthday_dt\"\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"tableName\": \"xtra_card_analytic_event\",\n" +
                "                \"colNames\": [\n" +
                "                    \"analytic_event_type_cd\",\n" +
                "                    \"cpn_seq_nbr\",\n" +
                "                    \"analytic_event_ts\"\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        return json_body;
    }

    public Response getServiceResponse(GetCustRequest getCustRequest) {
        RequestSpecBuilder requestSpecBuilder = getService_baseUri(getCustRequest);
        RequestSpecification requestSpecification = requestSpecBuilder.build();
        serviceResponse = given().spec(requestSpecification).contentType("application/json")
                .body(getService_body()).log().all().post();
        log.info("GetCust Response :\n");
        serviceResponse.prettyPrint();
        return serviceResponse;
    }

    public String getServiceResponse_Node(String node_fullpath) {
        return getServiceResponse().jsonPath().getString(node_fullpath);
    }
    public List<String> getServiceResponse_List_of_Nodes(String node_fullpath) {
        JsonPath jsonPath = new JsonPath(getServiceResponse().asString());
        return jsonPath.getList(node_fullpath);
    }
    public List<Map<String, String>> getServiceResponse_table_data(String table_name){
        JsonPath jsonFile = new JsonPath(getServiceResponse().asString());
        List<Map<String, String>> list = null;
        for (int i = 0; i < jsonFile.getInt( "cusInfResp.tables.tableName.size()"); i++) {
            if (jsonFile.getString("cusInfResp.tables.tableName[" + i + "]").equalsIgnoreCase(table_name)) {
                list = jsonFile.getList("cusInfResp.tables.row["+i+"]");
                break;
            }
        }
        log.info("tableName ::" + table_name + " :: " + list);
        return list;
    }

    public List<Map<String, String>> getServiceResponse_node_from_Table(String table_name){
        return getServiceResponse_table_data(table_name);
    }

    public String get_value(List<Map<String, String>> list, String key) throws NullPointerException{
        String val = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).containsKey(key)) {
                val = String.valueOf(list.get(i).get(key));
            }
            break;
        }
        return val;
    }
    
}

