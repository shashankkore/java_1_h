package com.cvs.crm.model.data;

import lombok.Data;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class CarepassMemberSmry {
    private Integer xtrCardNbr;
    private Timestamp enrollTswtz;
    private String curStatus;
    private String curPlanType;
    private Date expireTswtz;
    private Date benefitEligibilityDt;
    private Date nextRewardIssueDt;
    private String memberEnrollPriceTypeCd;

}