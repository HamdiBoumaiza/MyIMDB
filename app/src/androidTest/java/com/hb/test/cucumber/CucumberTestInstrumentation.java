package com.hb.test.cucumber;

import io.cucumber.android.runner.CucumberAndroidJUnitRunner;
import io.cucumber.junit.CucumberOptions;


@CucumberOptions(
        features = "features",
        glue = "com.hb.test.cucumber.SearchSteps"
)
public class CucumberTestInstrumentation extends CucumberAndroidJUnitRunner {}