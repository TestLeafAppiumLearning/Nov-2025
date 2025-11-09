package day2;

import initialWrappers.WrappersInitial;
import org.testng.annotations.Test;

public class CreateSessionWithPermissibleActivityInAndroid extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "", "in.amazon.mShop.android.shopping",
                "com.amazon.mShop.home.HomeActivity", "", "", "");
        closeSession();
    }
}