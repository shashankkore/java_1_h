package com.cvs.crm.cukes.carepassmmf;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@CarePassMMF", features = "src/test/resources/features/carepass/carepass_mmf.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/carepassmmf.json"},
        monochrome = true)

public class RunCukesTest {

}