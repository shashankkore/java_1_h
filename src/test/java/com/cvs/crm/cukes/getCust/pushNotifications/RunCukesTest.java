package com.cvs.crm.cukes.getCust.pushNotifications;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features/getCust/pushNotification.feature"},
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-pushNotifications.json"},
//        glue = {"com.cvs.crm.cukes.getCust.pushNotifications.glue"},
        monochrome = true)

public class RunCukesTest {
}
