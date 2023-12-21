package com.cvs.crm.model.response;

import lombok.Data;

import java.util.List;

@Data
public class QEBTransResponse {
    String transactionDate;
    Double extraBucksEarned;
    List<Double> transactionAmount;
}
