package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardSummaryData {
    private Integer xtraCardNbr;
    private Integer custId;
    private String trgtGeoMktCd;
    private String enrollSrcCd;
    private Integer enrollBtchNbr;
    private String surverCompletionDt;
    private Date cardMbrDt;
    private String cardFirstScanDt;
    private String cardLastScanDt;
    private String cardStatCd;
    private String cardInactDt;
    private Integer totLifetimeSpentAmt;
    private Integer totLifetimeVisitCnt;
    private Integer enrollStrNbr;
    private String rgstrXrefDt;
    private String enrollFormLangCd;
    private String referredByCd;
    private String enrollFormVerCd;
    private String rankCd;
    private String panelInd;
    private Double totYtdSaveAmt;
    private Double totLifetimeSaveAmt;
    private String mktgTypeCd;
    private String mktgTypeEndDt;
    private Integer encodedXtraCardNbr;
    private String pbmClientCd;
    private String lastUpdtSrcCd;
    private String lastUpdtDt;
    private String lastUpdtById;
    private String profitRankCd;
    private String badSeedInd;
    private String homeStoreNbr;
    private String virtualCardInd;
    private String gndrCd;
    private String firstName;
    private String middleInitialTxt;
    private String lastName;
    private String surName;
    private String pfxTxt;
    private String birthDt;
    private String ssn;
    private String firstUpdtById;
    private String firstUpdtUniqId;
    private String entryMethodCd;
    private Double totEbAvlAmt;
    private Integer qebCmpgnId;
    private Integer qebPtsQty;
    private Integer qebPtsThreshold;
    private Integer qebRewardEarnedAmt;
    private String qebRewardAvailDt;
    private Integer hrCmpgnId;
    private Integer hrPtsQty;
    private Integer hrPtsThreshold;
    private Integer hrRewardEarnedAmt;
    private Integer bcCmpgnId;
    private Integer bcPtsQty;
    private Integer bcPtsThreshold;
    private Integer bcIndvRwrdAmt;
    private Integer bcRewardEarnedAmt;
    private Date addedDt;
    private Integer empCardNbr;
}