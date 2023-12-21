package com.cvs.crm.cukes.getCust.tablesAndPreferences;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features/getCust/tablesAndPreferences.feature"},
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-tablesAndPreferences.json"},
//        glue = {"com.cvs.crm.cukes.getCust.tablesAndPreferences.glue"},
        monochrome = true)

public class RunCukesTest {
}
