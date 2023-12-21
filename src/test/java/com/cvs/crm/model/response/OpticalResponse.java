package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpticalResponse {
    String encodedXtraCardNumber;
    String employeeCard;
}
