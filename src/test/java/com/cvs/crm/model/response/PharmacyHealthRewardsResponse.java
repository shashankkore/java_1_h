package com.cvs.crm.model.response;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.cvs.crm.model.response.PHRMembersResponse;
@Data

public class PharmacyHealthRewardsResponse {
	@JsonIgnore
	Integer campaignId;
	@JsonIgnore
    String webDescription;
	@JsonIgnore
    String campaignEndDate;
	@JsonIgnore
    String thresholdTypeCode;
	@JsonIgnore
    Double firstThreshold;
	@JsonIgnore
    Double rewardAmount;
	@JsonIgnore
    Double pointsToNextThreshold;
	@JsonIgnore
    Boolean offerLimitReached;
	@JsonIgnore
    Double pointsProgress;
	@JsonIgnore
    Boolean enrolled;
	@JsonIgnore
    Double yearToDateEarned;
	@JsonIgnore
    Double yearToDateCredits;
	@JsonIgnore
    Double maxCredits;
	@JsonIgnore
    Boolean capped;
	@JsonIgnore
    Double maxRewardAmount;
	@JsonIgnore
    Integer xtraCardNbr;
	@JsonIgnore
    Integer errorCd;
	@JsonIgnore
    String errorMsg;
	@JsonIgnore
    @JsonProperty(value = "members")
    List<PHRMembersResponse> members; 
}