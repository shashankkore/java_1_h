package com.cvs.crm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

import java.util.List;

@Data
public class GetCustCusInfRespPushNotifsResponse {
    Integer cpnCnt;
    Integer msgId;
    Integer pushNotifSeqNbr;

}
