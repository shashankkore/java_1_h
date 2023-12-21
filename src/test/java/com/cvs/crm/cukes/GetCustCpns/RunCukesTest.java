package com.cvs.crm.cukes.GetCustCpns;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/GetCustomerCpn.feature",
        //tags = {"@cpns"},
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-GetCustomerCpn.json"},
        monochrome = true)

public class RunCukesTest {
}
