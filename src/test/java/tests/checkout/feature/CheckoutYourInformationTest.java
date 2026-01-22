package tests.checkout.feature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Base;
import tests.cart.action.YourCartPage;
import tests.checkout.action.CheckoutPage;
import tests.checkout.ui.CheckoutPageUI;
import tests.inventory.action.InventoryPage;
import tests.inventory.ui.InventoryPageUI;
import tests.login.action.LoginPage;
import vn.shopping.project.utils.WaitElement;
import vn.shopping.project.utils.validation.Validator;

public class CheckoutYourInformationTest extends Base {
    CheckoutPage checkoutPage;

    @BeforeMethod
    @Override
    protected void setUp() {
        super.setUp();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = new InventoryPage(driver);
        products = inventoryPage.addToCart(5);
        inventoryPage.onclickYourCar();
        YourCartPage yourCartPage = new YourCartPage(driver);
        yourCartPage.clickButtonCheckout();
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void userTestYourInformationWithValidCredentials() {
        checkoutPage.enterYourInformation("cuong", "nguyen", "123456");

        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html"), "Unable to switch pages.");
        WebElement titlePage = WaitElement.visible(driver, By.className("title"), 10);
        Assert.assertTrue(Validator.isTextEqual(titlePage.getText(), "Checkout: Overview"), "Unable to switch pages.");
    }

    @Test
    public void userTestYourInformationWithInvalidLastName() {
        checkoutPage.enterYourInformation("cuong", "", "123456");

        WebElement errorMsg = WaitElement.visible(driver, CheckoutPageUI.ERROR_MSG, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(errorMsg), "Error message not displayed!");
        Assert.assertTrue(Validator.isTextEqual(errorMsg.getText(), "Error: Last Name is required"), "Unexpected error message!");
    }

    @Test
    public void userTestYourInformationWithInvalidFirstName() {
        checkoutPage.enterYourInformation("", "nguyen", "123456");

        WebElement errorMsg = WaitElement.visible(driver, CheckoutPageUI.ERROR_MSG, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(errorMsg), "Error message not displayed!");
        Assert.assertTrue(Validator.isTextEqual(errorMsg.getText(), "Error: First Name is required"), "Unexpected error message!");
    }
    @Test
    public void userTestYourInformationWithInvalidPostalCode() {
        checkoutPage.enterYourInformation("cuong", "nguyen", "");

        WebElement errorMsg = WaitElement.visible(driver, CheckoutPageUI.ERROR_MSG, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(errorMsg), "Error message not displayed!");
        Assert.assertTrue(Validator.isTextEqual(errorMsg.getText(), "Error: Postal Code is required"), "Unexpected error message!");
    }

    @Test
    public void userTestClickButtonCancel(){
        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.clickable(driver, CheckoutPageUI.CANCEL_BUTTON, 10)), "The button cancel is not displayed.");
        checkoutPage.onClickButtonCancel();
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html"), "Unable to switch pages.");
        Assert.assertTrue(Validator.isTextEqual(WaitElement.visible(driver, InventoryPageUI.CART_TITLE, 10).getText(), "Your Cart"), "Unable to switch pages.");
    }

    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
