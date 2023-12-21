package com.cvs.crm.cukes.campaign;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags="@Campaign" ,features = "src/test/resources/features/campaignDefinations.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/campaign.json"},
        monochrome = true)

public class RunCukesTest {

}