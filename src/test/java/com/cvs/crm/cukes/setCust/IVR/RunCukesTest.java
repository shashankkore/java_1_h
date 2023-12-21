package com.cvs.crm.cukes.setCust.IVR;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/setCust/IVR.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-IVR.json"},
        monochrome = true)

public class RunCukesTest {
}

