package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BulkCpnsResponse {
    @JsonProperty(value = "viewOutList")
    List<CpnViewOutputResponse> viewResponseList;
    @JsonProperty(value = "printOutList")
    List<CpnPrintOutputResponse> printResponseList;
    @JsonProperty(value = "loadOutList")
    List<CpnLoadOutputResponse> loadResponseList;
    @JsonProperty(value = "redeemOutList")
    List<CpnRedeemOutputResponse> redeemResponseList;
}
