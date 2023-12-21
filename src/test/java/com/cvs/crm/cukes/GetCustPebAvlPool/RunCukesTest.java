package com.cvs.crm.cukes.GetCustPebAvlPool;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/GetCustomerPEBAvlbPool.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-GetCustomerPEBAvlbPool.json"},
        monochrome = true)

public class RunCukesTest {
}
