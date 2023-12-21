package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class XtraCardSummaryResponse {
    Double totalYearToDateSaving;
    Double totalLifetimeSaving;
    Double availableExtraBucks;
    String lifetimeSavingStartDate;
    String yearToDateStartDate;
    String quarterlyExtraBucksEarningType;
    String bcEarningsType;
    String carepassCouponAvailable;
    Double expiringSoonCouponCount;
    Double newCouponCount;
    Double totalAvailableCouponCount;
    
}
