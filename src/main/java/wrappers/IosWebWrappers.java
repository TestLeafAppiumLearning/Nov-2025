package wrappers;

import io.appium.java_client.remote.SupportsContextSwitching;

import java.util.Objects;

/**
 * Provides iOS-specific web browser automation capabilities.
 * Extends IosNativeWrappers to include Safari-specific functionality.
 */
@SuppressWarnings("ALL")
public class IosWebWrappers extends IosNativeWrappers {

    /**
     * Launches Safari browser on iOS device
     *
     * @param URL  The initial URL to load in Safari
     * @param udid Device unique identifier
     * @return true if browser launch succeeds, false otherwise
     */
    public boolean launchSafariBrowser(String URL, String udid) {
        // Launch Safari with default configuration (single device)
        return launchBrowser("iOS", URL, udid, "", "", "", true);
    }

    /**
     * Launches Safari browser in parallel test configuration
     *
     * @param URL                  The initial URL to load in Safari
     * @param udid                 Device unique identifier
     * @param wdaLocalPort         WebDriverAgent port for parallel execution
     * @param webkitDebugProxyPort WebKit debug proxy port for web inspection
     * @return true if browser launch succeeds, false otherwise
     */
    public boolean launchSafariBrowserInParallel(String URL, String udid, String wdaLocalPort,
                                                 String webkitDebugProxyPort) {
        // Launch Safari with parallel execution configuration
        return launchBrowser("iOS", URL, udid, "", wdaLocalPort, webkitDebugProxyPort, true);
    }

    /**
     * Clears all cookies and browsing data from Safari
     *
     * @return true if cookies are cleared successfully, false otherwise
     * @note This method navigates through iOS Settings app to clear Safari data
     */
    public boolean deleteSafariCookies() {
        try {
            // Close and reopen Settings app
            terminateOrStopRunningApp("com.apple.Preferences");
            activateOrRelaunchApp("com.apple.Preferences");

            // Switch to native context and navigate to Safari settings
            switchNativeView();
            swipe("down"); // Scroll down to reveal search

            // Search for Safari settings
            enterValue(getWebElement(Locators.XPATH.asString(), "//*[@label='Search']"), "Safari");

            // Handle different iOS versions' UI
            try {
                click(getWebElement(Locators.XPATH.asString(), "//XCUIElementTypeCell[2]//*[@name='Safari']"));
            } catch (Exception e) {
                click(getWebElement(Locators.XPATH.asString(), "//*[@label='Safari']"));
            }

            sleep(1000); // Wait for settings to load

            // Find and click clear data option
            swipeUpInAppUntilElementIsVisible(Locators.XPATH.asString(),
                    "//*[@value='Clear History and Website Data' and @visible='true']");
            click(getWebElement(Locators.XPATH.asString(),
                    "//*[@value='Clear History and Website Data' and @visible='true']"));
            click(getWebElement(Locators.XPATH.asString(), "//*[@label='Clear' or @label='Clear History and Data']"));

            // Return to Safari
            terminateOrStopRunningApp("com.apple.Preferences");
            activateOrRelaunchApp("com.apple.mobilesafari");
            switchWebView();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clicks a keyboard button by its name attribute
     *
     * @param name The name attribute of the keyboard button
     */
    public void clickGivenKeyboardButtonInIosByName(String name) {
        handleKeyboardButtonClick(Locators.NAME.asString(), name);
    }

    /**
     * Clicks a keyboard button by its accessibility ID
     *
     * @param accessId The accessibility ID of the keyboard button
     */
    public void clickGivenKeyboardButtonInIosByAccessibilityId(String accessId) {
        handleKeyboardButtonClick(Locators.ACCESSIBILITY_ID.asString(), accessId);
    }

    /**
     * Clicks a keyboard button by its XPath
     *
     * @param xPath The XPath of the keyboard button
     */
    public void clickGivenKeyboardButtonInIosByXpath(String xPath) {
        handleKeyboardButtonClick(Locators.XPATH.asString(), xPath);
    }

    /**
     * Handles the keyboard button click with context switching
     *
     * @param locator      The locator strategy to use
     * @param locatorValue The value for the locator strategy
     */
    private void handleKeyboardButtonClick(String locator, String locatorValue) {
        try {
            // Store current context
            String context = ((SupportsContextSwitching) driver).getContext();
            boolean isNative = Objects.requireNonNull(context).equalsIgnoreCase("NATIVE_APP");

            // Switch to native context if needed
            if (!isNative) {
                switchNativeView();
            }

            // Click keyboard button if keyboard is shown
            if (isKeyboardShown()) {
                click(getWebElement(locator, locatorValue));
            }

            // Restore original context if needed
            if (!isNative) {
                switchContext(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}