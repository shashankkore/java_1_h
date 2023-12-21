package com.cvs.crm.util;

import com.cvs.crm.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import com.cvs.crm.model.data.XtraParms;
import com.cvs.crm.repository.XtraCardDao;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;


@Service
@Data
@Slf4j
public class CacheRefreshUtil {
    private Response serviceResponse;
    @Autowired
    ServiceConfig serviceConfig;
    @Autowired
    XtraParms xtraParms;

    @Autowired
    XtraCardDao xtraCardDao;


    public void refresCacheforCmpgnDefns() {
        String msgSrcCd = "M";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        RequestSpecBuilder requestSpecBuilder1 = new RequestSpecBuilder();
        requestSpecBuilder1.setBaseUri(serviceConfig.getCmpgndefnUrl())
                .setBasePath("api/v1.1/campaign_definitions/caches/allCmpgns")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec1 = requestSpecBuilder1.build();
        serviceResponse = (Response) given().spec(spec1).when().put();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
      //  log.info("refresCacheforCmpgnDefns status code: [{}] ", sCode);
    }


    public void refresCacheforDashboardApi() {
        String msgSrcCd = "M";
        String userId = "CVS.COM";
        int srcLocCd = 90046;

        RequestSpecBuilder requestSpecBuilder1 = new RequestSpecBuilder();
        requestSpecBuilder1.setBaseUri(serviceConfig.getBaseUrl())
                .setBasePath("api/v1.1/customers/caches/cmpgnDefns")
                .addQueryParam("msg_src_cd", msgSrcCd)
                .addQueryParam("user_id", userId)
                .addQueryParam("src_loc_cd", srcLocCd);

        RequestSpecification spec1 = requestSpecBuilder1.build();
        serviceResponse = (Response) given().spec(spec1).when().put();

        int sCode = serviceResponse.getStatusCode();
        String res = serviceResponse.toString();
        //log.info("refresCacheforDashboardApi status code: [{}] ", sCode);
    }


    public void refresCacheusingXtraParms() throws InterruptedException {

        Thread.sleep(30000);
        //Xtra_Parms
        DateTime dateTimeXp = new DateTime();
        String todayDateXp = dateTimeXp.toString("yyyyMMdd");
        Integer sec = dateTimeXp.getSecondOfMinute();
        Integer min = dateTimeXp.getMinuteOfHour();
        Integer hour = dateTimeXp.getHourOfDay();
        String paddedMin;
        String paddedHour;
        String paddedSec;
        if (sec < 10) {
            paddedSec = String.format("%02d", sec);
        } else {
            paddedSec = Integer.toString(sec);
        }
        if (min < 10) {
            paddedMin = String.format("%02d", min);
        } else {
            paddedMin = Integer.toString(min);
        }
        if (hour < 10) {
            paddedHour = String.format("%02d", hour);
        } else {
            paddedHour = Integer.toString(hour);
        }
        String xpTime = todayDateXp + " " + paddedHour + ":" + paddedMin + ":" + paddedSec;
        xtraParms.setParmName("LST_CLONE_DT");
        xtraParms.setParmValueTxt(xpTime);
        xtraCardDao.updateXtraParms(xtraParms);

        Thread.sleep(30000);
    }
}