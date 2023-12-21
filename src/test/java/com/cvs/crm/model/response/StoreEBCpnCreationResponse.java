package com.cvs.crm.model.response;

import lombok.Data;

@Data
public class StoreEBCpnCreationResponse {
    Integer xtraCardNbr;
    Integer cmpgnId;
    Integer cpnNbr;
    Long cpnSeqNbr;
    String cpnFlag;
    String pfxTxt;
    String firstName;
    String lastName;
    String surName;

}
