package day2;

import initialWrappers.WrappersInitial;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class AndroidFirstCode extends WrappersInitial {

    @Test
    public void runTest() {
        launchApp("Android", "UiAutomator2", "leaforg.apk", "com.testleaf.leaforg",
                "com.testleaf.leaforg.MainActivity", "", "", "");
        WebElement email = driver.findElement(AppiumBy.className("android.widget.EditText"));
        enterText(email, "rajkumar@testleaf.com");
        WebElement pass = driver.findElement(AppiumBy.xpath("(//android.widget.EditText)[2]"));
        enterText(pass, "Leaf@123");
        WebElement login = driver.findElement(AppiumBy.className("android.widget.Button"));
        click(login);
        sleep(5);
        closeSession();
    }
}