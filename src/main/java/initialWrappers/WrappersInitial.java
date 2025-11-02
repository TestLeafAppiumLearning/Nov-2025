package initialWrappers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

public class WrappersInitial {

    public AppiumDriver driver;

    public void launchApp(String platformName, String automationName, String app, String appPackage, String appActivity,
                          String udid, String systemPort, String chromeDriverPort) {
        try {
            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setCapability("appium:platformName", platformName);
            dc.setCapability("appium:automationName", automationName);
            // Will not close the app after program completes even if driver.quit is called,
            // Will not clear the cache which launching the app,
            // Will not launch the app if running in background or already running in foreground
            dc.setCapability("appium:noReset", true);
            if (!app.isEmpty()) {
                dc.setCapability("appium:app", System.getProperty("user.dir") + File.separator + "apks" + File.separator + app);
            }
            if (!appPackage.isEmpty()) {
                dc.setCapability("appium:appPackage", appPackage);
            }
            if (!appActivity.isEmpty()) {
                dc.setCapability("appium:appActivity", appActivity);
            }
            if (!udid.isEmpty()) {
                dc.setCapability("appium:udid", udid);
            }
            if (!systemPort.isEmpty()) {
                dc.setCapability("appium:systemPort", systemPort);
            }
            if (!chromeDriverPort.isEmpty()) {
                dc.setCapability("appium:chromeDriverPort", chromeDriverPort);
            }
            driver = new AppiumDriver(new URI("http://127.0.0.1:4723/").toURL(), dc);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void launchAppInSuacelabs() {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:app", "storage:filename=leaforg.apk");  // The filename of the mobile app
        caps.setCapability("appium:deviceName", "Google Pixel 2 XL Emulator");
        caps.setCapability("appium:platformVersion", "current_major");
        caps.setCapability("appium:automationName", "UiAutomator2");
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", "oauth-novappium-6d9d8");
        sauceOptions.setCapability("accessKey", "32c5386d-fc93-4124-89b6-793a190d3a5f");
        sauceOptions.setCapability("build", "<your build id>");
        sauceOptions.setCapability("name", "<your test name>");
        sauceOptions.setCapability("deviceOrientation", "PORTRAIT");
        caps.setCapability("sauce:options", sauceOptions);

// Start the session
        URL url = null;
        try {
            url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        driver = new AndroidDriver(url, caps);

    }


    public void enterText(WebElement element, String value) {
        element.sendKeys(value);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSession() {
        driver.quit();
    }

}
