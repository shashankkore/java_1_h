package com.cvs.crm.cukes.GetCustCarepass;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/GetCust-Carepass.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-getcust-carepass.json"},
        monochrome = true)

public class RunCukesTest {
}
