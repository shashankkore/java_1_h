package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiErrorResponse {
    Integer errorCd;
    String errorMsg;
    Integer statusCd;
    String errorDesc;
}
