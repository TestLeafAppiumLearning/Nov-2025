package day2;

import initialWrappers.WrappersInitial;
import org.testng.annotations.Test;

public class CreateSessionWithPreInstallAppInAndroid extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "", "com.testleaf.leaforg",
                "com.testleaf.leaforg.MainActivity", "", "", "");
        closeSession();
    }
}