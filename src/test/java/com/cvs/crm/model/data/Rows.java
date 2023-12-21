package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rows {

    private String action;
    
    @JsonProperty("colData")
    private TableColumnData colData;

}
