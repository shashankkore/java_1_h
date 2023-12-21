package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class HREventRequest {
    String EPHID;
    String idNumber;
    String idType;
    String eventTypeCode;
    String eventEarnCode;
    String eventEarnReasonCode;
    Integer scriptNumber;
    String eventTimestamp;
    String miscInfoShippingAddrStateCd;
    String miscInfoStoreAddrStateCd;
    String channel;
//    Integer cardType;
}

