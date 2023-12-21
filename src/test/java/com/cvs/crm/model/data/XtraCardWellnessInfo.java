package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardWellnessInfo {
    private Integer xtraCardNbr;
    private String wellnessInfoCd;
    private String lastUpdtSrcCd;
    private Date lastUpdtDt;
    private String lastUpdtById;
}
