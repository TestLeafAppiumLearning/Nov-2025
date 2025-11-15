package wrappers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Set;

/**
 * Provides wrapper methods for web browser automation on mobile devices.
 * Extends CommonNativeWrappers to include web-specific functionality.
 */
@SuppressWarnings("ALL")
public class CommonWebWrappers extends CommonNativeWrappers {

    /**
     * Launches mobile web browser with specified configuration
     *
     * @param platformName         "android" or "ios" platform specification
     * @param URL                  Initial URL to load in browser
     * @param udid                 Unique device identifier for parallel testing
     * @param chromeDriverPort     ChromeDriver port for Android parallel execution
     * @param wdaLocalPort         WebDriverAgent port for iOS parallel execution
     * @param webkitDebugProxyPort WebKit debug proxy port for iOS web inspection
     * @param useExistingApp       Whether to preserve browser state between sessions
     * @return true if browser launch succeeds, false otherwise
     */
    public boolean launchBrowser(String platformName, String URL, String udid,
                                 String chromeDriverPort, String wdaLocalPort,
                                 String webkitDebugProxyPort, boolean useExistingApp) {
        try {
            if (startAppiumServerProgramatically) {
                startServer();
            }
            // I
            // Initialize desired capabilities
            DesiredCapabilities dc = new DesiredCapabilities();

            // Configure session reuse if requested
            if (useExistingApp) {
                dc.setCapability("appium:noReset", true);          // Preserve browser data
                dc.setCapability("appium:forceAppLaunch", true);   // Force browser relaunch
                dc.setCapability("appium:shouldTerminateApp", true); // Close browser on quit
            }

            // Set device identifier
            if (!udid.isEmpty()) {
                dc.setCapability("appium:udid", udid);
            }

            // Platform-specific configuration
            if (platformName.equalsIgnoreCase("android")) {
                // Android Chrome configuration
                if (!chromeDriverPort.isEmpty()) {
                    dc.setCapability("appium:chromedriverPort", chromeDriverPort);
                }
                dc.setCapability("appium:autoGrantPermissions", true);  // Auto-accept permissions
                dc.setCapability("appium:browserName", "Chrome");       // Use Chrome browser
                dc.setCapability("appium:automationName", "UiAutomator2"); // Android automation engine

                // Initialize Android Chrome driver
                driver.set(new AndroidDriver(new URI(serverUrl).toURL(), dc));
            } else if (platformName.equalsIgnoreCase("ios")) {
                // iOS Safari configuration
                if (!wdaLocalPort.isEmpty()) {
                    dc.setCapability("appium:wdaLocalPort", wdaLocalPort);
                }
                if (!webkitDebugProxyPort.isEmpty()) {
                    dc.setCapability("appium:webkitDebugProxyPort", webkitDebugProxyPort);
                }
                dc.setCapability("appium:browserName", "Safari");      // Use Safari browser
                dc.setCapability("appium:automationName", "XCUITest"); // iOS automation engine
                dc.setCapability("appium:webviewConnectTimeout", 40000); // WebView connection timeout
                dc.setCapability("appium:autoAcceptAlerts", true);     // Auto-accept system alerts
                dc.setCapability("appium:startIWDP", true);            // Start iOS web debug proxy
                dc.setCapability("appium:nativeWebTap", true);         // Enable native taps for web

                // Initialize iOS Safari driver
                driver.set(new IOSDriver(new URI(serverUrl).toURL(), dc));
            }

            // Navigate to initial URL
            driver.get().get(URL);
            // Set default implicit wait
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        }
    }

    /**
     * Switches context to WEBVIEW (Not recommended for Android)
     */
    public void switchWebView() {
        try {
            // Get all available contexts
            Set<String> contextNames = ((SupportsContextSwitching) driver.get()).getContextHandles();

            // Find and switch to first WEBVIEW context
            for (String contextName : contextNames) {
                if (contextName.contains("WEBVIEW")) {
                    ((SupportsContextSwitching) driver.get()).context(contextName);
                    break;
                }
            }

            // Reset implicit wait
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Scrolls down in browser by specified pixels
     *
     * @param pixelsToBeScrolled Number of pixels to scroll vertically
     * @return true if scroll succeeds, false otherwise
     */
    public boolean scrollDownInBrowser(int pixelsToBeScrolled) {
        try {
            // Execute JavaScript scroll
            JavascriptExecutor jse = driver.get();
            jse.executeScript("window.scrollBy(0," + pixelsToBeScrolled + ")", "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Navigates back in browser history
     *
     * @return true if navigation succeeds, false otherwise
     */
    public boolean navigateBackInBrowser() {
        try {
            driver.get().navigate().back();  // Go back in browser history
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads specified URL in current browser window
     *
     * @param url URL to load
     * @return true if URL loads successfully
     */
    public boolean loadURL(String url) {
        try {
            driver.get().navigate().to(url);  // Navigate to specified URL
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Switches to most recently opened browser window
     *
     * @return true if switch succeeds
     */
    public boolean switchToLastWindow() {
        try {
            // Wait for potential new window to open
            sleep(5000);

            // Get all window handles
            Set<String> windowHandles = driver.get().getWindowHandles();

            // Switch to last window in set
            for (String windowHandle : windowHandles) {
                driver.get().switchTo().window(windowHandle);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Switches to first/oldest browser window
     *
     * @return true if switch succeeds
     */
    public boolean switchToFirstWindow() {
        try {
            // Wait for potential window changes
            sleep(5000);

            // Get all window handles
            Set<String> windowHandles = driver.get().getWindowHandles();

            // Switch to first window
            for (String windowHandle : windowHandles) {
                driver.get().switchTo().window(windowHandle);
                break;  // Exit after first window
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}