package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
public class GetCustCusInfRespPtsResponse {
    String cmpgnEndDt;
    Integer cmpgnId;
    String cmpgnQualTxt;
    String cmpgnSubtypeCd;
    String cmpgnTermsTxt;
    String cmpgnTypeCd;
    @JsonProperty(value = "cpnCats")
    List<GetCustCusInfRespPebAvailPoolCpnCatsResponse> getCustCusInfRespPebAvailPoolCpnCatsResponseList;
    String cpnCatsXml;
    String cpnQualTxt;
    Boolean endSoonInd;
    Double firstThrshldLimNbr;
    String mfrCpnSrcCd;
    String mktgPrgCd;
    String newInd;
    String offerLimitReachedInd;
    Double ptsQty;
    Double ptsToNextThreshldQty;
    String recptTxt;
    Double rwrdQty;
    Boolean rwrdThrshldCycleInd;
    String startDt;
    String thrshldTypeCd;
    String webDsc;
}
