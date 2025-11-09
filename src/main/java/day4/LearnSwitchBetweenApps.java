package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnSwitchBetweenApps extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity", "UiAutomator2", "leaforg.apk");
        sleep(3000);
        activateOrRelaunchApp("com.android.vending");
        swipe("up");
        terminateOrStopRunningApp("com.testleaf.leaforg");
        activateOrRelaunchApp("com.testleaf.leaforg");
        activateOrRelaunchApp("com.android.vending");
        terminateOrStopRunningApp("com.android.vending");
        closeApp();
    }
}
