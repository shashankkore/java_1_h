package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CmpgnDefnRequest {
	String channel;
	String msgSrcCd;
	String userId;
	String campainId;
	int srcLocCd;
	String compressInd;
	String skipExpired;
	String campaignTypeCode;
	String subTypeCode;
	String filter_dt;
	String rhbEventType;
	String campainIdList;

}
