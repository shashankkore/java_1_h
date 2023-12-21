package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "creditsSummary")
public class CreditsSummaryResponse {
    Integer creditsEarned;
    String eventType;
    String firstName;
}
