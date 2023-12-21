package com.cvs.crm.model.response;

import lombok.Data;

@Data
public class MfrCpnsResponse {
    Long cpnSeqNbr;
    Integer cpnStatusCd;
    Integer cmpgnId;
    Integer cpnSkuNbr;
    String redeemEndDt;
}