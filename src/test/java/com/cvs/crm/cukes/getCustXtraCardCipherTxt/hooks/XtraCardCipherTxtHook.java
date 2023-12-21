package com.cvs.crm.cukes.getCustXtraCardCipherTxt.hooks;

import com.cvs.crm.config.ServiceConfig;
import com.cvs.crm.service.CarePassService;
import com.cvs.crm.stubs.CarePassStub;
import com.cvs.crm.util.CarePassEnrollmentUtil;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class XtraCardCipherTxtHook {

    @Autowired
    ServiceConfig serviceConfig;
}
