package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCard {
    private Integer xtraCardNbr;
    private Integer custId;
    private String trgtGeoMktCd;
    private String enrollSrcCd;
    private Integer enrollBtchNbr;
    private Date surveyCompletionDt;
    private Date cardMbrDt;
    private Date cardFirstScanDt;
    private Date cardLastScanDt;
    private String cardStatCd;
    private Date cardInactDt;
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
    private Date lastUpdtDt;
    private String lastUpdtById;
    private String profitRankCd;
    private String badSeedInd;
    private String homeStoreNbr;
    private String virtualCardInd;
    private Long encodedXtraCardNbr2;

    private Integer xtraCardMaskNbr;
    private String visibleIndicator;
    private String xtraCardSh1Nbr;
    private String xtraCardSh2Nbr;
}
