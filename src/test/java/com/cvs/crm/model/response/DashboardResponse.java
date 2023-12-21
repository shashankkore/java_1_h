package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DashboardResponse {

    //@JsonIgnoreProperties(ignoreUnknown = true)
    @JsonProperty(value = "extraCareCardSummary")
    XtraCardSummaryResponse xtraCardSummaryResponse;

    // @JsonIgnore
    @JsonProperty(value = "pharmacyHealthRewards")
    PharmacyHealthRewardsResponse pharmacyHealthRewardsResponse;

    @JsonIgnore
    @JsonProperty(value = "beautyClub")
   // BeautyClubResponse beautyClubResponse;
    BeautyClubRelaunchResponse beautyClubRelaunchResponse;

    @JsonProperty(value = "quarterlyExtraBucks")
    QuarterlyExtraBucksResponse quarterlyExtraBucksResponse;

    @JsonProperty(value = "carepass")
    CarepassResponse carepassResponse;

    @JsonProperty(value = "dealsInProgress")
    List<DealsInProgressResponse> dealsInProgressResponseList;
}
