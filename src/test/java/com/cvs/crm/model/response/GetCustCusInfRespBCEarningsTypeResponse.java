package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import java.util.List;

@Data
public class GetCustCusInfRespBCEarningsTypeResponse {
    String earningsType;
    List<Integer> cmpgnIds;
}
