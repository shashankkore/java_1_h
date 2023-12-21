package com.cvs.crm.cukes.recentlyRedeemedTransactionHistory;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/recentlyRedeemedTransactionHistory.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-recentlyRedeemedTransactionHistory.json"},
        monochrome = true)

public class RunCukesTest {
}