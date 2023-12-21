package com.cvs.crm.service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.CampaignCoupon;
import com.cvs.crm.model.data.CarepassEnrollFormHist;
import com.cvs.crm.model.data.CarepassMemberSmry;
import com.cvs.crm.model.data.CarepassMemberStatusHist;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraCardActiveCoupon;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.request.TLogRequest;
import com.cvs.crm.repository.CampaignDao;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class TransactionLogService {

	private Response serviceResponse;

	@Autowired
	CacheRefreshUtil cacheRefreshUtil;

	@Autowired
	CarePassEnrollmentUtil carePassEnrollmentUtil;

	@Autowired
	CarePassDateUtil carePassDateUtil;

	@Autowired
	Customer customer;

	@Autowired
	Campaign campaign;

	@Autowired
	CampaignCoupon campaignCoupon;

	@Autowired
	CustomerAddress customerAddress;

	@Autowired
	CustomerEmail customerEmail;

	@Autowired
	CustomerPhone customerPhone;

	@Autowired
	XtraCardActiveCoupon xtraCardActiveCoupon;

	@Autowired
	CarepassEnrollFormHist carepassEnrollFormHist;

	@Autowired
	CarepassMemberSmry carepassMemberSmry;

	@Autowired
	ServiceConfig serviceConfig;

	@Autowired
	XtraCard xtraCard;

	@Autowired
	XtraParms xtraParms;

	@Autowired
	CarepassMemberStatusHist carepassMemberStatusHist;

	@Autowired
	CarePassDao carePassDao;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	XtraCardDao xtraCardDao;

	@Autowired
	CampaignDao campaignDao;

	public void createTransactionforGivenXTraCardNbr(TLogRequest tlogRequest) {
		Integer cardNum = tlogRequest.getXtraCardNbr();
		Integer encodedXTraCardNbr = tlogRequest.getEncodedXtraCardNbr();
		String msgSrcCd = "W";
		String userId = "CVS.COM";
		int srcLocCd = 90046;
		
		int setactionTswtz = 0;
		String jsonStringM2A = "{\r\n" + "	\"xtraCare\": {\r\n" + "		\"carepass\": {\r\n"
				+ "			\"tables\": [\r\n" + "			{\r\n"
				+ "				\"name\": \"CAREPASS_MEMBER_STATUS_HIST\",\r\n" + "				\"rows\": [{\r\n"
				+ "					\"action\": \"insert\",\r\n" + "					\"colData\": {\r\n"
				+ "						\"actionTswtz\": \""
				+ (carePassDateUtil.carePassExpireTswtz(setactionTswtz)).substring(0, 4)
				+ (carePassDateUtil.carePassExpireTswtz(setactionTswtz)).substring(5, 7)
				+ (carePassDateUtil.carePassExpireTswtz(setactionTswtz)).substring(8, 10) + "00:00:00-05:00"
				+ "\",\r\n" + "						\"actionTswtzSetDt\": \"sysdate\",\r\n"
				+ "						\"mbrStatusCd\": \"E\",\r\n"
				+ "						\"mbrStatusRsnCd\": \"\"\r\n" + "					}\r\n"
				+ "				}]\r\n" + "			},\r\n" + "			{\r\n"
				+ "				\"name\": \"CAREPASS_MEMBER_SMRY\",\r\n" + "				\"rows\": [{\r\n"
				+ "					\"action\": \"update\",\r\n" + "					\"colData\": {\r\n"
				+ "                   \"curPlanType\": \"Y\"\r\n" + "					},\r\n"
				+ "					\"criterias\": [{\r\n" + "						\"colName\": \"curStatus\",\r\n"
				+ "						\"operation\": \"=\",\r\n" + "						\"value\": \"E\"\r\n"
				+ "				    },\r\n" + "			{\r\n"
				+ "                       \"colName\": \"curPlanType\",\r\n"
				+ "                       \"operation\": \"!=\",\r\n"
				+ "                       \"value\": \"Y\"\r\n" + "                   }]\r\n" + "			}]\r\n"
				+ "		},\r\n" + "       {\r\n" + "		\"name\": \"CAREPASS_ENROLL_FORM_HIST\",\r\n"
				+ "				\"rows\": [{\r\n" + "					\"action\": \"insert\",\r\n"
				+ "					\"colData\": {\r\n" + "						\"actionTswtz\": \""
				+ (carePassDateUtil.carePassExpireTswtz(setactionTswtz)).substring(0, 4)
				+ (carePassDateUtil.carePassExpireTswtz(setactionTswtz)).substring(5, 7)
				+ (carePassDateUtil.carePassExpireTswtz(setactionTswtz)).substring(8, 10) + "00:00:00-05:00"
				+ "\",\r\n" + "						\"actionTswtzSetDt\": \"sysdate\",\r\n"
				+ "						\"pymtPlanDur\": \"12\"\r\n" + "					}\r\n"
				+ "				}]\r\n" + "				}]\r\n" + "			}\r\n" + "	}\r\n" + "}";

		RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
		requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
				.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
				.addPathParam("search_card_type", encodedXTraCardNbr).addPathParam("search_card_nbr", cardNum)
				.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
				.addQueryParam("src_loc_cd", srcLocCd);

		RequestSpecification spec2 = requestSpecBuilder2.build();
		serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonStringM2A)
				.patch();

		int sCodeC = serviceResponse.getStatusCode();
		String resCan = serviceResponse.toString();
		log.info("enrollUsingSetCust status code: [{}] ", sCodeC +" Service Response: "+resCan);
	}
	
	/**
	 * Create Test Data For Digital receipts Scenario
	 *
	 * @param
	 * @throws InterruptedException
	 */
	public void createTLogTestData() throws ParseException, InterruptedException {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = new Date();
		String dateCurrent = simpleDateFormat.format(date);
		String patternTs = "dd-MMM-yy HH.MM.SS aaa";
		SimpleDateFormat simpleDateFormatTs = new SimpleDateFormat(patternTs);

		DateTime dateTime = new DateTime();
		String todayDate = dateTime.toString("yyyy-MM-dd");
		String yestarday2Date = dateTime.minusDays(2).toString("yyyy-MM-dd");
		String yestardayDate = dateTime.minusDays(1).toString("yyyy-MM-dd");
		String tomorrowDate = dateTime.plusDays(1).toString("yyyy-MM-dd");
		String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
		String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");

		String todayTimeStamp = dateTime.toString("yyyy-MM-dd HH.MM.SS a");
		String yestardayTimeStamp = dateTime.minusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
		String tomorrowTimeStamp = dateTime.plusDays(1).toString("yyyy-MM-dd HH.MM.SS a");
		String prev1yearTimeStamp = dateTime.minusYears(1).toString("yyyy-MM-dd HH.MM.SS a");
		String future1yearTimeStamp = dateTime.plusYears(1).toString("yyyy-MM-dd HH.MM.SS a");

		campaign.setCmpgnId(41083);
		Integer rowexists = campaignDao.checkCampaign(campaign);
		if (rowexists == 1) {
			campaign.setCmpgnId(41083);
			campaign.setEndDt(simpleDateFormat.parse("2030-12-31"));
			campaignDao.updateCampaign(campaign);
		} else if (rowexists == 0) {
			campaign.setCmpgnId(41083);
			campaign.setCmpgnTypeCd("C");
			campaign.setCmpgnSubtypeCd("H");
			campaign.setCmpgnDsc("CarePass_20% Off");
			campaign.setRecptPrntInd("0");
			campaign.setRecptPrntPriorityNbr(2);
			campaign.setRecptRxt("CarePass_20");
			campaign.setRecptScaleNbr(2);
			campaign.setRwrdRedirInd(" ");
			campaign.setStartDt(simpleDateFormat.parse("2021-01-01"));
			campaign.setEndDt(simpleDateFormat.parse("2030-12-31"));
			campaign.setLastUpdtTs(null);
			campaign.setIssueFreqTypeCd(null);
			campaign.setIssueFreqCnt(1);
			campaign.setFirstIssueDt(simpleDateFormat.parse(prev1yearDate));
			campaign.setLastIssueDt(simpleDateFormat.parse(future1yearDate));
			campaign.setPrevIssueDt(simpleDateFormat.parse(yestarday2Date));
			campaign.setNextIssueDt(simpleDateFormat.parse(yestardayDate));
			campaign.setDaysToPrintCnt(999);
			campaign.setDaysToRedeemCnt(14);
			campaign.setInHomeDt(simpleDateFormat.parse(prev1yearDate));
			campaign.setTotlaRwrdEarnAmt(5);
			campaign.setBonusSkuCalcDt(null);
			campaign.setCpnRedeemCalcDt(null);
			campaign.setCpnBaseDsc("Carepass 20%");
			campaign.setParentCmpgnId(null);
			campaign.setCpnCatNbr(null);
			campaign.setCpnSegNbr(null);
			campaign.setCpnFndgCd("6");
			campaign.setBillingTypeCd(null);
			campaign.setIndivRwrdAmt(3);
			campaign.setCpnAutoGenInd("-1");
			campaign.setRwrdLastCalcDt(simpleDateFormat.parse(yestarday2Date));
			campaign.setCsrVisibleInd("-1");
			campaign.setCmpgnTermsTxt("Spend $30 on beauty products and earn $3 ExtraBucks Rewards");
			campaign.setWebDsc("$3 ExtraBucks Rewards when you spend $30 on beauty");
			campaign.setWebDispInd("-1");
			campaign.setPayVendorNbr(null);
			campaign.setCpnOltpHoldInd(null);
			campaign.setCpnPurgeCd(null);
			campaign.setDfltCpnTermscd(1);
			campaign.setCatMgrId("K");
			campaign.setVendorNbr(null);
			campaign.setMultiVendorInd("0");
			campaign.setCpnFixedDscInd("0");
			campaign.setCpnPrntStartDelayDayCnt(0);
			campaign.setCpnRedmStartDelayDayCnt(null);
			campaign.setCpnPriorityNbr(2);
			campaign.setCpnQualTxt("Carepass 20%");
			campaign.setReqSkuList(null);
			campaign.setMaxVisitRwrdQty(null);
			campaign.setMaxRwrdQty(null);
			campaign.setRwrdRecalcInd(null);
			campaign.setCmpgnQualTxt("Carepass 20%");
			campaign.setTrgtPrntDestCd(null);
			campaign.setCpnMinPurchAmt(null);
			campaign.setLastFeedAccptDt(null);
			campaign.setAdvMaxRwrdQty(null);
			campaign.setPromoLinkNbr(null);
			campaign.setAmtTypeCd(null);
			campaign.setPctOffAmt(null);
			campaign.setFsaCpnInd(null);
			campaign.setPrtLabrlCpnInd(null);
			campaign.setDfltAlwaysInd(null);
			campaign.setDfltFreqDayCnt(null);
			campaign.setDfltFreqEmpDayCnt(null);
			campaign.setLoadableInd("-1");
			campaign.setCardTypeCd(null);
			campaign.setCpnRecptTxt("Beauty Club ExtraBucks Rewards");
			campaign.setCpnValRqrdCd("N");
			campaign.setAbsAmtInd("N");
			campaign.setItemLimitQty(null);
			campaign.setCpnFmtCd("2");
			campaign.setDfltCpnCatJson(null);
			campaign.setFreeItemInd(null);
			campaign.setMktgPrgCd("C");
			campaign.setMobileDispInd("-1");
			campaign.setOvrdPaperLessInd("N");
			campaign.setAnalyticEventTypeCd(null);
			campaign.setWebRedeemableInd("-1");
			campaign.setMfrCpnSrcCd(null);
			campaign.setXtraCardSegNbr(0);
			campaign.setProductCriteriaId(null);
			campaign.setDfltCpnCatXml(null);
			campaign.setSegIncExcCd(null);
			campaign.setSegSrcOwnerName(null);
			campaign.setSegSrcTableName(null);
			campaign.setSegReloadRqstTs(null);
			campaign.setSegLastProcStartTs(null);
			campaign.setSegLastProcEndTs(null);
			campaign.setSegLastProcStatCd(null);
			campaign.setSegLastProcRowCnt(null);
			campaign.setFixedRedeemStartDt(null);
			campaign.setFixedRedeemEndDt(null);
			campaign.setLastAutoReissueDt(null);
			campaign.setAutoReissueInd("Y");
			campaign.setTrgtPrntRegCd(null);
			campaign.setFacebookDispInd(null);
			campaign.setInstantCmpgnEarnigInd("0");
			campaign.setPeOptimizeTypeCd(null);
			campaignDao.createCampaign(campaign);

		}

		campaignCoupon.setCmpgnId(41083);
		campaignCoupon.setCpnNbr(61622);
		Integer cpnrowexists = campaignDao.checkCampaignCoupon(campaignCoupon);
		if (cpnrowexists == 1) {
			campaignCoupon.setCmpgnId(41083);
			campaignCoupon.setCpnNbr(61622);
			campaignCoupon.setCpnDsc("CarePass_20% Off");
			campaignCoupon.setCpnRecptTxt("CarePass_20% Off");
			campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
			campaignCoupon.setEndDt(simpleDateFormat.parse(future1yearDate));
			campaignDao.updateCampaignCoupon(campaignCoupon);
		} else if (cpnrowexists == 0) {
			campaignCoupon.setCmpgnId(41083);
			campaignCoupon.setRwrdQty(1);
			campaignCoupon.setCpnNbr(61622);
			campaignCoupon.setStartDt(simpleDateFormat.parse(prev1yearDate));
			campaignCoupon.setEndDt(simpleDateFormat.parse(future1yearDate));
			campaignCoupon.setCpnDsc("Carepass 20%");
			campaignCoupon.setMaxRedeemAmt(99);
			campaignCoupon.setCpnTermsCd(1);
			campaignCoupon.setAmtTypeCd("P");
			campaignCoupon.setPctOffAmt(20);
			campaignCoupon.setLoadableInd("-1");
			campaignCoupon.setFndgCd("6");
			campaignCoupon.setBillingTypeCd(null);
			campaignCoupon.setCpnRecptTxt("CarePass_20% Off");
			campaignCoupon.setCpnValRqrdCd("Y");
			campaignCoupon.setAbsAmtInd("N");
			campaignCoupon.setItemLimitQty(null);
			campaignCoupon.setCpnFmtCd("3");
			campaignCoupon.setFreeItemInd(null);
			campaignCoupon.setImageUrlTxt(" ");
			campaignCoupon.setLastUpdtTs(null);
			campaignCoupon.setCpnCatListXml(" ");
			campaignCoupon.setCpnCatListJson(" ");
			campaignCoupon.setCpnOltpHoldInd(" ");
			campaignCoupon.setCardValCd(null);
			campaignCoupon.setCatMgrId("Q");
			campaignCoupon.setMaxIssueCnt(null);
			campaignCoupon.setFirstUpdtById("BAT_TWRM_TD");
			campaignCoupon.setLastUpdtById(" ");
			campaignCoupon.setCpnPrntSuppressInd("-1");
			campaignCoupon.setDisclaimerTxt(" ");
			campaignDao.createCampaignCoupon(campaignCoupon);

		}

		/*
		 * I joined ExtraCare program today to participate in CarePass Monthly program
		 * and want to see 10$ and 20% coupons
		 *
		 */

		xtraCard.setXtraCardNbr(98158393);
		xtraCard.setCustId(80126);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158393);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80126);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80126);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80126);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80126);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * carepassMemberSmry.setXtrCardNbr(98158393);
		 * carepassMemberSmry.setEnrollTswtz(carePassDateUtil.carePassEnrollTswtz(0));
		 * carepassMemberSmry.setExpireTswtz(simpleDateFormat.parse(carePassDateUtil.
		 * carePassExpireTswtz(30)));
		 * carepassMemberSmry.setBenefitEligibilityDt(simpleDateFormat.parse(
		 * carePassDateUtil.carePassExpireTswtz(30)));
		 * carepassMemberSmry.setNextRewardIssueDt(simpleDateFormat.parse(
		 * carePassDateUtil.carePassExpireTswtz(30)));
		 * carepassMemberSmry.setCurPlanType("M"); carepassMemberSmry.setCurStatus("E");
		 * carePassDao.updateCarePassMemberSmry(carepassMemberSmry);
		 * 
		 * carepassEnrollFormHist.setXtraCardNbr(98158393);
		 * carepassEnrollFormHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(0)
		 * ); carepassEnrollFormHist.setActionTswtzSetDt(simpleDateFormat.parse(
		 * carePassDateUtil.carePassActionTswtzSetDt()));
		 * carepassEnrollFormHist.setPymtPlanDur(1);
		 * carePassDao.updateCarepassEnrollFormHist(carepassEnrollFormHist);
		 * 
		 * carepassMemberStatusHist.setXtraCardNbr(98158393);
		 * carepassMemberStatusHist.setActionTswtz(carePassDateUtil.carePassEnrollTswtz(
		 * 30)); carepassMemberStatusHist.setActionTswtzSetDt(simpleDateFormat.parse(
		 * carePassDateUtil.carePassActionTswtzSetDt()));
		 * carepassMemberStatusHist.setMbrStatusCd("E");
		 * carePassDao.UpdateCarepassMemberStatusHist(carepassMemberStatusHist);
		 * 
		 * xtraCardActiveCoupon.setXtraCardNbr(98158393);
		 * xtraCardActiveCoupon.setCmpgnId(40666);
		 * xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.
		 * carePassExpireTswtz(0)));
		 * xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.
		 * carePassPrntStartEndTswtz());
		 * xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.
		 * carePassPrntStartEndTswtz());
		 * xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.
		 * carePassPrntStartEndTswtz());
		 * xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(
		 * carePassDateUtil.carePassActionTswtzSetDt()));
		 * xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(
		 * carePassDateUtil.carePassActionTswtzSetDt()));
		 * xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.
		 * carePassRedeemEndTswtz(30)); xtraCardActiveCoupon.setRedeemActlTswtz(null);
		 * xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
		 * xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);
		 * 
		 * 
		 * xtraCardActiveCoupon.setXtraCardNbr(98158393);
		 * xtraCardActiveCoupon.setCmpgnId(41083);
		 * xtraCardActiveCoupon.setCpnCreateDt(simpleDateFormat.parse(carePassDateUtil.
		 * carePassExpireTswtz(0)));
		 * xtraCardActiveCoupon.setPrntStartTswtz(carePassDateUtil.
		 * carePassPrntStartEndTswtz());
		 * xtraCardActiveCoupon.setPrntEndTswtz(carePassDateUtil.
		 * carePassPrntStartEndTswtz());
		 * xtraCardActiveCoupon.setLoadActlTswtz(carePassDateUtil.
		 * carePassPrntStartEndTswtz());
		 * xtraCardActiveCoupon.setLoadActlTswtzSetDt(simpleDateFormat.parse(
		 * carePassDateUtil.carePassActionTswtzSetDt()));
		 * xtraCardActiveCoupon.setRedeemStartTswtz(simpleDateFormat.parse(
		 * carePassDateUtil.carePassActionTswtzSetDt()));
		 * xtraCardActiveCoupon.setRedeemEndTswtz(carePassDateUtil.
		 * carePassRedeemEndTswtz(30)); xtraCardActiveCoupon.setRedeemActlTswtz(null);
		 * xtraCardActiveCoupon.setRedeemActlTswtzSetDt(null);
		 * xtraCardDao.updateXtraCardActiveCoupon(xtraCardActiveCoupon);
		 * 
		 * cacheRefreshUtil.refresCacheusingXtraParms();
		 * cacheRefreshUtil.refresCacheforCmpgnDefns();
		 */

		/*
		 * I joined ExtraCare program today to participate in CarePass Yearly program
		 * and want to see 10$ and 20% coupons
		 *
		 */
		xtraCard.setXtraCardNbr(98158394);
		xtraCard.setCustId(80127);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158394);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80127);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80127);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80127);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80127);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an existing ExtraCare member and joined in CarePass Monthly program
		 * today and want to see 10$ and 20% coupons
		 *
		 */
		xtraCard.setXtraCardNbr(98158395);
		xtraCard.setCustId(80128);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158395);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80128);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80128);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80128);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80128);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an existing ExtraCare member and joined in CarePass Yearly program today
		 * and want to see 10$ and 20% coupons
		 *
		 */
		xtraCard.setXtraCardNbr(98158396);
		xtraCard.setCustId(80129);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158396);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80129);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80129);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80129);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80129);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an existing CarePass member and initiated cancellation of my monthly
		 * carepass membership today and want to see the status as U and
		 * benefit_eligibility_date as future date in carepass_member_status_hist and
		 * carepass_member_smry tables
		 *
		 */
		xtraCard.setXtraCardNbr(98158397);
		xtraCard.setCustId(80130);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158397);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80130);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80130);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80130);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80130);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an existing CarePass member and initiated cancellation of my yearly
		 * carepass membership today and want to see the status as U and
		 * benefit_eligibility_date as future date in carepass_member_status_hist and
		 * carepass_member_smry tables
		 *
		 */
		xtraCard.setXtraCardNbr(98158398);
		xtraCard.setCustId(80131);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158398);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80131);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80131);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80131);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80131);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an CarePass member and cancelled my monthly carepass membership 5 day
		 * ago and want to see the status as U and benefit_eligibility_date as past date
		 * in carepass_member_status_hist and carepass_member_smry tables
		 *
		 */
		xtraCard.setXtraCardNbr(98158399);
		xtraCard.setCustId(80132);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158399);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80132);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80132);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80132);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80132);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an CarePass member and cancelled my yearly carepass membership 5 day ago
		 * and want to see the status as U and benefit_eligibility_date as past date in
		 * carepass_member_status_hist and carepass_member_smry tables
		 *
		 */
		xtraCard.setXtraCardNbr(98158400);
		xtraCard.setCustId(80133);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158400);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80133);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80133);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80133);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80133);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a CarePass member with Hold status and benefit_eligibility_date as
		 * expire date and want to reactivate my monthly membership and see the status
		 * changed to E and benefit_eligibility_date changed to future 30 days from
		 * current date in carepass_member_status_hist and carepass_member_smry tables
		 *
		 */
		xtraCard.setXtraCardNbr(98158401);
		xtraCard.setCustId(80134);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158401);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80134);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80134);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80134);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80134);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a CarePass member with Hold status and benefit_eligibility_date as
		 * expire date and want to reactivate my yearly membership and see the status
		 * changed to E and benefit_eligibility_date changed to future 30 days from
		 * current date in carepass_member_status_hist and carepass_member_smry tables
		 *
		 */
		xtraCard.setXtraCardNbr(98158402);
		xtraCard.setCustId(80135);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158402);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80135);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80135);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80135);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80135);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a CarePass member with monthly membership program and my membership
		 * status went on Hold and want to see the status changed to H and
		 * benefit_eligibility_date as today in carepass_member_status_hist and
		 * carepass_member_smry tables
		 *
		 */
		xtraCard.setXtraCardNbr(98158403);
		xtraCard.setCustId(80136);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158403);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80136);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80136);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80136);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80136);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a CarePass member with yearly membership program and my membership
		 * status went on Hold and want to see the status changed to H and
		 * benefit_eligibility_date as today in carepass_member_status_hist and
		 * carepass_member_smry tables
		 *
		 */

		xtraCard.setXtraCardNbr(98158404);
		xtraCard.setCustId(80137);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158404);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80137);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80137);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80137);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80137);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a CarePass member with Hold status and want to unenroll from monthly
		 * membership program and see the status changed to U and benefit_eligibity_date
		 * to today in carepass_member_status_hist and carepass_member_smry tables *
		 */
		xtraCard.setXtraCardNbr(98158405);
		xtraCard.setCustId(80138);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158405);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80138);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80138);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80138);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80138);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a CarePass member with Hold status and want to unenroll from yearly
		 * membership program and see the status changed to U and benefit_eligibity_date
		 * to today in carepass_member_status_hist and carepass_member_smry tables *
		 */
		xtraCard.setXtraCardNbr(98158406);
		xtraCard.setCustId(80139);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158406);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80139);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80139);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80139);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80139);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a CarePass member currently under monthly program and would like to
		 * switch to yearly program and see the expire date changed to 365 days and
		 * benefit eligibility date changes to 1 month from current date and plan type
		 * changes to Y
		 *
		 */
		xtraCard.setXtraCardNbr(98158407);
		xtraCard.setCustId(80140);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158407);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80140);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80140);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80140);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80140);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am using invalid search card type and want to see the error response
		 *
		 */
		xtraCard.setXtraCardNbr(98158408);
		xtraCard.setCustId(80141);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158408);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80141);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80141);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80141);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80141);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am using invalid table name in the request payload and want to see the
		 * error response
		 *
		 */
		xtraCard.setXtraCardNbr(98158409);
		xtraCard.setCustId(80142);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158409);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80142);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80142);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80142);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80142);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am using invalid coulmn name in the request payload and want to see the
		 * error response
		 *
		 */
		xtraCard.setXtraCardNbr(98158410);
		xtraCard.setCustId(80143);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(98158410);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80143);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80143);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80143);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80143);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

	}

	/**
	 * Delete Test Data for Care Pass Scenario
	 */
	public void deleteSetCustCarepassEnrollmentTestData() {
		/*
		 * Purge All Test Cards
		 */
		List<Integer> xtraCardNbrList = Arrays.asList(98158393, 98158394, 98158395, 98158396, 98158397, 98158398,
				98158399, 98158400, 98158401, 98158402, 98158403, 98158404, 98158405, 98158406, 98158407, 98158408,
				98158409, 98158410);
		List<Integer> custIdList = Arrays.asList(80126, 80127, 80128, 80129, 80130, 80131, 80132, 80133, 80134, 80135,
				80136, 80137, 80138, 80139, 80140, 80141, 80142, 80143);

		customerDao.deleteCustomers(custIdList);
		xtraCardDao.deleteXtraCards(xtraCardNbrList);
		carePassDao.deleteCarePass(xtraCardNbrList);
	}
}