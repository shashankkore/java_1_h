package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class EmployeeCard {
    Integer empCardNbr;
    Integer custId;
}
