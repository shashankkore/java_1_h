package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CpnsResponse {
    @JsonProperty("cpnSeqNbr")
    Long cpnSeqNbr;
    @JsonProperty("cmpgnId")
    Integer cmpgnId;
    @JsonProperty("cpnSkuNbr")
    Integer cpnSkuNbr;
    @JsonProperty("redeemStartDt")
    String redeemStartDt;
    @JsonProperty("redeemEndDt")
    String redeemEndDt;
    @JsonProperty("statusCd")
    Integer statusCd;
    @JsonProperty("errorDesc")
    String errorDesc;

}
