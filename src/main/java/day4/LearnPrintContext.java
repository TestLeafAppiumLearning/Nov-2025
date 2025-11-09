package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnPrintContext extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity", "UiAutomator2", "leaforg.apk");
        printContext();
        closeApp();
    }

}
