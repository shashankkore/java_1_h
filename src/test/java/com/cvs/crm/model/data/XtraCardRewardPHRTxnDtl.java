package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class XtraCardRewardPHRTxnDtl {
    private Integer xtraCardNbr;
    private Integer ephLinkId;
    private Date txnDttm;
    private Integer cmpgnId;
    private Integer cpnNbr;
    private String txnTypCd;
    private Integer eventId;
    private Integer ptsQty;
    private Integer txnAmt;
    private Timestamp createDttm;
}
