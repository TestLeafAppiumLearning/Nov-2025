package testcases;

import pages.LoginPage;
import wrappers.GenericWrappers;

public class TC002 {

    public static void main(String[] args) {
        new GenericWrappers().launchAndroidApp("com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity","leaforg.apk");

        new LoginPage()
                .enterUserName("lokesh@testleaf.com")
                .enterPassword("Leaf@123")
                .clickLoginForNegative()
                .closeApp();
    }
}
