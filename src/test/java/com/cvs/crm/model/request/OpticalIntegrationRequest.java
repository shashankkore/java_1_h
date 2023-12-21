package com.cvs.crm.model.request;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class OpticalIntegrationRequest {
	String userId;
	String password;
	String channel;
    String searchCardType;
    String searchCardNbr;
    String AuthToken;
    Integer searchPhoneNumber;
    String searchEmailAddress;

}
