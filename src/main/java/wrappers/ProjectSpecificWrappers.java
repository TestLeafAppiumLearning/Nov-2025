package wrappers;

import org.testng.annotations.*;

public class ProjectSpecificWrappers extends GenericWrappers {
    @BeforeSuite
    public void bs() {
        startServer();
    }

    @Parameters({"platformName", "udid", "appPackage", "appActivity", "automationName", "chromeDriverPort", "systemPort",
            "xcodeOrgId", "xcodeSigningId", "bundleId", "app", "wdaLocalPort", "useExistingApp"})
    @BeforeMethod
    public void bm(@Optional("Android") String platformName, @Optional("") String udid, @Optional("") String appPackage,
                   @Optional("") String appActivity, @Optional("UiAutomator2") String automationName,
                   @Optional("") String chromeDriverPort, @Optional("") String systemPort, @Optional("") String xcodeOrgId,
                   @Optional("") String xcodeSigningId, @Optional("") String bundleId, @Optional("") String app,
                   @Optional("") String wdaLocalPort, @Optional("true") boolean useExistingApp) {
        launchApp(platformName, udid, appPackage, appActivity, automationName, chromeDriverPort, systemPort, xcodeOrgId,
                xcodeSigningId, bundleId, app, wdaLocalPort, useExistingApp);
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
