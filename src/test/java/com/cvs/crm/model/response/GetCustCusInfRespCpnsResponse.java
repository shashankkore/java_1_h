package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCustCusInfRespCpnsResponse {
    String absAmtInd;
    String amtTypeCd;
    String appOnlyInd;
    String autoReissueInd;
    Integer cmpgnId;
    String cmpgnSubtypeCd;
    String cmpgnTypeCd;
    @JsonProperty(value = "cpnCats")
    List<GetCustCusInfRespCpnCatsResponse> getCustCusInfRespCpnCatsResponseList;
    String cpnCreateDt;
    String cpnDefLastUpdtTs;
    String cpnDsc;
    String cpnFmtCd;
    String cpnGroupingCd;
    Integer cpnNbr;
    String cpnQualTxt;
    String cpnRecptTxt;
    Long cpnSeqNbr;
    String cpnSortInd;
    String cpnSuppressCd;
    String cpnTermsCd;
    String cpnValRqrdCd;
    String endTs;
    String everWebRedeemableInd;
    String expSoonInd;
    String expirDt;
    String fndgCd;
    String homeStoreNbr;
    String imgUrlTxt;
    String itemLimitQty;
    String lastUpdtTs;
    String loadActlDt;
    String loadableInd;
    Integer maxIssueCnt;
    String maxRedeemAmt;
    String mfrInd;
    String mfrOfferBrandName;
    String mfrOfferSrcTxt;
    String mfrOfferValueDsc;
    String mktgPrgCd;
    String newCpnInd;
    Double pctOffAmt;
    String printableInd;
    String prntActlDt;
    String prntEndTs;
    String prodCpnInd;
    String redeemableInd;
    String redmActlDt;
    String redmStartDt;
    Double regRetlAmt;
    String storeName;
    Integer storeNbr;
    String viewActlDt;
    String viewableInd;
    String webDsc;
    Character digitizedCpnInd;
    String redeemEligibleChannelCd;
}
