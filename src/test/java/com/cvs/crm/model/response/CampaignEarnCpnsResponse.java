package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "cpns")
public class CampaignEarnCpnsResponse {
    Integer cpnNbr;
    Long cpnSeqNbr;
    Integer cmpgnCpnSeqNbr;
    String expDt;
}
