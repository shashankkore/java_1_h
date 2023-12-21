package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuarterlyExtraBucksResponse {
    Integer campaignId;
    String webDescription;
    String campaignEndDate;
    String thresholdTypeCode;
    Double firstThreshold;
    Double rewardAmount;
    Double pointsToNextThreshold;
    Boolean offerLimitReached;
    Double extrabuckRewardsEarned;
    String couponIssueDate;
    Double pointsProgress;
}
