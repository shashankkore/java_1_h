package com.cvs.crm.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvisionCustomerRequest {
    String token;

    public Object getTheValueFromJson(String json, String jsonPath){
        DocumentContext jsonContext = JsonPath.parse(json);
        return jsonContext.read(jsonPath);
    }
}
