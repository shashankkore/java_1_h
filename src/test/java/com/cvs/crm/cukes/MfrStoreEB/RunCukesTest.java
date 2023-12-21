package com.cvs.crm.cukes.MfrStoreEB;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/MFR-StoreEB-CouponsCreation.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/cucumber-MFR-StoreEB-CouponsCreation.json"},
        monochrome = true)

public class RunCukesTest {
}