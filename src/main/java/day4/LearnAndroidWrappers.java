package day4;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnAndroidWrappers extends GenericWrappers {

    @Test
    public void runCode() {
        launchAndroidApp("", "", "UiAutomator2", "leaforg.apk");
//        activateOrRelaunchApp("in.amazon.mShop.android.shopping");
        startAnAppUsingActivity("in.amazon.mShop.android.shopping", "com.amazon.mShop.home.HomeActivity");
        showNotificationMenu();
        dataOffInAndroid();
        sleep(3000);
        dataOnInAndroid();
        sleep(3000);
        hideNotificationMenu();
        System.out.println(getCurrentAppPackage());
        System.out.println(getCurrentActivity());

        closeApp();
    }
}
