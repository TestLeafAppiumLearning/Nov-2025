package testRunner;


import io.cucumber.testng.CucumberOptions;
import wrappers.ProjectSpecificWrappers;

@CucumberOptions(features = {"src/test/resources/features"}, glue = {"pages", "hooks"}, publish = true,
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})

public class TestRunner extends ProjectSpecificWrappers {
}
