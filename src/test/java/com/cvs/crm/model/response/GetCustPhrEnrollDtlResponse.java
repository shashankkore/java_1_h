package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "phrEnrollDtl")
public class GetCustPhrEnrollDtlResponse {
    Integer cmpgnId;
    String enrolled;
}
