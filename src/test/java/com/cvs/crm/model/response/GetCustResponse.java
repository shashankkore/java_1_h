package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetCustResponse {
    @JsonProperty(value = "cusInfResp")
    GetCustCusInfRespResponse getCustCusInfRespResponse;
    @JsonProperty("errorCd")
    Integer errorCd;
    @JsonProperty("errorMsg")
    String errorMsg;
}
