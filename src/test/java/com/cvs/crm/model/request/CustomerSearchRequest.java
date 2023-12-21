package com.cvs.crm.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSearchRequest {
    String uniqUserId;
    String searchType;
    String cardNbr;
    String cardType;
    Integer phoneAreaCdNbr;
    Integer phonePfxNbr;
    Integer phoneSfxNbr;
    String emailAddrTxt;
    String firstName;
    String lastName;
    String encodedXtraCardNbr;
    String  xtraCardNbr;
    String zipCd;
    String partnerId;

    public Object getTheValueFromJson(String json, String jsonPath){
        DocumentContext jsonContext = JsonPath.parse(json);
        return jsonContext.read(jsonPath);
    }
}
