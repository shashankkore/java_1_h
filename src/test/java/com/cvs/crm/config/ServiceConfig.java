package com.cvs.crm.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("services")
@Data
public class ServiceConfig {

    private String appName;
    private String baseUrl;
    private String setcustUrl;
    private String getcustUrl;
    private String cmpgnEarnUrl;
    private String rewardHistoryUrl;
    private String cmpgndefnUrl;
    private String cpnapiUrl;
    private String productapiUrl;
    private String dataapiUrl;
    private String custsearchUrl;
    private String hrenrollUrl;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private String crmapiUrl;
    private String hreventsUrl;
}

