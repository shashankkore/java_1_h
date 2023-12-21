package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignCoupon {
    private Integer cmpgnId;
    private Integer rwrdQty;
    private Integer cpnNbr;
    private Date StartDt;
    private Date endDt;
    private String cpnDsc;
    private Integer maxRedeemAmt;
    private Integer cpnTermsCd;
    private String amtTypeCd;
    private Integer pctOffAmt;
    private String loadableInd;
    private String fndgCd;
    private String billingTypeCd;
    private String cpnRecptTxt;
    private String cpnValRqrdCd;
    private String absAmtInd;
    private Integer ItemLimitQty;
    private String cpnFmtCd;
    private String freeItemInd;
    private String imageUrlTxt;
    private Date lastUpdtTs;
    private String cpnCatListXml;
    private String cpnCatListJson;
    private String cpnOltpHoldInd;
    private Integer cardValCd;
    private String catMgrId;
    private String maxIssueCnt;
    private String firstUpdtById;
    private String lastUpdtById;
    private String disclaimerTxt;
    private String cpnPrntSuppressInd;
}
