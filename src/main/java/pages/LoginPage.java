package pages;

import wrappers.GenericWrappers;

public class LoginPage extends GenericWrappers {

    public LoginPage() {
        eleIsDisplayed(getWebElement(Locators.CLASS_NAME.asString(), "android.widget.EditText"));
    }

    public LoginPage enterUserName(String userName) {
        enterValue(getWebElement(Locators.CLASS_NAME.asString(), "android.widget.EditText"),userName);
        return new LoginPage();
    }

    public LoginPage enterPassword(String password) {
			enterValue(getWebElement(Locators.XPATH.asString(), "(//android.widget.EditText)[2]"),password);
        return new LoginPage();
    }

    public LoginPage clickLoginForNegative() {
			click(getWebElement(Locators.CLASS_NAME.asString(), "android.widget.Button"));
        return new LoginPage();
    }

    public HomePage clickLogin() {
        click(getWebElement(Locators.CLASS_NAME.asString(), "android.widget.Button"));
        return new HomePage();
    }

}
