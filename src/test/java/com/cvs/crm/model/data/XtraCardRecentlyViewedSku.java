package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class XtraCardRecentlyViewedSku {
	 private Integer xtraCardNbr;
	 private String viewActlDestCd;
	 private String lastViewSrcCd;
	 private Integer skuNbr;
	 private Date viewedTS;
	 private Integer totalViewedNbr;
}
