package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "rwrds")
public class CampaignEarnRwrdsResponse {
    Integer prevRwrdsQty;
    Integer newRwrdsQty;
    Integer deltaRwrdsQty;
    String offerLimitReachedInd;
}
