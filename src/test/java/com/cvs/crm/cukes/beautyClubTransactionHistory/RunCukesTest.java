package com.cvs.crm.cukes.beautyClubTransactionHistory;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/beautyClubTransactionHistory.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-beautyClubTransactionHistory.json"},
        tags = {"@skip"},
        monochrome = true)

public class RunCukesTest {
}