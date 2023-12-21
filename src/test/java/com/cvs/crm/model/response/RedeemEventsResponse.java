package com.cvs.crm.model.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import java.util.List;

@Data
@JsonRootName(value="redeemEvents")
public class RedeemEventsResponse {
    @JsonProperty(value = "couponInfo")
    List<CouponInfoResponse> couponInfo;
    Double savedAmount;
}
