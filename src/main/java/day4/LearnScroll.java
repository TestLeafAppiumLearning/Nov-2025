package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnScroll extends GenericWrappers {

    @Test
    public void runCode() {
//        launchApp("Android", "", "", "",
//                "UiAutomator2", "", "",
//                "", "", "", "MultiTouch.apk",
//                "", false);
//        click(getWebElement(Locators.ID.asString(), "com.easylabs.multitouch:id/personalizeButtonYes"));
//        click(getWebElement(Locators.ID.asString(), "com.easylabs.multitouch:id/buttonStart"));
//        swipe("up");
//        swipe("down");
//        swipe("left");
//        swipe("right");
//        pinchInApp();
//        zoomInApp();
//        doubleTap(500, 500);
//        longPress(600, 500);
//        closeApp();

        launchApp("Android", "", "com.android.vending", "com.android.vending.AssetBrowserActivity",
                "UiAutomator2", "", "",
                "", "", "", "",
                "", true);
        swipeUpInAppUntilElementIsVisible("xpath", "//*[@text='Recommended for you']");
        closeApp();

    }
}
