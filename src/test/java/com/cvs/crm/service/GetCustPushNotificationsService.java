package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.*;
import com.cvs.crm.model.request.GetCustRequest;
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

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Service
@Data
@Slf4j
public class GetCustPushNotificationsService {
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

