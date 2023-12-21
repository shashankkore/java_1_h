package com.cvs.crm.model.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RespCmpgnEarningsOutputResponse {
    Integer cmpgnId;
    @JsonProperty(value = "pts")
    CampaignEarnPtsResponse ptsResponse;
    @JsonProperty(value = "rwrds")
    CampaignEarnRwrdsResponse rwrdsResponse;
    @JsonProperty(value = "cpns")
    CampaignEarnCpnsResponse cpnsResponse;
}
