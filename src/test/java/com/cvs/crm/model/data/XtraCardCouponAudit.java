package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class XtraCardCouponAudit {
    private Integer xtraCardNbr;
    private Timestamp dateTs;
    private Long cpnSeqNbr;
    private String opCd;
    private String createdBy;
}
