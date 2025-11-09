package wrappers;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.remote.SupportsRotation;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * Provides comprehensive wrapper methods for Appium operations supporting both Android and iOS platforms.
 * Includes methods for application management, element interaction, gestures, and device operations.
 */
@SuppressWarnings("ALL")
public class CommonNativeWrappers {
    // Maximum number of scroll attempts when searching for elements
    public static final int MAX_SCROLL = 10;
    public static String serverUrl = "http://127.0.0.1:4723";

    // Appium driver instance for mobile automation
    public AppiumDriver driver;
    boolean startAppiumServerProgramatically = true;
    private AppiumDriverLocalService service;

    /**
     * Starts the Appium server using a custom AppiumServiceBuilder.
     * Automatically selects a free port and sets the base path.
     * <p>
     * Ensure the path to main.js is correct for your environment.
     */
    public void startServer() {
        service = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(() -> "--allow-cors")
                .withArgument(() -> "--allow-insecure", "*:chromedriver_autodownload")
                .build();
        service.start();
        serverUrl = String.valueOf(service.getUrl());
        System.out.println("Appium Server Started at: " + service.getUrl());


    }

    /**
     * Stops the Appium server if it is currently running.
     */
    public void stopServer() {
        if (service != null) {
            service.stop();
            System.out.println("Appium Server Stopped.");
        }
    }


