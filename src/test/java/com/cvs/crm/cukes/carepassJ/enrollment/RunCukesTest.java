package com.cvs.crm.cukes.carepassJ.enrollment;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@CarePass and @Enroll_Monthly", features = "src/test/resources/features/carepass/carepass_enrolement.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/carepass.json"},
        monochrome = true)

public class RunCukesTest {

}