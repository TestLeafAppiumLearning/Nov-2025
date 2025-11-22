package wrappers;

import org.openqa.selenium.WebElement;

import java.util.HashMap;

/**
 * Provides iOS-specific native automation capabilities.
 * Extends AndroidWebWrappers to include iOS-specific functionality.
 */
@SuppressWarnings("ALL")
public class IosNativeWrappers extends AndroidWebWrappers {

    /**
     * Launches an iOS application with basic configuration
     *
     * @param udid       Device unique identifier
     * @param xcodeOrgId Xcode organization identifier
     * @param bundleId   iOS application bundle identifier
     * @param app        Path to the application file
     * @return true if launch succeeds, false otherwise
     */
    public boolean launchIosApp(String udid, String xcodeOrgId, String bundleId, String app) {
        // Launch iOS app with default configuration (single device)
        return launchApp("iOS", udid, "", "", "XCUITest", "", "",
                xcodeOrgId, "iPhone Developer", bundleId, app, "", true);
    }

    /**
     * Launches an iOS application in parallel test configuration
     *
     * @param udid         Device unique identifier
     * @param xcodeOrgId   Xcode organization identifier
     * @param bundleId     iOS application bundle identifier
     * @param app          Path to the application file
     * @param wdaLocalPort WebDriverAgent port for parallel execution
     * @return true if launch succeeds, false otherwise
     */
    public boolean launchIosAppInParallel(String udid, String xcodeOrgId, String bundleId,
                                          String app, String wdaLocalPort) {
        // Launch iOS app with parallel execution configuration
        return launchApp("iOS", udid, "", "", "XCUITest", "", "",
                xcodeOrgId, "iPhone Developer", bundleId, app, wdaLocalPort, true);
    }

    /**
     * Selects the next option in an iOS picker wheel using locator
     *
     * @param locator      Locator strategy (id, name, xpath, etc.)
     * @param locatorValue Value of the locator
     * @return true if operation succeeds, false otherwise
     */
    public boolean chooseNextOptionInPickerWheel(String locator, String locatorValue) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("order", "next");      // Direction to move
            params.put("offset", 0.15);       // Scroll offset percentage
            params.put("element", getWebElement(locator, locatorValue));  // Picker wheel element
            driver.executeScript("mobile: selectPickerWheelValue", params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Selects the next option in an iOS picker wheel using WebElement
     *
     * @param ele The picker wheel WebElement
     * @return true if operation succeeds, false otherwise
     */
    public boolean chooseNextOptionInPickerWheel(WebElement ele) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("order", "next");      // Direction to move
            params.put("offset", 0.15);      // Scroll offset percentage
            params.put("element", ele);       // Picker wheel element
            driver.executeScript("mobile: selectPickerWheelValue", params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Selects the previous option in an iOS picker wheel using locator
     *
     * @param locator      Locator strategy (id, name, xpath, etc.)
     * @param locatorValue Value of the locator
     * @return true if operation succeeds, false otherwise
     */
    public boolean choosePreviousOptionInPickerWheel(String locator, String locatorValue) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("order", "previous");  // Direction to move
            params.put("offset", 0.15);       // Scroll offset percentage
            params.put("element", getWebElement(locator, locatorValue));  // Picker wheel element
            driver.executeScript("mobile: selectPickerWheelValue", params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Selects the previous option in an iOS picker wheel using WebElement
     *
     * @param ele The picker wheel WebElement
     * @return true if operation succeeds, false otherwise
     */
    public boolean choosePreviousOptionInPickerWheel(WebElement ele) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("order", "previous");  // Direction to move
            params.put("offset", 0.15);      // Scroll offset percentage
            params.put("element", ele);       // Picker wheel element
            driver.executeScript("mobile: selectPickerWheelValue", params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}