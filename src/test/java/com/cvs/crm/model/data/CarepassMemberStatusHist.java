package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class CarepassMemberStatusHist {
    private Integer xtraCardNbr;
    private Timestamp actionTswtz;
    private Date actionTswtzSetDt;
    private String mbrStatusCd;
    private String mbrStatusRsnCd;
}
