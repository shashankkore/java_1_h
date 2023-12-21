package com.cvs.crm.service;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.model.data.XtraCard;
import com.cvs.crm.model.data.XtraCardRewardSummary;
import com.cvs.crm.model.data.XtraCardSegment;
import com.cvs.crm.model.data.Customer;
import com.cvs.crm.model.data.CustomerOpt;
import com.cvs.crm.model.data.Campaign;
import com.cvs.crm.model.data.XtraHotCard;
import com.cvs.crm.model.request.DashBoardRequest;
import com.cvs.crm.repository.CustomerDao;
import com.cvs.crm.repository.XtraCardDao;
import com.cvs.crm.repository.CampaignDao;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Data
public class XtraCardSummaryService {

    private Response serviceResponse;

    @Autowired
    Customer customer;

    @Autowired
    CustomerOpt customerOpt;

    @Autowired
    Campaign campaign;

    @Autowired
    XtraCard xtraCard;

    @Autowired
    XtraCardRewardSummary xtraCardRewardSummary;

    @Autowired
    XtraHotCard xtraHotCard;

    @Autowired
    XtraCardSegment xtraCardSegment;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    XtraCardDao xtraCardDao;

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    ServiceConfig serviceConfig;


    public void viewXtraCardSummary(DashBoardRequest dashBoardRequest) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String msgSrcCd;
        String userId;
        int srcLocCd;

        //TODO - We need a Utility Method to determine attributes
        if ("MOBILE".equalsIgnoreCase(dashBoardRequest.getChannel())) {
            msgSrcCd = "M";
            userId = "MOBILE_ENT";
            srcLocCd = 90042;
        } else {
            msgSrcCd = "W";
            userId = "CVS.COM";
            srcLocCd = 2695;
        }

