package pages;

import io.appium.java_client.AppiumDriver;
import wrappers.GenericWrappers;

public class HomePage extends GenericWrappers {
    public HomePage(AppiumDriver driver) {
        eleIsDisplayed(getWebElement(Locators.XPATH.asString(), "//android.view.View[@text=\"PARTICIPANT NAME\"]"));
    }

    public HomePage verifyName(String name) {
        verifyText(getWebElement(Locators.XPATH.asString(), "//android.view.View[@text=\"PARTICIPANT NAME\"]/preceding-sibling::android.view.View"),
                name);
        return new HomePage(driver);
    }


}
