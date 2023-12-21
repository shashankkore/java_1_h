package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignActivePoint {
    private Integer cmpgnId;
    private Integer xtraCardNbr;
    private Integer ptsQty;
}
