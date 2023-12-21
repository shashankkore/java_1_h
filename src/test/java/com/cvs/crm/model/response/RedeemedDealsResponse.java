package com.cvs.crm.model.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import com.cvs.crm.model.response.PHRMembersResponse;

@Data
public class RedeemedDealsResponse {
    String redeemDate;
//    Double savedAmount;
    @JsonProperty(value = "redeemEvents")
    List<RedeemEventsResponse> redeemEvents;
}
