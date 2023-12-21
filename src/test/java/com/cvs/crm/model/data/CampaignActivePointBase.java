package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignActivePointBase {
    private Integer xtraCardNbr;
    private Integer cmpgnId;
    private Double ptsQty;
    private Date activationTs;
    private String activationSrcCd;
}
