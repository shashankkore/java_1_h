package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class HrEventDtl {
    private Date DateTs;
    private Integer storeNbr;
    private Integer regNbr;
    private Integer txnNbr;
    private String hrEventTypeCd;
    private Integer scriptNbr;
    private Integer scriptFillNbr;
    private Integer ephLinkId;
    private String idTypeCd;
    private Integer idNbr;
    private String eventEarnCd;
    private String eventEarnRsnCd;
    private Date loadTs;
    private String procStatusCd;
    private Date procStatusTs;
    private String lastUpdtSrcCd;
    private String lastUpdtById;
}
