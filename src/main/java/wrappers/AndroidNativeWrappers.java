package wrappers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.connection.HasNetworkConnection;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;

import java.util.HashMap;

/**
 * Provides Android-specific native automation capabilities.
 * Extends CommonWebWrappers to include Android-specific functionality.
 */
@SuppressWarnings("ALL")
public class AndroidNativeWrappers extends CommonWebWrappers {

    /**
     * Launches an Android application with basic configuration
     *
     * @param appPackage     Android application package name
     * @param appActivity    Main activity to launch
     * @param automationName Automation engine (e.g., UiAutomator2)
     * @param app            Path to application file
     * @return true if launch succeeds
     */
    public boolean launchAndroidApp(String appPackage, String appActivity, String automationName,
                                    String app) {
        // Launch with default configuration (no parallel execution)
        return launchApp("Android", "", appPackage, appActivity, automationName, "",
                "", "", "", "", app, "", true);
    }

    /**
     * Launches an Android application in parallel test configuration
     *
     * @param udid             Device unique identifier
     * @param appPackage       Android application package name
     * @param appActivity      Main activity to launch
     * @param automationName   Automation engine (e.g., UiAutomator2)
     * @param chromeDriverPort Port for ChromeDriver (hybrid apps)
     * @param systemPort       System port for parallel execution
     * @param app              Path to application file
     * @return true if launch succeeds
     */
    public boolean launchAndroidAppInParallel(String udid, String appPackage, String appActivity,
                                              String automationName, String chromeDriverPort,
                                              String systemPort, String app) {
        // Launch with parallel execution configuration
        return launchApp("Android", udid, appPackage, appActivity, automationName, chromeDriverPort,
                systemPort, "", "", "", app, "", true);
    }

    /**
     * Starts an Android activity using package and activity names
     *
     * @param appPackage  Application package name
     * @param appActivity Activity name to start
     * @return true if activity starts successfully
     */
    public boolean startAnAppUsingActivity(String appPackage, String appActivity) {
        /**
         * If App is running in FG - Do nothing
         * If App is running in BG - Bring the app to the FG
         * If App is not running - Launch the app with the given appPackage / bundleId
         */
        try {
            // Create intent parameters
            HashMap<String, Object> params = new HashMap<>();
            params.put("intent", appPackage + "/" + appActivity);

            // Execute mobile command to start activity
            driver.executeScript("mobile: startActivity", params);
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Simulates pressing the ENTER key
     *
     * @return true if key press succeeds
     */
    public boolean pressEnter() {
        ((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        return true;
    }

    /**
     * Simulates pressing the BACK key
     *
     * @return true if key press succeeds
     */
    public boolean pressBack() {
        ((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.BACK));
        return true;
    }

    /**
     * Opens the Android notification shade
     */
    public void showNotificationMenu() {
        ((AndroidDriver) driver).openNotifications();  // Open notification drawer
    }

    /**
     * Closes the Android notification shade
     */
    public void hideNotificationMenu() {
        pressBack();  // Use back button to close notifications
    }

    /**
     * Disables mobile data and WiFi connections
     *
     * @return true if operation succeeds
     */
    public boolean dataOffInAndroid() {
        // Disable WiFi first
        ((HasNetworkConnection) driver).setConnection(
                new ConnectionStateBuilder().withWiFiDisabled().build());
        sleep(2000);  // Brief pause

        // Then disable mobile data
        ((HasNetworkConnection) driver).setConnection(
                new ConnectionStateBuilder().withDataDisabled().build());
        sleep(2000);  // Brief pause
        return true;
    }

    /**
     * Enables mobile data and WiFi connections
     *
     * @return true if operation succeeds
     */
    public boolean dataOnInAndroid() {
        // Enable WiFi first
        ((HasNetworkConnection) driver).setConnection(
                new ConnectionStateBuilder().withWiFiEnabled().build());
        sleep(2000);  // Brief pause

        // Then enable mobile data
        ((HasNetworkConnection) driver).setConnection(
                new ConnectionStateBuilder().withDataEnabled().build());
        sleep(2000);  // Brief pause
        return true;
    }

    /**
     * Gets the current foreground activity name
     *
     * @return Name of current activity
     */
    public String getCurrentActivity() {
        return ((StartsActivity) driver).currentActivity();
    }

    /**
     * Gets the current foreground application package
     *
     * @return Package name of current app
     */
    public String getCurrentAppPackage() {
        return ((StartsActivity) driver).getCurrentPackage();
    }
}