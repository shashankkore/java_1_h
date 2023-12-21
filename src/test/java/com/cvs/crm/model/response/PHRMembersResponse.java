package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "member")
public class PHRMembersResponse {

    String firstName;
    //  String lastName;
    Boolean capped;
    String cappedDate;
    Double maxCredits;
    Double maxRewardAmount;
}
