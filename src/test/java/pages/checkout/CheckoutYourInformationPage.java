package pages.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import vn.shopping.project.utils.ElementActions;
import vn.shopping.project.utils.ElementValidate;
import vn.shopping.project.utils.WaitElement;

public class CheckoutYourInformationPage extends BasePage {
    // Locators
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");

    public CheckoutYourInformationPage(WebDriver driver) {
        super(driver);
    }

    private void enterFirstName(String firstName) {
        WebElement inputFirstName = WaitElement.visible(driver, firstNameInput, 10);
          ElementActions.clearAndType(inputFirstName, firstName);
    }

    private void enterLastName(String lastName) {
        WebElement inputLastName = WaitElement.visible(driver, lastNameInput, 10);
          ElementActions.clearAndType(inputLastName, lastName);
    }

    private void enterPostalCode(String postalCode) {
        WebElement inputPostalCode = WaitElement.visible(driver, postalCodeInput, 10);
          ElementActions.clearAndType(inputPostalCode, postalCode);
    }

    private void clickContinueBTN() {
        WaitElement.clickable(driver, continueButton, 10).click();
    }

    public void enterYourInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        clickContinueBTN();
    }
}
