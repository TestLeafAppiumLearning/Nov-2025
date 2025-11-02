package day2;

import initialWrappers.WrappersInitial;
import org.testng.annotations.Test;

public class CreateSessionWithPermissibleActivityInAndroid extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "leaforg.apk", "com.testleaf.leaforg",
                "com.testleaf.leaforg.MainActivity", "", "", "");
        closeSession();
    }
}