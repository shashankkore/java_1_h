package com.cvs.crm.cukes.getCust.mktgTypeBenefits;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/getCust/mktgTypeBenefits.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-mktgTypeBenefits.json"},
//        glue = {"com.cvs.crm.cukes.getCust.everDigitizedCpnIndAndDigitizedCpnInd.glue"},
        monochrome = true)

public class RunCukesTest {
}
