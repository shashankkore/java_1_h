package com.cvs.crm.cukes.IBotta;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@IBotta", 
				 features = "src/test/resources/features/iBotta/iBotta-CVS-linking.feature",
				 plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-iBotta.json"},
				 monochrome = true
				 )

public class RunCukesTest {
}