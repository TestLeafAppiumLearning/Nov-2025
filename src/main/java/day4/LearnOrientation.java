package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnOrientation extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity", "UiAutomator2", "leaforg.apk");
        setLandscapeOrientation();
        sleep(3000);
        System.out.println(getOrientation());
        setPortraitOrientation();
        sleep(3000);
        System.out.println(getOrientation());
        closeApp();
    }
}
