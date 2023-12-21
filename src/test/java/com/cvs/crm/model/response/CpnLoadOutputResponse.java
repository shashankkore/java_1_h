package com.cvs.crm.model.response;
import lombok.Data;

@Data
public class CpnLoadOutputResponse {
    Long cpnSeqNbr;
    Integer statusCd;
    Integer cmpgnId;
    Integer cpnSkuNbr;
    String redeemStartDt;
    String redeemEndDt;
}
