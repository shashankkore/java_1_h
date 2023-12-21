package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardSelectCategory {
    private Integer xtraCardNbr;
    private String selectCatSeqNbr;
    private String selectCatNbr;
}
