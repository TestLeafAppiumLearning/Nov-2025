package day3;

import initialWrappers.WrappersInitial;
import org.testng.annotations.Test;

public class LearnPInchAndZoom extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "Multitouch.apk", "", "", "", "", "");
//        click(driver.findElement(AppiumBy.id("com.easylabs.multitouch:id/personalizeButtonYes")));
//        click(driver.findElement(AppiumBy.id("com.easylabs.multitouch:id/buttonStart")));
//        pinchInApp();
//        sleep(3);
//        zoomInApp();
//        sleep(3);
        longPress();
        closeSession();
    }
}
