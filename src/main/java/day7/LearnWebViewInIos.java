package day7;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnWebViewInIos extends GenericWrappers {
    @Test
    public void runcode() {
        launchSafariBrowser("https://www.google.com", "2CDB9EC8-51BA-42D8-8973-3FBD791771D4");
        enterValue(getWebElement(Locators.NAME.asString(), "q"), "Appium", false);
        click(getWebElement(Locators.NAME.asString(), "q"));
//        switchNativeView();
//        System.out.println(driver.getPageSource());
//        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Search"));
//        switchWebView();
        clickGivenKeyboardButtonInIosByAccessibilityId("Search");
        click(getWebElement(Locators.PARTIAL_LINK_TEXT.asString(), "Appium"));
    }
}
