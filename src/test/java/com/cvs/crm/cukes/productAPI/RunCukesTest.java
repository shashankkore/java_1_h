package com.cvs.crm.cukes.productAPI;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/ProductAPI.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-productAPI.json"},
        monochrome = true)

public class RunCukesTest {
}
