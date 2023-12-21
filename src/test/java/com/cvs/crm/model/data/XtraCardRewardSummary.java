package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardRewardSummary {
    private Integer xtraCardNbr;
    private Double totLifeTimeSaveAmt;
    private Double totYtdSaveAmt;
    private Double totEbAvlAmt;
    private Integer qebCmpgnId;
    private Integer qebPtsQty;
    private Integer qebPtsThreshold;
    private Integer qebRewardEarnedAmt;
    private Integer qebRewardAvailDt;
    private Integer hrCmpgnId;
    private Integer hrPtsQty;
    private Integer hrPtsThreshold;
    private Integer hrRewardEarnedAmt;
    private Integer bcCmpgmId;
    private Integer bcPtsQty;
    private Integer bcPtsThreshold;
    private Integer bcIndvRwrdAmt;
    private Integer bcRewardEarnedAmt;
}
