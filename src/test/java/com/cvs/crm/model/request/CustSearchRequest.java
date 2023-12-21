package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class CustSearchRequest {
    String channel;
    String version;
    String typeCd;
    String cardNbr;
    Integer xtraCardNbr;
    String deviceUniqId;
    String uniqUserId;
    String searchType;
    String searchTypeFieldNames;
    String firstName;
    String lastName;
    String emailAddrTxt;
    int phoneAreaCdNbr;
    int phonePfxNbr;
    int phoneSfxNbr;
    Long encodedXtraCardNbr;
    String maskedXtraCard;
    String addr1_txt;
    String addr2_txt;
    String city;
    String stCd;
    String zipCd;
    int regNbr;
    String regTypeCd;
    int storeNbr;
    int txnNbr;
    int cashierNbr;
    String cardLastScanDt;
}
