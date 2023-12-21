package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CpnsRequest {
    String channel;
    String searchCardType;
    Integer searchCardNbr;
    String version;
    Long cpnSeqNbr;
    Long cmpCpnSeqNbr;
    Integer cmpgnId;
    Integer cpnSkuNbr;
    Integer matchCd;
    Character opCd;
    Character referrerCd;
    String redeemActlAmt;
    Integer redeemActlCashierNbr;
    String ts;
}
