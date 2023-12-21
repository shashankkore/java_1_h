package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class HrEvent {
    private Integer eventId;
    private Integer eventYearNbr;
    private String eventDsc;
    private Date eventStartDt;
    private Date eventEndDt;
    private String hrEventTypeCd;
    private Integer thrshldLimNbr;
    private Integer rwrdMultFctr;
    private Integer cmpgnId;
}
