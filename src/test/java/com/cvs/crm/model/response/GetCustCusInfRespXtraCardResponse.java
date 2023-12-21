package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
public class GetCustCusInfRespXtraCardResponse {
    String cardLastScanDt;
    String cardMbrDt;
    Integer encodedXtraCardNbr;
    Integer homeStoreNbr;
    Double totLifetimeSaveAmt;
    String totYtdSaveAmt;
    String xtraCardCipherTxt;
    Character everDigitizedCpnInd;
    Integer xtraCardNbr;
}
