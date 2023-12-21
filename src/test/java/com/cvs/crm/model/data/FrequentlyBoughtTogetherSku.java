package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class FrequentlyBoughtTogetherSku {
	 private Integer skuNbr;
	 private Integer associatedSkuNbr;
	 private Integer skuRankNbr;
}
