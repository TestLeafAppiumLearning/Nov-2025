package testcases;

import pages.LoginPage;
import wrappers.GenericWrappers;

public class TC001 {

    public static void main(String[] args) {
        new GenericWrappers().launchAndroidApp("com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity","leaforg.apk");

        new LoginPage()
                .enterUserName("rajkumar@testleaf.com")
                .enterPassword("Leaf@123")
                .clickLogin()
                .verifyName("Raj Kumar")
                .closeApp();
    }
}
