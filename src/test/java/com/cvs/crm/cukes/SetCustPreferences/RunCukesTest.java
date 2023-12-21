package com.cvs.crm.cukes.SetCustPreferences;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/SetCust-Preferences.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-Setcust-Preferences.json"},
        monochrome = true)

public class RunCukesTest {
}
