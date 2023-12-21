package com.cvs.crm.model.data;

import lombok.Data;
import oracle.jdbc.internal.OracleTimestampWithTimeZone;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class CampaignOMCoupon {
    private Integer cmpgnId;
    private Integer cpnNbr;
    private Timestamp mfrLastProTs;
    private String mfrOfferGuid;
    private Integer mfrOfferCd;
    private String mfrGs1Txt;
    private Timestamp mfrFirstIssueTswtz;
    private Timestamp mfrLastIssueTswtz;
    private String mfrLastRedeemTswtz;
    private String mfrOfferValueDsc;
    private String mfrOfferBrandName;
    private String mfrDisclaimerTxt;
    private Integer mfrLoadLimitCnt;
    private String mfrOfferSrcTxt;
    private String mfrLastUpdtTswtz;
    private Clob mfrUpc12ListJson;
    private Clob mfrSrcDataJson;
}
