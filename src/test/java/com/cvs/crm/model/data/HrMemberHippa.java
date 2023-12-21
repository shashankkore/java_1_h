package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class HrMemberHippa {
    private Date hippaSignTs;
    private String idTypeCd;
    private Integer idNbr;
    private String hippaStatusCd;
    private String hippaStatusRsnCd;
    private Date HippaStatusTs;
    private Date hippaExpireDt;
    private Integer hippaFormVerNbr;
    private String hippaFormLocCd;
    private Integer ephLinkId;
    private String hippaSignByCd;
    private String esigFmtCd;
    private String esigBlob;
}
