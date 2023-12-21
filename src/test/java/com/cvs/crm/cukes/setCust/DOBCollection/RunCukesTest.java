package com.cvs.crm.cukes.setCust.DOBCollection;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/setCust/DOBCollection.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-DOBCollection.json"},
        monochrome = true)

public class RunCukesTest {
}

