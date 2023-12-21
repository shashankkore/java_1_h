package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.*;

@Data
@JsonRootName(value = "tables")
public class GetCustCusInfRespTableNameResponse {
    // String tableName;
    //List<Map<String, Object>> row;
}
