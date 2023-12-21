package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class customerSearchResponse {
    @JsonProperty("xtraCardNbr")
    Integer xtraCardNbr;
    @JsonProperty("encodedXtraCardNbr")
    Long encodedXtraCardNbr;
    @JsonProperty("custId")
    Integer custId;
    @JsonProperty("firstName")
    String firstName;
    @JsonProperty("lastName")
    String lastName;
    @JsonProperty("gndrCd")
    String gndrCd;
    @JsonProperty("birthDt")
    String birthDt;
    @JsonProperty("addr1Txt")
    String addr1Txt;
    @JsonProperty("addr2Txt")
    String addr2Txt;
    @JsonProperty("cityName")
    String cityName;
    @JsonProperty("stCd")
    String stCd;
    @JsonProperty("zipCd")
    String zipCd;
    @JsonProperty("phoneAreaCdNbr")
    Integer phoneAreaCdNbr;
    @JsonProperty("phonePfxNbr")
    Integer phonePfxNbr;
    @JsonProperty("phoneSfxNbr")
    Integer phoneSfxNbr;
    @JsonProperty("emailAddrTxt")
    String emailAddrTxt;
    @JsonProperty("cardLastScanDt")
    String cardLastScanDt;
    @JsonProperty("carepassEnrollStatus")
    String carepassEnrollStatus;
    @JsonProperty("matchCnt")
    String matchCnt;
    @JsonProperty("bMatchName")
    String bMatchName;
    @JsonProperty("bMatchPhone")
    String bMatchPhone;
    @JsonProperty("bMatchEmail")
    String bMatchEmail;
    @JsonProperty("bMatchAddress")
    String bMatchAddress;
    @JsonProperty("bMktgEligible")
    String bMktgEligible;
    @JsonProperty("bEmpCard")
    String bEmpCard;
    @JsonProperty("isCarepassEnrolled")
    String isCarepassEnrolled;
    @JsonProperty("availableExtraBucks")
    String availableExtraBucks;
    @JsonProperty("xtraCardCipherText")
    String xtraCardCipherText;

    @JsonProperty("statusCd")
    String statusCd;
    @JsonProperty("errorDesc")
    String errorDesc;

}
