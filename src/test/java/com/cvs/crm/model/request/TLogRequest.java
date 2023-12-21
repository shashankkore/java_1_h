package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class TLogRequest {
    Integer encodedXtraCardNbr;
    Integer xtraCardNbr;
}
