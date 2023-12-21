package com.cvs.crm.cukes.opticalintegration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/opticalIntegration.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-opticalIntegration.json"},
        glue = {"com.cvs.crm.cukes.opticalintegration.glue"},		
        tags = {"@Optical"},
monochrome = true)

public class RunCukesTest {
}
