package com.cvs.crm.cukes.SetCustTables;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/SetCust-Tables.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-setcust-tables.json"},
        tags = {"@SetCustTables"},
        monochrome = true)

public class RunCukesTest {
}
