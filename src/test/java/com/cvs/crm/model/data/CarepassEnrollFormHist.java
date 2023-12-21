package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class CarepassEnrollFormHist {
    private Integer xtraCardNbr;
    private Timestamp actionTswtz;
    private Date actionTswtzSetDt;
    private Integer pymtPlanDur;
    private String enrollSrcCd;
    private Integer enrollStoreNbr;
    private String memberEnrollPriceTypeCd;
}
