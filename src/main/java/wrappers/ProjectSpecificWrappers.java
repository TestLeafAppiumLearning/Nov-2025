package wrappers;

import org.testng.annotations.*;

public class ProjectSpecificWrappers extends GenericWrappers {
    @BeforeSuite
    public void bs() {
        startServer();
    }

    @Parameters({"udid", "systemPort"})
    @BeforeMethod
    public void bm(@Optional("") String udid, @Optional("") String systemPort) {
        launchAndroidAppInParallel(udid, "com.testleaf.leaforg", "com.testleaf.leaforg.MainActivity", "", systemPort, "leaforg.apk");
    }

    @AfterMethod
    public void am() {
        closeApp();
    }

    @AfterSuite
    public void as() {
        stopServer();
    }
}
