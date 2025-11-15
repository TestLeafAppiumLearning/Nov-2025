package wrappers;

/**
 * Provides Android-specific web browser automation capabilities.
 * Extends AndroidNativeWrappers to include Chrome browser-specific functionality.
 */
@SuppressWarnings("ALL")
public class AndroidWebWrappers extends AndroidNativeWrappers {

    /**
     * Launches Chrome browser on Android with default configuration
     *
     * @param URL The initial URL to load in the browser
     * @return true if browser launch succeeds, false otherwise
     */
    public boolean launchChromeBrowser(String URL) {
        // Launch Chrome with default configuration (no parallel execution)
        return launchBrowser("Android", URL, "", "", "", "", true);
    }

    /**
     * Launches Chrome browser in parallel test configuration
     *
     * @param URL              The initial URL to load in the browser
     * @param udid             Device unique identifier for parallel execution
     * @param chromeDriverPort ChromeDriver port for parallel execution
     * @return true if browser launch succeeds, false otherwise
     */
    public boolean launchChromeBrowserInParallel(String URL, String udid, String chromeDriverPort) {
        // Launch Chrome with parallel execution configuration
        return launchBrowser("Android", URL, udid, chromeDriverPort, "", "", true);
    }

    /**
     * Deletes all cookies from Chrome browser
     *
     * @return true if cookies are deleted successfully, false otherwise
     */
    public boolean deleteChromeCookies() {
        try {
            // Delete all browser cookies
            driver.get().manage().deleteAllCookies();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}