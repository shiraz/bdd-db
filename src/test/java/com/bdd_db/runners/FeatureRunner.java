package com.bdd_db.runners;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * <p>
 * The test runner class.
 * </p>
 */
@ExtendedCucumberOptions(jsonReport = "target/cucumber.utils",
        retryCount = 1,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        jsonUsageReport = "target/cucumber-usage.utils",
        usageReport = true,
        toPDF = true,
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "utils:target/cucumber.utils", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.utils", "junit:target/cucumber-results.xml" },
        features = { "src/test/resources/features" },
        strict = true,
        glue = { "com.bdd_db.definitions" })
public class FeatureRunner extends AbstractTestNGCucumberTests {}