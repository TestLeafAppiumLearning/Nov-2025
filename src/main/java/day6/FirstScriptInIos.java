package day6;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class FirstScriptInIos extends GenericWrappers {

    @Test
    public void runCode() {
        launchIosApp("2CDB9EC8-51BA-42D8-8973-3FBD791771D4", "", "com.example.apple-samplecode.UICatalog", "uicatalog.zip");
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Action Sheets"));
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Other"));
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Safe Choice"));
        click(getWebElement(Locators.XPATH.asString(), "//XCUIElementTypeButton[@name=\"UICatalog\"]"));
        closeApp();
    }
}
