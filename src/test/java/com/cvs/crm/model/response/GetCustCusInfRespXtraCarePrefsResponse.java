package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
public class GetCustCusInfRespXtraCarePrefsResponse {
    @JsonProperty(value = "beautyClub")
    GetCustBeautyClubResponse getCustBeautyClubResponse;
    @JsonProperty(value = "beautyNotes")
    GetCustBeautyNotesResponse getCustBeautyNotesResponse;
    @JsonProperty(value = "carePass")
    GetCustCarepassResponse getCustCarepassResponse;
    @JsonProperty(value = "diabeticClub")
    GetCustDiabeticClubResponse getCustDiabeticClubResponse;
    String digitalReceipt;
    String optInEc;
    String optInEmail;
    @JsonProperty(value = "paperlessCpns")
    GetCustPaperlessCpnsResponse getCustPaperlessCpnsResponse;
    @JsonProperty(value = "phr")
    GetCustPhrEnrollDtlResponse getCustPhrEnrollDtlResponse;
    String phrEnroll;
    String sms;
}