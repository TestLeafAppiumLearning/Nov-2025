package pages;

import wrappers.GenericWrappers;

public class HomePage extends GenericWrappers {
    public HomePage() {
        eleIsDisplayed(getWebElement(Locators.XPATH.asString(), "//android.view.View[@text=\"PARTICIPANT NAME\"]"));
    }

    public HomePage verifyName(String name) {
        verifyText(getWebElement(Locators.XPATH.asString(), "//android.view.View[@text=\"PARTICIPANT NAME\"]/preceding-sibling::android.view.View"),
                name);
        return new HomePage();
    }


}