    /**
     * Launches the mobile application with specified capabilities
     *
     * @param platformName     "android" or "ios" platform specification
     * @param udid             Unique device identifier for parallel testing
     * @param appPackage       Android application package name
     * @param appActivity      Android application main activity
     * @param automationName   Automation engine name (e.g., UiAutomator2, XCUITest)
     * @param chromeDriverPort Port for ChromeDriver (hybrid apps)
     * @param systemPort       System port for Android parallel execution
     * @param xcodeOrgId       Xcode organization ID for iOS signing
     * @param xcodeSigningId   Xcode signing identity for iOS
     * @param bundleId         iOS application bundle identifier
     * @param app              Path to application file
     * @param wdaLocalPort     WebDriverAgent port for iOS parallel execution
     * @param useExistingApp   Whether to use existing app data (noReset)
     * @return true if launch succeeds, false otherwise
     */
    public boolean launchApp(String platformName, String udid, String appPackage, String appActivity,
                             String automationName, String chromeDriverPort, String systemPort, String xcodeOrgId,
                             String xcodeSigningId, String bundleId, String app,
                             String wdaLocalPort, boolean useExistingApp) {
        try {
            if (startAppiumServerProgramatically) {
                startServer();
            }
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Installs application on the device
     *
     * @param appFileName Relative path to application file from project root
     * @return true if installation succeeds, false otherwise
     */
    public boolean installApp(String appFileName) {
        try {
            // Convert relative path to absolute path and install the app in device
            ((InteractsWithApps) driver).installApp(System.getProperty("user.dir") + File.separator + "apks" + File.separator + appFileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes application from the device
     *
     * @param bundleIdOrAppPackage Bundle ID (iOS) or package name (Android)
     * @return true if removal succeeds, false otherwise
     */
    public boolean removeApp(String bundleIdOrAppPackage) {
        try {
            // Remove app using package/bundle identifier
            ((InteractsWithApps) driver).removeApp(bundleIdOrAppPackage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies app installation status and reinstalls if needed
     *
     * @param bundleIdOrAppPackage Bundle ID (iOS) or package name (Android)
     * @param appPath              Relative path to application file
     * @return true if verification and installation succeeds
     */
    public boolean verifyAndInstallApp(String bundleIdOrAppPackage, String appFileName) {
        try {
            // Check if app is already installed
            if (((InteractsWithApps) driver).isAppInstalled(bundleIdOrAppPackage)) {
                // Remove existing installation
                ((InteractsWithApps) driver).removeApp(bundleIdOrAppPackage);
            }
            // Install fresh copy
            ((InteractsWithApps) driver).installApp(System.getProperty("user.dir") + File.separator + "apks" + File.separator + appFileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Forces thread sleep (NOT RECOMMENDED for test automation)
     *
     * @param mSec Duration in milliseconds to pause execution
     */
    public void sleep(int mSec) {
        try {
            Thread.sleep(mSec);  // Pause current thread execution
        } catch (InterruptedException e) {
            e.printStackTrace();  // Log interruption if occurs
        }
    }

    /**
     * Prints all available contexts to console
     */
    public void printContext() {
        try {
            // Get all available contexts (NATIVE_APP, WEBVIEW, etc.)
            Set<String> contexts = ((SupportsContextSwitching) driver).getContextHandles();
            // Print each context to console
            for (String context : contexts) {
                System.out.println(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to specified context
     *
     * @param context Name of context to switch to (from printContext)
     */
    public void switchContext(String context) {
        try {
            // Switch driver context
            ((SupportsContextSwitching) driver).context(context);
            // Reset implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to native app context (NATIVE_APP)
     */
    public void switchNativeView() {
        switchContext("NATIVE_APP");  // Switch to default native context
    }

    /**
     * Locates element using specified strategy
     *
     * @param locator  Locator strategy (id, name, className, etc.)
     * @param locValue Value to locate element
     * @return WebElement if found, null otherwise
     */
    public WebElement getWebElement(String locator, String locValue) {
        try {
            switch (locator) {
                case "id":
//                    return driver.findElement(AppiumBy.id(locValue));  // Find by resource ID
                    return driver.findElement(AppiumBy.xpath("//*[@resource-id='" + locValue + "'] or @id='" + locValue + "']"));  // Find by id attribute using xpath for WEBVIEW
                case "name":
//                    return driver.findElement(AppiumBy.name(locValue));  // Find by name attribute
                    return driver.findElement(AppiumBy.xpath("//*[@name='" + locValue + "']"));  // Find by name attribute using xpath for WEBVIEW
                case "className":
                    return driver.findElement(AppiumBy.className(locValue));  // Find by class name
                case "link":
                    return driver.findElement(AppiumBy.linkText(locValue));  // Find by exact link text
                case "partialLink":
                    return driver.findElement(AppiumBy.partialLinkText(locValue));  // Find by partial link text
                case "tag":
                    return driver.findElement(AppiumBy.tagName(locValue));  // Find by HTML tag
                case "css":
                    return driver.findElement(AppiumBy.cssSelector(locValue));  // Find by CSS selector
                case "xpath":
                    return driver.findElement(AppiumBy.xpath(locValue));  // Find by XPath
                case "accessibilityId":
                    return driver.findElement(AppiumBy.accessibilityId(locValue));  // Find by (content-desc) accessibility ID
                default:
                    throw new IllegalArgumentException("Invalid locator type: " + locator);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Captures screenshot and saves to images folder
     *
     * @return Unique numeric identifier for screenshot file
     */
    public long takeScreenShot() {
        // Generate unique filename
        long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
        try {
            // Capture screenshot as file
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            // Save to images directory
            FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") +
                    File.separator + "images" + File.separator + number + ".png"));
        } catch (WebDriverException e) {
            e.printStackTrace();  // Handle driver-related errors
        } catch (IOException e) {
            System.err.println("Failed to save screenshot");  // Handle file system errors
        }
        return number;  // Return filename identifier
    }

    /**
     * Checks element visibility
     *
     * @param ele WebElement to check
     * @return true if displayed, false otherwise
     */
    public boolean eleIsDisplayed(WebElement ele) {
        try {
            return ele.isDisplayed();  // Check display status
        } catch (Exception e) {
            return false;  // Return false if element not found
        }
    }

    /**
     * Verifies element text matches expected value
     *
     * @param ele          WebElement containing text
     * @param expectedText Text to compare against
     * @return true if text matches, false otherwise
     */
    public boolean verifyText(WebElement ele, String expectedText) {
        try {
            return ele.getText().equals(expectedText);  // Compare element text
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Performs scroll between coordinates
     *
     * @param startX Starting X coordinate
     * @param startY Starting Y coordinate
     * @param endX   Ending X coordinate
     * @param endY   Ending Y coordinate
     * @return true if scroll succeeds
     */
    private boolean scrollWithGivenCoOrdinates(int startX, int startY, int endX, int endY) {
        try {
            // Create touch action sequence
            PointerInput input = new PointerInput(Kind.TOUCH, "index finger");
            Sequence sequence = new Sequence(input, 1);

            // Define scroll gesture
            sequence.addAction(input.createPointerMove(Duration.ZERO, Origin.viewport(), startX, startY));
            sequence.addAction(input.createPointerDown(MouseButton.LEFT.asArg()));
            sequence.addAction(input.createPointerMove(Duration.ofSeconds(1), Origin.viewport(), endX, endY));
            sequence.addAction(input.createPointerUp(MouseButton.LEFT.asArg()));

            // Execute gesture
            driver.perform(Collections.singletonList(sequence));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Performs double tap at coordinates
     *
     * @param x X coordinate to tap
     * @param y Y coordinate to tap
     */
    public void doubleTap(int x, int y) {
        // Create touch action sequence
        PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
        Sequence doubleTap = new Sequence(finger, 1);

        // Define double tap gesture
        doubleTap.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), x, y));
        doubleTap.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
        doubleTap.addAction(new Pause(finger, Duration.ofMillis(100)));
        doubleTap.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
        doubleTap.addAction(new Pause(finger, Duration.ofMillis(100)));
        doubleTap.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
        doubleTap.addAction(new Pause(finger, Duration.ofMillis(100)));
        doubleTap.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));

        // Execute gesture
        driver.perform(Collections.singletonList(doubleTap));
    }

    /**
     * Performs long press at coordinates
     *
     * @param x X coordinate to press
     * @param y Y coordinate to press
     */
    public void longPress(int x, int y) {
        // Create touch action sequence
        PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
        Sequence longPress = new Sequence(finger, 1);

        // Define long press gesture
        longPress.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), x, y));
        longPress.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
        longPress.addAction(new Pause(finger, Duration.ofMillis(2000)));
        longPress.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));

        // Execute gesture
        driver.perform(Collections.singletonList(longPress));
    }

    /**
     * Performs pinch gesture (zoom out)
     */
    public void pinchInApp() {
        // Get screen dimensions
        int maxY = driver.manage().window().getSize().getHeight();
        int maxX = driver.manage().window().getSize().getWidth();

        // Create first finger gesture (top-right to center)
        PointerInput finger1 = new PointerInput(Kind.TOUCH, "finger1");
        Sequence a = new Sequence(finger1, 1);
        a.addAction(finger1.createPointerMove(Duration.ZERO, Origin.viewport(),
                (int) (maxX * 0.75), (int) (maxY * 0.25)));
        a.addAction(finger1.createPointerDown(MouseButton.LEFT.asArg()));
        a.addAction(finger1.createPointerMove(Duration.ofSeconds(1), Origin.viewport(),
                (int) (maxX * 0.5), (int) (maxY * 0.5)));
        a.addAction(finger1.createPointerUp(MouseButton.LEFT.asArg()));

        // Create second finger gesture (bottom-left to center)
        PointerInput finger2 = new PointerInput(Kind.TOUCH, "finger2");
        Sequence b = new Sequence(finger2, 1);
        b.addAction(finger2.createPointerMove(Duration.ZERO, Origin.viewport(),
                (int) (maxX * 0.25), (int) (maxY * 0.75)));
        b.addAction(finger2.createPointerDown(MouseButton.LEFT.asArg()));
        b.addAction(finger2.createPointerMove(Duration.ofSeconds(1), Origin.viewport(),
                (int) (maxX * 0.5), (int) (maxY * 0.5)));
        b.addAction(finger2.createPointerUp(MouseButton.LEFT.asArg()));

        // Execute simultaneous gestures
        driver.perform(Arrays.asList(a, b));
    }

    /**
     * Performs zoom gesture (pinch out)
     */
    public void zoomInApp() {
        // Get screen dimensions
        int maxY = driver.manage().window().getSize().getHeight();
        int maxX = driver.manage().window().getSize().getWidth();

        // Create first finger gesture (center to top-right)
        PointerInput finger1 = new PointerInput(Kind.TOUCH, "finger1");
        Sequence a = new Sequence(finger1, 1);
        a.addAction(finger1.createPointerMove(Duration.ofSeconds(0), Origin.viewport(),
                (int) (maxX * 0.5), (int) (maxY * 0.5)));
        a.addAction(finger1.createPointerDown(MouseButton.LEFT.asArg()));
        a.addAction(finger1.createPointerMove(Duration.ofSeconds(1), Origin.viewport(),
                (int) (maxX * 0.75), (int) (maxY * 0.25)));
        a.addAction(finger1.createPointerUp(MouseButton.LEFT.asArg()));

        // Create second finger gesture (center to bottom-left)
        PointerInput finger2 = new PointerInput(Kind.TOUCH, "finger2");
        Sequence b = new Sequence(finger2, 1);
        b.addAction(finger2.createPointerMove(Duration.ofSeconds(0), Origin.viewport(),
                (int) (maxX * 0.5), (int) (maxY * 0.5)));
        b.addAction(finger2.createPointerDown(MouseButton.LEFT.asArg()));
        b.addAction(finger2.createPointerMove(Duration.ofSeconds(1), Origin.viewport(),
                (int) (maxX * 0.25), (int) (maxY * 0.75)));
        b.addAction(finger2.createPointerUp(MouseButton.LEFT.asArg()));

        // Execute simultaneous gestures
        driver.perform(Arrays.asList(a, b));
    }

    /**
     * Performs swipe in specified direction
     *
     * @param direction Swipe direction (up, down, left, right)
     * @throws RuntimeException for invalid direction
     */
    public void swipe(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                swipeUpInApp();  // Swipe from bottom to top
                break;
            case "down":
                swipeDownInApp();  // Swipe from top to bottom
                break;
            case "left":
                swipeLeftInApp();  // Swipe from right to left
                break;
            case "right":
                swipeRightInApp();  // Swipe from left to right
                break;
            default:
                throw new RuntimeException("Invalid swipe direction: " + direction);
        }
    }

    /**
     * Performs swipe up gesture
     *
     * @return true if swipe succeeds
     */
    private boolean swipeUpInApp() {
        // Calculate swipe coordinates (80% to 20% vertical)
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.getWidth() * 0.5);
        int startY = (int) (size.getHeight() * 0.8);
        int endX = (int) (size.getWidth() * 0.5);
        int endY = (int) (size.getHeight() * 0.2);
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Performs swipe down gesture
     *
     * @return true if swipe succeeds
     */
    private boolean swipeDownInApp() {
        // Calculate swipe coordinates (20% to 80% vertical)
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.getWidth() * 0.5);
        int startY = (int) (size.getHeight() * 0.2);
        int endX = (int) (size.getWidth() * 0.5);
        int endY = (int) (size.getHeight() * 0.8);
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Performs swipe left gesture
     *
     * @return true if swipe succeeds
     */
    private boolean swipeLeftInApp() {
        // Calculate swipe coordinates (80% to 20% horizontal)
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.getWidth() * 0.8);
        int startY = (int) (size.getHeight() * 0.5);
        int endX = (int) (size.getWidth() * 0.2);
        int endY = (int) (size.getHeight() * 0.5);
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Performs swipe right gesture
     *
     * @return true if swipe succeeds
     */
    private boolean swipeRightInApp() {
        // Calculate swipe coordinates (20% to 80% horizontal)
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.getWidth() * 0.2);
        int startY = (int) (size.getHeight() * 0.5);
        int endX = (int) (size.getWidth() * 0.8);
        int endY = (int) (size.getHeight() * 0.5);
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Performs swipe within WebElement boundaries
     *
     * @param direction Swipe direction (up, down, left, right)
     * @param ele       WebElement to contain swipe
     */
    public void swipeWithinWebElement(String direction, WebElement ele) {
        switch (direction.toLowerCase()) {
            case "up":
                swipeUpInAppWithWebElement(ele);  // Swipe up within element
                break;
            case "down":
                swipeDownInAppWithWebElement(ele);  // Swipe down within element
                break;
            case "left":
                swipeLeftInAppWithWebElement(ele);  // Swipe left within element
                break;
            case "right":
                swipeRightInAppWithWebElement(ele);  // Swipe right within element
                break;
            default:
                throw new RuntimeException("Invalid swipe direction: " + direction);
        }
    }

    /**
     * Performs swipe down within WebElement
     *
     * @param ele WebElement to contain swipe
     * @return true if swipe succeeds
     */
    private boolean swipeDownInAppWithWebElement(WebElement ele) {
        // Calculate element-relative coordinates (20% to 80% vertical)
        Rectangle rect = ele.getRect();
        int startX = (int) (((rect.getWidth()) * 0.5) + rect.getX());
        int startY = (int) (((rect.getHeight()) * 0.2) + rect.getY());
        int endX = (int) (((rect.getWidth()) * 0.5) + rect.getX());
        int endY = (int) (((rect.getHeight()) * 0.8) + rect.getY());
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Performs swipe up within WebElement
     *
     * @param ele WebElement to contain swipe
     * @return true if swipe succeeds
     */
    private boolean swipeUpInAppWithWebElement(WebElement ele) {
        // Calculate element-relative coordinates (80% to 20% vertical)
        Rectangle rect = ele.getRect();
        int startX = (int) (((rect.getWidth()) * 0.5) + rect.getX());
        int startY = (int) (((rect.getHeight()) * 0.8) + rect.getY());
        int endX = (int) (((rect.getWidth()) * 0.5) + rect.getX());
        int endY = (int) (((rect.getHeight()) * 0.2) + rect.getY());
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Performs swipe right within WebElement
     *
     * @param ele WebElement to contain swipe
     * @return true if swipe succeeds
     */
    private boolean swipeRightInAppWithWebElement(WebElement ele) {
        // Calculate element-relative coordinates (20% to 80% horizontal)
        Rectangle rect = ele.getRect();
        int startX = (int) (((rect.getWidth()) * 0.2) + rect.getX());
        int startY = (int) (((rect.getHeight()) * 0.5) + rect.getY());
        int endX = (int) (((rect.getWidth()) * 0.8) + rect.getX());
        int endY = (int) (((rect.getHeight()) * 0.5) + rect.getY());
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Performs swipe left within WebElement
     *
     * @param ele WebElement to contain swipe
     * @return true if swipe succeeds
     */
    private boolean swipeLeftInAppWithWebElement(WebElement ele) {
        // Calculate element-relative coordinates (80% to 20% horizontal)
        Rectangle rect = ele.getRect();
        int startX = (int) (((rect.getWidth()) * 0.8) + rect.getX());
        int startY = (int) (((rect.getHeight()) * 0.5) + rect.getY());
        int endX = (int) (((rect.getWidth()) * 0.2) + rect.getX());
        int endY = (int) (((rect.getHeight()) * 0.5) + rect.getY());
        return scrollWithGivenCoOrdinates(startX, startY, endX, endY);
    }

    /**
     * Swipes up until element is visible or max attempts reached
     *
     * @param locator  Locator strategy to find element
     * @param locValue Locator value to find element
     * @return true when element found or max attempts reached
     */
    public boolean swipeUpInAppUntilElementIsVisible(String locator, String locValue) {
        int attempt = 1;
        while (!eleIsDisplayed(getWebElement(locator, locValue))) {
            swipeUpInApp();  // Perform swipe up
            attempt++;
            if (attempt == MAX_SCROLL) break;  // Stop after max attempts
        }
        return true;
    }

    /**
     * Swipes down until element is visible or max attempts reached
     *
     * @param locator  Locator strategy to find element
     * @param locValue Locator value to find element
     * @return true when element found or max attempts reached
     */
    public boolean swipeDownInAppUntilElementIsVisible(String locator, String locValue) {
        int attempt = 1;
        while (!eleIsDisplayed(getWebElement(locator, locValue))) {
            swipeDownInApp();  // Perform swipe down
            attempt++;
            if (attempt == MAX_SCROLL) break;  // Stop after max attempts
        }
        return true;
    }

    /**
     * Swipes left until element is visible or max attempts reached
     *
     * @param locator  Locator strategy to find element
     * @param locValue Locator value to find element
     * @return true when element found or max attempts reached
     */
    public boolean swipeLeftInAppUntilElementIsVisible(String locator, String locValue) {
        int attempt = 1;
        while (!eleIsDisplayed(getWebElement(locator, locValue))) {
            swipeLeftInApp();  // Perform swipe left
            attempt++;
            if (attempt == MAX_SCROLL) break;  // Stop after max attempts
        }
        return true;
    }

    /**
     * Swipes right until element is visible or max attempts reached
     *
     * @param locator  Locator strategy to find element
     * @param locValue Locator value to find element
     * @return true when element found or max attempts reached
     */
    public boolean swipeRightInAppUntilElementIsVisible(String locator, String locValue) {
        int attempt = 1;
        while (!eleIsDisplayed(getWebElement(locator, locValue))) {
            swipeRightInApp();  // Perform swipe right
            attempt++;
            if (attempt == MAX_SCROLL) break;  // Stop after max attempts
        }
        return true;
    }

    /**
     * Closes application and cleans up driver session
     */
    public void closeApp() {
        if (driver != null) {
            try {
                driver.quit();  // Terminate driver session
            } catch (Exception ignored) {
                // Silently handle any cleanup errors
            }
        }
        if (startAppiumServerProgramatically) {
            stopServer();
        }
    }

    /**
     * Sets device orientation to portrait mode
     *
     * @return true if orientation change succeeds
     */
    public boolean setPortraitOrientation() {
        ((SupportsRotation) driver).rotate(ScreenOrientation.PORTRAIT);
        return true;
    }

    /**
     * Sets device orientation to landscape mode
     *
     * @return true if orientation change succeeds
     */
    public boolean setLandscapeOrientation() {
        ((SupportsRotation) driver).rotate(ScreenOrientation.LANDSCAPE);
        return true;
    }

    /**
     * Hides keyboard if visible (with iOS-specific workaround)
     * Note: May not work for numerical keyboards on iOS
     */
    public void hideKeyboard() {
        if (isKeyboardShown()) {
            try {
                // Standard keyboard dismissal
                ((HidesKeyboard) driver).hideKeyboard();
            } catch (Exception e) {
                // iOS-specific keyboard handling
                if (driver.getCapabilities().getPlatformName().toString().equalsIgnoreCase("iOS")) {
                    String context = ((SupportsContextSwitching) driver).getContext();
                    assert context != null;
                    boolean isNative = context.equalsIgnoreCase("NATIVE_APP");

                    // Switch to native context if needed
                    if (!isNative) switchNativeView();

                    // Try clicking Done button if keyboard still visible
                    if (isKeyboardShown()) {
                        click(getWebElement(Locators.ACCESSIBILITY_ID.toString(), "Done"));
                    }

                    // Restore original context if needed
                    if (!isNative) switchContext(context);
                }
            }
        }
    }

    /**
     * Checks if software keyboard is currently displayed
     *
     * @return true if keyboard is visible
     */
    public boolean isKeyboardShown() {
        return ((HasOnScreenKeyboard) driver).isKeyboardShown();
    }

    /**
     * Gets current device orientation
     *
     * @return Current orientation as string (PORTRAIT/LANDSCAPE)
     */
    public String getOrientation() {
        return ((SupportsRotation) driver).getOrientation().toString();
    }

    /**
     * Enters text into element with automatic keyboard hiding
     *
     * @param ele  WebElement to receive text
     * @param data Text to enter
     * @return true if text entry succeeds
     */
    public boolean enterValue(WebElement ele, String data) {
        return enterValue(ele, data, true);  // Default with keyboard hide
    }

    /**
     * Enters text into element with keyboard control
     *
     * @param ele          WebElement to receive text
     * @param data         Text to enter
     * @param hideKeyboard Whether to hide keyboard after entry
     * @return true if text entry succeeds
     */
    public boolean enterValue(WebElement ele, String data, boolean hideKeyboard) {
        ele.clear();  // Clear existing text
        ele.sendKeys(data);  // Enter new text
        if (hideKeyboard) hideKeyboard();  // Hide keyboard if requested
        return true;
    }

    /**
     * Clicks element with error handling
     *
     * @param ele WebElement to click
     */
    public void click(WebElement ele) {
        try {
            ele.click();  // Attempt click
        } catch (Exception ignored) {
            // Silently handle click failures
        }
    }

    /**
     * Gets visible text from element
     *
     * @param ele WebElement to read
     * @return Element's text content
     */
    public String getText(WebElement ele) {
        return ele.getText();
    }

    /**
     * Activates or relaunches application
     *
     * @param bundleIdOrAppPackage Bundle ID (iOS) or package name (Android)
     */
    public void activateOrRelaunchApp(String bundleIdOrAppPackage) {
        /**
         * If App is running in FG - Do nothing
         * If App is running in BG - Bring the app to the FG
         * If App is not running - Launch the app with the given appPackage / bundleId
         */
        ((InteractsWithApps) driver).activateApp(bundleIdOrAppPackage);
    }

    /**
     * Terminates running application
     *
     * @param bundleIdOrAppPackage Bundle ID (iOS) or package name (Android)
     */
    public void terminateOrStopRunningApp(String bundleIdOrAppPackage) {
        ((InteractsWithApps) driver).terminateApp(bundleIdOrAppPackage);
    }

    /**
     * Supported locator strategies for element finding
     */
    public enum Locators {
        ID("id"),                  // Find by resource-id (Android) or name (iOS)
        NAME("name"),              // Find by name attribute
        CLASS_NAME("className"),   // Find by class name
        LINK_TEXT("link"),         // Find by exact link text (web views)
        PARTIAL_LINK_TEXT("partialLink"),  // Find by partial link text
        TAG_NAME("tag"),           // Find by HTML tag name
        CSS("css"),                // Find by CSS selector (web views)
        XPATH("xpath"),            // Find by XPath expression
        ACCESSIBILITY_ID("accessibilityId");  // Find by accessibility identifier

        private final String value;

        Locators(String value) {
            this.value = value;
        }

        /**
         * Gets string representation of locator
         *
         * @return Locator strategy name
         */
        public String asString() {
            return this.value;
        }
    }
}