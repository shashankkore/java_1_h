package com.cvs.crm.cukes.beautyClubRelaunch;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/beautyClubRelaunch.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-beautyClubRelaunch.json"},
        tags = {"@skip"},
        monochrome = true)

public class RunCukesTest {
}