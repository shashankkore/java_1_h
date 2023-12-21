package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class Customer {
    private Integer custId;
    private String gndrCd;
    private String firstName;
    private String middleInitialTxt;
    private String lastName;
    private String surName;
    private String pfxTxt;
    private Date birthDt;
    private String ssn;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
    private String firstUpdtById;
    private String firstUpdtUniqId;
    private String entryMethodCd;
}
