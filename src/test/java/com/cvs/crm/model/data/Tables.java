package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tables {

    private String name;
    private List<Rows> rows;

}
