package com.cvs.crm.cukes.getCust.extraBuckRewardsSummary;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/getCust/extraBuckRewardsSummary.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-extraBuckRewardsSummary.json"},
//        glue = {"com.cvs.crm.cukes.getCust.everDigitizedCpnIndAndDigitizedCpnInd.glue"},
        monochrome = true)

public class RunCukesTest {
}
