package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class customerSearchListResponse {
    @JsonProperty(value = "test")
    List<customerSearchResponse> customerSearchResponse;
}
