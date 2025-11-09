package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnMultipleWebViewsInAndroid extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("", "", "UiAutomator2", "TheApp.apk");
        printContext();
        click(getWebElement(Locators.XPATH.asString(), "//*[@text='Dual Webview Demo']"));
        sleep(3000);
        printContext();
        switchContext("WEBVIEW_com.appiumpro.the_app");
        switchToLastWindow();
        System.out.println(getText(getWebElement(Locators.TAG_NAME.asString(), "h2")));
        switchToFirstWindow();
        System.out.println(getText(getWebElement(Locators.TAG_NAME.asString(), "h2")));
        closeApp();
    }
}
