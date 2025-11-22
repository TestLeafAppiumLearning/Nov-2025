package day7;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnHybridAppInIos extends GenericWrappers {

    @Test
    public void runCode() {
        //launchIosApp("2CDB9EC8-51BA-42D8-8973-3FBD791771D4", "", "com.example.apple-samplecode.UICatalog", "Uicatalog.zip");
        launchUiCatalogInIosUsingSauceLabs();
        swipe("up");
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Web View"));
        printContext();
        switchWebView();
//        click(getWebElement(Locators.ID.asString(), "ac-ls-continue"));
        click(getWebElement(Locators.XPATH.asString(), "(//a[text()='Learn more'])[1]"));
        swipe("up");
        click(getWebElement(Locators.XPATH.asString(), "(//a[text()='Buy'])[4]"));
        sleep(5000);
        closeApp();
    }
}
