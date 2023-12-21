package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "carePass")
public class GetCustCarepassResponse {
    String benefitEligibilityDt;
    String enrollDt;
    String expiryDt;
    String nextRewardIssueDt;
    String planType;
    String statusCd;
    String statusDt;
    String statusReason;
}
