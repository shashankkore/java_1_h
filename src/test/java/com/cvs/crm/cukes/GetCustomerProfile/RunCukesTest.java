package com.cvs.crm.cukes.GetCustomerProfile;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/GetCustomerProfile.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-GetCustomerProfile.json"},
        monochrome = true)

public class RunCukesTest {
}
