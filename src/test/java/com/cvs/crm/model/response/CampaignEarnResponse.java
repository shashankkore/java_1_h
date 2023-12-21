package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CampaignEarnResponse {
    @JsonProperty(value = "respCmpgnEarnings")
    List<RespCmpgnEarningsOutputResponse> respCmpgnEarningsResponseList;
}
