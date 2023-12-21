package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class customerSearchCloseMatchResponse {
    @JsonProperty("xtraCardNbr")
    Integer xtraCardNbr;
    @JsonProperty("encodedXtraCardNbr")
    Long encodedXtraCardNbr;
    @JsonProperty("custId")
    Integer custId;
    @JsonProperty("matchCnt")
    Integer matchCnt;
}
