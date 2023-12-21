package com.cvs.crm.model.response;

import lombok.Data;

@Data
public class CouponBeautyClubDashboardResponse {
    String Loadable;
    String redeemable;
    String redeemed;
    String expiryDate;
    Long couponSequenceNumber;
    String loadActualDate;
    String redeemActualDate;
}
