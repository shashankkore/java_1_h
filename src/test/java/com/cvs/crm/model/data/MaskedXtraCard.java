package com.cvs.crm.model.data;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class MaskedXtraCard {
    private Integer xtraCardNbr;
    private String xtraCardMaskNbr;
    private String visibleIndicator;
    private String xtraCardSh1Nbr;
    private String xtraCardSh2Nbr;
}
