package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnWebAppInAndroid extends GenericWrappers {
    @Test
    public void runCode() {
        launchChromeBrowser("https://www.google.com");
        enterValue(getWebElement(Locators.NAME.asString(), "q"), "Appium");
        pressEnter();
        click(getWebElement(Locators.TAG_NAME.asString(), "a"));
        sleep(5000);
        closeApp();
    }
}
