package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignReward {
    private Integer cmpgnId;
    private Integer xtraCardNbr;
    private Integer rwrdEarnQty;
    private Integer rwrdIssuedQty;
    private Date lastEarnUpdtDt;
    private Date lastIssuedUpdtDt;
    private Integer rwrdLinkQty;
    private Date lastLinkUpdtDt;
    private Integer lastEarnchngQty;
    private Integer rwrdEarnXferInQty;
    private Integer rwrdEarnXferOutQty;
    private Integer rwrdIssuedXferInQty;
    private Integer rwrdIssuedXferOutQty;
    private Integer rwrdLinkXferInQty;
    private Integer rwrdLinkXferOutQty;
    private Integer maxCmpgnCpnSeqNbr;
    private Integer rwrdExcessQty;
    private Integer rwrdExcessXferInQty;
    private Integer rwrdExcessXferOutQty;
}