        requestSpecBuilder.setBaseUri(serviceConfig.getBaseUrl())
                .setBasePath("api/v1.1/customers/{search_card_type},{search_card_nbr}/dashboards")
                .addPathParam("search_card_type", dashBoardRequest.getSearchCardType())
                .addPathParam("search_card_nbr", dashBoardRequest.getSearchCardNbr())
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);


        RequestSpecification spec = requestSpecBuilder.build();
        serviceResponse = (Response) given().spec(spec).when().get();
    }

    /**
     * Create Test Data For Xtra Card Summary Scenario
     */
    public void createXtraCardSummaryTestData() throws ParseException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;

        DateTime dateTime = new DateTime();
        String todayDate = dateTime.toString("yyyy-MM-dd");
        String yestardayDate = dateTime.minusDays(1).toString("yyyy-MM-dd");
        String tomorrowDate = dateTime.plusDays(1).toString("yyyy-MM-dd");
        String prev1yearDate = dateTime.minusYears(1).toString("yyyy-MM-dd");
        String future1yearDate = dateTime.plusYears(1).toString("yyyy-MM-dd");


        /*
         * Web - I recently joined as Extracare Member
         */


        xtraCard.setXtraCardNbr(98158260);
        xtraCard.setCustId(80000);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        //xtraCard.setCardMbrDt(simpleDateFormat.parse(todayDate));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(98158260);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80000);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);


        /*
         * Web - Existing Member from Feb 2020
         */

        xtraCard.setXtraCardNbr(98158261);
        xtraCard.setCustId(80001);
        xtraCard.setTotYtdSaveAmt(9.50);
        xtraCard.setTotLifetimeSaveAmt(10.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-02-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(7.00);
        xtraCardRewardSummary.setXtraCardNbr(98158261);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80001);
        customer.setGndrCd("M");
        customer.setFirstName("John1");
        customer.setLastName("Doe1");
        customerDao.createCustomer(customer);


        /*
         * Web - Existing Member from March 2018
         */

        xtraCard.setXtraCardNbr(98158262);
        xtraCard.setCustId(80002);
        xtraCard.setTotYtdSaveAmt(12.00);
        xtraCard.setTotLifetimeSaveAmt(50.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2018-03-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(9.00);
        xtraCardRewardSummary.setXtraCardNbr(98158262);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80002);
        customer.setGndrCd("M");
        customer.setFirstName("John2");
        customer.setLastName("Doe2");
        customerDao.createCustomer(customer);


        /*
         * Web - Existing Member from March 2013
         */

        xtraCard.setXtraCardNbr(98158263);
        xtraCard.setCustId(80003);
        xtraCard.setTotYtdSaveAmt(6.00);
        xtraCard.setTotLifetimeSaveAmt(50000.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2013-03-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(59.00);
        xtraCardRewardSummary.setXtraCardNbr(98158263);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80003);
        customer.setGndrCd("M");
        customer.setFirstName("John3");
        customer.setLastName("Doe3");
        customerDao.createCustomer(customer);

        /*
         *  Web - Existing Member from December 31 2014
         */
        xtraCard.setXtraCardNbr(98158264);
        xtraCard.setCustId(80004);
        xtraCard.setTotYtdSaveAmt(7.00);
        xtraCard.setTotLifetimeSaveAmt(100.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2014-12-31"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(38.00);
        xtraCardRewardSummary.setXtraCardNbr(98158264);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80004);
        customer.setGndrCd("M");
        customer.setFirstName("John4");
        customer.setLastName("Doe4");
        customerDao.createCustomer(customer);


        /*
         *  Web - I am an Existing Extracare Member and an Employee
         */

        xtraCard.setXtraCardNbr(98158271);
        xtraCard.setCustId(80011);
        xtraCard.setTotYtdSaveAmt(2.00);
        xtraCard.setTotLifetimeSaveAmt(10.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2019-12-31"));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(3.00);
        xtraCardRewardSummary.setXtraCardNbr(98158271);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80011);
        customer.setGndrCd("M");
        customer.setFirstName("John4");
        customer.setLastName("Doe4");
        customerDao.createCustomer(customer);


        /*
         * WEB- I am an Existing Extracare Member and have Caremark Customer Health Card
         */

        xtraCard.setXtraCardNbr(98158273);
        xtraCard.setCustId(80013);
        xtraCard.setTotYtdSaveAmt(17.00);
        xtraCard.setTotLifetimeSaveAmt(40.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2017-11-30"));
        xtraCard.setMktgTypeCd("H");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(8.00);
        xtraCardRewardSummary.setXtraCardNbr(98158273);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80013);
        customer.setGndrCd("M");
        customer.setFirstName("John4");
        customer.setLastName("Doe4");
        customerDao.createCustomer(customer);



        /*
         * Web - I will be a New Extracare Member planning to join from Jan 2nd 2021
         */
        xtraCard.setXtraCardNbr(98158275);
        xtraCard.setCustId(80015);
        xtraCard.setTotYtdSaveAmt(17.00);
        xtraCard.setTotLifetimeSaveAmt(40.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2021-01-02"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(8.00);
        xtraCardRewardSummary.setXtraCardNbr(98158275);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80015);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);


        /*
         * Web - I am an Existing Extracare Member since Jan 1st 2015
         */
        xtraCard.setXtraCardNbr(98158258);
        xtraCard.setCustId(79998);
        xtraCard.setTotYtdSaveAmt(7.00);
        xtraCard.setTotLifetimeSaveAmt(100.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2015-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(38.00);
        xtraCardRewardSummary.setXtraCardNbr(98158258);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(79998);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        /*
         * Web - I am an Existing Extracare Member since Jun 28th 2015
         */
        xtraCard.setXtraCardNbr(98158259);
        xtraCard.setCustId(79999);
        xtraCard.setTotYtdSaveAmt(7.00);
        xtraCard.setTotLifetimeSaveAmt(100.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2015-06-28"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(38.00);
        xtraCardRewardSummary.setXtraCardNbr(98158259);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(79999);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);






        /*
         * Mobile - I recently joined as Extracare Member
         */
        xtraCard.setXtraCardNbr(98158265);
        xtraCard.setCustId(80005);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(98158265);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80005);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);


        /*
         * Mobile - Existing Member from Feb 2020
         */
        xtraCard.setXtraCardNbr(98158266);
        xtraCard.setCustId(80006);
        xtraCard.setTotYtdSaveAmt(9.50);
        xtraCard.setTotLifetimeSaveAmt(10.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-02-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(7.00);
        xtraCardRewardSummary.setXtraCardNbr(98158266);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80006);
        customer.setGndrCd("M");
        customer.setFirstName("John1");
        customer.setLastName("Doe1");
        customerDao.createCustomer(customer);


        /*
         * Mobile - Existing Member from March 2018
         */
        xtraCard.setXtraCardNbr(98158267);
        xtraCard.setCustId(80007);
        xtraCard.setTotYtdSaveAmt(12.00);
        xtraCard.setTotLifetimeSaveAmt(50.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2018-03-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(9.00);
        xtraCardRewardSummary.setXtraCardNbr(98158267);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80007);
        customer.setGndrCd("M");
        customer.setFirstName("John2");
        customer.setLastName("Doe2");
        customerDao.createCustomer(customer);


        /*
         * Mobile - Existing Member from March 2013
         */
        xtraCard.setXtraCardNbr(98158268);
        xtraCard.setCustId(80008);
        xtraCard.setTotYtdSaveAmt(6.00);
        xtraCard.setTotLifetimeSaveAmt(50000.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2013-03-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(59.00);
        xtraCardRewardSummary.setXtraCardNbr(98158268);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80008);
        customer.setGndrCd("M");
        customer.setFirstName("John3");
        customer.setLastName("Doe3");
        customerDao.createCustomer(customer);

        /*
         *  Mobile - Existing Member from December 31 2014
         */
        xtraCard.setXtraCardNbr(98158269);
        xtraCard.setCustId(80009);
        xtraCard.setTotYtdSaveAmt(7.00);
        xtraCard.setTotLifetimeSaveAmt(100.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2014-12-31"));
        xtraCard.setMktgTypeCd("H");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(38.00);
        xtraCardRewardSummary.setXtraCardNbr(98158269);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80009);
        customer.setGndrCd("M");
        customer.setFirstName("John4");
        customer.setLastName("Doe4");
        customerDao.createCustomer(customer);


        /*
         *  Mobile - I am an Existing Extracare Member and an Employee
         */
        xtraCard.setXtraCardNbr(98158270);
        xtraCard.setCustId(80010);
        xtraCard.setTotYtdSaveAmt(2.00);
        xtraCard.setTotLifetimeSaveAmt(10.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2019-12-31"));
        xtraCard.setMktgTypeCd("E");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(3.00);
        xtraCardRewardSummary.setXtraCardNbr(98158270);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80010);
        customer.setGndrCd("M");
        customer.setFirstName("John4");
        customer.setLastName("Doe4");
        customerDao.createCustomer(customer);


        /*
         * Mobile- I am an Existing Extracare Member and have Caremark Customer Health Card
         */

        xtraCard.setXtraCardNbr(98158272);
        xtraCard.setCustId(80012);
        xtraCard.setTotYtdSaveAmt(17.00);
        xtraCard.setTotLifetimeSaveAmt(40.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2017-11-30"));
        xtraCard.setMktgTypeCd("H");
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(8.00);
        xtraCardRewardSummary.setXtraCardNbr(98158272);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80012);
        customer.setGndrCd("M");
        customer.setFirstName("John4");
        customer.setLastName("Doe4");
        customerDao.createCustomer(customer);

        /*
         *  Mobile - I am a fraudulent user, I would like to see my Dashboard
         */
        xtraHotCard.setXtraCardNbr(98158274);
        xtraHotCard.setAddedDt(simpleDateFormat.parse("2017-11-30"));
        xtraCardDao.createXtraHotCard(xtraHotCard);


        /*
         * Web -I am existing Default BC Customer and want to see earningsType indicator as D
         */


        xtraCard.setXtraCardNbr(900058661);
        xtraCard.setCustId(80631);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(900058661);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80631);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerOpt.setCustId(80631);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);


        /*
         * Web - I am existing XC customer but never enrolled in any BC program and want to see earningsType indicator as D
         */

        
        xtraCard.setXtraCardNbr(900058662);
        xtraCard.setCustId(80632);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(900058662);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80632);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);



        /*
         * Web - I am new XC Customer and enrolled in 10% BC program and want to see earningsType indicator as P
         */


        xtraCard.setXtraCardNbr(900058663);
        xtraCard.setCustId(80633);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(900058663);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80633);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerOpt.setCustId(80633);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

		/*
		 * Integer perSeg = campaignDao.selectPercentBCSegCampaign(campaign);
		 * xtraCardSegment.setXtraCardNbr(900058663);
		 * xtraCardSegment.setXtraCardSegNbr(perSeg); xtraCardSegment.setCtlGrpCd(null);
		 * xtraCardDao.createXtraCardSegment(xtraCardSegment);
		 */

        /*
         * Web - I am new XC Customer and enrolled in Free Item BC program and want to see earningsType indicator as P
         */


        xtraCard.setXtraCardNbr(900058664);
        xtraCard.setCustId(80634);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(900058664);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80634);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);

        customerOpt.setCustId(80634);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

		/*
		 * Integer freeSeg = campaignDao.selectFreeBCSegCampaign(campaign);
		 * xtraCardSegment.setXtraCardNbr(900058664);
		 * xtraCardSegment.setXtraCardSegNbr(freeSeg);
		 * xtraCardSegment.setCtlGrpCd(null);
		 * xtraCardDao.createXtraCardSegment(xtraCardSegment);
		 */

        /*
         * Web - I am new XC Customer and enrolled in both Free Item and 10% BC programs and want to see earningsType indicator as P
         */


        xtraCard.setXtraCardNbr(900058665);
        xtraCard.setCustId(80635);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(900058665);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80635);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);


        customerOpt.setCustId(80635);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("Y");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

		/*
		 * xtraCardSegment.setXtraCardNbr(900058665);
		 * xtraCardSegment.setXtraCardSegNbr(freeSeg);
		 * xtraCardSegment.setCtlGrpCd(null);
		 * xtraCardDao.createXtraCardSegment(xtraCardSegment);
		 * 
		 * xtraCardSegment.setXtraCardNbr(900058665);
		 * xtraCardSegment.setXtraCardSegNbr(perSeg); xtraCardSegment.setCtlGrpCd(null);
		 * xtraCardDao.createXtraCardSegment(xtraCardSegment);
		 */


        /*
         * Web - I am XC Customer I have unenrolled from both Free Item and 10% BC programs and want to see earningsType indicator as D
         */


        xtraCard.setXtraCardNbr(900058666);
        xtraCard.setCustId(80636);
        xtraCard.setTotYtdSaveAmt(0.00);
        xtraCard.setTotLifetimeSaveAmt(0.00);
        xtraCard.setCardMbrDt(simpleDateFormat.parse("2020-01-01"));
        xtraCardDao.createXtraCard(xtraCard);

        xtraCardRewardSummary.setTotEbAvlAmt(0.00);
        xtraCardRewardSummary.setXtraCardNbr(900058666);
        xtraCardDao.createXtraCardRewardSummary(xtraCardRewardSummary);

        customer.setCustId(80636);
        customer.setGndrCd("M");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerDao.createCustomer(customer);


        customerOpt.setCustId(80636);
        customerOpt.setLastUpdtById("testauto");
        customerOpt.setLastUpdtDt(simpleDateFormat.parse(prev1yearDate));
        customerOpt.setLastUpdtSrcCd("001");
        customerOpt.setOptCd("N");
        customerOpt.setOptSrcCd(null);
        customerOpt.setOptTypeCd("28");
        customerDao.createCustomerOpt(customerOpt);

    }

    /**
     * Delete Test Data for Xtra Card Summary Scenario
     */
    public void deleteXtraCardSummaryTestData() {
  /*
    Purge All Test Cards
     */
        List<Integer> xtraCardNbrList = Arrays.asList(98158258, 98158259, 98158260, 98158261, 98158262, 98158263, 98158264, 98158265, 98158266, 98158267, 98158268, 98158269, 98158270, 98158271, 98158272, 98158273, 98158274, 98158275, 900058661, 900058662, 900058663, 900058664, 900058665, 900058666);
        List<Integer> custIdList = Arrays.asList(79998, 79999, 80000, 80001, 80002, 80003, 80004, 80005, 80006, 80007, 80008, 80009, 80010, 80011, 80012, 80013, 80014, 80015, 80631, 80632, 80633, 80634, 80635, 80636);


        xtraCardDao.deleteXtraCards(xtraCardNbrList);
        customerDao.deleteCustomers(custIdList);
    }
}
