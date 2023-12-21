package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignRewardThreshold {
    private Integer cmpgnId;
    private Integer thrshldLimNbr;
    private Integer rwrdQty;
    private String rwrdThrshldCyclInd;
    private String thrshldTypeCd;

}
