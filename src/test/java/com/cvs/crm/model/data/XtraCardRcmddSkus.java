package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardRcmddSkus {
    private String xtraCardNbr;
    private String skuNbr;
    private String skuRank;
    private String healthskuNbr;
}
