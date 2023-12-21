package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class StoreSkuRank {
	 private Integer storeNbr;
	 private String rankTypeCode;
	 private Integer skuNbr;
	 private Integer skuRankNbr;
}
