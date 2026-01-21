package tests.login.feature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Base;
import tests.login.action.LoginPage;
import tests.login.ui.LoginPageUI;
import vn.shopping.project.utils.WaitElement;
import vn.shopping.project.utils.validation.Validator;

public class LoginTest extends Base {
    LoginPage loginPage;

    @BeforeMethod
    @Override
    protected void setUp() {
        super.setUp();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void userTestLoginWithValidCredentials() {
        Assert.assertTrue(Validator.isTextEqual(driver.getTitle(), "Swag Labs"), "Title mismatch!");
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/"), "URL mismatch!");

        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html"), "Login failed");
        WebElement appLogo = WaitElement.visible(driver, LoginPageUI.APP_LOGO, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(appLogo), "Login failed");
        WebElement listProduct = WaitElement.visible(driver, LoginPageUI.INVENTORY_ITEM, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(listProduct), "Login failed");
        WebElement sortProduct = WaitElement.visible(driver, LoginPageUI.INVENTORY_PRODUCT_SORT, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(sortProduct), "Login failed");

    }

    @Test
    public void userTestLoginWithInvalidUsername() {
        Assert.assertTrue(Validator.isTextEqual(driver.getTitle(), "Swag Labs"), "Title mismatch!");
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/"), "URL mismatch!");

        loginPage.login("invalid_user", "secret_sauce");

        WebElement errorMsg = WaitElement.visible(driver, LoginPageUI.ERROR_MESSAGE, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(errorMsg), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Username and password do not match"), "Unexpected error message!");
    }

    @Test
    public void userTestLoginWithInvalidPassword() {
        Assert.assertTrue(Validator.isTextEqual(driver.getTitle(), "Swag Labs"), "Title mismatch!");
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/"), "URL mismatch!");

        loginPage.login("standard_user", "invalid_pass");

        WebElement errorMsg = WaitElement.visible(driver, LoginPageUI.ERROR_MESSAGE, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(errorMsg), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Username and password do not match"), "Unexpected error message!");
    }

    @Test
    public void userTestLoginWithEmptyUsername() {
        Assert.assertTrue(Validator.isTextEqual(driver.getTitle(), "Swag Labs"), "Title mismatch!");
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/"), "URL mismatch!");

        loginPage.login("", "secret_sauce");

        WebElement errorMsg = WaitElement.visible(driver, LoginPageUI.ERROR_MESSAGE, 10);
        Assert.assertTrue(Validator.validateElementDisplayed(errorMsg), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Username is required"), "Unexpected error message!");
    }

    @Test
    public void userTestLoginWithEmptyPassword() {
        Assert.assertTrue(Validator.isTextEqual(driver.getTitle(), "Swag Labs"), "Title mismatch!");
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/"), "URL mismatch!");

        loginPage.login("standard_user", "");

        WebElement errorMsg = WaitElement.visible(driver, By.cssSelector("[data-test='error']"), 10);
        Assert.assertTrue(Validator.validateElementDisplayed(errorMsg), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Password is required"), "Unexpected error message!");
    }

    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
