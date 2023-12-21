package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardChild {
    private Integer xtraCardNbr;
    private Date birthdayDt;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
}
