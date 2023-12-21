package com.cvs.crm.model.response;
import lombok.Data;

@Data
public class CpnPrintOutputResponse {
    Long cpnSeqNbr;
    Integer statusCd;
    Integer cmpgnId;
    Integer cpnSkuNbr;
    String redeemStartDt;
    String redeemEndDt;
}
