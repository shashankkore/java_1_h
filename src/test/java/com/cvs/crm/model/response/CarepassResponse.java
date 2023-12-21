package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CarepassResponse {
    Integer rewardNextIssueDaysCount;
    String statusCode;
    String couponExpiryDate;
    String enrollmentExpiryDate;
    Long couponSequenceNumber;
    Integer campaignId;
    Integer couponNumber;

}
