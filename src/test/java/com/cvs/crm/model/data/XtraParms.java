package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class XtraParms {
    private String parmName;
    private String parmDesc;
    private Integer parmValueNbr;
    private String parmValueTxt;
    private String replicationCd;
}
