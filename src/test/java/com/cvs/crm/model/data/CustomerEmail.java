package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CustomerEmail {
    private Integer custId;
    private String emailAddrTypeCd;
    private Integer emailPrefSeqNbr;
    private String emailAddrTxt;
    private String emailStatusCd;
    private Integer qasSvcRspNbr;
    private Integer qasValidLvl;
    private Integer qasValidRspNbr;
    private String qasEmailCorrectXml;
    private String emailConfDt;
    private String EntryMethodCd;
    private String emailSrcCd;
    private String firstUpdtSrcCd;
    private Date firstUpdtDt;
    private String firstUpdtById;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
}