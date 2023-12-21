package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardSegment {
    private Integer xtraCardNbr;
    private Integer xtraCardSegNbr;
    private String ctlGrpCd;
}
