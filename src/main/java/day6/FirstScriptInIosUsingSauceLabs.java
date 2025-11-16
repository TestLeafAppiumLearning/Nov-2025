package day6;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class FirstScriptInIosUsingSauceLabs extends GenericWrappers {

    @Test
    public void runCode() {
        launchUiCatalogInIosUsingSauceLabs();
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Action Sheets"));
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Other"));
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Safe Choice"));
        click(getWebElement(Locators.XPATH.asString(), "//XCUIElementTypeButton[@name=\"UICatalog\"]"));
        closeApp();
    }
}
