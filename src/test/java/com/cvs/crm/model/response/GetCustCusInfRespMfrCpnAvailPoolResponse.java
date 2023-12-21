package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCustCusInfRespMfrCpnAvailPoolResponse {
    @JsonProperty(value = "cpns")
    List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList;
    @JsonProperty(value = "mfrCpnAvailPool")
    List<GetCustCusInfRespCpnsResponse> getCustCusInfRespMfrCpnAvailPoolResponseList;
    Integer cmpgnId;
    Integer cpnNbr;
    String maxRedeemAmt;
    String expirDt;
    String cpnDsc;
    String newCpnInd;
    String everWebRedeemableInd;
    String expSoonInd;
    String fndgCd;
    String imgUrlTxt;
    String mfrInd;
    String mfrOfferValueDsc;
    String mfrOfferBrandName;
    String redeemEligibleChannelCd;
    String lastUpdtTs;
    String personalizedInd;
    String endTs;
    String cpnSortInd;
}
