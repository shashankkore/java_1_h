package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BeautyClubEarningsProgressResponse {
    Integer daysLeft;
    String freeItemCoupon;
    @JsonProperty(value = "couponDetails")
    CouponBeautyClubDashboardResponse couponDetailsResponse;
    Integer campaignId;
    Integer couponNumber;
    String webDescription;
    String couponDescription;
    String campaignEndDate;
    String thresholdTypeCode;
    Double firstThreshold;
    Double rewardAmount;
    Double pointsToNextThreshold;
    String offerLimitReached;
    Double pointsProgress;
    Double yearToDateEarned;
    Double yearToDateSpent;
    Double extrabuckRewardsEarned;
    String everWebRedeemable;
    String couponIssueDate;

}
