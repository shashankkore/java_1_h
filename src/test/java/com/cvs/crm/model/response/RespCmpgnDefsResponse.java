package com.cvs.crm.model.response;

import lombok.Data;

@Data
public class RespCmpgnDefsResponse {
    Integer cmpgnId;
    String cmpgnTypeCd;
    String cmpgnSubTypeCd;
    String cmpgnStartDt;
    String cmpgnEndDt;
    String webDsc;
    String thrshldTypeCd;
    Integer daysToRedeemCnt;
    Integer maxRwrdQty;
    Integer maxVisitRwrdQty;
    String lastUpdtTs;
    String inHomeDt;
    Integer thrshldLimNbr;
    Integer rwrdAmt;
    Boolean everWebRedeemableInd;
    String mktgPrgCd;
    String cmpgnTermsTxt;
    String firstIssueDt;
}
