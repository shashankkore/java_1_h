package com.cvs.crm.cukes.phr;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/phr-supress-patientnames.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-phrSP.json"},
        tags = {"@Skip"},
        monochrome = true)

public class RunCukesTest {
}
