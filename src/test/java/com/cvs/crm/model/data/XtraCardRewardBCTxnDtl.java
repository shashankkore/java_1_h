package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class XtraCardRewardBCTxnDtl {
    private Integer xtraCardNbr;
    private Date txnDttm;
    private Integer cmpgnId;
    private Integer cpnNbr;
    private String txnTypCd;
    private Integer qualifiedTxnAmt;
    private Timestamp createDttm;
}
