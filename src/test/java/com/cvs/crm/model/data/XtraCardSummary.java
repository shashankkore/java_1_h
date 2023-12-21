package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardSummary {
    private Integer xtraCardNbr;
    private Integer firstDigitizedCpnSeqNbr;
    private String everDigitizedCpnInd;
    private String firstDigitizedCpnSrcCd;
    private Date firstDigitizedCpnTs;
    private String everBeautyClubEnrolledInd;
    private Date CreateTs;
    private Date lastUpdtTs;
 }
