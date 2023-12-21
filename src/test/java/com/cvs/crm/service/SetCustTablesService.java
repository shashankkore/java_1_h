package com.cvs.crm.service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerAddress;
import com.cvs.crm.model.data.CustomerEmail;
import com.cvs.crm.model.data.CustomerOpt;
import com.cvs.crm.model.data.CustomerPhone;
import com.cvs.crm.model.data.TableColumnData;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraCardChild;
import com.cvs.crm.model.data.XtraCardCustomer;
import com.cvs.crm.model.data.XtraCardSelectCategory;
import com.cvs.crm.model.data.XtraCardWellnessInfo;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.model.request.SetCustTablesRequest;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.JsonUtil;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class SetCustTablesService {

	private Response serviceResponse;

	@Autowired
	Customer customer;

	@Autowired
	CustomerAddress customerAddress;

	@Autowired
	CustomerEmail customerEmail;

	@Autowired
	CustomerPhone customerPhone;

	@Autowired
	CustomerOpt customerOpt;

	@Autowired
	XtraCard xtraCard;

	@Autowired
	XtraParms xtraParms;

	@Autowired
	CacheRefreshUtil cacheRefreshUtil;

	@Autowired
	ServiceConfig serviceConfig;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	XtraCardDao xtraCardDao;

	@Autowired
	XtraCardChild xtraCardChild;

	@Autowired
	XtraCardWellnessInfo xtraCardWellnessInfo;

	@Autowired
	XtraCardSelectCategory xtraCardSelectCategory;

	@Autowired
	XtraCardCustomer xtraCardCustomer;

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	private Environment environment;

	SetCustTablesRequest setCustRequest = new SetCustTablesRequest();
	TableColumnData actualDataFromDb = new TableColumnData();
	TableColumnData expectedData = new TableColumnData();
	Gson gson = new Gson();
	int currentCustId;
	int currentXtraCard;

	public Response patchSetCustomer(SetCustTablesRequest setCustRequest, int cardNbr, String cardType, String msgSrcCd,
			String userId, int srcLocCd) {

		String jsonBody = gson.toJson(setCustRequest);
		RestAssured.baseURI = serviceConfig.getSetcustUrl();
		log.info(jsonBody);

		String apiPath = "api/v1.1/customers/" + cardType + "," + cardNbr + "?msg_src_cd=" + msgSrcCd + "&src_loc_cd="
				+ srcLocCd + "&user_id=" + userId;
		RestAssured.baseURI = serviceConfig.getSetcustUrl();
		System.out.println(RestAssured.baseURI + apiPath);
		log.info(jsonBody);
		serviceResponse = RestAssured.given().contentType("application/json").log().all().body(jsonBody).patch(apiPath);
		serviceResponse.prettyPrint();
		return serviceResponse;
	}

	/**
	 * Create Test Data For Customer Search Scenarios
	 *
	 * @param
	 * @throws ParseException, IncorrectResultSizeDataAccessException
	 * @Author: Anurag R
	 */

	public void createTestDataForSetCustTablesFromJson(JSONObject jo)
			throws ParseException, IncorrectResultSizeDataAccessException {

		currentCustId = Integer.parseInt(jo.get("custId").toString());
		System.out.println("CustomerId: " + currentCustId);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = new Date();
		String dateCurrent = simpleDateFormat.format(date);
		DateTime dateTime = new DateTime();
		String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");

		// Customer
		customer.setCustId(currentCustId);
		customer.setGndrCd(jo.get("gndrCd").toString());
		customer.setFirstName(jo.get("firstName").toString());
		customer.setLastName(jo.get("lastName").toString());
		try {
			customerDao.createCustomer(customer);
		} catch (Exception e) {
			System.out.println("WARN: Data already exist or error in creating Customer: " + currentCustId);
		}

		// Xtra card, encoded card & SHA2 card
		xtraCard.setCustId(currentCustId);
		currentXtraCard = Integer.parseInt(jo.get("xtraCardNbr").toString());
		xtraCard.setXtraCardNbr(currentXtraCard);
		xtraCard.setEncodedXtraCardNbr2(Long.parseLong(jo.get("encodedXtraCardNbr").toString()));
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(dateCurrent));
		xtraCard.setMktgTypeCd(" ");
		xtraCard.setXtraCardMaskNbr(39 + currentCustId);
		try {
			xtraCardDao.createXtraCard(xtraCard);
		} catch (Exception e) {
			e.getMessage();
			System.out.println("WARN: Data already exist or error in creating XTRA CARD " + currentCustId);
		}

		// Customer Email
		JSONArray jsonArray1 = (JSONArray) jo.get("email");
		for (int i = 0; i < jsonArray1.size(); i++) {
			JSONObject jo2 = (JSONObject) jsonArray1.get(i);
			customerEmail.setCustId(currentCustId);
			customerEmail.setEmailAddrTypeCd(jo2.get("emailAddrTypeCd").toString());
			customerEmail.setEmailPrefSeqNbr(Integer.parseInt(jo2.get("emailPrefSeqNbr").toString()));
			customerEmail.setEmailAddrTxt(jo2.get("emailAddrTxt").toString());
			customerEmail.setEmailStatusCd("A");
			try {
				customerDao.createCustomerEmail(customerEmail);
			} catch (Exception e) {
				System.out.println("WARN: Data already exist or error in creating Customer email: " + currentCustId);
			}
		}

		// Customer Phone
		JSONArray jsonArray2 = (JSONArray) jo.get("phone");
		for (int i = 0; i < jsonArray2.size(); i++) {
			JSONObject jo3 = (JSONObject) jsonArray2.get(i);
			customerPhone.setCustId(currentCustId);
			customerPhone.setPhoneTypeCd(jo3.get("phoneTypeCd").toString());
			customerPhone.setPhonePrefSeqNbr(Integer.parseInt(jo3.get("phonePrefSeqNbr").toString()));
			customerPhone.setPhoneAreaCdNbr(Integer.parseInt(jo3.get("phoneAreaCdNbr").toString()));
			customerPhone.setPhonePfxNbr(Integer.parseInt(jo3.get("phonePfxNbr").toString()));
			customerPhone.setPhoneSfxNbr(Integer.parseInt(jo3.get("phoneSfxNbr").toString()));
			try {
				customerDao.createCustomerPhone(customerPhone);
			} catch (Exception e) {
				e.getMessage();
				System.out.println("WARN: Data already exist or error in creating Customer phone: " + currentCustId);
			}
		}

		// Customer Address
		JSONArray jsonArray3 = (JSONArray) jo.get("addresses");
		for (int i = 0; i < jsonArray3.size(); i++) {
			JSONObject jo4 = (JSONObject) jsonArray3.get(i);
			customerAddress.setCustId(currentCustId);
			customerAddress.setAddrTypeCd(jo4.get("addrTypeCd").toString());
			customerAddress.setAddrPrefSeqNbr(Integer.parseInt(jo4.get("addrPrefSeqNbr").toString()));
			customerAddress.setAddr1Txt(jo4.get("addr1Txt").toString());
			customerAddress.setCityName(jo4.get("cityName").toString());
			customerAddress.setStCd(jo4.get("stCd").toString());
			customerAddress.setZipCd(jo4.get("zipCd").toString());
			try {
				customerDao.createCustomerAddress(customerAddress);
			} catch (Exception e) {
				System.out.println("WARN: Data already exist or error in creating Customer address: " + currentCustId);
			}
		}

		// Customer Opt
		JSONArray jsonArray4 = (JSONArray) jo.get("opt");
		for (int i = 0; i < jsonArray4.size(); i++) {
			JSONObject jo5 = (JSONObject) jsonArray4.get(i);
			customerOpt.setCustId(currentCustId);
			customerOpt.setLastUpdtById(jo5.get("lastUpdtById").toString());
			customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
			customerOpt.setLastUpdtSrcCd(jo5.get("lastUpdtSrcCd").toString());
			customerOpt.setOptCd(jo5.get("optCd").toString());
			customerOpt.setOptSrcCd(jo5.get("optSrcCd").toString());
			customerOpt.setOptTypeCd(jo5.get("optTypeCd").toString());
			try {
				customerDao.createCustomerOpt(customerOpt);
			} catch (Exception e) {
				System.out.println("WARN: Data already exist or error in creating Customer opt: " + currentCustId);
			}
		}
		// xtraCardChild and xtraCardWellnessInfo
		xtraCardChild.setBirthdayDt(simpleDateFormat.parse(prev1yearDate));
		xtraCardChild.setXtraCardNbr(currentXtraCard);
		xtraCardWellnessInfo.setWellnessInfoCd(jo.get("wellNessInfoCd").toString());
		xtraCardWellnessInfo.setXtraCardNbr(currentXtraCard);
		try {
			xtraCardDao.createXtraCardChild(xtraCardChild);
			xtraCardDao.createXtraCardWellnessInfo(xtraCardWellnessInfo);
		} catch (Exception e) {
			System.out.println("WARN: Data already exist or error in creating Customer opt: " + currentCustId);
		}
		// xtraCardSelectCategory
		JSONArray jsonArray5 = (JSONArray) jo.get("selectCat");
		for (int i = 0; i < jsonArray5.size(); i++) {
			JSONObject jo6 = (JSONObject) jsonArray5.get(i);
			xtraCardSelectCategory.setSelectCatSeqNbr(jo6.get("selectCatSeqNbr").toString());
			xtraCardSelectCategory.setSelectCatSeqNbr(jo6.get("selectCatNbr").toString());
			xtraCardSelectCategory.setXtraCardNbr(currentXtraCard);
			try {
				xtraCardDao.createXtraCardSelectCat(xtraCardSelectCategory);
			} catch (Exception e) {
				System.out.println("WARN: Data already exist or error in creating Customer opt: " + currentCustId);
			}
		}

		// xtraCustomer
		JSONArray jsonArray6 = (JSONArray) jo.get("xtraCustomer");
		for (int i = 0; i < jsonArray6.size(); i++) {
			JSONObject jo6 = (JSONObject) jsonArray6.get(i);
			xtraCardCustomer.setRecruiteCriteriaCd(jo6.get("recruiteCriteriaCd").toString());
			xtraCardCustomer.setHhNbr(jo6.get("hhNbr").toString());
			xtraCardCustomer.setHhNbr(jo6.get("headOfHHInd").toString());
			xtraCardCustomer.setHhNbr(jo6.get("custStatCd").toString());
			xtraCardCustomer.setHhNbr(jo6.get("custStatUpdtDt").toString());
			xtraCardCustomer.setHhNbr(jo6.get("custFavCatNbr").toString());
			xtraCardCustomer.setHhNbr(jo6.get("custFavWiCd").toString());
			xtraCardCustomer.setHhNbr(jo6.get("hhCnt").toString());
			xtraCardCustomer.setCustId(currentCustId);
			try {
				xtraCardDao.createXtraCardCustomer(xtraCardCustomer);
			} catch (Exception e) {
				System.out.println("WARN: Data already exist or error in creating xtraCardCustomer: " + currentCustId);
			}
		}

	}

	public void deleteTestDataForSetCustTables() {
		List<Integer> custIds = new ArrayList<>();
		List<Integer> xtraCards = new ArrayList<>();
		xtraCards.add(currentXtraCard);
		xtraCardDao.deleteXtraCards(xtraCards);
		custIds.add(currentCustId);
		customerDao.deleteCustomers(custIds);

	}

	public SetCustTablesRequest generateARequestBody(JSONObject jo, String action, String table) {
		SetCustTablesRequest request1 = new SetCustTablesRequest();

		String filePath = "./src/test/resources/testdata/setCustomer.json";
//		String testEnvironment = this.environment.getActiveProfiles()[0];

		try {
			JSONArray inputDataArray = jsonUtil.readJsonFileGetArray(filePath, "initial_request_body");
			JSONObject baseRequestJo = (JSONObject) inputDataArray.get(0);
			String jsonString1 = gson.toJson(baseRequestJo);
			request1 = new Gson().fromJson(jsonString1, SetCustTablesRequest.class);
			request1.getTables().get(0).setName(table);
			request1.getTables().get(0).getRows().get(0).setAction(action);
			// build column data
			TableColumnData colData = new TableColumnData();
			if (!action.contains("delete")) {
				String jsonString = gson.toJson(jo);
				colData = new Gson().fromJson(jsonString, TableColumnData.class);
			}
			request1.getTables().get(0).getRows().get(0).setColData(colData);

		} catch (Exception e) {

			e.printStackTrace();
			e.getMessage();

		}

		return request1;

	}

	public JSONObject getTableColumnDataFromDb(SetCustTablesRequest actualRequest, boolean isExpected,
			String testDataFile) throws org.json.simple.parser.ParseException {

		Map<String, Object> dbDataMap = new HashMap<>();
		String tableName = actualRequest.getTables().get(0).getName();
		String seqNbr = "1";
		List<Integer> custIds = new ArrayList<>();
		custIds.add(currentCustId);
		List<Integer> xtraCards = new ArrayList<>();
		xtraCards.add(currentXtraCard);
		TableColumnData currentDataFromDb = new TableColumnData();
		if (tableName.toLowerCase().contains("customer")) {
			List<Map<String, Object>> dbDataMapList = customerDao.selectDataFromCustomerTable(custIds, tableName);
			dbDataMap = dbDataMapList.get(0);
			currentDataFromDb.setCustId(Integer.parseInt(dbDataMap.get("CUST_ID").toString()));
			currentDataFromDb.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
			// currentDataFromDb.setLastUpdtDt(getValueFromDataMap(dbDataMap, "LAST_UPDT_DT").toString());
			currentDataFromDb.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());
		}else if (tableName.toLowerCase().contains("xtra_card_selected_category")) {
			List<Map<String, Object>> dbDataMapList = xtraCardDao.selectDataFromXtraCardSelectCategoryTable(xtraCards, tableName);
			dbDataMap = (dbDataMapList.size() > 0) ? dbDataMapList.get(0) : null;
			
		}else {
			List<Map<String, Object>> dbDataMapList = xtraCardDao.selectDataFromXtraCardChildTable(xtraCards, tableName);
			dbDataMap = (dbDataMapList.size() > 0) ? dbDataMapList.get(0) : null;
		}
			
		/**/

		if (tableName.equalsIgnoreCase("customer")) {
			currentDataFromDb.setGndrCd(getValueFromDataMap(dbDataMap, "GNDR_CD").toString());
			currentDataFromDb.setFirstName(getValueFromDataMap(dbDataMap, "FIRST_NAME").toString());
			currentDataFromDb.setMiddleInitialTxt(getValueFromDataMap(dbDataMap, "MIDDLE_INITIAL_TXT").toString());
			currentDataFromDb.setLastName(getValueFromDataMap(dbDataMap, "LAST_NAME").toString());
			currentDataFromDb.setSurName(getValueFromDataMap(dbDataMap, "SUR_NAME").toString());
			currentDataFromDb.setPfxTxt(getValueFromDataMap(dbDataMap, "PFX_TXT").toString());
			currentDataFromDb.setBirthDt(getValueFromDataMap(dbDataMap, "BIRTH_DT").toString());
			currentDataFromDb.setSsn(getValueFromDataMap(dbDataMap, "SSN").toString());
		} else if (tableName.equalsIgnoreCase("customer_address")) {

			currentDataFromDb.setAddrTypeCd(getValueFromDataMap(dbDataMap, "ADDR_TYPE_CD").toString());
			currentDataFromDb.setAddrPrefSeqNbr(getValueFromDataMap(dbDataMap, "ADDR_PREF_SEQ_NBR").toString());
			currentDataFromDb.setAddr1Txt(getValueFromDataMap(dbDataMap, "ADDR1_TXT").toString());
			currentDataFromDb.setAddr2Txt(getValueFromDataMap(dbDataMap, "ADDR2_TXT").toString());
			currentDataFromDb.setCityName(getValueFromDataMap(dbDataMap, "CITY_NAME").toString());
			currentDataFromDb.setStCd(getValueFromDataMap(dbDataMap, "ST_CD").toString());
			currentDataFromDb.setZipCd(getValueFromDataMap(dbDataMap, "ZIP_CD").toString());
			currentDataFromDb.setZipSfxNbr(getValueFromDataMap(dbDataMap, "ZIP_SFX_NBR").toString());
			currentDataFromDb.setAddrQltyCd(getValueFromDataMap(dbDataMap, "ADDR_QLTY_CD").toString());
			currentDataFromDb.setAddrConfDt(getValueFromDataMap(dbDataMap, "ADDR_CONF_DT").toString());
			currentDataFromDb.setEntryMethodCd(getValueFromDataMap(dbDataMap, "ENTRY_METHOD_CD").toString());

		} else if (tableName.equalsIgnoreCase("customer_email")) {
			currentDataFromDb.setEmailPrefSeqNbr(getValueFromDataMap(dbDataMap, "EMAIL_PREF_SEQ_NBR").toString());
			currentDataFromDb.setEmailAddrTypeCd(getValueFromDataMap(dbDataMap, "EMAIL_ADDR_TYPE_CD").toString());
			currentDataFromDb.setEmailStatusCd(getValueFromDataMap(dbDataMap, "EMAIL_STATUS_CD").toString());
			currentDataFromDb.setEmailAddrTxt(getValueFromDataMap(dbDataMap, "EMAIL_ADDR_TXT").toString());
			currentDataFromDb.setEmailConfDt(getValueFromDataMap(dbDataMap, "EMAIL_CONF_DT").toString());
			currentDataFromDb.setEntryMethodCd(getValueFromDataMap(dbDataMap, "ENTRY_METHOD_CD").toString());
			currentDataFromDb.setEmailSrcCd(getValueFromDataMap(dbDataMap, "EMAIL_SRC_CD").toString());

		} else if (tableName.equalsIgnoreCase("customer_opt")) {
			currentDataFromDb.setOptTypeCd(getValueFromDataMap(dbDataMap, "OPT_TYPE_CD").toString());
			currentDataFromDb.setOptCd(getValueFromDataMap(dbDataMap, "OPT_CD").toString());
			currentDataFromDb.setOptSrcCd(getValueFromDataMap(dbDataMap, "OPT_SRC_CD").toString());

		} else if (tableName.equalsIgnoreCase("customer_phone")) {
			currentDataFromDb.setEntryMethodCd(getValueFromDataMap(dbDataMap, "ENTRY_METHOD_CD").toString());
			currentDataFromDb.setPhonePrefSeqNbr(getValueFromDataMap(dbDataMap, "PHONE_PREF_SEQ_NBR").toString());
			currentDataFromDb.setPhoneTypeCd(getValueFromDataMap(dbDataMap, "PHONE_TYPE_CD").toString());
			currentDataFromDb.setPhoneAreaCdNbr(getValueFromDataMap(dbDataMap, "PHONE_AREA_CD_NBR").toString());
			currentDataFromDb.setPhonePfxNbr(getValueFromDataMap(dbDataMap, "PHONE_PFX_NBR").toString());
			currentDataFromDb.setPhoneSfxNbr(getValueFromDataMap(dbDataMap, "PHONE_SFX_NBR").toString());

		} else if (tableName.equalsIgnoreCase("xtra_card_child")) {
			currentDataFromDb.setBirthdayDt(getValueFromDataMap(dbDataMap, "BIRTHDAY_DT").toString());
			currentDataFromDb.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
			currentDataFromDb.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());

		} else if (tableName.equalsIgnoreCase("xtra_card_wellness_info")) {
			currentDataFromDb.setWellnessInfoCd(getValueFromDataMap(dbDataMap, "WELLNESS_INFO_CD").toString());
			currentDataFromDb.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
			currentDataFromDb.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());

		} else if (tableName.equalsIgnoreCase("xtra_card_selected_category")) {
			currentDataFromDb.setSelCatSeqNbr(getValueFromDataMap(dbDataMap, "SEL_CAT_SEQ_NBR").toString());
			currentDataFromDb.setSelCatNbr(getValueFromDataMap(dbDataMap, "SEL_CAT_NBR").toString());

		} else if (tableName.equalsIgnoreCase("xtra_customer")) {
			currentDataFromDb.setCustStatCd(getValueFromDataMap(dbDataMap, "CUST_STAT_CD").toString());
			currentDataFromDb.setHhCnt(getValueFromDataMap(dbDataMap, "HH_CNT").toString());
			currentDataFromDb.setCustFavCatNbr(getValueFromDataMap(dbDataMap, "CUST_FAV_CAT_NBR").toString());
			currentDataFromDb.setCustFavWiCd(getValueFromDataMap(dbDataMap, "CUST_FAV_WI_CD").toString());
			currentDataFromDb.setCustStatUpdtDt(getValueFromDataMap(dbDataMap, "CUST_STAT_UPDT_DT").toString());
			currentDataFromDb.setHeadOfHhInd(getValueFromDataMap(dbDataMap, "HEAD_OF_HH_IND").toString());
			currentDataFromDb.setHhNbr(getValueFromDataMap(dbDataMap, "HH_NBR").toString());
			currentDataFromDb.setRecruitCriteriaCd(getValueFromDataMap(dbDataMap, "RECRUIT_CRITERIA_CD").toString());

		} else if (tableName.equalsIgnoreCase("xtra_card")) {
			currentDataFromDb.setTrgtGeoMktCd(getValueFromDataMap(dbDataMap, "TRGT_GEO_MKT_CD").toString());
			currentDataFromDb.setCardStatCd(getValueFromDataMap(dbDataMap, "CARD_STAT_CD").toString());
			currentDataFromDb.setEncodedXtraCardNbr(getValueFromDataMap(dbDataMap, "ENCODED_XTRA_CARD_NBR").toString());
			currentDataFromDb.setXtraCardNbr(getValueFromDataMap(dbDataMap, "XTRA_CARD_NBR").toString());
			currentDataFromDb.setCardFirstScanDt(getValueFromDataMap(dbDataMap, "CARD_FIRST_SCAN_DT").toString());
			currentDataFromDb
					.setTotLifetimeVisitCnt(getValueFromDataMap(dbDataMap, "TOT_LIFETIME_VISIT_CNT").toString());
			currentDataFromDb.setReferredByCd(getValueFromDataMap(dbDataMap, "REFERRED_BY_CD").toString());
			currentDataFromDb.setRankCd(getValueFromDataMap(dbDataMap, "RANK_CD").toString());

		} else if (tableName.equalsIgnoreCase("fsa_dtl_request")) {
			/*currentDataFromDb.setTrgtGeoMktCd();
			currentDataFromDb.setCardStatCd(getValueFromDataMap(dbDataMap, "CARD_STAT_CD").toString());
			currentDataFromDb.setEncodedXtraCardNbr(getValueFromDataMap(dbDataMap, "ENCODED_XTRA_CARD_NBR").toString());
			currentDataFromDb.setXtraCardNbr(getValueFromDataMap(dbDataMap, "XTRA_CARD_NBR").toString());
			currentDataFromDb.setCardFirstScanDt(getValueFromDataMap(dbDataMap, "CARD_FIRST_SCAN_DT").toString());
			currentDataFromDb
					.setTotLifetimeVisitCnt(getValueFromDataMap(dbDataMap, "TOT_LIFETIME_VISIT_CNT").toString());
			currentDataFromDb.setReferredByCd(getValueFromDataMap(dbDataMap, "REFERRED_BY_CD").toString());
			currentDataFromDb.setRankCd(getValueFromDataMap(dbDataMap, "RANK_CD").toString()); */

		}

		String filePath = "./src/test/resources/testdata/setCustomer.json";
