package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetCustCusInfRespTablesResponse {
    @JsonProperty(value = "tables")
    List<GetCustCusInfRespTableNameResponse> getCustCusInfRespTableNameResponseList;
    String tableName;
    List<Map<String, Object>> row;

}
