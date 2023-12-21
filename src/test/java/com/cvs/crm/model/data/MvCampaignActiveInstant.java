package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class MvCampaignActiveInstant {
    private Integer cmpgnId;
    private String cmpgnTypeCd;
    private String cmpgnSubTypeCd;
    private Integer recptScaleNbr;
    private Integer recptPrntPriorityNbr;
    private String recptPrntInd;
    private Date startDt;
    private Date endDt;
    private String recptTxt;
    private String webDesc;
    private String cmpgnTermsTxt;
    private String webDispInd;
    private String mobileDispInd;
    private String cmpgnQualTxt;
    private Integer maxRwrdQty;
    private String mktgPrgCd;
}
