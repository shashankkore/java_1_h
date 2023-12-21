package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CampaignDefnResponse {
   // @JsonProperty(value = "respCmpgnDefs")
  //  List<RespCmpgnDefsResponse> respCmpgnDefsResponseList;
   List<Map<String, Object>> respCmpgnDefs;
}
