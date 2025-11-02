package day2;

import initialWrappers.WrappersInitial;
import org.testng.annotations.Test;

public class CreateSessionAndInstallAppInAndroid extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "leaforg.apk", "", "", "", "", "");
        closeSession();
    }
}