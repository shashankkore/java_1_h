package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RHBCouponRequest extends CommonParamsRequest{
    String channel;
    String searchCardType;
    String searchCardNbr;
    String progType_Code;
    String couponEventType;
}
