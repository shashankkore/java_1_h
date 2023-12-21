package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
public class GetCustCusInfRespHrEvtDtlResponse {
    String dateTs;
    Integer ephLinkId;
    String eventEarnAltDsc;
    String eventEarnRsnAltDsc;
    String hrEventTypeAltDsc;
    String idNbr;
    String idTypeCd;
    String procStatusDsc;
    String procStatusDt;
    Integer storeNbr;
}
