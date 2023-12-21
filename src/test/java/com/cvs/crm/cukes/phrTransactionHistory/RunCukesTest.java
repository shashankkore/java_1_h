package com.cvs.crm.cukes.phrTransactionHistory;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/phrTransactionHistory.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-phrTH.json"},
        tags = {"@Skip"},
        monochrome = true)

public class RunCukesTest {
}
