package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Data
public class HrMemberProfile {
    private Integer ephLinkId;
    private String firstName;
    private String lastName;
    private String prefPhoneNbr;
    private Date createDttm;
    private Date lastUpdateDttm;
}
