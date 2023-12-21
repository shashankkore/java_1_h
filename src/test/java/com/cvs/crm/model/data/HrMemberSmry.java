package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class HrMemberSmry {
    private Integer ephLinkId;
    private Integer xtraCardNbr;
    private String enrollStatusCd;
    private Date enrollStatusTs;
    private String hippaStatusCd;
    private Date hippaSignTs;
    private Date hippaExpireDt;
    private String rxCapStatusCd;
    private Date rxCapTs;
    private Date lastUpdtTs;
}
