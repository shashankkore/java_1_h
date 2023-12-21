package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class XtraCardAnalyticEvent {
    private Integer xtraCardNbr;
    private Date loadTs;
    private Date analyticEventTs;
    private Integer analyticEventTypeCd;
    private String eventDataJson;
    private String eventMetaDataJson;
    private Integer cpnSeqNbr;
    private String firstUpdtSrcCd;
}
