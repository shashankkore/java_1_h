package com.cvs.crm.model.response;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
@Data
@JsonRootName(value="couponInfo")
public class CouponInfoResponse {
//    String couponHeader;
//    String couponDescription;
	String marketingProgramCode;
	String campaignTypeCode;
	String amountTypeCode;
	Double percentOffAmount;
	Double maxRedeemAmount;
	Integer couponFormatCode;
	String couponReceiptText;
	String expiryDate;
	String autoReissueIndicator;
	String couponDescription;
	Long couponSequenceNumber;	
}
