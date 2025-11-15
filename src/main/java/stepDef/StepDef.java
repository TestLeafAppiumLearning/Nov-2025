package stepDef;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.en.Given;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URI;
import java.time.Duration;

public class StepDef {
    public AppiumDriver driver;

    @Given("the application is launched successfully")
    public void the_application_is_launched_successfully() {
            try {
                String platformName = "Android";
                String appPackage = "com.testleaf.leaforg";
                String appActivity = "com.testleaf.leaforg.MainActivity";
                String automationName = "UiAutomator2";
                String app = "leaforg.apk";
                String serverUrl = "http://127.0.0.1:4723";
                String udid = "",chromeDriverPort = "", systemPort ="", wdaLocalPort = "",xcodeOrgId="",xcodeSigningId="",
                        bundleId="";
                boolean useExistingApp = true;

                // Initialize desired capabilities object
                DesiredCapabilities dc = new DesiredCapabilities();

                // Configure app reset behavior based on parameter
                if (useExistingApp) {
                    dc.setCapability("appium:noReset", true);         // Preserve app data between sessions
                    dc.setCapability("appium:forceAppLaunch", true);  // Force relaunch on session start
                    dc.setCapability("appium:shouldTerminateApp", true); // Close app on driver.quit()
                } else {
                    if (platformName.equalsIgnoreCase("android")) {
                        dc.setCapability("appium:autoGrantPermissions", true);  // Auto-accept permissions
                    } else {
                        dc.setCapability("appium:autoAcceptAlerts", true);  // Auto-accept system alerts
                    }
                }

                // Set mandatory capabilities
                if (!automationName.isEmpty()) {
                    dc.setCapability("appium:automationName", automationName);
                } else {
                    throw new RuntimeException("Automation Name capability is required");
                }

                // Set device-specific capabilities
                if (!udid.isEmpty()) {
                    dc.setCapability("appium:udid", udid);
                }

                if (!app.isEmpty())
                    dc.setCapability("appium:app", System.getProperty("user.dir") + File.separator + "apks" + File.separator + app);

                // Platform-specific configuration
                if (platformName.equalsIgnoreCase("android")) {
                    // Android-specific capabilities
                    if (!appPackage.isEmpty()) {
                        dc.setCapability("appium:appPackage", appPackage);
                    }
                    if (!appActivity.isEmpty()) {
                        dc.setCapability("appium:appActivity", appActivity);
                    }
                    if (!chromeDriverPort.isEmpty()) {
                        dc.setCapability("appium:chromedriverPort", chromeDriverPort);
                    }
                    if (!systemPort.isEmpty()) {
                        dc.setCapability("appium:systemPort", systemPort);
                    }

                    // Initialize Android driver
                    driver = new AndroidDriver(new URI(serverUrl).toURL(), dc);
                } else if (platformName.equalsIgnoreCase("ios")) {
                    // iOS-specific capabilities
                    if (!wdaLocalPort.isEmpty()) {
                        dc.setCapability("appium:wdaLocalPort", wdaLocalPort);
                    }
                    if (!xcodeOrgId.isEmpty()) {
                        dc.setCapability("appium:xcodeOrgId", xcodeOrgId);
                    }
                    if (!xcodeSigningId.isEmpty()) {
                        dc.setCapability("appium:xcodeSigningId", xcodeSigningId);
                    }
                    if (!bundleId.isEmpty()) {
                        dc.setCapability("appium:bundleId", bundleId);
                    }
                    dc.setCapability("appium:wdaLaunchTimeout", 90000);
                    // Initialize iOS driver
                    driver = new IOSDriver(new URI(serverUrl).toURL(), dc);
                }

                // Set default implicit wait
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    @Given("the user enters the email address as {string}")
    public void the_user_enters_the_email_address_as(String string) {
        driver.findElement(AppiumBy.className("android.widget.EditText")).sendKeys(string);
    }
    @Given("the user enters the password as {string}")
    public void the_user_enters_the_password_as(String string) {
        driver.findElement(AppiumBy.xpath("(//android.widget.EditText)[2]")).sendKeys(string);

    }
    @Given("the user clicks on the login button")
    public void the_user_clicks_on_the_login_button() {
        driver.findElement(AppiumBy.className("android.widget.Button")).click();
    }
    @Given("Verify Home page is displayed")
    public void verify_home_page_is_displayed() {
        driver.findElement(AppiumBy.xpath("//android.view.View[@text=\"PARTICIPANT NAME\"]")).isDisplayed();
    }
    @Given("Error message is displayed")
    public void error_message_is_displayed() {
        driver.findElement(AppiumBy.xpath("//*[@text=\"User does not exist\"]")).isDisplayed();
    }


}
