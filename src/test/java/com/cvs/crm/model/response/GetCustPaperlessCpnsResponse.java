package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
@Data
@JsonRootName(value="paperlessCpns")
public class GetCustPaperlessCpnsResponse {
    String enrolled;
}
