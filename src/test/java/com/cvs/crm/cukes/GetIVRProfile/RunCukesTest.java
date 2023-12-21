package com.cvs.crm.cukes.GetIVRProfile;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@IVRProfileLookUp",features = "src/test/resources/features/GetCustomerIVRProfile.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-GetCustomerIVRProfile.json"},
        monochrome = true)

public class RunCukesTest {
}
