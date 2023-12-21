package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
public class GetCustCusInfRespPebAvailPoolResponse {
    String cmpgnEndDt;
    Integer cmpgnId;
    @JsonProperty(value = "cpnCats")
    List<GetCustCusInfRespPebAvailPoolCpnCatsResponse> getCustCusInfRespPebAvailPoolCpnCatsResponseList;
    Boolean endSoonInd;
    String newInd;
    String thrshldTypeCd;
    String webDsc;
    String redeemEligibleChannelCd;
}
