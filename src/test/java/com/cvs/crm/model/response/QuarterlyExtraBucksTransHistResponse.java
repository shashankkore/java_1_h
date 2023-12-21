package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuarterlyExtraBucksTransHistResponse {
    @JsonProperty(value = "quarterlyExtraBucks")
    List<QEBTransResponse> qebTransResponseList;
}
