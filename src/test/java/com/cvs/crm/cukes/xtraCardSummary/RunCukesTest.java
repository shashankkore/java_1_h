package com.cvs.crm.cukes.xtraCardSummary;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/xtra-card-summary.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-xtraCardSummary.json"},
        monochrome = true)

public class RunCukesTest {
}
