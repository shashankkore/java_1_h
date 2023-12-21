package com.cvs.crm.cukes.SetCustCarepass;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/SetCust-Carepass.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-setcust-carepass.json"},
        tags = {"@Smoke","~@Regression"},
        monochrome = true)

public class RunCukesTest {
}
