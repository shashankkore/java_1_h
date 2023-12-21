package com.cvs.crm.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerProfileSearchRequest {
    String uniqUserId;
    String cardNumber;
    String registerNumber;
    Integer registerTypeCode;
    Integer transactionNumber;
    Integer cashierNumber;
    String storeNbr;
    Number phoneNumber;
    String emailAddressText;
    String  xtraCardNbr;
    String zipCd;
    String channel;
    StoreMetaDataRequest storeMetaData;
    String partnerId;

    public Object getTheValueFromJson(String json, String jsonPath){
        DocumentContext jsonContext = JsonPath.parse(json);
        return jsonContext.read(jsonPath);
    }
}
