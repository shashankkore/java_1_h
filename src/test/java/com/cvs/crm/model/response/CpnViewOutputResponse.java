package com.cvs.crm.model.response;
import lombok.Data;

@Data
public class CpnViewOutputResponse {
    Long cpnSeqNbr;
    Integer statusCd;
    Integer cmpgnId;
    Integer cpnSkuNbr;
    String redeemStartDt;
    String redeemEndDt;
}
