package tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;
import vn.shopping.project.utils.ElementValidate;
import vn.shopping.project.utils.WaitElement;

public class LoginMultiCaseTest extends BasePage {

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginWithValidCredentials() {
        driver.get("https://www.saucedemo.com/");

        // Kiểm tra tiêu đề và URL
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // Điền thông tin đúng
        WebElement userName = WaitElement.visible(driver, By.id("user-name"), 10);
        ElementValidate.clearAndType(userName, "standard_user");
        Assert.assertEquals(userName.getAttribute("value").trim(), "standard_user", "Incorrect user name entered");

        WebElement password = WaitElement.visible(driver, By.id("password"), 10);
        ElementValidate.clearAndType(password, "secret_sauce");
        Assert.assertEquals(password.getAttribute("value").trim(), "secret_sauce", "Incorrect password entered");

        WebElement buttonLogin = WaitElement.clickable(driver, By.id("login-button"), 10);
        Assert.assertTrue(buttonLogin.isDisplayed(), "The login button is not displayed.");
        buttonLogin.click();
        // Xác nhận vào trang inventory
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        // Kiểm tra logo & sản phẩm
        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No products displayed!");
    }

    @Test
    public void testLoginWithInvalidUsername() {
        driver.get("https://www.saucedemo.com/");

        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        WebElement userName = WaitElement.visible(driver, By.id("user-name"), 10);
        ElementValidate.clearAndType(userName, "invalid_user");
        Assert.assertEquals(userName.getAttribute("value").trim(), "invalid_user", "Incorrect user name entered");

        WebElement password = WaitElement.visible(driver, By.id("password"), 10);
        ElementValidate.clearAndType(password, "secret_sauce");
        Assert.assertEquals(password.getAttribute("value").trim(), "secret_sauce", "Incorrect password entered");

        WebElement buttonLogin = WaitElement.clickable(driver, By.id("login-button"), 10);
        Assert.assertTrue(buttonLogin.isDisplayed(), "The login button is not displayed.");
        buttonLogin.click();
        // Kiểm tra thông báo lỗi
        WebElement errorMsg = WaitElement.visible(driver, By.cssSelector("[data-test='error']"), 10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Username and password do not match"), "Unexpected error message!");
    }

    @Test
    public void testLoginWithInvalidPassword() {
        driver.get("https://www.saucedemo.com/");

        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        WaitElement.visible(driver, By.id("user-name"), 10).sendKeys("standard_user");
        WaitElement.visible(driver, By.id("password"), 10).sendKeys("invalid_pass");
        WaitElement.clickable(driver, By.id("login-button"), 10).click();

        WebElement errorMsg = WaitElement.visible(driver, By.cssSelector("[data-test='error']"), 10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Username and password do not match"), "Unexpected error message!");
    }

    @Test
    public void testLoginWithEmptyUsername() {
        driver.get("https://www.saucedemo.com/");

        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        WaitElement.visible(driver, By.id("user-name"), 10).sendKeys("");
        WaitElement.visible(driver, By.id("password"), 10).sendKeys("secret_sauce");
        WebElement userName = WaitElement.visible(driver, By.id("user-name"), 10);
        ElementValidate.clearAndType(userName, "");
//        Assert.assertEquals(userName.getAttribute("value").trim(), "", "Incorrect user name entered");

        WebElement password = WaitElement.visible(driver, By.id("password"), 10);
        ElementValidate.clearAndType(password, "secret_sauce");
        Assert.assertEquals(password.getAttribute("value").trim(), "secret_sauce", "Incorrect password entered");

        WebElement buttonLogin = WaitElement.clickable(driver, By.id("login-button"), 10);
        Assert.assertTrue(buttonLogin.isDisplayed(), "The login button is not displayed.");
        buttonLogin.click();

        WebElement errorMsg = WaitElement.visible(driver, By.cssSelector("[data-test='error']"), 10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Username is required"), "Unexpected error message!");
    }

    @Test
    public void testLoginWithEmptyPassword() {
        driver.get("https://www.saucedemo.com/");

        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        WebElement userName = WaitElement.visible(driver, By.id("user-name"), 10);
        ElementValidate.clearAndType(userName, "invalid_user");
        Assert.assertEquals(userName.getAttribute("value").trim(), "invalid_user", "Incorrect user name entered");

        WebElement password = WaitElement.visible(driver, By.id("password"), 10);
        ElementValidate.clearAndType(password, "");
        Assert.assertEquals(password.getAttribute("value").trim(), "", "Incorrect password entered");

        WebElement buttonLogin = WaitElement.clickable(driver, By.id("login-button"), 10);
        Assert.assertTrue(buttonLogin.isDisplayed(), "The login button is not displayed.");
        buttonLogin.click();

        WebElement errorMsg = WaitElement.visible(driver, By.cssSelector("[data-test='error']"), 10);
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed!");
        Assert.assertTrue(errorMsg.getText().contains("Password is required"), "Unexpected error message!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
