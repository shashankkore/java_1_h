package com.cvs.crm.cukes.digitalreceipts;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@DigitalReceipts", 
				 features = "src/test/resources/features/digital_receipts/digital_receipts.feature",
				 plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-digital_receipts.json"},
				 monochrome = true
				 )

public class RunCukesTest {
}