package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableColumnData {

	// common
	private Integer custId;
	private String lastUpdtSrcCd;
	private String lastUpdtDt;
	private String lastUpdtById;
	// customer
	private String gndrCd;
	private String firstName;
	private String middleInitialTxt;
	private String lastName;
	private String surName;
	private String pfxTxt;
	private String birthDt;
	private String ssn;
	// address
	private String addrTypeCd;
	private String addrPrefSeqNbr;
	private String addr1Txt;
	private String addr2Txt;
	private String cityName;
	private String stCd;
	private String zipCd;
	private String zipSfxNbr;
	private String addrQltyCd;
	private String addrConfDt;
	// phone&email
	private String entryMethodCd;
	// phone
	private String phonePrefSeqNbr;
	private String phoneTypeCd;
	private String phoneAreaCdNbr;
	private String phonePfxNbr;
	private String phoneSfxNbr;
	// email
	private String emailPrefSeqNbr;
	private String emailAddrTypeCd;
	private String emailStatusCd;
	private String emailAddrTxt;
	private String emailConfDt;
	private String emailSrcCd;
	// opt
	private String optTypeCd;
	private String optCd;
	private String optSrcCd;
	// xtra_card_child
	private String birthdayDt;
	// xtra_card_wellness_info
	private String wellnessInfoCd;
	// xtra_card_selected_category
	private String selCatSeqNbr;
	private String selCatNbr;
	// xtra_customer
	private String custStatCd;
	private String hhCnt;
	private String custFavCatNbr;
	private String custFavWiCd;
	private String custStatUpdtDt;
	private String headOfHhInd;
	private String hhNbr;
	private String recruitCriteriaCd;
	// xtra_card
	private String trgtGeoMktCd;
	private String cardStatCd;
	private String encodedXtraCardNbr;
	private String xtraCardNbr;
	private String cardFirstScanDt;
	private String totLifetimeVisitCnt;
	private String referredByCd;
	private String rankCd;
	//fsa_dtl_request
	private String reqDt;
	private String startDt;
	private String endDt;
//	private String emailAddrTxt;
	private String procDt;
	private String undelivInd;
	
	private String cpnSeqNbr;
	private String lastUpdtTs;
	private String reprntCd;
	
	private String rqstDt;
	private String purgeRqstInd;
	private String rptRqstInd;
	private String firstUpdtSrcCd;
	private String firstUpdtById;
	private String firstUpdtDt;
	private String rptDestIsCcInd;
	private String rptDestEmailAddrTxt;
	private String rptRqstRefCd;
	private String rptRqstRefCdSentDt;
	private String rptGenDt;
	private String oltpPurgeProcDt;
	private String dwPurgeProcDt;
	private String cdpOltpPurgeProcDt;
	//private String cpnSeqNbr;
}
