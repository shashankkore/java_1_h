package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SetCustResponse {
    @JsonProperty("xtraCardNbr")
    Integer xtraCardNbr;
    @JsonProperty("encodedXtraCardNbr")
    Long encodedXtraCardNbr;
    @JsonProperty("errorCd")
    Integer errorCd;
    @JsonProperty("errorMsg")
    String errorMsg;
}
