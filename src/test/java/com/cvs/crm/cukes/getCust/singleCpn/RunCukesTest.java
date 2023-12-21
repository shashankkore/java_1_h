package com.cvs.crm.cukes.getCust.singleCpn;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/getCust/singleCpn.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-singleCpn.json"},
//        glue = {"com.cvs.crm.cukes.getCust.everDigitizedCpnIndAndDigitizedCpnInd.glue"},
        monochrome = true)

public class RunCukesTest {
}
