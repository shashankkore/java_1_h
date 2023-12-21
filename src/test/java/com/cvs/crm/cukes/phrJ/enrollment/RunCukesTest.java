package com.cvs.crm.cukes.phrJ.enrollment;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features/phr/enroll.feature",
							 "src/test/resources/features/phr/HREvents.feature",
							 "src/test/resources/features/phr/dashboard.feature",
							 "src/test/resources/features/phr/getcustnode.feature"},
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/phrj.json"},
        monochrome = true)

public class RunCukesTest {

}