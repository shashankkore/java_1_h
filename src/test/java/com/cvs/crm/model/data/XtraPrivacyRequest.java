package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraPrivacyRequest {
    private Integer xtraCardNbr;
    private Date rqstDt;
    private String rqstUuid;
    private Integer custId;
    private Long encodedXtraCardNbr;
    private String purgeRqstInd;
    private String rptRqstInd;
    private String rptDestTypeCd;
    private String rptDestIsCcInd;
    private String rptDestEmailAddrTxt;
    private String firstUpdtSrcCd;
    private Date firstUpdtDt;
    private String firstUpdtById;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
    private String rqstStateCd;
}
