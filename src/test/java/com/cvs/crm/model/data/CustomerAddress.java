package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CustomerAddress {
    private Integer custId;
    private String addrTypeCd;
    private Integer addrPrefSeqNbr;
    private String addr1Txt;
    private String addr2Txt;
    private String cityName;
    private String stCd;
    private String zipCd;
    private String zipSfxNbr;
    private String addrQltyCd;
    private Integer Latitude;
    private Integer longitude;
    private String latLonPrecisionCd;
    private Date addrConfDt;
    private String entryMethodCd;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
}
