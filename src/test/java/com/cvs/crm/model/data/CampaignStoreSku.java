package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class CampaignStoreSku {
    private Integer cmpgnId;
    private Integer storeNbr;
    private Integer skuNbr;
    private Timestamp startDt;
    private Timestamp endDt;
}
