package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardCustomer {
    private Integer custId;
    private String recruiteCriteriaCd;
    private String hhNbr;
    private String headOfHHInd;
    private String custStatCd;
    private Date custStatUpdtDt;
    private String custFavCatNbr;
    private String custFavWiCd;
    private String hhCnt;

    private String lastUpdtSrcCd;
    private String lastUpdtDt;
    private String lastUpdtById;
  
}
