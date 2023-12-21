package com.cvs.crm.model.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Data
@Slf4j
public class ProductResponse {
    List<Integer> skus;
}
