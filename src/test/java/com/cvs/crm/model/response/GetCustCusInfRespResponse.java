package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCustCusInfRespResponse {
    @JsonProperty(value = "bcEarningsType")
    GetCustCusInfRespBCEarningsTypeResponse getCustCusInfRespBCEarningsTypeResponse;
    @JsonIgnore
    @JsonProperty(value = "beautyClub")
    GetCustCusInfRespBeautyClubResponse getCustCusInfRespBeautyClubResponse;
    @JsonProperty(value = "carePassCpns")
    List<GetCustCusInfRespCarePassCpnsResponse> getCustCusInfRespCarePassCpnsResponseList;
    @JsonProperty(value = "carePassEnrollStatus")
    GetCustCusInfRespCarePassEnrollStatusResponse getCustCusInfRespCarePassEnrollStatusResponse;
    @JsonProperty(value = "cpn")
    GetCustCusInfRespCpnResponse getCustCusInfRespCpnResponse;
    @JsonProperty(value = "cpns")
    List<GetCustCusInfRespCpnsResponse> getCustCusInfRespCpnsResponseList;
    @JsonProperty(value = "hrEnrollCnt")
    GetCustCusInfRespHrEnrollCntResponse getCustCusInfRespHrEnrollCntResponse;
    @JsonProperty(value = "hrEnrollStatus")
    List<GetCustCusInfRespHrEnrollStatusResponse> getCustCusInfRespHrEnrollStatusResponseList;
    @JsonProperty(value = "hrEvtDtl")
    List<GetCustCusInfRespHrEvtDtlResponse> getCustCusInfRespHrEvtDtlResponseList;
    @JsonProperty(value = "hrMembers")
    List<GetCustCusInfRespHrMembersResponse> getCustCusInfRespHrMembersResponseList;
    @JsonProperty(value = "mfrCpnAvailPool")
    List<GetCustCusInfRespMfrCpnAvailPoolResponse> getCustCusInfRespMfrCpnAvailPoolResponseList;
    @JsonProperty(value = "mktgTypeBenefits")
    GetCustCusInfRespMktgTypeBenefitsResponse getCustCusInfRespMktgTypeBenefitsResponse;
    @JsonProperty(value = "pebAvailPool")
    List<GetCustCusInfRespPebAvailPoolResponse> getCustCusInfRespPebAvailPoolResponseList;
    @JsonProperty(value = "pts")
    List<GetCustCusInfRespPtsResponse> getCustCusInfRespPtsResponseList;
    @JsonProperty(value = "pushNotifs")
    GetCustCusInfRespPushNotifsResponse getCustCusInfRespPushNotifsResponse;
    @JsonProperty(value = "qebEarningType")
    GetCustCusInfRespQebEarningTypeResponse getCustCusInfRespQebEarningTypeResponse;
    @JsonProperty(value = "tables")
    List<GetCustCusInfRespTablesResponse> getCustCusInfRespTablesResponseList;
    @JsonProperty(value = "xtraCard")
    GetCustCusInfRespXtraCardResponse getCustCusInfRespXtraCardResponse;
    @JsonProperty(value = "xtraCarePrefs")
    GetCustCusInfRespXtraCarePrefsResponse getCustCusInfRespXtraCarePrefsResponse;
}
