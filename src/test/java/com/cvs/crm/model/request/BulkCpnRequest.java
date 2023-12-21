package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class BulkCpnRequest {
    String channel;
    String searchCardType;
    Integer searchCardNbr;
    Integer xtraCardNbr;
    String referrerCd;

}
