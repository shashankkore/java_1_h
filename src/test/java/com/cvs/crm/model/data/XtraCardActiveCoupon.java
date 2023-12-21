package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardActiveCoupon {
    private Integer xtraCardNbr;
    private Long cpnseqNbr;
    private Integer cmpgnId;
    private Integer cpnNbr;
    private Integer cmpgnCpnSeqNbr;
    private String cpnSrcCd;
    private Date cpnCreateDt;
    private Integer regRetlAmt;
    private Integer emailCmpgnId;
    private Integer reIssueNbr;
    private Integer storeSeenNbr;
    private Date storeSeenTs;
    private Date viewActlTswtz;
    private Date viewActlTswtzSetDt;
    private String viewActlDestCd;
    private String viewActlDestSubSrcCd;
    private Integer viewActlStoreNbr;
    private Date prntStartTswtz;
    private Date prntEndTswtz;
    private Date prntActlTswtz;
    private Date prntActlTswtzSetDt;
    private String prntDestCd;
    private String prntActlDestSubSrcCd;
    private Integer prntPriorityNbr;
    private String prntActlReferByCd;
    private Integer prntStoreNbr;
    private Date loadActlTswtz;
    private Date loadActlTswtzSetDt;
    private String loadActlDestCd;
    private String loadActlDestSubSrcCd;
    private String loadActlReferByCd;
    private Integer loadActlStoreNbr;
    private String mfrLoadNotifStatusCd;
    private Integer mfrLoadNotifCspStatusCd;
    private Date mfrLoadNotifStatusTs;
    private Date redeemStartTswtz;
    private Date redeemEndTswtz;
    private Date redeemActlTswtz;
    private Date redeemActlTswtzSetDt;
    private Integer redeemStoreNbr;
    private Double redeemActlAmt;
    private String redeemPrgCd;
    private String redeemMatchCd;
    private String reprntCd;
    private Integer redeemActlXtraCardNbr;
    private Integer redeemOvrdRsnNbr;
    private Integer redeemActlCashierNbr;
    private String mfrRedeemNotifStatusCd;
    private Integer mfrRedeemNotifCspStatusCd;
    private Date mfrRedeemNotifStatusTs;
}
