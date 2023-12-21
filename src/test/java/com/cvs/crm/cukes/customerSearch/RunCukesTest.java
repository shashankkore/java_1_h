package com.cvs.crm.cukes.customerSearch;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(tags="@CustomerSearch and @runThis", features = "src/test/resources/features/CustomerSearch.feature",
@CucumberOptions(features = {"src/test/resources/features/CustomerSearch.feature"},
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-customerSearch.json"},
        monochrome = true)

public class RunCukesTest {
}
