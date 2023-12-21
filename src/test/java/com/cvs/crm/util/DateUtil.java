package com.cvs.crm.util;

import com.cvs.crm.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class DateUtil {
    @Autowired
    ServiceConfig serviceConfig;


    //QEB data Methods
    public String campaignStartDate(Integer days) {
        DateTime dateTimeCp = new DateTime();
        Integer daysAdd = days;
        String campStartDate = dateTimeCp.minusDays(daysAdd).toString("yyyy-MM-dd");
        return campStartDate;
    }


    public String campaignEndDate(Integer days) {
        DateTime dateTimeCp = new DateTime();
        Integer daysSub = days;
        String campEndDate = dateTimeCp.plusDays(daysSub).toString("yyyy-MM-dd");
        return campEndDate;
    }

    public String campaignCouponStartDate() {
        DateTime dateTimeCp = new DateTime();
        String cmpgnCouponStartDt = dateTimeCp.plusDays(92).toString("yyyy-MM-dd");
        return cmpgnCouponStartDt;
    }

    public String campaignCouponEndDate() {
        DateTime dateTimeCp = new DateTime();
        String campEndDate = dateTimeCp.plusDays(390).toString("yyyy-MM-dd");
        return campEndDate;
    }

    public String timeStamp(int days, String pattern) {
        DateTime dateTime = new DateTime();
        String cpnDays  = dateTime.plusDays(days).toString(pattern);
        return cpnDays;
    }

    public String couponIssueDate(Integer days) {
        DateTime dateTimeCp = new DateTime();
        Integer subDays = days;
        String coupIssueDate = dateTimeCp.plusDays(subDays).toString("yyyy-MM-dd");
        return coupIssueDate;
    }

    public String firstIssueDate() {
        DateTime dateTimeCp = new DateTime();
        String firIssueDate = dateTimeCp.plusDays(93).toString("yyyy-MM-dd");
        return firIssueDate;
    }

    public String lastIssueDate() {
        DateTime dateTimeCp = new DateTime();
        String lasIssueDate = dateTimeCp.plusDays(93).toString("yyyy-MM-dd");
        return lasIssueDate;
    }

    public String previousIssueDate() {
        DateTime dateTimeCp = new DateTime();
        String prevIssueDate = dateTimeCp.plusDays(93).toString("yyyy-MM-dd");
        return prevIssueDate;
    }

    public String nextIssueDate() {
        DateTime dateTimeCp = new DateTime();
        String nexIssueDate = dateTimeCp.plusDays(94).toString("yyyy-MM-dd");
        return nexIssueDate;
    }

    public String inHomeDtDate() {
        DateTime dateTimeCp = new DateTime();
        String homDate = dateTimeCp.plusDays(104).toString("yyyy-MM-dd");
        return homDate;
    }

    public String rewLastCalcDtDate() {
        DateTime dateTimeCp = new DateTime();
        String rewLastDate = dateTimeCp.plusDays(105).toString("yyyy-MM-dd");
        return rewLastDate;
    }

    public String webDescriptionYear() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String webDescription = "This Quarter Spending for year";
        String webDesc = webDescription.replace("year", reYear);
        return webDesc;
    }

    public String recptRxtYear() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String recptRxtext = "Quarter year Spending";
        String recptRxt = recptRxtext.replace("year", reYear);
        return recptRxt;
    }

    public String cmpgnDscYear() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String cmpgnDescription = "year Q3 FS $";
        String cmpgnDsc = cmpgnDescription.replace("year", reYear);
        return cmpgnDsc;
    }

    public String cmpgnTermsTxtYear() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String CmpaignTermsTxt = "2% of your Quarter year Spend will be issued on 1st of next quarter.  Rewards are redeemable in-store and online.";
        String cmpgnTermsTxt = CmpaignTermsTxt.replace("year", reYear);
        return cmpgnTermsTxt;
    }

    public String cpnQualTxtYear() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String couponQualText = "Here are your ExtraBucks Rewards for your Quarter year Spending";
        String cpnQualTxt = couponQualText.replace("year", reYear);
        return cpnQualTxt;
    }

    public String cmpgnQualTxtYear() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String campaignQualTxt = "2% of your Quarter year Spend";
        String cmpgnQualTxt = campaignQualTxt.replace("year", reYear);
        return cmpgnQualTxt;
    }


