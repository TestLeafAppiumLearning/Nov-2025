package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnSwitchBetweenAppsWithActivity extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity", "UiAutomator2", "leaforg.apk");
        startAnAppUsingActivity("in.amazon.mShop.android.shopping", "com.amazon.mShop.home.HomeActivity");
        closeApp();
    }
}
