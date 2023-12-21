package com.cvs.crm.cukes.dealsInProgress;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@dealsInProgress", features = "src/test/resources/features/dealsInProgress.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/dealsInProgress.json"},
        monochrome = true)
public class RunCukesTest {

}