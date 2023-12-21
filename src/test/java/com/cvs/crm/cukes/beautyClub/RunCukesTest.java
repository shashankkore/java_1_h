package com.cvs.crm.cukes.beautyClub;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

  @CucumberOptions(features =
  "src/test/resources/features/beauty-club.feature", plugin = {"pretty",
  "html:target/cucumber/cucumber-html-report",
  "json:target/cucumber/cucumber-beautyClub.json"}, 
		  tags = {"@skip"},
		  monochrome = true)
 
public class RunCukesTest {
}