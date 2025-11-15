package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import wrappers.GenericWrappers;

public class LoginPage extends GenericWrappers {

    @AndroidFindBy(className = "android.widget.EditText")
    @iOSXCUITFindBy(id = "sample")
    private WebElement usernameTextBox;

    @AndroidFindBy(xpath = "(//android.widget.EditText)[2]")
    @iOSXCUITFindBy(id = "sample")
    private WebElement passwordTextBox;

    @AndroidFindBy(className = "android.widget.Button")
    @iOSXCUITFindBy(id = "sample")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//*[@text='User does not exist']")
    @iOSXCUITFindBy(id = "sample")
    private WebElement errorMessageText;


    public LoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver.get()), this);
        eleIsDisplayed(usernameTextBox);
    }

    @When("the user enters the email address as {string}")
    public LoginPage enterUserName(String userName) {
        enterValue(usernameTextBox, userName);
        return new LoginPage();
    }

    @When("the user enters the password as {string}")
    public LoginPage enterPassword(String password) {
        enterValue(passwordTextBox, password);
        return new LoginPage();
    }

    @When("the user clicks on the login button")
    public void clickLogin() {
        click(loginButton);
    }

    @When("Error message is displayed")
    public void error_message_is_displayed() {
        verifyText(errorMessageText, "User does not exist");
    }
}
