package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
public class SetCustRequest {
    String channel;
    String searchCardType;
    Integer searchCardNbr;
    String version;
    Long encodedXtraCardNbr;
    String phoneTypeCd;
    Integer phoneAreaCdNbr;
    Integer phonePfxNbr;
    Integer phoneSfxNbr;
    Integer phonePrefSeqNbr;
    String emailAddrTypeCd;
    String emailStatusCd;
    String emailAddrTxt;
    Integer emailPrefSeqNbr;
    String birthDt;
    String firstName;
    String lastName;
    String Addr1Txt;
    String cityName;
    String stCd;
    String zipCd;
    String rqstDt;
    String rqstStateCd;
    String purgeRqstInd;
    String RptRqstInd;
}
