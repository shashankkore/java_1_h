package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BeautyClubRelaunchResponse {
   Boolean enrolled;
   @JsonProperty(value = "earningsProgress")
   List<BeautyClubEarningsProgressResponse> earningsProgressList;
   Integer campaignId;
   String webDescription;
   String campaignEndDate;
   String thresholdTypeCode;
   Double firstThreshold;
   Double rewardAmount;
   Double pointsToNextThreshold;
   Boolean offerLimitReached;
   Double pointsProgress;
   Double yearToDateEarned;
   Double yearToDateSpent;
}
