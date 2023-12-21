package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CustomerPhone {
    private Integer custId;
    private String phoneTypeCd;
    private Integer phonePrefSeqNbr;
    private Integer phoneAreaCdNbr;
    private Integer phonePfxNbr;
    private Integer phoneSfxNbr;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
    private String entryMethodCd;
}