// Previous Campaigns

    public String campaignStartDateExisting(Integer days) {
        DateTime dateTimeCp = new DateTime();
        Integer daysAdd = days;
        String campStartDateExisting = dateTimeCp.minusDays(91).toString("yyyy-MM-dd");
        return campStartDateExisting;
    }

    public String campaignEndDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String campEndDateExisting = dateTimeCp.minusDays(2).toString("yyyy-MM-dd");
        return campEndDateExisting;
    }


    public String campaignStartDateToday() {
        DateTime dateTimeCp = new DateTime();
        String campStartDateExisting = dateTimeCp.minusDays(0).toString("yyyy-MM-dd");
        return campStartDateExisting;
    }

    public String campaignEndDateToday() {
        DateTime dateTimeCp = new DateTime();
        String campEndDateExisting = dateTimeCp.plusDays(89).toString("yyyy-MM-dd");
        return campEndDateExisting;
    }

    public String campaignCouponStartDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String cmpgnCouponStartDtExisting = dateTimeCp.plusDays(1).toString("yyyy-MM-dd");
        return cmpgnCouponStartDtExisting;
    }

    public String campaignCouponEndDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String campEndDateExisting = dateTimeCp.plusDays(300).toString("yyyy-MM-dd");
        return campEndDateExisting;
    }


    public String couponIssueDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String coupIssueDateExisting = dateTimeCp.plusDays(14).toString("yyyy-MM-dd");
        return coupIssueDateExisting;
    }

    public String firstIssueDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String firIssueDateExisting = dateTimeCp.plusDays(3).toString("yyyy-MM-dd");
        return firIssueDateExisting;
    }

    public String lastIssueDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String lasIssueDateExisting = dateTimeCp.plusDays(3).toString("yyyy-MM-dd");
        return lasIssueDateExisting;
    }

    public String previousIssueDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String prevIssueDateExisting = dateTimeCp.plusDays(3).toString("yyyy-MM-dd");
        return prevIssueDateExisting;
    }

    public String nextIssueDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String nexIssueDateExisting = dateTimeCp.plusDays(4).toString("yyyy-MM-dd");
        return nexIssueDateExisting;
    }

    public String inHomeDtDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String homDateExisting = dateTimeCp.plusDays(14).toString("yyyy-MM-dd");
        return homDateExisting;
    }

    public String rewLastCalcDtDateExisting() {
        DateTime dateTimeCp = new DateTime();
        String rewLastDateExisting = dateTimeCp.plusDays(15).toString("yyyy-MM-dd");
        return rewLastDateExisting;
    }

    public String webDescriptionYearExisting() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String webDescription = "Previous Quarter Spending for year";
        String webDescExisting = webDescription.replace("year", reYear);
        return webDescExisting;
    }

    public String recptRxtYearExisting() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String recptRxtext = "Previous Quarter year Spending";
        String recptRxtExisting = recptRxtext.replace("year", reYear);
        return recptRxtExisting;
    }

    public String cmpgnDscYearExisting() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String cmpgnDescription = "previous year Q2 FS $";
        String cmpgnDscExisting = cmpgnDescription.replace("year", reYear);
        return cmpgnDscExisting;
    }

    public String cmpgnTermsTxtYearExisting() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String CmpaignTermsTxt = "2% of your Previous Quarter year Spend will be issued on 1st of next quarter.  Rewards are redeemable in-store and online.";
        String cmpgnTermsTxtExisting = CmpaignTermsTxt.replace("year", reYear);
        return cmpgnTermsTxtExisting;
    }

    public String cpnQualTxtYearExisting() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String couponQualText = "Here are your ExtraBucks Rewards for your Previous Quarter year Spending";
        String cpnQualTxtExisting = couponQualText.replace("year", reYear);
        return cpnQualTxtExisting;
    }

    public String cmpgnQualTxtYearExisting() {
        DateTime dateTime = new DateTime();
        Integer year = dateTime.getYear();
        String reYear = year.toString();
        String campaignQualTxt = "2% of Prev Quarter year Spend";
        String cmpgnQualTxtExisting = campaignQualTxt.replace("year", reYear);
        return cmpgnQualTxtExisting;
    }

    //DealsInProgress data Methods
    public String dealEndDate(Integer days) {
        DateTime dateTimeCp = new DateTime();
        Integer daysAdd = days;
        String dealEndDate = dateTimeCp.plusDays(daysAdd).toString("yyyy-MM-dd");
        return dealEndDate;
    }

    //DealsInProgress data Methods
    public String dealEndDateMinus(Integer days) {
        DateTime dateTimeCp = new DateTime();
        Integer daysAdd = days;
        String dealEndDate = dateTimeCp.minusDays(daysAdd).toString("yyyy-MM-dd");
        return dealEndDate;
    }

    public Timestamp setTswtzHour(int expDays, int amount) throws ParseException {
        int daysRemain = expDays;
        Timestamp timeStampwithZoneTswtz = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(timeStampwithZoneTswtz);
        cal.add(Calendar.DAY_OF_WEEK, daysRemain);
        cal.add(Calendar.MINUTE, amount);
        timeStampwithZoneTswtz.setTime(cal.getTime().getTime());
        return timeStampwithZoneTswtz;

    }
}