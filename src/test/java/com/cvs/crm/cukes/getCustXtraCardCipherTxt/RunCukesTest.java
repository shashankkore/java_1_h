package com.cvs.crm.cukes.getCustXtraCardCipherTxt;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/GetCustXtraCardCipherTxt.feature",
        //tags = {"@cpns"},
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-GetCustXtraCardCipherTxt.json"},
        monochrome = true)

public class RunCukesTest {
}
