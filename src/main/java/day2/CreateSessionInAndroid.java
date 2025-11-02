package day2;

import initialWrappers.WrappersInitial;
import org.testng.annotations.Test;

public class CreateSessionInAndroid extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "", "", "", "", "", "");
        closeSession();
    }
}