//		String testEnvironment = this.environment.getActiveProfiles()[0];
		JSONArray inputDataArray = jsonUtil.readJsonFileGetArray(filePath, testDataFile);
		JSONObject jsonObjectInput = (JSONObject) inputDataArray.get(0);

		if (isExpected) {
			JSONObject existingJo = toJsonFromStrng(gson.toJson(currentDataFromDb));
			expectedData = actualRequest.getTables().get(0).getRows().get(0).getColData();
			JSONObject currentJo = toJsonFromStrng(gson.toJson(expectedData));
			Iterator<?> existingKeys = existingJo.keySet().iterator();
			Iterator<?> currentJoKeys = currentJo.keySet().iterator();
			while (existingKeys.hasNext()) {
				String currentKey = (String) existingKeys.next();
				String currentValue = existingJo.get(currentKey).toString();
				if (!currentJo.containsKey(currentKey)) {
					if (currentKey.contains("custId"))
						currentJo.put(currentKey, currentValue);
					else
						currentJo.put(currentKey, currentValue);
				}
			}
			currentJo = removeTheUnwantedFromSecondJo(jsonObjectInput, currentJo);
			return currentJo;
		} else {
			actualDataFromDb = currentDataFromDb;
			JSONObject currentJo2 = toJsonFromStrng(gson.toJson(currentDataFromDb));
			currentJo2 = removeTheUnwantedFromSecondJo(jsonObjectInput, currentJo2);
			return currentJo2;
		}
	}

	public JSONObject removeTheUnwantedFromSecondJo(JSONObject jsonObjectInput, JSONObject jo2) {

		Iterator<?> expctedKeys = jsonObjectInput.keySet().iterator();
		Iterator<?> currentJoKeys = jo2.keySet().iterator();
		while (expctedKeys.hasNext()) {
			String currentKey = (String) expctedKeys.next();
			if (!jo2.containsKey(currentKey)) {
				jo2.remove(currentKey);
			}
		}
		return jo2;

	}

	public JSONObject getExpectedData(TableColumnData expected) throws org.json.simple.parser.ParseException {
		try {
			expected.setLastUpdtDt(actualDataFromDb.getLastUpdtDt());
		} catch (Exception e) {
		}
		try {
			expected.setBirthDt(actualDataFromDb.getBirthDt());
		} catch (Exception e) {
		}
		try {
			expected.setAddrConfDt(actualDataFromDb.getAddrConfDt());
		} catch (Exception e) {
		}
		try {
			expected.setEmailConfDt(actualDataFromDb.getEmailConfDt());
		} catch (Exception e) {
		}
		try {
			expected.setBirthdayDt((actualDataFromDb.getBirthdayDt()));
		} catch (Exception e) {
		}
		try {
			expected.setReqDt((actualDataFromDb.getReqDt()));
		} catch (Exception e) {
		}
		
		try {
			expected.setStartDt((actualDataFromDb.getReqDt()));
		} catch (Exception e) {
		}
		try {
			expected.setEndDt((actualDataFromDb.getReqDt()));
		} catch (Exception e) {
		}
		try {
			expected.setProcDt((actualDataFromDb.getReqDt()));
		} catch (Exception e) {
		}
		
		try {
			expected.setLastUpdtTs((actualDataFromDb.getLastUpdtTs()));
		} catch (Exception e) {
		}
		
		try {
			expected.setRqstDt((actualDataFromDb.getRqstDt()));
		} catch (Exception e) {
		}
		try {
			expected.setFirstUpdtDt((actualDataFromDb.getRqstDt()));
		} catch (Exception e) {
		}
		try {
			expected.setLastUpdtDt((actualDataFromDb.getRqstDt()));
		} catch (Exception e) {
		}
		try {
			expected.setRptRqstRefCdSentDt((actualDataFromDb.getRqstDt()));
		} catch (Exception e) {
		}
		try {
			expected.setRptGenDt((actualDataFromDb.getRqstDt()));
		} catch (Exception e) {
		}
		try {
			expected.setOltpPurgeProcDt((actualDataFromDb.getRqstDt()));
		} catch (Exception e) {
		}
		try {
			expected.setCdpOltpPurgeProcDt((actualDataFromDb.getRqstDt()));
		} catch (Exception e) {
		}
		
		
		JSONObject jo = toJsonFromStrng(gson.toJson(expected));
		return jo;
	}

	public JSONObject toJsonFromStrng(String jsonString) throws org.json.simple.parser.ParseException {
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(jsonString);

	}

	public String getValueFromDataMap(Map<String, Object> dbDataMap, String keyName) {
		String toReturn = "";
		try {
			toReturn = dbDataMap.get(keyName).toString();
		} catch (Exception e) {
		}

		return toReturn;
	}

	public Customer setCustomerObject(SetCustTablesRequest actualRequest, boolean isExpected) {

		Map<String, Object> dbDataMap = new HashMap<>();
		String tableName = actualRequest.getTables().get(0).getName();
		List<Integer> custIds = new ArrayList<>();
		custIds.add(currentCustId);
		List<Integer> xtraCards = new ArrayList<>();
		xtraCards.add(currentXtraCard);
		TableColumnData currentDataFromRequest = actualRequest.getTables().get(0).getRows().get(0).getColData();

		List<Map<String, Object>> dbDataMapList = customerDao.selectDataFromCustomerTable(custIds, tableName);
		dbDataMap = dbDataMapList.get(0);
		customer.setCustId(Integer.parseInt(dbDataMap.get("CUST_ID").toString()));
		customer.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
		customer.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());
		// customer.setLastUpdtDt(getValueFromDataMap(dbDataMap,
		// "LAST_UPDT_DT").toString());
		customer.setGndrCd(getValueFromDataMap(dbDataMap, "GNDR_CD").toString());
		customer.setFirstName(getValueFromDataMap(dbDataMap, "FIRST_NAME").toString());
		customer.setMiddleInitialTxt(getValueFromDataMap(dbDataMap, "MIDDLE_INITIAL_TXT").toString());
		customer.setLastName(getValueFromDataMap(dbDataMap, "LAST_NAME").toString());
		customer.setSurName(getValueFromDataMap(dbDataMap, "SUR_NAME").toString());
		customer.setPfxTxt(getValueFromDataMap(dbDataMap, "PFX_TXT").toString());
		// customer.setBirthDt(getValueFromDataMap(dbDataMap, "BIRTH_DT").toString());
		customer.setSsn(getValueFromDataMap(dbDataMap, "SSN").toString());

		if (isExpected) {
			customer.setCustId(Integer.parseInt(currentDataFromRequest.getCustId().toString()));
			customer.setLastUpdtSrcCd(currentDataFromRequest.getLastUpdtSrcCd());
			customer.setLastUpdtById(currentDataFromRequest.getLastUpdtById());
			// customer.setLastUpdtDt(getValueFromDataMap(dbDataMap,
			// "LAST_UPDT_DT").toString());
			customer.setGndrCd(currentDataFromRequest.getGndrCd());
			customer.setFirstName(currentDataFromRequest.getFirstName());
			customer.setMiddleInitialTxt(currentDataFromRequest.getMiddleInitialTxt());
			customer.setLastName(currentDataFromRequest.getLastName());
			customer.setSurName(currentDataFromRequest.getSurName());
			customer.setPfxTxt(currentDataFromRequest.getPfxTxt());
			// customer.setBirthDt(currentDataFromRequest);
			customer.setSsn(currentDataFromRequest.getSsn());
		}

		return customer;

	}

	public CustomerAddress setCustomerAddressObject(SetCustTablesRequest actualRequest, boolean isExpected) {

		Map<String, Object> dbDataMap = new HashMap<>();
		String tableName = actualRequest.getTables().get(0).getName();
		List<Integer> custIds = new ArrayList<>();
		custIds.add(currentCustId);
		List<Integer> xtraCards = new ArrayList<>();
		xtraCards.add(currentXtraCard);
		TableColumnData currentDataFromRequest = actualRequest.getTables().get(0).getRows().get(0).getColData();

		List<Map<String, Object>> dbDataMapList = customerDao.selectDataFromCustomerTable(custIds, tableName);
		dbDataMap = dbDataMapList.get(0);
		customerAddress.setCustId(Integer.parseInt(dbDataMap.get("CUST_ID").toString()));
		customerAddress.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
		customerAddress.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());
		customerAddress.setAddrTypeCd(getValueFromDataMap(dbDataMap, "ADDR_TYPE_CD").toString());
		customerAddress
				.setAddrPrefSeqNbr(Integer.parseInt(getValueFromDataMap(dbDataMap, "ADDR_PREF_SEQ_NBR").toString()));
		customerAddress.setAddr1Txt(getValueFromDataMap(dbDataMap, "ADDR1_TXT").toString());
		customerAddress.setAddr2Txt(getValueFromDataMap(dbDataMap, "ADDR2_TXT").toString());
		customerAddress.setCityName(getValueFromDataMap(dbDataMap, "CITY_NAME").toString());
		customerAddress.setStCd(getValueFromDataMap(dbDataMap, "ST_CD").toString());
		customerAddress.setZipCd(getValueFromDataMap(dbDataMap, "ZIP_CD").toString());
		customerAddress.setZipSfxNbr(getValueFromDataMap(dbDataMap, "ZIP_SFX_NBR").toString());
		customerAddress.setAddrQltyCd(getValueFromDataMap(dbDataMap, "ADDR_QLTY_CD").toString());
		// customerAddress.setAddrConfDt(getValueFromDataMap(dbDataMap,
		// "ADDR_CONF_DT").toString());
		customerAddress.setEntryMethodCd(getValueFromDataMap(dbDataMap, "ENTRY_METHOD_CD").toString());

		if (isExpected) {
			customerAddress.setCustId(currentDataFromRequest.getCustId());
			customerAddress.setLastUpdtSrcCd(currentDataFromRequest.getLastUpdtSrcCd());
			customerAddress.setLastUpdtById(currentDataFromRequest.getLastUpdtById());
			customerAddress.setAddrTypeCd(currentDataFromRequest.getAddrTypeCd());
			customerAddress.setAddrPrefSeqNbr(Integer.parseInt(currentDataFromRequest.getAddrPrefSeqNbr()));
			customerAddress.setAddr1Txt(currentDataFromRequest.getAddr1Txt());
			customerAddress.setAddr2Txt(currentDataFromRequest.getAddr2Txt());
			customerAddress.setCityName(currentDataFromRequest.getCityName());
			customerAddress.setStCd(currentDataFromRequest.getStCd());
			customerAddress.setZipCd(currentDataFromRequest.getZipCd());
			customerAddress.setZipSfxNbr(currentDataFromRequest.getZipSfxNbr());
			customerAddress.setAddrQltyCd(currentDataFromRequest.getAddrQltyCd());
			// customerAddress.setAddrConfDt(currentDataFromRequest);
			customerAddress.setEntryMethodCd(currentDataFromRequest.getEntryMethodCd());
		}

		return customerAddress;

	}

	public CustomerEmail setCustomerEmailObject(SetCustTablesRequest actualRequest, boolean isExpected) {

		Map<String, Object> dbDataMap = new HashMap<>();
		String tableName = actualRequest.getTables().get(0).getName();
		List<Integer> custIds = new ArrayList<>();
		custIds.add(currentCustId);
		List<Integer> xtraCards = new ArrayList<>();
		xtraCards.add(currentXtraCard);
		TableColumnData currentDataFromRequest = actualRequest.getTables().get(0).getRows().get(0).getColData();

		List<Map<String, Object>> dbDataMapList = customerDao.selectDataFromCustomerTable(custIds, tableName);
		dbDataMap = dbDataMapList.get(0);
		customerEmail.setCustId(Integer.parseInt(dbDataMap.get("CUST_ID").toString()));
		customerEmail.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
		customerEmail.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());

		customerEmail
				.setEmailPrefSeqNbr(Integer.parseInt(getValueFromDataMap(dbDataMap, "EMAIL_PREF_SEQ_NBR").toString()));
		customerEmail.setEmailAddrTypeCd(getValueFromDataMap(dbDataMap, "EMAIL_ADDR_TYPE_CD").toString());
		customerEmail.setEmailStatusCd(getValueFromDataMap(dbDataMap, "EMAIL_STATUS_CD").toString());
		customerEmail.setEmailAddrTxt(getValueFromDataMap(dbDataMap, "EMAIL_ADDR_TXT").toString());
		customerEmail.setEmailConfDt(getValueFromDataMap(dbDataMap, "EMAIL_CONF_DT").toString());
		customerEmail.setEntryMethodCd(getValueFromDataMap(dbDataMap, "ENTRY_METHOD_CD").toString());
		customerEmail.setEmailSrcCd(getValueFromDataMap(dbDataMap, "EMAIL_SRC_CD").toString());

		if (isExpected) {
			customerEmail.setCustId(currentDataFromRequest.getCustId());
			customerEmail.setLastUpdtSrcCd(currentDataFromRequest.getLastUpdtSrcCd());
			customerEmail.setLastUpdtById(currentDataFromRequest.getLastUpdtById());

			customerEmail.setEmailPrefSeqNbr(Integer.parseInt(currentDataFromRequest.getEmailPrefSeqNbr().toString()));
			customerEmail.setEmailAddrTypeCd(currentDataFromRequest.getEmailAddrTypeCd());
			customerEmail.setEmailStatusCd(currentDataFromRequest.getEmailStatusCd());
			customerEmail.setEmailAddrTxt(currentDataFromRequest.getEmailAddrTxt());
			customerEmail.setEmailConfDt(currentDataFromRequest.getEmailConfDt());
			customerEmail.setEntryMethodCd(currentDataFromRequest.getEntryMethodCd());
			customerEmail.setEmailSrcCd(currentDataFromRequest.getEmailSrcCd());

		}

		return customerEmail;

	}

	public CustomerOpt setCustomerOptObject(SetCustTablesRequest actualRequest, boolean isExpected) {

		Map<String, Object> dbDataMap = new HashMap<>();
		String tableName = actualRequest.getTables().get(0).getName();
		List<Integer> custIds = new ArrayList<>();
		custIds.add(currentCustId);
		List<Integer> xtraCards = new ArrayList<>();
		xtraCards.add(currentXtraCard);
		TableColumnData currentDataFromRequest = actualRequest.getTables().get(0).getRows().get(0).getColData();

		List<Map<String, Object>> dbDataMapList = customerDao.selectDataFromCustomerTable(custIds, tableName);
		dbDataMap = dbDataMapList.get(0);
		customerOpt.setCustId(Integer.parseInt(dbDataMap.get("CUST_ID").toString()));
		customerOpt.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
		customerOpt.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());
		customerOpt.setOptTypeCd(getValueFromDataMap(dbDataMap, "OPT_TYPE_CD").toString());
		customerOpt.setOptCd(getValueFromDataMap(dbDataMap, "OPT_CD").toString());
		customerOpt.setOptSrcCd(getValueFromDataMap(dbDataMap, "OPT_SRC_CD").toString());

		if (isExpected) {
			customerOpt.setCustId(currentDataFromRequest.getCustId());
			customerOpt.setLastUpdtSrcCd(currentDataFromRequest.getLastUpdtSrcCd());
			customerOpt.setLastUpdtById(currentDataFromRequest.getLastUpdtById());

			customerOpt.setOptTypeCd(currentDataFromRequest.getOptTypeCd());
			customerOpt.setOptCd(currentDataFromRequest.getOptCd());
			customerOpt.setOptSrcCd(currentDataFromRequest.getOptSrcCd());

		}

		return customerOpt;

	}

	public CustomerPhone setCustomerPhoneObject(SetCustTablesRequest actualRequest, boolean isExpected) {

		Map<String, Object> dbDataMap = new HashMap<>();
		String tableName = actualRequest.getTables().get(0).getName();
		List<Integer> custIds = new ArrayList<>();
		custIds.add(currentCustId);
		List<Integer> xtraCards = new ArrayList<>();
		xtraCards.add(currentXtraCard);
		TableColumnData currentDataFromRequest = actualRequest.getTables().get(0).getRows().get(0).getColData();

		List<Map<String, Object>> dbDataMapList = customerDao.selectDataFromCustomerTable(custIds, tableName);
		dbDataMap = dbDataMapList.get(0);
		customerPhone.setCustId(Integer.parseInt(dbDataMap.get("CUST_ID").toString()));
		customerPhone.setLastUpdtSrcCd(getValueFromDataMap(dbDataMap, "LAST_UPDT_SRC_CD").toString());
		customerPhone.setLastUpdtById(getValueFromDataMap(dbDataMap, "LAST_UPDT_BY_ID").toString());

		customerPhone.setEntryMethodCd(getValueFromDataMap(dbDataMap, "ENTRY_METHOD_CD").toString());
		customerPhone
				.setPhonePrefSeqNbr(Integer.parseInt(getValueFromDataMap(dbDataMap, "PHONE_PREF_SEQ_NBR").toString()));
		customerPhone.setPhoneTypeCd(getValueFromDataMap(dbDataMap, "PHONE_TYPE_CD").toString());
		customerPhone
				.setPhoneAreaCdNbr(Integer.parseInt(getValueFromDataMap(dbDataMap, "PHONE_AREA_CD_NBR").toString()));
		customerPhone.setPhonePfxNbr(Integer.parseInt(getValueFromDataMap(dbDataMap, "PHONE_PFX_NBR").toString()));
		customerPhone.setPhoneSfxNbr(Integer.parseInt(getValueFromDataMap(dbDataMap, "PHONE_SFX_NBR").toString()));

		if (isExpected) {
			customerPhone.setCustId(currentDataFromRequest.getCustId());
			customerPhone.setLastUpdtSrcCd(currentDataFromRequest.getLastUpdtSrcCd());
			customerPhone.setLastUpdtById(currentDataFromRequest.getLastUpdtById());

			customerPhone.setEntryMethodCd(currentDataFromRequest.getEntryMethodCd());
			customerPhone.setPhonePrefSeqNbr(Integer.parseInt(currentDataFromRequest.getPhonePrefSeqNbr()));
			customerPhone.setPhoneTypeCd(currentDataFromRequest.getPhoneTypeCd());
			customerPhone.setPhoneAreaCdNbr(Integer.parseInt(currentDataFromRequest.getPhoneAreaCdNbr()));
			customerPhone.setPhonePfxNbr(Integer.parseInt(currentDataFromRequest.getPhonePfxNbr()));
			customerPhone.setPhoneSfxNbr(Integer.parseInt(currentDataFromRequest.getPhoneSfxNbr()));

		}

		return customerPhone;

	}

	public Integer getRowCountOfGivenTable(String tableName) {
		if(tableName.toLowerCase().contains("customer")) {
			return customerDao.getCountOfRecordsInCustomerTable(tableName, currentCustId);
		}else {
			return xtraCardDao.getCountOfRecordsInXtraCardTable(tableName, currentXtraCard);
		}
		

	}

}