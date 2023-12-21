package com.cvs.crm.cukes.BulkCoupons;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/Bulk-CouponAPI.feature",
        plugin = {"pretty", "html:target/cucumber/cucumber-html-report", "json:target/cucumber/BulkCoupons.json"},
        monochrome = true)

public class RunCukesTest {

}