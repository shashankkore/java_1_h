package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignCouponSkuRank {
    private Integer cmpgnId;
    private Integer cpnNbr;
    private Integer skuNbr;
    private Integer skuRankNbr;
}
