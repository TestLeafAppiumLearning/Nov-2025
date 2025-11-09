package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnHybridAppInAndroid extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity", "UiAutomator2", "leaforg.apk");
        printContext();
        switchContext("WEBVIEW_com.testleaf.leaforg");
        enterValue(getWebElement(Locators.TAG_NAME.asString(), "input"), "rajkumar@testleaf.com");
        enterValue(getWebElement(Locators.XPATH.asString(), "(//input)[2]"), "Leaf@123");
        click(getWebElement(Locators.XPATH.asString(), "(//button)[last()]"));
        sleep(5000);
        closeApp();
    }

}
