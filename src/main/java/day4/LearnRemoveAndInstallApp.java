package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnRemoveAndInstallApp extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("", "", "UiAutomator2", "");
        installApp("leaforg.apk");
        sleep(3000);
        removeApp("com.testleaf.leaforg");
        sleep(3000);
        verifyAndInstallApp("com.testleaf.leaforg", "leaforg.apk");
        closeApp();
    }

}
