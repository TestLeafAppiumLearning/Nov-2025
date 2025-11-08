package day3;

import initialWrappers.WrappersInitial;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class LearnSwipeWithinWebElement extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "", "com.android.vending", "com.android.vending.AssetBrowserActivity", "", "", "");
        WebElement spon = driver.findElement(AppiumBy.xpath("(//android.view.View[@content-desc=\"About this ad\"]/following-sibling::android.view.View)[1]"));
        swipeWithinWebElement(spon, "left");
        sleep(3);
        swipeWithinWebElement(spon, "right");
        sleep(3);
        closeSession();
    }
}
