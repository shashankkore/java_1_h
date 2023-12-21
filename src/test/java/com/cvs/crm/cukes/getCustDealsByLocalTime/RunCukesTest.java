package com.cvs.crm.cukes.getCustDealsByLocalTime;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@GetCustDealsRewardsByLocalTime",features = "src/test/resources/features/GetCustDealsRewardsByLocalTime.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-GetCustDealsRewardsByLocalTime.json"},
        monochrome = true)

public class RunCukesTest {
}
