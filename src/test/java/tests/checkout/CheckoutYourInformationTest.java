package tests.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.cart.AddToCartPage;
import pages.cart.YourCartPage;
import pages.login.LoginPage;
import tests.Base;
import vn.shopping.project.utils.ElementActions;
import vn.shopping.project.utils.ElementValidate;
import vn.shopping.project.utils.WaitElement;

public class CheckoutYourInformationTest extends Base {
    @BeforeMethod
    @Override
    protected void setUp() {
        super.setUp();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        AddToCartPage addToCartPage = new AddToCartPage(driver);
        products = addToCartPage.addToCart(2);
        addToCartPage.onclickYourCar();
        YourCartPage yourCartPage = new YourCartPage(driver);
        yourCartPage.clickBTNCheckout();
    }

    @Test
    public void testYourInformationWithValidCredentials() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
          ElementActions.clearAndType(inputFirstName, "cuong");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
          ElementActions.clearAndType(inputLastName, "nguyen");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
          ElementActions.clearAndType(inputPostalCode, "0987654321");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html", "Unable to switch pages.");
        WebElement titlePage = WaitElement.visible(driver, By.className("title"), 10);
        Assert.assertEquals(titlePage.getText(), "Checkout: Overview", "Unable to switch pages.");
    }

    @Test
    public void testYourInformationWithInvalidLastName() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
          ElementActions.clearAndType(inputFirstName, "cuong");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
          ElementActions.clearAndType(inputLastName, "");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
          ElementActions.clearAndType(inputPostalCode, "0987654321");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        WebElement errorMsg =WaitElement.visible(driver,By.xpath("//h3"),10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Error: Last Name is required"), "Unexpected error message!");
    }

    @Test
    public void testYourInformationWithInvalidFirstName() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
          ElementActions.clearAndType(inputFirstName, "");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
          ElementActions.clearAndType(inputLastName, "nguyen");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
          ElementActions.clearAndType(inputPostalCode, "0987654321");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        WebElement errorMsg =WaitElement.visible(driver,By.xpath("//h3"),10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Error: First Name is required"), "Unexpected error message!");
    }
    @Test
    public void testYourInformationWithInvalidPostalCode() {
        WebElement inputFirstName = WaitElement.visible(driver, By.id("first-name"), 10);
          ElementActions.clearAndType(inputFirstName, "cuong");
        WebElement inputLastName = WaitElement.visible(driver, By.id("last-name"), 10);
          ElementActions.clearAndType(inputLastName, "nguyen");
        WebElement inputPostalCode = WaitElement.visible(driver, By.id("postal-code"), 10);
          ElementActions.clearAndType(inputPostalCode, "");
        WebElement btnContinue = WaitElement.clickable(driver, By.id("continue"), 10);
        Assert.assertTrue(btnContinue.isDisplayed(),"The button continue is not displayed.");
        btnContinue.click();
        WebElement errorMsg =WaitElement.visible(driver,By.xpath("//h3"),10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Error: Postal Code is required"), "Unexpected error message!");
    }

    @Test
    public void testClickButtonCancel(){
        WebElement btnCancel = WaitElement.clickable(driver, By.id("cancel"), 10);
        Assert.assertTrue(btnCancel.isDisplayed(),"The button cancel is not displayed.");
        btnCancel.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, By.xpath("//span[@class=\"title\"]"), 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }

    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
