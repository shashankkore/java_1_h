package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ProductRequest {
    String channel;
    String searchCardType;
    Integer searchCardNbr;
    String productTypeCd;
    Integer skuNumber;
}
