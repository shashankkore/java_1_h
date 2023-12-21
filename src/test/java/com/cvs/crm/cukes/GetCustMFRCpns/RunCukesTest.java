package com.cvs.crm.cukes.GetCustMFRCpns;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/GetCustomerMFRCpns.feature",
        tags = {"@mfrcpns"},
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-GetCustomerMFRCpns.json"},
        monochrome = true)

public class RunCukesTest {
}
