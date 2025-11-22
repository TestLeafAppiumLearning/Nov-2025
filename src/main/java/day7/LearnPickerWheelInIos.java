package day7;

import org.testng.annotations.Test;
import wrappers.GenericWrappers;

public class LearnPickerWheelInIos extends GenericWrappers {

    @Test
    public void runCode() {
        launchIosApp("2CDB9EC8-51BA-42D8-8973-3FBD791771D4", "", "com.example.apple-samplecode.UICatalog", "Uicatalog.zip");
        click(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Picker View"));

        enterValue(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Red color component value"), "60", false);
        String data = getText(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Red color component value"));
        if (!data.equals("50")) {
            System.out.println("Send Keys not working, so checking out with picker wheel");
            while (Integer.parseInt(getText(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Red color component value"))) < 50) {
                chooseNextOptionInPickerWheel(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Red color component value"));
            }
            while (Integer.parseInt(getText(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Red color component value"))) > 50) {
                choosePreviousOptionInPickerWheel(getWebElement(Locators.ACCESSIBILITY_ID.asString(), "Red color component value"));
            }
        }


    }
}
