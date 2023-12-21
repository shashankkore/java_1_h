package com.cvs.crm.cukes.setCust.CCPA;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/setCust/CCPA.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-CCPA.json"},
        monochrome = true)

public class RunCukesTest {
}

