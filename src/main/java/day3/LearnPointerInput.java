package day3;

import initialWrappers.WrappersInitial;
import org.testng.annotations.Test;

public class LearnPointerInput extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "Multitouch.apk", "", "", "", "", "");
//        click(driver.findElement(AppiumBy.id("com.easylabs.multitouch:id/personalizeButtonYes")));
//        click(driver.findElement(AppiumBy.id("com.easylabs.multitouch:id/buttonStart")));
        swipe("up");
        sleep(3);
        swipe("down");
        sleep(3);
        swipe("left");
        sleep(3);
        swipe("right");
        sleep(3);
        closeSession();
    }
}
