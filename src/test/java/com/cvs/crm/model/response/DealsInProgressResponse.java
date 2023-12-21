package com.cvs.crm.model.response;

import lombok.Data;

@Data
public class DealsInProgressResponse {
    Integer campaignId;
    String webDescription;
    String campaignEndDate;
    String thresholdTypeCode;
    Double firstThreshold;
    Double rewardAmount;
    Double pointsToNextThreshold;
    Boolean offerLimitReached;
    String campaignTypeCode;
    String campaignSubtypeCode;
    String pointsQuantity;
    Boolean newDeal;
    Boolean dealEndingSoon;
}
