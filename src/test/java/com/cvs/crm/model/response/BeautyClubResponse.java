package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BeautyClubResponse {
    Boolean enrolled;
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

  /* Boolean enrolled;
   @JsonProperty(value = "earningsProgress")
   List<BeautyClubEarningsProgressResponse> earningsProgress;
*/
}
