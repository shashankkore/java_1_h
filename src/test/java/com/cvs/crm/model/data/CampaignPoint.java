package com.cvs.crm.model.data;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class CampaignPoint {
	private Integer cmpgnId;
	private Integer xtraCardNbr;
	private Integer ptsQty;    
	private Integer ptsAdjQty;
	private Integer ptsOtherQty;
	private Date lastLastUpdtDt;     
	private Integer ptSpeakQty;
	private Integer lastChngPtsQty;
	private Integer ptsXferInQty;
	private Integer ptsXrefOutQty;
	private Integer ptsAdjXferInQty;
	private Integer ptsAdjXferOutQty;
	private Integer ptsOtherXferInqty;
	private Integer ptsOtherXferOutQty;
	private Integer ActivationTs; 
	private String ActivationSrcCd;
}
