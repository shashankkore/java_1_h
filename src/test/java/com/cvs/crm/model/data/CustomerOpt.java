package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CustomerOpt {
    private Integer custId;
    private String optTypeCd;
    private String optCd;
    private String optSrcCd;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
}
