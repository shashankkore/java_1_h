package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignCouponCriteria {
    private Integer cmpgnId;
    private Integer cpnNbr;
    private String criteriaUsageCd;
    private String allItemInd;
    private Integer criteriaId;
    private String incExcCd;
    private Integer rqrdPurchAmt;
    private String amtTypeCd;
    private Date lastChngDt;
}
