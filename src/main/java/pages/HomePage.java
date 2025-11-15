package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import wrappers.GenericWrappers;

public class HomePage extends GenericWrappers {
    @AndroidFindBy(xpath = "//android.view.View[@text=\"PARTICIPANT NAME\"]")
    @iOSXCUITFindBy(id = "sample")
    private WebElement participantNameText;

    @AndroidFindBy(xpath = "//android.view.View[@text=\"PARTICIPANT NAME\"]/preceding-sibling::android.view.View")
    @iOSXCUITFindBy(id = "sample")
    private WebElement usersNameInHomePageTxt;

    public HomePage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver.get()), this);
        eleIsDisplayed(participantNameText);
    }

    @Then("Verify Home page is displayed")
    public HomePage verifyName() {
        verifyText(usersNameInHomePageTxt, "Raj Kumar");
        return new HomePage();
    }

}
