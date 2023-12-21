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
import com.cvs.crm.model.request.SetCustRequest;
import com.cvs.crm.repository.CarePassDao;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.util.CacheRefreshUtil;
import com.cvs.crm.util.CarePassDateUtil;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import com.cvs.crm.util.SetCustPreferencesUtil;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class SetCustPreferencesService {

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
	SetCustPreferencesUtil setCustPreferencesUtil;

	public void viewSetCustPreferences(SetCustRequest setCustRequest, String type, String opt_cd)
			throws ParseException {
		Integer cardNum = setCustRequest.getSearchCardNbr();
		String cardTyp = setCustRequest.getSearchCardType();
		String enrollType = type;
		String msgSrcCd = null;
		String userId = null;
		int srcLocCd = 0;
		if ("MOBILE".equalsIgnoreCase(setCustRequest.getChannel())) {
			msgSrcCd = "M";
			userId = "MOBILE_ENT";
			srcLocCd = 90042;
		} else if ("WEB".equalsIgnoreCase(setCustRequest.getChannel())) {
			msgSrcCd = "W";
			userId = "CVS.COM";
			srcLocCd = 2695;
		} else if ("POS".equalsIgnoreCase(setCustRequest.getChannel())) {
			msgSrcCd = "R";
			userId = "store";
			srcLocCd = 90037;
		}

		String jsonString = null;
		String opt = opt_cd;
		if (enrollType.equals("beautyClub")) {
			jsonString = "{\r\n" + " \"xtraCare\": {\n" + " \"prefs\" : {\n" + " \"beautyClub\": {\n" + " \"optCd\": \""
					+ opt + "\"\n" + " }\n" + " }\n" + " },\n" + " \"deviceInfo\": {\n" + " \"deviceTypeCd\": \"\",\n"
					+ " \"deviceVerCd\": \"\",\n" + " \"deviceUniqIdTypeCd\": \"\",\n" + " \"deviceUniqId\": \"\",\n"
					+ " \"appVer\": \"\"\n" + " }\n" + "}";

			RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
			requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
					.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
					.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
					.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
					.addQueryParam("src_loc_cd", srcLocCd);

			RequestSpecification spec2 = requestSpecBuilder2.build();
			serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

			int sCode = serviceResponse.getStatusCode();
			String res = serviceResponse.toString();
			log.info("SetCustEnrollBeautyClub code: [{}] ", sCode);
		} else if (enrollType.equals("beautyNotes")) {
			jsonString = "{\r\n" + " \"xtraCare\": {\n" + " \"prefs\" : {\n" + " \"beautyNotes\": {\n"
					+ " \"optCd\": \"" + opt + "\"\n" + " }\n" + " }\n" + " },\n" + " \"deviceInfo\": {\n"
					+ " \"deviceTypeCd\": \"\",\n" + " \"deviceVerCd\": \"\",\n" + " \"deviceUniqIdTypeCd\": \"\",\n"
					+ " \"deviceUniqId\": \"\",\n" + " \"appVer\": \"\"\n" + " }\n" + "}";
			RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
			requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
					.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
					.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
					.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
					.addQueryParam("src_loc_cd", srcLocCd);

			RequestSpecification spec2 = requestSpecBuilder2.build();
			serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

			int sCode = serviceResponse.getStatusCode();
			String res = serviceResponse.toString();
			log.info("SetCustEnrollbeautyNotes code: [{}] ", sCode);
		} else if (enrollType.equals("paperlessCpns")) {
			jsonString = "{\r\n" + " \"xtraCare\": {\n" + " \"prefs\" : {\n" + " \"paperlessCpns\": {\n"
					+ " \"optCd\": \"" + opt + "\"\n" + " }\n" + " }\n" + " },\n" + " \"deviceInfo\": {\n"
					+ " \"deviceTypeCd\": \"\",\n" + " \"deviceVerCd\": \"\",\n" + " \"deviceUniqIdTypeCd\": \"\",\n"
					+ " \"deviceUniqId\": \"\",\n" + " \"appVer\": \"\"\n" + " }\n" + "}";

			RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
			requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
					.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
					.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
					.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
					.addQueryParam("src_loc_cd", srcLocCd);

			RequestSpecification spec2 = requestSpecBuilder2.build();
			serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

			int sCode = serviceResponse.getStatusCode();
			String res = serviceResponse.toString();
			log.info("SetCustEnrollpaperlessCpns code: [{}] ", sCode);
		} else if (enrollType.equals("sms")) {
			jsonString = "{\r\n" + " \"xtraCare\": {\n" + " \"prefs\" : {\n" + " \"sms\": {\n" + " \"optCd\": \"" + opt
					+ "\",\n" + " \"lastUpdtById\":\"000111\"\n" + " }\n" + " }\n" + " },\n" + " \"deviceInfo\": {\n"
					+ " \"deviceTypeCd\": \"\",\n" + " \"deviceVerCd\": \"\",\n" + " \"deviceUniqIdTypeCd\": \"\",\n"
					+ " \"deviceUniqId\": \"\",\n" + " \"appVer\": \"\"\n" + " }\n" + "}";

			RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
			requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
					.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
					.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
					.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
					.addQueryParam("src_loc_cd", srcLocCd);

			RequestSpecification spec2 = requestSpecBuilder2.build();
			serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

			int sCode = serviceResponse.getStatusCode();
			String res = serviceResponse.toString();
			log.info("SetCustEnrollsms code: [{}] ", sCode);
		} else if (enrollType.equals("optInEc")) {
			jsonString = "{\r\n" + " \"xtraCare\": {\n" + " \"prefs\" : {\n" + " \"mail\": {\n" + " \"optCd\": \"" + opt
					+ "\",\n" + " \"lastUpdtById\":\"000111\",\n" + " \"lastUpdtSrcCd\":\"001\"\n" + " }\n" + " }\n"
					+ " },\n" + " \"deviceInfo\": {\n" + " \"deviceTypeCd\": \"\",\n" + " \"deviceVerCd\": \"\",\n"
					+ " \"deviceUniqIdTypeCd\": \"\",\n" + " \"deviceUniqId\": \"\",\n" + " \"appVer\": \"\"\n" + " }\n"
					+ "}";

			RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
			requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
					.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
					.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
					.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
					.addQueryParam("src_loc_cd", srcLocCd);

			RequestSpecification spec2 = requestSpecBuilder2.build();
			serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

			int sCode = serviceResponse.getStatusCode();
			String res = serviceResponse.toString();
			log.info("SetCustEnrolloptInEc code: [{}] ", sCode);
		} else if (enrollType.equals("optInEmail")) {
			jsonString = "{\r\n" + " \"xtraCare\": {\n" + " \"prefs\" : {\n" + " \"email\": {\n" + " \"optCd\": \""
					+ opt + "\",\n" + " \"lastUpdtById\":\"000111\",\n" + " \"lastUpdtSrcCd\":\"001\"\n" + " }\n"
					+ " }\n" + " },\n" + " \"deviceInfo\": {\n" + " \"deviceTypeCd\": \"\",\n"
					+ " \"deviceVerCd\": \"\",\n" + " \"deviceUniqIdTypeCd\": \"\",\n" + " \"deviceUniqId\": \"\",\n"
					+ " \"appVer\": \"\"\n" + " }\n" + "}";

			RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
			requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
					.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
					.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
					.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
					.addQueryParam("src_loc_cd", srcLocCd);

			RequestSpecification spec2 = requestSpecBuilder2.build();
			serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

			int sCode = serviceResponse.getStatusCode();
			String res = serviceResponse.toString();
			log.info("SetCustEnrolloptInEmail code: [{}] ", sCode);
		} else if (enrollType.equals("DR")) {
			jsonString = "{\r\n" + " \"xtraCare\": {\n" + " \"prefs\" : {\n" + " \"digitalReceipt\": {\n"
					+ " \"optCd\": \"" + opt + "\",\n" + " \"lastUpdtById\":\"000111\",\n"
					+ " \"lastUpdtSrcCd\":\"001\"\n" + " }\n" + " }\n" + " },\n" + " \"deviceInfo\": {\n"
					+ " \"deviceTypeCd\": \"\",\n" + " \"deviceVerCd\": \"\",\n" + " \"deviceUniqIdTypeCd\": \"\",\n"
					+ " \"deviceUniqId\": \"\",\n" + " \"appVer\": \"\"\n" + " }\n" + "}";

			RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
			requestSpecBuilder2.setBaseUri(serviceConfig.getSetcustUrl())
					.setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}")
					.addPathParam("search_card_type", cardTyp).addPathParam("search_card_nbr", cardNum)
					.addQueryParam("msg_src_cd", msgSrcCd).addQueryParam("user_id", userId)
					.addQueryParam("src_loc_cd", srcLocCd);

			RequestSpecification spec2 = requestSpecBuilder2.build();
			serviceResponse = (Response) given().spec(spec2).contentType("application/json").body(jsonString).patch();

			int sCode = serviceResponse.getStatusCode();
			String res = serviceResponse.toString();
			log.info("SetCustEnroll code: [{}] ", sCode);
		}

	}

	/**
	 * Create Test Data For SetCust Preferences Scenario
	 *
	 * @param
	 * @throws InterruptedException
	 */
	public void createSetCustPreferencesTestData() throws ParseException, InterruptedException {
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

		/*
		 * I am an Existing XtraCare Customer and want to save my preferences
		 * information with beautyClub enrolled as true
		 *
		 */

		xtraCard.setXtraCardNbr(900058444);
		xtraCard.setCustId(80178);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058444);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80178);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80178);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80178);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80178);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an Existing XtraCare Customer and want to save my preferences
		 * information with beautyClub enrolled as true
		 *
		 */

		xtraCard.setXtraCardNbr(900058445);
		xtraCard.setCustId(80179);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058445);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80179);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80179);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80179);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80179);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a new XtraCare Customer and want to save my preferences information with
		 * paperlessCpns enrolled as true
		 *
		 */

		xtraCard.setXtraCardNbr(900058447);
		xtraCard.setCustId(80181);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058447);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80181);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80181);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80181);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80181);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a New XtraCare Customer and want to save my preferences information with
		 * beautyNotes enrolled as false
		 *
		 */

		xtraCard.setXtraCardNbr(900058449);
		xtraCard.setCustId(80183);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058449);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80183);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80183);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80183);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80183);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a new XtraCare Customer and want to save my preferences information with
		 * paperlessCpns enrolled as false
		 *
		 */

		xtraCard.setXtraCardNbr(900058451);
		xtraCard.setCustId(80185);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058451);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80185);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80185);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80185);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80185);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a new XtraCare Customer and want to save my preferences information with
		 * sms enrolled as false
		 */
		xtraCard.setXtraCardNbr(900058453);
		xtraCard.setCustId(80187);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058453);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80187);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80187);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80187);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80187);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an Existing XtraCare Customer and want to save my preferences
		 * information with optInEc enrolled as true
		 */
		xtraCard.setXtraCardNbr(900058454);
		xtraCard.setCustId(80188);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058454);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80188);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80188);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80188);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80188);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a new XtraCare Customer and want to save my preferences information with
		 * sms enrolled as true
		 */
		xtraCard.setXtraCardNbr(900058455);
		xtraCard.setCustId(80189);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058455);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80189);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80189);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80189);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80189);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a Existing XtraCare Customer and want to save my preferences information
		 * with optInEc enrolled as false
		 */
		xtraCard.setXtraCardNbr(900058456);
		xtraCard.setCustId(80190);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058456);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80190);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80190);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80190);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80190);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am a new XtraCare Customer and want to save my preferences information with
		 * optInEmail as A
		 */

		xtraCard.setXtraCardNbr(900058457);
		xtraCard.setCustId(80191);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058457);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80191);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerPhone.setCustId(80191);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80191);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an XtraCare Customer and want to fetch my preferences information with
		 * digitalReceipt as N
		 */
		xtraCard.setXtraCardNbr(900058459);
		xtraCard.setCustId(80193);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058459);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80193);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80193);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80193);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80193);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an XtraCare Customer and want to fetch my preferences information with
		 * digitalReceipt as N
		 */
		xtraCard.setXtraCardNbr(900058460);
		xtraCard.setCustId(80194);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058460);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80194);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80194);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80194);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80194);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an XtraCare Customer and want to fetch my preferences information with
		 * digitalReceipt as N
		 */
		xtraCard.setXtraCardNbr(900058461);
		xtraCard.setCustId(80195);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058461);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80195);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80195);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80195);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80195);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		/*
		 * I am an XtraCare Customer and want to fetch my preferences information with
		 * digitalReceipt as D
		 */
		xtraCard.setXtraCardNbr(900058462);
		xtraCard.setCustId(80196);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058462);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80196);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80196);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80196);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80196);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058463);
		xtraCard.setCustId(80197);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058463);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80197);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80197);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80197);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80197);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058464);
		xtraCard.setCustId(80198);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058464);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80198);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80198);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80198);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80198);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058465);
		xtraCard.setCustId(80199);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058465);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80199);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80199);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80199);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80199);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058466);
		xtraCard.setCustId(80200);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058466);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80200);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80200);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80200);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80200);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058467);
		xtraCard.setCustId(80201);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058467);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80201);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80201);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80201);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80201);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058468);
		xtraCard.setCustId(80202);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058468);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80202);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80202);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80202);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80202);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058469);
		xtraCard.setCustId(80203);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058469);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80203);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80203);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80203);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80203);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058470);
		xtraCard.setCustId(80204);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058470);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80204);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80204);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80204);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80204);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058471);
		xtraCard.setCustId(80205);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058471);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80205);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80205);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80205);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80205);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058472);
		xtraCard.setCustId(80206);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058472);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80206);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80206);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80206);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80206);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058473);
		xtraCard.setCustId(80207);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058473);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80207);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80207);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80207);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80207);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058474);
		xtraCard.setCustId(80208);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058474);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80208);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80208);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80208);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80208);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058475);
		xtraCard.setCustId(80209);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058475);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80209);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80209);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80209);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80209);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058476);
		xtraCard.setCustId(80210);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058476);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80210);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80210);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80210);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80210);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058477);
		xtraCard.setCustId(80211);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058477);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80211);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80211);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80211);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80211);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058478);
		xtraCard.setCustId(80212);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058478);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80212);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80212);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80212);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80212);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058479);
		xtraCard.setCustId(80213);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058479);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80213);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80213);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80213);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80213);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058480);
		xtraCard.setCustId(80214);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058480);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80214);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80214);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80214);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80214);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058481);
		xtraCard.setCustId(80215);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058481);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80215);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80215);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80215);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80215);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058482);
		xtraCard.setCustId(80216);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058482);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80216);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80216);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80216);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80216);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058483);
		xtraCard.setCustId(80217);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058483);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80217);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80217);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80217);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80217);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058484);
		xtraCard.setCustId(80218);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058484);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80218);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80218);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80218);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80218);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058485);
		xtraCard.setCustId(80219);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058485);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80219);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80219);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80219);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80219);
		customerAddress.setAddrTypeCd("H");
		customerAddress.setAddrPrefSeqNbr(1);
		customerAddress.setAddr1Txt("951 YORK RD #106");
		customerAddress.setAddr2Txt(" ");
		customerAddress.setCityName("PARMA HTS");
		customerAddress.setStCd("OH");
		customerAddress.setZipCd("44130");
		customerDao.createCustomerAddress(customerAddress);

		xtraCard.setXtraCardNbr(900058486);
		xtraCard.setCustId(80220);
		xtraCard.setTotYtdSaveAmt(0.00);
		xtraCard.setTotLifetimeSaveAmt(0.00);
		xtraCard.setCardMbrDt(simpleDateFormat.parse(prev1yearDate));
		xtraCard.setMktgTypeCd(" ");
		xtraCardDao.createXtraCard(xtraCard);

		xtraCard.setEncodedXtraCardNbr(900058486);
		xtraCardDao.updateXtraCard(xtraCard);

		customer.setCustId(80220);
		customer.setGndrCd("M");
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customerDao.createCustomer(customer);

		customerEmail.setCustId(80220);
		customerEmail.setEmailAddrTypeCd("H");
		customerEmail.setEmailPrefSeqNbr(1);
		customerEmail.setEmailAddrTxt("abc@CVS.com");
		customerEmail.setEmailStatusCd("A");
		customerDao.createCustomerEmail(customerEmail);

		customerPhone.setCustId(80220);
		customerPhone.setPhoneTypeCd("H");
		customerPhone.setPhonePrefSeqNbr(1);
		customerPhone.setPhoneAreaCdNbr(123);
		customerPhone.setPhonePfxNbr(456);
		customerPhone.setPhoneSfxNbr(7890);
		customerDao.createCustomerPhone(customerPhone);

		customerAddress.setCustId(80220);
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
	 * Delete Test Data for SetCust Preferences
	 */
	public void deleteSetCustPreferencesTestData() {
		/*
		 * Purge All Test Cards
		 */
		List<Integer> xtraCardNbrList = Arrays.asList(900058444, 900058445, 900058446, 900058447, 900058448, 900058449,
				900058450, 900058451, 900058452, 900058453, 900058454, 900058455, 900058456, 900058457, 900058458,
				900058459, 900058460, 900058461, 900058462, 900058463, 900058464, 900058465, 900058466, 900058467,
				900058468, 900058469, 900058470, 900058471, 900058472, 900058473, 900058474, 900058475, 900058476,
				900058477, 900058478, 900058479, 900058480, 900058481, 900058482, 900058483, 900058484, 900058485,
				900058486);
		List<Integer> custIdList = Arrays.asList(80178, 80179, 80180, 80181, 80182, 80183, 80184, 80185, 80186, 80187,
				80188, 80189, 80190, 80191, 80192, 80193, 80194, 80195, 80196, 80197, 80198, 80199, 80200, 80201, 80202,
				80203, 80204, 80205, 80206, 80207, 80208, 80209, 80210, 80211, 80212, 80213, 80214, 80215, 80216, 80217,
				80218, 80219, 80220);

		customerDao.deleteCustomers(custIdList);
		xtraCardDao.deleteXtraCards(xtraCardNbrList);
		carePassDao.deleteCarePass(xtraCardNbrList);
	}
}
