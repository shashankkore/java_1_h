package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class HrMemberEnroll {
    private Integer ephLinkId;
    private Date enrollStatusTs;
    private String enrollStatusCd;
    private String idTypeCd;
    private Integer idNbr;
    private Integer xtraCardNbr;
    private String enrollSrcCd;
    private Integer enrollStoreNbr;
    private Integer enrollSrcAcctNbr;
}
